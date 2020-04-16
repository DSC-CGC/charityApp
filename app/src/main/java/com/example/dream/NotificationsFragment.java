package com.example.dream;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference recyclerData;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (firebaseUser == null) {
            Intent intent = new Intent(getActivity(), PhoneInput.class);
            startActivity(intent);
        } else {
            recyclerData = FirebaseDatabase.getInstance().getReference().child("Notifications").child(uId);
            recyclerData.keepSynced(true);
            final DatabaseReference delete=FirebaseDatabase.getInstance().getReference().child("Notifications").child(uId);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            FirebaseRecyclerOptions<NotificationResult> options = new FirebaseRecyclerOptions.Builder<NotificationResult>()
                    .setQuery(recyclerData, NotificationResult.class)
                    .build();
            final FirebaseRecyclerAdapter<NotificationResult, DataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NotificationResult, DataViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull DataViewHolder holder, final int i, @NonNull NotificationResult model) {
                    holder.nameTv.setText(model.getNameN());
                    holder.timeTv.setText(model.getTime());
                    holder.dateTv.setText(model.getDate());
                    holder.thingTv.setText(model.getThing());
                    holder.quantityTv.setText(model.getQuantity());
                    final String phoneNumber = model.getPhoneN();
                    holder.call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));

                            startActivity(intent1);
                        }
                    });
                    final Double Latitude = model.getLatitude();
                    final Double Longitude = model.getLongitude();

                    holder.directions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", Latitude, Longitude);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setPackage("com.google.android.apps.maps");
                            startActivity(intent);
                        }
                    });

                }



                @NonNull
                @Override
                public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                    View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_cardview, viewGroup, false);
                    return new DataViewHolder(view1);
                }


            };
            recyclerView.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.startListening();
        }
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, thingTv, quantityTv, timeTv, dateTv;
        Button call, directions;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            thingTv = itemView.findViewById(R.id.thingTv);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            call = itemView.findViewById(R.id.callButton);
            directions = itemView.findViewById(R.id.directionButton);
        }
    }
}