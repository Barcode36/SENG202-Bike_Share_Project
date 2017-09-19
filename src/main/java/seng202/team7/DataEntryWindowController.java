package seng202.team7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DataEntryWindowController {

    public Button uploadcsvButton;
    public Button addDataButton;
    //@FXML
    //private TextField fileName;

    @FXML private ComboBox dataEntryComboBox;

    // Trip
    @FXML private TextField startTime;
    @FXML private TextField endTime;
    @FXML private TextField bikeID;
    @FXML private ComboBox userTypeComboBox;
    @FXML private TextField birthYear;
    @FXML private TextField gender;
    @FXML private TextField startStationID;
    @FXML private TextField endStationID;

    // Wifi
    @FXML private TextField provider;
    @FXML private TextField typeWifi;
    @FXML private TextField locationWifi;
    @FXML private TextField cityWifi;
    @FXML private TextField borough;
    @FXML private TextField SSID;
    @FXML private TextField remarks;
    @FXML private TextField longitude;
    @FXML private TextField latitude;

    // Retailer
    @FXML private TextField name;
    @FXML private TextField ZIP;
    @FXML private TextField state;
    @FXML private TextField cityRetailer;
    @FXML private TextField pAddress;
    @FXML private TextField sAddress;
    @FXML private TextField typeID;
    @FXML private TextField typeRetailer;

    /**
     * Loads a file of data from a csv file name provided
     * @param event
     */
    public void uploadcsvButton(ActionEvent event) {
        InputHandler toParse = new InputHandler();
        DatabaseUpdater toUpload = new DatabaseUpdater();
        ArrayList<Data> toAdd = null;

        String dataTypeAdded = (String ) dataEntryComboBox.getValue();

        /*
        String csvFile = fileName.getText();
        csvFile = csvFile  + ".csv";
        */

        String csvFile;
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(stage);

        csvFile = file.toString();

        try {
            toAdd =  toParse.loadCSV(csvFile, dataTypeAdded);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        toUpload.addData(toAdd);
    }

    /**
     * Loads manually inputted data and adds to database
     * @param event
     */
    public void addDataButton(ActionEvent event) {

        ArrayList<Data> toAdd = new ArrayList<Data>();
        InputHandler toTest = new InputHandler();
        DatabaseUpdater dataUploader = new DatabaseUpdater();
        String dataTypeAdded = (String ) dataEntryComboBox.getValue();

        try {
            //case handling
            switch (dataTypeAdded) {
                case "trip":
                    String startTime2 = startTime.getText();                        //year-month-day hour:minute:second
                    String endTime2 = endTime.getText();                            //year-month-day hour:minute:second
                    String bikeID2 = bikeID.getText();
                    String userType2 = (String ) userTypeComboBox.getValue();
                    int birthYear2 = Integer.parseInt(birthYear.getText());
                    int gender2 = Integer.parseInt(gender.getText().trim());
                    int startStationID2 = Integer.parseInt(startStationID.getText());
                    int endStationID2 = Integer.parseInt(endStationID.getText());

                    //start and end station address, lat and long derived here, need case handling for if station doesn't exist
                    DatabaseRetriever databaseRetriever = new DatabaseRetriever();
                    Station start  = databaseRetriever.queryStation(StaticVariables.stationIDQuery(startStationID2)).get(0);
                    Station end  = databaseRetriever.queryStation(StaticVariables.stationIDQuery(endStationID2)).get(0);

                    double startStationLat = start.getLatitude();
                    double startStationLong = start.getLongitude();
                    String startStationAddress = start.getAddress();
                    Station startStation = new Station(startStationID2, startStationAddress, "default", startStationLat, startStationLong);
                    if (!toTest.checkValidity(startStation)) {
                        //stations not valid
                        break;
                    }
                    double endStationLat = end.getLatitude();
                    double endStationLong = end.getLongitude();
                    String endStationAddress = end.getAddress();
                    Station endStation = new Station(endStationID2, endStationAddress, "default", endStationLat, endStationLong);
                    int duration = 0;   //derive duration
                    if (!toTest.checkValidity(endStation)) {
                        //stations not valid
                        break;
                    }

                    System.out.println(endStationLat);
                    System.out.println(endStationLong);
                    System.out.println(endStationAddress);
                    System.out.println(userType2);               //for testing
                    System.out.println(birthYear2);
                    System.out.println(gender2);
                    System.out.println(startStationID2);
                    System.out.println(endStationID2);

                    Trip trip = new Trip(startStation, endStation, duration, startTime2, endTime2, userType2, birthYear2, gender2, "default");
                    if (toTest.checkValidity(trip) == true) {
                        Data tripToAdd = new Trip(startStation, endStation, duration, startTime2, endTime2, userType2, birthYear2, gender2, "default");
                        toAdd.add(tripToAdd);
                        break;
                    }
                    break;

                case "retailer":
                    String nameRetailer2 = name.getText();
                    int ZIP2 = Integer.parseInt(ZIP.getText());
                    String state2 = state.getText();
                    String cityRetailer2 = cityRetailer.getText();
                    String pAddress2 = pAddress.getText();
                    String sAddress2 = sAddress.getText();
                    String typeID2 = typeID.getText();
                    String typeRetailer2 = typeRetailer.getText();

                    Retailer retailer = new Retailer(nameRetailer2, cityRetailer2, pAddress2, sAddress2, state2, ZIP2, typeID2, typeRetailer2, "default");
                    if (toTest.checkValidity(retailer) == true) {
                        Data retailerToAdd = new Retailer(nameRetailer2, cityRetailer2, pAddress2, sAddress2, state2, ZIP2, typeID2, typeRetailer2, "default");
                        toAdd.add(retailerToAdd);

                    }

                /*System.out.println(retailer.getTypeID());
                System.out.println(retailer.getType());
                System.out.println(retailer.getPAddress());
                System.out.println(retailer.getSAddress());
                System.out.println(retailer.getState());         for testing
                System.out.println(retailer.getZipCode());
                System.out.println(retailer.getName());
                System.out.println(retailer.getLongitude());
                System.out.println(retailer.getLatitude());
                System.out.println(retailer.getStreet());
                System.out.println(retailer.getCity());
                */

                    break;

                case "wifi":
                    String provider2 = provider.getText();
                    String typeWifi2 = typeWifi.getText();
                    String location2 = locationWifi.getText();
                    String cityWifi2 = cityWifi.getText();
                    String borough2 = borough.getText();
                    String SSID2 = SSID.getText();
                    String remarks2 = remarks.getText();
                    double longitude2 = Double.parseDouble(longitude.getText());
                    double latitude2 = Double.parseDouble(latitude.getText());

                    System.out.println(provider2);


                    Wifi wifi = new Wifi(borough2, typeWifi2, provider2, location2, cityWifi2, SSID2, remarks2, "default", longitude2, latitude2);
                    if (toTest.checkValidity(wifi) == true) {
                        Data wifiToAdd = new Wifi(borough2, typeWifi2, provider2, location2, cityWifi2, SSID2, remarks2, "default", longitude2, latitude2);
                        toAdd.add(wifiToAdd);

                    }

                    break;
            }
        }
        catch (NumberFormatException | NullPointerException e) {
            System.out.println("Not enough data inputted, maybe wrong data type selected?");
        }

        dataUploader.addData(toAdd);
    }
}
