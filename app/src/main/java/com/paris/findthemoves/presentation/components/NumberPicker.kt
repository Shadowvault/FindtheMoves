package com.paris.findthemoves.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    minValue: Int = 1,
    maxValue: Int = 16,
    defaultValue: Int,
    onValueChange: (Int) -> Unit
) {
    var selectedNumber = defaultValue.coerceIn(minValue, maxValue)

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (selectedNumber > minValue) {
                        selectedNumber--
                        onValueChange(selectedNumber)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrement"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = selectedNumber.toString()
            )

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = {
                    if (selectedNumber < maxValue) {
                        selectedNumber++
                        onValueChange(selectedNumber)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increment"
                )
            }
        }
    }
}