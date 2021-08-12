package com.example.nishnushimadmin.adapters.users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.activities.UserProfileActivity;
import com.example.nishnushimadmin.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    Context context;
    List<User> users;


    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.userNameTextView.setText(users.get(holder.getAdapterPosition()).getId());
        holder.userNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("user", users.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });

    }





    @Override
    public int getItemCount() {
        return users.size();
    }





    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView userNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.user_name_item);
        }
    }


}
