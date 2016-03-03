package com.jhb.dvt.dvt_store.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jhb.dvt.dvt_store.Adapters.BasketRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.Constants.Constants;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;
import com.jhb.dvt.dvt_store.Utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hmdau on 2016/03/01.
 */
public class BasketFragment extends Fragment {

    List<Item> itemList = new ArrayList<>();
    View view;
    private RecyclerView recyclerView;
    private BasketRecyclerViewAdapter basketRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.basket_activity, container, false);
        RelativeLayout basketContainer = (RelativeLayout) view.findViewById(R.id.basketContainer);

        recyclerView = (RecyclerView) view.findViewById(R.id.basket_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            basketRecyclerViewAdapter = new BasketRecyclerViewAdapter(itemList);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(basketRecyclerViewAdapter);



        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return Utilities.webServiceCall(Constants.HostAddress + "/Api/GetCart?ids=58,57&apiToken=" + Constants.ApiKey, itemList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                basketRecyclerViewAdapter.notifyDataSetChanged();
                //progressDialog.dismiss();
                super.onPostExecute(s);
            }
        });




        return basketContainer;
    }
}
