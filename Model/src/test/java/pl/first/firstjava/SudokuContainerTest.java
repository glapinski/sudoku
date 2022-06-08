package pl.first.firstjava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.first.firstjava.exception.BadContainerSizeException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SudokuContainerTest {

    private SudokuRow makeObjectWithValidList() {
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
    public void verifyValidTest() {
        SudokuRow testSudokuRow = makeObjectWithValidList();
        assertTrue(testSudokuRow.verify());
    }

    @Test
    public void verifyInvalidTest() {
        SudokuRow testSudokuRow = new SudokuRow(Arrays.asList(
                new SudokuField(2),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)));
        assertFalse(testSudokuRow.verify());
    }

    @Test
    public void invalidContainerSizeTest() {
        Assertions.assertThrows(BadContainerSizeException.class, () -> {
            SudokuRow testSudokuRow = new SudokuRow(Arrays.asList(
                    new SudokuField(1),
                    new SudokuField(3),
                    new SudokuField(4),
                    new SudokuField(5),
                    new SudokuField(6),
                    new SudokuField(7),
                    new SudokuField(8),
                    new SudokuField(9)));
        });
    }

    @Test
    public void toStringTest() {
        SudokuRow testSudokuRow = makeObjectWithValidList();
        assertNotNull(testSudokuRow.toString());
    }

    @Test
    public void equalsTest() {
        SudokuRow testSudokuRow = makeObjectWithValidList();
        SudokuRow testSudokuRow2 = null;
        assertFalse(testSudokuRow.equals(testSudokuRow2));
        testSudokuRow2 = testSudokuRow;
        assertTrue(testSudokuRow.equals(testSudokuRow2));
        SudokuField testSudokuField = new SudokuField();
        assertFalse(testSudokuRow.equals(testSudokuField));
        SudokuRow testSudokuRow3 = makeObjectWithValidList();
        assertTrue(testSudokuRow.equals(testSudokuRow3));
        SudokuRow testSudokuRow4 = new SudokuRow(Arrays.asList(
                new SudokuField(2),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)));
        assertFalse(testSudokuRow.equals(testSudokuRow4));
    }

    @Test
    public void hashCodeTest() {
        SudokuRow testSudokuRow = makeObjectWithValidList();
        SudokuRow testSudokuRow2 = makeObjectWithValidList();
        assertEquals(testSudokuRow.hashCode(), testSudokuRow2.hashCode());
        SudokuRow testSudokuRow3 = new SudokuRow(Arrays.asList(
                new SudokuField(2),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)));
        assertNotEquals(testSudokuRow.hashCode(), testSudokuRow3.hashCode());
    }
}