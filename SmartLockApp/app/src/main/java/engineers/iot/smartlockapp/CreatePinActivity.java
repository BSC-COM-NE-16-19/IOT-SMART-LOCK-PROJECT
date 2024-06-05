package engineers.iot.smartlockapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.HomeOwner;


public class CreatePinActivity extends AppCompatActivity {

    private EditText username, pinCode, confirmCode;

    private ConnectDB db;

    private TextView already;
    private Button create;

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
    }

    private void handleActions() {
     create.setOnClickListener(e -> {

         String name = username.getText().toString();
         String code = pinCode.getText().toString();
         String confPin = confirmCode.getText().toString();

         if(name.isEmpty() || code.isEmpty() || confPin.isEmpty()) {
             Toast.makeText(this, "Please fill all details!!", Toast.LENGTH_SHORT).show();
         } else if(!code.equals(confPin)) {
             Toast.makeText(this, "The code do not match!!", Toast.LENGTH_SHORT).show();
         } else {
             HomeOwner owner = new HomeOwner(name,code, " ");
             db = new ConnectDB("HOMEOWNER");
             db.getDatabaseReference().child(name.toLowerCase()).setValue(owner).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     Toast.makeText(CreatePinActivity.this, "Successfully created a code", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(intent);
                     finish();
                 }
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