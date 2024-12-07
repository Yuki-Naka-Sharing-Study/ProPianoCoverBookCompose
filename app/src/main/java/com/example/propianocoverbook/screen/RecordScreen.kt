package com.example.propianocoverbook.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propianocoverbook.R
import com.example.propianocoverbook.data.MusicInfoViewModel
import com.example.propianocoverbook.ui.theme.ProPianoCoverBookTheme

@Composable
fun RecordScreen(viewModel: MusicInfoViewModel) {
//fun RecordScreen() {
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16_dp))
    ) {
        var textOfMusic by rememberSaveable { mutableStateOf("") }
        var textOfArtist by rememberSaveable { mutableStateOf("") }
        var textOfGenre by rememberSaveable { mutableStateOf("") }
        var textOfStyle by rememberSaveable { mutableStateOf("") }
        var textOfMemo by rememberSaveable { mutableStateOf("") }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_16_dp)))
        MusicOutlinedTextField(
            label = stringResource(id = R.string.music_name),
            placeholder = stringResource(id = R.string.placeholder_music),
            value = textOfMusic,
            onValueChange = { textOfMusic = it }
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
        MusicOutlinedTextField(
            label = stringResource(id = R.string.artist_name),
            placeholder = stringResource(id = R.string.placeholder_artist),
            value = textOfArtist,
            onValueChange = { textOfArtist = it }
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
        Row {
            Text(text = "ジャンル")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            DropdownMenuWithIcon(
                items = listOf("Classic", "Jazz", "Pop", "Rock", "Others"),
                value = textOfGenre,
                onValueChange = { textOfGenre = it }
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))

            Text(text = "演奏スタイル")
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
            DropdownMenuWithIcon(
                items = listOf("独奏", "伴奏", "弾き語り"),
                value = textOfStyle,
                onValueChange ={ textOfStyle = it }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
        MusicOutlinedTextField(
            label = stringResource(id = R.string.memo_name),
            placeholder = stringResource(id = R.string.placeholder_memo),
            value = textOfMemo,
            onValueChange = { textOfMemo = it }
        )

        val isButtonEnabled = textOfMusic.isNotBlank()
                && textOfArtist.isNotBlank()
                && textOfGenre.isNotBlank()
                && textOfStyle.isNotBlank()
                && textOfMemo.isNotBlank()

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
        ProgressSection(stringResource(id = R.string.right_hand)) { RightHandCircularProgressWithSeekBar() }
        ProgressSection(stringResource(id = R.string.left_hand)) { LeftHandCircularProgressWithSeekBar() }

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
                            textOfMemo
                        )
                    },
                    enabled = isButtonEnabled
                )
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
private fun MusicOutlinedTextField(label: String, placeholder: String, value: String, onValueChange: (String) -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
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

@Composable
private fun DropdownMenuWithIcon(items: List<String>, value: String, onValueChange: (String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

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
                    text = items[selectedIndex],
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
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedIndex = index
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
private fun CircularProgressWithSeekBar(progress: MutableState<Float>) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(dimensionResource(id = R.dimen.circular_progress_with_seek_bar_size))) {
        CircularProgressIndicator(
            color = Color(0xff8a2be2),
            strokeWidth = dimensionResource(id = R.dimen.circular_progress_indicator_stroke_width),
            progress = progress.value / 100,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.circular_progress_with_seek_bar_size))
                .padding(dimensionResource(id = R.dimen.space_8_dp))
        )
        Text(
            text = "${progress.value.toInt()}%",
            style = TextStyle(fontSize = dimensionResource(id = R.dimen.text_size_large).value.sp),
        )
    }
    Slider(
        colors = SliderDefaults.colors(
            activeTrackColor = Color(0xff8a2be2),
            inactiveTrackColor = Color.Gray,
            thumbColor = Color.Gray
        ),
        value = progress.value,
        onValueChange = { newValue -> progress.value = newValue },
        valueRange = 0f..100f,
        thumb = { Canvas(Modifier.size(8.dp)) { drawCircle(Color(0xff8a2be2)) } }
    )
}

@Composable
private fun RightHandCircularProgressWithSeekBar() {
    val rightHandProgress = rememberSaveable { mutableFloatStateOf(0f) }
    CircularProgressWithSeekBar(progress = rightHandProgress)
}

@Composable
private fun LeftHandCircularProgressWithSeekBar() {
    val leftHandProgress = rememberSaveable { mutableFloatStateOf(0f) }
    CircularProgressWithSeekBar(progress = leftHandProgress)
}

@Composable
private fun SaveButton(
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Blue),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(stringResource(id = R.string.record_button))
    }
}