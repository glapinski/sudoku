package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {

    private SudokuRow makeObject() {
        return new SudokuRow(Arrays.asList(
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)));
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuRow sudokuRow = makeObject();
        SudokuRow sudokuRow1 = (SudokuRow) sudokuRow.clone();

        assertTrue(sudokuRow.equals(sudokuRow1)
                && sudokuRow1.equals(sudokuRow));
    }
}