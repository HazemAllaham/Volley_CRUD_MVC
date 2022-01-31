package com.honor.volleyfullproject.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.honor.volleyfullproject.Model.DataMain;
import com.honor.volleyfullproject.Model.UserListData;
import com.honor.volleyfullproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolderClass> {


    private final List<UserListData> list ;
    private final Context context ;

    public ListUserAdapter(List<UserListData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ListUserAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(context).inflate(R.layout.item_list_user , parent , false);
        return new ViewHolderClass(root);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

       final UserListData dataMain = list.get(position);


        try {

            holder.tv_first_name.setText(dataMain.getFirst_name());
            holder.tv_last_name.setText(dataMain.getLast_name());
            holder.tv_email_user.setText(dataMain.getEmail());
            Picasso.with(context).load(dataMain.getAvatar()).into(holder.profile_image);

        }catch (Exception e){

            Toast.makeText(context, "the erroe photo : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            holder.profile_image.setImageResource(R.drawable.edit_img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder {

        private final TextView tv_first_name , tv_last_name , tv_email_user;
        private final ImageView profile_image;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tv_first_name = itemView.findViewById(R.id.tv_first_name);
            tv_last_name = itemView.findViewById(R.id.tv_last_name);
            tv_email_user = itemView.findViewById(R.id.tv_email_user);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }
}
