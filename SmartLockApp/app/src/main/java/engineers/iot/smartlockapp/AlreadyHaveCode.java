package engineers.iot.smartlockapp;

import static engineers.iot.smartlockapp.CreatePinActivity.SECURITYCODE;
import static engineers.iot.smartlockapp.CreatePinActivity.nameUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;

import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.PreferencesShared;

public class AlreadyHaveCode extends AppCompatActivity {

    private Button continues;

    private EditText code, username;

    private ConnectDB db;

    private PreferencesShared preferencesShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_have_code);

        initViews();

        handleActions();

    }

    private void initViews() {
        db = new ConnectDB("HOMEOWNER");
        continues = findViewById(R.id.continues);
        code = findViewById(R.id.code);
        username = findViewById(R.id.username);
        preferencesShared = new PreferencesShared(this);
    }

    private void handleActions() {
        continues.setOnClickListener(e-> {
            String code1 = code.getText().toString();
            String username1 = username.getText().toString();


            if(code1.isEmpty() || username1.isEmpty()) {
                Toast.makeText(this, "Enter your details?", Toast.LENGTH_SHORT).show();
            } else {
                db.getDatabaseReference().child(username1.toLowerCase()).get().addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        if(task.getResult().exists()) {
                            DataSnapshot data = task.getResult();
                            String passcode = String.valueOf(data.child("code").getValue());
                            String name = String.valueOf(data.child("username").getValue());
                            if(passcode.equals(code1) && name.equals(username1)){

                                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferencesShared.getPreferences().edit();
                                editor.putString(SECURITYCODE, "set");
                                editor.putString(nameUser,username1.toLowerCase());
                                editor.apply();

                                Intent intent = new Intent(AlreadyHaveCode.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AlreadyHaveCode.this, "Incorrect username or security code", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }

        });
    }
}