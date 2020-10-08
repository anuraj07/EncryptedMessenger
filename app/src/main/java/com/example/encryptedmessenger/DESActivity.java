package com.example.encryptedmessenger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESActivity extends AppCompatActivity {


    EditText textInput;
    ImageView voiceInput;
    TextView outputText;
    Button enc, dec, clear, send;
    String ans="";
    //for key generation
    private Cipher cipher;
    SecretKey key;
    private static final String UNICODE_FORMAT="UTF8";
    public static final String DES_ENCRYPTION_SCHEME="DES";
    private KeySpec myKeySpec;
    private SecretKeyFactory mySecretKeyFactory;
    byte[] KeyAsBytes;
    private String myEncryptionKey;
    private String getMyEncryptionScheme;
    String myEncKey="This is my Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_e_s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DES Encryption Decryption");
        initViews();

        myEncryptionKey=myEncKey;
        getMyEncryptionScheme=DES_ENCRYPTION_SCHEME;
        try {
            KeyAsBytes=myEncryptionKey.getBytes(UNICODE_FORMAT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            myKeySpec=new DESKeySpec(KeyAsBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            mySecretKeyFactory=SecretKeyFactory.getInstance(getMyEncryptionScheme);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            cipher=Cipher.getInstance(getMyEncryptionScheme);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            key=mySecretKeyFactory.generateSecret(myKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp=textInput.getText().toString();
                ans=encrypt(temp);
                outputText.setText(ans);
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp=textInput.getText().toString();
                ans=decrypt(temp);
                outputText.setText(ans);
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    textInput.setText("");
                    outputText.setText("");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ans.length()>0) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, ans);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(DESActivity.this, "Empty Output", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(DESActivity.this, "your device does not support this feature", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initViews() {
        textInput = findViewById(R.id.text_input);
        outputText = findViewById(R.id.output_text);
        voiceInput = findViewById(R.id.voice_input);
        enc = findViewById(R.id.enc);
        dec = findViewById(R.id.dec);
        clear = findViewById(R.id.clear_button);
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

    public String encrypt(String unencryptedString) {
        String encryptedString =null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);

            encryptedString= Base64.encodeToString(encryptedText, Base64.DEFAULT);
        } catch (InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return encryptedString;
    }

    public String decrypt(String encrytedString) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decode(encrytedString, Base64.DEFAULT);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(plainText);
        } catch(InvalidKeyException|IllegalBlockSizeException|BadPaddingException e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    private static String bytes2String(byte[] bytes) {
        StringBuilder stringBuffer=new StringBuilder();
        for(int i=0;i<bytes.length;i++)
        {
            stringBuffer.append((char) bytes[i]);

        }
        return stringBuffer.toString();
    }

}