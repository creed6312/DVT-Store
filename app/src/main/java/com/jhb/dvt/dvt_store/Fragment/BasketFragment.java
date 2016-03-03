package com.jhb.dvt.dvt_store.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhb.dvt.dvt_store.Adapters.BasketRecyclerViewAdapter;
import com.jhb.dvt.dvt_store.Models.Item;
import com.jhb.dvt.dvt_store.R;

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
        view = inflater.inflate(R.layout.item_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.items_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            basketRecyclerViewAdapter = new BasketRecyclerViewAdapter(itemList);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(basketRecyclerViewAdapter);

        return recyclerView;
    }
}
