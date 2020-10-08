package com.example.encryptedmessenger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESActivity extends AppCompatActivity {


    EditText textInput;
    ImageView voiceInput;
    TextView outputText;
    Button enc, dec, clear, resetPswd, send;
    public static String pwdtext = "asdfgh";
    String inputString, outputString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_e_s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("AES Encryption Decryption");
        initViews();

        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    inputString = textInput.getText().toString();
                    outputString = encrypt(inputString, pwdtext);
                    outputText.setText(outputString);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    inputString = textInput.getText().toString();
                    outputString = decrypt(inputString, pwdtext);
                    outputText.setText(outputString);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        resetPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AESActivity.this, AESPasswordResetActivity.class));
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    textInput.setText("");
                    outputText.setText("");
                    textInput.setHint("Enter message");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outputString.length()>0){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, outputString);
                    intent.setType("text/plain");
                    startActivity(intent);
                } else {
                    Toast.makeText(AESActivity.this, "Empty Output", Toast.LENGTH_SHORT).show();
                }
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
                    Toast.makeText(AESActivity.this, "your device does not support this feature", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private String encrypt(String data, String passwordKey) throws Exception{
        SecretKeySpec keySpec = generateKey(passwordKey);
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encVal = c.doFinal(data.getBytes("UTF-8"));
        //Encrypts or decrypts data in a single-part operation, or finishes a multiple-part operation
        String encryptedvalue = Base64.encodeToString(encVal, Base64.DEFAULT);
        //Base64-encode the given data and return a newly allocated String with the result.
        //It's basically a way of encoding arbitrary binary data in ASCII text. It takes 4 characters per 3 bytes of data,
        // plus potentially a bit of padding at the end.
        //Essentially each 6 bits of the input is encoded in a 64-character alphabet.
        //The "standard" alphabet uses A-Z, a-z, 0-9 and + and /, with = as a padding character. There are URL-safe variants.
        return encryptedvalue;

    }

    private String decrypt(String data, String password_text) throws Exception {
        SecretKeySpec keySpec = generateKey(password_text);
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedValue = Base64.decode(data, Base64.DEFAULT);//pehle vo base64 me encoded tha, to decode to karna padega na
        byte[] decvalue = c.doFinal(decodedValue);//final decoding operation
        String decryptedValue = new String(decvalue, "UTF-8");
        return decryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return  secretKeySpec;
    }



    public void initViews() {
        textInput = findViewById(R.id.text_input);
        outputText = findViewById(R.id.output_text);
        voiceInput = findViewById(R.id.voice_input);
        enc = findViewById(R.id.enc);
        dec = findViewById(R.id.dec);
        clear = findViewById(R.id.clear_button);
        resetPswd = findViewById(R.id.reset);
        send = findViewById(R.id.send);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AESActivity.this, MainActivity.class));
        finish();
    }
}