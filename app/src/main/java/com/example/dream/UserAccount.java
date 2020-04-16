package com.example.dream;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserAccount extends AppCompatActivity {
    TextInputEditText ngoNameEt, ngoPhoneEt;
    String ngoName, ngoEmail, ngoPhone;
    FirebaseAuth mAuth;
    String uId;
    DatabaseReference userDatabase;
    Map<String, Object> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        ngoNameEt = findViewById(R.id.ngoName);

        ngoPhoneEt = findViewById(R.id.ngoContact);
        mAuth = FirebaseAuth.getInstance();

        uId = mAuth.getCurrentUser().getUid();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User").child("NGO").child(uId);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    rootRef.removeValue();
                }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference rootRef1 = FirebaseDatabase.getInstance().getReference().child("User").child("BloodBank").child(uId);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    rootRef1.removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference().child("User").child("Volunteer").child(uId);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    rootRef2.removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference rootRef3 = FirebaseDatabase.getInstance().getReference().child("GeoLocation").child("BloodBank").child(uId);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    rootRef3.removeValue();
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference rootRef4 = FirebaseDatabase.getInstance().getReference().child("GeoLocation").child("NGO").child(uId);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    rootRef4.removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference rootRef5 = FirebaseDatabase.getInstance().getReference().child("GeoLocation").child("Volunteer").child(uId);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    rootRef5.removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngoName = ngoNameEt.getText().toString();
                ngoPhone = ngoPhoneEt.getText().toString();
                if (TextUtils.isEmpty(ngoName)) {
                    ngoNameEt.setError("Please Enter Name");
                    ngoNameEt.requestFocus();

                } else if (ngoPhone.length() < 10) {
                    ngoPhoneEt.setError("Please Enter Valid Phone Number");
                    ngoPhoneEt.requestFocus();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(UserAccount.this);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();

                    userDatabase = FirebaseDatabase.getInstance().getReference().child("User").child("Donor").child(uId);
                    userDetails = new HashMap<>();
                    userDetails.put("Name", ngoName);
                    userDetails.put("Phone", ngoPhone);
                    userDatabase.setValue(userDetails);
                    DatabaseReference userProfile = FirebaseDatabase.getInstance().getReference().child("User").child(uId);
                    Map<String, Object> profileDetails = new HashMap<>();
                    profileDetails.put("Name", ngoName);
                    profileDetails.put("Phone", ngoPhone);
                    userProfile.setValue(profileDetails);
                    userProfile.setValue(profileDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent=new Intent(UserAccount.this,MainActivity.class);
                            progressDialog.dismiss();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserAccount.this, (CharSequence) e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
