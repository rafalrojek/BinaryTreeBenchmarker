package pl.edu.wat.wcy.ita;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.wat.wcy.ita.gui.Controller;
import pl.edu.wat.wcy.ita.gui.WelcomeController;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("welcome.fxml")));
        Parent root = fxmlLoader.load();
        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setMain(this); // or what you want to do

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Tester drzew binarnych");
        stage.setScene(scene);
        stage.show();
    }

    public void newScene () {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = fxmlLoader.getController();
        controller.setStageAndSetupListeners(stage); // or what you want to do

        Scene scene = new Scene(root, 1600, 900);

        stage.setScene(scene);
        stage.show();
    }
}
