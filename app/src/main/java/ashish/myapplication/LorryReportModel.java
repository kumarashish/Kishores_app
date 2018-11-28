package ashish.myapplication;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 23-11-2018.
 */

public class LorryReportModel {
    String Lorrytype;
    String Item;
    String Bookfrom;
    String Bookto;
    String Weight;
    String Package;
    int id;
    String Consignor;
    String Consignee;
    String Bookby;
    String Bookdtm;
    String Lorryrate;
    String Broker;
    String Mobileno;
    String Passedby;
    String Arrangedby;
    String Paseddtm;
    String Lorryno;
    String Arrangtime;
    String Reporttime;
    String Loryby;
    String Lorrydtm;
    public LorryReportModel(JSONObject jsonObject) {
        try {
            this.id=jsonObject.isNull("Srno")?0:jsonObject.getInt("Srno");
            this.Consignor = jsonObject.isNull("Consignor") ? "" : jsonObject.getString("Consignor");
            this.Consignee = jsonObject.isNull("Consignee") ? "" : jsonObject.getString("Consignee");
            this.Bookby = jsonObject.isNull("Bookby") ? "" : jsonObject.getString("Bookby");
            this.Bookdtm = jsonObject.isNull("Bookdtm") ? "" : jsonObject.getString("Bookdtm");
            this.Lorryrate = jsonObject.isNull("Lorryrate") ? "" : jsonObject.getString("Lorryrate");
            this.Broker = jsonObject.isNull("Broker") ? "" : jsonObject.getString("Broker");
            this.Mobileno = jsonObject.isNull("Mobileno") ? "" : jsonObject.getString("Mobileno");
            this.Passedby = jsonObject.isNull("Passedby") ? "" : jsonObject.getString("Passedby");
            this.Arrangedby = jsonObject.isNull("Arrangedby") ? "" : jsonObject.getString("Arrangedby");
            this.Paseddtm = jsonObject.isNull("Paseddtm") ? "" : jsonObject.getString("Paseddtm");
            this.Lorryno = jsonObject.isNull("Lorryno") ? "" : jsonObject.getString("Lorryno");
            this.Arrangtime = jsonObject.isNull("Arrangtime") ? "" : jsonObject.getString("Arrangtime");
            this.Reporttime = jsonObject.isNull("Reporttime") ? "" : jsonObject.getString("Reporttime");
            this.Loryby = jsonObject.isNull("Loryby") ? "" : jsonObject.getString("Loryby");
            this.Lorrytype = jsonObject.isNull("Lorrytype") ? "" : jsonObject.getString("Lorrytype");
            this.Item = jsonObject.isNull("Item") ? "" : jsonObject.getString("Item");
            this.Bookfrom = jsonObject.isNull("Bookfrom") ? "" : jsonObject.getString("Bookfrom");
            this.Bookto = jsonObject.isNull("Bookto") ? "" : jsonObject.getString("Bookto");
            this.Weight = jsonObject.isNull("Weight") ? "" : jsonObject.getString("Weight");
            this.Package = jsonObject.isNull("Package") ? "" : jsonObject.getString("Package");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getBookfrom() {
        return Bookfrom;
    }

    public String getBookto() {
        return Bookto;
    }

    public String getItem() {
        return Item;
    }

    public String getLorrytype() {
        return Lorrytype;
    }

    public String getPackage() {
        return Package;
    }

    public String getWeight() {
        return Weight;
    }

    public int getId() {
        return id;
    }

    public String getConsignee() {
        return Consignee;
    }

    public String getArrangedby() {
        return Arrangedby;
    }

    public String getArrangtime() {
        return Arrangtime;
    }

    public String getBookby() {
        return Bookby;
    }

    public String getBookdtm() {
        return Bookdtm;
    }

    public String getBroker() {
        return Broker;
    }

    public String getConsignor() {
        return Consignor;
    }

    public String getLorrydtm() {
        return Lorrydtm;
    }

    public String getLorryno() {
        return Lorryno;
    }

    public String getLorryrate() {
        return Lorryrate;
    }

    public String getLoryby() {
        return Loryby;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public String getPaseddtm() {
        return Paseddtm;
    }

    public String getPassedby() {
        return Passedby;
    }

    public String getReporttime() {
        return Reporttime;
    }
}
