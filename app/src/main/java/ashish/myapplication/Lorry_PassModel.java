package ashish.myapplication;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 29-11-2018.
 */

public class Lorry_PassModel {
    String Lorryrate;
    String Broker;
    String Mobileno;
    String Arrangedby;
    Boolean approved=false;
    public Lorry_PassModel(JSONObject jsonObject)
    {
        try{
           this.Lorryrate=jsonObject.isNull("Lorryrate")?"":jsonObject.getString("Lorryrate");
            this.Broker=jsonObject.isNull("Broker")?"":jsonObject.getString("Broker");
            this.Mobileno=jsonObject.isNull("Mobileno")?"":jsonObject.getString("Mobileno");
            this.Arrangedby=jsonObject.isNull("Arrangedby")?"":jsonObject.getString("Arrangedby");
            this.approved=jsonObject.isNull("Approved")?false:jsonObject.getBoolean("Approved");
        }catch (Exception ex)
        {

            ex.fillInStackTrace();
        }
    }

    public Boolean getApproved() {
        return approved;
    }

    public String getArrangedby() {
        return Arrangedby;
    }

    public String getBroker() {
        return Broker;
    }

    public String getLorryrate() {
        return Lorryrate;
    }

    public String getMobileno() {
        return Mobileno;
    }
}
