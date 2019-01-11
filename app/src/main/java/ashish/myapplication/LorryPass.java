package ashish.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    @BindView(R.id.table)
    LinearLayout table;
    @BindView(R.id.content)
            LinearLayout content;
    @BindView(R.id.bookingId)
            TextView bookingId;
    ArrayList<Lorry_PassModel> passesList=new ArrayList<>();
    int apiCall=0;
    int getData=1,approve=2;
    LorryReportModel model;
    @BindView(R.id.consiner)
    TextView consiner;
    @BindView(R.id.consignee)
    TextView consine;
    @BindView(R.id.type)
    TextView lorryType;
    @BindView(R.id.item)
    TextView item;
    @BindView(R.id.s_d)
    TextView source_dest;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.pkg)
    TextView pckg;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.cft)
    TextView cft;
    @BindView(R.id.load_type)
    TextView load_type;
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
                            apiCall=getData;
                            controller.getWebApiCall().postData(Common.getLorryPasspending, getRequestJSON().toString(), callback);
                            Thread T =new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String value=  controller.getWebApiCall().postData(Common.getBookingReport, getRequestJSON().toString());
                                    if (Utils.jsonObject(value) != null) {
                                        model = new LorryReportModel(Utils.jsonObject(value));
                                        clearAll();
                                        setValue();
                                    } else {
                                        clearAll();
                                    }
                                }
                            });
                            T.start();


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

    public void clearAll()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        bookingId.setText("");
        lorryType.setText("");
        item.setText("");
        source_dest.setText("");
        weight.setText("");
        pckg.setText("");
        consiner.setText("");
        consine.setText("");
        load_type.setText("");
        cft.setText("");
        freight.setText("");
            }
        });
    }
    public void setValue()
    {runOnUiThread(new Runnable() {
        @Override
        public void run() {
            bookingId.setText(Integer.toString(model.getId()));
            lorryType.setText(model.getLorrytype());;
            item.setText(model.getItem());;
            source_dest.setText(model.Bookfrom+" - "+model.getBookto());;
            weight.setText(model.getWeight() +" kgs");;
            pckg.setText(model.getPackage());
            consiner.setText(model.getConsignor());
            consine.setText(model.getConsignee());
            load_type.setText(model.getLoadtype());
            freight.setText(model.getFreight());
            cft.setText(model.getCft());
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
                    switch (apiCall) {
                        case 1:
                        JSONArray passesArray = Utils.jsonArrayy(value);
                        if ((passesArray != null) && (passesArray.length() > 0)) {
                            passesList.clear();
                            for (int i = 0; i < passesArray.length(); i++) {
                                try {
                                    passesList.add(new Lorry_PassModel(passesArray.getJSONObject(i)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (passesList.size() > 0) {
                                updateTable();
                            }else{
                                content.removeAllViews();
                            }
                        } else {
                            Utils.showToast(LorryPass.this, Utils.getMessage(value));
                        }
                        break;
                        case 2:
                            Utils.showToast(LorryPass.this, "You have sucessfully approved pass");
                            finish();
                            break;
                    }
                    progressBar.setVisibility(View.GONE);

                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    content.removeAllViews();
                }
            });

            Utils.showToast(LorryPass.this, Utils.getMessage(value));
        }
    }

    public boolean getStatus() {
        for (int i = 0; i < passesList.size(); i++) {
            if (passesList.get(i).getApproved() == true) {
                return true;
            }
        }
        return false;
    }
    private void updateTable() {
        content.removeAllViews();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int count=1;
                final Boolean isAnyRateAPproved=getStatus();
                for(int i=0;i<passesList.size();i++)
                {
                    Lorry_PassModel model=passesList.get(i);
                    View contentview = getLayoutInflater().inflate(R.layout.pass_row, null, false);
                    TextView sno=(TextView) contentview.findViewById(R.id.sno);
                    final TextView rate=(TextView) contentview.findViewById(R.id.rate);
                    final TextView broker=(TextView) contentview.findViewById(R.id.name);
                    final TextView mobile=(TextView) contentview.findViewById(R.id.mobile);
                    final TextView approvedby=(TextView) contentview.findViewById(R.id.passedby);
                    final TextView status=(TextView) contentview.findViewById(R.id.status);
                    sno.setText(Integer.toString(count));
                    rate.setText(model.getLorryrate());
                    broker.setText(model.getBroker());
                    mobile.setText(model.getMobileno());
                    approvedby.setText(model.getArrangedby());
                    if(model.getApproved()) {
                       status.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        status.setText("Approved");
                    }
                    status.setId(i);

                        status.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isAnyRateAPproved == false) {
                                    if (Utils.isNetworkAvailable(LorryPass.this)) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        apiCall = approve;
                                        table.setVisibility(View.GONE);
                                        controller.getWebApiCall().postData(Common.getLorryPassUrl, getApprovePassJSON(rate.getText().toString(), broker.getText().toString(), mobile.getText().toString(), approvedby.getText().toString()).toString(), callback);

                                    }
                                } else {
                                    Utils.showToast(LorryPass.this, "You have already approved pass");
                                }

                            }
                        });

                    content.addView(contentview);
                    count++;

                }
                table.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);

            }
        });
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

public JSONObject getApprovePassJSON(String rate,String broker,String mobile,String arrangedBy)
{
    JSONObject jsonObject=new JSONObject();
    try{

        jsonObject.put(Common.getLorryPassKeys[0],search.getText().toString());
        jsonObject.put(Common.getLorryPassKeys[1],rate);
        jsonObject.put(Common.getLorryPassKeys[2],broker);
        jsonObject.put(Common.getLorryPassKeys[3],mobile);
        jsonObject.put(Common.getLorryPassKeys[4],arrangedBy);
        jsonObject.put(Common.getLorryPassKeys[5],controller.getManager().getUserId());
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
}
