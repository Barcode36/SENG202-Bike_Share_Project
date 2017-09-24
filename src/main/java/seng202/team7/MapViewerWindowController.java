package seng202.team7;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Map viewer window to view data points on a map controller class
 * @author Mitchell Fenwick
 */

public class MapViewerWindowController implements Initializable {

    // Widgets used in map viewer
    @FXML
    private ComboBox<String> mapComboBorough;
    @FXML
    private ComboBox<String> mapComboRetailerType;
    @FXML
    private ComboBox<String> mapComboProvider;
    @FXML
    private ComboBox<String> mapComboWifiType;
    @FXML
    private ComboBox<String> mapComboStreet;
    @FXML
    private ComboBox<String> mapComboZipcode;
    @FXML
    private WebView mapView;

    //Local variables needed
    private int currentRetailerIndex = -1;
    private int loadedData = 0;
    private DatabaseRetriever dbRetriever;
    private DatabaseUpdater dbUpdater;
    private boolean loadedAll = false;
    private boolean scrollAdded = false;
    private ObservableList<Retailer> retailerList;
    private ObservableList<Retailer> filteredRetailerList;
    private WebEngine webEngine;
    private JSObject jsBridge;
    private ObservableList<Wifi> wifiList;
    private ObservableList<Wifi> filteredWifiList;


    /**
     * Initialises the map view and adds data to it
     *
     * @param url Required parameter that is not used in the function
     * @param rb  Required parameter that is not used in the function
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



        //initialise the map view
        webEngine = mapView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
                JSObject jsObject = (JSObject) webEngine.executeScript("window");
                jsObject.setMember("bridge", new JSHandler());
                jsBridge = (JSObject) webEngine.executeScript("getJsConnector()");
            }
        });
        webEngine.load(getClass().getClassLoader().getResource("googlemaps.html").toExternalForm());



        //Initialise retailers and wifi in combo boxes
        dbUpdater = new DatabaseUpdater();
        dbRetriever = new DatabaseRetriever();
        ArrayList<Retailer> retailerArrayList = dbRetriever.queryRetailer(StaticVariables.steppedQuery(Retailer.tableName, loadedData));
        retailerList = FXCollections.observableArrayList(retailerArrayList);
        filteredRetailerList = FXCollections.observableArrayList(retailerList);
        if (retailerList.size() < 50) {
            loadedAll = true;
        }
        ArrayList<Wifi> wifiArrayList = dbRetriever.queryWifi(StaticVariables.steppedQuery(Wifi.tableName, loadedData));
        wifiList = FXCollections.observableArrayList(wifiArrayList);
        filteredWifiList = FXCollections.observableArrayList(wifiList);
        if (wifiList.size() < 50) {
            loadedAll = true;
        }

        ArrayList<String> streets = new ArrayList<>();
        ArrayList<String> zips = new ArrayList<>();
        for (Retailer retailer : retailerList) {
            if (!streets.contains(retailer.getStreet())) {
                streets.add(retailer.getStreet());
            }
            if (!zips.contains(Integer.toString(retailer.getZipCode()))) {
                zips.add(Integer.toString(retailer.getZipCode()));
            }
        }

        ArrayList<String> providers = new ArrayList<String>();
        for (Wifi wifi : wifiList) {
            if (!providers.contains(wifi.getProvider())) {
                providers.add(wifi.getProvider());
            }
        }

        //Retailers combo boxes
        mapComboZipcode.getItems().addAll(zips);
        mapComboZipcode.getItems().add("None");
        mapComboStreet.getItems().addAll(streets);
        mapComboStreet.getItems().add("None");
        mapComboRetailerType.getItems().removeAll(mapComboRetailerType.getItems());
        mapComboRetailerType.getItems().addAll(
                "Food",
                "Nightlife",
                "Shopping",
                "Personal/Professional Services",
                "Visitor Services",
                "Community Resources",
                "None"
        );
        mapComboRetailerType.getSelectionModel().select("None");

        //Wifi combo boxes
        mapComboProvider.getItems().addAll(providers);
        mapComboProvider.getItems().add("None");
        mapComboWifiType.getItems().removeAll();
        mapComboWifiType.getItems().addAll(
                "Free",
                "Limited Free",
                "Partner Site",
                "None"
        );
        mapComboBorough.getItems().removeAll();
        mapComboBorough.getItems().addAll(
                "QU",
                "BK",
                "BX",
                "MN",
                "SI",
                "None"
        );
        WebConsoleListener.setDefaultListener(new WebConsoleListener() {
            @Override
            public void messageAdded(WebView webView, String message, int lineNumber, String sourceId) {
                System.out.println("Console: [" + sourceId + ":" + lineNumber + "] " + message);
            }
        });



    }

    public void displayClicked()
    {
        //webEngine.executeScript("loadWifi();");
        webEngine.executeScript("loadRetailers();");
    }
}



