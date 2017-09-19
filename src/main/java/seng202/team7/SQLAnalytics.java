package seng202.team7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used to complete analytics through sql queries where applicable
 */
public class SQLAnalytics {

    /**
     * Total trip duration of a datagroup of trips
     * @param datagroup datagroup string to match. if "" will look at all trips
     * @return sum of durations of all trips matched
     */
    public static long totalGroupTripDuration(String datagroup)
    {
        long totalTime = 0;
        String sql;
        if(datagroup != ""){
            sql = "SELECT SUM(duration) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \""+datagroup+"\";";
        } else {
            sql = "SELECT SUM(duration) AS sum FROM " + Trip.tableName + ";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
              totalTime = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("Get Station error");
            System.out.println(e.getMessage());
        }

        return totalTime;
    }


    /**
     * Finds the total duration of a datagroup of trips
     * @param datagroup datagroup string to match. if "" will look at all trips
     * @return total duration of matched trips
     */
    public static long totalGroupTripDistance(String datagroup)
    {
        long totalTime = 0;
        String sql;
        if(datagroup != "") {
            sql = "SELECT SUM(distance) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \"" + datagroup + "\";";
        } else {
            sql = "SELECT SUM(distance) AS sum FROM " + Trip.tableName
                    + ";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
                totalTime = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("SQLAnalytic Error");
            System.out.println(e.getMessage());
        }

        return totalTime;
    }

    /**
     * Returns the total number of trips done by a certain gender can specify a data group to use
     * @param gender gender to search for ("Male", "Female", "Other")
     * @param datagroup datagroup to look for. if "" will match everything
     * @return integer of number of that gender
     */
    public static int totalGenderTrips(String gender, String datagroup)
    {
        if (gender.toLowerCase() == "m") {
            gender = "male";
        } else if (gender.toLowerCase() == "f" || gender.toLowerCase() == "w") {
            gender = "female";
        }
        int trips = 0;
        String sql;

        if(datagroup != ""){
            sql = "SELECT COUNT(gender) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \""+datagroup+"\" \n" +
                    " AND gender = \"" +gender+"\";";
        } else {
            sql = "SELECT COUNT(gender) AS sum FROM " + Trip.tableName
                    + " WHERE Lower(gender) = \"" +gender.toLowerCase()+"\";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
                trips = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("SQLAnalytic Error");
            System.out.println(e.getMessage());
        }

        return trips;
    }

    /**
     * Calculates the total number of trips done by a certain user type with specific datagroup
     * @param userType usertype to search for ("Customer", "Subscriber")
     * @param datagroup datagroup to look for. If "" will match all
     * @return integer of number of trips by usertype
     */
    public static int totalUserTypeTrips(String userType, String datagroup)
    {
        int trips = 0;
        String sql;

        if(datagroup != ""){
            sql = "SELECT COUNT(gender) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \""+datagroup+"\" \n" +
                    " AND LOWER(userType) = \"" +userType.toLowerCase()+"\";";
        } else {
            sql = "SELECT COUNT(gender) AS sum FROM " + Trip.tableName
                    + " WHERE Lower(userType) = \"" +userType.toLowerCase()+"\";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
                trips = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("SQLAnalytic Error totalUserTrips");
            System.out.println(e.getMessage());
        }

        return trips;
    }


    public static int totalTimeTrips(String startTime, String endTime,String datagroup)
    {
        int trips = 0;
        String sql;

        if(datagroup != ""){
            sql = "SELECT COUNT(*) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \""+datagroup+"\" \n" +
                    " AND STRFTIME('%H', startTime) < \"" +endTime+"\" \n"
                    + "AND STRFTIME('%H',startTime) > \"" + startTime+"\";";
        } else {
            sql = "SELECT COUNT(*) AS sum FROM " + Trip.tableName
                    + " WHERE startTime BETWEEN \""+startTime+"\" AND \""+endTime+"\";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
                trips = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("SQLAnalytic Error totalTimeTrips");
            System.out.println(e.getMessage());
        }

        return trips;
    }


    public static int totalDistTrips(double lowDist, double highDist, String datagroup)
    {
        int trips = 0;
        String sql;

        if(datagroup != ""){
            sql = "SELECT COUNT(*) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \""+datagroup+"\" \n" +
                    " AND distance < " +highDist+" \n"
                    + "AND distance > " + lowDist+";";
        } else {
            sql = "SELECT COUNT(*) AS sum FROM " + Trip.tableName
                    + " WHERE distance < " +highDist+" \n"
                    + "AND distance > " + lowDist+";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
                trips = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("SQLAnalytic Error totalDistTrips");
            System.out.println(e.getMessage());
        }

        return trips;
    }

    public static int totalDurTrips(int lowDur, int highDur, String datagroup)
    {
        int trips = 0;
        String sql;

        if(datagroup != ""){
            sql = "SELECT COUNT(*) AS sum FROM " + Trip.tableName
                    + " WHERE datagroup = \""+datagroup+"\" \n" +
                    " AND duration < " +highDur+" \n"
                    + "AND duration > " + lowDur+";";
        } else {
            sql = "SELECT COUNT(*) AS sum FROM " + Trip.tableName
                    + " WHERE duration < " +highDur+" \n"
                    + "AND duration > " + lowDur+";";
        }

        try (Connection conn = DatabaseHandler.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()){
                trips = rs.getInt("sum");
            }

        } catch (SQLException e){
            System.out.println("SQLAnalytic Error totalDurTrips");
            System.out.println(e.getMessage());
        }

        return trips;
    }
}
