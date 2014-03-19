package com.pasanlive.corosel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity {
    private ImageSwitcher imageSwitcher;

    private int[] imageList = new int[3];
    private int currentPos = 0;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);

        imageList[0] = R.drawable.one;
        imageList[1] = R.drawable.two;
        imageList[2] = R.drawable.three;

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_XY);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.
                        MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

                return myView;
            }

        });

        // Gesture detection
        gestureDetector = new GestureDetector(getApplicationContext(), new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };


        imageSwitcher.setImageResource(imageList[currentPos]);
        imageSwitcher.setOnTouchListener(gestureListener);


    }


    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    currentPos--;
                    if (currentPos < 0) {
                        currentPos = 2;
                    }
                    Toast.makeText(getApplicationContext(), "previous Image",
                            Toast.LENGTH_SHORT).show();
                    Animation in = AnimationUtils.loadAnimation(MainActivity.this,
                            R.anim.slide_out_left);
                    Animation out = AnimationUtils.loadAnimation(MainActivity.this,
                            R.anim.slide_in_right);
                    imageSwitcher.setInAnimation(out);
                    imageSwitcher.setOutAnimation(in);
                    imageSwitcher.setImageResource(imageList[currentPos]);

                }
                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    currentPos++;
                    if (currentPos > 2) {
                        currentPos = 0;
                    }
                    Toast.makeText(getApplicationContext(), "Next Image",
                            Toast.LENGTH_SHORT).show();
                    Animation in = AnimationUtils.loadAnimation(MainActivity.this,
                            android.R.anim.slide_in_left);
                    Animation out = AnimationUtils.loadAnimation(MainActivity.this,
                            android.R.anim.slide_out_right);
                    imageSwitcher.setInAnimation(in);
                    imageSwitcher.setOutAnimation(out);
                    imageSwitcher.setImageResource(imageList[currentPos]);

                }

            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    }

}
