package com.example.propianocoverbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.room.Room
import com.example.propianocoverbook.data.MusicInfoDao
import com.example.propianocoverbook.data.MusicInfoDatabase
import com.example.propianocoverbook.data.MusicInfoRepository
import com.example.propianocoverbook.data.MusicInfoViewModel
import com.example.propianocoverbook.data.MusicInfoViewModelFactory
import com.example.propianocoverbook.screen.ConfirmScreen
import com.example.propianocoverbook.screen.RecordScreen
import com.example.propianocoverbook.screen.SettingScreen

class MainActivity : ComponentActivity() {
    private val repository: MusicInfoRepository = MusicInfoRepository()
    private lateinit var musicInfoDao: MusicInfoDao
    private val viewModel: MusicInfoViewModel by viewModels {
        MusicInfoViewModelFactory(repository, musicInfoDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ライトモードを強制的に設定
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        musicInfoDao = Room.databaseBuilder(
            application,
            MusicInfoDatabase::class.java, "music_info_database"
        ).build().musicInfoDao()
        setContent {
            MyApp(viewModel = viewModel)
        }
    }
}

@Composable
private fun MyApp(viewModel: MusicInfoViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = stringResource(id = R.string.confirm),
            Modifier.padding(innerPadding)
        ) {
            // 以下、BottomNavigation
            composable("記録確認") { ConfirmScreen() }
//            composable("record") { RecordScreen(viewModel = viewModel) }
            composable("記録する") { RecordScreen() }
            composable("設定") { SettingScreen() }

            // 以下、設定画面からの画面遷移
//            composable("about") { AboutThisAppScreen() }
//            composable("contact") { ContactScreen() }
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    BottomNavigation(
        backgroundColor = Color(0xff7b68ee),
        contentColor = Color(0xffffffff)
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Check, contentDescription = stringResource(id = R.string.confirm)) },
            label = { Text(stringResource(id = R.string.confirm)) },
            selected = currentDestination?.route == stringResource(id = R.string.confirm),
            onClick = {
                navController.navigate("記録確認") {
//                    popUpTo("confirm") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            selectedContentColor = Color(0xffffffff),
            unselectedContentColor = Color(0xff0000ff)
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Edit, contentDescription = stringResource(id = R.string.record)) },
            label = { Text(stringResource(id = R.string.record)) },
            selected = currentDestination?.route == stringResource(id = R.string.record),
            onClick = {
                navController.navigate("記録する") {
//                    popUpTo("record") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            selectedContentColor = Color(0xffffffff),
            unselectedContentColor = Color(0xff0000ff)
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = stringResource(id = R.string.setting)) },
            label = { Text(stringResource(id = R.string.setting)) },
            selected = currentDestination?.route == stringResource(id = R.string.setting),
            onClick = {
                navController.navigate("設定") {
//                    popUpTo("setting") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            selectedContentColor = Color(0xffffffff),
            unselectedContentColor = Color(0xff0000ff)
        )
    }
}