package pl.first.firstjava;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.first.firstjava.exception.BadContainerSizeException;

public abstract class SudokuContainer {
    public static final int SIZE = 9;
    private final List<SudokuField> elements;

    public SudokuContainer(final List<SudokuField> elements) {
        if (elements.size() != SIZE) {
            throw new BadContainerSizeException("Length must be 9");
        }
        this.elements = elements;
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (elements.get(i).getFieldValue() == elements.get(j).getFieldValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<SudokuField> getSudokuFieldList() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("elements", elements).toString();
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
        SudokuContainer rhs = (SudokuContainer) obj;
        return new EqualsBuilder()
                .append(elements, rhs.elements)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(elements)
                .toHashCode();
    }
}
