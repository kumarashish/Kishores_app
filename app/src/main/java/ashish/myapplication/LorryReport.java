package ashish.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashish.kumar on 23-11-2018.
 */

public class LorryReport extends Activity implements View.OnClickListener  ,WebApiResponseCallback{
    public static String headingValue = "";
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.heading)
    TextView heading;
    AppController controller;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.serachId)
    EditText search;
    WebApiResponseCallback callback;
    LorryReportModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lorry_report);
        callback=this;
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        heading.setText(headingValue);

            search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /* Write your logic here that will be executed when user taps next button */
                        if (search.getText().length() > 0) {
                            if (Utils.isNetworkAvailable(LorryReport.this)) {
                                progressBar.setVisibility(View.VISIBLE);
                                controller.getWebApiCall().postData(Common.getBookingReport, getRequestJSON().toString(), callback);
                            }
                        }else{
                            Utils.showToast(LorryReport.this,"Please enter booking id");
                        }
                        handled = true;
                    }
                    return handled;
                }
            });
        }
    public void clearAll()
    {
//        sno.setText("");
//        name.setText("");
//        dob.setText("");
//        address.setText("");
//        salary.setText("");
    }
    public void setValue()
    {
//        sno.setText(model.getId());
//        name.setText(model.getName());
//        dob.setText(model.getDob());
//        address.setText(model.getAddress());
//        salary.setText(model.getSalary());
    }
    public JSONObject getRequestJSON()
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.getBookingKeys[0],search.getText().toString());
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

    @Override
    public void onSucess(final String value) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Utils.getStatus(value)) {
                    if (Utils.jsonObject(value) != null) {
                        model=new LorryReportModel(Utils.jsonObject(value));
                        clearAll();
                        setValue();
                    } else {
                        clearAll();
                        Utils.showToast(LorryReport.this, Utils.getMessage(value));
                    }
                } else {
                    Utils.showToast(LorryReport.this, Utils.getMessage(value));
                }
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onError(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clearAll();
                progressBar.setVisibility(View.GONE);

            }
        });
        Utils.showToast(LorryReport.this, Utils.getMessage(value));
    }
}
