package engineers.iot.smartlockapp;

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

    }

    private void initViews() {
        login = findViewById(R.id.authB);
        password = findViewById(R.id.editPass);
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
}