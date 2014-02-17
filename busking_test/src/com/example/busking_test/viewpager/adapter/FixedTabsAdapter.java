
package com.example.busking_test.viewpager.adapter;

import com.example.busking_test.viewpager.TabsAdapter;
import com.example.busking_test.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class FixedTabsAdapter implements TabsAdapter {

    private Activity mContext;

    private String[] mTitles = null;

    public FixedTabsAdapter(Activity ctx) {
        this.mContext = ctx;
    }

    public void setTabMenuArr(String[] titleArr) {
        mTitles = titleArr;
    }

    @Override
    public View getView(int position) {
        Button tab;

        LayoutInflater inflater = mContext.getLayoutInflater();
        tab = (Button) inflater.inflate(R.layout.tab_fixed, null);

        if ( position < mTitles.length )
            tab.setText(mTitles[position]);

        return tab;
    }

}
