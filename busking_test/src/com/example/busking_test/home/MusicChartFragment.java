
package com.example.busking_test.home;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.busking_test.MainActivity;
import com.example.busking_test.main.BuskingMainActivity;
import com.example.busking_test.util.SdkUtils;
import com.example.busking_test.viewpager.FixedTabsView;
import com.example.busking_test.viewpager.adapter.FixedTabsAdapter;
import com.example.busking_test.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MusicChartFragment extends Fragment implements OnClickListener {
	private FragmentActivity mContext;
    // 뷰페이지 관련
	private ViewPager mPager;
    private FixedTabsView mSwipeyTabs;
	private PagerAdapter mPagerAdapter;
    private FixedTabsAdapter mSwipeyTabsAdapter;
	int mCurPos = 0;
    public String[] tabArrayTitle = { "실시간차트", "일간차트", "주간차트" };
	
	//레프트 메뉴 
    private ImageView m_oBtnList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpage_home_musiclist, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        
        initViewPager(tabArrayTitle.length, 0xFFFFFFFF, 0xFF000000);
        mSwipeyTabs = (FixedTabsView) getView().findViewById(R.id.scrolling_tabs);
        mSwipeyTabsAdapter = new FixedTabsAdapter(this.getActivity());
        mSwipeyTabsAdapter.setTabMenuArr(tabArrayTitle);
        mSwipeyTabs.setAdapter(mSwipeyTabsAdapter);
        mSwipeyTabs.setViewPager(mPager);

        mSwipeyTabs.addListener(mShowListener);
        ChartPagerAdapter adapter = (ChartPagerAdapter) mPagerAdapter;
        if ( null != adapter ) {
            View view = adapter.findViewForPosition(0);
            if ( null != view ) {
//                adapter.setRequest(0, view);
            } else {
                mCheckHandler.sendEmptyMessage(0);
            }
        }
        
        m_oBtnList = (ImageView)getActivity().findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
    }
    
    private void initViewPager(int pageCount, int backgroundColor, int textColor) {
        mPager = (ViewPager) getView().findViewById(R.id.pager);
        mPagerAdapter = new ChartPagerAdapter(getActivity(), pageCount);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(0);
        if ( SdkUtils.hasHoneycomb() ) {
            mPager.setOffscreenPageLimit(3);
        } else {
            mPager.setOffscreenPageLimit(1);
        }
        mPager.setPageMargin(1);

    }
    final Handler mCheckHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChartPagerAdapter adapter = (ChartPagerAdapter) mPagerAdapter;
            View view = adapter.findViewForPosition(0);
            if ( null != view ) {
//                adapter.setRequest(0, view);
            } else {
                mCheckHandler.sendEmptyMessageDelayed(0, 500);
            }
        }
    };
    final OnPageChangeListener mShowListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int arg0) {
            mCurPos = arg0;
            ChartPagerAdapter adapter = (ChartPagerAdapter) mPagerAdapter;
            if ( null != adapter ) {
                View view = adapter.findViewForPosition(arg0);
//                adapter.setRequest(arg0, view);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
        case R.id.title_btn_menu :
        {
        	MainActivity.getInstance().showMenu();
        	break;
        }
        }

    }
    public class ChartPagerAdapter extends PagerAdapter {
        protected transient Context mContext;
        private LayoutInflater mInflater;
        private View mCurrentView;
        private int mLength = 0;
        private TextView mText;

        private HashMap<Integer, View> mViewMap = new HashMap<Integer, View>();

        public ChartPagerAdapter(Context context, int length) {
            super();
            mContext = context;
            mLength = length;
            mInflater = LayoutInflater.from(context);
        }


        public View findViewForPosition(int position) {
            Object object = mViewMap.get(position);
            if ( object != null ) {
                for ( int i = 0; i < mPager.getChildCount(); i++ ) {
                    View view = mPager.getChildAt(i);
                    if ( isViewFromObject(view, object) ) {
                        return view;
                    }
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return mLength;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            // 현재 주화면의 View를 저장
            mCurrentView = (View) object;
            // 현재 주화면의 Position를 저장
            mCurPos = position;
            ((ViewPager) container).getAdapter().getItemPosition(position);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            View itemView = null;
            itemView = mInflater.inflate(R.layout.home_musiclist, null);
            mText = (TextView)itemView.findViewById(R.id.list_text);

            if ( 0 == position ) {
            	 mText.setText(" 뷰 페이저 1!");
            } else if ( 1 == position ) {
            	 mText.setText(" 뷰 페이저 2!");
            } else {
            	 mText.setText(" 뷰 페이저 3!");
            }

            ((ViewPager) container).addView(itemView, 0);

            //현재 아이템 뷰 저장
            mViewMap.put(position, itemView);
            return itemView;
        }

        @Override
        public void destroyItem(View container, int position, Object view) {
            ((ViewPager) container).removeView((View) view);
            //아이템 뷰 제거
            mViewMap.remove(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void finishUpdate(View container) {}

        @Override
        public void startUpdate(View container) {}

    }

}
