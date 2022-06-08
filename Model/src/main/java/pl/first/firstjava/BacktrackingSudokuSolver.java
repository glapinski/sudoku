package pl.first.firstjava;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {

    public void solve(final SudokuBoard board) {
        Random rand = new Random();
        List<Integer> startNumbers = Arrays.asList(new Integer[81]);
        for (int i = 0; i < startNumbers.size(); i++) {
            startNumbers.set(i, 0);
        }
        for (int i = 0; i < 81; i++) {
            int rowNumber = i / SudokuBoard.SIZE;
            int columnNumber = i % SudokuBoard.SIZE;

            boolean flagOK = false;
            if (startNumbers.get(i) == 0) {
                //jeżeli krok do przodu to ustawiamy nową wartość początkową
                startNumbers.set(i, rand.nextInt(9) + 1);
                board.set(rowNumber, columnNumber, startNumbers.get(i));

                do {
                    if (isValid(i, board)) {
                        flagOK = true;
                        break;
                    }
                    board.set(rowNumber, columnNumber, board.get(rowNumber, columnNumber) % 9 + 1);
                } while (board.get(rowNumber, columnNumber) != startNumbers.get(i));

            } else {
                //jeżeli krok do tyłu to wykorzystujemy poprzednią wartość początkową
                board.set(rowNumber, columnNumber, board.get(rowNumber, columnNumber) % 9 + 1);
                while (board.get(rowNumber, columnNumber) != startNumbers.get(i)) {
                    if (isValid(i, board)) {
                        flagOK = true;
                        break;
                    }
                    board.set(rowNumber, columnNumber, board.get(rowNumber, columnNumber) % 9 + 1);
                }
            }

            //jeżeli żaden nie pasuje to się cofamy
            if (!flagOK) {
                startNumbers.set(i, 0);
                board.set(rowNumber, columnNumber, 0);
                i -= 2;
            }
        }
    }

    private boolean isValid(int index, final SudokuBoard board) {
        int rowNumber = index / SudokuBoard.SIZE;
        int columnNumber = index % SudokuBoard.SIZE;
        int number = board.get(rowNumber, columnNumber);

        //Sprawdzanie wiersza
        for (int i = 0; i < columnNumber; i++) {
            if (number == board.get(rowNumber, i)) {
                return false;
            }
        }

        //Sprawdzanie kolumny
        for (int i = 0; i < rowNumber; i++) {
            if (number == board.get(i, columnNumber)) {
                return false;
            }
        }

        //Sprawdzanie malego kwadratu
        int bigRowIndex = rowNumber / (SudokuBoard.SIZE / 3);
        int bigColumnIndex = columnNumber / (SudokuBoard.SIZE / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int realRowIndex = i + bigRowIndex * 3;
                int realColumnIndex = j + bigColumnIndex * 3;
                if (board.get(realRowIndex, realColumnIndex) == number
                        && (realRowIndex * 9 + realColumnIndex) < index) {
                    return false;
                }
            }
        }

        return true;
    }
}
