package ashish.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class Login extends Activity implements View.OnClickListener,WebApiResponseCallback{
    AppController controller;
    @BindView(R.id.emailID)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.progress)
    ProgressBar progressbar;
    boolean isProgressbarVisible=false;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit:
                if (controller.getValidation().isEmailIdValid(emailId)) {
                    if (password.getText().length() > 0) {
                        if ((emailId.getText().toString().equalsIgnoreCase("demo@gmail.com")) && (password.getText().toString().equalsIgnoreCase("demo"))) {
                                new Handler().postDelayed(new Runnable() {
                                    /*
                                     * Showing splash screen with a timer. This will be useful when you
                                     * want to show case your app logo / company
                                     */
                                    @Override
                                    public void run() {
                                        // This method will be executed once the timer is over
                                        // Start your app main activity
                                        Toast.makeText(Login.this, "Logged in sucessfully.", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(Login.this,MainActivity.class));
                                        controller.getManager().setUserLoggedIn(true);
                                        finish();
                                    }
                                }, 3000);
                           progressbar.setVisibility(View.VISIBLE);
                           submit.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(Login.this, "Please enter valid email id and password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

    }

    @Override
    public void onSucess(String value) {
        if(isProgressbarVisible)
        {isProgressbarVisible=false;
            progressbar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onError(String value) {
        if(isProgressbarVisible)
        {isProgressbarVisible=false;
            progressbar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        }
    }
}
