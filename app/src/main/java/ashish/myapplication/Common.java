package ashish.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {
    //public static String baseUrl="http://182.74.216.107/ds/api/employee/";
    public static String ip1="http://115.112.226.30/";
    public static String ip="";
    public static String baseUrl="http://115.112.226.30/dhtcsky/";
    public static String login=baseUrl+"Login";
    public static String addEmployee=baseUrl+"setEmp";
    public static String getEmployee=baseUrl+"getEmp";
    public static String getBookLorryUrl=baseUrl+"LorryBooking";
    public static String getdeleteBookingUrl=baseUrl+"DeleteBooking";
    public static String getBookingReport=baseUrl+"LorryBookingReport";
    public static String getPendigReport=baseUrl+"RatePendingReport";

    public static String getConsinerListUrl=baseUrl+"Getprcmst";
    public static String getSource_destListUrl=baseUrl+"Getprstnmst?BranchCode=";
    public static String getItemListUrl=baseUrl+"Getpritmst";
    public static String getLorryType=baseUrl+"GetLorryTypes";
    public static String getLorryReachUrl=baseUrl+"LorryReach";
    public static String getLorryPassUrl=baseUrl+"LorryPass";
    public static String getLorryPasspending=baseUrl+"LorryPassPending";
    public static String getLorryArrangeUrl=baseUrl+"LorryArrange";
    public static String getUploadUrl="http://3.16.29.242/dhtcdss/DSService.svc/UploadFile?Filename=";


    public static String getConsinerRequestKey="BRANCH_CODE";


    public static int SELECT_FILE=200;
    public static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=210;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static Uri imageUri = null;
    public static String sdCardPath;
    public static String folderName;
    public static String tempPath;

    public static String [] loginKeys={"User","Password"};
    public static String [] getEmpKeys={"ID"};
    public static String [] getBookingKeys={"BookingId","FromDate","ToDate","RatePending"};
    public static String [] setEmpKeys={"Firstname","Lastname","Address","Dob","Salary"};
    public static String [] lorryBookingKeys={"LorryType","Item","From","To","Weight","Package","Consignee","Consignor","BookedBy","Freight","Cft","LoadType","BookingId"};
    public static String[] getLorryReachKeys={"BookingId","LorryNo","PickFrom","GoodRemark","ArrangeDt","Reportdate","Reporttime","Reportby"};
    public static String [] getLorryPassKeys={"BookingId","Lorryrate","Broker","MobileNo","Arrangedby","PassedBy"};
    public static String [] getLorryArrangeKeys={"BookingId", "Lorryrate", "Broker", "MobileNo", "Arrangedby"};



    /* * camera module popup
     *************************************/
    public static void selectImageDialog(final Activity act) {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle("Upload");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (isDeviceSupportCamera(act)) {
                        captureImage(act);
                    } else {
                        Toast.makeText(act, "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
                    }
                } else if (items[item].equals("Choose from gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    act.startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                         SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Checking device has camera hardware or not
     */
    private static boolean isDeviceSupportCamera(Activity act) {
        if (act.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static void captureImage(Activity act) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Common.imageUri = getOutputMediaFileUri(Common.MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Common.imageUri);

        // start the image capture Intent
        try {
            act.startActivityForResult(intent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public static Uri getOutputMediaFileUri(int type) {
        File tempFile = getOutputMediaFile(type);
        Uri uri = Uri.fromFile(tempFile);
        return uri;
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Common.sdCardPath);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Common.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        File files = mediaFile;
        return mediaFile;
    }
    public static void makeFolder(String path, String folder) {
        File directory = new File(path, folder);
        if (directory.exists() == false) {
            directory.mkdirs();
        }
    }


}
