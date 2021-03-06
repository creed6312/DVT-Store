package com.jhb.dvt.dvt_store;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jhb.dvt.dvt_store.Models.BasketItem;
import com.jhb.dvt.dvt_store.Utils.Utilities;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView itemDetailQuantity;
    private ImageView itemDetailImage;
    private TextView itemDetailName;
    private TextView itemDetailDetails;
    private TextView itemDetailPrice;
    private FloatingActionButton floatingActionBuy, floatActionMinus, floatActionPlus;
    private Menu menu ;

    private void itemInstantiation() {
        itemDetailImage = (ImageView) findViewById(R.id.idItemDetailImage);
        itemDetailName = (TextView) findViewById(R.id.idItemDetailName);
        itemDetailDetails = (TextView) findViewById(R.id.idItemDetailDetails);
        itemDetailPrice = (TextView) findViewById(R.id.idItemDetailPrice);
        itemDetailQuantity = (TextView) findViewById(R.id.idDetailQuantity);
        floatingActionBuy = (FloatingActionButton) findViewById(R.id.floatingActionBuy);
        floatActionPlus = (FloatingActionButton) findViewById(R.id.floatActionPlus);
        floatActionMinus = (FloatingActionButton) findViewById(R.id.floatActionMinus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemInstantiation();
        final ProgressBar loadingBar = (ProgressBar) findViewById(R.id.detail_loading_bar);
        checkQuantity();

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("itemImage"))
                .into(new GlideDrawableImageViewTarget(itemDetailImage) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        loadingBar.setVisibility(View.VISIBLE);
                        super.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation animation) {
                        loadingBar.setVisibility(View.INVISIBLE);
                super.onResourceReady(drawable, animation);
            }
        });
        itemDetailName.setText(getIntent().getStringExtra("itemName"));
        itemDetailDetails.setText(Html.fromHtml(getIntent().getStringExtra("itemDetails")));
        itemDetailPrice.setText(getIntent().getStringExtra("itemPrice"));
        floatingActionBuy();
        floatActionIncrease();
        floatActionDecrease();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            return true;
        }
        else if (item.getItemId() == R.id.action_cart)
        {
            startActivity(new Intent(getApplicationContext(),BasketActivity.class));
        }
        else if (item.getItemId() == R.id.action_blank)
        {

        }
        else
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void checkQuantity() {
        String id = getIntent().getStringExtra("itemID");

        for (int i = 0; i < Utilities.basketItems.size(); i++) {
            if (Utilities.basketItems.get(i).getId().equals(id)) {
                showQuantity();
                itemDetailQuantity.setText(String.valueOf(Utilities.basketItems.get(i).getQuantity()));
                return;
            }
        }
        hideQuantity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        Utilities.CheckCart(menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_blank).setVisible(true);
        return true;
    }

    private void hideQuantity() {
        floatingActionBuy.setVisibility(View.VISIBLE);
        floatActionMinus.setVisibility(View.INVISIBLE);
        floatActionPlus.setVisibility(View.INVISIBLE);
        itemDetailQuantity.setVisibility(View.INVISIBLE);
    }

    private void showQuantity() {
        floatingActionBuy.setVisibility(View.INVISIBLE);
        itemDetailQuantity.setVisibility(View.VISIBLE);
        floatActionMinus.setVisibility(View.VISIBLE);
        floatActionPlus.setVisibility(View.VISIBLE);
    }

    private void floatActionIncrease() {
        floatActionPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Cart!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                for (int i = 0; i < Utilities.basketItems.size(); i++) {
                    if (Utilities.basketItems.get(i).getId().equals(getIntent().getStringExtra("itemID"))) {
                        Utilities.basketItems.get(i).increaseQuantity();
                        itemDetailQuantity.setText(String.valueOf(Utilities.basketItems.get(i).getQuantity()));
                        Utilities.saveBasket(getApplicationContext());
                        return;
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        Utilities.CheckCart(menu);
        checkQuantity();
        super.onResume();
    }

    private void floatActionDecrease() {
        floatActionMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Utilities.basketItems.size(); i++) {
                    if (Utilities.basketItems.get(i).getId().equals(getIntent().getStringExtra("itemID"))) {
                        Utilities.basketItems.get(i).decreaseQuantity();
                        itemDetailQuantity.setText(String.valueOf(Utilities.basketItems.get(i).getQuantity()));
                        if (Utilities.basketItems.get(i).getQuantity() < 1) {
                            Snackbar.make(view, "Removed from Cart!", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            Utilities.basketItems.remove(i);
                            Utilities.CheckCart(menu);
                            hideQuantity();
                        }
                        Utilities.saveBasket(getApplicationContext());
                        return;
                    }
                }
            }
        });
    }

    private void floatingActionBuy() {
        floatingActionBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Cart!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                Utilities.basketItems.add(new BasketItem(getIntent().getStringExtra("itemID")));
                Utilities.saveBasket(getApplicationContext());
                itemDetailQuantity.setText("1");
                showQuantity();
                Utilities.CheckCart(menu);
            }
        });
    }
}
