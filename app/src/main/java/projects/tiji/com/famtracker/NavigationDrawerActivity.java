package projects.tiji.com.famtracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {


    private LocationManager manager;
    private LocationListener locationListener;
    FirebaseAuth auth;
    private GoogleMap mMap;
    String uid;
    // GoogleApiClient client;
    // LocationRequest request;
    Double latitude;
    Double longitude;
    LatLng latLng;
    DatabaseReference locreference,reference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();
        uid=user.getUid();
        reference=FirebaseDatabase.getInstance().getReference().child("Users");
        locreference= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //get the location service
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(manager.isProviderEnabled(manager.NETWORK_PROVIDER))
        {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude and longitude from the location
                   latitude = location.getLatitude();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ;

                     longitude = location.getLongitude();

                    locreference.child("lng").setValue(latitude);
                    locreference.child("lat").setValue(longitude);

                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getSubLocality()+":";
                        result += addresses.get(0).getLocality()+":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(latLng).title("My Location")).showInfoWindow();
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });


        }
        else if(manager.isProviderEnabled(manager.GPS_PROVIDER))
        {
          manager.requestLocationUpdates(manager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
              @Override
              public void onLocationChanged(Location location) {
                  //get the latitude and longitude from the location
                   latitude = location.getLatitude();

                  longitude = location.getLongitude();

                locreference.child("lat").setValue(latitude);
                  locreference.child("lng").setValue(longitude);
                  //get the location name from latitude and longitude
                  Geocoder geocoder = new Geocoder(getApplicationContext());
                  try {
                      List<Address> addresses =
                              geocoder.getFromLocation(latitude, longitude, 1);
                      String result = addresses.get(0).getSubLocality()+":";
                      result += addresses.get(0).getLocality()+":";
                      result += addresses.get(0).getCountryName();
                      LatLng latLng = new LatLng(latitude, longitude);
                      mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                      mMap.setMaxZoomPreference(20);
                      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }

              @Override
              public void onStatusChanged(String s, int i, Bundle bundle) {

              }

              @Override
              public void onProviderEnabled(String s) {

              }

              @Override
              public void onProviderDisabled(String s) {

              }
          });
        }

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.signOut) {
            auth.signOut();
            Intent myIntent = new Intent(NavigationDrawerActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish();
        } else if (id == R.id.joinCircle)
        {
            Intent myIntent = new Intent(NavigationDrawerActivity.this, joinCircleActivity.class);
            startActivity(myIntent);


        } else if (id == R.id.joinedCircle) {
          Intent myIntent = new Intent(NavigationDrawerActivity.this, MyCircleActivity.class);
          //  Intent myIntent = new Intent(NavigationDrawerActivity.this, MyGroup.class);
            startActivity(myIntent);

        }
        else if (id == R.id.myjoinedCircle) {
            Intent myIntent = new Intent(NavigationDrawerActivity.this,JoinedCircleActivity.class);
            //  Intent myIntent = new Intent(NavigationDrawerActivity.this, MyGroup.class);
            startActivity(myIntent);

        }
        else if (id == R.id.shareLoc) {
            //Location loc = null;

          Intent myIntent = new Intent(Intent.ACTION_SEND);
          myIntent.setType("text/plain");
        myIntent.putExtra(Intent.EXTRA_TEXT,"My Location is:"+"http://maps.google.com/maps?q=loc:"+latitude+","+longitude);
          startActivity(myIntent.createChooser(myIntent,"Share Using: "));

        } else if (id == R.id.inviteMembers) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  String  code=dataSnapshot.child(uid).child("code").getValue(String.class);
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    myIntent.putExtra(Intent.EXTRA_TEXT,"Hi join my Circle to share location.My Circle Code is"+code);
                    startActivity(myIntent.createChooser(myIntent,"Share Using: "));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

































}