package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.report)
    LinearLayout report;

    @BindView(R.id.idView)
    LinearLayout idView;
    @BindView(R.id.dateView)
    LinearLayout dateView;
    @BindView(R.id.startDate)
    Button startDate;
    @BindView(R.id.endDate)
    Button endDate;
    @BindView(R.id.id_search)
    Button id_search;
    @BindView(R.id.date_search)
    Button date_search;
    @BindView(R.id.bookingId)
    TextView bookingId;
    @BindView(R.id.consiner)
    TextView consiner;
    @BindView(R.id.consignee)
    TextView consine;
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.broaker)
    TextView broaker;
    @BindView(R.id.mobilenumber)
    TextView mobilenumber;
    @BindView(R.id.lorrynumber)
    TextView lorryNumber;

    @BindView(R.id.arranged_time)
    TextView arranged_time;
    @BindView(R.id.reporting_time)
    TextView reporting_time;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.addDelivery)
    Button delivery;
AlertDialog dialog;
int apiCall=0;
int searchBooking=1,arrangeLorry=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lorry_report);
        callback=this;
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        id_search.setOnClickListener(this);
        date_search.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        delivery.setOnClickListener(this);
        add.setOnClickListener(this);
        heading.setText(headingValue);

            search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /* Write your logic here that will be executed when user taps next button */
                        if (search.getText().length() > 0) {
                            if (Utils.isNetworkAvailable(LorryReport.this)) {
                                report.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                apiCall=searchBooking;
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

        bookingId.setText("");
       lorryType.setText("");
        item.setText("");
        source_dest.setText("");
         weight.setText("");
        pckg.setText("");
        consiner.setText("");
        consine.setText("");

                rate.setText("");
        broaker.setText("");
                mobilenumber.setText("");
        lorryNumber.setText("");
                arranged_time.setText("");
        reporting_time.setText("");
    }
    public void setValue()
    {
        bookingId.setText(Integer.toString(model.getId()));
        lorryType.setText(model.getLorrytype());;
        item.setText(model.getItem());;
        source_dest.setText(model.Bookfrom+" - "+model.getBookto());;
        weight.setText(model.getWeight());;
        pckg.setText(model.getPackage());
        report.setVisibility(View.VISIBLE);
        SpannableString content = new SpannableString( bookingId.getText().toString());
        content.setSpan(new UnderlineSpan(), 0,bookingId.getText().length(), 0);
        bookingId.setText(content);
        consiner.setText(model.getConsignor());
                consine.setText(model.getConsignee());

    rate.setText(model.getLorryrate());
    broaker.setText(model.getBroker());
    mobilenumber.setText(model.getMobileno());


                lorryNumber.setText(model.getLorryno());
        arranged_time.setText(model.getArrangtime());

        reporting_time.setText(model.getReporttime());


        if((model.getPassedby().length()>0)&&(model.getReporttime().length()==0))
        {
            add.setVisibility(View.GONE);
            delivery.setVisibility(View.VISIBLE);

        }else if((model.getPassedby().length()>0)&&(model.getReporttime().length()>0)){
            add.setVisibility(View.GONE);
            delivery.setVisibility(View.GONE);
        }else{
            add.setVisibility(View.VISIBLE);
            delivery.setVisibility(View.GONE);
        }
    }
    public JSONObject getRequestJSON()
    {JSONObject jsonObject=new JSONObject();
        try{
         jsonObject.put(Common.getBookingKeys[0],search.getText().toString());
          //  jsonObject.put(Common.getBookingKeys[0],);
            jsonObject.put(Common.getBookingKeys[1],"");
            jsonObject.put(Common.getBookingKeys[2],"");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    public JSONObject getLorrryArrangeRequestJSON(String rate,String broker,String mobilenumber)
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.getLorryArrangeKeys[0],search.getText().toString());
            jsonObject.put(Common.getLorryArrangeKeys[1],rate);
            jsonObject.put(Common.getLorryArrangeKeys[2],broker);
            jsonObject.put(Common.getLorryArrangeKeys[3],mobilenumber);
            jsonObject.put(Common.getLorryArrangeKeys[4],controller.manager.getUserId());
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
            case R.id.id_search:
                id_search.setBackgroundColor(getResources().getColor(R.color.purple));
                id_search.setTextColor(getResources().getColor(R.color.white));
                date_search.setBackgroundColor(getResources().getColor(R.color.white));
                date_search.setTextColor(getResources().getColor(R.color.black));
                idView.setVisibility(View.VISIBLE);
                dateView.setVisibility(View.GONE);
                break;
            case R.id.date_search:
                idView.setVisibility(View.GONE);
                dateView.setVisibility(View.VISIBLE);
                id_search.setBackgroundColor(getResources().getColor(R.color.white));
                id_search.setTextColor(getResources().getColor(R.color.black));
                date_search.setBackgroundColor(getResources().getColor(R.color.purple));
                date_search.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.startDate:
                break;
            case R.id.endDate:
                break;
            case R.id.add:
                lorryArrangePopUp();
                break;
            case R.id.addDelivery:
                deliveryPopUp();
                break;
        }

    }

    @Override
    public void onSucess(final String value) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Utils.getStatus(value)) {
                    switch (apiCall) {
                        case 1:
                        if (Utils.jsonObject(value) != null) {
                            model = new LorryReportModel(Utils.jsonObject(value));
                            clearAll();
                            setValue();
                        } else {
                            clearAll();
                            Utils.showToast(LorryReport.this, Utils.getMessage(value));
                        }

                    break;
                        case 2:
                            if(dialog!=null) {
                                dialog.cancel();
                            }
                            Utils.showToast(LorryReport.this, "Successfully submited");
                            break;

                } }else{
                    if(apiCall==searchBooking) {
                        clearAll();
                    }
                    Utils.showToast(LorryReport.this, Utils.getMessage(value));
                }
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clearAll();
                progressBar.setVisibility(View.GONE);
                clearAll();
                Utils.showToast(LorryReport.this, Utils.getMessage(value));
            }
        });

    }

    public void deliveryPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.loory_reach_popup, null);
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.show();
    }
    public void lorryArrangePopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.lorry_arrange_popup, null);
        final EditText rate=(EditText)dialogLayout.findViewById(R.id.rate);
        final EditText mobile=(EditText)dialogLayout.findViewById(R.id.mobile);
        final EditText broker=(EditText)dialogLayout.findViewById(R.id.broker);
        final Button submit=(Button)dialogLayout.findViewById(R.id.submit);
        ProgressBar pb=(ProgressBar)dialogLayout.findViewById(R.id.progress);
        builder.setView(dialogLayout);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((rate.getText().length()>0)&&(mobile.getText().length()>0)&&(broker.getText().length()>0))
                {apiCall=arrangeLorry;

                    controller.getWebApiCall().postData(Common.getLorryArrangeUrl,getLorrryArrangeRequestJSON(rate.getText().toString(),broker.getText().toString(),mobile.getText().toString()).toString(), callback);
                    submit.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    if(rate.getText().length()==0)
                    {
                        Toast.makeText(LorryReport.this,"Please enter rate",Toast.LENGTH_SHORT).show();
                    }else if(broker.getText().length()>0)
                    {
                        Toast.makeText(LorryReport.this,"Please enter broker details",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LorryReport.this,"Please enter mobile number",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
