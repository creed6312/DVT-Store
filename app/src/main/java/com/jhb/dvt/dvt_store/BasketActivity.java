package com.jhb.dvt.dvt_store;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.jhb.dvt.dvt_store.Fragment.BasketFragment;
import com.jhb.dvt.dvt_store.Fragment.MainActivityFragment;

/**
 * Created by Hmdau on 2016/02/26.
 */
public class BasketActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basket_activity);


        BasketFragment frag = new BasketFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.basketContainer, frag);
        transaction.commit();


    }

}
