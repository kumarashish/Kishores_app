package ashish.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeDetails extends Activity implements View.OnClickListener ,WebApiResponseCallback{
    @BindView(R.id.back)
    Button back;
    AppController controller;
    @BindView(R.id.progress)
    ProgressBar progressBar;
@BindView(R.id.serachId)
EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emplyee_info);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /* Write your logic here that will be executed when user taps next button */

if(search.getText().length()>0)
{
    if(Utils.isNetworkAvailable(EmployeeDetails.this))
    {
        progressBar.setVisibility(View.VISIBLE);
        controller.getWebApiCall().postData(Common.getEmployee, getRequestJSON().toString(), this);
    }
}
                    handled = true;
                }
                return handled;
            }
        });


    }
public JSONObject getRequestJSON()
{JSONObject jsonObject=new JSONObject();
    try{
        jsonObject.put(Common.getEmpKeys[0],search.getText().toString());
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
    public void onSucess(String value) {
     progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onError(String value) {
        progressBar.setVisibility(View.GONE);
    }
}