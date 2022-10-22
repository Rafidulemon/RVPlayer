package com.example.rvplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMe extends AppCompatActivity {
    Animation fade,bottom_animation,blink;
    ImageView image,gm,li,gh,fb;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        getSupportActionBar().hide();

        fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        blink = AnimationUtils.loadAnimation(this,R.anim.blink);
        bottom_animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.imageView2);
        image.setAnimation(fade);

        tv2 = findViewById(R.id.textView2);
        gm = findViewById(R.id.gm);
        gh = findViewById(R.id.gh);
        li = findViewById(R.id.li);
        fb = findViewById(R.id.fb);

        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/Rafidulemon"));
                startActivity(intent);
            }
        });
        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/md-rafidul-8b013118b/"));
                startActivity(intent);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/rafidul.emon.50"));
                startActivity(intent);
            }
        });
        tv2.setAnimation(bottom_animation);
    }
}