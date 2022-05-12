package com.example.shoppingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.data.MyDbHandler;
import com.example.shoppingapp.model.Item;
import com.example.shoppingapp.ui.fragments.CartFragment;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProductPage extends AppCompatActivity {
    Button cancel,save_btn;
    TextView costEt,name;
    EditText prodID;
    LinearLayout inc;
    TextView t1, t2, t3,finalAmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        cancel=findViewById(R.id.cancel_productPage);
        Toast.makeText(getApplicationContext(),"Please enter Product ID from1 to 5",Toast.LENGTH_SHORT).show();
        prodID=findViewById(R.id.prod_id);
        name=findViewById(R.id.prod_name);
        save_btn=findViewById(R.id.save_btn);
        inc = findViewById(R.id.inc);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        finalAmt=findViewById(R.id.final_amt);
        costEt=findViewById(R.id.cost);

        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            Log.d("line50","now");
             String name_str = extras.getString("name");
             String id=String.valueOf(extras.getInt("id"));

             String cost=String.valueOf(extras.getInt("cost"));
             String quantity=String.valueOf(extras.getInt("quantity"));
            Log.d("line55","values are "+id+" "+name_str);

             prodID.setText(id);
             costEt.setText(cost);
             name.setText(name_str);
             t2.setText(quantity);
             updateFinalAmt();
        }
        MyDbHandler db = new MyDbHandler(getApplicationContext());
        List<Item> allItems = db.getAllItems();
        Log.d("line67","size of list "+allItems.size());
        prodID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                List<Item> allItems2=allItems;
               String val= prodID.getText().toString();
               if(val.equals("1")){
                   Item item1=allItems2.get(0);
                   name.setText(item1.getName());
                   costEt.setText(String.valueOf(item1.getCost()));
                   updateFinalAmt();
               }else if(val.equals("2")){
                   Item item1=allItems2.get(1);
                   name.setText(item1.getName());
                   costEt.setText(String.valueOf(item1.getCost()));
                   updateFinalAmt();

               }else if(val.equals("3")){
                   Item item1=allItems2.get(2);
                   name.setText(item1.getName());
                   costEt.setText(String.valueOf(item1.getCost()));
                   updateFinalAmt();

               }else if(val.equals("4")){
                   Item item1=allItems2.get(3);
                   name.setText(item1.getName());
                   costEt.setText(String.valueOf(item1.getCost()));
                   updateFinalAmt();

               }else if(val.equals("5")){
                   Item item1=allItems2.get(4);
                   name.setText(item1.getName());
                   costEt.setText(String.valueOf(item1.getCost()));
                   updateFinalAmt();

               }else{
                   if(val.length()>0)
                   Toast.makeText(getApplicationContext(),"Please enter ID from 1 to 5",Toast.LENGTH_SHORT).show();
               }

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc(false);
                updateFinalAmt();
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc(true);
                updateFinalAmt();
            }
        });

    }

    public void goBack(View view){
        finish();
    }

    private void inc(Boolean x){
        int y = Integer.parseInt(t2.getText().toString());
        if(x){
            y++;
            t2.setText(String.valueOf(y));
        }else {
            y--;
            if(y <0){
                t2.setText("0");
            }else {
                t2.setText(String.valueOf(y));
            }
        }
    }

    private void updateFinalAmt(){
        String q = t2.getText().toString();
        String c= costEt.getText().toString();
        if(q.length()==0 || c.length()==0) return;
        int qu=Integer.parseInt(q);
        int co=Integer.parseInt(c);
        int amt=qu*co;
        finalAmt.setText(Integer.toString(amt));
    }

    public void saveItem(View view) {
        String q = t2.getText().toString();
        if(q.equals("0")){
            Toast.makeText(getApplicationContext(),"Add quantity",Toast.LENGTH_SHORT).show();
            return;
        }
        String c= costEt.getText().toString();

        int qu=Integer.parseInt(q);
        int co=Integer.parseInt(c);
        String name_tv=name.getText().toString();

        String id_str=prodID.getText().toString();
        if(id_str.equals("")){
            Toast.makeText(getApplicationContext(),"Product ID is required",Toast.LENGTH_SHORT).show();
            return;
        }

        MyDbHandler db = new MyDbHandler(getApplicationContext());
        List<Item> allItems = db.getAllItems();
        for(Item item1:allItems){
            String str= String.valueOf(item1.getId());
            if(str.equals(id_str)){
                //updating record;
                item1.setQuantity(qu);
                item1.setCost(co);
                int affectedRows=db.updateItem(item1);
                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }


        //CREATING AN ITEM TO ADD IN DB
//        Item newItem = new Item();
//        newItem.setId(product_id);
//        newItem.setName(name_tv);
//        newItem.setCost(co);
//        newItem.setQuantity(qu);
//        //ADDING IN DB
//        db.addItem(newItem);
//        Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
//        finish();
    }
}
