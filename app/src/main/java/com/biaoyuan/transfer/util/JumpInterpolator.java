package com.biaoyuan.transfer.util;

import android.animation.TimeInterpolator;

/**
 * Title  :
 * Create : 2017/7/7
 * Author ：chen
 */

public class JumpInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {

        if (input <= 2/5f) {
            return 25 / 4f * input * input;
        } else if (input <= 4/5f) {
            return 1 / 2f + 25 / 2f * (input - 3 / 5f) * (input - 3 / 5f);
        } else {
            return 3 / 4f + 25 * (input - 9 / 10f) * (input - 9 / 10f);
        }

    }
}
