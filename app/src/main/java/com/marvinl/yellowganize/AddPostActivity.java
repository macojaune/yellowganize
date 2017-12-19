package com.marvinl.yellowganize;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

public class AddPostActivity extends Activity {
    final AppBdd db = Room.databaseBuilder(AddPostActivity.this, AppBdd.class, "Yellowganize").build();

    ImageView pic;
    Button selectPicBtn, selectDateBtn, selectTimeBtn, submitBtn;
    EditText caption;
    Uri imgURI;
    int pYear, pMonth, pDay, pHour, pMin;
    boolean dateIsSet = false, timeIsSet = false;

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
        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vérifie données
                if (null != imgURI && dateIsSet && timeIsSet && (caption.getText().length() > 0)) {
                    //Crée Post
                    Post p = new Post(imgURI,pYear,pMonth,pDay,pHour,pMin,caption.getText().toString());
                    //Save Post
                    new AddPostTask().execute(p);
                }
            }
        });
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 1);
    }

    public void pickDate() {
        Calendar cal = Calendar.getInstance();
        if (pDay > 0 && pMonth > 0 && pYear > 0)
            cal.set(pYear, pMonth, pDay);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pDay = dayOfMonth;
                pMonth = month +1;
                pYear = year;
                String date = pDay + "/" + pMonth + "/" + pYear;
                selectDateBtn.setText(date);
                dateIsSet= true;
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void pickTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pHour = hourOfDay;
                pMin = minute;
                String time = pHour + ":" + pMin;
                selectTimeBtn.setText(time);
                timeIsSet=true;
            }
        }, pHour, pMin, true);
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                imgURI = data.getData();
                this.pic.setImageURI(imgURI);
            }
        }
    }
    private class AddPostTask extends AsyncTask<Post, Void, Void> {
        @Override
        protected Void doInBackground(Post... p) {
            db.postDao().insertAll(p);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            db.close();
            //Redirige vers home
            finish();
        }
    }
}
