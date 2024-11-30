package com.example.propianocoverbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.propianocoverbook.ui.theme.ProPianoCoverBookTheme

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.space_16)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.space_16)
            )
        )

        SettingTextView()

        Spacer(
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.space_16)
            )
        )

        AboutThisAppLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.space_16))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.space_16)))
                .background(color = androidx.compose.ui.graphics.Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    ProPianoCoverBookTheme {
        SettingScreen()
    }
}

@Composable
private fun SettingTextView() {
    Text(
        stringResource(id = R.string.setting_text_view),
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingTextViewPreview() {
    ProPianoCoverBookTheme {
        SettingTextView()
    }
}

@Composable
private fun AboutThisAppLazyColumn(modifier: Modifier) {
    val itemList = listOf(
        stringResource(id = R.string.about_this_app),
        stringResource(id = R.string.contact),
        stringResource(id = R.string.review),
        stringResource(id = R.string.share_this_app),
        stringResource(id = R.string.version),
    )

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.space_16))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.space_16)))
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
    ) {
        items(itemList.size) { index ->
            val item = itemList[index]
            val action = when (index) {
                0 -> { { /* このアプリについて */ AboutThisAppScreen() } }
                1 -> { { /* お問い合わせ */ ContactScreen() } }
                2 -> { { /* レビューする */ reviewScreen() } }
                3 -> { { /* このアプリを共有する */ shareAppScreen() } }
                else -> { { /* 何もしない */ } }
            }
            BasicText(
                text = item,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.space_16)),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.space_16).value.sp,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
            )
            if (index < itemList.size - 1) {
                Divider(color = androidx.compose.ui.graphics.Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutThisAppLazyColumnPreview() {
    ProPianoCoverBookTheme {
        AboutThisAppLazyColumn(modifier = Modifier)
    }
}

fun AboutThisAppScreen() {

}

fun ContactScreen() {

}

private fun reviewScreen() {

}

private fun shareAppScreen() {
//private fun shareAppScreen(context: Context) {
//    val shareIntent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(Intent.EXTRA_TEXT, "このアプリをチェックしてください！ [アプリのURL]")
//        type = "text/plain"
//    }
//    context.startActivity(Intent.createChooser(shareIntent, "共有する"))
}