package college.canteen.collegecanteen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    RecyclerView lastfoodOrder, availableNow;
    RecyclerView.LayoutManager showing, showingavailabenow;
    AdapterFoodOrder adapter;

    TextView userName, userPhoneNumber;
    ImageView displayPic;

    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_profile);

        lastfoodOrder = findViewById(R.id.profile_last_food_order);
        availableNow = findViewById(R.id.profile_available_now);
        userName = findViewById(R.id.userDisplayName_UserProfile);
        userPhoneNumber = findViewById(R.id.userPhoneNumber_UserProfile);
        displayPic = findViewById(R.id.displayPhoto_UserProfile);

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

    private void LoadUserInformation() {

        try {
            user = mAuth.getCurrentUser();
            if (user != null) {
                if (user.getPhotoUrl() != null) {
                    Picasso.get().load(user.getPhotoUrl().toString()).into(displayPic);
                }
                if (user.getDisplayName() != null) {
                    userName.setText(user.getDisplayName());
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Error", e.getMessage());
        }

    }
}