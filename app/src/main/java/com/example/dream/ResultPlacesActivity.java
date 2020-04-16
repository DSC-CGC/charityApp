package com.example.dream;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultPlacesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference recyclerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_places);
        recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        final String key = intent.getStringExtra("Key");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!😊");
        progressDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (key != null) {
                    progressDialog.dismiss();
                    recyclerData = FirebaseDatabase.getInstance().getReference().child("AvailablePlace").child(key);
                    recyclerData.keepSynced(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ResultPlacesActivity.this));
                    FirebaseRecyclerOptions<ResultPlaces> options = new FirebaseRecyclerOptions.Builder<ResultPlaces>()
                            .setQuery(recyclerData, ResultPlaces.class)
                            .build();
                    FirebaseRecyclerAdapter<ResultPlaces, DataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ResultPlaces, DataViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull DataViewHolder holder, int i, @NonNull ResultPlaces model) {
                            holder.name.setText(model.getName());
                            holder.phone.setText(model.getPhone());
                            holder.quantity.setText(model.getQuantity());
                            holder.date.setText(model.getUploadDate());
                            holder.thing.setText(model.getThingRequired());
                        }

                        @NonNull
                        @Override
                        public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.places_result_cardview, viewGroup, false);
                            return new DataViewHolder(view);
                        }
                    };
                    recyclerView.setAdapter(firebaseRecyclerAdapter);
                    firebaseRecyclerAdapter.startListening();

                }
            }
        }, 3000);

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, thing, date, phone;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTv);
            quantity = itemView.findViewById(R.id.quantityTv);
            thing = itemView.findViewById(R.id.thingTv);
            date = itemView.findViewById(R.id.dateTv);
            phone = itemView.findViewById(R.id.contactTv);


        }
    }
}
