package engineers.iot.smartlockapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.PopupMenu;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.Client;


public class MainActivity extends AppCompatActivity {

    private ImageView lockImage,more;
    private AppCompatRadioButton lock,unlock;
    private TextView live;
    private LinearLayout battery, history, authList, authentication;

    private ConnectDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        handleActions();
    }


    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this,more);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(this::onItemMenuListener);
        popupMenu.show();

    }

    private boolean onItemMenuListener(MenuItem item) {

        if(item.getItemId() == R.id.addMobileNumber) {
            Toast.makeText(this, "Clicked add mobile number", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.changePassword) {
            Toast.makeText(this, "Clicked change password", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private void initViews(){
        live = findViewById(R.id.live);
        lockImage = findViewById(R.id.smallLock);
        lock = findViewById(R.id.locked);
        unlock = findViewById(R.id.unlocked);
        history = findViewById(R.id.history);
        authentication = findViewById(R.id.authentication);
        authList = findViewById(R.id.list);
        more = findViewById(R.id.more);
        database = new ConnectDB("STATUS");
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void handleActions(){

        history.setOnClickListener(e-> goToHistoryActivity());

        authList.setOnClickListener(e-> goToAuntListActivity());

        authentication.setOnClickListener(e-> goToStatesActivity());

        more.setOnClickListener(e-> showPopupMenu());

        live.setOnClickListener(e-> Toast.makeText(this, "LIVE Pressed", Toast.LENGTH_SHORT).show());

        lock.setOnClickListener(e->{

                     if(lock.isChecked()){
                         database.getDatabaseReference().child("lockStatus").setValue("Locked");
                         lock.setText("Locked");
                         unlock.setText("Unlock");
                         lockImage.setImageResource(R.drawable.laucher);
                     }


        });

        unlock.setOnClickListener(e->{

                    if(unlock.isChecked()){
                        database.getDatabaseReference().child("lockStatus").setValue("Unlocked");
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