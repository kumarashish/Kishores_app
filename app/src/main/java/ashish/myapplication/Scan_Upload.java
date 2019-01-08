package ashish.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class Scan_Upload  extends Activity implements View.OnClickListener, WebApiResponseCallback{
    AppController controller;
    WebApiResponseCallback callback;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.booking_id)
    EditText bookingId;
    @BindView(R.id.id_type)
    Spinner idType;
    @BindView(R.id.camera)
    ImageView image;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.progress)
    ProgressBar progressBar;
   final int PERMISSION_REQUEST_CODE=22;
    public static final int MULTIPLE_PERMISSIONS = 10;
    TextView register;
    Button home;
    boolean isImageCaptured=false;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA};
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_upload);
        callback = this;
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        image.setOnClickListener(this);
        idType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if(!checkPermissions()){
            }
        }
    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (int i=0;i<PERMISSIONS.length;i++) {
            result = ContextCompat.checkSelfPermission(this,PERMISSIONS[i]);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(PERMISSIONS[i]);
            }
        }
        String [] permissionList=new String[listPermissionsNeeded.size()];
        for(int i=0;i<listPermissionsNeeded.size();i++)
        {
            permissionList[i]=listPermissionsNeeded.get(i);
        }
        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.camera:
                Common.captureImage(Scan_Upload.this);
                break;
            case R.id.submit:

                if((bookingId.getText().toString().length()>0)&&(isImageCaptured==true))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    controller.getWebApiCall().uploadData(Common.getUploadUrl+""+bookingId.getText().toString()+"-"+idType.getSelectedItem().toString()+"-"+file.getName(),file,callback);
                }else{
                    if(bookingId.getText().length()==0)
                    {
                       Toast.makeText(Scan_Upload.this,"Please enter booking id",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Scan_Upload.this,"Please capture image",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    checkPermissions();
                }
                return;
            }

        }
    }

    @Override
    public void onSucess(String value) {
        if(Utils.getStatus(value)==true)
        {
            Utils.showToast(Scan_Upload.this,"Document uploaded sucessfully");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    image.setImageResource(R.drawable.camera_icon);
                    bookingId.setText("");
                }
            });
        }else {
            Utils.showToast(Scan_Upload.this,Utils.getMessage(value));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);

                }
            });

        }


    }

    @Override
    public void onError(String value) {
        Utils.showToast(Scan_Upload.this,Utils.getMessage(value));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Common.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Common.tempPath = Common.imageUri.getPath();
                isImageCaptured = true;
                setImage();
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Common.SELECT_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                Common.tempPath = c.getString(columnIndex);
                c.close();
                isImageCaptured = true;
                setImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                // Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, options));

            } else {
                Toast.makeText(this, " This Image cannot be stored .please try with some other Image. ", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void setImage()
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath,options);
         file =new File(Common.tempPath);
        final String name=file.getName();

        image.setImageBitmap(myBitmap);
    }
}
