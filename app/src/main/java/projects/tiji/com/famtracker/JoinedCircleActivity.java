package projects.tiji.com.famtracker;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinedCircleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    FirebaseAuth auth;
    FirebaseUser user;
    CreateUser createUser=null;
    ArrayList<CreateUser> nameList1;
    DatabaseReference reference,usersReference;
    String  circlememeberid;
    String current_user_id,join_user_id;
    DatabaseReference circleReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_circle);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview2);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        nameList1=new ArrayList<>();

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        usersReference= FirebaseDatabase.getInstance().getReference().child("Users");
        reference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("JoinedCircle");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading your joined user circle. please wait...");
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList1.clear();
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dssU:dataSnapshot.getChildren())

                    {

                        circlememeberid=dssU.child("circlememberid").getValue(String.class);

                        usersReference.child(circlememeberid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                createUser=dataSnapshot.getValue(CreateUser.class);
                                progressDialog.dismiss();
                                nameList1.add(createUser);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }






                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "No Users has joined your Circle.Please invite users to join your circle", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter=new JoinedMemberAdapter(nameList1,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
