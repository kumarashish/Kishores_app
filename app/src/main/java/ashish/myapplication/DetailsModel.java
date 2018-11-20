package ashish.myapplication;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 20-11-2018.
 */

public class DetailsModel {
    String name;
    String dob;
    String address;
    String salary;
    String id;

    public DetailsModel(JSONObject jsonObject) {
        try {
            this.id=jsonObject.isNull("ID") ? "" : jsonObject.getString("ID");
            this.name = (jsonObject.isNull("Firstname") ? "" : jsonObject.getString("Firstname")) + " " + (jsonObject.isNull("Lastname") ? "" : jsonObject.getString("Lastname"));
            this.dob = (jsonObject.isNull("Dob") ? "" : jsonObject.getString("Dob"));
            this.address = (jsonObject.isNull("Address") ? "" : jsonObject.getString("Address"));
            this.salary = (jsonObject.isNull("Salary") ? "" : jsonObject.getString("Salary"));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }

    public String getSalary() {
        return salary;
    }
}
