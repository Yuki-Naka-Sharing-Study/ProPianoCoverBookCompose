package com.example.propianocoverbook.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.propianocoverbook.R
import com.example.propianocoverbook.ui.theme.ProPianoCoverBookTheme

@Composable
fun ConfirmScreen() {
    // ComposeのUIを定義

    // 以下は「記録無し」のコード
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NoRecordImageView()
            NoRecordText()
            NoRecordDescriptionText()
        }
    }

    // 以下は「記録あり」のコード
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SearchScreen()
//            MusicInfoLazyColumn()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmScreenPreview() {
    ProPianoCoverBookTheme {
        ConfirmScreen()
    }
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

@Composable
private fun SearchScreen() {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16_dp))) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        // 検索結果を表示するためのUIをここに追加。
    }
}

//@Composable
//private fun MusicInfoLazyColumn() {
//    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//        items(4) { index ->
//
//            // 以下が完成のイメージのコード
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                SongNameTextView(songName = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                SongNameUserEnteredTextView(songName = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                ArtistNameTextView(artistName = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                ArtistNameUserEnteredTextView(artistName = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                GenreNameTextView(genreName = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                GenreNameUserEnteredTextView(genreName = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                StyleNameTextView(styleName = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                StyleNameUserEnteredTextView(styleName = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                MemoTextView(memoName = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                MemoUserEnteredTextView(memoName = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                RightHandTextView(rightHand = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                RightHandUserEnteredTextView(rightHand = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_24_dp)))
//                LeftHandTextView(leftHand = "")
//                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.space_16_dp)))
//                LeftHandUserEnteredTextView(leftHand = "")
//            }
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_8_dp)))
//
//            Divider(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                color = androidx.compose.ui.graphics.Color.LightGray,
//                thickness = 1.dp,
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MusicInfoLazyColumnPreview() {
//    ProPianoCoverBookTheme {
//        MusicInfoLazyColumn()
//    }
//}
//
//@Composable
//private fun SongNameTextView(songName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.music_name),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun SongNameTextViewPreview() {
//    ProPianoCoverBookTheme {
//        SongNameTextView(stringResource(id = R.string.music_name))
//    }
//}
//
//@Composable
//private fun SongNameUserEnteredTextView(songName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.placeholder_music),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun SongNameUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        SongNameUserEnteredTextView(stringResource(id = R.string.placeholder_music))
//    }
//}
//
//@Composable
//private fun ArtistNameTextView(artistName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.artist_name),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ArtistNameTextViewPreview() {
//    ProPianoCoverBookTheme {
//        ArtistNameTextView(stringResource(id = R.string.artist_name))
//    }
//}
//
//@Composable
//private fun ArtistNameUserEnteredTextView(artistName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.placeholder_artist),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ArtistNameUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        ArtistNameUserEnteredTextView(stringResource(id = R.string.placeholder_artist))
//    }
//}
//
//@Composable
//private fun GenreNameTextView(genreName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.genre_name),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun GenreNameTextViewPreview() {
//    ProPianoCoverBookTheme {
//        GenreNameTextView(stringResource(id = R.string.genre_name))
//    }
//}
//
//@Composable
//private fun GenreNameUserEnteredTextView(genreName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.genre_user_input),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun GenreNameUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        GenreNameUserEnteredTextView(stringResource(id = R.string.genre_user_input))
//    }
//}
//
//@Composable
//private fun StyleNameTextView(styleName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.style_name),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun StyleNameTextViewPreview() {
//    ProPianoCoverBookTheme {
//        StyleNameTextView(stringResource(id = R.string.style_name))
//    }
//}
//
//@Composable
//private fun StyleNameUserEnteredTextView(styleName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.style_user_input),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun StyleNameUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        StyleNameUserEnteredTextView(stringResource(id = R.string.style_user_input))
//    }
//}
//
//@Composable
//private fun MemoTextView(memoName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.memo_name),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MemoTextViewPreview() {
//    ProPianoCoverBookTheme {
//        MemoTextView(stringResource(id = R.string.memo_name))
//    }
//}
//
//@Composable
//private fun MemoUserEnteredTextView(memoName: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.placeholder_memo),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MemoUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        MemoUserEnteredTextView(stringResource(id = R.string.placeholder_memo))
//    }
//}
//
//@Composable
//private fun RightHandTextView(rightHand: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.right_hand),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun RightHandTextViewPreview() {
//    ProPianoCoverBookTheme {
//        RightHandTextView(stringResource(id = R.string.right_hand))
//    }
//}
//
//@Composable
//private fun RightHandUserEnteredTextView(rightHand: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "100",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun RightHandUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        RightHandUserEnteredTextView("100")
//    }
//}
//
//@Composable
//private fun LeftHandTextView(leftHand: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(id = R.string.left_hand),
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun LeftHandTextViewPreview() {
//    ProPianoCoverBookTheme {
//        LeftHandTextView(stringResource(id = R.string.left_hand))
//    }
//}
//
//@Composable
//private fun LeftHandUserEnteredTextView(leftHand: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "90",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun LeftHandUserEnteredTextViewPreview() {
//    ProPianoCoverBookTheme {
//        LeftHandUserEnteredTextView("90")
//    }
//}