package com.example.dream;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendDetailsActivity extends AppCompatActivity {
    Double Latitude, Longitude;
    TextInputEditText nameEt, phoneEt, thingEt, quantityEt, dateEt;
    String name, phone, thing, quantity, dateString;
    TextView date, time;
    Calendar myCalendar;
    String mToTime;
    int dateS = 0, timeS = 0;
    String key;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_details);
        myCalendar = Calendar.getInstance();
        final Intent intent = getIntent();

firebaseDatabase=FirebaseDatabase.getInstance();
key=firebaseDatabase.getReference("Notifications").push().getKey();
        Bundle bundle = getIntent().getExtras();
        final String uId = intent.getStringExtra("uId");
        Latitude = bundle.getDouble("Latitude");
        Longitude = bundle.getDouble("Longitude");
        nameEt = findViewById(R.id.name);
        phoneEt = findViewById(R.id.phone);
        thingEt = findViewById(R.id.thing);
        quantityEt = findViewById(R.id.quantity);
        date = findViewById(R.id.fromTimeTv);
        time = findViewById(R.id.toTimeTv);
        dateEt = findViewById(R.id.date);
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                name = nameEt.getText().toString();
                phone = phoneEt.getText().toString();
                thing = thingEt.getText().toString();
                quantity = quantityEt.getText().toString();
                dateString = dateEt.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    nameEt.setError("Please Enter Name");
                    nameEt.requestFocus();

                } else if (TextUtils.isEmpty(phone)) {
                    phoneEt.setError("Please Enter Phone Number");
                    phoneEt.requestFocus();
                } else if (TextUtils.isEmpty(thing)) {
                    thingEt.setError("Please Enter Thing Name");
                    thingEt.requestFocus();
                } else if (TextUtils.isEmpty(quantity)) {
                    quantityEt.setError("Please Enter Quantity");
                    quantityEt.requestFocus();
                } else if (TextUtils.isEmpty(dateString)) {
                    dateEt.setError("Please Enter Pickup Date");
                    dateEt.requestFocus();
                } else if (timeS == 0) {
                    Snackbar.make(view, "Please Select Pickup Time", Snackbar.LENGTH_LONG).show();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(SendDetailsActivity.this);
                    progressDialog.setMessage("Uploading");
                    progressDialog.show();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Notifications").child(uId).child(key);
                    Map<String, Object> map = new HashMap<>();
                    map.put("NameN", name);
                    map.put("PhoneN", phone);
                    map.put("Thing", thing);
                    map.put("Quantity", quantity);
                    map.put("Time", mToTime);
                    map.put("Date", dateString);
                    map.put("Latitude", Latitude);
                    map.put("Longitude", Longitude);
                    databaseReference.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent1 = new Intent(SendDetailsActivity.this, MainActivity.class);
                            progressDialog.dismiss();
                            startActivity(intent1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view, e.toString(), Snackbar.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });

    }


    public void selectTime(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog toTimePickerDialog;
        toTimePickerDialog = new TimePickerDialog(SendDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                timeS = 1;
                mToTime = hourOfDay + ":" + minute;
                time.setText(mToTime);

            }
        }, hour, minute, false);
        toTimePickerDialog.show();
    }
}
