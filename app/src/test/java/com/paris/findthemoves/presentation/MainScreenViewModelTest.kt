package com.paris.findthemoves.presentation

import com.google.common.truth.Truth.assertThat
import com.paris.findthemoves.MainDispatcherRule
import com.paris.findthemoves.R
import com.paris.findthemoves.domain.usecases.chessmoves.PathsToChessMoves
import com.paris.findthemoves.domain.usecases.chessmoves.PathsToChessMovesUseCaseImpl
import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFS
import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFSUseCaseImpl
import com.paris.findthemoves.fakes.FakeMainScreenStateRepositoryImpl
import com.paris.findthemoves.presentation.utils.UIText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val testDispatcher = mainDispatcherRule.testDispatcher

    private val dfs = KnightPathsDFS(testDispatcher)
    private val findPathsUseCase = KnightPathsDFSUseCaseImpl(dfs)

    private val pathsToChessMoves = PathsToChessMoves(testDispatcher)
    private val convertPathsToChessMoves = PathsToChessMovesUseCaseImpl(pathsToChessMoves)

    private val fakeMainScreenStateRepository = FakeMainScreenStateRepositoryImpl()

    private lateinit var mainScreenViewModel: MainScreenViewModel

    @Test
    fun `Test init ViewModel`() = runTest {
        fakeMainScreenStateRepository.insertScreenState(MainScreenState())

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        assertThat(mainScreenViewModel.mainScreenState.value).isEqualTo(
            MainScreenState(
                switchText = UIText.StringResource(R.string.start_point),
                buttonText = UIText.StringResource(R.string.button),
                resetButtonText = UIText.StringResource(R.string.reset_button)
            )
        )
    }

    @Test
    fun `Test switch press`() = runTest {
        fakeMainScreenStateRepository.insertScreenState(MainScreenState())

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.SwitchValueChanged)

        assertThat(mainScreenViewModel.mainScreenState.value.switchText).isEqualTo(
            UIText.StringResource(R.string.end_point)
        )
    }

    @Test
    fun `Test set green tile`() = runTest {

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.TilePressed(1 to 2))

        assertThat(mainScreenViewModel.mainScreenState.value.greenTile).isEqualTo(
            1 to 2
        )
    }

    @Test
    fun `Test set red tile`() = runTest {

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.SwitchValueChanged)
        mainScreenViewModel.onEvent(MainScreenEvent.TilePressed(1 to 2))

        assertThat(mainScreenViewModel.mainScreenState.value.redTile).isEqualTo(
            1 to 2
        )
    }

    @Test
    fun `Test change chessboard size`() = runTest {

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.SliderValueChanged(8f))

        assertThat(mainScreenViewModel.mainScreenState.value.chessboard.size).isEqualTo(8)
    }

    @Test
    fun `Test button press returns correct paths`() = runTest {
        fakeMainScreenStateRepository.insertScreenState(
            MainScreenState(
                redTile = 4 to 2,
                greenTile = 5 to 0
            )
        )

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.ButtonPress)

        assertThat(mainScreenViewModel.mainScreenState.value.paths).isEqualTo(
            listOf(listOf(5 to 0, 3 to 1, 2 to 3, 4 to 2), listOf(5 to 0, 4 to 2))
        )
    }

    @Test
    fun `Test button press returns correct paths with smaller depth`() = runTest {
        fakeMainScreenStateRepository.insertScreenState(
            MainScreenState(
                redTile = 4 to 2,
                greenTile = 5 to 0
            )
        )

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.MaxNumberOfMovesChanged(2))
        mainScreenViewModel.onEvent(MainScreenEvent.ButtonPress)

        assertThat(mainScreenViewModel.mainScreenState.value.paths).isEqualTo(
            listOf(listOf(5 to 0, 4 to 2))
        )
    }

    @Test
    fun `Test button press returns correct paths, no paths found`() = runTest {
        fakeMainScreenStateRepository.insertScreenState(
            MainScreenState(
                redTile = 3 to 2,
                greenTile = 5 to 0
            )
        )

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.ButtonPress)

        assertThat(mainScreenViewModel.mainScreenState.value.paths).isEqualTo(
            emptyList<List<Pair<Int, Int>>>()
        )
    }

    @Test
    fun `Test correct chess moves conversion`() = runTest {
        fakeMainScreenStateRepository.insertScreenState(
            MainScreenState(
                redTile = 4 to 2,
                greenTile = 5 to 0
            )
        )

        mainScreenViewModel = MainScreenViewModel(
            knightPathsUseCase = findPathsUseCase,
            mainScreenStateRepository = fakeMainScreenStateRepository,
            pathsToChessMovesUseCase = convertPathsToChessMoves,
            dispatcher = testDispatcher
        )

        mainScreenViewModel.onEvent(MainScreenEvent.ButtonPress)

        assertThat(mainScreenViewModel.mainScreenState.value.foundPathsText).isEqualTo(
            UIText.DynamicString("A1 to B3 to D4 to C2\nA1 to C2")
        )
    }
}