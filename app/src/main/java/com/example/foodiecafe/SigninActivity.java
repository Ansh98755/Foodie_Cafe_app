package com.example.foodiecafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SigninActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView google;
    TextView dont;

    // Define ActivityResultLauncher
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        navigateToSecondActivity();
                    } catch (ApiException e) {
                        Toast.makeText(SigninActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        dont = findViewById(R.id.dont);
        dont.setOnClickListener(v -> {
            Toast.makeText(SigninActivity.this, "Register", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(SigninActivity.this, SignupActivity.class);
            startActivity(it);
        });

        google = findViewById(R.id.google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(SigninActivity.this, gso);

        google.setOnClickListener(v -> signIn());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Signin");
    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    void navigateToSecondActivity() {
        finish();
        Intent it = new Intent(SigninActivity.this, SigninActivitySecond.class);
        startActivity(it);
    }
}


