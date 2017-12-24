package com.example.asif.roomormstoreimages;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asif.roomormstoreimages.adapter.MyImageAdapter;
import com.example.asif.roomormstoreimages.config.AppConfig;
import com.example.asif.roomormstoreimages.database.AppDatabase;
import com.example.asif.roomormstoreimages.libs.BitmapManager;
import com.example.asif.roomormstoreimages.models.MyImage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CAMERA_REQUEST_CODE=101;

    Button btnCapture, btnAddPhoto;
    EditText etTitle;
    ImageView ivPhoto;
    RecyclerView rvItems;
    AppDatabase db;
    Bitmap bitmap;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCapture=findViewById(R.id.btnCapture);
        btnAddPhoto=findViewById(R.id.btnAddPhoto);
        etTitle=findViewById(R.id.etTitle);
        ivPhoto=findViewById(R.id.ivPhoto);
        rvItems=findViewById(R.id.rvItems);
        db= Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppConfig.DB_NAME)
                .allowMainThreadQueries()
                .build();
        ivPhoto.setOnClickListener(this);
        btnCapture.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        btnAddPhoto.setEnabled(false);
        loadList();
    }

    private void loadList(){
        adapter=new MyImageAdapter(db.myImageDao().getAll(),this);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivPhoto:
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQUEST_CODE);
                break;

            case R.id.btnCapture:
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQUEST_CODE);
                break;

            case R.id.btnAddPhoto:
                insertImages();
                break;
        }
    }

    private void insertImages(){
        String title=etTitle.getText().toString();
        String photo= BitmapManager.bitmapToBase64(bitmap);
        byte[] image=BitmapManager.bitmapToByte(bitmap);
        db.myImageDao().insertAll(new MyImage(title,photo,image));
        Toast.makeText(getApplicationContext(),"Image Saved",Toast.LENGTH_SHORT).show();
        etTitle.setText("");
        ivPhoto.setImageResource(R.drawable.ic_camera_alt_black_24dp);
        btnAddPhoto.setEnabled(false);
        loadList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap=(Bitmap)data.getExtras().get("data");
//        bitmap=BitmapManager.drawString(bitmap,"Hi,\nI am Asif Mohammad Mollah,\nHow are you ?",0,0,10);
        ivPhoto.setImageBitmap(bitmap);
        int lastUID=0;
        try{
            lastUID=db.myImageDao().last().getUid();
        }catch (Exception e){}
        String title="Image "+String.valueOf(lastUID+1);
        etTitle.setText(title);
        btnAddPhoto.setEnabled(true);
    }
}
