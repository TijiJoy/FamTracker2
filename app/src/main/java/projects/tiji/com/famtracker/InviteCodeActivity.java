package projects.tiji.com.famtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCodeActivity extends AppCompatActivity {
String name,email,password,date,issharing,code,lat,lng;
    Uri uri;
    TextView t1;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userId;
    private DatabaseReference reference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1=(TextView)findViewById(R.id.textView);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog = new ProgressDialog(InviteCodeActivity.this);
        Intent myIntent=getIntent();
        if(myIntent!=null)
        {
            name=myIntent.getStringExtra("name");
            email=myIntent.getStringExtra("email");
            password=myIntent.getStringExtra("password");
            date=myIntent.getStringExtra("date");
           issharing=myIntent.getStringExtra("isSharing");
            uri=myIntent.getParcelableExtra("imageUri");
            code=myIntent.getStringExtra("code");
            lat=myIntent.getStringExtra("lat");
            lng=myIntent.getStringExtra("lng");

        }
        t1.setText(code);
    }
    public void registerUser(View V)

    {
      progressDialog.setMessage("Pleas wait while we are creating an account for you.");
      progressDialog.show();
        final Task<AuthResult> authResultTask = auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = auth.getCurrentUser();
                    Double na = null;
                    CreateUser createUser = new CreateUser(name, email, password, code, "false", na, na, "na", user.getUid());
                    //  CreateUser createUser=new CreateUser(name,email,password,code,user.getUid());


                    userId = user.getUid();

                    reference.child(userId).setValue(createUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(InviteCodeActivity.this, NavigationDrawerActivity.class);
                                startActivity(myIntent);
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "could not  register user", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
