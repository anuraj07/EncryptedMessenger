package com.example.encryptedmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DESActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_e_s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DES Encryption Decryption");
    }
}