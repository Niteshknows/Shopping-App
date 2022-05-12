package com.example.shoppingapp.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.eap.EapSessionConfig;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.data.ClickListener;
import com.example.shoppingapp.data.MyDbHandler;
import com.example.shoppingapp.model.Item;
import com.example.shoppingapp.ui.CartAdapter;
import com.example.shoppingapp.ui.ProductPage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CartFragment extends Fragment {
    RecyclerView itemList;
    MyDbHandler db;
    List<Item> allItems;
   LinearLayout bg;
   Handler handler=new Handler();
   Runnable runnable;
   int delay=1000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        itemList = view.findViewById(R.id.cart_rv);
        bg=view.findViewById(R.id.empty_cart_view);
        db = new MyDbHandler(getContext());
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshDb();

        return view;
    }


    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                refreshDb();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

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
        allItems=db.getAllItems();

        if(allItems.size()==0) {
            bg.setVisibility(View.VISIBLE);
        return;
        }

        List<Item> allItems2=new ArrayList<>();
        for(Item item : allItems){
            if(item.getQuantity()>0)
                allItems2.add(item);
        }

        if(allItems2.size()!=0) {
            bg.setVisibility(View.GONE);
            itemList.setVisibility(View.VISIBLE);
            CartAdapter mAdapter = new CartAdapter(allItems2, new ClickListener() {
                @Override
                public void onDeleteClicked(View v, int position) {
                      deleteRecord(allItems2.get(position));
                }

                @Override
                public void onEditClicked(View v, int position) {
                     Intent i = new Intent(getActivity(), ProductPage.class);
                    Item item1=allItems2.get(position);
                    Bundle extras=new Bundle();
                    extras.putString("name",item1.getName());
                    extras.putInt("id",item1.getId());
                    extras.putInt("cost",item1.getCost());
                    extras.putInt("quantity",item1.getQuantity());
                    i.putExtras(extras);
                     startActivity(i);
                }
            });
            itemList.setAdapter(mAdapter);
        }else{
            bg.setVisibility(View.VISIBLE);
            itemList.setVisibility(View.GONE);
        }
    }

    private void deleteRecord(Item item1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Log.d("line134","yes clicked");
                item1.setQuantity(0);
                int affectedRows=db.updateItem(item1);
                dialog.dismiss();
                refreshDb();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
}


