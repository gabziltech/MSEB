package com.gabzil.msebproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;



/**
 * Created by Yogesh on 10/27/2015.
 */
public class SaveConsumerAccess  extends AsyncTask<Object,String, String>
{
    private Context mContext;
    ProgressDialog mProgress;
    private OnTaskCompleted mCallback;
    int imageid;

    public SaveConsumerAccess(Context context,OnTaskCompleted listner)
    {
        this.mContext = context;
        this.mCallback = listner;
    }

    @Override
    public void onPreExecute()
    {
        mProgress = CreateProgress();
        mProgress.show();
    }

    @Override
    protected String doInBackground(Object[] params)
    {
        // do above Server call here
        ArrayList<Object> data = (ArrayList<Object>) params[0];
        CustomerInfoEntity consumerdata =(CustomerInfoEntity)data.get(0) ;
        ArrayList<PhotoEntity> photoListData = (ArrayList<PhotoEntity>)data.get(1);
        return getServerInfo(consumerdata, photoListData);
    }

    @Override
    protected void onPostExecute(String message)
    {
        //process message
        mProgress.dismiss();
        mCallback.onTaskCompleted(message);
    }

    private ProgressDialog CreateProgress()
    {
        ProgressDialog cProgress = new ProgressDialog(mContext);
        try
        {
            String msg= "Connecting... wait";
            String title = "Connecting to Internet";
            SpannableString ss1 = new SpannableString(title);
            ss1.setSpan(new RelativeSizeSpan(2f),0,ss1.length(),0);
            ss1.setSpan(new ForegroundColorSpan(Color.RED), 0,ss1.length(),0);

            SpannableString ss2 = new SpannableString(msg);
            ss2.setSpan(new RelativeSizeSpan(2f),0,ss2.length(),0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0,ss2.length(),0);

            cProgress.setTitle(null);
            cProgress.setMessage(ss2);

            return cProgress;
        }

        catch(Exception Ex)
        {
            return cProgress;
        }
    }

    private String getServerInfo(CustomerInfoEntity ConsumerData, ArrayList<PhotoEntity> photoList)
    {
        String reply1="";
        try {
            // Build JSON string
            JSONStringer vehicle = new JSONStringer()
                    .object()
                    .key("ConsumerInfo")
                    .object()
                    .key("CustomerID").value(ConsumerData.getCustomerID())
                    .key("CustomerNo").value(ConsumerData.getCustomerNo())
                    .key("CustomerName").value(ConsumerData.getCustomerName())
                    .key("AliasName").value(ConsumerData.getAliasName())
                    .key("ContactNo1").value(ConsumerData.getContactNo1())
                    .key("ContactNo2").value(ConsumerData.getContactNo2())
                    .key("ContactNo3").value(ConsumerData.getContactNo3())
                    .key("ContactPerson").value(ConsumerData.getContactPerson())
                    .key("Email").value(ConsumerData.getEmail())
                    .key("Landmark").value(ConsumerData.getLandmark())
                    .key("MNO").value(ConsumerData.getMNO())
                    .key("Make").value(ConsumerData.getMake())
                    .key("MeterType").value(ConsumerData.getMeterType())
                    .key("CTRatio").value(ConsumerData.getCTRatio())
                    .key("KWH").value(ConsumerData.getKWH())
                    .key("KVA").value(ConsumerData.getKVA())
                    .key("KVAH").value(ConsumerData.getKVAH())
                    .key("RKVAH").value(ConsumerData.getRKVAH())
                    .key("KWH1").value(ConsumerData.getKWH1())
                    .key("KVA1").value(ConsumerData.getKVA1())
                    .key("KVAH1").value(ConsumerData.getKVAH1())
                    .key("RKVAH1").value(ConsumerData.getRKVAH1())
                    .key("KWH2").value(ConsumerData.getKWH2())
                    .key("KVA2").value(ConsumerData.getKVA2())
                    .key("KVAH2").value(ConsumerData.getKVAH2())
                    .key("RKVAH2").value(ConsumerData.getRKVAH2())
                    .key("KWH3").value(ConsumerData.getKWH3())
                    .key("KVA3").value(ConsumerData.getKVA3())
                    .key("KVAH3").value(ConsumerData.getKVAH3())
                    .key("RKVAH3").value(ConsumerData.getRKVAH3())
                    .key("KWH4").value(ConsumerData.getKWH4())
                    .key("KVA4").value(ConsumerData.getKVA4())
                    .key("KVAH4").value(ConsumerData.getKVAH4())
                    .key("RKVAH4").value(ConsumerData.getRKVAH4())
                    .key("CTType").value(ConsumerData.getCTType())
                    .key("CCTRatio").value(ConsumerData.getCCTRatio())
                    .key("CTMake").value(ConsumerData.getCTMake())
                    .key("L1C").value(ConsumerData.getL1C())
                    .key("L1D").value(ConsumerData.getL1D())
                    .key("L2C").value(ConsumerData.getL2C())
                    .key("L2D").value(ConsumerData.getL2D())
                    .key("L3C").value(ConsumerData.getL3C())
                    .key("L3D").value(ConsumerData.getL3D())
                    .key("NatureOfWork").value(ConsumerData.getNatureOfWork())
                    .key("NoOfShift").value(Integer.parseInt(ConsumerData.getNoOfShifts()))
                    .key("Remarks").value(ConsumerData.getRemarks())
                    .key("BoxSeal").value(ConsumerData.getBoxSeal())
                    .key("TerminalCoverSeal").value(ConsumerData.getTerminalCoverSeal())
                    .key("MDResetSeal").value(ConsumerData.getMDResetSeal())
                    .key("BodySeal").value(ConsumerData.getBodySeal())
                    .key("ReaderID").value(ConsumerData.getReaderID())
                    .key("Reader").value(ConsumerData.getReader())
//                    .key("ReaderName").value(ConsumerData.getReaderName())

                    .key("SUBDN").value(ConsumerData.getSUBDN())
                    .key("Address").value(ConsumerData.getAddress())
                    .key("Longitude").value(ConsumerData.getLongitude())
                    .key("Latitude").value(ConsumerData.getLatitude())
                    .key("PreMNO").value(ConsumerData.getPreMNO())
                    .key("PreMake").value(ConsumerData.getPreMake())
                    .key("PreKWH").value(ConsumerData.getPreKWH())
                    .key("PreKVA").value(ConsumerData.getPreKVA())
                    .key("PreKVAH").value(ConsumerData.getPreKVAH())
                    .key("PreRKVAH").value(ConsumerData.getPreRKVAH())
                    .key("PreReader").value(ConsumerData.getPreReader())
                    .key("TAR").value(ConsumerData.getTAR())
                    .key("PreTAR").value(ConsumerData.getPreTAR())
                    .key("CD_KVA").value(ConsumerData.getCD_KVA())
                    .key("PreCD_KVA").value(ConsumerData.getPreCD_KVA())
                    .key("SANCTIONED_DEMAND").value(ConsumerData.getSANCTIONED_DEMAND())
                    .key("PreSANCTIONED_DEMAND").value(ConsumerData.getPreSANCTIONED_DEMAND())
                    .key("NO_OF_RESET").value(ConsumerData.getNO_OF_RESET())
                    .key("PreNO_OF_RESET").value(ConsumerData.getPreNO_OF_RESET())
                    .key("Mobile").value(ConsumerData.getMobile())
                    .key("MSEBType").value(ConsumerData.getMSEBType())
                    .key("IsChecked").value(true)
                    .endObject()
                    .endObject();

            HttpPost request = new HttpPost("http://gabmseb.azurewebsites.net/msebservice.svc/savecustomer1");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            StringEntity entity = new StringEntity(vehicle.toString());

            reply1 = vehicle.toString();

            request.setEntity(entity);

            // Send request to WCF service
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);

            for (int i = 0; i < photoList.size(); i++)
            {
                imageid=i;
                SetImage(photoList.get(i),ConsumerData.getCustomerID(),ConsumerData.getCustomerNo());
            }
        }
        catch (Exception ex)
        {
            String e = ex.getMessage();
        }
        mProgress.dismiss();
        return reply1;
    }

    private void SetImage(PhotoEntity photo, int CustomerID,String CustomerNo) {
        File imgFile = new File(photo.getPath());

        try {
            if (imgFile.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    SendImageToServer(bitmap, CustomerID,CustomerNo);
                } catch (OutOfMemoryError e) {

                }
            }
        } catch (Exception e) {
        }
    }

    private void SendImageToServer(Bitmap data2,int CustomerID,String CustomerNo)
    {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            data2.compress(Bitmap.CompressFormat.JPEG, 50, bao);
            byte[] data = bao.toByteArray();
            int size = data.length;

            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();

            final String URL1 = "http://gabmsebimg.azurewebsites.net/msebimgserv.svc/GetData";
            HttpPost httpPost = new HttpPost(URL1);
            httpPost.setEntity(new ByteArrayEntity(data));

//            ByteArrayOutputStream bao = new ByteArrayOutputStream();
//            data2.compress(Bitmap.CompressFormat.JPEG, 50, bao);
////            String stringToConvert = "370374";
//            byte[] CustomerNo1 = CustomerNo.getBytes();
//            byte[] data = bao.toByteArray();
////            int size = data.length;
//            byte[] c = ByteBuffer.allocate(CustomerNo1.length + data.length).put(CustomerNo1).put(data).array();
//            int length = c.length;
//
//            // defaultHttpClient
//            DefaultHttpClient httpClient = new DefaultHttpClient();
////            final String URL1 = "http://gabmsebimg.azurewebsites.net/msebimgserv.svc/GetData";
//            final String URL1 = "http://gabmseb.azurewebsites.net/msebimage.svc/GetData1";
//            HttpPost httpPost = new HttpPost(URL1);
//
//            String json = "";
//
//            // 3. build jsonObject
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("img", CustomerNo);
//
//            // 4. convert JSONObject to JSON to String
//            json = jsonObject.toString();
//
//            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
//            // ObjectMapper mapper = new ObjectMapper();
//            // json = mapper.writeValueAsString(person);
//
//            // 5. set json to StringEntity
//            StringEntity se = new StringEntity(json);
//
//            // 6. set httpPost Entity
//            httpPost.setEntity(se);
//
//
////            httpPost.setEntity(new ByteArrayEntity(CustomerNo1));
            HttpResponse response = httpClient.execute(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);

            String s1= String.valueOf(s);

            s1=s1.replace("\\\\","\\");

            s1=s1.replace("\"","");

            SendImageUrl(s1, CustomerID);

        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }

    private void SendImageUrl(String s1,int CustomerID)
    {
        try {
            // Build JSON string
            JSONStringer vehicle1 = new JSONStringer()
                    .object()
                    .key("Data")
                    .object()
                    .key("ImageUrl").value(s1)
                    .key("CustomerID").value(CustomerID)
                    .endObject()
                    .endObject();

            HttpPost request = new HttpPost("http://gabmseb.azurewebsites.net/msebservice.svc/saveimage");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            StringEntity entity = new StringEntity(vehicle1.toString());

            request.setEntity(entity);

            // Send request to WCF service
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
        }
        catch (Exception ex)
        {
            String e = ex.getMessage();
        }
    }
}
