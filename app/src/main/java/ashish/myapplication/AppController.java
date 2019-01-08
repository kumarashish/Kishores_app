package ashish.myapplication;

import android.app.Application;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;

public class AppController extends Application {
    Validation validation;
    WebApiCall webApiCall;
    PrefManager manager;
    @Override
    public void onCreate() {
        super.onCreate();
        validation=new Validation(getApplicationContext());
        webApiCall=new WebApiCall(getApplicationContext());
        manager=new PrefManager(getApplicationContext());
        Common.makeFolder(String.valueOf(Environment.getExternalStorageDirectory()), "/DHTC");
        Common.sdCardPath = Environment.getExternalStorageDirectory() + "/DHTC";
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public Validation getValidation() {
        return validation;
    }

    public WebApiCall getWebApiCall() {
        return webApiCall;
    }

    public PrefManager getManager() {
        return manager;
    }
}
