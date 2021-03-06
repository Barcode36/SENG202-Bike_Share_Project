package seng202.team7.Windows.HelpWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * A custom JavaFX object representing the data entry help screen.
 * @author Connor McEwan-McDowall
 */
public class DataEntryHelp extends AnchorPane {

    /**
     * Attempts to create a new data entry help screen. Throws a RuntimeException if this fails.
     */
    public DataEntryHelp(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Views/HelpWindow/DataEntryHelp.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}