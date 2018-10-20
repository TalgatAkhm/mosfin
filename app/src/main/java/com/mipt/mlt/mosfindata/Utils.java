package com.mipt.mlt.mosfindata;

import android.content.res.Resources;

public final class Utils {


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
