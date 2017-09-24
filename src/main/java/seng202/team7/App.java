package seng202.team7;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        String basePath = new File("").getAbsolutePath();

        String url = "jdbc:sqlite:DataStorage.db";

        System.out.println( "Hello World! How is it going?" );
        System.out.println("Just testing a change");
        System.out.println("test commit Morgan");
        System.out.println("Aidan's test");
        System.out.println("Connor's test");
        System.out.println("Joshua's test");

        DatabaseRetriever databaseRetriever = new DatabaseRetriever();
        setRetailer(databaseRetriever.getRetailerList());


        //DatabaseTester.deleteTables();
        //DatabaseTester.createTables();
        //DatabaseTester.init();
        //Retailer retailer = new Retailer("Bob's Burgers", "New York", "3 New York Plaza", "", "NY", 10004, "F-Coffeehouse", "Casual Eating & Takeout", "a");
        //System.out.println(retailer.getLatitude() +);
        //System.out.println(retailer.getLongitude());

    }

    public static void setRetailer (ArrayList<Retailer> retailers) {
        DatabaseUpdater databaseUpdater=  new DatabaseUpdater();

        for(Retailer r: retailers) {
            databaseUpdater.deleteRetailer(r);
            if (r.getLatitude() == 0) {
                try {
                    r.addressToLATLONG(r.getZipCode() + ", " + r.getCity());
                } catch (java.lang.Exception e){
                    System.out.println("nope");
                }
                databaseUpdater.insertRetailer(r);

            }

        }
    }
}
