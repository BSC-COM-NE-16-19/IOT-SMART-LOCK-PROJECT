package engineers.iot.smartlockapp;

import static engineers.iot.smartlockapp.CreatePinActivity.SECURITYCODE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import engineers.iot.smartlockapp.Model.PreferencesShared;


@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        PreferencesShared preferencesShared = new PreferencesShared(this);

        String code = preferencesShared.getPreferences().getString(SECURITYCODE,null);

        new Handler().postDelayed(() -> {

            Intent intent;
            if(code == null) {
                intent = new Intent(getApplicationContext(), CreatePinActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
            }
            startActivity(intent);
            finish();

        }, 1500);

    }
}