package com.example.encryptedmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MD5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_d5);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MD5 Hashing");
    }
}