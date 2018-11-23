package ashish.myapplication;

public class Common {
    //public static String baseUrl="http://182.74.216.107/ds/api/employee/";
    public static String baseUrl="http://115.112.226.30/dhtcsky/";
    public static String login=baseUrl+"Login";
    public static String addEmployee=baseUrl+"setEmp";
    public static String getEmployee=baseUrl+"getEmp";
    public static String getBookLorryUrl=baseUrl+"LorryBooking";
    public static String getBookingReport=baseUrl+"LorryBookingReport";

    public static String [] loginKeys={"User","Password"};
    public static String [] getEmpKeys={"ID"};
    public static String [] getBookingKeys={"BookingId"};
    //dob="01/01/1980"
    public static String [] setEmpKeys={"Firstname","Lastname","Address","Dob","Salary"};
    public static String [] lorryBookingKeys={"LorryType","Item","From","To","Weight","Package"};
}
