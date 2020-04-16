package com.example.dream;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;

    int radius;
    String typeText, donationCategory;
    Double Latitude, Longitude;
    int result = 0;
    Spinner typeSpinner, donationSpinner;
    Button findButton;
    ProgressDialog progressDialog;
    FirebaseDatabase getKey;
    String id;
    private Location mylocation;
    private GoogleApiClient googleApiClient;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpGClient();
        getKey = FirebaseDatabase.getInstance();
        id = getKey.getReference("AvailableVol").push().getKey();
        //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        typeSpinner = view.findViewById(R.id.selectType);
        donationSpinner = view.findViewById(R.id.donationType);
        findButton = view.findViewById(R.id.find);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (typeText.equals("Select Your Requirement")) {
                    Snackbar.make(view, "Please Select Your Requirement", Snackbar.LENGTH_LONG).show();
                }
                // ngo selection ke liye

                else if (typeText.equals("NGO")) {
                    if (donationCategory.equals("Select Donation Category")) {
                        Snackbar.make(view, "Please Select Donation Category", Snackbar.LENGTH_LONG).show();
                    } else {
                        if (Latitude != null && Longitude != null) {

                            radius = 15;
                            Location();
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage("Loading");
                            progressDialog.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (result == 1) {
                                        Intent intent = new Intent(getActivity(), AvailableResultActivity.class);
                                        intent.putExtra("Key", id);
                                        intent.putExtra("Category", typeText);
                                        intent.putExtra("DonationCategory", donationCategory);
                                        Bundle bundle = new Bundle();
                                        bundle.putDouble("Latitude", Latitude);
                                        bundle.putDouble("Longitude", Longitude);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                    } else if (result == 0) {
                                        progressDialog.dismiss();
                                        Snackbar.make(view, "No Result Found", Snackbar.LENGTH_LONG).show();

                                    }
                                }

                            }, 4000);
                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(view, "Location Not Found", Snackbar.LENGTH_LONG).show();

                        }
                    }
                } else if (typeText.equals("Volunteer")) {
                    if (donationCategory.equals("Select Donation Category")) {
                        Snackbar.make(view, "Please Select Donation Category", Snackbar.LENGTH_LONG).show();
                    } else {
                        if (Latitude != null && Longitude != null) {
                            radius = 5;
                            Location();
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage("Loading");
                            progressDialog.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (result == 1) {
                                        Intent intent = new Intent(getActivity(), AvailableResultActivity.class);
                                        intent.putExtra("Key", id);
                                        intent.putExtra("Category", typeText);
                                        intent.putExtra("DonationCategory", donationCategory);
                                        Bundle bundle = new Bundle();
                                        bundle.putDouble("Latitude", Latitude);
                                        bundle.putDouble("Longitude", Longitude);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                    } else if (result == 0) {
                                        progressDialog.dismiss();
                                        Snackbar.make(view, "No Result Found", Snackbar.LENGTH_LONG).show();

                                    }
                                }
                            }, 3000);
                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(view, "Location Not Found", Snackbar.LENGTH_LONG).show();

                        }
                    }
                } else if (typeText.equals("BloodBank")) {
                    if (Latitude != null && Longitude != null) {

                        radius = 15;
                        donationCategory = "Blood";
                        Location();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Loading");
                        progressDialog.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (result == 1) {
                                    Intent intent = new Intent(getActivity(), AvailableResultActivity.class);
                                    intent.putExtra("Key", id);
                                    intent.putExtra("Category", typeText);
                                    intent.putExtra("DonationCategory", "Bank");
                                    Bundle bundle = new Bundle();
                                    bundle.putDouble("Latitude", Latitude);
                                    bundle.putDouble("Longitude", Longitude);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                } else if (result == 0) {
                                    progressDialog.dismiss();
                                    Snackbar.make(view, "No Result Found", Snackbar.LENGTH_LONG).show();

                                } else {
                                    progressDialog.dismiss();
                                    Snackbar.make(view, "Location Not Found", Snackbar.LENGTH_LONG).show();

                                }
                            }
                        }, 5000);
                    } else {

                        Snackbar.make(view, "Location Not Found", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });

        String[] types = new String[]{"Select Your Requirement", "NGO", "Volunteer", "BloodBank"};

        ArrayAdapter typeAdapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item, types);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                typeText = typeSpinner.getItemAtPosition(position).toString();
                if (typeText.equals("BloodBank")) {
                    donationSpinner.setVisibility(View.INVISIBLE);
                } else {
                    donationSpinner.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] donationArray = getResources().getStringArray(R.array.donationCategoryView);
        ArrayAdapter donationAdapter = new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item,
                donationArray);
        donationSpinner.setAdapter(donationAdapter);
        donationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                donationCategory = donationSpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            Latitude = mylocation.getLatitude();
            Longitude = mylocation.getLongitude();

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "Connection is suspended", Toast.LENGTH_SHORT).show();
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                            .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                                            Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    getMyLocation();
                    break;
                case Activity.RESULT_CANCELED:

                    break;
            }
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(getActivity(),
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void Location() {
        DatabaseReference geoData = FirebaseDatabase.getInstance().getReference().child("GeoLocation").child(typeText);
        GeoFire geoFire = new GeoFire(geoData);

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(Latitude, Longitude), radius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, GeoLocation location) {
                if (key.length() > 0) {

                    FirebaseDatabase.getInstance().getReference().child("User").child(typeText).child(key)
                            .child(donationCategory).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                result = 1;
                                String ngoName = dataSnapshot.child("Name").getValue().toString();
                                String Address = dataSnapshot.child("Address").getValue().toString();
                                String ngoFromTime = dataSnapshot.child("FromTime").getValue().toString();
                                String ngoToTime = dataSnapshot.child("ToTime").getValue().toString();
                                String ngoPhone = dataSnapshot.child("Phone").getValue().toString();
                                String workingDays = dataSnapshot.child("Working Days").getValue().toString();
                                Map<String, Object> entryDetails = new HashMap<>();
                                entryDetails.put("Name", ngoName);

                                entryDetails.put("Phone", ngoPhone);

                                entryDetails.put("FromTime", ngoFromTime);
                                entryDetails.put("ToTime", ngoToTime);
                                entryDetails.put("WorkingDays", workingDays);
                                entryDetails.put("Address", Address);
                                entryDetails.put("uId", key);
                                if (typeText.equals("NGO")) {
                                    DatabaseReference putData = FirebaseDatabase.getInstance().getReference()
                                            .child("AvailableUser").child(id).child(typeText)
                                            .child(donationCategory).child(key);
                                    putData.setValue(entryDetails);

                                } else if (typeText.equals("Volunteer")) {
                                    DatabaseReference putData = FirebaseDatabase.getInstance().getReference()
                                            .child("AvailableUser").child(id).child(typeText)
                                            .child(donationCategory).child(key);
                                    putData.setValue(entryDetails);
                                } else if (typeText.equals("BloodBank")) {
                                    DatabaseReference putData = FirebaseDatabase.getInstance().getReference()
                                            .child("AvailableUser").child(id).child(typeText).child("Bank")
                                            .child(key);
                                    putData.setValue(entryDetails);
                                }

                            } else {

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {

                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }
}
