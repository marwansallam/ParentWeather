package com.marwansallam.parentweather.CustomViews;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by marwansallam on 7/2/17.
 */

public class Fonts {
    public static Typeface getFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/proximanovalight.ttf");
        return lighttypeface;
    }


    public static Typeface getWFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/wfont.ttf");
        return lighttypeface;
    }

    public static Typeface getBFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/proximanova-semibold-webfont.ttf");
        return lighttypeface;
    }

    public static Typeface getRFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Regular.ttf");
        return lighttypeface;
    }

    public static Typeface getCFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/Calibri.ttf");
        return lighttypeface;
    }
}
