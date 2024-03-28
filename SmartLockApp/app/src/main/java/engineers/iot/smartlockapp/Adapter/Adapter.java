package engineers.iot.smartlockapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import engineers.iot.smartlockapp.Model.User;
import engineers.iot.smartlockapp.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Context context;
    private Dialog dialog;
    private final ArrayList<User> userList;
    public Adapter(Context context,ArrayList<User> userList){
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.auth_list_items,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(userList.get(position).getFirstName());
        holder.id.setText(String.valueOf(userList.get(position).getId()));

        holder.layout.setOnClickListener(e-> {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.update_user_dialog_box);
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.white_card_view_style));
            dialog.show();


        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, id;
        private final RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            layout = itemView.findViewById(R.id.layout);


        }
    }
}
