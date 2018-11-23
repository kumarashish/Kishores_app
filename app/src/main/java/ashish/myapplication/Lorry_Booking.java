package ashish.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    @BindView(R.id.item)
    EditText item_edt;
    @BindView(R.id.from)
    EditText from_edt;
    @BindView(R.id.to)
    EditText to_edt;
    @BindView(R.id.weight)
    EditText weight_edt;
    @BindView(R.id.package_edt)
    EditText package_edt;
    @BindView(R.id.submit)
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_lorry);
         controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        heading.setText(headingValue);
        form.setVisibility(View.GONE);
        if(Utils.isNetworkAvailable(Lorry_Booking.this))
        { apiCall=apiGetType;
            controller.getWebApiCall().getData(Common.getLorryType,this);
            progressBar.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:

                if((item_edt.getText().length()>0)&&(from_edt.getText().length()>0)&&(to_edt.getText().length()>0)&&(weight_edt.getText().length()>0)&&(package_edt.getText().length()>0))
                {
                   if(Utils.isNetworkAvailable(Lorry_Booking.this))
                   {
                       apiCall=bookLorry;
                       controller.getWebApiCall().postData(Common.getBookLorryUrl,jsonObject().toString(),Lorry_Booking.this);
                       submit.setVisibility(View.GONE);
                       progressBar2.setVisibility(View.VISIBLE);
                   }
                }else{
                    if(item_edt.getText().length()==0)
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
                             clearAll();
                             Utils.showToast(Lorry_Booking.this,"Booked sucessfully.");
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
{
    item_edt.setText("");
   from_edt.setText("");;
  to_edt.setText("");;
    weight_edt.setText("");;
  package_edt.setText("");;
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

    public JSONObject jsonObject()
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(Common.lorryBookingKeys[0],lorry_type_spn.getSelectedItem().toString());
            jsonObject.put(Common.lorryBookingKeys[1], item_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[2],from_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[3],to_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[4],weight_edt.getText().toString());
            jsonObject.put(Common.lorryBookingKeys[5],package_edt.getText().toString());
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
}