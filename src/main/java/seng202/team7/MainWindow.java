package seng202.team7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Main window model for running the main program
 * @author Connor McEwan-McDowall
 */

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));

        primaryStage.setTitle("Meraki Bikes");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
