package ashish.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Delivery_Details extends Activity implements View.OnClickListener {
    public static String headingValue="";
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.heading)
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pickup_details);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        heading.setText(headingValue);
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