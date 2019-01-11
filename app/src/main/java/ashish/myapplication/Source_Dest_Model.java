package ashish.myapplication;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 11-01-2019.
 */

public class Source_Dest_Model {
  String BCD;
    String CODE;
      String NAME;
    public Source_Dest_Model(JSONObject jsonObject)
    {
        try{
           this.BCD=jsonObject.isNull("BCD")?"":jsonObject.getString("BCD");
            this.CODE=jsonObject.isNull("CODE")?"":jsonObject.getString("CODE");
            this.NAME=jsonObject.isNull("NAME")?"":jsonObject.getString("NAME");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getBCD() {
        return BCD;
    }

    public String getCODE() {
        return CODE;
    }

    public String getNAME() {
        return NAME;
    }
}
