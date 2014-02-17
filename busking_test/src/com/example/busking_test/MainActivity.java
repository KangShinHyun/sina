
package com.example.busking_test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.busking_test.home.MainHomeFragment;
import com.example.busking_test.util.BaseActivity;
import com.example.busking_test.R;

public class MainActivity extends BaseActivity {
    private Context mContext;
    private static MainActivity mInstance;
    
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        mInstance = this;
        
    }

    @Override
    protected void onNewIntent(Intent intent) {
       
        super.onNewIntent(intent);

    }

   
    public static MainActivity getInstance() {
        return mInstance;
    }

  
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //브로드캐스트 해제
    }

    @Override
    protected void onDestroy() {
       
        super.onDestroy();
    }
    public void replaceFragment(Class<?> clss, Bundle bundle, boolean isAddStack) {
        Fragment fragment = Fragment.instantiate(this, clss.getName(), bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        if ( isAddStack ) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
        MainActivity.getInstance().showContent();
    }

    public Fragment getCurFragment() {
        Fragment frg = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        return frg;
    }
    public  void goHomeFragment(Context context) {
        if ( context instanceof MainActivity ) {
            FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment frg = fm.findFragmentById(R.id.content_frame);
            if ( frg.getClass() != MainHomeFragment.class ) {
                replaceFragment(MainHomeFragment.class, null, false);
            }
        }
    }
}
