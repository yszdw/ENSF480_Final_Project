import java.sql.*;

/**
 * Database Management System, handles any interaction between the program and the database
 *
 * DBMS instance - The global instance for our database manager (See Singleton Design Pattern)
 * Connection dbConnect - SQL connection object for initializing connection with database
 * ResultSet results - The results of queries will be in this object
 *
 */
public class DBMS {

    private static DBMS instance;
    private Connection dbConnect;
    private ResultSet results;

    /** DBMS Constructor
     *
     *  Called only when an instance does not yet exist, creates onnection to local database
     *
     */
    private DBMS() throws SQLException{
        //the connection info here will need to be changed depending on the user
        dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/flight_reservations", "root", "AbXy219!");
    }
    /** DBMS Constructor
     *
     * Ensures only one instance of database manager exists at once, returns DBMS object
     *
     */
    public static DBMS getDBMS() throws SQLException{
        if(instance == null){
            System.out.println("Database instance created");
            instance = new DBMS();
        }
        return instance;
    }

    /** closeConnection
     *
     *  Closes database connection, use at end of program
     *
     */
    public void closeConnection() throws SQLException{
        dbConnect.close();
        results.close();


    }

    /**
     * A sample test to verify connection
     */
    public void test_SQL() throws SQLException{

        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Users");

        while(results.next()){
            System.out.println(results.getString("Address"));
        }

    }
    public static void main(String args[]) throws SQLException{

        DBMS connect = getDBMS();
        connect.test_SQL();
        DBMS thing = getDBMS();
        thing.test_SQL();
    }
}