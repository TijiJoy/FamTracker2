package projects.tiji.com.famtracker;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tg on 29-05-2018.
 */
public class SingleInstaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String post_key = null;
    FirebaseAuth auth;
    Double latitude;
    Double longitude;
    CreateUser createUser = null;
    LatLng latLng;
    String circlememeberid;
    DatabaseReference reference, usersReference;
    FirebaseUser user;
    GoogleMap mMap;

    private String Lat1;
    private String Lng1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_navigation_drawer);

        Lat1 = getIntent().getExtras().getString("Latitude");
        Lng1 = getIntent().getExtras().getString("Longitude");
        latitude=Double.parseDouble(Lat1);
        longitude=Double.parseDouble(Lng1);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(latitude,longitude, 1);
            String result = addresses.get(0).getSubLocality() + ":";
            result += addresses.get(0).getLocality() + ":";
            result += addresses.get(0).getCountryName();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title(result)).showInfoWindow();
            mMap.setMaxZoomPreference(20);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}



