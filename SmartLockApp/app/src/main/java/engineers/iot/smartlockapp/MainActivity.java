package engineers.iot.smartlockapp;

import static engineers.iot.smartlockapp.CreatePinActivity.nameUser;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.PopupMenu;


import com.google.firebase.database.DataSnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Objects;

import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.PreferencesShared;


public class MainActivity extends AppCompatActivity {

    private ImageView lockImage,more;
    private AppCompatRadioButton lock,unlock;
    private TextView live;

    private Dialog passDialog;

    private Button cancel, change;

    private EditText oldPass, newPass, confPass;
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

    private void showChangePasswordDialogBox() {
     passDialog.show();
    }

    private boolean onItemMenuListener(MenuItem item) {

        if(item.getItemId() == R.id.addMobileNumber) {
            showDialogPlus();
        } else if(item.getItemId() == R.id.changePassword) {
           showChangePasswordDialogBox();
        }

        return true;
    }

    private void showDialogPlus() {
        final DialogPlus addNumberPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.add_number_dialog))
                .setExpanded(true,220)
                .create();

        View view = addNumberPlus.getHolderView();

        EditText phoneNumber = view.findViewById(R.id.phoneNumber);
        Button add = view.findViewById(R.id.addNumber);

        add.setOnClickListener(e-> {

            if(phoneNumber.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please add you phone number!!", Toast.LENGTH_SHORT).show();
            } else {
                String phone = phoneNumber.getText().toString();

                PreferencesShared preferencesShared = new PreferencesShared(this);
                String username = preferencesShared.getPreferences().getString(nameUser, null);

                ConnectDB database = new ConnectDB("HOMEOWNER");
                assert username != null;
                database.getDatabaseReference().child(username).child("phoneNumber").setValue(phone).addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Successfully added phone number", Toast.LENGTH_SHORT).show();
                        addNumberPlus.dismiss();
                    }

                });
            }
        });

        addNumberPlus.show();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
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

        passDialog = new Dialog(this);
        passDialog.setContentView(R.layout.change_password_dialog_box);
        Objects.requireNonNull(passDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        passDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.white_card_view_style));
        passDialog.setCancelable(false);

        cancel = passDialog.findViewById(R.id.cancel);
        change = passDialog.findViewById(R.id.change);
        oldPass = passDialog.findViewById(R.id.oldCode);
        newPass = passDialog.findViewById(R.id.newCode);
        confPass = passDialog.findViewById(R.id.confirmCode);



    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void handleActions(){

        history.setOnClickListener(e-> goToHistoryActivity());

        authList.setOnClickListener(e-> goToAuntListActivity());

        authentication.setOnClickListener(e-> goToStatesActivity());

        more.setOnClickListener(e-> showPopupMenu());

        live.setOnClickListener(e-> Toast.makeText(this, "LIVE Pressed", Toast.LENGTH_SHORT).show());

        cancel.setOnClickListener(e-> passDialog.dismiss());

        change.setOnClickListener(e->{

            if(oldPass.getText().toString().isEmpty() || newPass.getText().toString().isEmpty() || confPass.getText().toString().isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else if(!newPass.getText().toString().equals(confPass.getText().toString())) {
                Toast.makeText(this, "Confirm password does match new password", Toast.LENGTH_SHORT).show();
            } else {
                String currentPass = oldPass.getText().toString();
                String newCode = newPass.getText().toString();

                PreferencesShared preferencesShared = new PreferencesShared(this);
                String username = preferencesShared.getPreferences().getString(nameUser, null);

                ConnectDB database = new ConnectDB("HOMEOWNER");

                assert username != null;
                database.getDatabaseReference().child(username).get().addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        if(task.getResult().exists()) {
                            DataSnapshot data = task.getResult();
                            String passcode = String.valueOf(data.child("code").getValue());
                            if(passcode.equals(currentPass)) {
                            database.getDatabaseReference().child(username).child("code").setValue(newCode).addOnCompleteListener(task1 -> {

                                if(task1.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                    passDialog.dismiss();
                                    oldPass.setText("");
                                    newPass.setText("");
                                    confPass.setText("");
                                }
                            });
                            } else {
                                Toast.makeText(this, "Password unknown", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                });
            }

        });

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