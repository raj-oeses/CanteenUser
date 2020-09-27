package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

import okhttp3.internal.cache.DiskLruCache;

public class UserProfile extends AppCompatActivity {
    RecyclerView lastfoodOrder, availableNow;
    RecyclerView.LayoutManager showing, showingavailabenow;
    AdapterFoodOrder adapter;

    TextView userName, userPhoneNumber;
    ImageView displayPic;
    ProgressBar profilePicProgressBar;

    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference mDataRef;
    DocumentReference mDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_profile);

        lastfoodOrder = findViewById(R.id.profile_last_food_order);
        availableNow = findViewById(R.id.profile_available_now);
        userName = findViewById(R.id.userDisplayName_UserProfile);
        userPhoneNumber = findViewById(R.id.userPhoneNumber_UserProfile);
        displayPic = findViewById(R.id.displayPhoto_UserProfile);
        profilePicProgressBar = findViewById(R.id.profilePic_UserProfile);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        LoadUserInformation();

        showing = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        showingavailabenow = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, true);
        lastfoodOrder.setLayoutManager(showing);
        availableNow.setLayoutManager(showingavailabenow);

        int[] arr = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f};
        adapter = new AdapterFoodOrder(arr);

        lastfoodOrder.setAdapter(adapter);
        availableNow.setAdapter(adapter);
        lastfoodOrder.setHasFixedSize(true);
        availableNow.setHasFixedSize(true);
    }

    public void LoadUserInformation() {


        /*if (user.getEmail() != null) {
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();

            mDataRef = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(user.getUid());
            mDataRef.keepSynced(true);
            mDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userName.setText(snapshot.child("UserName").getValue().toString());
                    userPhoneNumber.setText(snapshot.child("PhoneNumber").getValue().toString());
                    Picasso.get().load(snapshot.child("ImageUrl").getValue().toString()).into(displayPic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }*/
        if (user != null) {

            profilePicProgressBar.setVisibility(View.VISIBLE);
            mDoc = FirebaseFirestore.getInstance().collection("UserDetails").document(user.getUid());
            mDoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                    userName.setText(snapshot.getString("UserName"));
                    userPhoneNumber.setText(snapshot.getString("PhoneNumber"));
                    Picasso.get().load(snapshot.getString("ImageUrl")).into(displayPic);
                    profilePicProgressBar.setVisibility(View.GONE);
                }
            });

        } else {
            profilePicProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
        }


    }
}