/*
 * Copyright (C) 2011 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.busking_test.viewpager;

import java.util.ArrayList;

import com.example.busking_test.R;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FixedTabsView extends LinearLayout implements ViewPager.OnPageChangeListener {

    @SuppressWarnings("unused")
    private static final String TAG = "com.astuetz.viewpager.extensions";

    private Context mContext;

    private ViewPager mPager;

    private TabsAdapter mAdapter;

    private ArrayList<View> mTabs = new ArrayList<View>();

    private Drawable mDividerDrawable;

    private int mDividerColor = 0xFF636363;
    private int mDividerMarginTop = 0;
    private int mDividerMarginBottom = 0;

    public FixedTabsView(Context context) {
        this(context, null);
    }

    public FixedTabsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerExtensions);
        mDividerDrawable = typeArray.getDrawable(R.styleable.ViewPagerExtensions_dividerDrawable);
    }

    public FixedTabsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        this.mContext = context;

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerExtensions, defStyle, 0);

        mDividerColor = a.getColor(R.styleable.ViewPagerExtensions_dividerColor, mDividerColor);

        mDividerMarginTop = a.getDimensionPixelSize(R.styleable.ViewPagerExtensions_dividerMarginTop, mDividerMarginTop);
        mDividerMarginBottom = a.getDimensionPixelSize(R.styleable.ViewPagerExtensions_dividerMarginBottom, mDividerMarginBottom);

        mDividerDrawable = a.getDrawable(R.styleable.ViewPagerExtensions_dividerDrawable);

        a.recycle();

        this.setOrientation(LinearLayout.HORIZONTAL);
    }

	private ArrayList<OnPageChangeListener> mListeners = new ArrayList<OnPageChangeListener>();

	public void addListener(OnPageChangeListener listener) {
		mListeners.add(listener);
	}
    public void setDeividerDrawble(Drawable drawable) {
        mDividerDrawable = drawable;
    }

    /**
     * Sets the data behind this FixedTabsView.
     * 
     * @param adapter
     *          The {@link TabsAdapter} which is responsible for maintaining the
     *          data backing this FixedTabsView and for producing a view to
     *          represent an item in that data set.
     */
    public void setAdapter(TabsAdapter adapter) {
        this.mAdapter = adapter;

        if ( mPager != null && mAdapter != null )
            initTabs();
    }

    /**
     * Binds the {@link ViewPager} to this View
     * 
     */
    public void setViewPager(ViewPager pager) {
        this.mPager = pager;
        mPager.setOnPageChangeListener(this);

        if ( mPager != null && mAdapter != null )
            initTabs();
    }

    /**
     * Initialize and add all tabs to the layout
     */
    private void initTabs() {

        removeAllViews();
        mTabs.clear();

        if ( mAdapter == null )
            return;

        for ( int i = 0; i < mPager.getAdapter().getCount(); i++ ) {

            final int index = i;

            View tab = mAdapter.getView(i);
            //크기결정 버튼크기
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
            params.height = 100;
            tab.setLayoutParams(params);
            
            this.addView(tab);

            mTabs.add(tab);

            if ( i != mPager.getAdapter().getCount() - 1 ) {
                this.addView(getSeparator());
            }

            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(index);
                }
            });

        }

        selectTab(mPager.getCurrentItem());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
		for (OnPageChangeListener l : mListeners) {
			l.onPageScrollStateChanged(state);
		}
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		for (OnPageChangeListener l : mListeners) {
			l.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
    }

    @Override
    public void onPageSelected(int position) {
        selectTab(position);
		for (OnPageChangeListener l : mListeners) {
			l.onPageSelected(position);
		}
    }

    /**
     * Creates and returns a new Separator View
     * 
     * @return
     */
    private View getSeparator() {
        View v = new View(mContext);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, LayoutParams.FILL_PARENT);
        params.setMargins(0, mDividerMarginTop, 0, mDividerMarginBottom);
        v.setLayoutParams(params);

        if ( mDividerDrawable != null ) {
            v.setBackgroundDrawable(mDividerDrawable);

        } else
            v.setBackgroundColor(mDividerColor);

        return v;
    }

    /**
     * Runs through all tabs and sets if they are currently selected.
     * 
     * @param position
     *          The position of the currently selected tab.
     */
    public void selectTab(int position) {

        for ( int i = 0, pos = 0; i < getChildCount(); i++ ) {

            if ( this.getChildAt(i) instanceof Button ) {
                if ( pos == position ) {
                    ((Button) this.getChildAt(i)).setTextColor(Color.WHITE);
                } else {
                    ((Button) this.getChildAt(i)).setTextColor(0xff6d7278);
                }
                this.getChildAt(i).setSelected(pos == position);
                pos++;
            }

        }
    }

}
