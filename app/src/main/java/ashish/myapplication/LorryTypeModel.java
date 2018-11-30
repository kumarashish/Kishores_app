package ashish.myapplication;

import org.json.JSONObject;

public class LorryTypeModel {
    String code;
    String lorryType;
    public LorryTypeModel(JSONObject jsonObject)
    {
        try{
            code=jsonObject.isNull("Code")?"":jsonObject.getString("Code");
            lorryType=jsonObject.isNull("Lorrytype")?"":jsonObject.getString("Lorrytype").toUpperCase();
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public String getLorryType() {
        return lorryType;
    }
}
