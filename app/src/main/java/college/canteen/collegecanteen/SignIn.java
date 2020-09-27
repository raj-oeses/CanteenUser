package college.canteen.collegecanteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    EditText emailSignIn, passwordSignIn;
    TextView signUp;
    LinearLayout signInButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailSignIn = findViewById(R.id.email_signIn);
        passwordSignIn = findViewById(R.id.password_signIn);
        progressBar = findViewById(R.id.progressbar_signIn);

        signUp = findViewById(R.id.sign_Up);
        signInButton = findViewById(R.id.logIn_Button);

        signInButton.setOnClickListener(this);
        signUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }
    /*===============================signing in wala function===================================*/
    public void LoggingIn() {
        String email, password;
        email = emailSignIn.getText().toString().trim();
        password = passwordSignIn.getText().toString().trim();

        if (email.isEmpty()) {
            emailSignIn.setError("Email is Required");
            emailSignIn.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordSignIn.setError("Passowrd is required");
            passwordSignIn.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignIn.setError("Enter Valid Email Address");
            emailSignIn.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordSignIn.setError("Minumun Length is 6");
            passwordSignIn.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    finish();
                    Intent fullKhanaFeed = new Intent(getApplicationContext(), FullKhanaFeed.class);
                    fullKhanaFeed.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);              //when pressed back it will not come to the login again
                    startActivity(fullKhanaFeed);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logIn_Button:
                LoggingIn();
                break;
            case R.id.sign_Up:
                startActivity(new Intent(getApplicationContext(), SignUp.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}