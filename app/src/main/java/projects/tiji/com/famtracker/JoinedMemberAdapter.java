package projects.tiji.com.famtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Tg on 06-06-2018.
 */
public class JoinedMemberAdapter extends RecyclerView.Adapter <JoinedMemberAdapter.JoinedMemberViewHolder> implements OnMapReadyCallback {
    GoogleMap mMap;
    ArrayList<CreateUser> nameList1;
    Context c;

    public JoinedMemberAdapter(ArrayList<CreateUser> nameList1, Context c) {
        this.nameList1 = nameList1;
        this.c = c;
    }

    @Override
    public JoinedMemberAdapter.JoinedMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout2, parent, false);
        JoinedMemberAdapter.JoinedMemberViewHolder membersViewHolder = new JoinedMemberAdapter.JoinedMemberViewHolder(v, c, v, nameList1);
        return membersViewHolder;

    }

    @Override
    public void onBindViewHolder(JoinedMemberAdapter.JoinedMemberViewHolder holder, int position) {
        final CreateUser currentUserObj = nameList1.get(position);

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


    }

    @Override
    public int getItemCount() {
        return nameList1.size();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public static class JoinedMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FirebaseAuth auth;
        FirebaseUser user;
        View v;
        TextView t2;
        TextView t1;
        ImageView circleImageView;
        ImageView imageView;
        Context c;
        ArrayList<CreateUser> nameList1;

        public JoinedMemberViewHolder(View v1, Context c, View v, ArrayList<CreateUser> nameList1) {
            super(v1);
            this.c = c;
            this.v = v;
            this.nameList1 = nameList1;
            v.setOnClickListener(this);
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            t1 = (TextView) itemView.findViewById(R.id.item_title1);
            t2 = (TextView) itemView.findViewById(R.id.textMail1);

        }

        @Override
        public void onClick(View view) {

        }
    }
}

