package com.danmu;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufreedom.floatingview.Floating;
import com.ufreedom.floatingview.FloatingBuilder;
import com.ufreedom.floatingview.FloatingElement;
import com.ufreedom.floatingview.effect.TranslateFloatingTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fischer on 2016/11/4.
 */
public class DamBuilder {
    private Activity activity;
    private View root;
    private LayoutInflater inflater;
    private Floating floating;

    /** 弹幕字体颜色 */
    private int danmuTextColor = -1;
    /** 弹幕字体大小（sp） */
    private int danmuTextSize = -1;
    /** 弹幕右侧点赞按钮是否显示 */
    private boolean likeVisible = true;
    /** 弹幕出现位置 */
    private DamuFloating.DamuPos damuPos = DamuFloating.DamuPos.All;
    /** 点赞按钮点击事件 */
    private OnLickClickListener onLickClickListener;

    private List<View> visibleDamus;

    public DamBuilder(Activity activity, View layer){
        this.activity = activity;
        this.root = layer;
        this.inflater = LayoutInflater.from(activity);
        this.floating = new Floating(activity);
        visibleDamus = new ArrayList<>();
    }

    /**
     * 设置弹幕字体颜色
     * @param color
     * @return
     */
    public DamBuilder setDamTxtColor(int color){
        this.danmuTextColor = color;
        return this;
    }

    /**
     * 设置弹幕字体大小 （单位sp）
     * @param sp
     * @return
     */
    public DamBuilder setDamTxtSize(int sp){
        this.danmuTextSize = sp;
        return this;
    }

    /**
     * 设置弹幕弹出位置
     * @param pos 全屏、上、中、下
     * @return
     */
    public DamBuilder setDamPos(DamuFloating.DamuPos pos){
        this.damuPos = pos;
        return this;
    }

    /**
     * 设置是否显示点赞按钮
     * @param visible
     * @return
     */
    public DamBuilder setLikeVisible(boolean visible){
        this.likeVisible = visible;
        return this;
    }

    /**
     * 设置点赞按钮点击事件
     * @param likeClickListener
     * @return
     */
    public DamBuilder setOnLikeClickListener(OnLickClickListener likeClickListener){
        this.onLickClickListener = likeClickListener;
        return this;
    }

    public DamBuilder setDanmuVisible(boolean visible){
        int len = visibleDamus.size();
        for(int i = 0 ; i<len ; i++){
            View v = visibleDamus.get(i);
            if(v != null){
                v.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
            }
        }
        return this;
    }

    /**
     * 发送弹幕
     * @param danmu
     */
    public void sendDanmu(Danmu danmu) {
        View viewDm = inflater.inflate(R.layout.danmu, null);
        TextView tvTxt = (TextView) viewDm.findViewById(R.id.txt);
        ImageView ivLike = (ImageView) viewDm.findViewById(R.id.icLike);
        tvTxt.setText(danmu.damuText);
        if(danmuTextColor != -1){
            tvTxt.setTextColor(danmuTextColor);
        }
        if(danmuTextSize != -1){
            tvTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,danmuTextSize);
        }
        if(likeVisible) {
            ivLike.setVisibility(View.VISIBLE);
            ivLike.setOnClickListener(new OnDanmuLikeClick(danmu.damuId));
        }else{
            ivLike.setVisibility(View.INVISIBLE);
        }

        int floatWidth = getFloatWidth(viewDm);
        FloatingElement floatingElement = new FloatingBuilder()
                .anchorView(root)
                .targetView(viewDm)
                .floatingTransition(new DamuFloating(activity,floatWidth,damuPos,visibleDamus))
                .build();
        floating.startFloating(floatingElement);
    }

    private int getFloatWidth(View v){
        int w =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        v.measure(w,h);
        return v.getMeasuredWidth();
    }

    private class OnDanmuLikeClick implements View.OnClickListener{
        private int danmuId;

        public OnDanmuLikeClick(int danmuId) {
            this.danmuId = danmuId;
        }

        @Override
        public void onClick(View view) {
            View vfloat = inflater.inflate(R.layout.ic_like,null);
            FloatingElement floatingElement = new FloatingBuilder()
                    .anchorView(view)
                    .targetView(vfloat)
                    .floatingTransition(new TranslateFloatingTransition())
                    .build();
            floating.startFloating(floatingElement);
            if(onLickClickListener != null){
                onLickClickListener.onLickClick(danmuId);
            }
        }
    }

    public interface OnLickClickListener{
        void onLickClick(int danmuId);
    }
}
