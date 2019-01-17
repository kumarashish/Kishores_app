package ashish.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Lorry_Booking extends Activity implements View.OnClickListener ,WebApiResponseCallback {
    public static String headingValue="";
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.form)
    LinearLayout form;
    AppController controller;
    int apiCall=0;
    int apiGetType=1,bookLorry=2;
    @BindView(R.id.progress1)
    ProgressBar progressBar;
    @BindView(R.id.progress)
    ProgressBar progressBar2;
    ArrayList<LorryTypeModel> lorryTypes=new ArrayList();
    List<String> items=new ArrayList();
    @BindView(R.id.type)
    Spinner lorry_type_spn;
    @BindView(R.id.load_type)
    Spinner load_type_spn;
    @BindView(R.id.consine)
    AutoCompleteTextView  consine;
    @BindView(R.id.consiner)
    AutoCompleteTextView consiner;
    @BindView(R.id.item)
    AutoCompleteTextView  item_edt;
    @BindView(R.id.from)
    AutoCompleteTextView  from_edt;
    @BindView(R.id.to)
    AutoCompleteTextView  to_edt;
    @BindView(R.id.weight)
    EditText weight_edt;
    @BindView(R.id.package_edt)
    EditText package_edt;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.l)
    EditText l;
    @BindView(R.id.h)
    EditText h;
    @BindView(R.id.w)
    EditText w;
    @BindView(R.id.total)
    EditText total;
    @BindView(R.id.freight_edt)
    EditText freight_edt;
    int length=0;
    int width=0;
    int height=0;
    int totalValue=1;
    @BindView(R.id.bookingId)
    TextView bookingId;
    @BindView(R.id.bookingView)
    LinearLayout bookingView;
    @BindView(R.id.clear)
    Button clear;
    ArrayList<ConsinerModel>autocompleteModelList=new ArrayList<>();
    ArrayList<String>autocompleteList=new ArrayList<>();
    ArrayList<String>source_dest=new ArrayList<>();
    ArrayList<String>item=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_lorry);
         controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        search.setOnClickListener(this);
        clear.setOnClickListener(this);

        heading.setText(headingValue);
        form.setVisibility(View.GONE);
        if (Utils.isNetworkAvailable(Lorry_Booking.this)) {
            apiCall = apiGetType;
            controller.getWebApiCall().getData(Common.getLorryType, this);
            progressBar.setVisibility(View.VISIBLE);

            Thread T = new Thread(new Runnable() {
                @Override
                public void run() {

                    String source_dest = controller.getWebApiCall().getData(Common.getSource_destListUrl + "" + controller.getManager().getBranchcode());
                    setSource_Dest(source_dest);
                }
            });
            T.start();
            Thread T2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String value = controller.getWebApiCall().postData(Common.getConsinerListUrl, getAutoCompleteRequestJSON(controller.getManager().getBranchcode()).toString());
                    setConsine(value);
                }
            });
            T2.start();
            Thread T3 = new Thread(new Runnable() {
                @Override
                public void run() {

                    String items = controller.getWebApiCall().getData(Common.getItemListUrl);
                    setItem(items);
                }
            });
            T3.start();

        }


        lorry_type_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        load_type_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        l.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    length = Integer.parseInt(s.toString());
                }
                setValue();
            }
        });
        w.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    width = Integer.parseInt(s.toString());
                }
                setValue();

            }
        });
        h.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    height = Integer.parseInt(s.toString());
                }
                setValue();
            }
        });
    }

    public void setSource_Dest(String value) {
        source_dest.clear();
        if (value != null) {
            if (Utils.getStatus(value) == true) {


                try {
                    JSONArray jsonArray = Utils.jsonArrayy(value);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Source_Dest_Model model = new Source_Dest_Model(jsonArray.getJSONObject(i));
                        source_dest.add(model.getNAME());
                        // autocompleteModelList.add(model);
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (Lorry_Booking.this, android.R.layout.simple_list_item_1, source_dest);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            from_edt.setAdapter(adapter);
                            to_edt.setAdapter(adapter);
                            from_edt.setThreshold(1);
                            to_edt.setThreshold(1);
                        }
                    });


                } catch (Exception ex)

                {
                    ex.fillInStackTrace();
                }
            }
        }
    }

    public void setItem(String value) {
        item.clear();
        if (value != null) {
            if (Utils.getStatus(value) == true) {
                try {
                    JSONArray jsonArray = Utils.jsonArrayy(value);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Item_Model model = new Item_Model(jsonArray.getJSONObject(i));
                        item.add(model.getNAME());
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (Lorry_Booking.this, android.R.layout.simple_list_item_1, item);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            item_edt.setAdapter(adapter);
                            item_edt.setAdapter(adapter);
                        }
                    });
                } catch (Exception ex)
                {
                    ex.fillInStackTrace();
                }
            }
        }
    }

public void setConsine(String value)
{autocompleteList.clear();
    if(value!=null)
    {
        if(Utils.getStatus(value)==true) {


            try {
                JSONArray jsonArray = Utils.jsonArrayy(value);

                for (int i = 0; i < jsonArray.length(); i++) {
                    ConsinerModel model=new ConsinerModel(jsonArray.getJSONObject(i));
                    autocompleteList.add(model.getName());
                    autocompleteModelList.add(model);
                }
                final  ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (Lorry_Booking.this,android.R.layout.simple_list_item_1,autocompleteList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        consiner.setAdapter(adapter);
                        consine.setAdapter(adapter);
                        consiner.setThreshold(1);
                        consine.setAdapter(adapter);
                    }
                });


            } catch (Exception ex)

            {
                ex.fillInStackTrace();
            }
        }}
}

public void setValue()
{totalValue=1;
    if(length!=0)
    {
        totalValue=totalValue*length;
    }
    if(width!=0)
    {
        totalValue=totalValue*width;
    }
    if(height!=0)
    { totalValue=totalValue*height;
    }
    total.setText(""+totalValue);
}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear:
                clearAll();
                break;

            case R.id.search:
                startActivity(new Intent(this,LorryReport.class));
                break;
            case R.id.submit:

                if((item_edt.getText().length()>0)&&(from_edt.getText().length()>0)&&(to_edt.getText().length()>0)&&(weight_edt.getText().length()>0)&&(package_edt.getText().length()>0)&&(consine.getText().length()>0)&&(consiner.getText().length()>0)&&(freight_edt.getText().length()>0)&&(l.getText().length()>0)&&(w.getText().length()>0)&&(h.getText().length()>0))
                {
                   if(Utils.isNetworkAvailable(Lorry_Booking.this))
                   {
                       apiCall=bookLorry;
                       controller.getWebApiCall().postData(Common.getBookLorryUrl,jsonObject().toString(),Lorry_Booking.this);
                       submit.setVisibility(View.GONE);
                       progressBar2.setVisibility(View.VISIBLE);
                   }
                }else{
                    if(consine.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter consinee");
                    }else  if(consiner.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter consiner");
                    }
                    else if(item_edt.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter item name");
                    }
                    else if(from_edt.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter source");
                    }else if(to_edt.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter destination");

                    }
                    else if(weight_edt.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter weight");
                    }
                    else if(package_edt.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter package count");
                    }
                    else if(freight_edt.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter freight value");
                    }
                    else if(l.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter length");
                    }
                    else if(w.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter width");
                    }
                    else if(h.getText().length()==0)
                    {
                        Utils.showToast(Lorry_Booking.this,"Please enter height");
                    }

                }
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
                            try {
                            JSONArray type=Utils.jsonArrayy(value);
                            for(int i=0;i<type.length();i++)
                            {
                                LorryTypeModel model=new LorryTypeModel(type.getJSONObject(i));
                                    lorryTypes.add(model);
                                items.add(model.getLorryType());
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(lorryTypes.size()>0)
                            {

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Lorry_Booking.this, android.R.layout.simple_spinner_item,items);
                                lorry_type_spn.setAdapter(adapter );

                            }
                            form.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            break;
                        case 2:
                            progressBar2.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(bookingId.getText().toString().trim().equalsIgnoreCase("0"))
                                    {   bookingId.setText(Utils.getBookingId(value));

                                        Utils.showToast(Lorry_Booking.this,"Booked sucessfully.Booking Id : "+bookingId.getText().toString());
                                    }else{
                                        Utils.showToast(Lorry_Booking.this,"Your Booking Id : "+bookingId.getText().toString()+" has been updated sucessfully"+bookingId.getText().toString());
                                    }

                                    bookingView.setVisibility(View.VISIBLE);

                                }
                            });


                         break;
                    }


                }else {
                    if(apiCall==apiGetType) {
                        progressBar.setVisibility(View.GONE);

                    }else{
                        progressBar2.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                    }
                    Utils.showToast(Lorry_Booking.this, Utils.getMessage(value));
                }
            }
        });

    }
public void clearAll()
{   consine.setText("");
consiner.setText("");
    item_edt.setText("");
   from_edt.setText("");;
  to_edt.setText("");;
    weight_edt.setText("");;
  package_edt.setText("");;
  freight_edt.setText("");
  l.setText("");
  w.setText("");
  h.setText("");
 total.setText("");
 load_type_spn.setSelection(0);
    length=0;
    width=0;
    height=0;
    totalValue=1;
    bookingId.setText("0");
    bookingView.setVisibility(View.GONE);
}
    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(apiCall==apiGetType) {
                    progressBar.setVisibility(View.GONE);

                }else{
                    progressBar2.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }

                Utils.showToast(Lorry_Booking.this, Utils.getMessage(value));
            }
        });
    }
     public JSONObject getAutoCompleteRequestJSON(String value)
     {
         JSONObject jsonObject=new JSONObject();
         try{
             jsonObject.put(Common.getConsinerRequestKey,value);

         }catch (Exception ex)
         {
             ex.fillInStackTrace();
         }
         return jsonObject;
     }
    public JSONObject jsonObject()
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.lorryBookingKeys[0],lorry_type_spn.getSelectedItem().toString());
            jsonObject.put(Common.lorryBookingKeys[1], item_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[2],from_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[3],to_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[4],weight_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[5],package_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[6],consine.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[7],consiner.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[8],controller.manager.getUserId());
            jsonObject.put(Common.lorryBookingKeys[9],freight_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[10],length+"x"+width+"x"+height+"="+totalValue);
            jsonObject.put(Common.lorryBookingKeys[11],load_type_spn.getSelectedItem().toString());
            jsonObject.put(Common.lorryBookingKeys[12],bookingId.getText().toString());
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
}