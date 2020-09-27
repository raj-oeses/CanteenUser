package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FullKhanaFeed extends AppCompatActivity {
    RecyclerView khanaharu;
    AdapterFullKhanaFeed adapterr;
    ViewFlipper flipper;

    ImageView contextMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_khana_feed);

        flipper = findViewById(R.id.flipper);
        //contextMenu = findViewById(R.id.context_Menu);

        //registerForContextMenu(contextMenu);

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
    /*==========================================          3 dot menu ======================================*/
    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_layout,menu);

    }
    *//*==========================================when item is clicked in menu======================================*//*
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.userProfile_Menu:
                Toast.makeText(this, "Profile Is Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "SomeThing went wrong", Toast.LENGTH_SHORT).show();
                return super.onContextItemSelected(item);
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        adapterr.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterr.stopListening();
    }
}