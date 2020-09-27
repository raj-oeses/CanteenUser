package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FullKhanaFeed extends AppCompatActivity {
    RecyclerView khanaharu;
    AdapterFullKhanaFeed adapterr;
    ViewFlipper flipper;

    FirebaseAuth mAuth;

    //ImageView contextMenu;
    FloatingActionButton userProfile, aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_khana_feed);

        flipper = findViewById(R.id.flipper);
        userProfile = findViewById(R.id.userProfile_FullKhanaFeed);
        aboutUs = findViewById(R.id.aboutUs_FullKhanaFeed);

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutUs.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();

        int image[] = {R.drawable.a, R.drawable.food_art};

        for (int Image : image) {
            flipperimage(Image);
        }

        khanaharu = findViewById(R.id.khanafeed_khanas);
        khanaharu.setLayoutManager(new LinearLayoutManager(this));
        khanaharu.setHasFixedSize(true);


        FirebaseRecyclerOptions<ModleFullKhanaFeed> options =
                new FirebaseRecyclerOptions.Builder<ModleFullKhanaFeed>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Details"), ModleFullKhanaFeed.class)
                        .build();

        adapterr = new AdapterFullKhanaFeed(options, this);
        khanaharu.setAdapter(adapterr);

    }

    /*==========================================for flippint the image======================================*/
    public void flipperimage(int image) {
        ImageView imageView = new ImageView(this);

        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(5000);   //10 sec ko lagi
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();


        try {
            if (mAuth.getCurrentUser() != null) {
                adapterr.startListening();
            } else {
                startActivity(new Intent(getApplicationContext(), SignIn.class));

            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterr.stopListening();
    }
}