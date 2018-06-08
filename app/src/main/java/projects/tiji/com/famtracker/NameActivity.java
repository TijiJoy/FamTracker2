package projects.tiji.com.famtracker;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class NameActivity extends AppCompatActivity {
String email,password;
    EditText nameField;

    double latitude ;
    double longitude ;
    Uri resultUri;
    private Uri uri = null;
    private LocationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        nameField=(EditText)findViewById(R.id.nameFIELD);

        Intent myIntent=getIntent();

        if(myIntent!=null)
        {
            email=myIntent.getStringExtra("email");
            password=myIntent.getStringExtra("password");
        }
    }
    public void generateCode(View v) {
        Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();
        int n = 100000 + r.nextInt(900000);
        String code = String.valueOf(n);
        if (uri == null) {
            Intent myIntent = new Intent(NameActivity.this, InviteCodeActivity.class);
            myIntent.putExtra("email", email);
            myIntent.putExtra("name", nameField.getText().toString());
            myIntent.putExtra("password", password);
            myIntent.putExtra("date", date);
            myIntent.putExtra("isSharing", "false");
            myIntent.putExtra("imageUri", uri);
            myIntent.putExtra("code", code);
            startActivity(myIntent);


            // else
            // {
            // Toast.makeText(getApplicationContext(),"Please choose an image",Toast.LENGTH_SHORT).show();
            // }

        }
    }
    public void selectImage (View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 12) && (resultCode == RESULT_OK)) {
            uri = data.getData();
            if (null != uri) {

              //  circleImageView.setImageURI(uri);
               // circleImageView.setAdjustViewBounds(true);
               // circleImageView.setScaleType(ImageView.ScaleType.FIT_XY);

            }
        }
    }
}
