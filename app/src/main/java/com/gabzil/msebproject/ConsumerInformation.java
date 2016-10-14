package com.gabzil.msebproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Boolean.parseBoolean;

/*
 * Created by Yogesh on 10/10/2015.
 */
public class ConsumerInformation extends Fragment implements View.OnClickListener
{
    LinearLayout ctratiolinear,ctdetailslayout,typelinear,cctratiolinear,makelinear;
    CustomerInfoEntity PageConsumerEntity;
    Button nxtbtn,change;
    int back,selectionPosition;

    Spinner mtrtypespinner,ctratiospinner,cttypespinner,cctratiospinner;
    ArrayAdapter<CharSequence> mtypeadapter,ctratioadapter,cttypeadapter,cctratioadapter;

    EditText cnsmrno,cnsmrname,aliasname,cntctno1,cntctno2,cntctno3,cntctprsn,email,location,mno,make,mtype,ctratio;
    EditText ctmake,l1c,l1d,l2c,l2d,l3c,l3d;

    public ConsumerInformation()
    {
        // Required empty public constructor
    }

    ConsumerInformation(int back) {
        this.back=back;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customer_information, container, false);

        mtypeadapter= ArrayAdapter.createFromResource(getActivity(),
                R.array.mtype_array, android.R.layout.simple_spinner_item);
        mtypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mtrtypespinner=(Spinner)v.findViewById(R.id.mtype_spinner);
        mtrtypespinner.setAdapter(mtypeadapter);

        ctratioadapter= ArrayAdapter.createFromResource(getActivity(),
                R.array.cct_ratio_array, android.R.layout.simple_spinner_item);
        ctratioadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ctratiospinner=(Spinner)v.findViewById(R.id.ctratio_spinner);
        ctratiospinner.setAdapter(ctratioadapter);

        cttypeadapter= ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        cttypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cttypespinner=(Spinner)v.findViewById(R.id.type_spinner);
        cttypespinner.setAdapter(cttypeadapter);

        cctratioadapter= ArrayAdapter.createFromResource(getActivity(),
                R.array.cct_ratio_array, android.R.layout.simple_spinner_item);
        cctratioadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cctratiospinner=(Spinner)v.findViewById(R.id.cct_ratio_spinner);
        cctratiospinner.setAdapter(cctratioadapter);

        change=(Button)v.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                openAlert(change);
            }
        });

        ctratiolinear=(LinearLayout)v.findViewById(R.id.ctratiolinear);
        ctdetailslayout=(LinearLayout)v.findViewById(R.id.ctdetailslayout);
        typelinear=(LinearLayout)v.findViewById(R.id.typelinear);
        cctratiolinear=(LinearLayout)v.findViewById(R.id.cctratiolinear);
        makelinear=(LinearLayout)v.findViewById(R.id.makelinear);

        mtrtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectionPosition= mtypeadapter.getPosition(mtrtypespinner.getSelectedItem().toString());

                if (mtrtypespinner.getSelectedItem().toString().equals("WHOLE"))
                {
                    ctratiolinear.setVisibility(LinearLayout.GONE);
                    ctdetailslayout.setVisibility(LinearLayout.GONE);
                    typelinear.setVisibility(LinearLayout.GONE);
                    cctratiolinear.setVisibility(LinearLayout.GONE);
                    makelinear.setVisibility(LinearLayout.GONE);
                }
                else if(mtrtypespinner.getSelectedItem().toString().equals("Select"))
                {
                    ctratiolinear.setVisibility(LinearLayout.GONE);
                    ctdetailslayout.setVisibility(LinearLayout.GONE);
                    typelinear.setVisibility(LinearLayout.GONE);
                    cctratiolinear.setVisibility(LinearLayout.GONE);
                    makelinear.setVisibility(LinearLayout.GONE);
                }
                else
                {
                    ctratiolinear.setVisibility(View.VISIBLE);
                    ctdetailslayout.setVisibility(View.VISIBLE);
                    typelinear.setVisibility(View.VISIBLE);
                    cctratiolinear.setVisibility(View.VISIBLE);
                    makelinear.setVisibility(View.VISIBLE);
                }
                String metertypespinner = mtrtypespinner.getSelectedItem().toString();
                PageConsumerEntity.setMeterType(metertypespinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });

        ctratiospinner= (Spinner) v.findViewById(R.id.ctratio_spinner);
        ctratiospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String ctrationspinner = ctratiospinner.getSelectedItem().toString();
                PageConsumerEntity.setCTRatio(ctrationspinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                PageConsumerEntity.setCTRatio(" ");
            }
        });

        cttypespinner= (Spinner) v.findViewById(R.id.type_spinner);
        cttypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String typespinner = cttypespinner.getSelectedItem().toString();
                PageConsumerEntity.setCTType(typespinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                PageConsumerEntity.setCTType(" ");
            }
        });

        cctratiospinner= (Spinner) v.findViewById(R.id.cct_ratio_spinner);
        cctratiospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String cctrtospinner = cctratiospinner.getSelectedItem().toString();
                PageConsumerEntity.setCCTRatio(cctrtospinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                PageConsumerEntity.setCCTRatio(" ");
            }
        });

        nxtbtn=(Button)v.findViewById(R.id.nextbtn);
        nxtbtn.setOnClickListener(this);

        PageConsumerEntity = ((ConsumerInfo) getActivity().getApplicationContext()).getConsumerDetails();

        cnsmrno= (EditText) v.findViewById(R.id.cnsmrno);
        cnsmrno.setText(new String(PageConsumerEntity.getCustomerNo()));

        cnsmrname= (EditText) v.findViewById(R.id.cnsmrname);
        cnsmrname.setText(new String(PageConsumerEntity.getCustomerName()));

        aliasname= (EditText) v.findViewById(R.id.aliasname);
        aliasname.setText(new String(PageConsumerEntity.getAliasName()));

        cntctno1= (EditText) v.findViewById(R.id.cntcno1);
        cntctno1.setText(new String(PageConsumerEntity.getContactNo1()));

        cntctno2= (EditText) v.findViewById(R.id.cntcno2);
        if(PageConsumerEntity.getContactNo2() == null)
        {
            cntctno2.setText("");
        }
        else
        {
            cntctno2.setText(new String(PageConsumerEntity.getContactNo2()));
        }

        cntctno3= (EditText) v.findViewById(R.id.cntcno3);
        if(PageConsumerEntity.getContactNo3() == null)
        {
            cntctno3.setText("");
        }
        else
        {
            cntctno3.setText(new String(PageConsumerEntity.getContactNo3()));
        }

        cntctprsn= (EditText) v.findViewById(R.id.cntcprsn);
        if(PageConsumerEntity.getContactPerson() == null)
        {
            cntctprsn.setText("");
        }
        else
        {
            cntctprsn.setText(new String(PageConsumerEntity.getContactPerson()));
        }

        email= (EditText) v.findViewById(R.id.email);
        if(PageConsumerEntity.getEmail() == null)
        {
            email.setText("");
        }
        else
        {
            email.setText(new String(PageConsumerEntity.getEmail()));
        }

        location= (EditText) v.findViewById(R.id.location);
        location.setText(new String(PageConsumerEntity.getLandmark()));

        mno= (EditText) v.findViewById(R.id.mno);
        mno.setText(new String(PageConsumerEntity.getPreMNO()));

        make= (EditText) v.findViewById(R.id.make);
        make.setText(new String(PageConsumerEntity.getPreMake()));

        String meterTypeValue = PageConsumerEntity.getMeterType();
        if (!meterTypeValue.equals(null)) {
            int spinnerPosition = mtypeadapter.getPosition(meterTypeValue);
            mtrtypespinner.setSelection(spinnerPosition);
        }

        String ctRatioValue = PageConsumerEntity.getCTRatio();
        if (ctRatioValue == null)
        {
            ctratiospinner.setSelection(0);
        }
        else
        {
            if (!ctRatioValue.equals(null))
            {
                int spinnerPosition = ctratioadapter.getPosition(ctRatioValue);
                ctratiospinner.setSelection(spinnerPosition);
            }
        }

        String ctTypeValue = PageConsumerEntity.getCTType();
        if (ctTypeValue == null)
        {
            cttypespinner.setSelection(0);
        }
        else
        {
            if (!ctTypeValue.equals(null))
            {
                int spinnerPosition = cttypeadapter.getPosition(ctTypeValue);
                cttypespinner.setSelection(spinnerPosition);
            }
        }

        String cctRatioValue = PageConsumerEntity.getCCTRatio();
        if (cctRatioValue == null)
        {
            cctratiospinner.setSelection(0);
        }
        else
        {
            if (!cctRatioValue.equals(null))
            {
                int spinnerPosition = cctratioadapter.getPosition(cctRatioValue);
                cctratiospinner.setSelection(spinnerPosition);
            }
        }

        ctmake= (EditText) v.findViewById(R.id.make1);
        if(PageConsumerEntity.getCTMake() == null)
        {
            ctmake.setText("");
        }
        else
        {
            ctmake.setText(new String(PageConsumerEntity.getCTMake()));
        }

        l1c= (EditText) v.findViewById(R.id.l1c);
        if(PageConsumerEntity.getL1C() == null)
        {
            l1c.setText("");
        }
        else
        {
            l1c.setText(new String(PageConsumerEntity.getL1C()));
        }

        l1d= (EditText) v.findViewById(R.id.l1d);
        if(PageConsumerEntity.getL1D() == null)
        {
            l1d.setText("");
        }
        else
        {
            l1d.setText(new String(PageConsumerEntity.getL1D()));
        }

        l2c= (EditText) v.findViewById(R.id.l2c);
        if(PageConsumerEntity.getL2C() == null)
        {
            l2c.setText("");
        }
        else
        {
            l2c.setText(new String(PageConsumerEntity.getL2C()));
        }

        l2d= (EditText) v.findViewById(R.id.l2d);
        if(PageConsumerEntity.getL2D() == null)
        {
            l2d.setText("");
        }
        else
        {
            l2d.setText(new String(PageConsumerEntity.getL2D()));
        }

        l3c= (EditText) v.findViewById(R.id.l3c);
        if(PageConsumerEntity.getL3C() == null)
        {
            l3c.setText("");
        }
        else
        {
            l3c.setText(new String(PageConsumerEntity.getL3C()));
        }

        l3d= (EditText) v.findViewById(R.id.l3d);
        if(PageConsumerEntity.getL3D() == null)
        {
            l3d.setText("");
        }
        else
        {
            l3d.setText(new String(PageConsumerEntity.getL3D()));
        }

        if(back == 1)
        {
//            String meterTypeValue = PageConsumerEntity.getMeterType();
            if (!meterTypeValue.equals(null)) {
                int spinnerPosition = mtypeadapter.getPosition(meterTypeValue);
                mtrtypespinner.setSelection(spinnerPosition);
            }

 //           String ctRatioValue = PageConsumerEntity.getCTRatio();
            if (!ctRatioValue.equals(null)) {
                int spinnerPosition = ctratioadapter.getPosition(ctRatioValue);
                ctratiospinner.setSelection(spinnerPosition);
            }

   //         String ctTypeValue = PageConsumerEntity.getCTType();
            if (!ctTypeValue.equals(null)) {
                int spinnerPosition = cttypeadapter.getPosition(ctTypeValue);
                cttypespinner.setSelection(spinnerPosition);
            }

     //       String cctRatioValue = PageConsumerEntity.getCCTRatio();
            if (!cctRatioValue.equals(null)) {
                int spinnerPosition = cctratioadapter.getPosition(cctRatioValue);
                cctratiospinner.setSelection(spinnerPosition);
            }

            ctmake.setText(new String(PageConsumerEntity.getCTMake()));

            l1c.setText(new String(PageConsumerEntity.getL1C()));

            l1d.setText(new String(PageConsumerEntity.getL1D()));

            l2c.setText(new String(PageConsumerEntity.getL2C()));

            l2d.setText(new String(PageConsumerEntity.getL2D()));

            l3c.setText(new String(PageConsumerEntity.getL3C()));

            l3d.setText(new String(PageConsumerEntity.getL3D()));
        }

        return v;
    }

    private boolean IsChangeEnabled= true;
    private void openAlert(final Button Changebtn)
    {
        if(IsChangeEnabled)
        {
            IsChangeEnabled= false;
            Changebtn.setText("Undo");

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(getActivity().getTitle() + " decision");
            alertDialogBuilder.setMessage("Are You Sure To Change The MNO & MAKE?");

            // set positive button: Yes message
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // go to a new activity of the app
                    // For MAKE Change
                    mno.setClickable(true);
                    mno.setCursorVisible(true);
                    mno.setFocusable(true);
                    mno.setFocusableInTouchMode(true);

                    // For MAKE Change
                    make.setClickable(true);
                    make.setCursorVisible(true);
                    make.setFocusable(true);
                    make.setFocusableInTouchMode(true);
                }
            });

            // set negative button: No message
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // cancel the alert box and put a Toast to the user
                    Changebtn.setText("Change");
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            // show alert
            alertDialog.show();
        }
        else
        {
            IsChangeEnabled= true;
            Changebtn.setText("Change");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(getActivity().getTitle() + " decision");
            alertDialogBuilder.setIcon(R.drawable.alert_icon);
            alertDialogBuilder.setMessage("Are You Sure To Undo The Changes?");

            // set positive button: Yes message
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // go to a new activity of the app
                    mno.setText(new String(PageConsumerEntity.getMNO()));
                    make.setText(new String(PageConsumerEntity.getMake()));
                    // For MAKE Change
                    mno.setClickable(false);
                    mno.setCursorVisible(false);
                    mno.setFocusable(false);
                    mno.setFocusableInTouchMode(false);
                    mno.setText(new String(PageConsumerEntity.getPreMNO()));

                    // For MAKE Change
                    make.setClickable(false);
                    make.setCursorVisible(false);
                    make.setFocusable(false);
                    make.setFocusableInTouchMode(false);
                    make.setText(new String(PageConsumerEntity.getPreMake()));

                }
            });

            // set negative button: No message
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // cancel the alert box and put a Toast to the user
                    Changebtn.setText("Undo");
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            // show alert
            alertDialog.show();
        }
    }

    @Override
    public void onClick(View view)
    {
        if(!ValidateData())
        {
            Fragment fragment = new Checking();
            getFragmentManager().beginTransaction()
                    .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private boolean ValidateData()
    {
        boolean error=false;
        String strmsg= "Please Enter ";

        if(aliasname.getText().toString().trim().length() == 0)
        {
            strmsg += ", Alias Name";
            error = true;
        }
        if(cntctno1.getText().toString().trim().length() == 0)
        {
            strmsg += ", Contact No";
            error = true;
        }
        else if ((cntctno1.getText().toString().trim().length() < 10) || (cntctno1.getText().toString().trim().length() > 13))
        {
            strmsg += ", Contact No between 10 and 13 characters";
            error = true;
        }
        if (cntctno2.getText().toString().trim().length() > 0)
        {
            if ((cntctno2.getText().toString().trim().length() < 10) || (cntctno2.getText().toString().trim().length() > 13))
            {
                strmsg += ", Contact No2 between 10 and 13 characters";
                error = true;
            }
        }
        if (cntctno3.getText().toString().trim().length() > 0)
        {
            if ((cntctno3.getText().toString().trim().length() < 10) || (cntctno3.getText().toString().trim().length() > 13))
            {
                strmsg += ", Contact No3 between 10 and 13 characters";
                error = true;
            }
        }
        if(email.getText().toString().trim().length() > 0)
        {
            if (email.getText().toString().trim().contains("@") == false)
            {
                strmsg += ", Email id with @";
                error = true;
            }
        }
        if(location.getText().toString().trim().length() == 0)
        {
            strmsg += ", Nearest Location";
            error = true;
        }
        if(mno.getText().toString().trim().length() == 0)
        {
            strmsg += ", Meter No";
            error = true;
        }
        if(make.getText().toString().trim().length() == 0)
        {
            strmsg += ", Make";
            error = true;
        }
        if(mtrtypespinner.getSelectedItem().toString().equals("Select"))
        {
            strmsg += ", Meter Type";
            error = true;
        }
        else if(mtrtypespinner.getSelectedItem().toString().equals("CT OPT"))
        {
            if(ctratiospinner.getSelectedItem().toString().equals("Select"))
            {
                strmsg += ", CT Ratio";
                error = true;
            }
            if(cttypespinner.getSelectedItem().toString().equals("Select"))
            {
                strmsg += ", CT Type";
                error = true;
            }
            if(cctratiospinner.getSelectedItem().toString().equals("Select"))
            {
                strmsg += ", CCT Ratio";
                error = true;
            }
            if(ctmake.getText().toString().trim().length() == 0)
            {
                strmsg += ", CT Make";
                error = true;
            }
        }
        if(error == true)
        {
            String replacedString =strmsg.replace("Please Enter ,", "Please Enter ");
            Toast.makeText(getActivity(), replacedString, Toast.LENGTH_SHORT).show();
        }

        PageConsumerEntity.setAliasName(aliasname.getText().toString().trim());
        PageConsumerEntity.setContactNo1(cntctno1.getText().toString().trim());
        PageConsumerEntity.setContactNo2(cntctno2.getText().toString().trim());
        PageConsumerEntity.setContactNo3(cntctno3.getText().toString().trim());
        PageConsumerEntity.setContactPerson(cntctprsn.getText().toString().trim());
        PageConsumerEntity.setEmail(email.getText().toString().trim());
        PageConsumerEntity.setLandmark(location.getText().toString().trim());
        PageConsumerEntity.setMNO(mno.getText().toString().trim());
        PageConsumerEntity.setMake(make.getText().toString().trim());
        PageConsumerEntity.setCTMake(ctmake.getText().toString().trim());
        PageConsumerEntity.setL1C(l1c.getText().toString().trim());
        PageConsumerEntity.setL1D(l1d.getText().toString().trim());
        PageConsumerEntity.setL2C(l2c.getText().toString().trim());
        PageConsumerEntity.setL2D(l2d.getText().toString().trim());
        PageConsumerEntity.setL3C(l3c.getText().toString().trim());
        PageConsumerEntity.setL3D(l3d.getText().toString().trim());

        return  error;
    }
}
