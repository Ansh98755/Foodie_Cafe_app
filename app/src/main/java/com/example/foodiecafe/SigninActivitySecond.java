package com.example.foodiecafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SigninActivitySecond extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView Name, Email;
    Button sign_out, proceed_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_second);

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        sign_out = findViewById(R.id.sign_out);
        proceed_button = findViewById(R.id.proceed);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(SigninActivitySecond.this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String person_name = acct.getDisplayName();
            String person_email = acct.getEmail();
            Name.setText(person_name);
            Email.setText(person_email);
        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        proceed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display toast message
                Toast.makeText(SigninActivitySecond.this, "Give your order", Toast.LENGTH_SHORT).show();

                // Start MenuActivity
                Intent menuIntent = new Intent(SigninActivitySecond.this, MenuActivity.class);
                startActivity(menuIntent);
            }
        });
    }

    void signout() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                Intent it = new Intent(SigninActivitySecond.this, SigninActivity.class);
                startActivity(it);
            }
        });
    }
}