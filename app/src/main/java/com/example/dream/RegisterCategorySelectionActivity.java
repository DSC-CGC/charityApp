package com.example.dream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.data.model.User;

public class RegisterCategorySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_category_selection);
        findViewById(R.id.ngoRegisterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterCategorySelectionActivity.this,NgoCategoryActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.bankRegBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterCategorySelectionActivity.this,BankRegister.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.volRegBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterCategorySelectionActivity.this,VolCategoryRegister.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.userRegBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterCategorySelectionActivity.this,UserAccount.class);
                startActivity(intent);
            }
        });
    }
}
