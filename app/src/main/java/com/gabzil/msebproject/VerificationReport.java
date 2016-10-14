package com.gabzil.msebproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VerificationReport extends Fragment implements View.OnClickListener, OnTaskMustCompleted,TaskCompletedConsumer
{
    AutoCompleteTextView cons;
    Button gprsbtn,surveybtn;
    TextView ErrMsg;
    int flag=0;
    String CheckLogin1;
    View ParentView;
    RadioGroup checkinggrp;
    RadioButton CheckButton;
    String Check;

    public VerificationReport()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_verification_report, container, false);

        getActivate(v);
        return v;
    }

    private boolean CallConsumerDB(View v)
    {
        if (cons.getText().toString().length() == 2)
        {
            new ConsumerAutoComplete(getActivity(), this).execute(cons.getText().toString());
        }
        return false;
    }

    private void getActivate(View v)
    {
        cons= (AutoCompleteTextView)v.findViewById(R.id.consumer);
        cons.setThreshold(2);//will start working from first character
        cons.setTextColor(Color.RED);

        cons.setOnKeyListener(new AutoCompleteTextView.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                ParentView = arg0;
                return CallConsumerDB(arg0);
            }
        });

        gprsbtn = (Button) v.findViewById(R.id.gprsbtn);
        gprsbtn.setOnClickListener(this);

        surveybtn = (Button) v.findViewById(R.id.surveybtn);
        surveybtn.setOnClickListener(this);

        checkinggrp = (RadioGroup) v.findViewById(R.id.boxsealgrp);
        checkinggrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = checkinggrp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                CheckButton = (RadioButton) getActivity().findViewById(selectedId);
                Check=(String) CheckButton.getText();
            }
        });

        ErrMsg = (TextView) v.findViewById(R.id.err);
    }

    @Override
    public void onClick(View v)
    {
        boolean check=isNetworkAvailable(getActivity());
        if(check == true)
        {
            cons.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(cons.getWindowToken(), 0);
            if(cons.getText().toString().trim().length() == 0 || Check == null)
            {
                ErrMsg.setText("Please Enter the Specified Fields");
            }
            else
            {
                switch (v.getId())
                {
                    case (R.id.gprsbtn):
                        flag=2;
                        break;

                    case (R.id.surveybtn):
                        flag=1;
                        break;

                    default:
                        break;
                }
                CheckConsumer(getActivity());
            }
        }
        else
        {
            ErrMsg.setText("Please Check Your Internet Connection");
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    private void CheckConsumer(Context context)
    {
        String InputNo = cons.getText().toString();
        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(context.TELEPHONY_SERVICE);
        String IMEI =tm.getDeviceId();
        String MsebType="GPS";
        if (flag == 1)
        {
            MsebType="SURVEY";
        }
        new LoginAccess(getActivity(), this).execute(Check,InputNo,IMEI,MsebType);
    }

    @Override
    public void onTaskMustCompleted(String results)
    {
        try
        {
            JSONObject obj = new JSONObject(results);
            CheckLogin1 = obj.getString("GetCustomerResult");
            if (CheckLogin1 != "null" && CheckLogin1.length() > 0)
            {
                CheckLogin(results);
            }
            else
            {
                ErrMsg.setText("Invalid Consumer Number");
            }
        }
        catch (Exception e)
        {
            ErrMsg.setText("Invalid Consumer Number");
        }

    }

    @Override
    public void onTaskCompletedConsumer(String results)
    {
        try
        {
            if (results != null && results.length() > 0)
            {
                SetConsumerNo(results);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "No Connection",
                     Toast.LENGTH_SHORT).show();
        }
    }

    private void SetConsumerNo(String results)
    {
        JSONObject obj = null;
        JSONObject obj1;
        try {
            obj = new JSONObject(results);
            ArrayList<String> stringArrayList = new ArrayList<String>();
            JSONArray jsonarray = obj.getJSONArray("GetCustomerAutoResult");

            for (int i = 0; i <= jsonarray.length()-1; i++)
            {
                obj1 = jsonarray.getJSONObject(i);

                stringArrayList.add(obj1.optString("CustomerNo")); //add to arraylist
            }

            String [] consumerno = stringArrayList.toArray(new String[stringArrayList.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (getActivity(),android.R.layout.select_dialog_item,consumerno);
            AutoCompleteTextView cons= (AutoCompleteTextView)ParentView.findViewById(R.id.consumer);
            cons.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void CheckLogin(String results)
    {
        try
        {
            if (results != null && results.length() > 0)
            {
                Gson gson = new Gson();

                final CustomerInfoEntity cust1 = gson.fromJson(CheckLogin1, CustomerInfoEntity.class);

                JSONObject obj = new JSONObject(results);
                CheckLogin1 = obj.getString("GetCustomerResult");

                if(cust1.getIsChecked() == "false")
                {
                    if(flag==2)
                    {
                        cust1.setNoOfShifts("1");
                        ((ConsumerInfo) getActivity().getApplicationContext()).setConsumerDetails(cust1);

                        Fragment fragment = new GPS(flag);
                        getFragmentManager().beginTransaction()
                                .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (flag == 1)
                    {
                        ((ConsumerInfo) getActivity().getApplicationContext()).setConsumerDetails(cust1);

                        Fragment fragment = new ConsumerInformation(flag);
                        getFragmentManager().beginTransaction()
                                .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Result");
                    alertDialogBuilder.setIcon(R.drawable.alert_icon);
                    alertDialogBuilder.setMessage("Customer Already Filled. For Changes Please Contact To Your Administrator!!!");

//                    alertDialogBuilder.setMessage("Customer Already Filled. Do You Want To Update???");

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // go to a new activity of the app
                            dialog.cancel();

//                            if(flag==2)
//                            {
//                                cust1.setNoOfShifts("1");
//                                ((ConsumerInfo) getActivity().getApplicationContext()).setConsumerDetails(cust1);
//
//                                Fragment fragment = new GPS(flag);
//                                getFragmentManager().beginTransaction()
//                                        .replace(((ViewGroup) getView().getParent()).getId(), fragment)
//                                        .addToBackStack(null)
//                                        .commit();
//                            }
//                            else if (flag == 1)
//                            {
////                    Gson gson = new Gson();
////
////                    CustomerInfoEntity cust1 = gson.fromJson(CheckLogin1, CustomerInfoEntity.class);
//                                ((ConsumerInfo) getActivity().getApplicationContext()).setConsumerDetails(cust1);
//
//                                Fragment fragment = new ConsumerInformation();
//                                getFragmentManager().beginTransaction()
//                                        .replace(((ViewGroup) getView().getParent()).getId(), fragment)
//                                        .addToBackStack(null)
//                                        .commit();
//                            }
                        }
                    });

                    // set negative button: No message
                    alertDialogBuilder.setNegativeButton(" ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // cancel the alert box and put a Toast to the user
                            //dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show alert
                    alertDialog.show();

                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
