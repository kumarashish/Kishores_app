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

    public LorryReportModel(JSONObject jsonObject) {
        try {
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
}
