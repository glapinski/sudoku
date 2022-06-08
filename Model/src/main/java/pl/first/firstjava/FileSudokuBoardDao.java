package pl.first.firstjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.firstjava.exception.LoadFromFileException;
import pl.first.firstjava.exception.WriteToFileException;



public class FileSudokuBoardDao implements  Dao<SudokuBoard>, AutoCloseable {
    String fileName;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSudokuBoardDao.class);
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.exceptions");

    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws LoadFromFileException {
        try (FileInputStream fin = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fin)) {
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warn("File " + fileName + " not found", e);
            throw new LoadFromFileException(bundle.getString("LOAD_FROM_FILE"), e);
        }
    }

    @Override
    public void write(SudokuBoard board) throws WriteToFileException {
        try (FileOutputStream fout = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(board);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new WriteToFileException(bundle.getString("WRITE_TO_FILE"), e);
        }
    }


    @Override
    public void close() throws Exception {

    }
}

