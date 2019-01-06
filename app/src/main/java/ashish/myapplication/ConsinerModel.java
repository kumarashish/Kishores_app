package ashish.myapplication;

import org.json.JSONObject;

public class ConsinerModel {
    String name;
    String bcd,party,code;
    public ConsinerModel(JSONObject jsonObject)
    {
        try{
            this.name=jsonObject.isNull("NAME")?"":jsonObject.getString("NAME");
            this.bcd=jsonObject.isNull("BCD")?"":jsonObject.getString("BCD");
            this.party=jsonObject.isNull("PARTY")?"":jsonObject.getString("PARTY");
            this.code=jsonObject.isNull("CODE")?"":jsonObject.getString("CODE");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

    }

    public String getCode() {
        return code;
    }

    public String getBcd() {
        return bcd;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }
}

