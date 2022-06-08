package pl.first.firstjava;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class SudokuBoardTest {
    private SudokuBoard testSudokuBoard;

    @BeforeEach
    public void setUp() {
        BacktrackingSudokuSolver testBacktrackingSudokuSolver = new BacktrackingSudokuSolver();
        testSudokuBoard = new SudokuBoard(testBacktrackingSudokuSolver);
    }

    @Test
    void getTest() {
        assertEquals(0, testSudokuBoard.get(0, 0));
    }

    @Test
    void setTest() {
        testSudokuBoard.set(0, 0, 3);
        assertEquals(3, testSudokuBoard.get(0, 0));
        testSudokuBoard.set(0,0,13);
        assertEquals(3, testSudokuBoard.get(0,0));
        testSudokuBoard.set(0,0,-3);
        assertEquals(3, testSudokuBoard.get(0,0));
    }

    @Test
    public void getRowTest() {
        assertNotNull(testSudokuBoard.getRow(3));
    }

    @Test
    public void getColumnTest() {
        assertNotNull(testSudokuBoard.getColumn(6));
    }

    @Test
    public void getBoxTest() {
        assertNotNull(testSudokuBoard.getBox(6, 7));
    }

    @Test
    public void checkBoardTest() {
        assertFalse(testSudokuBoard.checkBoard());
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            testSudokuBoard.set(0, i, i + 1);
        }
        for (int j = 0; j < SudokuBoard.SIZE; j++) {
            testSudokuBoard.set(j, 0, j + 1);
        }
        assertFalse(testSudokuBoard.checkBoard());
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            testSudokuBoard.set(0, i, 0);
        }
        for (int j = 0; j < SudokuBoard.SIZE; j++) {
            testSudokuBoard.set(j, 0, 0);
        }
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            testSudokuBoard.set(0, i, i + 1);
        }
        assertFalse(testSudokuBoard.checkBoard());
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            testSudokuBoard.set(0, i, 0);
        }
        for (int j = 0; j < SudokuBoard.SIZE; j++) {
            testSudokuBoard.set(j, 0, j + 1);
        }
        assertFalse(testSudokuBoard.checkBoard());
        for (int j = 0; j < SudokuBoard.SIZE; j++) {
            testSudokuBoard.set(j, 0, 0);
        }
        int num = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testSudokuBoard.set(i, j, num);
                num++;
            }
        }
        assertFalse(testSudokuBoard.checkBoard());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testSudokuBoard.set(i, j, 0);
            }
        }
        testSudokuBoard.solveGame();
        assertTrue(testSudokuBoard.checkBoard());
    }

    @Test
    public void checkTest() {
        assertTrue(testSudokuBoard.check(0, 0, 1, testSudokuBoard));
        assertFalse(testSudokuBoard.check(0, 0, 0, testSudokuBoard));
        testSudokuBoard.set(2,0,1);
        assertFalse(testSudokuBoard.check(0, 0, 1, testSudokuBoard));
        testSudokuBoard.set(2,0,0);
        testSudokuBoard.set(1,1,1);
        assertFalse(testSudokuBoard.check(0, 0, 1, testSudokuBoard));
    }

    @Test
    public void toStringTest() {
        assertNotNull(testSudokuBoard.toString());
    }

    @Test
    public void equalsTest() {
        SudokuField testSudokuField = new SudokuField();
        assertFalse(testSudokuBoard.equals(testSudokuField));
        SudokuBoard testSudokuBoard2 = null;
        assertFalse(testSudokuBoard.equals(testSudokuBoard2));
        testSudokuBoard2 = testSudokuBoard;
        assertTrue(testSudokuBoard.equals(testSudokuBoard2));
        BacktrackingSudokuSolver testBacktrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard testSudokuBoard3 = new SudokuBoard(testBacktrackingSudokuSolver);
        assertTrue(testSudokuBoard.equals(testSudokuBoard3));
        testSudokuBoard3.set(0, 0, 4);
        assertFalse(testSudokuBoard.equals(testSudokuBoard3));
    }

    @Test
    public void hashCodeTest() {
        BacktrackingSudokuSolver testBacktrackingSudokuSolver2 = new BacktrackingSudokuSolver();
        SudokuBoard testSudokuBoard2 = new SudokuBoard(testBacktrackingSudokuSolver2);
        assertEquals(testSudokuBoard.hashCode(), testSudokuBoard2.hashCode());
        testSudokuBoard2.set(0, 0, 4);
        assertNotEquals(testSudokuBoard.hashCode(), testSudokuBoard2.hashCode());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        solver.solve(testSudokuBoard);
        SudokuBoard testSudokuBoard1 = (SudokuBoard) testSudokuBoard.clone();

        assertTrue(testSudokuBoard.equals(testSudokuBoard1)
                && testSudokuBoard1.equals(testSudokuBoard));
    }

    @Test
    public void getBoardTest() {
        int[][] testTab = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(testTab[i][j], testSudokuBoard.getBoard()[i][j]);
            }
        }
    }

    @Test
    public void clearBoardTest() {
        testSudokuBoard.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(0, testSudokuBoard.get(i, j));
            }
        }
        testSudokuBoard.clearBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(0, testSudokuBoard.get(i, j));
            }
        }
    }

    @Test
    public void changeBoardTest() {
        Difficulty easyDifficulty = Difficulty.Easy;
        Difficulty mediumDifficulty = Difficulty.Medium;
        Difficulty hardDifficulty = Difficulty.Hard;
        testSudokuBoard.solveGame();
        testSudokuBoard.changeBoard(easyDifficulty);
        int blanks = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudokuBoard.get(i, j) == 0) {
                    blanks++;
                }
            }
        }
        assertEquals(30, blanks);
        testSudokuBoard.solveGame();
        testSudokuBoard.changeBoard(mediumDifficulty);
        blanks = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudokuBoard.get(i, j) == 0) {
                    blanks++;
                }
            }
        }
        assertEquals(45, blanks);
        testSudokuBoard.solveGame();
        testSudokuBoard.changeBoard(hardDifficulty);
        blanks = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testSudokuBoard.get(i, j) == 0) {
                    blanks++;
                }
            }
        }
        assertEquals(59, blanks);
    }
}
