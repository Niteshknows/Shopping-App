package com.example.shoppingapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.data.ClickListener;
import com.example.shoppingapp.data.MyDbHandler;
import com.example.shoppingapp.model.Item;
import com.example.shoppingapp.ui.CartAdapter;
import com.example.shoppingapp.ui.CheckoutAdapter;
import com.example.shoppingapp.ui.ProductPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {
    TextView mrp;
    TextView total_amt;
    Handler handler=new Handler();
    Runnable runnable;
    int delay=100;
    MyDbHandler db;
    List<Item> allItems;
    RecyclerView itemList;
    ProgressBar progressBar;
    int count=0;
    Timer timer;
    LinearLayout ll;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        mrp=view.findViewById(R.id.mrp_tv);
        total_amt=view.findViewById(R.id.total_amt_tv);
        progressBar=view.findViewById(R.id.progressBarCheckout);
        ll=view.findViewById(R.id.content_ll);
        itemList = view.findViewById(R.id.checkout_rv);
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        db = new MyDbHandler(getContext());
        allItems=db.getAllItems();

        int tot_quantity=0;
        for(Item item2 :allItems){
            tot_quantity+=item2.getQuantity();
        }

        if(tot_quantity!=0) {
            itemList.setAdapter(new CheckoutAdapter(allItems));
            int price=0;
            for(Item item1: allItems){
                int cost=item1.getCost();
                int qu=item1.getQuantity();
                price+=cost*qu;
            }
            mrp.setText(String.valueOf(price));
            total_amt.setText(String.valueOf(price));
        }

        return view;
    }

    @Override
    public void onResume() {
        //start handler as activity become visible
        progressBar.setVisibility(View.VISIBLE);
        ll.setVisibility(View.GONE);
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                refreshDb();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 1000ms
                progressBar.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);

            }
        }, 1000);
        super.onResume();

    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }


    public void refreshDb(){
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        db = new MyDbHandler(getContext());
        allItems=db.getAllItems();

        List<Item> allItems2=new ArrayList<>();
        for(Item item : allItems){
            if(item.getQuantity()>0){
                allItems2.add(item);
            }
        }

        if(allItems2.size()!=0) {
            Log.d("line171","size of final list  "+allItems2.size());
            itemList.setAdapter(new CheckoutAdapter(allItems2));
            int price=0;
            for(Item item1: allItems2){
                int cost=item1.getCost();
                int qu=item1.getQuantity();
                price+=cost*qu;
            }
            mrp.setText(String.valueOf(price));
            total_amt.setText(String.valueOf(price));
        }else{
            Log.d("line182","size of final list  "+allItems2.size());

        }
    }
}