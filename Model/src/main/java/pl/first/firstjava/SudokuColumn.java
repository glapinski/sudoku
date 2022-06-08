package pl.first.firstjava;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn extends SudokuContainer {
    public SudokuColumn(final List<SudokuField> elements) {
        super(elements);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> elements = new ArrayList<>(getSudokuFieldList());
        return new SudokuColumn(elements);
    }
}
