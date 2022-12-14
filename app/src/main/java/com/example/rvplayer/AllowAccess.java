package com.example.rvplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AllowAccess extends AppCompatActivity {
    public static final int STORAGE_PERMISSION = 1;
    public static final int REQUEST_PERMISSION_SETTING = 12;
    Button allow_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_allow_access);
        allow_btn = findViewById(R.id.allow_access);

        SharedPreferences preferences = getSharedPreferences("AllowAccess",MODE_PRIVATE);
        String value = preferences.getString("Allow","");
        if (value.equals("Ok")){
            startActivity(new Intent(AllowAccess.this,Home.class));
            finish();
        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Allow","Ok");
            editor.apply();

        }
        allow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(AllowAccess.this,Home.class));
                    finish();
                }else{
                    ActivityCompat.requestPermissions(AllowAccess.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==STORAGE_PERMISSION){
            for(int i=0;i<permissions.length;i++){
                String per= permissions[i];
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){

                    boolean showRationale = shouldShowRequestPermissionRationale(per);
                    if (!showRationale){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("App Permission")
                        .setMessage("For playing videos in this app, you have to allow for accessing all videos of your device."+"\n\n"+
                                "Now follow the below steps:"+"\n\n"+"-Open setting from below button"+"\n"+"-Click on permissions"+"\n"+
                                "-Allow Access for storage")
                                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                                        intent.setData(uri);
                                        startActivityForResult(intent,REQUEST_PERMISSION_SETTING);
                                    }
                                }).create().show();
                    }else{
                        ActivityCompat.requestPermissions(AllowAccess.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION);
                    }
                }else{
                    startActivity(new Intent(AllowAccess.this,Home.class));
                    finish();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(AllowAccess.this,Home.class));
            finish();
        }
    }
}