package info.androidhive.navigationdrawer.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by Admin on 29-12-2016.
 */

public class Gallery_Activity extends Gallery {

    private boolean stuck = false;

    public Gallery_Activity(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Gallery_Activity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Gallery_Activity(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return stuck || super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return stuck || super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setScrollingEnabled(boolean enabled) {
        stuck = !enabled;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        return super.onFling(e1, e2, 0, velocityY);
    }
}