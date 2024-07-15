package com.example.foodiecafe;

import static com.example.foodiecafe.R.layout.activity_main2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread td = new Thread(){
            public void run()
            {
                try {
                    sleep(4000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent it = new Intent(MainActivity2.this , MainActivity.class );
                    startActivity(it);
                    finish();
                }
            }
        };td.start();
    }
}