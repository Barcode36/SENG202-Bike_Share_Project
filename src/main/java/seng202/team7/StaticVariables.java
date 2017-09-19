package seng202.team7;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.text.SimpleDateFormat;
import java.lang.Math;

public class StaticVariables {
    public static int step = 50;
    private static double defaultDist = 1;
    public static int currentYear = 2017;
    public static SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
    public static SimpleDateFormat ift = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");


    public static String singleStringQuery(String tableName, String col, String toMatch)
    {
        return "SELECT obj FROM " + tableName + " WHERE LOWER("+col+") = \"" + toMatch.toLowerCase() +"\"";
    }

    public static  String doubleStringQuery(String tableName, String col1, String match1, String col2, String match2)
    {
        return "SELECT obj FROM " + tableName + " WHERE LOWER("+col1+") = \"" + match1.toLowerCase() +"\" AND LOWER("+col2+")\""+ match2.toLowerCase()+"\"";
    }

    public static String steppedQuery(String tableName, int start)
    {
        return "SELECT obj FROM " + tableName + " LIMIT " + start + "," + (start+step);

    }
    /**
     * SQL query for station by ID
     * @param stationID ID to search for
     * @return SQL Query finding station by id
     */
    public static String stationIDQuery(int stationID)
    {
        return "SELECT * FROM "+ Station.tableName + " WHERE stationid = " + stationID;
    }

    /**
     * SQL String used to find nearby retailers with default distance
     * @param lat lat of center
     * @param lon lon of center
     * @return SQL Query String
     */
    public static String retailerByLocation(double lat, double lon)
    {
        return retailerByLocation(lat, defaultDist,lon,defaultDist);
    }

    /**
     * SQL String used to find nearby retailers with specified distance
     * @param lat lat of center
     * @param latOffset difference in lat accepted
     * @param lon lon of centre
     * @param lonOffset difference in lon
     * @return SQL Query String
     */
    public static String retailerByLocation(double lat, double latOffset, double lon, double lonOffset)
    {
        return "SELECT obj FROM "+ Retailer.tableName + " WHERE (latitude BETWEEN ("+(lat-latOffset)+") AND ("+(lat+latOffset)+")) AND (longitude BETWEEN ("+(lon-lonOffset)+") AND ("+(lon+lonOffset)+"));";

    }

    /**
     * SQL String used to find nearby wifi with default distance
     * @param lat lat of center
     * @param lon lon of center
     * @return SQL Query String
     */
    public static String wifiByLocation(double lat, double lon)
    {
        return wifiByLocation(lat, defaultDist,lon,defaultDist);
    }
    /**
     * SQL String used to find nearby wifis with specified distance
     * @param lat lat of center
     * @param latOffset difference in lat accepted
     * @param lon lon of centre
     * @param lonOffset difference in lon
     * @return SQL Query String
     */
    public static String wifiByLocation(double lat, double latOffset, double lon, double lonOffset)
    {
        //System.out.println("latlow: " + (lat-latOffset) + " lathigh" + (lat+latOffset));
        return "SELECT obj FROM "+Wifi.tableName + " WHERE (latitude BETWEEN ("+(lat-latOffset)+") AND ("+(lat+latOffset)+")) AND (longitude BETWEEN ("+(lon-lonOffset)+") AND ("+(lon+lonOffset)+"));";

    }


    /**
     * Calculates distance in meters between two lat lon points
     * @param lat1 lat point1
     * @param lon1 lon point1
     * @param lat2 lat point2
     * @param lon2 lon point2
     * @return distance in meters as double
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2)
    {
        double radiusOfEarth = 6372; //radius of earth in km
        double dLat = degreeToRadian(lat2 - lat1);
        double dLon = degreeToRadian(lon2 - lon1);

        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double d = radiusOfEarth * c;
        return d;
    }

    /**
     * Converts degrees to radians. Helper for calculateDistance
     * @param degree degrees to convert
     * @return radian out as double
     */
    private static double degreeToRadian(double degree)
    {
        return degree* (Math.PI/180);
    }
}
