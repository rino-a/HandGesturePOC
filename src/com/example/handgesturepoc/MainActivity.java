package com.example.handgesturepoc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
int windowwidth;
int screenCenter;
int x_cord, y_cord;
int Likes = 0;
RelativeLayout parentView;
float alphaValue = 0;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    parentView = (RelativeLayout) findViewById(R.id.layoutview);
    windowwidth = getWindowManager().getDefaultDisplay().getWidth();
    screenCenter = windowwidth / 2;
    int[] myImageList = new int[] { R.drawable.stencil1, R.drawable.stencil2,
            R.drawable.stencil3, R.drawable.stencil4, R.drawable.stencil5,
            R.drawable.stencil6 };
    for (int i = 0; i < 6; i++) {
        final RelativeLayout myRelView = new RelativeLayout(this);
        myRelView
                .setLayoutParams(new LayoutParams((windowwidth - 80), 300));
        myRelView.setX(40);
        myRelView.setY(40);
        myRelView.setTag(i);
        myRelView.setBackgroundResource(myImageList[i]);

        if (i == 0) {
            myRelView.setRotation(-1);
        } else if (i == 1) {
            myRelView.setRotation(-5);

        } else if (i == 2) {
            myRelView.setRotation(3);

        } else if (i == 3) {
            myRelView.setRotation(7);

        } else if (i == 4) {
            myRelView.setRotation(-2);

        } else if (i == 5) {
            myRelView.setRotation(5);

        }

        final Button imageLike = new Button(this);
        imageLike.setLayoutParams(new LayoutParams(100, 50));
        imageLike.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.stencil7));
        imageLike.setX(20);
        imageLike.setY(80);
        imageLike.setAlpha(alphaValue);
        myRelView.addView(imageLike);

        final Button imagePass = new Button(this);
        imagePass.setLayoutParams(new LayoutParams(100, 50));
        imagePass.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.ic_launcher));

        imagePass.setX((windowwidth - 200));
        imagePass.setY(100);
        imagePass.setRotation(45);
        imagePass.setAlpha(alphaValue);
        myRelView.addView(imagePass);

        myRelView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x_cord = (int) event.getRawX();
                y_cord = /*(int) event.getRawY()*/screenCenter;

                myRelView.setX(x_cord - screenCenter + 40);
                myRelView.setY(y_cord/* - 150*/);
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                	Log.d("Action: ", "MotionEvent.ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                	Log.d("Action: ", "MotionEvent.ACTION_MOVE"+"--x_cord = "+x_cord+"--y_cord = "+y_cord);
                    x_cord = (int) event.getRawX();
                    y_cord = screenCenter/*(int) event.getRawY()*/;
                    
                    myRelView.setX(x_cord - screenCenter + 40);
                    myRelView.setY(y_cord/* - 150*/);
                    if (x_cord >= screenCenter) {
                        myRelView
                                .setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                        if (x_cord > (screenCenter + (screenCenter / 2))) {
                            imageLike.setAlpha(1);
                            if (x_cord > (windowwidth - (screenCenter / 4))) {
                                Likes = 2;
                            } else {
                                Likes = 0;
                            }
                        } else {
                            Likes = 0;
                            imageLike.setAlpha(0);
                        }
                        imagePass.setAlpha(0);
                    } else {
                        // rotate
                        myRelView
                                .setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                        if (x_cord < (screenCenter / 2)) {
                            imagePass.setAlpha(1);
                            if (x_cord < screenCenter / 4) {
                                Likes = 1;
                            } else {
                                Likes = 0;
                            }
                        } else {
                            Likes = 0;
                            imagePass.setAlpha(0);
                        }

                        imageLike.setAlpha(0);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                	Log.d("Action: ", "MotionEvent.ACTION_UP");
                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                    imagePass.setAlpha(0);
                    imageLike.setAlpha(0);

                    if (Likes == 0) {
                        Log.e("Event Status", "Nothing");
                        myRelView.setX(40);
                        myRelView.setY(40);
                        myRelView.setRotation(0);
                    } else if (Likes == 1) {
                        Log.e("Event Status", "Passed");
                        parentView.removeView(myRelView);
                    } else if (Likes == 2) {

                        Log.e("Event Status", "Liked");
                        parentView.removeView(myRelView);
                    }
                    break;
                default:
                    break;
                }
                return true;
            }
        });

        parentView.addView(myRelView);

    }

}
}
