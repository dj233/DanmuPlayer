package com.danmu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.view.View;

import com.ufreedom.floatingview.spring.SimpleReboundListener;
import com.ufreedom.floatingview.spring.SpringHelper;
import com.ufreedom.floatingview.transition.BaseFloatingPathTransition;
import com.ufreedom.floatingview.transition.FloatingPath;
import com.ufreedom.floatingview.transition.PathPosition;
import com.ufreedom.floatingview.transition.YumFloating;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Fischer on 2016/11/8.
 * 弹幕移动轨迹
 */
public class DamuFloating extends BaseFloatingPathTransition {

    private int screenWidth,screenHeight;
    private int floatWidth;
    private DamuPos damuPos;
    private float pos;

    private List<View> visibleDamus;

    public DamuFloating(Context ctx,int floatWidth,DamuPos damPos,List<View> visibleDamus){
        init(ctx);
        this.floatWidth = floatWidth;
        this.damuPos = damPos;
        this.visibleDamus = visibleDamus;
        pos = caculatePos();
    }

    private void init(Context ctx){
        screenWidth = UIUtils.getScreenWidth(ctx);
        screenHeight = UIUtils.getScreenHeight(ctx);
    }

    @Override
    public FloatingPath getFloatingPath() {
        Path path = new Path();
        float floatPos = -pos * screenHeight / 2;
        path.moveTo(screenWidth/2+floatWidth, floatPos);
        path.lineTo(-screenWidth/2-floatWidth,floatPos);
        return FloatingPath.create(path, false);
    }

    /**
     * 计算弹幕出现位置
     * @return
     */
    private float caculatePos(){
        float posMin,posMax;
        switch (damuPos){
            case Top:
                posMin = 0.5f;
                posMax = 0.8f;
                break;
            case Mid:
                posMin = -0.25f;
                posMax = 0.25f;
                break;
            case But:
                posMin = -0.8f;
                posMax = -0.5f;
                break;
            case All:
            default:
                posMin = -0.8f;
                posMax = 0.8f;
        }
        Random random = new Random();
        float rand = random.nextFloat();
        float pos = posMin + rand*(posMax-posMin);
        return pos;
    }

    @Override
    public void applyFloating(final YumFloating yumFloating) {

        final ValueAnimator translateAnimator = ObjectAnimator.ofFloat(getStartPathPosition(), getEndPathPosition());
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                PathPosition floatingPosition = getFloatingPosition(value);
                yumFloating.setTranslationX(floatingPosition.x);
                yumFloating.setTranslationY(floatingPosition.y);

            }
        });
        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                yumFloating.setTranslationX(0);
                yumFloating.setTranslationY(0);
                yumFloating.setAlpha(0f);
                yumFloating.clear();
                View view = yumFloating.getTargetView();
                if (view != null) {
                    visibleDamus.remove(view);
                }
            }
        });

        SpringHelper.createWidthBouncinessAndSpeed(0.0f, 1.0f, 14, 15)
                .reboundListener(new SimpleReboundListener() {
                    @Override
                    public void onReboundUpdate(double currentValue) {
                        yumFloating.setScaleX((float) currentValue);
                        yumFloating.setScaleY((float) currentValue);
                    }
                }).start(yumFloating);

        translateAnimator.setDuration(10000);
        translateAnimator.start();

        View view = yumFloating.getTargetView();
        visibleDamus.add(view);
    }

    /**
     * 弹幕出现位置，全屏、上、中、下
     */
    public enum DamuPos{
        All,Top,Mid,But
    }
}
