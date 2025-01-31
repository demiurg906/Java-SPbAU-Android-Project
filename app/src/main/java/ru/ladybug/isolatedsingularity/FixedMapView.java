package ru.ladybug.isolatedsingularity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.views.MapView;

/** Class adjusting standard OSMDroid MapView by removing unnecessarily event interceptions by parent ViewPager */
public class FixedMapView extends MapView {
    /** {@inheritDoc} */
    public FixedMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
    }
    /** {@inheritDoc} */
    public FixedMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs, boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
    }
    /** {@inheritDoc} */
    public FixedMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /** {@inheritDoc} */
    public FixedMapView(Context context) {
        super(context);
    }
    /** {@inheritDoc} */
    public FixedMapView(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
    }
    /** {@inheritDoc} */
    public FixedMapView(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
    }

    /** true if we intercept MOVE events in order to prevent the view pager to swipe views */
    private boolean interceptMove = false;

    /** {@inheritDoc} */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Prevent the ViewPager to swipe instead of scrolling
        // See https://stackoverflow.com/questions/8594361/horizontal-scroll-view-inside-viewpager
        // Touching the borders allow the view pager to swipe
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // See if we touch the screen borders
                interceptMove = 100 * event.getX() > 5 * getWidth() && 100 * event.getX() < 95 * getWidth();
                break;
            case MotionEvent.ACTION_MOVE:
                if (interceptMove && getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
        }
        return false;
    }
}
