package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hendraanggrian.viewpager.autosliding.R;

/**
 * Simple {@link ViewPager} with auto-sliding behavior.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class AutoSlidingViewPager extends ViewPager implements AutoSlidableView {

    static final long DEFAULT_COUNTDOWN = 5000;
    private static final long DEFAULT_INTERVAL = 50;

    @Nullable private CountDownTimer timer;
    @Nullable private OnTickListener listener;
    private long slidingCountDown;
    private boolean stopOnTouch;

    @NonNull AutoSlidableView internalOnTouch = this;

    public AutoSlidingViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoSlidingViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.autoSlidingViewPagerStyle);
    }

    public AutoSlidingViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AutoSlidingViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoSlidingViewPager, defStyleAttr, defStyleRes);
        slidingCountDown = a.getInt(R.styleable.AutoSlidingViewPager_slidingCountDown, (int) DEFAULT_COUNTDOWN);
        stopOnTouch = a.getBoolean(R.styleable.AutoSlidingViewPager_stopSlideOnTouch, false);
        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (stopOnTouch && isStarting()) {
            internalOnTouch.stop();
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void start() {
        if (timer != null) {
            stop();
        }
        timer = new CountDownTimer(slidingCountDown, DEFAULT_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                final PagerAdapter adapter = getAdapter();
                if (adapter == null) {
                    throw new IllegalStateException("An adapter must be set to start sliding!");
                }
                int currentItem = getCurrentItem();
                if (currentItem < adapter.getCount() - 1) {
                    setCurrentItem(++currentItem);
                } else {
                    setCurrentItem(0);
                }
                start();
            }
        };
        timer.start();
    }

    @Override
    public void stop() {
        if (timer == null) {
            throw new IllegalStateException("ViewPager is not currently sliding!");
        }
        timer.cancel();
        timer = null;
    }

    public boolean isStarting() {
        return timer != null;
    }

    public void setSlidingCountDown(long countDown) {
        this.slidingCountDown = countDown;
    }

    public long getSlidingCountDown() {
        return slidingCountDown;
    }

    public void setStopSlideOnTouch(boolean stopOnTouch) {
        this.stopOnTouch = stopOnTouch;
    }

    public boolean isStopSlideOnTouch() {
        return stopOnTouch;
    }

    public void setOnTickListener(@Nullable OnTickListener listener) {
        this.listener = listener;
    }

    public interface OnTickListener {

        void onTick(long millisUntilFinished);
    }
}