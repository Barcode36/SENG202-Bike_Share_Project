package seng202.team7;

import java.util.HashMap;
import java.util.Map;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;

/**
 * Base class for a Retailer object to hold parameters and basic getters and setters
 * @author Aidan Smith asm142, Morgan English
 * Last edited 05/09/17
 */
public class Retailer extends Location implements Data, java.io.Serializable{
    /**
     * SQL tablename
     */
    public static String tableName = "retailer";

    public static String[] columns = {"id","name","city","pAddress","sAddress","state","zipCode","typeID","type","latitude","longitude","datagroup","obj"};
    /**
     * SQL table creation script
     */
    public static String tableCreation = "CREATE TABLE IF NOT EXISTS "+tableName+" (\n"
            + columns[0]+" integer PRIMARY KEY NOT NULL ,\n"
            + columns[1]+" text,\n"
            + columns[2]+" text,\n"
            + columns[3]+" text,\n"
            + columns[4]+" text,\n"
            + columns[5]+" text,\n"
            + columns[6]+" integer,\n"
            + columns[7]+" text,\n"
            + columns[8]+" text,\n"
            + columns[9]+" real NOT NULL,\n"
            + columns[10]+" real NOT NULL,\n"
            + columns[11]+" text,\n"
            + columns[12]+" blob\n"
            + ");";
    /**
     * Retailer name
     */
    private String name;
    /**
     * City
     */
    private String city;
    /**
     * Primary Address
     */
    private String pAddress;
    /**
     * Secondary Address
     */
    private String sAddress;
    /**
     * State
     */
    private String state;
    /**
     * zipCode
     */
    private int zipCode;
    /**
     * Letter representation of type. Could be made an enum
     */
    private String typeID;
    /**
     * Better description of type
     */
    private String type;
    /**
     * Datagroup string for database storing
     */
    private String dataGroup;
    /**
     * Street where the retailer is to be used for filtering
     */
    private String street;

    private double latitude;
    private double longitude;


    public Retailer(String name, String city, String pAddress, String sAddress, String state, int zipCode, String typeID, String type, String dataGroup)
    {
        this.name = name;
        this.city = city;
        this.pAddress = pAddress;
        boolean streetStart = false;
        String street = "";
        for (int i = 0; i < pAddress.length(); i++) {
            char character = pAddress.charAt(i);
            if (streetStart) {
                street += character;
            } else if (!Character.isDigit(character)) {
                if (!Character.isDigit(pAddress.charAt(i + 1))) {
                    if (i == 0) {
                        street += character;
                        streetStart = true;
                    } else if (character == ' ') {
                        streetStart = true;
                    }
                }
            }
        }
        this.street = street;
        this.sAddress = sAddress;
        this.state = state;
        this.zipCode = zipCode;
        this.type = type;
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("F", "Food");
        typeMap.put("N", "Nightlife");
        typeMap.put("S", "Shopping");
        typeMap.put("P", "Personal/Professional Services");
        typeMap.put("V", "Visitor Services");
        typeMap.put("C", "Community Resources");
        this.typeID = typeMap.get(typeID);
        this.dataGroup = dataGroup;
        try {
            String zipcode_s = Integer.toString(zipCode) + ", " + city;
            System.out.println(zipcode_s);
            addressToLATLONG(zipcode_s);
        } catch (java.lang.Exception e) {
            System.out.println("exception occured");
        }
    }


    /**
     * Converts an address to a latitude and longitude and stores them within the object.
     * Should be ran on instantiation
     */
    public void addressToLATLONG(String address) throws Exception
    {
        int responseCode = 0;
        String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
        System.out.println("URL : "+api);
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();
        if(responseCode == 200) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ;
            Document document = builder.parse(httpConnection.getInputStream());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");
            String status = (String) expr.evaluate(document, XPathConstants.STRING);
            if (status.equals("OK")) {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
                double d_latitude = Double.parseDouble(latitude);
                double d_longitude = Double.parseDouble(longitude);
                this.latitude = d_latitude;
                this.longitude = d_longitude;
            } else {
                throw new Exception("Error from the API - response status: " + status);
            }
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getPAddress() {
        return pAddress;
    }
    public void setPAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getSAddress() {
        return sAddress;
    }
    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getTypeID() {
        return typeID;
    }
    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDataGroup() {
        return dataGroup;
    }
    public void setDataGroup(String dataGroup) {
        this.dataGroup = dataGroup;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public void print(){
        System.out.println("Name: " + name +" Lat: "+latitude + " Lon: "+ longitude);
    }
}
