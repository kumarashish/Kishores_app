package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

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
    @BindView(R.id.picked_from)
    TextView picked_from;
    @BindView(R.id.goods_remark)
    TextView goods_remark;
    @BindView(R.id.arranged_date)
    TextView arranged_date;
    @BindView(R.id.report_date)
    TextView reporting_date;
    @BindView(R.id.reporting_time)
    TextView reporting_time;
    @BindView(R.id.freight)
    TextView freight;
    @BindView(R.id.cft)
    TextView cft;
    @BindView(R.id.load_type)
    TextView load_type;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.addDelivery)
    Button delivery;
AlertDialog dialog;
int apiCall=0;
int searchBooking=1,arrangeLorry=2,updateReporting=3;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    boolean isStartDateClicked=false;
    boolean isEndDateClicked=false;
    boolean isDateClicked=false;
    boolean isStartDateSelected=false;
    boolean isEndDateSelected=false;
    @BindView(R.id.search_with_date)
    Button search_with_date;
    String bookingIdValue="0";
    ArrayList <LorryReportModel>reportList=new ArrayList<>();
    ProgressBar progress;
    boolean isarrageDateClicked=false;
    boolean isreportdateclicked=false;

     EditText arrange_date;
    EditText report_date;

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
        search_with_date.setOnClickListener(this);
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
                                bookingIdValue=search.getText().toString();
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
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }else{
            return new DatePickerDialog(this,
                    myDateListener2, year, month, day);
        }

    }

    private DatePickerDialog.OnDateSetListener myDateListener2= new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate2(arg1, arg2+1, arg3);
                }
            };
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
            endDate.setText("Select end date");
            isEndDateSelected=false;
        } else {
            isEndDateSelected=true;
            endDate.setText( day+ "/" +  month+ "/" + year);
        }

    }

    private void showDate2(int year, int month, int day) {
        if (isarrageDateClicked) {

           arrange_date.setText(day + "/" + month + "/" + year);

        } else {

            report_date.setText(day + "/" + month + "/" + year);
        }

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
        load_type.setText("");
        cft.setText("");
        freight.setText("");
        lorryNumber.setText("");
        arranged_date.setText("");
        goods_remark.setText("");
        picked_from.setText("");
        reporting_date.setText("");
        reporting_time.setText("");
    }
    public void setValue()
    {   bookingIdValue=Integer.toString(model.getId());
        bookingId.setText(Integer.toString(model.getId()));
        lorryType.setText(model.getLorrytype());;
        item.setText(model.getItem());;
        source_dest.setText(model.Bookfrom+" - "+model.getBookto());;
        weight.setText(model.getWeight() +" kgs");;
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
        arranged_date.setText(model.getArrangedt());
        reporting_time.setText(model.getReporttime());
        goods_remark.setText(model.getGoodremark());
        picked_from.setText(model.getPickfrom());
        reporting_date.setText(model.getReportdate());
        freight.setText(model.getFreight());
        cft.setText(model.getCft());
        load_type.setText(model.getLoadtype());


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
       // delivery.setVisibility(View.VISIBLE);
        bookingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportList.size()>0)
                {bookingIdListPopUp();
                }
            }
        });

   ///delivery.setVisibility(View.VISIBLE);
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
    public JSONObject getReportRequestJSON(String lorryNumber,String pickedFrom,String goodRemark,String arrangeDate,String reportDate,String reportTime)
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.getLorryReachKeys[0], bookingIdValue);
            jsonObject.put(Common.getLorryReachKeys[1],lorryNumber);
            jsonObject.put(Common.getLorryReachKeys[2],pickedFrom);
            jsonObject.put(Common.getLorryReachKeys[3],goodRemark);
            jsonObject.put(Common.getLorryReachKeys[4],arrangeDate);
            jsonObject.put(Common.getLorryReachKeys[5],reportDate);
            jsonObject.put(Common.getLorryReachKeys[6],reportTime);
            jsonObject.put(Common.getLorryReachKeys[7],controller.getManager().getUserId());
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    public JSONObject getLorrryArrangeRequestJSON(String rate,String broker,String mobilenumber)
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.getLorryArrangeKeys[0], bookingIdValue);
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
        controller.getWebApiCall().postData(Common.getBookingReport, getRequestJSON().toString(), callback);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.id_search:
                report.setVisibility(View.GONE);
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
                report.setVisibility(View.GONE);
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
                    apiCall = searchBooking;
                    controller.getWebApiCall().postData(Common.getBookingReport, getRequestJSON().toString(), callback);
                } else {
                    if (isStartDateSelected == false) {
                        Utils.showToast(LorryReport.this, "Please choose start date");
                    } else {
                        Utils.showToast(LorryReport.this, "Please choose end date");
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
                            }else {
                                if (Utils.jsonObject(value) != null) {
                                    model = new LorryReportModel(Utils.jsonObject(value));
                                    clearAll();
                                    setValue();
                                } else {
                                    clearAll();
                                    Utils.showToast(LorryReport.this, Utils.getMessage(value));
                                }
                            }

                    break;
                        case 2:
                            if(dialog!=null) {
                                dialog.cancel();
                            }
                            Utils.showToast(LorryReport.this, "Successfully submited");
                            break;
                        case 3:
                            if(dialog!=null) {
                                dialog.cancel();
                            }
                            Utils.showToast(LorryReport.this, "Successfully Updated the report");
                            break;

                } }else{
                    if(apiCall==searchBooking) {
                        clearAll();
                    }
                    Utils.showToast(LorryReport.this, Utils.getMessage(value));
                }
                if(progress!=null) {
                    progress.setVisibility(View.GONE);
                }
                else if( progressBar!=null) {
                    progressBar.setVisibility(View.GONE);
                }

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
        TextView bookingId=(TextView)dialogLayout.findViewById(R.id.bookingId);
        final EditText lorry_number1=(EditText)dialogLayout.findViewById(R.id.num1);
        final EditText lorry_number2=(EditText)dialogLayout.findViewById(R.id.num2);
        final EditText lorry_number3=(EditText)dialogLayout.findViewById(R.id.num3);
        final EditText lorry_number4=(EditText)dialogLayout.findViewById(R.id.num4);
       arrange_date=(EditText)dialogLayout.findViewById(R.id.arrange_date);
        report_date=(EditText)dialogLayout.findViewById(R.id.report_date);
        final EditText pickfrom=(EditText)dialogLayout.findViewById(R.id.picked_from);
        final EditText remark=(EditText)dialogLayout.findViewById(R.id.remark);
        final Spinner report_time_hh=(Spinner)dialogLayout.findViewById(R.id.hour);
        final Spinner report_time_mm=(Spinner)dialogLayout.findViewById(R.id.minute);
        final Spinner report_time_ampm=(Spinner)dialogLayout.findViewById(R.id.am_pm);
        final Button submit=(Button)dialogLayout.findViewById(R.id.submit);
         progress=(ProgressBar)dialogLayout.findViewById(R.id.progress);
        lorry_number1.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        lorry_number3.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        pickfrom.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        remark.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        bookingId.setText("Booking Id : "+ bookingIdValue);


        report_time_hh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.aqua));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        report_time_mm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.aqua));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        report_time_ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.aqua));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrange_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isarrageDateClicked=true;
                showDialog(1000);
            }
        });
        report_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isarrageDateClicked=false;
                isreportdateclicked=true;
                showDialog(1000);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((lorry_number1.getText().length()>0)&&(lorry_number1.getText().length()==2)&&(lorry_number2.getText().length()>0)&&(lorry_number2.getText().length()==2)&&(lorry_number3.getText().length()>0)&&(lorry_number4.getText().length()>0)&&(lorry_number4.getText().length()==4)&&(pickfrom.getText().length()>0)&&(remark.getText().length()>0)&&(arrange_date.getText().length()>0)&&(arrange_date.getText().toString().contains("/"))&&(report_date.getText().toString().contains("/")))
                {
                    progress.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    apiCall=updateReporting;
                    controller.getWebApiCall().postData(Common.getLorryReachUrl, getReportRequestJSON(lorry_number1.getText().toString()+"-"+lorry_number2.getText().toString()+"-"+lorry_number3.getText().toString()+"-"+lorry_number4.getText().toString(),pickfrom.getText().toString(),remark.getText().toString(),arrange_date.getText().toString(),report_date.getText().toString(),report_time_hh.getSelectedItem().toString()+":"+report_time_mm.getSelectedItem().toString()+""+report_time_ampm.getSelectedItem().toString()).toString() ,callback);
                }else{
                    if((lorry_number1.getText().length()==0)||(lorry_number1.getText().length()!=2))
                    {
                        Toast.makeText(LorryReport.this,"Please enter first two characters of lorry number",Toast.LENGTH_SHORT).show();
                    }
                    else if((lorry_number2.getText().length()==0)||(lorry_number2.getText().length()!=2))
                    {
                        Toast.makeText(LorryReport.this,"Please enter next two characters of lorry number",Toast.LENGTH_SHORT).show();
                    }
                    else if(lorry_number3.getText().length()==0)
                    {
                        Toast.makeText(LorryReport.this,"Please enter next two characters of lorry number",Toast.LENGTH_SHORT).show();
                    }
                    else if((lorry_number4.getText().length()==0)||(lorry_number4.getText().length()!=4))
                    {
                        Toast.makeText(LorryReport.this,"Please enter last four characters of lorry number",Toast.LENGTH_SHORT).show();
                    }
                    else if((pickfrom.getText().length()==0))
                    {
                        Toast.makeText(LorryReport.this,"Please enter value for picked from",Toast.LENGTH_SHORT).show();
                    } else if((remark.getText().length()==0))
                    {
                        Toast.makeText(LorryReport.this,"Please enter goods remark",Toast.LENGTH_SHORT).show();
                    }
                  else if(arrange_date.getText().length()==0)
                    {
                        Toast.makeText(LorryReport.this,"Please select arrange date",Toast.LENGTH_SHORT).show();
                    }else if(!arrange_date.getText().toString().contains("/"))
                    {
                        Toast.makeText(LorryReport.this,"Please select arrange date in dd/mm/yyyy format",Toast.LENGTH_SHORT).show();
                    }else if(report_date.getText().length()==0)
                    {
                        Toast.makeText(LorryReport.this,"Please select report date",Toast.LENGTH_SHORT).show();
                    }else if(!report_date.getText().toString().contains("/"))
                    {
                        Toast.makeText(LorryReport.this,"Please select report date in dd/mm/yyyy format",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.show();
    }

    public void bookingIdListPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.list_layout, null);
        ListView list=(ListView)dialogLayout.findViewById(R.id.listView);
       list.setAdapter(new ListAdapter(reportList,LorryReport.this));
        builder.setView(dialogLayout);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model=reportList.get(position);
                setValue();
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
    public void lorryArrangePopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.lorry_arrange_popup, null);
        final EditText rate=(EditText)dialogLayout.findViewById(R.id.rate);
        final EditText mobile=(EditText)dialogLayout.findViewById(R.id.mobile);
        final EditText name_edt=(EditText)dialogLayout.findViewById(R.id.broker);
        final RadioButton broker=(RadioButton) dialogLayout.findViewById(R.id.broaker);
        final RadioButton owner=(RadioButton) dialogLayout.findViewById(R.id.owner);
        final RadioGroup gp=(RadioGroup)dialogLayout.findViewById(R.id.radiogp);
        final Button submit=(Button)dialogLayout.findViewById(R.id.submit);
       final ProgressBar pb=(ProgressBar)dialogLayout.findViewById(R.id.progress);
        builder.setView(dialogLayout);
        broker.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        gp.check(broaker.getId());
        gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                if((rate.getText().length()>0)&&(mobile.getText().length()>0)&&(mobile.getText().length()==10)&&(name_edt.getText().length()>0))
                {apiCall=arrangeLorry;
                    if(gp.getCheckedRadioButtonId()==R.id.broaker)
                    {
                        name=name_edt.getText().toString()+"(BROKER)";
                    }else{
                        name=name_edt.getText().toString()+"(OWNER)";
                    }


                    controller.getWebApiCall().postData(Common.getLorryArrangeUrl,getLorrryArrangeRequestJSON(rate.getText().toString(),name,mobile.getText().toString()).toString(), callback);
                    submit.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                }else{
                    if(rate.getText().length()==0)
                    {
                        Toast.makeText(LorryReport.this,"Please enter rate",Toast.LENGTH_SHORT).show();
                    }else if(name_edt.getText().length()==0)
                    {
                        Toast.makeText(LorryReport.this,"Please enter name",Toast.LENGTH_SHORT).show();
                    }
                    else if (mobile.getText().length()==0){
                        Toast.makeText(LorryReport.this,"Please enter mobile number",Toast.LENGTH_SHORT).show();
                    }
                    else if (mobile.getText().length()!=10){
                        Toast.makeText(LorryReport.this,"Please enter valid mobile number",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

}
