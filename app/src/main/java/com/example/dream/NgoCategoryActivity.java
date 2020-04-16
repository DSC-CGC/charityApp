package com.example.dream;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NgoCategoryActivity extends AppCompatActivity {
    ListView categoryListView;

    ArrayAdapter<String> categoryAdapter;
    String[] outputCategory = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        categoryListView = findViewById(R.id.categoryList);
        String[] categories = getResources().getStringArray(R.array.categoryList);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, categories);
        categoryListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        categoryListView.setAdapter(categoryAdapter);


    }

    public void Continue(View view) {
        SparseBooleanArray categoryChecked = categoryListView.getCheckedItemPositions();
        ArrayList<String> selectedCategory = new ArrayList<>();

        for (int i = 0; i < categoryChecked.size(); i++) {
            int position = categoryChecked.keyAt(i);
            if (categoryChecked.valueAt(i)) {
                selectedCategory.add(categoryAdapter.getItem(position));
            }

        }
        outputCategory = new String[selectedCategory.size()];

        for (int i = 0; i < selectedCategory.size(); i++) {
            outputCategory[i] = selectedCategory.get(i);

        }
        Bundle b = new Bundle();
        b.putStringArray("Selected Category", outputCategory);
        if (outputCategory.length == 0) {
            Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), NgoRegister.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}