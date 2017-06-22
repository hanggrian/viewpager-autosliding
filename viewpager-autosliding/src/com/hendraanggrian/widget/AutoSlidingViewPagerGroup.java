package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hendraanggrian.viewpager.autosliding.R;

import static com.hendraanggrian.widget.AutoSlidingViewPager.DEFAULT_COUNTDOWN;

/**
 * A container of {@link AutoSlidingViewPager} with a counting down progress bar.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class AutoSlidingViewPagerGroup extends FrameLayout implements AutoSlidingViewPager.OnTickListener, AutoSlidableView {

    private static final boolean DEBUG_ANIMATE = false;

    @NonNull private final AutoSlidingViewPager viewPager;
    @NonNull private final ProgressBar progressBar;

    public AutoSlidingViewPagerGroup(@NonNull Context context) {
        this(context, null);
    }

    public AutoSlidingViewPagerGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.autoSlidingViewPagerStyle);
    }

    public AutoSlidingViewPagerGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.autoslidingviewpagergroup, this, true);
        viewPager = (AutoSlidingViewPager) findViewById(R.id.autoslidingviewpager);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoSlidingViewPager, defStyleAttr, 0);
        viewPager.setSlidingCountDown(a.getInt(R.styleable.AutoSlidingViewPager_slidingCountDown, (int) DEFAULT_COUNTDOWN));
        viewPager.setStopSlideOnTouch(a.getBoolean(R.styleable.AutoSlidingViewPager_stopSlideOnTouch, false));
        a.recycle();

        viewPager.setOnTickListener(this);
        viewPager.internalOnTouch = this;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long progress = progressBar.getMax() * millisUntilFinished / viewPager.getSlidingCountDown();
        if (!DEBUG_ANIMATE && Build.VERSION.SDK_INT >= 24) {
            progressBar.setProgress((int) progress, true);
        } else {
            progressBar.setProgress((int) progress);
        }
    }

    @Override
    public void start() {
        viewPager.start();
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void stop() {
        viewPager.stop();
        progressBar.setVisibility(GONE);
    }

    @NonNull
    public AutoSlidingViewPager getViewPager() {
        return viewPager;
    }

    @NonNull
    public ProgressBar getProgressBar() {
        return progressBar;
    }
}