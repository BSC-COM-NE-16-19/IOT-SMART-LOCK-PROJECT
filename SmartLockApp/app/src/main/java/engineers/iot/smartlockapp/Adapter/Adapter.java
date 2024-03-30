package engineers.iot.smartlockapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Objects;

import engineers.iot.smartlockapp.Model.User;
import engineers.iot.smartlockapp.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Context context;
    private Dialog dialog;

    private EditText fName, surname;

    private Button cancel, update;

    private RadioButton access, deny;
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
            initViews();

            handleActions();

            setEditViewText(userList.get(position).getFirstName(),userList.get(position).getLastName());




            dialog.show();


        });

    }

    private void updateUserDetails(String fName, String surname, boolean status) {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViews(){

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.update_user_dialog_box);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.white_card_view_style));
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
