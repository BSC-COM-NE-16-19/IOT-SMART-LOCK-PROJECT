package engineers.iot.smartlockapp;

import android.os.Bundle;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import engineers.iot.smartlockapp.Adapter.Adapter;
import engineers.iot.smartlockapp.Model.User;


public class AuthListActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView recyclerView;
    private Adapter recycleViewAdapter;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_list);
    }


}