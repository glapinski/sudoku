package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {

    private SudokuBox makeObject() {
        return new SudokuBox(Arrays.asList(
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
        SudokuBox sudokuBox = makeObject();
        SudokuBox sudokuBox1 = (SudokuBox) sudokuBox.clone();

        assertTrue(sudokuBox.equals(sudokuBox1)
             && sudokuBox1.equals(sudokuBox));
    }
}