package projects.tiji.com.famtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class joinCircleActivity extends AppCompatActivity {

    Pinview pinview;
    DatabaseReference reference,currentReference;
    FirebaseUser user;
    FirebaseAuth auth;
    String current_user_id,join_user_id,user_name,current_user_name;
    DatabaseReference circleReference,joinedReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);
        pinview=(Pinview)findViewById(R.id.pinView);
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        currentReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

    }
    public void SubmitButtonClicked (View v)
    {
        //CHECK IF THE INPUT CODE IS PRESENT IN DATABASE
        //IF CODE IS PRESENT,FIND THE USER AND CREATE A NODE(CircleMembers)
        user=auth.getCurrentUser();
       current_user_id=user.getUid();
        current_user_name=user.getDisplayName();


        Query query=reference.orderByChild("code").equalTo(pinview.getValue());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if(dataSnapshot.exists()){
                  CreateUser createUser=null;
                  for(DataSnapshot childDss:dataSnapshot.getChildren()){
                      createUser=childDss.getValue(CreateUser.class);
                      join_user_id=createUser.userid;
                      user_name=createUser.name;

                      circleReference=FirebaseDatabase.getInstance().getReference().child("Users").child(join_user_id).child("CircleMembers");
                      //joinedReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("JoinedCircle");
                      joinedReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("JoinedCircle");
                     // CircleJoin circleJoin=new CircleJoin(current_user_id);
                      CircleJoin circleJoin=new CircleJoin(current_user_id);
                       final CircleJoin circleJoin1=new CircleJoin(join_user_id);

                      circleReference.push().setValue(circleJoin).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful())
                              {
                                  joinedReference.child(join_user_id).setValue(circleJoin1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {

                                          Toast.makeText(getApplicationContext(),"User Joined Circle Successafully",Toast.LENGTH_SHORT).show();
                                          finish();
                                          Intent myIntent=new Intent(joinCircleActivity.this,NavigationDrawerActivity.class);
                                          startActivity(myIntent);
                                      }
                                  });

                              }
                          }
                      });

                  }
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
