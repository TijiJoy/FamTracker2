package projects.tiji.com.famtracker;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     FirebaseAuth auth;
    FirebaseUser user;
PermissionManager manager;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        MyReceiver mReceiver=new MyReceiver(this);
        registerReceiver(mReceiver,filter);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            setContentView(R.layout.activity_main);
            manager = new PermissionManager() {};
            manager.checkAndRequestPermissions(this);
        }

        else
        {

            Intent myIntent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
            startActivity(myIntent);
            finish();
        }
    }



public void goToLoginActivity(View v)
{
    Intent myIntent=new Intent(MainActivity.this,LoginActivity.class);
    startActivity(myIntent);
}
public void goToRegisterActivity(View v)
{
    Intent myIntent=new Intent(MainActivity.this,RegisterActivity.class);
    startActivity(myIntent);
}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       manager.checkResult(requestCode,permissions,grantResults);
        ArrayList<String>denied_permissions=manager.getStatus().get(0).denied;
        if(denied_permissions.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Permissions enabled",Toast.LENGTH_SHORT).show();
        }
    }


}


