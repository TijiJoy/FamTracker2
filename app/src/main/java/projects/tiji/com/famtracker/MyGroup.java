package projects.tiji.com.famtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyGroup extends AppCompatActivity {
    private RecyclerView mInstaList;
    private DatabaseReference uDataBase,cDatabase;

   private FirebaseAuth auth;
    private FirebaseUser user;

    private LinearLayoutManager mLayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);
        mInstaList = (RecyclerView) findViewById(R.id.recyclerview);
        mInstaList.setHasFixedSize(true);
        //To display reverse order oflist items
        mLayoutmanager=new LinearLayoutManager(MyGroup.this);
        mLayoutmanager.setReverseLayout(true);
        mLayoutmanager.setStackFromEnd(true);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        cDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("CircleMembers");


    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();

        FirebaseRecyclerAdapter<CreateUser,InstaViewHolder> FBRA=new FirebaseRecyclerAdapter<CreateUser, InstaViewHolder>(
               CreateUser.class,
                R.layout.card_layout,
                InstaViewHolder.class,cDatabase
        ) {
            @Override
            protected void populateViewHolder(InstaViewHolder viewHolder, CreateUser model, int position) {
                final String post_key=getRef(position).getKey().toString();

               //viewHolder.setCirclememberid(model.getCirclememberid());

                viewHolder.setName(model.getName());
                viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){

                        Toast.makeText(MyGroup.this, "welcome ", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };
        mInstaList.setLayoutManager(mLayoutmanager);
        mInstaList.setAdapter(FBRA); 
            }

          
    public static class InstaViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public InstaViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView t1=(TextView) mView.findViewById(R.id.item_title);
            t1.setText(name);
        }

        public void setCirclememberid(String circlememberid) {
            TextView t1=(TextView) mView.findViewById(R.id.item_title);
            t1.setText(circlememberid);
        }
    }


}

