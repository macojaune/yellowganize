package com.marvinl.yellowganize;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddPostActivity extends Activity {
    ImageView pic;
    Button selectPicBtn, selectDateBtn, selectTimeBtn, submitBtn;
    EditText caption;
    int pYear, pMonth, pDay, pHour,pMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        pic = (ImageView) findViewById(R.id.imageView);
        selectPicBtn = (Button) findViewById(R.id.selectPicBtn);
        selectDateBtn = (Button) findViewById(R.id.selectDateBtn);
        selectTimeBtn = (Button) findViewById(R.id.selectTimeBtn);
        caption = (EditText) findViewById(R.id.captionInput);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        selectPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ouvre gallery
                choosePhotoFromGallery();
            }
        });

    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 1);
    }
    public void pickDateTime(){
        Calendar cal = Calendar.getInstance();
       DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               pDay = dayOfMonth;
               pMonth = month;
               pYear = year;

           }
       },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
       datePickerDialog.show();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pHour = hourOfDay;
                pMin = minute;
            }
        },8,0,true);
        timePickerDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri contentURI = data.getData();
                this.pic.setImageURI(contentURI);
            }
        }
    }
}
