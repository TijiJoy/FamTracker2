package projects.tiji.com.famtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    private EditText  logInEmail;
    private EditText  logInPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logInEmail=(EditText)findViewById(R.id.logInEmail);
        logInPass=(EditText)findViewById(R.id.logInPass);
        progressDialog=new ProgressDialog(LoginActivity.this);
        mAuth=FirebaseAuth.getInstance();
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }
    public void loginButtonClicked(View view){
        String  email=logInEmail.getText().toString().trim();
       String  pass=logInPass.getText().toString().trim();

      //  if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pass)){
        progressDialog.setTitle("Logging in..... ");
        progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //checkUserExists();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "User Successfully Logged In", Toast.LENGTH_LONG).show();
                        Intent myIntent=new Intent(LoginActivity.this,NavigationDrawerActivity.class);
                        startActivity(myIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


}
