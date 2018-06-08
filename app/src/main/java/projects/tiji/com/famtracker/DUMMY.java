package projects.tiji.com.famtracker;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class DUMMY extends AppCompatActivity implements OnMapReadyCallback {
    Double user_latitude;
    Double user_longitude;
    String name2;
    FirebaseAuth auth;
    DatabaseReference locreference,reference;
    FirebaseUser user;
    Double latitude;
    Double longitude;
    String uid;
    private GoogleMap mMap,mMap1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_navigation_drawer);
        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();
        uid=user.getUid();
        reference=FirebaseDatabase.getInstance().getReference();
        locreference= FirebaseDatabase.getInstance().getReference().child("Users");

       String title1=getIntent().getExtras().getString("Longitude");
        String title2=getIntent().getExtras().getString("Latitiude");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latitude = Double.parseDouble(title2) ;
        longitude = Double.parseDouble(title1) ;
locreference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        user_latitude=dataSnapshot.child(uid).child("lng").getValue(Double.class);
        user_longitude=dataSnapshot.child(uid).child("lat").getValue(Double.class);
        name2=dataSnapshot.child(uid).child("name").getValue(String.class);
        Geocoder geocoder1 = new Geocoder(getApplicationContext());
        try {
            List<Address> addresses =
                    geocoder1.getFromLocation(user_latitude, user_longitude, 1);
            String result = addresses.get(0).getSubLocality()+":";
            result += addresses.get(0).getLocality()+":";
            result += addresses.get(0).getCountryName();
            LatLng latLng2 = new LatLng(user_latitude,user_longitude);
            BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            mMap1.addMarker(new MarkerOptions().position(latLng2).title(name2).icon(bitmapDescriptor));
            mMap1.setMaxZoomPreference(20);
            mMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2,10.2f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});



/*
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(latitude, longitude, 1);
            String result = addresses.get(0).getSubLocality()+":";
            result += addresses.get(0).getLocality()+":";
            result += addresses.get(0).getCountryName();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title(result));
            mMap.setMaxZoomPreference(20);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap1=googleMap;
        String title1=getIntent().getExtras().getString("Longitude");
        String title2=getIntent().getExtras().getString("Latitiude");
        String name=getIntent().getExtras().getString("Name");
        latitude = Double.parseDouble(title2) ;
        longitude = Double.parseDouble(title1) ;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(longitude, latitude);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(longitude, latitude, 1);
            String result = addresses.get(0).getSubLocality()+":";
            result += addresses.get(0).getLocality()+":";
            result += addresses.get(0).getCountryName();
            LatLng latLng = new LatLng(longitude, latitude);
        //    mMap.addMarker(new MarkerOptions().position(latLng).title(name + '\n'+ result));
            mMap.addMarker(new MarkerOptions().position(latLng).title(name));
            mMap.setMaxZoomPreference(20);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));


        } catch (IOException e) {
            e.printStackTrace();
        }
        //mMap.addMarker(new MarkerOptions().position(sydney).title("im here"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
