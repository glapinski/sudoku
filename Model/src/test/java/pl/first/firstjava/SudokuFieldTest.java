package pl.first.firstjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    private SudokuField testSudokuField;

    @BeforeEach
    void setUp() {
        testSudokuField = new SudokuField();
    }

    @Test
    public void getFieldValueTest() {
        assertEquals(testSudokuField.getFieldValue(), 0);
    }

    @Test
    public void setFieldValueTest() {
        testSudokuField.setFieldValue(5);
        assertEquals(testSudokuField.getFieldValue(), 5);
        testSudokuField.setFieldValue(10);
        assertEquals(testSudokuField.getFieldValue(), 5);
        testSudokuField.setFieldValue(-3);
        assertEquals(testSudokuField.getFieldValue(), 5);
    }

    @Test
    public void setEditableTest() {
        testSudokuField.setEditable(true);
        assertTrue(testSudokuField.isEditable());
    }

    @Test
    public void toStringTest() {
        assertNotNull(testSudokuField.toString());
    }

    @Test
    public void equalsTest() {
        SudokuField testSudokuField2 = null;
        assertFalse(testSudokuField.equals(testSudokuField2));
        testSudokuField2 = testSudokuField;
        assertTrue(testSudokuField.equals(testSudokuField2));
        BacktrackingSudokuSolver testBacktrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard testSudokuBoard = new SudokuBoard(testBacktrackingSudokuSolver);
        assertFalse(testSudokuField.equals(testSudokuBoard));
        SudokuField testSudokuField3 = new SudokuField(1);
        SudokuField testSudokuField4 = new SudokuField(1);
        assertTrue(testSudokuField3.equals(testSudokuField4));
        SudokuField testSudokuField5 = new SudokuField(5);
        assertFalse(testSudokuField3.equals(testSudokuField5));
    }

    @Test
    public void hashCodeTest() {
        SudokuField testSudokuField2 = new SudokuField(1);
        SudokuField testSudokuField3 = new SudokuField(1);
        assertEquals(testSudokuField2.hashCode(), testSudokuField3.hashCode());
        testSudokuField3.setFieldValue(7);
        assertNotEquals(testSudokuField2.hashCode(), testSudokuField3.hashCode());
    }

    @Test
    public void compareToTest() {
        SudokuField testSudokuField1 = new SudokuField();


        testSudokuField.setFieldValue(4);
        testSudokuField1.setFieldValue(4);
        assertEquals(testSudokuField.compareTo(testSudokuField1), 0);

        testSudokuField.setFieldValue(8);
        testSudokuField1.setFieldValue(4);
        assertEquals(testSudokuField.compareTo(testSudokuField1), 1);

        testSudokuField.setFieldValue(4);
        testSudokuField1.setFieldValue(8);
        assertEquals(testSudokuField.compareTo(testSudokuField1), -1);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        testSudokuField = new SudokuField(8);
        SudokuField testSudokuField1 = new SudokuField();
        testSudokuField1 = (SudokuField) testSudokuField.clone();

        assertTrue(testSudokuField.equals(testSudokuField1)
                && testSudokuField1.equals(testSudokuField));
    }
}