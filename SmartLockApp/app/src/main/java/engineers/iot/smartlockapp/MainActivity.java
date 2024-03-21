package engineers.iot.smartlockapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;


public class MainActivity extends AppCompatActivity {

    private ImageView lockImage;
    private AppCompatRadioButton lock,unlock;
    private LinearLayout battery, history, authList, authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        handleActions();
    }

    private void initViews(){
        lockImage = findViewById(R.id.smallLock);
        lock = findViewById(R.id.locked);
        unlock = findViewById(R.id.unlocked);
        history = findViewById(R.id.history);
        authentication = findViewById(R.id.authentication);
        authList = findViewById(R.id.list);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void handleActions(){

        history.setOnClickListener(e-> goToHistoryActivity());

        authList.setOnClickListener(e-> goToAuntListActivity());

        authentication.setOnClickListener(e-> goToStatesActivity());

        lock.setOnClickListener(e->{

            if(lock.isChecked()){
                lock.setText("Locked");
                unlock.setText("Unlock");
                lockImage.setImageResource(R.drawable.laucher);
            }
        });

        unlock.setOnClickListener(e->{

            if(unlock.isChecked()){
                unlock.setText("Unlocked");
                lock.setText("Lock");
                lockImage.setImageResource(R.drawable.unlock);
            }
        });
    }
    private void goToHistoryActivity() {
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(intent);
    }

    private void goToAuntListActivity() {
        Intent intent = new Intent(getApplicationContext(), AuthListActivity.class);
        startActivity(intent);
    }

    private void goToStatesActivity() {
        Intent intent = new Intent(getApplicationContext(), StatesActivity.class);
        startActivity(intent);
    }
}