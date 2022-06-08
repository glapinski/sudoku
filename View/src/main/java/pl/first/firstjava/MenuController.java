package pl.first.firstjava;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuController implements Initializable {
    Logger logger = LoggerFactory.getLogger(MenuController.class);
    private Stage stage;
    private int levelFlag;
    private Scene scene;
    Locale locale = new Locale("pl");
    private ResourceBundle authors = ResourceBundle.getBundle("pl.first.firstjava.Authors",locale);
    private ResourceBundle bundle = ResourceBundle.getBundle("Language", locale);
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao base;
    int chosenIndex;
    @FXML
    Button easyButton;
    @FXML
    Button mediumButton;
    @FXML
    Button hardButton;
    @FXML
    Button exitButton;
    @FXML
    Button loadButton;
    @FXML
    Button authorsButton;
    @FXML
    private ToggleButton buttonLanguageEnglish;
    @FXML
    private ToggleButton buttonLanguagePolish;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteButton.setOnAction(actionEvent -> {
            try {
                delButtonPress();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        easyButton.setOnAction(actionEvent -> {
            try {
                openBoard(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        mediumButton.setOnAction(actionEvent -> {
            try {
                openBoard(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        hardButton.setOnAction(actionEvent -> {
            try {
                openBoard(3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        loadButton.setOnAction(actionEvent -> {
            try {
                loadClicked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        final ToggleGroup group = new ToggleGroup();
        buttonLanguagePolish.setToggleGroup(group);
        buttonLanguagePolish.setSelected(true);
        buttonLanguageEnglish.setToggleGroup(group);

        buttonLanguagePolish.selectedProperty().addListener((observable, oldValue, newValue) -> {
            changeLanguage("pl");
            logger.info(bundle.getString("_LanguageChoosePolish"));
            try {
                reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonLanguageEnglish.selectedProperty().addListener((observable, oldValue, newValue) -> {
            changeLanguage("en");
            logger.info(bundle.getString("_LanguageChooseEnglish"));
            try {
                reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void delButtonPress() throws SQLException {
        List<Integer> indexes = base.getBoardIndexes();
        List<String> names = base.getBoardNames();
        if (names.size() > 0) {
        ChoiceDialog<String> chd = new ChoiceDialog<String>(names.get(0), names);
        chd.setHeaderText(bundle.getString("_delete"));
        Optional<String> result = chd.showAndWait();
        result.ifPresent(s -> base.delete(indexes.get(names.indexOf(s))));
        } else {
            logger.warn(bundle.getString("_nofiles"));
        }
    }

    public void reload() throws IOException {
        stage.close();
        stage = new Stage();
        bundle = ResourceBundle.getBundle("Language", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/form.fxml"));
        loader.setController(this);
        loader.setResources(bundle);
        scene = new Scene(loader.load(), 300, 450);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("applicationTitle");
        showStage();
    }


    public MenuController() throws SQLException {
        stage = new Stage();
        base = factory.getDataBaseDao("DataBase");
        try {
            logger.info(bundle.getString("_MenuSetup"));
            bundle = ResourceBundle.getBundle("Language", locale);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/form.fxml"));
            loader.setController(this);
            loader.setResources(bundle);
            scene = new Scene(loader.load(), 300, 450);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("applicationTitle");
        } catch (IOException e) {
            logger.error("Error while setting scene", e);
            e.printStackTrace();
        }

    }

    public void showStage() {
        stage.show();
    }

    public int getLevelFlag() {
        return levelFlag;
    }

    public int getChosenIndex() {
        return chosenIndex;
    }

    public void setLevelFlag(int levelFlag) {
        this.levelFlag = levelFlag;
    }

    public void openBoard(int levelFlag) throws SQLException {
        setLevelFlag(levelFlag);
        SudokuController sudokuController = new SudokuController(this);
        stage.close();
        sudokuController.showStage();
    }

    public void loadClicked() throws SQLException {
        List<String> names = base.getBoardNames();
        if (names.size() > 0) {
            ChoiceDialog<String> chd = new ChoiceDialog<String>(names.get(0), names);
            chd.setHeaderText(bundle.getString("_loading"));
            Optional<String> result = chd.showAndWait();
            if (result.isPresent()) {
                List<Integer> indexes = base.getBoardIndexes();
                chosenIndex = indexes.get(names.indexOf(result.get()));
                openBoard(0);
                logger.info(bundle.getString("_Load"));
            } else {
                logger.warn(bundle.getString("_chooseFileCancel"));
            }
        } else {
            logger.warn(bundle.getString("_nofiles"));
        }
    }

    public void changeLanguage(String languageCode) {
        if (languageCode.length() == 2) {
            this.locale = new Locale(languageCode);
        }
    }

    public void exitClicked() {
        Platform.exit();
        System.exit(0);
        logger.info(bundle.getString("_ClosingBoard"));
    }

    public Locale getLocale() {
        return locale;
    }

    public void authorsClicked() {
        logger.info(bundle.getString("_ShowingAuthors"));
        PopOut.messageBox("",
                authors.getObject("1. ") + "\n" + authors.getObject("2. "),
                Alert.AlertType.INFORMATION);
    }

}
