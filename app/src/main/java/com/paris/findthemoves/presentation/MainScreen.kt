package com.paris.findthemoves.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris.findthemoves.R
import com.paris.findthemoves.presentation.components.Chessboard
import com.paris.findthemoves.presentation.components.NumberPicker
import com.paris.findthemoves.presentation.components.PossiblePaths

@Composable
fun MainScreen(state: MainScreenState, onEvent: (MainScreenEvent) -> Unit) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Chessboard(
            size = state.sliderValue.toInt(),
            tileColor = { i, j ->
                state.chessboard[i][j]
            },
            tilePress = { i, j ->
                onEvent(MainScreenEvent.TilePressed(Pair(i, j)))
            }
        )
        Slider(
            value = state.sliderValue,
            onValueChange = { newSize ->
                onEvent(MainScreenEvent.SliderValueChanged(newSize))
            },
            valueRange = 6f..16f,
            steps = 4,
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Moves")
            NumberPicker(
                defaultValue = state.maxDepth,
                onValueChange = { newMax ->
                    onEvent(MainScreenEvent.MaxNumberOfMovesChanged(newMax))
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = state.switchText.asString())
            Switch(checked = state.switchValue,
                onCheckedChange = {
                    onEvent(MainScreenEvent.SwitchValueChanged)
                })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button({ onEvent(MainScreenEvent.ButtonPress) }) {
                Text(text = state.buttonText.asString())
            }
            Button({ onEvent(MainScreenEvent.ResetButtonPress) }) {
                Text(text = state.resetButtonText.asString())
            }
        }
        PossiblePaths(paths = state.foundPathsText)
    }

}