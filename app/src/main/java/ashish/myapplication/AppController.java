package ashish.myapplication;

import android.app.Application;

public class AppController extends Application {
    Validation validation;
    WebApiCall webApiCall;
    @Override
    public void onCreate() {
        super.onCreate();
        validation=new Validation(getApplicationContext());
        webApiCall=new WebApiCall(getApplicationContext());
    }

    public Validation getValidation() {
        return validation;
    }

    public WebApiCall getWebApiCall() {
        return webApiCall;
    }
}
