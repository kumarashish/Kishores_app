package ashish.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

public class Login extends Activity implements View.OnClickListener,WebApiResponseCallback {
    AppController controller;
    @BindView(R.id.emailID)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.progress)
    ProgressBar progressbar;
    boolean isProgressbarVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = (AppController) getApplicationContext();

        if (controller.getManager().isUserLoggedIn() == true) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();

        } else {
            setContentView(R.layout.login);
            ButterKnife.bind(this);

            submit.setOnClickListener(this);
            emailId.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (emailId.getText().length() > 0) {
                    if (password.getText().length() > 0) {
//                        if ((emailId.getText().toString().equalsIgnoreCase("demo")) && (password.getText().toString().equalsIgnoreCase("demo"))) {
////                                new Handler().postDelayed(new Runnable() {
////                                    /*
////                                     * Showing splash screen with a timer. This will be useful when you
////                                     * want to show case your app logo / company
////                                     */
////                                    @Override
////                                    public void run() {
////                                        // This method will be executed once the timer is over
////                                        // Start your app main activity
////                                        Toast.makeText(Login.this, "Logged in sucessfully.", Toast.LENGTH_SHORT).show();
////
////                                        startActivity(new Intent(Login.this,MainActivity.class));
////                                        controller.getManager().setUserLoggedIn(true);
////                                        finish();
////                                    }
////                                }, 3000);
//
//                            controller
//                           progressbar.setVisibility(View.VISIBLE);
//                           submit.setVisibility(View.GONE);
//                        }else {
//                            Toast.makeText(Login.this, "Please enter valid  id and password", Toast.LENGTH_SHORT).show();
//                        }
                        controller.getWebApiCall().postData(Common.login, getRequestString().toString(), Login.this);
                        progressbar.setVisibility(View.VISIBLE);
                        isProgressbarVisible = true;
                        submit.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Please enter userid", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public JSONObject getRequestString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Common.loginKeys[0], emailId.getText().toString());
            jsonObject.put(Common.loginKeys[1], password.getText().toString());

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSucess(final String value) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isProgressbarVisible) {
                    isProgressbarVisible = false;
                    progressbar.setVisibility(View.GONE);
                }
                if(Utils.getStatus(value)==true) {

                       startActivity(new Intent(Login.this,MainActivity.class));
                                   controller.getManager().setUserLoggedIn(true);
                                   controller.getManager().setUserProfile(Utils.jsonObject(value));
                                   finish();
                }else{
                    Utils.showToast(Login.this,Utils.getMessage(value));
                    submit.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    @Override
    public void onError(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isProgressbarVisible) {
                    isProgressbarVisible = false;
                    progressbar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
