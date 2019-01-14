package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingDelete extends Activity implements WebApiResponseCallback,View.OnClickListener {
    AppController controller;
    WebApiResponseCallback callback;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.bookingId)
    TextView bookingId;
    int apiCall = 0;
    int getData = 1, delete = 2;
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
    @BindView(R.id.delete)
    Button deletebtn;
    Dialog dialog;
    @BindView(R.id.table)
    LinearLayout table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_booking);
        callback = this;
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /* Write your logic here that will be executed when user taps next button */
                    if (search.getText().length() > 0) {
                        if (Utils.isNetworkAvailable(BookingDelete.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            apiCall = getData;
                            controller.getWebApiCall().postData(Common.getBookingReport, getRequestJSON().toString(), callback);


                        }
                    } else {
                        Utils.showToast(BookingDelete.this, "Please enter employee id");
                    }
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void clearAll() {
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

    public void setValue() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bookingId.setText(Integer.toString(model.getId()));
                lorryType.setText(model.getLorrytype());;
                item.setText(model.getItem());
                ;
                source_dest.setText(model.Bookfrom + " - " + model.getBookto());
                ;
                weight.setText(model.getWeight() + " kgs");
                ;
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
    public JSONObject getDeleteRequestJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Common.getBookingKeys[0],model.getId());

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    public JSONObject getRequestJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Common.getBookingKeys[0], search.getText().toString());
            jsonObject.put(Common.getBookingKeys[1], "");
            jsonObject.put(Common.getBookingKeys[2], "");
        } catch (Exception ex) {
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
                            if (Utils.jsonObject(value) != null) {
                                model = new LorryReportModel(Utils.jsonObject(value));
                                clearAll();
                                setValue();
                            } else {
                                clearAll();
                                table.setVisibility(View.GONE);
                                Utils.showToast(BookingDelete.this, Utils.getMessage(value));
                            }

                            break;
                        case 2:
                            Utils.showToast(BookingDelete.this, "Booking Id : "+model.getId()+" has been deleted sucessfully.");
                            clearAll();
                            table.setVisibility(View.GONE);
                            search.setText("");
                            break;

                    }
                    progressBar.setVisibility(View.GONE);

                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    model=null;
                    clearAll();
                    table.setVisibility(View.GONE);
                }
            });
            Utils.showToast(BookingDelete.this, Utils.getMessage(value));
        }
    }
    @Override
    public void onError(String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                table.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                model=null;
                clearAll();

            }
        });
        Utils.showToast(BookingDelete.this, Utils.getMessage(value));
    }
    public void deletePopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.alert, null);
        final Button yes=(Button) dialogLayout.findViewById(R.id.yes);
        final Button no=(Button) dialogLayout.findViewById(R.id.no);


        builder.setView(dialogLayout);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
                if (Utils.isNetworkAvailable(BookingDelete.this)) {
                    progressBar.setVisibility(View.VISIBLE);
                    apiCall = delete;
                    controller.getWebApiCall().deleteData(Common.getdeleteBookingUrl, getDeleteRequestJSON().toString(), callback);


                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
        dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                if( model!=null) {
                    deletePopUp();
                }else{
                    Toast.makeText(BookingDelete.this,"Please search booking id",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}