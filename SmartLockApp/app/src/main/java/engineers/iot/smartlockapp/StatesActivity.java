package engineers.iot.smartlockapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import engineers.iot.smartlockapp.Database.ConnectDB;

public class StatesActivity extends AppCompatActivity {

    private ImageView back;

    private ConnectDB database;

    private AppCompatRadioButton auth, enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        initViews();
        handleActions();
    }

    private void initViews(){

        back = findViewById(R.id.back);
        database = new ConnectDB("STATUS");
        auth = findViewById(R.id.authentication);
        enroll = findViewById(R.id.enrollment);

        database.getDatabaseReference().child("sensorStatus").get().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
               String sensorState = String.valueOf(task.getResult().getValue());
               if(sensorState.equals("Enrollment")) {
                   enroll.setChecked(true);
               } else if(sensorState.equals("Authentication")) {
                   auth.setChecked(true);
               }
            }
        });

    }

    private void handleActions() {
        auth.setOnClickListener(e-> {
               if(auth.isChecked()) {
                   database.getDatabaseReference().child("sensorStatus").setValue("Authentication");
               }
                });

        enroll.setOnClickListener(e-> {
            if(enroll.isChecked()) {
                database.getDatabaseReference().child("sensorStatus").setValue("Enrollment");
            }
        });

        back.setOnClickListener(e-> onBackPressed());
    }

}