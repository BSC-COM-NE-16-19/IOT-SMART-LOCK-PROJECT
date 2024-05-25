package engineers.iot.smartlockapp;

import android.os.Bundle;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;


import engineers.iot.smartlockapp.Adapter.UsersAdapter;
import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.User;


public class AuthListActivity extends AppCompatActivity {

    private ImageView back;
    private UsersAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_list);

        initViews();

        handEvents();
    }

    private void initViews() {
        back = findViewById(R.id.back);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        ConnectDB database = new ConnectDB("USER");


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(database.getDatabaseReference(), User.class)
                        .build();


        recycleViewAdapter = new UsersAdapter(options);
        recyclerView.setAdapter(recycleViewAdapter);
    }


    private  void handEvents(){
        back.setOnClickListener(e-> onBackPressed());
    }

    @Override
    protected void onStart() {
        super.onStart();
        recycleViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recycleViewAdapter.stopListening();
    }
}