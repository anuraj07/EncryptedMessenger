package com.example.encryptedmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AESPasswordResetActivity extends AppCompatActivity {

    EditText oldPaswd, newPaswd, cnfNewPaswd;
    Button save;
    String oldPaswdString, newPaswdString, cnfPaswdString;
    String keyPwd = AESActivity.pwdtext.toString();
    TextView resetToDefaultPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_e_s_password_reset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPaswdString = oldPaswd.getText().toString();
                newPaswdString = newPaswd.getText().toString();
                cnfPaswdString = cnfNewPaswd.getText().toString();
                if (keyPwd.equals(oldPaswdString)){
                    if (newPaswdString.equals(cnfPaswdString)){
                        AESActivity.pwdtext=newPaswdString;
                        startActivity(new Intent(AESPasswordResetActivity.this, AESActivity.class));
                        finish();
                        Toast.makeText(AESPasswordResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AESPasswordResetActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AESPasswordResetActivity.this, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetToDefaultPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AESActivity.pwdtext = "asdfgh";
                startActivity(new Intent(AESPasswordResetActivity.this, AESActivity.class));
                finish();
                Toast.makeText(AESPasswordResetActivity.this, "Password Reset", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void initViews() {
        save = findViewById(R.id.save);
        oldPaswd = findViewById(R.id.old_paswd);
        newPaswd = findViewById(R.id.new_paswd);
        cnfNewPaswd = findViewById(R.id.cnf_new_paswd);
        resetToDefaultPassword = findViewById(R.id.set_default_pswd);
    }
}