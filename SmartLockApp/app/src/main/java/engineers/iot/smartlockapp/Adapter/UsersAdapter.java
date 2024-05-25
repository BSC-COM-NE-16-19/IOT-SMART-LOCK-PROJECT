package engineers.iot.smartlockapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

import engineers.iot.smartlockapp.Model.User;
import engineers.iot.smartlockapp.R;

public class UsersAdapter extends FirebaseRecyclerAdapter<User,UsersAdapter.ViewHolder> {

    private Dialog dialog;

    private EditText fName, surname;

    private Button cancel, update;

    private RadioButton access, deny;

    public UsersAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auth_list_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull User user) {
        viewHolder.name.setText(user.getfName());
        viewHolder.id.setText(user.getSurname());

        viewHolder.layout.setOnClickListener(e-> {
                initViews(viewHolder);

                 handleActions();

                 setEditViewText(user.getfName(),user.getSurname());

                 if(user.getPermission().equals("access")) {
                     access.setChecked(true);
                     deny.setChecked(false);
                 } else if(user.getPermission().equals("deny")) {
                     deny.setChecked(true);
                     access.setChecked(false);
                 }

        dialog.show();
        });
    }

    private void updateUserDetails(String fName, String surname, String permission) {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViews(ViewHolder view){

        dialog = new Dialog(view.itemView.getContext());
        dialog.setContentView(R.layout.update_user_dialog_box);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(view.itemView.getContext().getDrawable(R.drawable.white_card_view_style));
        dialog.setCancelable(false);

        fName = dialog.findViewById(R.id.fName);
        surname = dialog.findViewById(R.id.surname);
        access = dialog.findViewById(R.id.accessR);
        deny = dialog.findViewById(R.id.denyR);
        cancel = dialog.findViewById(R.id.cancel);
        update = dialog.findViewById(R.id.update);

    }

    private void handleActions(){
        cancel.setOnClickListener(e-> dialog.dismiss());
    }

    private void setEditViewText(String fName, String surname) {
        this.fName.setText(fName);
        this.surname.setText(surname);
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
