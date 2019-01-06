package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by ashish.kumar on 06-07-2018.
 */

public class Utils {
    static String strAdd = "";
    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

public static void showToast(final Activity activity,final String message)
{
    activity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(activity,  message, Toast.LENGTH_SHORT).show();
        }
    });
}
    public static boolean getStatus(String value)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);
            return jsonObject.getBoolean("Status");
        }catch (Exception ex){
            ex.fillInStackTrace();
        }
        return false;
    }

    public static JSONObject jsonObject(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            return jsonArray.getJSONObject(0);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }
    public static JSONArray jsonArrayy(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            return jsonArray;
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }
    public static String getMessage(String value)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);
            return jsonObject.getString("Message");
        }catch (Exception ex){
            ex.fillInStackTrace();
        }
        return "";
    }

    public static String getBookingId(String value) {
        try{
            JSONObject jsonObject=new JSONObject(value);
            return jsonObject.getString("Data");
        }catch (Exception ex){
            ex.fillInStackTrace();
        }
        return "";
    }
}
