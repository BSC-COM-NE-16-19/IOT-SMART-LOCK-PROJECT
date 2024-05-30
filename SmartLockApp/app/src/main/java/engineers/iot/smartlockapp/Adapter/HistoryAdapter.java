package engineers.iot.smartlockapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import engineers.iot.smartlockapp.Model.History;
import engineers.iot.smartlockapp.R;

public class HistoryAdapter extends FirebaseRecyclerAdapter<History, HistoryAdapter.ViewHolder> {

    public HistoryAdapter(@NonNull FirebaseRecyclerOptions<History> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull History history) {

        if(history.getStatus().equals("Accessed")) {
            viewHolder.status.setTextColor(Color.GREEN);
        } else if(history.getStatus().equals("Denied")) {
            viewHolder.status.setTextColor(Color.RED);
        }

        viewHolder.name.setText(history.getName());

        viewHolder.time.setText(history.getTime());

        viewHolder.status.setText(history.getStatus());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout,parent,false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, time, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
        }
    }
}
