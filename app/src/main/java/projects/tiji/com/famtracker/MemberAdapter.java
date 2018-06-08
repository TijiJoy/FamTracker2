package projects.tiji.com.famtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Tg on 15-05-2018.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MembersViewHolder> implements OnMapReadyCallback {
    Double latitude;
    Double longitude;
    GoogleMap mMap;
    ArrayList<CreateUser> nameList;
    Context c;
    FirebaseAuth auth;
    FirebaseUser user;
    private DatabaseReference mDatabase, usersReference;

    public MemberAdapter(ArrayList<CreateUser> nameList, Context c) {
        this.nameList = nameList;
        this.c = c;
    }

    @Override
    public MembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        MembersViewHolder membersViewHolder = new MembersViewHolder(v, c, v, nameList);
        return membersViewHolder;
    }

    @Override
    public void onBindViewHolder(final MembersViewHolder holder, final int position) {

        final CreateUser currentUserObj = nameList.get(position);
        holder.t1.setText(currentUserObj.getName());
        holder.t2.setText(currentUserObj.getEmail());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent singleInstaActivity = new Intent(c, DUMMY.class);
                singleInstaActivity.putExtra("Latitiude", currentUserObj.getLat().toString());
                singleInstaActivity.putExtra("Longitude", currentUserObj.getLng().toString());
                singleInstaActivity.putExtra("Name", currentUserObj.getName().toString());
                c.startActivity(singleInstaActivity);


            }
        });
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameList.remove(position);
                auth = FirebaseAuth.getInstance();
                notifyDataSetChanged();
                user = auth.getCurrentUser();
                usersReference = FirebaseDatabase.getInstance().getReference().child("Users");
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("CircleMembers");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dssU : dataSnapshot.getChildren()) {
                                if (dssU.child("circlememberid").getValue().equals(currentUserObj.getUserid())) {
                                    dssU.getRef().setValue(null);
                                }


                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FirebaseAuth auth;
        FirebaseUser user;
        View v;
        Button b1;
        TextView t2;
        TextView t1;
        DatabaseReference reference;

        Context c;
        ArrayList<CreateUser> nameArrayList;

        public MembersViewHolder(View itemView, Context c, View v, ArrayList<CreateUser> nameArrayList) {
            super(itemView);
            this.c = c;
            this.v = v;
            this.nameArrayList = nameArrayList;
            itemView.setOnClickListener(this);


            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();

            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("CircleMembers");

            t1 = (TextView) itemView.findViewById(R.id.item_title);
            t2 = (TextView) itemView.findViewById(R.id.textMail);
            b1 = (Button) itemView.findViewById(R.id.deleteBotton);
            b1.setOnClickListener(this);


            // circleImageView=(ImageView)itemView.findViewById(R.id.circleImage);
            // imageView=(ImageView)itemView.findViewById(R.id.locenable);

        }


        @Override
        public void onClick(View view) {

        }
    }
}


