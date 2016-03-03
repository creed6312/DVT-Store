package com.jhb.dvt.dvt_store;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.jhb.dvt.dvt_store.Utils.FeaturedLoader;

/**
 * Created by Hmdau on 2016/02/26.
 */
public class BasketActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basket_activity);




    }

    private void createSlider() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.stopAutoCycle();
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        new FeaturedLoader(mDemoSlider, this, "GetCart",getSupportFragmentManager()).execute();
    }
}
