package com.example.encryptedmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RSAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_s_a);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RSA Encryption Decryption");
    }
}