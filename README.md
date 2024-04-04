# FindtheMoves

An app that helps you find all possible moves between a knight and an end point.

# Overview

Using a DFS algorithm the app calculates all the possible moves that the knight (green square) can reach the end point (red square) 
If moves are found then they are displayed in a list, otherwise a "No paths found" message appears.

# Main Screen

![Screenshot_20240404_112715_Find the Moves](https://github.com/Shadowvault/FindtheMoves/assets/102808624/35876d36-170d-44a8-91e8-b73d4d6e89ff)

* The chessboard size can be changed via the slider
* The number of allowed moves can change via the arrow buttons
* The switch control the type of square that can be placed (knight or end point)
* The "Reset" button resets the board
* The "Find paths" calculates the paths between the points in order to display a list of possible moves/solutions

# Demo 
 
![gif](https://github.com/Shadowvault/FindtheMoves/assets/102808624/13638acc-115b-4468-b19e-7d32b9677cf5)

# Clean Architecture

The project uses clean architecture, with usecases and single state

* Usecases: 
  1) Chessmoves usecase: This usecase converts the list of lists of possible paths to chess move notation. 
  2) Findpaths usecase: This usecase using DFS, finds all possible paths from knight to end point.
 
* Events: 

```
sealed class MainScreenEvent {
    data class SliderValueChanged(val value: Float) : MainScreenEvent()
    data class MaxNumberOfMovesChanged(val moves: Int) : MainScreenEvent()
    data object SwitchValueChanged : MainScreenEvent()
    data class TilePressed(val pair: Pair<Int, Int>) : MainScreenEvent()
    data object ButtonPress : MainScreenEvent()
    data object ResetButtonPress : MainScreenEvent()
}
```

The above events are send from the UI to the ViewModel. When the VM receives it performs the need action 

* The state is manipulated through copying and uses with a mutable state flow. It contains the following fields with default initial values:

```
    val switchText: UIText = UIText.DynamicString(""),
    val buttonText: UIText = UIText.DynamicString(""),
    val resetButtonText: UIText = UIText.DynamicString(""),
    val foundPathsText: UIText = UIText.DynamicString(""),
    val redTile: Pair<Int, Int> = -1 to -1,
    val greenTile: Pair<Int, Int> = -1 to -1,
    val sliderValue: Float = 6f,
    val maxDepth: Int = 3,
    val switchValue: Boolean = true,
    val chessboard: Array<Array<Long>> = Array(6) { i ->
        Array(6) { j ->
            0x00000000
        }
    },
    val paths: List<List<Pair<Int, Int>>> = emptyList()
```

# DI

The project uses hilt for DI. The two main modules are:
  1) App module that contains providers for the database and the usecases
  2) Dispatch module that contains providers for different coroutine dispatches.

# Caching

Room database is used for storing the last moves that were calculated, plus most UI elements like the chessboard, slider, points and number of moves

# Testing

A fake repository modeling the behavior of Room with a list is used. Dispatchers for coroutines is switched for the test dispatcher. There are tests for initial state, button and switches presses, moves calculactions and display.

