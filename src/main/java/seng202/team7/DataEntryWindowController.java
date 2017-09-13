package seng202.team7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;


public class DataEntryWindowController {

    public Button uploadcsvButton;
    public Button addDataButton;

    @FXML
    private TextField fileName;

    @FXML
    private ComboBox dataEntryComboBox;


    //trip
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private TextField bikeID;
    @FXML
    private ComboBox userTypeComboBox;
    @FXML
    private TextField birthYear;
    @FXML
    private TextField gender;
    @FXML
    private TextField startStationID;
    @FXML
    private TextField endStationID;



    //wifi
    @FXML
    private TextField provider;
    @FXML
    private TextField typeWifi;
    @FXML
    private TextField locationWifi;
    @FXML
    private TextField cityWifi;
    @FXML
    private TextField borough;
    @FXML
    private TextField SSID;
    @FXML
    private TextField remarks;
    @FXML
    private TextField longitude;
    @FXML
    private TextField latitude;



    //retailer
    @FXML
    private TextField name;
    @FXML
    private TextField ZIP;
    @FXML
    private TextField state;
    @FXML
    private TextField cityRetailer;
    @FXML
    private TextField pAddress;
    @FXML
    private TextField sAddress;
    @FXML
    private TextField typeID;
    @FXML
    private TextField typeRetailer;


    /**
     * loads a file of data from a csv file name provided
     * @param event
     */
    public void uploadcsvButton(ActionEvent event) {
        InputHandler toTest = new InputHandler();
        DatabaseUpdater toUpload = new DatabaseUpdater();
        ArrayList<Data> toAdd = null;

        String dataTypeAdded = (String ) dataEntryComboBox.getValue();
        String csvFile = fileName.getText();
        csvFile = csvFile  + ".csv";

        try {
            toAdd =  toTest.loadCSV(csvFile, dataTypeAdded);
        } catch (IOException e) {
            e.printStackTrace();
        }

        toUpload.addData(toAdd);
    }

    /**
     * loads manually inputted data
     * @param event
     */
    public void addDataButton(ActionEvent event) {
        //to implement, will take the data entered and add to file
        //placeholder
        System.out.println("To Implement");

        ArrayList<Data> toAdd = new ArrayList<Data>();
        InputHandler toTest = new InputHandler();
        DatabaseUpdater toUpload = new DatabaseUpdater();
        String dataTypeAdded = (String ) dataEntryComboBox.getValue();

        //System.out.println(dataTypeAdded);

        //case handling

        switch (dataTypeAdded) {
            case "trip":

                System.out.println("trip");
                String startTime2 = startTime.getText();                        //year-month-day hour:minute:second   ... maybe
                String endTime2 = endTime.getText();                            //year-month-day hour:minute:second   ... maybe
                String bikeID2 = bikeID.getText();
                String userType2 = (String ) userTypeComboBox.getValue();
                int birthYear2 = Integer.parseInt(birthYear.getText());
                String gender2 = gender.getText();
                int startStationID2 = Integer.parseInt(startStationID.getText());
                int endStationID2 = Integer.parseInt(endStationID.getText());

                //start/end station address and lat and long derived here with functions yet to be created
                double startStationLat = 0.0;
                double startStationLong = 0.0;
                String startStationAddress = "nada";
                Station startStation = new Station(startStationID2, startStationAddress, "default", startStationLat, startStationLong);
                if (!toTest.checkValidity(startStation)) {
                    //stations not valid
                    break;
                }
                double endStationLat = 0.0;
                double endStationLong = 0.0;
                String endStationAddress = "nada";
                Station endStation = new Station(endStationID2, endStationAddress, "default", endStationLat, endStationLong);
                int duration = 0;   //derive duration
                if (!toTest.checkValidity(endStation)) {
                    //stations not valid
                    break;
                }

                /*System.out.println(startTime2);
                System.out.println(endTime2);
                System.out.println(bikeID2);
                System.out.println(userType2);               //for testing
                System.out.println(birthYear2);
                System.out.println(gender2);
                System.out.println(startStationID2);
                System.out.println(endStationID2);
                */

                Trip trip = new Trip(startStation, endStation, duration, startTime2, endTime2, userType2, birthYear2, gender2, "default");
                if (toTest.checkValidity(trip) == true) {
                    Data tripToAdd = new Trip(startStation, endStation, duration, startTime2, endTime2, userType2, birthYear2, gender2, "default");
                    toAdd.add(tripToAdd);
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

        toUpload.addData(toAdd);

    }
}