package com.example.propianocoverbook.screen

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.example.propianocoverbook.R
import com.example.propianocoverbook.api.Artist
import com.example.propianocoverbook.api.SpotifyApiService
import com.example.propianocoverbook.api.Track
import com.example.propianocoverbook.data.MusicInfoViewModel

@Composable
fun RecordScreen(
    viewModel: MusicInfoViewModel,
    retrofitService: SpotifyApiService,
    authToken: String
) {
    var textOfMusic by rememberSaveable { mutableStateOf("") }
    var textOfArtist by rememberSaveable { mutableStateOf("") }
    var textOfGenre by rememberSaveable { mutableStateOf("") }
    var textOfStyle by rememberSaveable { mutableStateOf("") }
    var textOfMemo by rememberSaveable { mutableStateOf("") }
    var numOfRightHand by rememberSaveable { mutableFloatStateOf(0F) }
    var numOfLeftHand by rememberSaveable { mutableFloatStateOf(0F) }
    var suggestedMusic by remember { mutableStateOf<List<Track>>(emptyList()) }
    var suggestedArtists by remember { mutableStateOf<List<Artist>>(emptyList()) }
    var isArtistsSuggestionVisible by remember { mutableStateOf(false) }
    var isMusicSuggestionVisible by remember { mutableStateOf(false) }

    // 曲名の入力フィールドの位置を取得するための参照
    val musicFieldOffset = remember { mutableStateOf(Offset.Zero) }

    // アーティスト名の入力フィールドの位置を取得するための参照
    val artistFieldOffset = remember { mutableStateOf(Offset.Zero) }

    // アーティスト名の入力フィールドの高さを取得するための変数
    var musicFieldHeight by remember { mutableStateOf(60) }

    // アーティスト名の入力フィールドの高さを取得するための変数
    var artistFieldHeight by remember { mutableStateOf(120) }

    // 曲名の入力フィールドの位置を取得するModifier
    val musicFieldModifier = Modifier
        .onGloballyPositioned { coordinates ->
            musicFieldOffset.value = coordinates.positionInRoot()
            musicFieldHeight = coordinates.size.height
        }

    // アーティスト名の入力フィールドの位置を取得するModifier
    val artistFieldModifier = Modifier
        .onGloballyPositioned { coordinates ->
            artistFieldOffset.value = coordinates.positionInRoot()
            artistFieldHeight = coordinates.size.height
        }

    LaunchedEffect(isMusicSuggestionVisible) {
        suggestedMusic = if (isMusicSuggestionVisible && textOfMusic.isNotBlank()) {
            fetchMusicSuggestions(
                textOfMusic,
                authToken,
                retrofitService
            )
        } else {
            emptyList()
        }
    }

    LaunchedEffect(isArtistsSuggestionVisible) {
        suggestedArtists = if (isArtistsSuggestionVisible && textOfArtist.isNotBlank()) {
            fetchArtistSuggestions(
                textOfArtist,
                authToken,
                retrofitService
            )
        } else {
            emptyList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.space_16_dp))
    ) {
        Column {
            MusicOutlinedTextField(
                label = stringResource(id = R.string.music_name),
                placeholder = stringResource(id = R.string.placeholder_music),
                value = textOfMusic,
                onValueChange = {
                    textOfMusic = it
                    isMusicSuggestionVisible = true},
                modifier = musicFieldModifier
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))

            // アーティスト名の入力フィールド
            MusicOutlinedTextField(
                label = stringResource(id = R.string.artist_name),
                placeholder = stringResource(id = R.string.placeholder_artist),
                value = textOfArtist,
                onValueChange = {
                    textOfArtist = it
                    isArtistsSuggestionVisible = true},
                modifier = artistFieldModifier
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = dimensionResource(id = R.dimen.space_16_dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "ジャンル")
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
                    DropdownMenuWithIcon(
                        modifier = Modifier.weight(1f),
                        items = listOf("クラシック", "ジャズ", "ポップス", "ロック", "その他"),
                        value = textOfGenre,
                        onValueChange = { textOfGenre = it },
                    )
                }
                Row (
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "演奏スタイル")
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
                    DropdownMenuWithIcon(
                        modifier = Modifier.weight(1f),
                        items = listOf("独奏", "連弾", "伴奏", "弾き語り"),
                        value = textOfStyle,
                        onValueChange = { textOfStyle = it }
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
            MusicOutlinedTextField(
                label = stringResource(id = R.string.memo_name),
                placeholder = stringResource(id = R.string.placeholder_memo),
                value = textOfMemo,
                onValueChange = { textOfMemo = it },
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))
            ProgressSection(stringResource(id = R.string.right_hand)) {
                CircularProgressWithSeekBar(
                    value = numOfRightHand,
                    onValueChange = { numOfRightHand = it }
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))

            ProgressSection(stringResource(id = R.string.left_hand)) {
                CircularProgressWithSeekBar(
                    value = numOfLeftHand,
                    onValueChange = { numOfLeftHand = it }
                )
            }

            val isButtonEnabled = textOfMusic.isNotBlank()
                    && textOfArtist.isNotBlank()
                    && textOfGenre.isNotBlank()
                    && textOfStyle.isNotBlank()
                    && textOfMemo.isNotBlank()
                    && numOfRightHand > 1
                    && numOfLeftHand > 1

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SaveButton(
                        onClick = {
                            viewModel.saveValues(
                                textOfMusic,
                                textOfArtist,
                                textOfGenre,
                                textOfStyle,
                                textOfMemo,
                                numOfRightHand,
                                numOfLeftHand
                            )
                        },
                        enabled = isButtonEnabled
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))
        }

        // 曲の候補リストを表示
        if (suggestedMusic.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.TopStart)
                    .offset(
                        x = artistFieldOffset.value.x.dp,
                        y = (artistFieldOffset.value.y + artistFieldHeight).dp
                    )
                    .clip(RoundedCornerShape(8.dp)) // 角丸を適用
                    .background(Color.White.copy(alpha = 0.9f)) // 背景色を半透明の白に設定
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // 角丸の枠線を適用
            ) {
                items(suggestedMusic) { music ->
                    Text(
                        text = music.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                textOfMusic = music.name // タップした曲名を格納
                                isMusicSuggestionVisible = false  // 選択後は候補を非表示にする
                            }
                            .padding(8.dp) // 余白を追加
                    )
                }
            }
        }

        // アーティストの候補リストを表示
        if (suggestedArtists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.TopStart)
                    .offset(
                        x = artistFieldOffset.value.x.dp,
                        y = (artistFieldOffset.value.y + artistFieldHeight).dp
                    )
                    .clip(RoundedCornerShape(8.dp)) // 角丸を適用
                    .background(Color.White.copy(alpha = 0.9f)) // 背景色を半透明の白に設定
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // 角丸の枠線を適用
            ) {
                items(suggestedArtists) { artist ->
                    Text(
                        text = artist.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                textOfArtist = artist.name // タップしたアーティスト名を格納
                                isArtistsSuggestionVisible = false  // 選択後は候補を非表示にする
                            }
                            .padding(8.dp) // 余白を追加
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun RecordScreenPreview(
//    @PreviewParameter(PreviewParameterProvider::class)
//    viewModel: MusicInfoViewModel
//) {
//    ProPianoCoverBookTheme {
//        RecordScreen(viewModel = viewModel)
//    }
//}

//@Preview
//@Composable
//private fun RecordScreenPreview(
//
//) {
//    ProPianoCoverBookTheme {
//        RecordScreen()
//    }
//}

@Composable
private fun MusicOutlinedTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(0.18f))
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
        androidx.compose.material.OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(id = R.dimen.text_field_height)),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.text_size_normal).value.sp),
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}

// Modify fetchArtistSuggestions to accept retrofitService
suspend fun fetchArtistSuggestions(query: String, authToken: String, retrofitService: SpotifyApiService): List<Artist> {
    return withContext(Dispatchers.IO) {
        try {
            val response = retrofitService.searchMusic( // Use the passed retrofitService here
                query = query,
                type = "artist",
                authHeader = "Bearer $authToken"
            )

            if (response.isSuccessful) {
                val spotifySearchResponse = response.body()
                spotifySearchResponse?.artists?.items ?: emptyList()
            } else {
                // Handle error (e.g., log, display error message)
                println("Error fetching artist suggestions: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            println("Exception fetching artist suggestions: ${e.message}")
            emptyList()
        }
    }
}

suspend fun fetchMusicSuggestions(
    query: String,
    authToken: String,
    retrofitService: SpotifyApiService
): List<Track> {
    return withContext(Dispatchers.IO) {
        try {
            val response = retrofitService.searchMusic( // Use the passed retrofitService here
                query = query,
                type = "track",
                authHeader = "Bearer $authToken"
            )

            if (response.isSuccessful) {
                val spotifySearchResponse = response.body()
                spotifySearchResponse?.tracks?.items ?: emptyList()
            } else {
                // Handle error (e.g., log, display error message)
                println("Error fetching artist suggestions: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            println("Exception fetching artist suggestions: ${e.message}")
            emptyList()
        }
    }
}

@Composable
private fun DropdownMenuWithIcon(
    modifier: Modifier,
    items: List<String>,
    value: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.wrapContentSize(Alignment.Center)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.clickable { expanded = !expanded }
            ) {
                Text(
                    text = value, // 現在の値を表示
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, 8.dp)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onValueChange(item) // 選択されたアイテムを通知
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressSection(label: String, progressContent: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 20.sp)
        progressContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CircularProgressWithSeekBar(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(dimensionResource(id = R.dimen.circular_progress_with_seek_bar_size))
    ) {
        CircularProgressIndicator(
            color = Color(0xff8a2be2),
            strokeWidth = dimensionResource(id = R.dimen.circular_progress_indicator_stroke_width),
            progress = value / 100,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.circular_progress_with_seek_bar_size))
                .padding(dimensionResource(id = R.dimen.space_8_dp))
        )
        Text(
            text = "${value.toInt()}%",
            style = TextStyle(fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp),
        )
    }
    Slider(
        colors = SliderDefaults.colors(
            activeTrackColor = Color(0xff8a2be2),
            inactiveTrackColor = Color.Gray,
            thumbColor = Color.Gray
        ),
        value = value,
        onValueChange = onValueChange,
        valueRange = 0f..100f,
        thumb = {
            Canvas(Modifier.size(8.dp)) {
                drawCircle(Color(0xff8a2be2))
            }
        }
    )
}

@Composable
private fun SaveButton(
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    val context = LocalContext.current
    Button(
        onClick = {
            onClick()
            showToast(context, "記録しました")
        },
        colors = ButtonDefaults.buttonColors(Color.Blue),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
    ) {
        Text(stringResource(id = R.string.record_button))
    }
}

private fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}