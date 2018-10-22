package com.example.dream.mtracker;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context ctx) {
        gestureDetector=new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private  final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static  final int SWIPE_THRESHHOLD=100;
        private static  final int SWIPE_VELOCITY_THRESHHOLD=100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result=false;
            try {
                float diffY=e2.getY()-e1.getY();
                float diffX=e2.getX()-e1.getX();
                if(Math.abs(diffY)>SWIPE_THRESHHOLD&&Math.abs(velocityX)>SWIPE_VELOCITY_THRESHHOLD){
                    if(diffX>0){
                        onSwipeRight();
                    }
                    else{
                        onSwipeLeft();
                    }
                    result=true;
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
    public void onSwipeRight(){

    }
    public void onSwipeLeft(){

    }
}