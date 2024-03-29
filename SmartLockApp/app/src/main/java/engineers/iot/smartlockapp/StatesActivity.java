package engineers.iot.smartlockapp;

import android.os.Bundle;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

public class StatesActivity extends AppCompatActivity {

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        initViews();
        handleActions();
    }

    private void initViews(){
        back = findViewById(R.id.back);
    }

    private void handleActions() {
        back.setOnClickListener(e-> onBackPressed());
    }

}