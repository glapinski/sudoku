package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {

    private SudokuColumn makeObject() {
        return new SudokuColumn(Arrays.asList(
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
        SudokuColumn sudokuColumn = makeObject();
        SudokuColumn sudokuColumn1 = (SudokuColumn) sudokuColumn.clone();

        assertTrue(sudokuColumn.equals(sudokuColumn1)
                && sudokuColumn1.equals(sudokuColumn));
    }

}