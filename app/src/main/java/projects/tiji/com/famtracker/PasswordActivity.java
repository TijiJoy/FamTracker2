package projects.tiji.com.famtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
String email;
    EditText passswordField;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passswordField=(EditText) findViewById(R.id.passwordField);
    progressDialog = new ProgressDialog(PasswordActivity.this);
        Intent myIntent=getIntent();
        if(myIntent!=null)
        {
            email=myIntent.getStringExtra("email");
        }
    }
    public void NextButtonClicked(View v){

        if(passswordField.getText().toString().length()>6)
        {
          Intent myIntent=new Intent(PasswordActivity.this,NameActivity.class) ;
            myIntent.putExtra("email",email);
            myIntent.putExtra("password",passswordField.getText().toString());
            startActivity(myIntent);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Password should be greater than 6 characters",Toast.LENGTH_SHORT).show();
        }

    }
}
