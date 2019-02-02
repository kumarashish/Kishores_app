package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

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
    int getData=1,approve=2,searchBooking=3;
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
    @BindView(R.id.idView)
    LinearLayout idView;
    boolean isStartDateClicked=false;
    boolean isEndDateClicked=false;
    boolean isDateClicked=false;
    boolean isStartDateSelected=false;
    boolean isEndDateSelected=false;
    private Calendar calendar;
    private int year, month, day;
    ArrayList <LorryReportModel>reportList=new ArrayList<>();
    String bookingIdValue="0";
    AlertDialog dialog;
    @BindView(R.id.search_with_date)
    Button search_with_date;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lorry_pass);
        callback=this;
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        bookingId.setOnClickListener(this);
        id_search.setOnClickListener(this);
        date_search.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        search_with_date.setOnClickListener(this);
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
                            isDateClicked=false;
                            bookingIdValue=search.getText().toString();
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
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayy=day-1;
        int monthh=month+1;
        startDate.setText(day + "/" +  monthh+ "/" + year);
        endDate.setText(day + "/" + monthh + "/" + year);
        initializeIdView();
    }
    public void initializeIdView()
    {
        isDateClicked=true;
        search.setText("");
        idView.setVisibility(View.GONE);
        dateView.setVisibility(View.VISIBLE);
        id_search.setBackgroundColor(getResources().getColor(R.color.purple));
        id_search.setTextColor(getResources().getColor(R.color.white));
        date_search.setBackgroundColor(getResources().getColor(R.color.white));
        date_search.setTextColor(getResources().getColor(R.color.purple));
        isStartDateSelected=true;
        isEndDateSelected=true;
        bookingIdValue="0";
        progressBar.setVisibility(View.VISIBLE);
        apiCall = searchBooking;
        controller.getWebApiCall().postData(Common.getPendigReport, getRequestJSON().toString(), callback);
    }

    public void getData()
    {
        progressBar.setVisibility(View.VISIBLE);
        apiCall=getData;
        isDateClicked=false;
        if(search.getText().length()!=0)
        {
            bookingIdValue=search.getText().toString();
        }

        controller.getWebApiCall().postData(Common.getLorryPasspending, getRequestJSON().toString(), callback);
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
            SpannableString content = new SpannableString( bookingId.getText().toString());
            content.setSpan(new UnderlineSpan(), 0,bookingId.getText().length(), 0);
            bookingId.setText(content);
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
            table.setVisibility(View.VISIBLE);
        }
    });


    }

    public JSONObject getRequestJSON()
    {JSONObject jsonObject=new JSONObject();
        try{
            if(isDateClicked)
            {
                jsonObject.put(Common.getBookingKeys[0], 0);
                // jsonObject.put(Common.getBookingKeys[0],0);
                jsonObject.put(Common.getBookingKeys[1], startDate.getText().toString());
                jsonObject.put(Common.getBookingKeys[2], endDate.getText().toString());
                jsonObject.put(Common.getBookingKeys[3], false);
            }else {
                jsonObject.put(Common.getBookingKeys[0], bookingIdValue);
                // jsonObject.put(Common.getBookingKeys[0],0);
                jsonObject.put(Common.getBookingKeys[1], "");
                jsonObject.put(Common.getBookingKeys[2], "");
            }
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

            return new DatePickerDialog(this,
                    myDateListener, year, month, day);


    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {

        if (isStartDateClicked) {
            isStartDateSelected = true;
            startDate.setText(day+ "/" +  month+ "/" + year);
        } else {
            isEndDateSelected=true;
            endDate.setText( day+ "/" +  month+ "/" + year);
        }

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

                        case 3:
                            if(isDateClicked)
                            { reportList.clear();
                                JSONArray bookingArray= Utils.jsonArrayy(value);
                                for(int i=0;i<bookingArray.length();i++)
                                {try {
                                    reportList.add(new LorryReportModel(bookingArray.getJSONObject(i)));
                                }catch (Exception ex)
                                {
                                    ex.fillInStackTrace();
                                }

                                }
                                if(reportList.size()>0)
                                {
                                    bookingIdListPopUp();
                                }
                            }
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

    public void bookingIdListPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.list_layout, null);
        ListView list=(ListView)dialogLayout.findViewById(R.id.listView);
        list.setAdapter(new ListAdapter(reportList,LorryPass.this));
        builder.setView(dialogLayout);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model=reportList.get(position);
                bookingIdValue=Integer.toString(model.getId());
                setValue();
                getData();
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
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
            case R.id.id_search:
                table.setVisibility(View.GONE);
                reportList.clear();
                clearAll();
                isDateClicked=false;
                isStartDateSelected=false;
                isEndDateSelected=false;
                startDate.setText("Select start date");
                endDate.setText("Select end date");
                id_search.setBackgroundColor(getResources().getColor(R.color.white));
                id_search.setTextColor(getResources().getColor(R.color.purple));
                date_search.setBackgroundColor(getResources().getColor(R.color.purple));
                date_search.setTextColor(getResources().getColor(R.color.white));
                idView.setVisibility(View.VISIBLE);
                dateView.setVisibility(View.GONE);
                break;
            case R.id.date_search:
                table.setVisibility(View.GONE);
                isDateClicked=true;
                search.setText("");
                clearAll();
                idView.setVisibility(View.GONE);
                dateView.setVisibility(View.VISIBLE);
                id_search.setBackgroundColor(getResources().getColor(R.color.purple));
                id_search.setTextColor(getResources().getColor(R.color.white));
                date_search.setBackgroundColor(getResources().getColor(R.color.white));
                date_search.setTextColor(getResources().getColor(R.color.purple));
                break;
            case R.id.search_with_date:

                if ((isStartDateSelected == true) && (isEndDateSelected == true)) {
                    bookingIdValue="0";
                    progressBar.setVisibility(View.VISIBLE);
                    table.setVisibility(View.GONE);
                    apiCall = searchBooking;
                    controller.getWebApiCall().postData(Common.getPendigReport, getRequestJSON().toString(), callback);
                } else {
                    if (isStartDateSelected == false) {
                        Utils.showToast(LorryPass.this, "Please choose start date");
                    } else {
                        Utils.showToast(LorryPass.this, "Please choose end date");
                    }
                }
                break;
            case R.id.startDate:
                isStartDateClicked=true;
                isEndDateClicked=false;
                showDialog(999);
                break;
            case R.id.endDate:
                isStartDateClicked=false;
                isEndDateClicked=true;
                showDialog(999);
                break;

            case R.id.bookingId:
                if(reportList.size()>0)

                {
                    bookingIdListPopUp();
                }

                break;
        }
    }
}
