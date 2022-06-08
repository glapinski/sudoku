package pl.first.firstjava;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard implements Serializable, Cloneable {
    public static final int SIZE = 9;
    private final SudokuSolver sudokuSolver;
    private List<List<SudokuField>> board;

    public SudokuBoard(SudokuSolver sudokuSolver1) {
        //PRZYPISYWANIE DO REFERENCJI PIERSZEGO WYMIARU MACIERZY
        board = Arrays.asList(new List[SIZE]);
        sudokuSolver = sudokuSolver1;
        //PRZYPISYWANIE DRUGIEGO WYMIARU MACIERZY
        for (int i = 0; i < SIZE; i++) {
            board.set(i, Arrays.asList(new SudokuField[SIZE]));
        }
        //ZMIANA ZAWARTOSCI LISTY Z NULL NA OBIEKTY SudokuField
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board.get(i).set(j, new SudokuField());
            }
        }
    }

    public int rng() {
        return ThreadLocalRandom.current().nextInt(0, 9);
    }

    public int get(int x, int y) {
        return board.get(x).get(y).getFieldValue();
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value < 10) {
            board.get(x).get(y).setFieldValue(value);
        }
    }

    public SudokuRow getRow(int y) {
        List<SudokuField> elements = Arrays.asList(new SudokuField[SudokuContainer.SIZE]);
        for (int i = 0; i < 9; i++) {
            elements.set(i, board.get(y).get(i));
        }
        return new SudokuRow(elements);
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> elements = Arrays.asList(new SudokuField[SudokuContainer.SIZE]);
        for (int i = 0; i < 9; i++) {
            elements.set(i, board.get(i).get(x));
        }

        return new SudokuColumn(elements);
    }

    public SudokuBox getBox(int x, int y) {
        List<SudokuField> elements = Arrays.asList(new SudokuField[SudokuContainer.SIZE]);
        int index = 0;
        int startingRow = x - x % 3;
        int startingColumn = y - y % 3;
        for (int i = 0; i < SudokuBox.BOX_SIZE; i++) {
            for (int j = 0; j < SudokuBox.BOX_SIZE; j++) {
                elements.set(index++, board.get(startingRow + i).get(startingColumn + j));
            }
        }

        return new SudokuBox(elements);
    }

    public boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!getRow(i).verify() || !getColumn(j).verify() || !getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        int[][] tab = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[i][j] = board.get(i).get(j).getFieldValue();
            }
        }
        return tab;
    }

    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                set(i, j, 0);
            }
        }
    }

    public void changeBoard(Difficulty difficulty) {
        if (difficulty.equals(Difficulty.Easy)) {
            int i = 30;
            while (i != 0) {
                int a;
                int b;
                a = rng();
                b = rng();
                    if (get(a, b) != 0) {
                        set(a, b, 0);
                        i--;
                    }
            }
        } else if (difficulty.equals(Difficulty.Medium)) {
            int i = 45;
            while (i != 0) {
                int a;
                int b;
                a = rng();
                b = rng();
                if (get(a, b) != 0) {
                    set(a, b, 0);
                    i--;
                }
            }
        } else {
            int i = 59;
            while (i != 0) {
                int a;
                int b;
                a = rng();
                b = rng();
                if (get(a, b) != 0) {
                    set(a, b, 0);
                    i--;
                }
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public boolean check(int row, int column, int number, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.get(row, i) == number) {
                return false;
            }
        }
        //W KOLUMNE//
        for (int i = 0; i < 9; i++) {
            if (board.get(i, column) == number) {
                return false;
            }
        }
        int sqrt = (int) Math.sqrt(9);
        int boxRowStart = row - row % sqrt;
        int boxColStart = column - column % sqrt;
        //W KWADRAT//
        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++) {
                if (board.get(r, d) == number) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("board", board).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SudokuBoard rhs = (SudokuBoard) obj;
        return new EqualsBuilder()
                .append(board, rhs.board)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board)
                .toHashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sudokuBoard.set(i, j, get(i, j));
            }
        }

        return sudokuBoard;
    }

    public SudokuField getField(int x, int y) {
        return board.get(x).get(y);
    }
}
