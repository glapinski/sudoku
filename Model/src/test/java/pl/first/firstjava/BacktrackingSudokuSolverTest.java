package pl.first.firstjava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    private SudokuBoard testSudokuBoard;
    private BacktrackingSudokuSolver testBacktrackingSudokuSolver;

    public static final int SIZE = 9;

    @BeforeEach
    public void setUp() {
        testBacktrackingSudokuSolver = new BacktrackingSudokuSolver();
        testSudokuBoard = new SudokuBoard(testBacktrackingSudokuSolver);
    }

    @Test
    public void checkTwoFillBoardCallingsTest() {
        boolean differentValues = false;
        SudokuBoard testSudokuBoard1 = new SudokuBoard(testBacktrackingSudokuSolver);
        testSudokuBoard.solveGame();
        testSudokuBoard1.solveGame();
        loop: for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (testSudokuBoard.get(i, j) != testSudokuBoard1.get(i, j)) {
                    differentValues = true;
                    break loop;
                }
            }
        }
        assertTrue(differentValues);
    }

    private boolean checkRows(SudokuBoard board) {
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                for (int j2 = j + 1; j2 < SudokuBoard.SIZE; j2++) {
                    if (board.get(i, j) == board.get(i, j2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkColumns(SudokuBoard board) {
        for (int j = 0; j < SudokuBoard.SIZE; j++) {
            for (int i = 0; i < SudokuBoard.SIZE; i++) {
                for (int i2 = i + 1; i2 < SudokuBoard.SIZE; i2++) {
                    if (board.get(i, j) == board.get(i2, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkSmallSquares(SudokuBoard board) {
        for (int I = 0; I < 3; I++) {
            for (int J = 0; J < 3; J++) {
                //mały kwadrat (I, J)
                for (int checked = 0; checked < 9; checked++) {
                    for (int compared = checked + 1; compared < 9; compared++) {
                        if (board.get(I * 3 + (checked / 3), J * 3 + (checked % 3)) ==
                                board.get(I * 3 + (compared / 3), J * 3 + (compared % 3))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    @Test
    public void solveTest() {
        testSudokuBoard.solveGame();
        assertTrue(checkRows(testSudokuBoard));
        assertTrue(checkColumns(testSudokuBoard));
        assertTrue(checkSmallSquares(testSudokuBoard));
    }

}