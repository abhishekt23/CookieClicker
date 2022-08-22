package com.example.cookieclickerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    ImageView monster;
    ImageView imagex2;
    TextView plusone;
    TextView score;
    Button timestwo;
    int counter = 0;
    AtomicInteger total;
    float randomV;
    float randomH;
    Boolean b = false;
    Boolean w = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.id_layout);
        monster = findViewById(R.id.imageView1);
        score = findViewById(R.id.id_text_score);
        timestwo = findViewById(R.id.id_button_upgrade_);
        imagex2 = findViewById(R.id.imageViewx2);
        timestwo.setEnabled(b);




        monster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
                animationDrawable.setEnterFadeDuration(2000);
                animationDrawable.setExitFadeDuration(4000);
                animationDrawable.start();

                ScaleAnimation animation1 = new ScaleAnimation(1.0f, 1.25f, 1.0f, 1.25f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                animation1.setDuration(400);
                v.startAnimation(animation1);

                plusone = new TextView(MainActivity.this);
                plusone.setId(View.generateViewId());
                plusone.setTextColor(Color.BLUE);
                plusone.setTextSize(50f);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                plusone.setLayoutParams(params);
                constraintLayout.addView(plusone);

                final ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(plusone.getId(), ConstraintSet.TOP, constraintLayout.getId(), constraintSet.TOP);
                constraintSet.connect(plusone.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), constraintSet.BOTTOM);
                constraintSet.connect(plusone.getId(), ConstraintSet.LEFT, constraintLayout.getId(), constraintSet.LEFT);
                constraintSet.connect(plusone.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), constraintSet.RIGHT);

                randomH = (float)(Math.random()*1.0f) + 0.00f;
                randomV = (float)(Math.random()*1.0f) + 0.00f;
                constraintSet.setHorizontalBias(plusone.getId(), randomH);
                constraintSet.setVerticalBias(plusone.getId(), randomV);
                constraintSet.applyTo(constraintLayout);

                Animation floating = new TranslateAnimation(0,0,0,-200);
                floating.setDuration(1000);
                floating.setFillAfter(false);

                Animation fadeOut = new AlphaAnimation(1,0);
                fadeOut.setDuration(1000);

                AnimationSet set = new AnimationSet(false);
                set.addAnimation(floating);
                set.addAnimation(fadeOut);

                plusone.startAnimation(set);
                plusone.setVisibility(View.INVISIBLE);


                if(b.equals(false)) {
                    plusone.setText("+1");
                    timestwo.setText("TimesTwo-Insufficient Funds");
                    counter++;
                    score.setText("Cookie Monster Ate " + counter + " cookies!");
                }
                else if (b.equals(true)){
                    plusone.setText("+2");
                    counter+=2;
                    score.setText("Cookie Monster Ate " + counter + " cookies!");
                        if(counter < 50){
                            timestwo.setEnabled(false);
                            timestwo.setBackgroundColor(Color.GRAY);

                        }
                }

                if(counter >= 50){
                    timestwo.setEnabled(true);
                    timestwo.setBackgroundColor(Color.BLUE);
                    timestwo.setText("TimesTwo-Sufficient Funds");
                }
                timestwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b = true;
                        w = false;
                        counter-=50;
                        timestwo.setEnabled(false);
                        timestwo.setBackgroundColor(Color.GRAY);
                        timestwo.setText("TimesTwo-USED!");
                        score.setText("Cookie Monster Ate " + counter + " cookies!");
                        imagex2.setImageResource(R.drawable.timestwopic);
                        //imagex2 = new ImageView(MainActivity.this);
                        //imagex2.setId(View.generateViewId());
                        TranslateAnimation translateAnimation = new TranslateAnimation(500.0f, 7.0f, 900.0f, -4.0f);
                        translateAnimation.setDuration(5000);
                        imagex2.startAnimation(translateAnimation);
                        imagex2.setImageResource(R.drawable.timestwopic);

                    }
                });

                if(w.equals(false)){
                    timestwo.setBackgroundColor(Color.GRAY);
                    timestwo.setText("USED!");
                    timestwo.setEnabled(false);

                }

                score.setText("Cookie Monster Ate " + counter + " cookies!");

            }
        });
    }
    public class PassiveIncome extends Thread{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                total.getAndAdd(counter);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        score.setText("Cookie Monster Ate " + total + " cookies!");
                    }
                });
            }

        }
    }
}
