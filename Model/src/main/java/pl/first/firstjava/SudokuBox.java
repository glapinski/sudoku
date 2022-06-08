package pl.first.firstjava;

import java.util.ArrayList;
import java.util.List;

public class SudokuBox extends SudokuContainer {
    public static final int BOX_SIZE = 3;

    public SudokuBox(final List<SudokuField> elements) {
        super(elements);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> elements = new ArrayList<>(getSudokuFieldList());
        return new SudokuBox(elements);
    }
}
