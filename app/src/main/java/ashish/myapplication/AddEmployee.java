package ashish.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEmployee extends Activity implements View.OnClickListener,WebApiResponseCallback {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.fname)
    EditText fname;
    @BindView(R.id.lname)
    EditText lname;
    @BindView(R.id.address)
    EditText adddress;
    @BindView(R.id.salary)
    EditText salary;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    AppController controller;
    WebApiResponseCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        callback=this;
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if ((fname.getText().length() > 0) && (lname.getText().length() > 0) && (adddress.getText().length() > 0) && (adddress.getText().length() > 10) && (salary.getText().length() > 0)) {
                    if (Utils.isNetworkAvailable(AddEmployee.this)) {
                        controller.getWebApiCall().postData(Common.addEmployee, getRequestJSON().toString(), callback);
                        progressBar.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.GONE);
                    }

                } else {
                    if (fname.getText().length() == 0) {
                        Toast.makeText(this, "Please enter Fist Name", Toast.LENGTH_SHORT).show();
                    } else if (lname.getText().length() == 0) {
                        Toast.makeText(this, "Please enter Last Name", Toast.LENGTH_SHORT).show();
                    } else if (adddress.getText().length() == 0) {
                        Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
                    } else if (adddress.getText().length() < 10) {
                        Toast.makeText(this, "Address length should be greater than 10 letter", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please enter salary", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }
    public JSONObject getRequestJSON()
    {
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.setEmpKeys[0],fname.getText().toString());
            jsonObject.put(Common.setEmpKeys[1],lname.getText().toString());
            jsonObject.put(Common.setEmpKeys[2],adddress.getText().toString());
            jsonObject.put(Common.setEmpKeys[3],salary.getText().toString());
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    public void clearAll() {
        fname.setText("");
        lname.setText("");
        adddress.setText("");
        salary.setText("");
    }
    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Utils.getStatus(value)) {
                    clearAll();
                }
                Utils.showToast(AddEmployee.this,Utils.getMessage(value));
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onError(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }
        });
        Utils.showToast(AddEmployee.this,Utils.getMessage(value));
    }
}
