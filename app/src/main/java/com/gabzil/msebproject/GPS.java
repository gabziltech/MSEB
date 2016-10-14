package com.gabzil.msebproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GPS extends Fragment implements View.OnClickListener, OnTaskCompleted,TaskDeleteComplete
{
    static WifiManager wifiManager;
    int flag;
    LocationManager lm;
    GPSTracker gps;
    ArrayList<PhotoEntity> photoList;
    ListView listView;
    private int Counter=0;
    CustomerInfoEntity PageConsumerEntity;
    Button sbmtbtn,backbtn;
    EditText readername,loc;
    int backbttn=0;

    EditText kwh,kva,kvah,rkvah,kwh1,kva1,kvah1,rkvah1,kwh2,kva2,kvah2,rkvah2,kwh3,kva3,kvah3,rkvah3,kwh4,kva4,kvah4,rkvah4;

    double latitude,longitude;

    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    Uri imageUri                      = null;
    static TextView imageDetails      = null;
    GPS CameraActivity = null;

    public GPS()
    {
        // Required empty public constructor
    }

    GPS(int flag)
    {
        this.flag=flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gps, container, false);
        photoList = new ArrayList<PhotoEntity>();

        listView = (ListView) v.findViewById(R.id.photolist);

        PageConsumerEntity = ((ConsumerInfo) getActivity().getApplicationContext()).getConsumerDetails();

        loc= (EditText) v.findViewById(R.id.location);
        if(PageConsumerEntity.getLandmark() == null)
        {
            loc.setText("");
        }
        else
        {
            loc.setText(new String(PageConsumerEntity.getLandmark()));
        }

        readername= (EditText) v.findViewById(R.id.reader);
        readername.setText(new String(PageConsumerEntity.getReaderName()));
//        if(PageConsumerEntity.getPreReader() == null)
//        {
//            readername.setText("");
//        }
//        else
//        {
//            readername.setText(new String(PageConsumerEntity.getPreReader()));
//        }

        if(flag == 0)
        {
            LinearLayout loclayout=(LinearLayout)v.findViewById(R.id.loclayout);
            loclayout.setVisibility(LinearLayout.GONE);
        }

        kwh= (EditText) v.findViewById(R.id.kwh);
        if(PageConsumerEntity.getKWH() == null)
        {
            kwh.setText("");
        }
        else
        {
            kwh.setText(new String(PageConsumerEntity.getKWH()));
        }

        kva= (EditText) v.findViewById(R.id.kva);
        if(PageConsumerEntity.getKVA() == null)
        {
            kva.setText("");
        }
        else
        {
            kva.setText(new String(PageConsumerEntity.getKVA()));
        }

        kvah= (EditText) v.findViewById(R.id.kvah);
        if(PageConsumerEntity.getKVAH() == null)
        {
            kvah.setText("");
        }
        else
        {
            kvah.setText(new String(PageConsumerEntity.getKVAH()));
        }

        rkvah= (EditText) v.findViewById(R.id.rkvah);
        if(PageConsumerEntity.getRKVAH() == null)
        {
            rkvah.setText("");
        }
        else
        {
            rkvah.setText(new String(PageConsumerEntity.getRKVAH()));
        }

        kwh1= (EditText) v.findViewById(R.id.kwh1);
        if(PageConsumerEntity.getKWH1() == null)
        {
            kwh1.setText("");
        }
        else
        {
            kwh1.setText(new String(PageConsumerEntity.getKWH1()));
        }

        kva1= (EditText) v.findViewById(R.id.kva1);
        if(PageConsumerEntity.getKVA1() == null)
        {
            kva1.setText("");
        }
        else
        {
            kva1.setText(new String(PageConsumerEntity.getKVA1()));
        }

        kvah1= (EditText) v.findViewById(R.id.kvah1);
        if(PageConsumerEntity.getKVAH1() == null)
        {
            kvah1.setText("");
        }
        else
        {
            kvah1.setText(new String(PageConsumerEntity.getKVAH1()));
        }

        rkvah1= (EditText) v.findViewById(R.id.rkvah1);
        if(PageConsumerEntity.getRKVAH1() == null)
        {
            rkvah1.setText("");
        }
        else
        {
            rkvah1.setText(new String(PageConsumerEntity.getRKVAH1()));
        }

        kwh2= (EditText) v.findViewById(R.id.kwh2);
        if(PageConsumerEntity.getKWH2() == null)
        {
            kwh2.setText("");
        }
        else
        {
            kwh2.setText(new String(PageConsumerEntity.getKWH2()));
        }

        kva2= (EditText) v.findViewById(R.id.kva2);
        if(PageConsumerEntity.getKVA2() == null)
        {
            kva2.setText("");
        }
        else
        {
            kva2.setText(new String(PageConsumerEntity.getKVA2()));
        }

        kvah2= (EditText) v.findViewById(R.id.kvah2);
        if(PageConsumerEntity.getKVAH2() == null)
        {
            kvah2.setText("");
        }
        else
        {
            kvah2.setText(new String(PageConsumerEntity.getKVAH2()));
        }

        rkvah2= (EditText) v.findViewById(R.id.rkvah2);
        if(PageConsumerEntity.getRKVAH2() == null)
        {
            rkvah2.setText("");
        }
        else
        {
            rkvah2.setText(new String(PageConsumerEntity.getRKVAH2()));
        }

        kwh3= (EditText) v.findViewById(R.id.kwh3);
        if(PageConsumerEntity.getKWH3() == null)
        {
            kwh3.setText("");
        }
        else
        {
            kwh3.setText(new String(PageConsumerEntity.getKWH3()));
        }

        kva3= (EditText) v.findViewById(R.id.kva3);
        if(PageConsumerEntity.getKVA3() == null)
        {
            kva3.setText("");
        }
        else
        {
            kva3.setText(new String(PageConsumerEntity.getKVA3()));
        }

        kvah3= (EditText) v.findViewById(R.id.kvah3);
        if(PageConsumerEntity.getKVAH3() == null)
        {
            kvah3.setText("");
        }
        else
        {
            kvah3.setText(new String(PageConsumerEntity.getKVAH3()));
        }

        rkvah3= (EditText) v.findViewById(R.id.rkvah3);
        if(PageConsumerEntity.getRKVAH3() == null)
        {
            rkvah3.setText("");
        }
        else
        {
            rkvah3.setText(new String(PageConsumerEntity.getRKVAH3()));
        }

        kwh4= (EditText) v.findViewById(R.id.kwh4);
        if(PageConsumerEntity.getKWH4() == null)
        {
            kwh4.setText("");
        }
        else
        {
            kwh4.setText(new String(PageConsumerEntity.getKWH4()));
        }

        kva4= (EditText) v.findViewById(R.id.kva4);
        if(PageConsumerEntity.getKVA4() == null)
        {
            kva4.setText("");
        }
        else
        {
            kva4.setText(new String(PageConsumerEntity.getKVA4()));
        }

        kvah4= (EditText) v.findViewById(R.id.kvah4);
        if(PageConsumerEntity.getKVAH4() == null)
        {
            kvah4.setText("");
        }
        else
        {
            kvah4.setText(new String(PageConsumerEntity.getKVAH4()));
        }

        rkvah4= (EditText) v.findViewById(R.id.rkvah4);
        if(PageConsumerEntity.getRKVAH4() == null)
        {
            rkvah4.setText("");
        }
        else {
            rkvah4.setText(new String(PageConsumerEntity.getRKVAH4()));
        }

        wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        sbmtbtn = (Button) v.findViewById(R.id.sbmtbtn);
        sbmtbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (!ValidateData()) {
                    getloglat();
                    if(longitude == 0 && latitude == 0)
                    {
                        Toast.makeText(getActivity(),"Location Can't Get",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        SaveDataToServer();

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Result");
                        alertDialogBuilder.setIcon(R.drawable.alert_icon);
                        alertDialogBuilder.setMessage("Record Successfully Saved");

                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // go to a new activity of the app
                                dialog.cancel();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        // set negative button: No message
                        alertDialogBuilder.setNegativeButton("", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the alert box and put a Toast to the user

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show alert
                        alertDialog.show();
                    }
                }
            }
        });

        backbtn = (Button) v.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(flag == 0)
                {
                    backbttn=1;
                    Fragment fragment = new Checking(backbttn);
                    getFragmentManager().beginTransaction()
                            .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                            .addToBackStack(null)
                            .commit();
                }
                else
                {
                    Toast.makeText(getActivity(),"You Should Not Enter In This Portion",Toast.LENGTH_SHORT).show();
                }
            }
        });

  //      selectedImage = (ImageView) v.findViewById(R.id.selectedImage);
        Button openGallery = (Button) v.findViewById(R.id.opengallery);

        openGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Counter<3)
                {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 0);
                }
                else
                    Toast.makeText(getActivity(),"Maximum 3 Photos Will Be Allow To Capture",
                            Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void SaveDataToServer()
    {
        ArrayList<Object> data = new ArrayList<Object>();

        data.add(PageConsumerEntity);
        data.add(photoList);
        new SaveConsumerAccess(getActivity(), this).execute(data);
    }

    @Override
    public void OnDeleteComplete(int Position)
    {
        try
        {
            photoList.remove(Position);
            Counter=Counter-1;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Result");
            alertDialogBuilder.setIcon(R.drawable.alert_icon);
            alertDialogBuilder.setMessage("Image Successfully Deleted");

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // go to a new activity of the app
                    dialog.cancel();
                }
            });

            // set negative button: No message
            alertDialogBuilder.setNegativeButton("", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // cancel the alert box and put a Toast to the user
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            // show alert
            alertDialog.show();

            setphotoinfo();
        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(), "No Connection",
            //         Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskCompleted(String results)
    {
        try
        {
            if (results != null && results.length() > 0)
            {

            }
        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(), "No Connection",
            //         Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ValidateData()
    {
        boolean error=false;
        String strmsg= "Please ";

        if(photoList.size() == 0)
        {
            strmsg += "Select Atleast 1 Photo";
            error = true;
        }
        if(flag == 2)
        {
            if(loc.getText().toString().trim().length() == 0)
            {
                strmsg += ", Enter Address";
                error = true;
            }
        }
        if(readername.getText().toString().trim().length() == 0)
        {
            Toast.makeText(getActivity(), "Enter Reader Name", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if(error == true)
        {
            String replacedString =strmsg.replace("Please ,", "Please ");
            Toast.makeText(getActivity(), replacedString, Toast.LENGTH_SHORT).show();
        }

        PageConsumerEntity.setLandmark(loc.getText().toString().trim());
        PageConsumerEntity.setKWH(kwh.getText().toString().trim());
        PageConsumerEntity.setKVA(kva.getText().toString().trim());
        PageConsumerEntity.setKVAH(kvah.getText().toString().trim());
        PageConsumerEntity.setRKVAH(rkvah.getText().toString().trim());
        PageConsumerEntity.setKWH1(kwh1.getText().toString().trim());
        PageConsumerEntity.setKVA1(kva1.getText().toString().trim());
        PageConsumerEntity.setKVAH1(kvah1.getText().toString().trim());
        PageConsumerEntity.setRKVAH1(rkvah1.getText().toString().trim());
        PageConsumerEntity.setKWH2(kwh2.getText().toString().trim());
        PageConsumerEntity.setKVA2(kva2.getText().toString().trim());
        PageConsumerEntity.setKVAH2(kvah2.getText().toString().trim());
        PageConsumerEntity.setRKVAH2(rkvah2.getText().toString().trim());
        PageConsumerEntity.setKWH3(kwh3.getText().toString().trim());
        PageConsumerEntity.setKVA3(kva3.getText().toString().trim());
        PageConsumerEntity.setKVAH3(kvah3.getText().toString().trim());
        PageConsumerEntity.setRKVAH3(rkvah3.getText().toString().trim());
        PageConsumerEntity.setKWH4(kwh4.getText().toString().trim());
        PageConsumerEntity.setKVA4(kva4.getText().toString().trim());
        PageConsumerEntity.setKVAH4(kvah4.getText().toString().trim());
        PageConsumerEntity.setRKVAH4(rkvah4.getText().toString().trim());
        PageConsumerEntity.setReader(readername.getText().toString().trim());

        return  error;
    }

    @Override
    public void onClick(View view)
    {
        PageConsumerEntity.setLocation(loc.getText().toString().trim());
        if(Counter<3)
        {
            CameraActivity=null;
            CameraActivity = this;
            getCameraIntent();
        }
        else
            Toast.makeText(getActivity(),"Maximum 3 Photos Will Be Allow To Capture",
                Toast.LENGTH_SHORT).show();
    }

    private void getloglat()
    {
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation())
        {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(!(addresses == null)){
                if (!addresses.isEmpty() ){
                    System.out.println(addresses.get(0).getLocality());

                    Address address = addresses.get(0);

                    String addressText = String.format("%s, %s, %s, %s, %s, %s",
                            address.getAddressLine(0),
                            address.getAddressLine(1),
                            address.getAddressLine(2),
                            address.getAddressLine(3),
                            address.getPhone(),
                            address.getPremises());

                    System.out.println(addressText);

                    // \n is for new line
                    Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    PageConsumerEntity.setLongitude(String.valueOf(longitude));
                    PageConsumerEntity.setLatitude(String.valueOf(latitude));

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        }
    }

    private void getCameraIntent()
    {
        /*************************** Camera Intent Start ************************/
        // Define the file-name to save photo taken by Camera activity
        String fileName = "Camera_Example.jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/
        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        /*************************** Camera Intent End ************************/
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == 0)
//        {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (resultCode == Activity.RESULT_OK) {
//                Uri photoUri = data.getData();
//                if (photoUri != null) {
//                    try {
//                        currentImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
////                        selectedImage.setImageBitmap(currentImage);
//
//                        String imageId = convertImageUriToFile( photoUri,CameraActivity);
//
//                        setphotoinfo();
//
//
//                    } catch (Exception e) {
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//        else
//        {
//            try
//            {
//                if ( requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
//                {
//                    if ( resultCode == Activity.RESULT_OK)
//                    {
//                        /*********** Load Captured Image And Data Start ****************/
//                        String imageId = convertImageUriToFile( imageUri,CameraActivity);
//
//                        setphotoinfo();
//
//                        /*********** Load Captured Image And Data End ****************/
//                    }
//                    else if ( resultCode == Activity.RESULT_CANCELED)
//                    {
//                        Toast.makeText(getActivity(), " Picture was not taken ", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(), " Picture was not taken ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                Toast.makeText(getActivity().getApplicationContext(), "Error In Capturing The Image",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri photoUri = data.getData();
            long size = 0;
         //   Uri fileUri = resultData.getData();
            try{
                Cursor cursor = getActivity().getContentResolver().query(photoUri,
                        null, null, null, null);
                cursor.moveToFirst();
                size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
                cursor.close();
            }
            catch (Exception e){
                e.getMessage();
            }

            try{
                if (size < 2097152)
                {
                    if (photoUri != null) {
                        try {
//                    currentImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                            //                   selectedImage.setImageBitmap(currentImage);
                            String imageId = convertImageUriToFile( photoUri,CameraActivity);

                            setphotoinfo();
                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(),"Out Of Memory",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Size is Too Large",Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e)
            {
                Toast.makeText(getActivity().getApplicationContext(),"Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

//            File f = new File(photoUri.getPath());
//            long size = f.length();
//            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(photoUri));
//            bitmap.getByteCount();

        }
    }

    PhotoAdapter photoAdap;
    public void setphotoinfo()
    {
        try
        {
            photoAdap = new PhotoAdapter(getActivity(), R.layout.myphotolist, photoList,this);

            // Assign adapter to ListView
            listView.setAdapter(photoAdap);

            //enables filtering for the contents of the given ListView
            listView.setTextFilterEnabled(true);

        }
        catch (Exception Ex)
        {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Error"+ Ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /************ Convert Image Uri path to physical path **************/

    public String convertImageUriToFile(Uri imageUri, GPS activity)
    {
        Cursor cursor = null;
        int imageID=0;

        try
        {
            /*********** Which columns values want to get *******/
            String [] proj={
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = getActivity().getContentResolver().query(
                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)
            );

            //  Get Query Data
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/
            if (size == 0)
            {
                imageDetails.setText("No Image");
            }
            else
            {
                if (cursor.moveToFirst())
                {
                    imageID     = cursor.getInt(columnIndex);
                    String Path = cursor.getString(file_ColumnIndex);

                    PhotoEntity photodata = new PhotoEntity();
                    photodata.setPath(Path);
                    Counter = Counter + 1;
                    if(flag == 0)
                    {
                        String conno = PageConsumerEntity.getCustomerNo()  +"_"+ Integer.toString(Counter);
                        photodata.setPhotoName(conno);
                    }
                    else
                    {
                        String conno = PageConsumerEntity.getCustomerNo()  +"_"+ Integer.toString(Counter);
                        photodata.setPhotoName(conno);
                    }
                    photoList.add(photodata);
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )
        return ""+imageID;
    }
}