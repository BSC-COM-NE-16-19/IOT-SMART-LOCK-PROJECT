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
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
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
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i, @NonNull User user) {
        viewHolder.name.setText(user.getfName());
        viewHolder.id.setText(user.getSurname());

        viewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            final int position = i;
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.name.getContext());
                builder.setTitle("Are you sure you want to delete the user " + user.getfName() +"?");
                builder.setMessage("Deleted data can't be Undo.");
                builder.setCancelable(false);

                builder.setPositiveButton("Delete", (dialogInterface, i1) -> FirebaseDatabase.getInstance().getReference().child("USER")
                        .child(Objects.requireNonNull(getRef(position).getKey())).removeValue());

                builder.setNegativeButton("Cancel", (dialogInterface, i12) -> {

                });
            builder.show();
                return true;
            }
        });

        viewHolder.layout.setOnClickListener(e-> {
                initViews(viewHolder);

                 handleActions();

                 update.setOnClickListener(event-> {
                     Map<String, Object> map = new HashMap<>();
                     map.put("fName", fName.getText().toString());
                     map.put("surname",surname.getText().toString());

                     if(deny.isChecked()) {
                         map.put("permission","deny");
                     } else if (access.isChecked()){
                         map.put("permission","access");
                     }
                     FirebaseDatabase.getInstance().getReference().child("USER")
                             .child(Objects.requireNonNull(getRef(i).getKey())).updateChildren(map)
                             .addOnSuccessListener(unused -> {
                                 Toast.makeText(viewHolder.name.getContext(), "User updated successfully", Toast.LENGTH_SHORT).show();
                                 dialog.dismiss();
                             })
                             .addOnFailureListener(e1 -> Toast.makeText(viewHolder.name.getContext(), "Error on updating user", Toast.LENGTH_SHORT).show());
                 });

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
