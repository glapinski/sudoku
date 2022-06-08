package pl.first.firstjava;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.firstjava.exception.DaoException;
import pl.first.firstjava.exception.DbLoadException;
import pl.first.firstjava.exception.WriteToFileException;


public class SudokuController implements Initializable {
    Logger logger = LoggerFactory.getLogger(SudokuController.class);
    private final MenuController menuController;
    private final Stage stage;
    private Difficulty difficulty;
    private ResourceBundle bundle;
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao base;
    @FXML
    Button buttonOne;
    @FXML
    Button buttonTwo;
    @FXML
    Button buttonThree;
    @FXML
    Button buttonFour;
    @FXML
    Button buttonFive;
    @FXML
    Button buttonSix;
    @FXML
    Button buttonSeven;
    @FXML
    Button buttonEight;
    @FXML
    Button buttonNine;
    @FXML
    Canvas canvas;
    @FXML
    Button exitButton;
    @FXML
    Button saveButton;
    @FXML
    Text txtID;

    SudokuBoard gameboard;
    SudokuSolver solver;
    int playerSelectedRow;
    int playerSelectedCol;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (menuController.getLevelFlag() == 1) {
            difficulty = Difficulty.Easy;
        }
        if (menuController.getLevelFlag() == 2) {
            difficulty = Difficulty.Medium;
        }
        if (menuController.getLevelFlag() == 3) {
            difficulty = Difficulty.Medium;
        }
        Color awtColor = Color.WHITE;
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);
        solver = new BacktrackingSudokuSolver();
        gameboard = new SudokuBoard(solver);
        GraphicsContext context = canvas.getGraphicsContext2D();
        drawOnCanvas(context);
        context.clearRect(0, 0, 450, 450);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int positionY = row * 50 + 2;
                int positionX = col * 50 + 2;
                int width = 46;
                context.setFill(fxColor);
                context.fillRoundRect(positionX, positionY, width, width, 10, 10);
            }
        }
        if (menuController.getLevelFlag() == 0) {
            try {
                gameboard = this.loadGame(menuController.getChosenIndex());
            } catch (IOException | DbLoadException e) {
                e.printStackTrace();
            }
        }
    }

    public SudokuController(MenuController menuController) throws SQLException {
        this.menuController = menuController;
        base = factory.getDataBaseDao("DataBase");
        stage = new Stage();
        try {
            logger.info("Setting scene");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout.fxml"));
            loader.setController(this);
            bundle = ResourceBundle.getBundle("Language", menuController.getLocale());
            loader.setResources(bundle);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Sudoku");
        } catch (IOException e) {
            logger.error("Error while setting scene", e);
            e.printStackTrace();
        }
        if (difficulty != null) {
            gameboard.clearBoard();
            gameboard.solveGame();
            gameboard.changeBoard(difficulty);
            drawOnCanvas(canvas.getGraphicsContext2D());
        }
        if (menuController.getLevelFlag() == 0) {
            drawOnCanvas(canvas.getGraphicsContext2D());
        }
    }

    public void drawOnCanvas(GraphicsContext context) {
        //String text = bundle.getString("_Attempts") + " " + fails;
        //txtID.setText(text);
        Color awtColor = Color.WHITE;
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);
        context.clearRect(0, 0, 450, 450);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int positionY = row * 50 + 2;
                int positionX = col * 50 + 2;
                int width = 46;
                context.setFill(fxColor);
                context.fillRoundRect(positionX, positionY, width, width, 10, 10);
            }
        }
        canvas.setStyle("-fx-background-color: white;");
        awtColor = Color.RED;
        r = awtColor.getRed();
        g = awtColor.getGreen();
        b = awtColor.getBlue();
        a = awtColor.getAlpha();
        opacity = a / 255.0;
        fxColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);
        context.setStroke(fxColor);
        context.setLineWidth(5);
        context.strokeRoundRect(playerSelectedCol * 50 + 2,
                playerSelectedRow * 50 + 2, 46, 46, 10, 10);
        int[][] initial = gameboard.getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int positionY = row * 50 + 30;
                int positionX = col * 50 + 20;
                Color awtColor2 = Color.BLACK;
                int r2 = awtColor2.getRed();
                int g2 = awtColor2.getGreen();
                int b2 = awtColor2.getBlue();
                int a2 = awtColor2.getAlpha();
                double opacity2 = a2 / 255.0;
                javafx.scene.paint.Color black = javafx.scene.paint.Color.rgb(r2, g2, b2, opacity2);
                context.setFill(black);
                if (initial[row][col] != 0) {
                    context.fillText(initial[row][col] + "", positionX, positionY);
                }
            }
        }
    }

    public void canvasMouseClicked() {
        logger.info(bundle.getString("_Canvas"));
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int mouseX = (int) event.getX();
                int mouseY = (int) event.getY();
                playerSelectedRow = (int) (mouseY / 50);
                playerSelectedCol = (int) (mouseX / 50);
                drawOnCanvas(canvas.getGraphicsContext2D());
            }
        });
    }

    public void exitGame() {
        logger.info(bundle.getString("_Leaving"));
        menuController.showStage();
        this.stage.close();
    }

    public void badNumberWarning() {
        logger.info(bundle.getString("_Wrong"));
        PopOut.messageBox(bundle.getString("_warning"),
                bundle.getString("_badNumber"), Alert.AlertType.WARNING);
        fails--;
        //String text = bundle.getString("_Attempts") + " " + fails;
        //txtID.setText(text);
        if (fails == 0) {
            endGame();
        }
    }

    public void endGame() {
        PopOut.messageBox(bundle.getString("_warning"),
                bundle.getString("_Ending"), Alert.AlertType.WARNING);
        exitGame();
    }

    public void showStage() {
        stage.show();
    }

    public SudokuBoard loadGame(int name) throws IOException, DbLoadException {
        return base.read(name);
    }

    public void saveClicked() throws SQLException {
        TextInputDialog td = new TextInputDialog(bundle.getString("_name"));
        td.setHeaderText(bundle.getString("_chooseName"));
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            base.write(gameboard, result.get());
        } else {
            logger.warn(bundle.getString("_chooseFileCancel"));
        }
    }

    public void saveToFile(String name) throws SQLException, DaoException {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try {
            daoFactory.getFileDao(name).write(gameboard);
        } catch (WriteToFileException e) {
            e.printStackTrace();
        }
    }

    private int fails = 3;

    public void oneClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 1, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 1);

        } else {
            badNumberWarning();
        }
    }

    public void twoClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 2, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 2);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void threeClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 3, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 3);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void fourClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 4, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 4);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void fiveClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 5, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 5);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void sixClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 6, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 6);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void sevenClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 7, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 7);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void eightClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 8, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 8);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }

    public void nineClicked() {
        if (gameboard.get(playerSelectedRow, playerSelectedCol) == 0
                && gameboard.check(playerSelectedRow, playerSelectedCol, 9, gameboard)) {
            gameboard.set(playerSelectedRow, playerSelectedCol, 9);
            drawOnCanvas(canvas.getGraphicsContext2D());
        } else {
            badNumberWarning();
        }
    }
}
