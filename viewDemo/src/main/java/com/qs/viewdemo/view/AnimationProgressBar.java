package com.qs.viewdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by zxj on 15/3/28.
 */
public class AnimationProgressBar extends ProgressBar {
    public AnimationProgressBar(Context context) {
        super(context);
    }

    public AnimationProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public synchronized void setAnimationSetProgress(int progress) {


        if(getProgress()<=progress) {
            ObjectAnimator.ofInt(this, "secondaryProgress", getSecondaryProgress(), progress).setDuration(300).start();
            ObjectAnimator.ofInt(this, "progress", getProgress(), progress).setDuration(800).start();
        }else {
            ObjectAnimator.ofInt(this, "secondaryProgress", getSecondaryProgress(), progress).setDuration(800).start();
            ObjectAnimator.ofInt(this, "progress", getProgress(), progress).setDuration(300).start();
        }
    }

}
