package ashish.myapplication;

public class Common {
    //public static String baseUrl="http://182.74.216.107/ds/api/employee/";
    public static String baseUrl="http://115.112.226.30/dhtcsky/";
    public static String login=baseUrl+"Login";
    public static String addEmployee=baseUrl+"setEmp";
    public static String getEmployee=baseUrl+"getEmp";
    public static String getBookLorryUrl=baseUrl+"LorryBooking";
    public static String getBookingReport=baseUrl+"LorryBookingReport";
    public static String getLorryType=baseUrl+"GetLorryTypes";
    public static String getLorryReachUrl=baseUrl+"LorryReach";
    public static String getLorryPassUrl=baseUrl+"LorryPass";
    public static String getLorryPasspending=baseUrl+"LorryPassPending";
    public static String getLorryArrangeUrl=baseUrl+"LorryArrange";




    public static String [] loginKeys={"User","Password"};
    public static String [] getEmpKeys={"ID"};
    public static String [] getBookingKeys={"BookingId","FromDate","ToDate"};
    public static String [] setEmpKeys={"Firstname","Lastname","Address","Dob","Salary"};
    public static String [] lorryBookingKeys={"LorryType","Item","From","To","Weight","Package","Consignee","Consignor","BookedBy","Freight","Cft","LoadType"};
    public static String[] getLorryReachKeys={"BookingId","LorryNo","PickedFrom","GoodRemark","ArrangeDt","Reportdate","Reporttime","Reportby"};
    public static String [] getLorryPassKeys={"BookingId","Lorryrate","Broker","MobileNo","Arrangedby","PassedBy"};
    public static String [] getLorryArrangeKeys={"BookingId", "Lorryrate", "Broker", "MobileNo", "Arrangedby"};
}
