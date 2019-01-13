package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pending_Report extends Activity implements View.OnClickListener  ,WebApiResponseCallback {
    public static String headingValue = "";
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.heading)
    TextView heading;
    AppController controller;
    @BindView(R.id.progress)
    ProgressBar progressBar;
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

    @BindView(R.id.report1)
    LinearLayout report;


    @BindView(R.id.startDate)
    Button startDate;
    @BindView(R.id.endDate)
    Button endDate;
    @BindView(R.id.search)
    Button search;

    @BindView(R.id.bookingId)
    TextView bookingId;
    @BindView(R.id.consiner)
    TextView consiner;
    @BindView(R.id.consignee)
    TextView consine;


    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.cft)
    TextView cft;
    @BindView(R.id.load_type)
    TextView load_type;
    @BindView(R.id.radiogp)
    RadioGroup radioGp;
    @BindView(R.id.rate_pending)
    RadioButton ratePending;
    @BindView(R.id.approval_pending)
            RadioButton approval_pending;


    AlertDialog dialog;
    int apiCall = 0;
    int searchBooking = 1;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    boolean isStartDateClicked = false;
    boolean isEndDateClicked = false;
    boolean isDateClicked = false;
    boolean isStartDateSelected = false;
    boolean isEndDateSelected = false;

    String bookingIdValue = "0";
    ArrayList<LorryReportModel> reportList = new ArrayList<>();
    ProgressBar progress;
    boolean RatePending=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_pending);
        callback = this;
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayy = day - 1;
        int monthh = month + 1;
        startDate.setText(day + "/" + monthh + "/" + year);
        endDate.setText(day + "/" + monthh + "/" + year);
        initializeIdView();
        radioGp.check(R.id.rate_pending);
        radioGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rate_pending)
                {
                    RatePending=true;
                }else{
                    RatePending=false;
                }
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
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
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {

        if (isStartDateClicked) {
            isStartDateSelected = true;
            startDate.setText(day + "/" + month + "/" + year);
            endDate.setText("Select end date");
            isEndDateSelected = false;
        } else {
            isEndDateSelected = true;
            endDate.setText(day + "/" + month + "/" + year);
        }

    }

    public void clearAll() {

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

    public void setValue() {
        bookingIdValue = Integer.toString(model.getId());
        bookingId.setText(Integer.toString(model.getId()));
        lorryType.setText(model.getLorrytype());
        ;
        item.setText(model.getItem());
        ;
        source_dest.setText(model.Bookfrom + " - " + model.getBookto());
        ;
        weight.setText(model.getWeight() + " kgs");
        ;
        pckg.setText(model.getPackage());
        report.setVisibility(View.VISIBLE);
        SpannableString content = new SpannableString(bookingId.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, bookingId.getText().length(), 0);
        bookingId.setText(content);
        consiner.setText(model.getConsignor());
        consine.setText(model.getConsignee());

        load_type.setText(model.getLoadtype());
        freight.setText(model.getFreight());
        cft.setText(model.getCft());


        // delivery.setVisibility(View.VISIBLE);
        bookingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reportList.size() > 0) {
                    bookingIdListPopUp();
                }
            }
        });
        progressBar.setVisibility(View.GONE);
        search.setVisibility(View.VISIBLE);

        //delivery.setVisibility(View.VISIBLE);
    }

    public JSONObject getRequestJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (isDateClicked) {
                jsonObject.put(Common.getBookingKeys[0], 0);
                // jsonObject.put(Common.getBookingKeys[0],0);
                jsonObject.put(Common.getBookingKeys[1], startDate.getText().toString());
                jsonObject.put(Common.getBookingKeys[2], endDate.getText().toString());
                jsonObject.put(Common.getBookingKeys[3], RatePending);
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }


    public void initializeIdView() {
        isDateClicked = true;
        isStartDateSelected = true;
        isEndDateSelected = true;
        bookingIdValue = "0";
        progressBar.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);
        apiCall = searchBooking;
        controller.getWebApiCall().postData(Common.getPendigReport, getRequestJSON().toString(), callback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.search:

                if ((isStartDateSelected == true) && (isEndDateSelected == true)) {
                    bookingIdValue = "0";
                    progressBar.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    report.setVisibility(View.GONE);
                    apiCall = searchBooking;
                    controller.getWebApiCall().postData(Common.getPendigReport, getRequestJSON().toString(), callback);
                } else {
                    if (isStartDateSelected == false) {
                        Utils.showToast(Pending_Report.this, "Please choose start date");
                    } else {
                        Utils.showToast(Pending_Report.this, "Please choose end date");
                    }
                }
                break;
            case R.id.startDate:
                isStartDateClicked = true;
                isEndDateClicked = false;
                showDialog(999);
                break;
            case R.id.endDate:
                isStartDateClicked = false;
                isEndDateClicked = true;
                showDialog(999);
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
                            if (isDateClicked) {
                                reportList.clear();
                                JSONArray bookingArray = Utils.jsonArrayy(value);
                                for (int i = 0; i < bookingArray.length(); i++) {
                                    try {
                                        LorryReportModel model=new LorryReportModel(bookingArray.getJSONObject(i));

                                        reportList.add(model);

                                    } catch (Exception ex) {
                                        ex.fillInStackTrace();
                                    }

                                }
                                if (reportList.size() > 0) {
                                    bookingIdListPopUp();
                                }
                            } else {
                                if (Utils.jsonObject(value) != null) {
                                    model = new LorryReportModel(Utils.jsonObject(value));
                                    clearAll();
                                    setValue();
                                } else {
                                    clearAll();
                                    Utils.showToast(Pending_Report.this, Utils.getMessage(value));
                                }
                            }

                            break;
                        case 2:
                            if (dialog != null) {
                                dialog.cancel();
                            }
                            Utils.showToast(Pending_Report.this, "Successfully submited");
                            break;
                        case 3:
                            if (dialog != null) {
                                dialog.cancel();
                            }
                            Utils.showToast(Pending_Report.this, "Successfully Updated the report");
                            break;

                    }
                } else {
                    if (apiCall == searchBooking) {
                        clearAll();
                    }
                    Utils.showToast(Pending_Report.this, Utils.getMessage(value));
                }
                if (progress != null) {
                    progress.setVisibility(View.GONE);
                } else if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                search.setVisibility(View.VISIBLE);

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
                Utils.showToast(Pending_Report.this, Utils.getMessage(value));
                search.setVisibility(View.VISIBLE);
            }
        });

    }


    public void bookingIdListPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.list_layout, null);
        ListView list = (ListView) dialogLayout.findViewById(R.id.listView);
        list.setAdapter(new ListAdapter(reportList, Pending_Report.this));
        builder.setView(dialogLayout);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model = reportList.get(position);
                setValue();
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}