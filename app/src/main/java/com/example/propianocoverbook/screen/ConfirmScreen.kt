package com.example.propianocoverbook.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

import com.example.propianocoverbook.R
import com.example.propianocoverbook.data.MusicInfo
import com.example.propianocoverbook.data.MusicInfoViewModel
import com.example.propianocoverbook.ui.theme.ProPianoCoverBookTheme

@Composable
fun ConfirmScreen(viewModel: MusicInfoViewModel) {
    val musicInfoList = viewModel.musicInfo.collectAsState().value

    if (musicInfoList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "まだデータが登録されていません。",
                fontSize = 18.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SearchScreen()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = musicInfoList) { musicInfo ->
                    MusicItem(musicInfo = musicInfo, viewModel)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ConfirmScreenPreview() {
//    ProPianoCoverBookTheme {
//        ConfirmScreen()
//    }
//}

@Composable
private fun MusicItem(
    musicInfo: MusicInfo,
    viewModel: MusicInfoViewModel
) {
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(text = "曲名: ${musicInfo.nameOfMusic}")
                Text(text = "作曲者名: ${musicInfo.nameOfArtist}")
                Text(text = "ジャンル: ${musicInfo.nameOfJenre}")
                Text(text = "演奏スタイル: ${musicInfo.nameOfStyle}")
                Text(text = "メモ: ${musicInfo.nameOfMemo}")
                Text(text = "右手の習熟度: ${musicInfo.levelOfRightHand}")
                Text(text = "左手の習熟度: ${musicInfo.levelOfLeftHand}")
            }
            Menu(
                modifier = Modifier.align(Alignment.CenterEnd),
                viewModel = viewModel,
                musicInfo = musicInfo,
                onEdit = { showEditDialog = true }
            )
        }
    }
    if (showEditDialog) {
        EditToeicDialog(
            musicInfo = musicInfo,
            viewModel = viewModel,
            onDismiss = { showEditDialog = false }
        )
    }
}

@Composable
private fun Menu(
    modifier: Modifier = Modifier,
    viewModel: MusicInfoViewModel,
    musicInfo: MusicInfo,
    onEdit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "",
                tint = Color(0xFF9C27B0)
            )
        }
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.onSurface),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onEdit()
                }
            ) {
                Text(
                    text = "編集",
                    fontSize = 24.sp,
                    color = Color.Gray,
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    showDialog = true
                }
            ) {
                Text(
                    text = "削除",
                    fontSize = 24.sp,
                    color = Color.Gray,
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("削除確認") },
            text = { Text("このデータを削除しますか？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteMusicValues(musicInfo)
                        showDialog = false
                    }
                ) {
                    Text("削除", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("キャンセル")
                }
            }
        )
    }
}

@Composable
private fun EditToeicDialog(
    musicInfo: MusicInfo,
    viewModel: MusicInfoViewModel,
    onDismiss: () -> Unit
) {
    var nameOfMusic by remember { mutableStateOf(musicInfo.nameOfMusic) }
    var nameOfArtist by remember { mutableStateOf(musicInfo.nameOfArtist) }
    var nameOfJenre by remember { mutableStateOf(musicInfo.nameOfJenre) }
    var nameOfStyle by remember { mutableStateOf(musicInfo.nameOfStyle) }
    var nameOfMemo by remember { mutableStateOf(musicInfo.nameOfMemo) }
    var levelOfRightHand by remember { mutableStateOf(musicInfo.levelOfRightHand.toString()) }
    var levelOfLeftHand by remember { mutableStateOf(musicInfo.levelOfLeftHand.toString()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("音楽データを編集") },
        text = {
            Column {
                OutlinedTextField(
                    value = nameOfMusic,
                    onValueChange = { nameOfMusic = it },
                    label = { Text("曲名") }
                )
                OutlinedTextField(
                    value = nameOfArtist,
                    onValueChange = { nameOfArtist = it },
                    label = { Text("作曲者名") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = nameOfJenre,
                    onValueChange = { nameOfJenre = it },
                    label = { Text("ジャンル") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = nameOfStyle,
                    onValueChange = { nameOfStyle = it },
                    label = { Text("演奏スタイル") }
                )
                OutlinedTextField(
                    value = nameOfMemo,
                    onValueChange = { nameOfMemo = it },
                    label = { Text("メモ") }
                )
                OutlinedTextField(
                    value = levelOfRightHand,
                    onValueChange = { levelOfRightHand = it },
                    label = { Text("右手の習熟度") }
                )
                OutlinedTextField(
                    value = levelOfLeftHand,
                    onValueChange = { levelOfLeftHand = it },
                    label = { Text("左手の習熟度") }
                )

            }
        },
        confirmButton = {
            Button (
                onClick = {
                    viewModel.updateMusicValues(
                        musicInfo.copy(
                            nameOfMusic = nameOfMusic,
                            nameOfArtist = nameOfArtist,
                            nameOfJenre = nameOfJenre,
                            nameOfStyle = nameOfStyle,
                            nameOfMemo = nameOfMemo,
                            levelOfRightHand = levelOfRightHand.toInt(),
                            levelOfLeftHand = levelOfLeftHand.toInt()
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("保存")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("キャンセル")
            }
        }
    )
}

@Composable
private fun NoRecordImageView(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.music_note),
        contentDescription = stringResource(id = R.string.description_of_the_image),
        modifier = modifier
            .size((dimensionResource(id = R.dimen.no_record_image_view)))
            .aspectRatio(1f)
    )
}

// 以下は「記録無し」のコード
@Preview(showBackground = true)
@Composable
private fun NoRecordImageViewPreview() {
    ProPianoCoverBookTheme {
        NoRecordImageView(modifier = Modifier)
    }
}

@Composable
private fun NoRecordText(modifier: Modifier = Modifier) {
    androidx.compose.material.Text(
        text = stringResource(id = R.string.no_record),
        fontWeight = FontWeight.Bold,
        fontSize = dimensionResource(
            id = R.dimen.no_record_text_font_size
        ).value.sp,
    )
}

@Preview(showBackground = true)
@Composable
private fun NoRecordTextPreview() {
    ProPianoCoverBookTheme {
        NoRecordText()
    }
}

@Composable
private fun NoRecordDescriptionText(modifier: Modifier = Modifier) {
    androidx.compose.material.Text(
        text = stringResource(id = R.string.no_record_description),
        fontWeight = FontWeight.Light,
        color = androidx.compose.ui.graphics.Color.LightGray
    )
}

@Preview(showBackground = true)
@Composable
private fun NoRecordDescriptionTextPreview() {
    ProPianoCoverBookTheme {
        NoRecordDescriptionText()
    }
}

// 以下は「記録あり」のコード
@Composable
private fun SearchScreen() {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16_dp))) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val modifier = Modifier
        .fillMaxWidth()
        .background(
            androidx.compose.ui.graphics.Color.Gray.copy(alpha = 0.1f),
            shape = RoundedCornerShape(
                dimensionResource(
                    id = R.dimen.search_bar_background_rounded_corner_shape
                )
            )
        )
        .padding(dimensionResource(id = R.dimen.space_8_dp))

    Box(modifier = modifier) {
        androidx.compose.material.TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { androidx.compose.material.Text(
                stringResource(id = R.string.search_by_name)
            ) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(
                        id = R.string.content_description_search_icon
                    )
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
