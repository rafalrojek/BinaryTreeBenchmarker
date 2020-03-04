package pl.edu.wat.wcy.ita;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.wat.wcy.ita.gui.Controller;
import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setStageAndSetupListeners(stage); // or what you want to do

        Scene scene = new Scene(root, 1600, 900);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
