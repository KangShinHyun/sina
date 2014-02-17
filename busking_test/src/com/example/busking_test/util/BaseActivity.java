
package com.example.busking_test.util;

import com.example.busking_test.main.BuskingMainActivity;

import android.os.Bundle;


public class BaseActivity extends BuskingMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    	
  
}
