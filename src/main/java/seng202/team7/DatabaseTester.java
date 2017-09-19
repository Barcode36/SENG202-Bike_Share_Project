package seng202.team7;

import java.util.ArrayList;

public class DatabaseTester {
    /**
     * Static method to test the database
     */
    public static void init()
    {
        //Create database
        //DatabaseHandler.createDatabase();

        deleteTables();
        createTables();
        DatabaseUpdater databaseUpdater = new DatabaseUpdater();
        addData(databaseUpdater);

        DatabaseRetriever databaseRetriever = new DatabaseRetriever();
        printTrip(databaseRetriever);
        Station s  = databaseRetriever.queryStation(StaticVariables.stationIDQuery(231)).get(0);
        s.print();
        System.out.println("test");

        for(Wifi w : databaseRetriever.getWifiList())
        {
            w.setCity("Changed");
            w.setBorough("WOW");
            w.setLatitude(0.789);
            databaseUpdater.updateWifi(w);
        }
        System.out.println("after change");
        for(Wifi w : databaseRetriever.getWifiList())
        {
            System.out.println("testing update: " + w.getCity());
        }

        long sumDuration = SQLAnalytics.totalGroupTripDuration("test");
        long sumDistance = SQLAnalytics.totalGroupTripDistance("test");
        int genderTripsF = SQLAnalytics.totalGenderTrips("Female","");
        int genderTripsM = SQLAnalytics.totalGenderTrips("Male","");
        int userTrips = SQLAnalytics.totalUserTypeTrips("Customer","");
        //System.out.println(sumDuration);
        //System.out.println(sumDistance);
        System.out.println("By gender:");
        System.out.println(genderTripsF);
        System.out.println(genderTripsM);
        //System.out.println(userTrips);



        databaseRetriever.testQueryTrip();

        System.out.println("BY TIME: "+SQLAnalytics.totalTimeTrips("00:00:00","23:00:00",""));
        System.out.println("BY Dist: "+SQLAnalytics.totalDistTrips(0,10000,""));
        System.out.println("BY Dur: "+SQLAnalytics.totalDurTrips(0,10000,""));



        /*
        for(Retailer r : databaseRetriever.QueryRetailer("SELECT obj FROM retailer WHERE datagroup = \"test\""))
        {
            r.print();
        }
        */

        //printWifi(databaseRetriever);
        //printRetailer(databaseRetriever);



    }

    /**
     * Deletes all the tables
     */
    public static void deleteTables()
    {
        DatabaseHandler.deleteTable(Wifi.tableName);
        DatabaseHandler.deleteTable(Station.tableName);
        DatabaseHandler.deleteTable(Retailer.tableName);
        DatabaseHandler.deleteTable(Trip.tableName);
    }

    /**
     * Creates all the tables
     */
    public static void createTables()
    {
        DatabaseHandler.createTable(Wifi.tableName, Wifi.tableCreation);
        DatabaseHandler.createTable(Station.tableName, Station.tableCreation);
        DatabaseHandler.createTable(Retailer.tableName, Retailer.tableCreation);
        DatabaseHandler.createTable(Trip.tableName, Trip.tableCreation);
    }

    /**
     * Adds an arraylist of datatypes
     * @param databaseUpdater databaseupdater object with connection to the current database
     */
    public static void addData(DatabaseUpdater databaseUpdater)
    {
        ArrayList<Data> data = new ArrayList<Data>();

        Wifi w1 = new Wifi("BX", "Limited free","Alcatel","5th Ave","NY","Alcatel Hotspot","","",234.324,324.554);
        Wifi w2 = new Wifi("SI", "Free","AlcatEl","5th Ave","NY","Alcatel Hotspot","","",234.354,324.484);
        Wifi w3 = new Wifi("MN", "Subscription","Alcatekl","5th Ave","NY","Alcatel Hotspot","","",234.324,324.554);

        Station s1 = new Station(231,"5th ave", "CitiBike", 2367.987, 394.98);
        Station s2 = new Station(3241,"34 square", "Bike Shah", 2387.987, 384.98);

        Retailer r1 = new Retailer("McD's Lower MN", "New York", "5th ave", "23", "NY", 2344, "F", "Phast Phood", "test" );
        Retailer r2 = new Retailer("McD's Upper BO", "New York", "6th ave", "23", "NY", 2324, "P", "Phast Phood", "test" );
        Retailer r3 = new Retailer("McD's Upper BO", "New York", "7th ave", "23", "NY", 2333, "P", "Phast Phood", "test" );
        Retailer r4 = new Retailer("McD's Upper BO", "New York", "8th ave", "23", "NY", 2312, "S", "Phast Phood", "test1" );
        Retailer r5 = new Retailer("McD's Upper BO", "New York", "9th ave", "23", "NY", 2322, "F", "Phast Phood", "test1" );



        Trip t1 = new Trip(s1,s2,4345,"2015-10-01 05:22:42","2015-10-01 00:38:42", "customer", 1990, 0, "kl");
        Trip t2 = new Trip(s2,s1,4345,"2015-10-01 00:20:42","2015-10-01 00:29:42", "CUSTOMER", 1934, 1, "test");
        Trip t3 = new Trip(s1,s2,4345,"2015-10-01 00:22:42","2015-10-01 00:38:42", "Subscriber", 1990, 2, "test");


        data.add(w1);
        data.add(w2);
        data.add(w3);

        data.add(s1);
        data.add(s2);

        data.add(r1);
        data.add(r2);
        data.add(r3);
        data.add(r4);
        data.add(r5);

        data.add(t1);
        data.add(t2);
        data.add(t3);

        databaseUpdater.addData(data);



    }

    /**
     * prints wifis
     * @param databaseRetriever
     */
    private static void printWifi(DatabaseRetriever databaseRetriever)
    {
        for(Wifi w :databaseRetriever.getWifiList()){
            w.print();
        }
    }

    /**
     * prints retailer
     * @param databaseRetriever
     */
    private static void printRetailer(DatabaseRetriever databaseRetriever)
    {
        for(Retailer r : databaseRetriever.getRetailerList()){
            r.print();
        }
    }

    private static void printTrip(DatabaseRetriever databaseRetriever)
    {
        for(Trip t : databaseRetriever.getTripList()){
            t.testPrint();
        }
    }


}
