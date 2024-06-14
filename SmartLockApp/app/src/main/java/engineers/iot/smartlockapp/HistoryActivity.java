package engineers.iot.smartlockapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import engineers.iot.smartlockapp.Adapter.HistoryAdapter;
import engineers.iot.smartlockapp.Database.ConnectDB;
import engineers.iot.smartlockapp.Model.History;

public class HistoryActivity extends AppCompatActivity {

    private ImageView back;

    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initViews();
        handleActions();

    }

    private void initViews(){

        back = findViewById(R.id.back);
        RecyclerView historyRecycleView = findViewById(R.id.historyRecycleView);

        ConnectDB database = new ConnectDB("HISTORY");

        historyRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<History> options =
                new FirebaseRecyclerOptions.Builder<History>()
                        .setQuery(database.getDatabaseReference(), History.class)
                        .build();


        historyAdapter = new HistoryAdapter(options);
        historyRecycleView.setAdapter(historyAdapter);
    }

    private void handleActions() {
        back.setOnClickListener(e-> onBackPressed());
    }

    @Override
    protected void onStop() {
        super.onStop();
        historyAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        historyAdapter.startListening();
    }
}