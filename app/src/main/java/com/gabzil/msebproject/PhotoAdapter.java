package com.gabzil.msebproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 456690 on 5/9/2015.
 */
public class PhotoAdapter extends ArrayAdapter<PhotoEntity>
{
    private ArrayList<PhotoEntity> PhotoList;
    private TaskDeleteComplete mCallback;

    public PhotoAdapter(Context context, int textViewResourceId, ArrayList<PhotoEntity> photoList,TaskDeleteComplete listner)
    {
        super(context, textViewResourceId, photoList);
        this.PhotoList = new ArrayList<PhotoEntity>();
        this.PhotoList.addAll(photoList);
        this.mCallback=listner;
    }

    private class ViewHolder
    {
        View view;
        TextView photoname;
        TextView path;
        ImageView img1;
        Button delete;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        ViewHolder holder = null;

        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.myphotolist, null);

            holder = new ViewHolder();

            holder.photoname = (TextView) convertView.findViewById(R.id.photoname);
     //       holder.path = (TextView) convertView.findViewById(R.id.path);
            holder.img1 = (ImageView) convertView.findViewById(R.id.showImg1);

            holder.delete=(Button) convertView.findViewById(R.id.delete);

            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setTitle("Decision");
                    alertDialogBuilder.setIcon(R.drawable.alert_icon);
                    alertDialogBuilder.setMessage("Do You Want To Delete???");

                    // set positive button: Yes message
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // go to a new activity of the app
                            mCallback.OnDeleteComplete(position);
                        }
                    });

                    // set negative button: No message
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // cancel the alert box and put a Toast to the user
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show alert
                    alertDialog.show();
                }
            });

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
                PhotoEntity data = PhotoList.get(position);

//        holder.path.setText("Path: " +data.getPath());
                holder.photoname.setText("Photo_Name: " + data.getPhotoName());
                File imgFile = new File(data.getPath());

                try {
                    if (imgFile.exists()) {
                        try {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            holder.img1.setImageBitmap(myBitmap);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Out Of Memory", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext().getApplicationContext(), "No Image",
                            Toast.LENGTH_SHORT).show();
                }
        return convertView;
    }
}