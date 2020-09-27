package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {
    EditText ownerNamSignUp, emailSignUp, passwordSignUp, rePasswordSignUp;
    LinearLayout signUpButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ownerNamSignUp = findViewById(R.id.ownerName_SignUp);
        emailSignUp = findViewById(R.id.email_signUp);
        passwordSignUp = findViewById(R.id.password_signUp);
        rePasswordSignUp = findViewById(R.id.rePassword_signUp);
        signUpButton = findViewById(R.id.signUp_Button);
        progressBar = findViewById(R.id.progressbar_signUp);


        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

    }

    public void RegisterUser() {
        String email = emailSignUp.getText().toString().trim();
        String password = passwordSignUp.getText().toString().trim();
        String rePassword = rePasswordSignUp.getText().toString().trim();

        if (email.isEmpty()) {
            emailSignUp.setError("Email is Required");
            emailSignUp.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordSignUp.setError("Passowrd is required");
            passwordSignUp.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignUp.setError("Enter Valid Email Address");
            emailSignUp.requestFocus();
            return;
        }
       /* if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailSignUp.setError("Email Already Exist");
            emailSignUp.requestFocus();
            return;
        }*/
        if (password.length() < 6) {
            passwordSignUp.setError("Minumun Length is 6");
            passwordSignUp.requestFocus();
            return;
        }
        if (!password.equals(rePassword)) {
            rePasswordSignUp.setError("Password Doesn't Match");
            rePasswordSignUp.requestFocus();
            return;
        }
        /*final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Registering...");*/
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, "User Register SuccessFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SignUpAdditional.class));
                } else {
                    progressBar.setVisibility(View.GONE);
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        emailSignUp.setError("User Already Register");
                        emailSignUp.requestFocus();
                        return;
                    } else {
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
}