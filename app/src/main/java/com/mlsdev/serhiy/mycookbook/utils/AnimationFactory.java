package com.mlsdev.serhiy.mycookbook.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mlsdev.serhiy.mycookbook.R;

/**
 * Created by android on 06.04.15.
 */
public class AnimationFactory {

    public static Animation getShowReadyEditButtonAnim(Context mContext){
        return AnimationUtils.loadAnimation(mContext, R.anim.show_ready_btn);
    }

    public static Animation getHideReadyEditButtonAnim(Context mContext){
        return AnimationUtils.loadAnimation(mContext, R.anim.hide_ready_btn);
    }

    public static Animation getMakeEditTextShorterAnim(Context mContext){
        return AnimationUtils.loadAnimation(mContext, R.anim.scale_edit_text_to_smoller_size);
    }

    public static Animation getMakeEditTextLongerAnim(Context mContext){
        return AnimationUtils.loadAnimation(mContext, R.anim.scale_edit_text_to_bigger_size);
    }
}
