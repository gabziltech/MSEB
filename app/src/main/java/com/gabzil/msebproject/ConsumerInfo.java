package com.gabzil.msebproject;

import android.app.Application;

/**
 * Created by Yogesh on 10/10/2015.
 */
public class ConsumerInfo extends Application
{
    private CustomerInfoEntity ConsumerDetailsData;

    public  CustomerInfoEntity getConsumerDetails()
    {
        return ConsumerDetailsData;
    }
    public void setConsumerDetails(CustomerInfoEntity ConsumerDetails1)
    {
        ConsumerDetailsData = ConsumerDetails1;
    }
}
