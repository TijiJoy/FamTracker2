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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText passField;
    private EditText emailField;
    private FirebaseAuth  mAuth;
    private DatabaseReference   mDatabase;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog=new ProgressDialog(RegisterActivity.this);
        emailField=(EditText)findViewById(R.id.emailField);
        mAuth=FirebaseAuth.getInstance();
       // mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog=new ProgressDialog(RegisterActivity.this);
    }
    public void registerButtonClicked(View  view) {
      progressDialog.setMessage("Checking email address....");

        mAuth.fetchProvidersForEmail(emailField.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if (task.isSuccessful()) {
                   progressDialog.dismiss();
                    boolean check = !task.getResult().getProviders().isEmpty();
                    if (!check) {

                        Intent myIntent=new Intent(RegisterActivity.this,PasswordActivity.class);
                        myIntent.putExtra("email",emailField.getText().toString());
                        startActivity(myIntent);

                    } else {
                       progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }


}
