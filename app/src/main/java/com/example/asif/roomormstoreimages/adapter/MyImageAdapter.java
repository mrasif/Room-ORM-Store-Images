package com.example.asif.roomormstoreimages.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asif.roomormstoreimages.R;
import com.example.asif.roomormstoreimages.config.AppConfig;
import com.example.asif.roomormstoreimages.database.AppDatabase;
import com.example.asif.roomormstoreimages.libs.BitmapManager;
import com.example.asif.roomormstoreimages.models.MyImage;

import java.util.List;

/**
 * Created by asif on 12/24/17.
 */

public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.ViewHolder> {
    AppDatabase db;
    List<MyImage> images;
    Context ctx;
    LayoutInflater inflater;

    public MyImageAdapter(List<MyImage> images, Context ctx) {
        this.images = images;
        this.ctx = ctx;
        this.inflater=LayoutInflater.from(ctx);
    }

    @Override
    public MyImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.item_image,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyImageAdapter.ViewHolder holder, int position) {
        MyImage current=images.get(position);
        holder.tvTitle.setText(current.getTitle());

        String base64Image = current.getPhoto();
        holder.ivPhoto.setImageBitmap(BitmapManager.base64ToBitmap(base64Image));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivPhoto;
        TextView tvTitle;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto=itemView.findViewById(R.id.ivPhoto);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            db= Room.databaseBuilder(ctx, AppDatabase.class, AppConfig.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
            MyImage image=images.get(getPosition());
            db.myImageDao().delete(image);
            images.remove(getPosition());
            notifyItemRemoved(getPosition());
        }
    }
}
