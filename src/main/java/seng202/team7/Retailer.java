package seng202.team7;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for a Retailer object to hold parameters and basic getters and setters
 * @author Aidan Smith asm142, (Morgan English???)
 * Last edited 05/09/17
 */
public class Retailer extends Location implements Data, java.io.Serializable{
    /**
     * SQL tablename
     */
    public static String tableName = "retailer";
    /**
     * SQL table creation script
     */
    public static String tableCreation = "CREATE TABLE IF NOT EXISTS "+tableName+" (\n"
            + "	id integer PRIMARY KEY NOT NULL ,\n"
            + "	name text,\n"
            + "	city text,\n"
            + "	pAddress text,\n"
            + " sAddress text,\n"
            + "	state text,\n"
            + "	zipCode integer,\n"
            + "	typeID text,\n"
            + "	type text,\n"
            + " latitude real NOT NULL,\n"
            + " longitude real NOT NULL,\n"
            + "	datagroup text,\n"
            + "	obj blob\n"
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
        for (char character : pAddress.toCharArray()) {
            if (streetStart) {
                street += character;
            } else if (!Character.isDigit(character)) {
                street += character;
                streetStart = true;
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
        addressToLATLONG();
    }


    /**
     * Converts an address to a latitude and longitude and stores them within the object.
     * Should be ran on instantiation
     */
    private void addressToLATLONG()
    {

        //find lat and long
        this.latitude = 0;
        this.longitude = 0;
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
