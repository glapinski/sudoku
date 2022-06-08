package pl.first.firstjava;

import org.junit.jupiter.api.Test;
import pl.first.firstjava.exception.DaoException;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private SudokuBoard sudokuBoard;

    private Dao<SudokuBoard> fileSudokuBoardDao;

    @Test
    public void writeReadTest() throws DaoException {
        try {
            fileSudokuBoardDao = factory.getFileDao("name");
            fileSudokuBoardDao.write(sudokuBoard);
            SudokuBoard sudokuBoard2 = null;
            sudokuBoard2 = fileSudokuBoardDao.read();
        assertEquals(sudokuBoard, sudokuBoard2);
        } catch (IOException | SQLException e) {
            throw new DaoException("Board loading fail", e);
        }
    }

    @Test
    public void readExceptionTest() {
        fileSudokuBoardDao = factory.getFileDao("name2");
        assertThrows(DaoException.class, () -> {fileSudokuBoardDao.read();});
    }

    @Test
    public void writeExceptionTest() {
        fileSudokuBoardDao = factory.getFileDao("?;/:");
        assertThrows(DaoException.class, () -> {fileSudokuBoardDao.write(sudokuBoard);});
    }
}