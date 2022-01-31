package com.honor.volleyfullproject.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.honor.volleyfullproject.Model.DataMain;
import com.honor.volleyfullproject.R;
import com.honor.volleyfullproject.UI.EditActivity;
import java.util.List;

public  class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolderClass> {

    private final Context context ;
    private final List<DataMain> list ;

    public MainActivityAdapter(Context context, List<DataMain> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MainActivityAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.recycler_item,parent,false);
        return new ViewHolderClass(itemView);
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapter.ViewHolderClass holder, int position) {

        final  DataMain dataMain = list.get(position);

        try {

            holder.tv_name.setText(dataMain.getName());
            holder.tv_color.setText(dataMain.getColor());
            holder.tv_year.setText(String.valueOf(dataMain.getYear()));
            holder.tv_pantone_value.setText(dataMain.getPantone_value());
            holder.mycardview.setCardBackgroundColor(Color.parseColor(dataMain.getColor()));

        }catch (Exception e){

            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteData(position,dataMain.getId());
            }
        });

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("name",dataMain.getName());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderClass extends RecyclerView.ViewHolder {

        private final TextView tv_name , tv_color , tv_year ,tv_pantone_value ;
        private final ImageView img_delete , img_edit;
        private final CardView mycardview ;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_color = itemView.findViewById(R.id.tv_color);
            tv_year = itemView.findViewById(R.id.tv_year);
            tv_pantone_value = itemView.findViewById(R.id.tv_pantone_value);
            img_delete = itemView.findViewById(R.id.img_delete);
            img_edit = itemView.findViewById(R.id.img_edit);
            mycardview = itemView.findViewById(R.id.mycardview);

        }
    }

//    private String dormatDates(String dataStr){
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//            Date date = dateFormat.parse(dataStr);
//            SimpleDateFormat fmtOut = new SimpleDateFormat("MM dd");
//            return fmtOut.format(date);
//        }catch (ParseException e){
//            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        return "";
//
//    }

//    private void deleteData(int position , int id){
//
//    }


}
