package com.gabzil.msebproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Yogesh on 10/10/2015.
 */
public class Checking extends Fragment
{
    CustomerInfoEntity PageConsumerEntity;
    Button nxtbtn,backbtn;
    EditText work,remark;
    Spinner shiftspinner;
    int back=0,backbttn=0,selectionPosition;
    RadioButton BoxSealButton,TerminalCoverSealButton,MDResetSealButton,BodySealButton;

    RadioGroup boxgrp,covergrp,resetgrp,bodygrp;


    public Checking()
    {
        // Required empty public constructor
    }

    Checking(int backbttn) {
        this.backbttn=backbttn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_checking, container, false);

        final ArrayAdapter<CharSequence> shiftadapter= ArrayAdapter.createFromResource(getActivity(), R.array.shifts_array, android.R.layout.simple_spinner_item);
        shiftadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shiftspinner=(Spinner)v.findViewById(R.id.shift_spinner);
        shiftspinner.setAdapter(shiftadapter);

        boxgrp = (RadioGroup) v.findViewById(R.id.boxsealgrp);
        boxgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = boxgrp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                BoxSealButton = (RadioButton) getActivity().findViewById(selectedId);
                PageConsumerEntity.setBoxSeal((String) BoxSealButton.getText());
            }
        });

        covergrp = (RadioGroup) v.findViewById(R.id.coversealgrp);
        covergrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int selectedId = covergrp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                TerminalCoverSealButton = (RadioButton) getActivity().findViewById(selectedId);
                PageConsumerEntity.setTerminalCoverSeal((String) TerminalCoverSealButton.getText());
            }
        });

        resetgrp = (RadioGroup) v.findViewById(R.id.resetsealgrp);
        resetgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int selectedId = resetgrp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                MDResetSealButton = (RadioButton) getActivity().findViewById(selectedId);
                PageConsumerEntity.setMDResetSeal((String) MDResetSealButton.getText());
            }
        });

        bodygrp = (RadioGroup) v.findViewById(R.id.bodysealgrp);
        bodygrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int selectedId = bodygrp.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                BodySealButton = (RadioButton) getActivity().findViewById(selectedId);
                PageConsumerEntity.setBodySeal((String) BodySealButton.getText());
            }
        });

        shiftspinner= (Spinner) v.findViewById(R.id.shift_spinner);
        shiftspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

              //  selectionPosition= shiftadapter.getPosition(shiftspinner.getSelectedItem().toString());

                String shftspinner = shiftspinner.getSelectedItem().toString();
                PageConsumerEntity.setNoOfShifts(shftspinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                PageConsumerEntity.setNoOfShifts("1");
            }
        });

        nxtbtn = (Button) v.findViewById(R.id.nextbtn);
        nxtbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(!ValidateData())
                {
                    Fragment fragment = new GPS();
                    getFragmentManager().beginTransaction()
                            .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        backbtn = (Button) v.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                back=1;
                Fragment fragment = new ConsumerInformation(back);
                    getFragmentManager().beginTransaction()
                            .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                            .addToBackStack(null)
                            .commit();
            }
        });

        PageConsumerEntity = ((ConsumerInfo) getActivity().getApplicationContext()).getConsumerDetails();

        work= (EditText) v.findViewById(R.id.work);
        work.setText(new String(PageConsumerEntity.getNatureOfWork()));

        remark= (EditText) v.findViewById(R.id.remark);
        if(PageConsumerEntity.getRemarks() == null)
        {
            remark.setText("");
        }
        else
        {
            remark.setText(new String(PageConsumerEntity.getRemarks()));
        }

        String shiftValue = PageConsumerEntity.getNoOfShifts();
//        if (!shiftValue.equals(null))
//        {
//            int spinnerPosition = shiftadapter.getPosition(shiftValue);
//            shiftspinner.setSelection(spinnerPosition);
//        }

        if(backbttn == 1) {
            work.setText(new String(PageConsumerEntity.getNatureOfWork()));

            remark.setText(new String(PageConsumerEntity.getRemarks()));
           // String shiftValue = PageConsumerEntity.getNoOfShifts();
            if (!shiftValue.equals(null))
            {
                int spinnerPosition = shiftadapter.getPosition(shiftValue);
                shiftspinner.setSelection(spinnerPosition);
            }
        }

        return v;
    }

    private boolean ValidateData()
    {
        boolean error=false;
        String strmsg= "Please Enter ";

        if(work.getText().toString().trim().length() == 0)
        {
            strmsg += "Nature of Work";
            error = true;
        }
        if(shiftspinner.getSelectedItem().toString().equals("Select"))
        {
            strmsg += ", Number of Shifts";
            error = true;
        }
//        if (boxgrp.getCheckedRadioButtonId() == -1)
//        {
//            strmsg += ", Box Seal";
//            error = true;
//        }
//        if (covergrp.getCheckedRadioButtonId() == -1)
//        {
//            strmsg += ", Terminal Cover Seal";
//            error = true;
//        }
//        if (resetgrp.getCheckedRadioButtonId() == -1)
//        {
//            strmsg += ", MD Reset Seal";
//            error = true;
//        }
//        if (bodygrp.getCheckedRadioButtonId() == -1) {
//            strmsg += ", Body Seal";
//            error = true;
//        }

        if(error == true)
        {
            String replacedString =strmsg.replace("Please Enter ,", "Please Enter ");
            Toast.makeText(getActivity(), replacedString, Toast.LENGTH_SHORT).show();
        }

        PageConsumerEntity.setNatureOfWork(work.getText().toString().trim());
        PageConsumerEntity.setRemarks(remark.getText().toString().trim());

        return  error;
    }
}