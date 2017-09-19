package seng202.team7;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Retailer data model for raw data viewing of retailer data
 * @author Aidan Smith asm142
 * Last updated 13/09/17
 */


/**
 * todo
 */
public class RetailerDataViewer extends AnchorPane {

    public RetailerDataViewer(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("RetailerDataViewer.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}