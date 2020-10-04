package com.example.encryptedmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AESActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_e_s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("AES Encryption Decryption");
    }
}