package engineers.iot.smartlockapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class CreatePinActivity extends AppCompatActivity {

    private EditText username, pinCode, confirmCode;
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
    }

    private void handleActions() {
     create.setOnClickListener(e -> {
         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
         startActivity(intent);
         finish();
     });
    }
}