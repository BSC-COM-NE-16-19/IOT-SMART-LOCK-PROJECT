package engineers.iot.smartlockapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AlreadyHaveCode extends AppCompatActivity {

    private Button continues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_have_code);

        initViews();

        handleActions();

    }

    private void initViews() {
        continues = findViewById(R.id.continues);
    }

    private void handleActions() {
        continues.setOnClickListener(e-> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}