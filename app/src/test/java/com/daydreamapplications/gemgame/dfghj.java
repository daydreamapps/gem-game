package com.daydreamapplications.gemgame;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;

public class dfghj {

    public static ValueAnimator ofObject(TypeEvaluator evaluator, Object... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setObjectValues(values);
        anim.setEvaluator(evaluator);
        return anim;
    }
}
