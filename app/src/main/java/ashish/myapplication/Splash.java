package ashish.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

public class Splash extends Activity {
    private static int SPLASH_TIME_OUT = 1500;
    AppController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        controller=(AppController) getApplicationContext();
            runThread();
        }

        public void runThread()
        {
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    launchHomeScreen();
                }
            }, SPLASH_TIME_OUT);
        }

        public void launchHomeScreen() {
            Intent i;
            if (controller.getManager().isUserLoggedIn() == true) {
                i = new Intent(Splash.this,  MainActivity.class);

            } else {
                i = new Intent(Splash.this, Login.class);
           }
            startActivity(i);
            finish();
        }
    }

