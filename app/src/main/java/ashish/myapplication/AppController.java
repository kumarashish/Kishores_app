package ashish.myapplication;

import android.app.Application;

public class AppController extends Application {
    Validation validation;
    @Override
    public void onCreate() {
        super.onCreate();
        validation=new Validation(getApplicationContext());
    }

    public Validation getValidation() {
        return validation;
    }
}
