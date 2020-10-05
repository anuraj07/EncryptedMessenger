package com.example.encryptedmessenger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class MD5Activity extends AppCompatActivity {

    String password = "";
    Button hashButton;
    EditText textInput;
    TextView outputText;
    ImageView voiceInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_d5);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MD5 Hashing");
        initViews();

        hashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = textInput.getText().toString();
                if (password.length()>0)
                    outputText.setText(hashPasswd(password));
                else
                    Toast.makeText(MD5Activity.this, "Empty Input", Toast.LENGTH_SHORT).show();

            }
        });

        voiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                if(intent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(intent, 1001);
                } else {
                    Toast.makeText(MD5Activity.this, "your device does not support this feature", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String hashPasswd(String password){

        String passwordToHash = password;
        String generatedPassword;
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            //https://stackoverflow.com/questions/4846484/md5-hashing-in-android
            //link to take help for md5 hashing
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 32).substring(1));
            }
            generatedPassword = sb.toString();
            Log.d("check", generatedPassword);
            return generatedPassword;

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }

    }
    public void initViews() {
        hashButton = findViewById(R.id.hash_button);
        textInput = findViewById(R.id.text_input);
        outputText = findViewById(R.id.output_text);
        voiceInput = findViewById(R.id.voice_input);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                if (resultCode==RESULT_OK && data!=null){
                    ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textInput.setText(res.get(0));
                }
                break;
        }
    }
}