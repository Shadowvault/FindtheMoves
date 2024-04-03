package com.paris.findthemoves

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.paris.findthemoves.presentation.MainScreen
import com.paris.findthemoves.presentation.MainScreenViewModel
import com.paris.findthemoves.ui.theme.FindTheMovesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindTheMovesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                    val mainScreenState by mainScreenViewModel.mainScreenState.collectAsState()

                    MainScreen(
                        state = mainScreenState,
                        onEvent = mainScreenViewModel::onEvent
                    )
                }
            }
        }
    }
}
