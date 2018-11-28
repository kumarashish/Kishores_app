package ashish.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class LorryPass extends Activity implements WebApiResponseCallback,View.OnClickListener {
    AppController controller;
    WebApiResponseCallback callback;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lorry_pass);
        callback=this;
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /* Write your logic here that will be executed when user taps next button */
                    if (search.getText().length() > 0) {
                        if (Utils.isNetworkAvailable(LorryPass.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            controller.getWebApiCall().postData(Common.getLorryPasspending, getRequestJSON().toString(), callback);
                        }
                    }else{
                        Utils.showToast(LorryPass.this,"Please enter employee id");
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
            jsonObject.put(Common.getBookingKeys[0],search.getText().toString());
            jsonObject.put(Common.getBookingKeys[1],"");
            jsonObject.put(Common.getBookingKeys[2],"");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSucess(final String value) {
        if (Utils.getStatus(value)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(Utils.jsonObject(value)!=null) {

                    }else{
                      ;
                        Utils.showToast(LorryPass.this, Utils.getMessage(value));
                    }
                    progressBar.setVisibility(View.GONE);

                }
            });
        }
    }

    @Override
    public void onError(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);

            }
        });
        Utils.showToast(LorryPass.this, Utils.getMessage(value));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
