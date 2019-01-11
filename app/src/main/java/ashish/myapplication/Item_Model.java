package ashish.myapplication;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 11-01-2019.
 */

public class Item_Model {
    String CODE;
    String NAME;
    public Item_Model(JSONObject jsonObject)
    {
        try{
            this.CODE=jsonObject.isNull("CODE")?"":jsonObject.getString("CODE");
            this.NAME=jsonObject.isNull("NAME")?"":jsonObject.getString("NAME");

        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getNAME() {
        return NAME;
    }

    public String getCODE() {
        return CODE;
    }
}
