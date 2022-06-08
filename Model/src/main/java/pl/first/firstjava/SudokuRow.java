package pl.first.firstjava;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends SudokuContainer {
    public SudokuRow(final List<SudokuField> elements) {
        super(elements);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> elements = new ArrayList<>(getSudokuFieldList());
        return new SudokuRow(elements);
    }
}
