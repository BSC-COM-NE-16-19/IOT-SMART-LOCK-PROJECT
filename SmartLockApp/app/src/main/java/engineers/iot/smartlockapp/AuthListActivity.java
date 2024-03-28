package engineers.iot.smartlockapp;

import android.os.Bundle;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

        initViews();

        handEvents();
    }

    private void initViews() {
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycleView);
        userList = new ArrayList<>();

        getUserData();

        recycleViewAdapter = new Adapter(this,userList);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    private void getUserData() {
        userList.add(new User("Godbless"," ", 49869));
        userList.add(new User("Blessing"," ", 59598));
        userList.add(new User("Moddy"," ", 48585));
        userList.add(new User("Dr. Kim"," ", 57876));
    }

    private  void handEvents(){
        back.setOnClickListener(e-> onBackPressed());
    }

}