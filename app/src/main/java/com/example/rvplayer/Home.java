package com.example.rvplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ArrayList<MediaFiles> mediaFiles = new ArrayList<>();
    private ArrayList<String> allFolderList = new ArrayList<>();
    private ImageView about_me;
    RecyclerView recyclerView;
    VideoFolderAdapter adapter;
    private static final int REQUEST_PERMISSION_SETTING = 10;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"Click on permissions and allow access",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",getPackageName(),null);
            intent.setData(uri);
            startActivityForResult(intent,REQUEST_PERMISSION_SETTING);
        }
        about_me = findViewById(R.id.about_me);
        about_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutMe();
            }
        });
        recyclerView = findViewById(R.id.folders_rv);
        showFolders();
    }

    private void showFolders() {
        mediaFiles = fetchMedia();
        adapter = new VideoFolderAdapter(mediaFiles, allFolderList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter.notifyDataSetChanged();
    }

    public ArrayList<MediaFiles> fetchMedia() {

        ArrayList<MediaFiles> mediaFilesArrayList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null&&cursor.moveToNext()){
            do {
                String id= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                String title= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String displayName= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                String size= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                String duration= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String path= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                String dateAdded= cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));

                MediaFiles mediaFiles = new MediaFiles(id,title,displayName,size,duration,path,dateAdded);

                int index = path.lastIndexOf("/");
                String subString = path.substring(0,index);
                if(!allFolderList.contains(subString)){
                    allFolderList.add(subString);
                }
                mediaFilesArrayList.add(mediaFiles);
            }while (cursor.moveToNext());
        }
        return mediaFilesArrayList;
    }
    public void openAboutMe(){
        Intent i= new Intent(this, AboutMe.class);
        startActivity(i);
    }
}