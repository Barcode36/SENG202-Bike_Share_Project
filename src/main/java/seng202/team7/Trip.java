package seng202.team7;
import java.text.ParseException;
import java.util.Date;


public class Trip extends Location implements Data, java.io.Serializable {

    public static String tableName = "trip";

    public static String tableCreation = "CREATE TABLE IF NOT EXISTS "+tableName+" (\n"
            + "	id integer PRIMARY KEY NOT NULL ,\n"
            + "	duration integer,\n"
            + "	startStationID integer,\n"
            + " startStation obj,\n"
            + "	endStationID integer,\n"
            + " endStation obj,\n"
            + " bikeID integer,\n"
            + "	gender text,\n"
            + "	age integer,\n"
            + "	userType text,\n"
            + "	startDate date,\n"
            + " startTime time,\n"
            + " endDate date,\n"
            + " endTime time,\n"
            + " distance real,\n"
            + "	datagroup text,\n"
            + "	obj blob\n"
            + ");";
    /**
     * Station at the start of the trip
     */
    private Station startStation;
    private int startStationID;
    /**
     * Station at the end of trip
     */
    private Station endStation;
    private int endStationID;

    /**
     * Start and end to reference the names of the stations for easy access from the raw data viewer
     */

    /**
     * Duration in seconds
     */
    private int duration;
    /**
     * Start time as java datetime object
     */
    private Date startDate;
    /**
     * End time as java datetime object
     */
    private Date endDate;
    /*What type should we make this
     * thinking ENUM so more types can be added
     */
    private String userType;
    /**
     * Bikeid of the bike used for the trip
     */
    private int bikeID;
    /**
     * Gender, could this be an Enum or int??
     */
    private String gender;
    /**
     * Attribute must be derived
     */
    private int age;
    /**
     * String for sorting within a table
     */
    private String dataGroup;

    /**
     * Distance of biketrip currently worked out using
     */
    private double distance;


    /**
     *
     * @param startStation station object at start of route
     * @param endStation station object at nd of route
     * @param duration duration of trip in seconds
     * @param startDate startdate of trip as string given with format "yyyy-mm-dd hh:mm:ss" 24hr time
     * @param endDate enddate of trip as string given with format "yyyy-mm-dd hh:mm:ss" 24hr time
     * @param userType usertype of cyclist
     * @param birthYear year of birth for user can then be used to calculate age
     * @param gender gander of cyclist
     * @param dataGroup datagroup string for sorting within tables
     */
    public Trip(Station startStation, Station endStation, int duration, String startDate, String endDate, String userType, int birthYear, int gender, String dataGroup)
    {
        this.startStation = startStation;
        this.startStationID = startStation.getId();
        this.endStation = endStation;
        this.endStationID = endStation.getId();
        this.duration = duration;
        try {
            this.startDate = StaticVariables.ft.parse(startDate);
            this.endDate = StaticVariables.ft.parse(endDate);
        } catch (ParseException e) {
            System.out.println("Error parsing Dates in Trip.java Constructor");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.userType = userType;
        this.age = StaticVariables.currentYear - birthYear;
        if (gender == 0) {
            this.gender = "Unknown";
        } else if (gender == 1 ) {
            this.gender = "Male";
        } else {
            this.gender = "Female";
        }
        this.dataGroup = dataGroup;

        this.distance = findDistance();
    }

    /**
     * Todo make birthyear take either age or birthyear. if number is greater than 1000 is birthyear else is age?? FIX GENDER HERE
     * @param startStation station object at start of route
     * @param endStation station object at nd of route
     * @param duration duration of trip in seconds
     * @param startDate startdate of trip as java date object
     * @param endDate enddate of trip as java date object
     * @param userType usertype of cyclist
     * @param birthYear year of birth for user can then be used to calculate age
     * @param gender gender of cyclist
     * @param dataGroup datagroup string for sorting within tables
     */
    public Trip(Station startStation, Station endStation, int duration, Date startDate, Date endDate, String userType, int birthYear, int gender, String dataGroup)
    {
        this.startStation = startStation;
        this.startStationID = startStation.getId();
        this.endStation = endStation;
        this.endStationID = endStation.getId();
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userType = userType;
        this.age = StaticVariables.currentYear - birthYear;
        if (gender == 0) {
            this.gender = "Unknown";
        } else if (gender == 1 ) {
            this.gender = "Male";
        } else {
            this.gender = "Female";
        }
        this.dataGroup = dataGroup;
        this.distance = findDistance();
    }



    /**
     * Works out and returns the distance of the trip in kilometers using the algorithm in StaticVariables class
     * @return the length of the trip between the start and end station
     */
    private double findDistance()
    {
        return StaticVariables.calculateDistance(startStation.getLatitude(), startStation.getLongitude(), endStation.getLatitude(), endStation.getLongitude());

    }


    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public int getStartStationID() {
        return startStationID;
    }

    public void setStartStationID(int startStationID) {
        this.startStationID = startStationID;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public int getEndStationID() {
        return endStationID;
    }

    public void setEndStationID(int endStationID) {
        this.endStationID = endStationID;
    }

    public int getDuration() {
        return duration;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setStartDate(String startDate) {
        try {
            this.startDate = StaticVariables.ft.parse(startDate);
        } catch (ParseException e) {
            System.out.println("Error parsing start date in setter");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String endDate) {
        try {
            this.endDate = StaticVariables.ft.parse(endDate);
        } catch (ParseException e) {
            System.out.println("Error parsing end date in setter");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Method to set age based on the birthyear of rider
     * @param birthYear Year of birth of bikerider
     */
    public void setAgeFromBirth(int birthYear) {
        this.age = StaticVariables.currentYear - birthYear;
    }

    public String getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(String dataGroup) {
        this.dataGroup = dataGroup;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * gets the end of the bike trip from the bike station attached
     * @return location of ending bikestation
     */
    public String getEnd() {
        return endStation.getAddress();
    }

    /**
     * gets the start of the bike trip from the bike station attached
     * @return location of starting bikestation
     */
    public String getStart() {
        return startStation.getAddress();
    }

    /**
     * Prints object for testing
     */
    public void testPrint()
    {
        System.out.println("Trip object:\n " +
                "Duration:" + this.getDuration() + "\n" +
                "Distance: " + this.getDistance());
    }


}

