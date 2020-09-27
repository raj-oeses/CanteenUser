package college.canteen.collegecanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button profile, coursedetails, hotelProfile, khanaFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = findViewById(R.id.profile);
        coursedetails = findViewById(R.id.courseDetails);
        hotelProfile = findViewById(R.id.hotelProfile);
        khanaFeed = findViewById(R.id.khana_feed);

        profile.setOnClickListener(this);
        coursedetails.setOnClickListener(this);
        hotelProfile.setOnClickListener(this);
        khanaFeed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile:
                startActivity(new Intent(this, UserProfile.class));
                break;
            case R.id.courseDetails:
                //startActivity(new Intent(this, CanteenKhanaDetails.class));
                break;
            case R.id.hotelProfile:
                startActivity(new Intent(this, HotelProfile.class));
                break;
            case R.id.khana_feed:
                startActivity(new Intent(this, FullKhanaFeed.class));
                break;
        }
    }
}