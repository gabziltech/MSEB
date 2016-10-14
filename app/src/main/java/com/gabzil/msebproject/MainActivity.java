package com.gabzil.msebproject;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity implements OnReaderTaskCompleted
{
    private String[] mNavigationDrawerItemTitles;
    ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tm = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        String IMEI =tm.getDeviceId();
        CheckReader(IMEI);

        try
        {
            mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);

            //Set the array if increasing the menu
            ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[2];

            drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_noti, "Verification Report");
            drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_contact, "Contact");

            DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
            mDrawerList.setAdapter(adapter);

            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

            mTitle = mDrawerTitle = getTitle();
            android.support.v7.widget.Toolbar toolbar = new android.support.v7.widget.Toolbar(getApplicationContext());
            toolbar.setLogo(R.drawable.ic_drawer);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    mDrawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close
            ) {

                /**
                 * Called when a drawer has settled in a completely closed state.
                 */
                public void onDrawerClosed(View view)
                {
                    super.onDrawerClosed(view);
                    getSupportActionBar().setTitle(mTitle);
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 */
                public void onDrawerOpened(View drawerView)
                {
                    super.onDrawerOpened(drawerView);
                    getSupportActionBar().setTitle(mDrawerTitle);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            if(savedInstanceState==null)
            {
                selectItem(0);
            }
        }
        catch(Exception Ex)
        {
            Log.println(3, "Err", Ex.getMessage());
        }
    }

    public void CheckReader(String imei){
        new ReaderAccess(getApplicationContext(), this).execute(imei);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void setTitle(CharSequence title)
    {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    private void selectItem(int position)
    {
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new VerificationReport();
                break;
            case 1:
                fragment = new Contact();
                break;

            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            getSupportActionBar().setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void OnReaderTaskCompleted(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            String CheckLogin1 = obj.getString("CheckReaderResult");
            if (CheckLogin1 != "null" && CheckLogin1.length() > 0) {
                if (CheckLogin1.equals("false")){
                    Toast.makeText(getApplicationContext(),"This IMEI is not registered, Please contact to your admin for registration",Toast.LENGTH_SHORT).show();
                    finish();
                    System.exit(0);
                }
                if (CheckLogin1.equals("Error")){
                    Toast.makeText(getApplicationContext(),"Some problem occured,Please try again",Toast.LENGTH_SHORT).show();
                }
            }
            else  {
                Toast.makeText(getApplicationContext(),"Some problem occured,Please try again",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectItem(position);
        }
    }
}