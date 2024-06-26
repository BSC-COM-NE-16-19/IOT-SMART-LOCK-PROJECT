package engineers.iot.smartlockapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.HomeOwner;
import engineers.iot.smartlockapp.Model.PreferencesShared;


public class CreatePinActivity extends AppCompatActivity {

    private EditText username, pinCode, confirmCode;

    private ConnectDB db;

    private TextView already;
    private Button create;

    public static String SECURITYCODE = "code";

    public static String nameUser = "username";

    private PreferencesShared sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);

        initViews();

        handleActions();

    }

    private void initViews() {
        username = findViewById(R.id.username);
        pinCode = findViewById(R.id.code);
        confirmCode = findViewById(R.id.confirmCode);
        create = findViewById(R.id.create);
        already = findViewById(R.id.already);
        sharedPreferences = new PreferencesShared(this);
    }

    private void handleActions() {
     create.setOnClickListener(e -> {

         String name = username.getText().toString();
         String code = pinCode.getText().toString();
         String confPin = confirmCode.getText().toString();

         if(name.isEmpty() || code.isEmpty() || confPin.isEmpty()) {
             Toast.makeText(this, "Please fill all details!!", Toast.LENGTH_SHORT).show();
         } else if(!code.equals(confPin)) {
             Toast.makeText(this, "The security code do not match!!", Toast.LENGTH_SHORT).show();
         } else {
             HomeOwner owner = new HomeOwner(name,code, " ");
             db = new ConnectDB("HOMEOWNER");
             db.getDatabaseReference().child(name.toLowerCase()).setValue(owner).addOnCompleteListener(task -> {
                 Toast.makeText(CreatePinActivity.this, "Successfully created a code", Toast.LENGTH_SHORT).show();

                 @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.getPreferences().edit();
                 editor.putString(SECURITYCODE, "set");
                 editor.putString(nameUser,name.toLowerCase());
                 editor.apply();

                 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(intent);
                 finish();
             });
         }

     });

     already.setOnClickListener(e-> {
         Intent intent = new Intent(getApplicationContext(), AlreadyHaveCode.class);
         startActivity(intent);
         finish();
     });
    }
}