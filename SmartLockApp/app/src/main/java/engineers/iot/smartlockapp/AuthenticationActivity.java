package engineers.iot.smartlockapp;

import static engineers.iot.smartlockapp.CreatePinActivity.nameUser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;

import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.PreferencesShared;


public class AuthenticationActivity extends AppCompatActivity {

    private ImageButton login;
    private EditText password;

    private ConnectDB db;

    private Button one, two, three, four, five, six, seven, eight, nine, zero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        initViews();

        handleActions();

    }

    private void initViews() {
        login = findViewById(R.id.authB);
        password = findViewById(R.id.editPass);
        password.setFocusable(true);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three= findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        db = new ConnectDB("HOMEOWNER");
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void handleActions() {
        login.setOnClickListener(e-> goToMainActivity());

        one.setOnClickListener(e->addCharacterToEditView(one));

        two.setOnClickListener(e-> addCharacterToEditView(two));

        three.setOnClickListener(e-> addCharacterToEditView(three));

        four.setOnClickListener(e-> addCharacterToEditView(four));

        five.setOnClickListener(e-> addCharacterToEditView(five));

        six.setOnClickListener(e-> addCharacterToEditView(six));

        seven.setOnClickListener(e-> addCharacterToEditView(seven));

        eight.setOnClickListener(e-> addCharacterToEditView(eight));

        nine.setOnClickListener(e-> addCharacterToEditView(nine));

        zero.setOnClickListener(e-> addCharacterToEditView(zero));

        login.setOnClickListener(e-> {
            PreferencesShared preferencesShared = new PreferencesShared(this);
            String username = preferencesShared.getPreferences().getString(nameUser, null);
            String code = password.getText().toString();
            if(code.isEmpty()) {
                Toast.makeText(this, "Enter security code", Toast.LENGTH_SHORT).show();
            } else {
                assert username != null;
                db.getDatabaseReference().child(username).get().addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        if(task.getResult().exists()) {
                            DataSnapshot data = task.getResult();
                            String passcode = String.valueOf(data.child("code").getValue());
                            if(passcode.equals(code)){
                                Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AuthenticationActivity.this, "Incorrect security code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
            }

        });
    }
    private void addCharacterToEditView(Button button) {
        String pass = password.getText().toString();
        pass += button.getText().toString();
        password.setText(pass);
        password.setSelection(pass.length());
    }
}