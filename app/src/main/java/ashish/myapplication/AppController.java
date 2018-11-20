package ashish.myapplication;

import android.app.Application;
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
