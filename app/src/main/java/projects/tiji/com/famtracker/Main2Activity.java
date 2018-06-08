package projects.tiji.com.famtracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends Activity {
    DatabaseReference  reference;
    FirebaseUser user;
    FirebaseAuth auth;
    String uid;
    TextView text1,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_navigation_drawer);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        text1=(TextView)findViewById(R.id.textView) ;
        text2 =(TextView) findViewById(R.id.name1);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(uid).child("name").getValue(String.class);
                text2.setText(name);
                String mail = dataSnapshot.child(uid).child("email").getValue(String.class);
                text1.setText(mail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }
}


