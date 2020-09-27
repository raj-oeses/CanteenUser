package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class SignUpAdditional extends AppCompatActivity implements View.OnClickListener {
    private static final int FROM_STORAGE = 101;
    ImageButton ownerImage;
    EditText hotelName, hotelPhoneNumber, college;
    LinearLayout signUp;
    Uri ImageUri;
    private StorageReference mStorage;
    String hotelNameRef, profileImageUrl;
    String htlName, htlPhoneNumber, collegeAround;
    ProgressBar hotelImageProgress;
    Uri link;

    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_additional);


        ownerImage = findViewById(R.id.owner_photo);
        hotelName = findViewById(R.id.hotel_name);
        hotelPhoneNumber = findViewById(R.id.hotel_phoneNumber);
        college = findViewById(R.id.college_around);
        hotelImageProgress = findViewById(R.id.progressbar_HotelImage);

        signUp = findViewById(R.id.signUp_Button_additional);

        ownerImage.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    /*================================================OnClick===================================*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.owner_photo:
                ChoosingPhoto();
                break;
            case R.id.signUp_Button_additional:
                UploadingInformation();
                //UploadImage();
                break;
        }
    }

    /*======================================Uploadint Infromation=====================================*/
    private void UploadingInformation() {
        Toast.makeText(this, "UploadingInformation Samma pugyo", Toast.LENGTH_SHORT).show();

        htlName = hotelName.getText().toString().trim();
        htlPhoneNumber = hotelPhoneNumber.getText().toString().trim();
        collegeAround = college.getText().toString().trim();
        if ((hotelName.getText().toString().trim()).isEmpty()) {
            hotelName.setError("Hotel Name Required");
            hotelName.requestFocus();
            return;
        }
        if (htlPhoneNumber.isEmpty()) {
            hotelPhoneNumber.setError("Hotel Phone Number Required");
            hotelPhoneNumber.requestFocus();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null && ImageUri != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(htlName)
                    .setPhotoUri(ImageUri)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    UploadImage();
                }
            });
        } else {
            Toast.makeText(this, "Image Is Mandatory", Toast.LENGTH_SHORT).show();
        }
    }

    /*=========================================uploading Image===================================*/
    private void UploadImage() {
        mStorage = FirebaseStorage.getInstance()
                .getReference("HotelProfilePics/" + hotelNameRef + "/" + System.currentTimeMillis() + ".jpg");
        Toast.makeText(this, "UplaodingImage samma Pugyo", Toast.LENGTH_SHORT).show();

        /*==================================ProgressBar Seen=========================================*/


        hotelImageProgress.setVisibility(View.VISIBLE);

        if (ImageUri != null) {
            mStorage.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot snapshot) {
                    hotelImageProgress.setVisibility(View.GONE);
                    Toast.makeText(SignUpAdditional.this, snapshot.getStorage().getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                    /* =====================getting downloadable Url================================*/
                    Task<Uri> uri = snapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete()) ;
                    link = uri.getResult();

                    profileImageUrl = link.toString();
                    DataBaseStroing(profileImageUrl);
                    Toast.makeText(SignUpAdditional.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpAdditional.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*=======================================================String Details InDataBase==========================================*/
    public void DataBaseStroing(String ImageUrl) {
        mRef = FirebaseDatabase.getInstance().getReference().child("HotelInfo").child(htlName + System.currentTimeMillis());
        HashMap<String, Object> HotelInfo = new HashMap<>();
        HotelInfo.put("HotelName", htlName);
        HotelInfo.put("PhoneNumber", htlPhoneNumber);
        HotelInfo.put("CollegeAround", collegeAround);
        HotelInfo.put("ImageUrl", ImageUrl);
        mRef.setValue(HotelInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                hotelName.getText().clear();
                hotelPhoneNumber.getText().clear();
                college.getText().clear();
                Toast.makeText(SignUpAdditional.this, "Uploaded EveryThing ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpAdditional.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*=====================================OnActivity restult is performed=========================================*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FROM_STORAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                ownerImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*=========================================Choosing the photo=================================*/
    public void ChoosingPhoto() {
        Intent choosePhoto = new Intent();
        choosePhoto.setType("image/*");
        choosePhoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(choosePhoto, "Select Profile Image"), FROM_STORAGE);
    }
}