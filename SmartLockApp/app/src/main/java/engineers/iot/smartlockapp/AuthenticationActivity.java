package engineers.iot.smartlockapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


public class AuthenticationActivity extends AppCompatActivity {

    private ImageButton login;
    private EditText password;

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
    }
    private void addCharacterToEditView(Button button) {
        String pass = password.getText().toString();
        pass += button.getText().toString();
        password.setText(pass);
        password.setSelection(pass.length());
    }
}