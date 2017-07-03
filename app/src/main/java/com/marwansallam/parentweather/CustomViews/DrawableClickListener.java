package com.marwansallam.parentweather.CustomViews;

/**
 * Created by marwansallam on 7/2/17.
 */

public interface DrawableClickListener {
    public static enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT};

    public void onClick(DrawablePosition target);

}