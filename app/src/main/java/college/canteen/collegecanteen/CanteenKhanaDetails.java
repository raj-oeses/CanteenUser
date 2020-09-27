package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.Key;
import java.util.HashMap;

public class CanteenKhanaDetails extends AppCompatActivity {
    LinearLayout hotelname;
    Button order;
    DatabaseReference myRef;
    String key,category;
    TextView CategroyToDisplay,course,tarkari,price,achar,masu,masuPrice,orderNumber;
    ImageView courseImage,papadYes,papadNo, saladYes,saladNo;
    int count=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_khana_details);

        hotelname=findViewById(R.id.khanadetail_hotel_name);
        order=findViewById(R.id.order);

        courseImage=findViewById(R.id.course_photo);
        CategroyToDisplay=findViewById(R.id.category_courseSingleUnit);
        course=findViewById(R.id.course_singleUnit);
        tarkari=findViewById(R.id.tarkari_singleUnit);
        price=findViewById(R.id.price_singleUnit);
        achar=findViewById(R.id.achar_singleUnit);
        masu=findViewById(R.id.masu_singleUnit);
        masuPrice=findViewById(R.id.masuPrice_singleUnit);
        orderNumber=findViewById(R.id.orderNumber_singleUnit);

        papadYes=findViewById(R.id.papad_yes_singleUnit);
        papadNo=findViewById(R.id.papad_no_singleUnit);
        saladYes=findViewById(R.id.salad_yes_singleUnit);
        saladNo=findViewById(R.id.salad_no_singleUnit);

        hotelname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HotelProfile.class));
            }
        });

        ShowingInCanvas();

       /* myRef=FirebaseDatabase.getInstance().getReference().child("Details");
        myRef.keepSynced(true);                                                  //For offline Usage of data
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CategroyToDisplay.setText(snapshot.child("Category").getValue().toString());
                course.setText(snapshot.child("Course").getValue().toString());
                price.setText(snapshot.child("CoursePrice").getValue().toString());
                achar.setText(snapshot.child("Achar").getValue().toString());
                masu.setText(snapshot.child("Masu").getValue().toString());
                masuPrice.setText(snapshot.child("MasuPrice").getValue().toString());
                if(snapshot.child("Papad").getValue().toString().equals("Yes")){
                    papadYes.setVisibility(View.VISIBLE);
                    papadNo.setVisibility(View.GONE);
                }
                if(snapshot.child("Salad").getValue().toString().equals("Yes")){
                    saladYes.setVisibility(View.VISIBLE);
                    saladNo.setVisibility(View.GONE);
                }
                Picasso.get().load(snapshot.child("ImageUri").getValue().toString()).into(courseImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }


    /*====================================displaying in the single unit section====================================*/
    public void ShowingInCanvas(){
        key=getIntent().getStringExtra("Key");
        myRef=FirebaseDatabase.getInstance().getReference().child("Details");
        myRef.keepSynced(true);                                                  //For offline Usage of data
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CategroyToDisplay.setText(snapshot.child("Category").getValue().toString());
                course.setText(snapshot.child("Course").getValue().toString());
                tarkari.setText(snapshot.child("Tarkari").getValue().toString());
                price.setText(snapshot.child("CoursePrice").getValue().toString());
                achar.setText(snapshot.child("Achar").getValue().toString());
                masu.setText(snapshot.child("Masu").getValue().toString());
                masuPrice.setText(snapshot.child("MasuPrice").getValue().toString());
                if(snapshot.child("Papad").getValue().toString().equals("Yes")){
                    papadYes.setVisibility(View.VISIBLE);
                    papadNo.setVisibility(View.GONE);
                }
                if(snapshot.child("Salad").getValue().toString().equals("Yes")){
                    saladYes.setVisibility(View.VISIBLE);
                    saladNo.setVisibility(View.GONE);
                }
                Picasso.get().load(snapshot.child("ImageUri").getValue().toString()).into(courseImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}