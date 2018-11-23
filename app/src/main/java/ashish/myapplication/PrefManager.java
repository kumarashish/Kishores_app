package ashish.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;


/**
 * Created by Ashish.Kumar on 16-01-2018.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MyApp";
    private static final String LoggedIn = "MyAppUserLoggedIn";

    private static final String UserToken = "MyAppUSerToken";
    private static final String rememberId = "MyAppUSerRemId";
    private static final String rememberpassword = "MyAppUSerRemPass";
     private static final String userId="userId";
    private static final String name="name";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void clearPreferences() {
        editor.clear().commit();
    }
    public void setUserLoggedIn(boolean isloggedIn) {
        editor.putBoolean(LoggedIn , isloggedIn);
        editor.commit();
    }
    public boolean isUserLoggedIn()
    {
        return pref.getBoolean(LoggedIn,false);
    }

    public void setUserProfile(JSONObject jsonObject) {
        try {
            editor.putString(userId, jsonObject.isNull("Code") ? "" : jsonObject.getString("Code"));
            editor.putString(name, jsonObject.isNull("Name") ? "" : jsonObject.getString("Name"));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }
 public String getUserId()
 {
     return pref.getString(userId,"");
 }
    public String getName()
    {
        return pref.getString(name,"");
    }

}
