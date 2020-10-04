package com.example.encryptedmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout MD5, AES, DES, RSA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        MD5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MD5Activity.class));
            }
        });

        AES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AESActivity.class));
            }
        });

        DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DESActivity.class));
            }
        });

        RSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RSAActivity.class));
            }
        });




    }
    public void initViews() {
        MD5 = findViewById(R.id.md5);
        AES = findViewById(R.id.aes);
        DES = findViewById(R.id.des);
        RSA = findViewById(R.id.rsa);
    }
}