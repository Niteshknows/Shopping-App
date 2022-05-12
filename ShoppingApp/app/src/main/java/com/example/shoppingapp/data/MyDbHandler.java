package com.example.shoppingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;

import com.example.shoppingapp.model.Item;
import com.example.shoppingapp.params.params;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, params.DB_NAME, null, params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String create = "CREATE TABLE "+ params.TABLE_NAME + "(" +params.KEY_ID + " INTEGER PRIMARY KEY," +
                params.ITEM_NAME+" TEXT NOT NULL, " + params.ITEM_COST+" INTEGER, "+params.ITEM_QUANTITY+" INTEGER"+")";
       Log.d("LINE 20",create);
       db.execSQL(create);

        addItem(new Item(1,"Laptop",50000,0),db);
        addItem(new Item(2,"Chair",1200,0),db);
        addItem(new Item(3,"Air Conditioner",30000,0),db);
        addItem(new Item(4,"Television",25000,0),db);
        addItem(new Item(5,"Mobile Phone",20000,0),db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addItem(Item item,SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put(params.ITEM_NAME,item.getName());
        values.put(params.ITEM_COST,item.getCost());
        values.put(params.ITEM_QUANTITY,item.getQuantity());
        db.insert(params.TABLE_NAME,null,values);
        Log.d("line 42","successfully inserted in db");
        //closing the connection with db
    }

    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(params.ITEM_NAME, item.getName());
        values.put(params.ITEM_QUANTITY,item.getQuantity());
        values.put(params.ITEM_COST,item.getCost());
        return db.update(params.TABLE_NAME,values,params.KEY_ID+"=?",
                new String[]{String.valueOf(item.getId())});
    }
    
    public List<Item> getAllItems(){
      List<Item> itemList = new ArrayList<>();
      SQLiteDatabase db= this.getReadableDatabase();
      String select =  "SELECT * FROM "+params.TABLE_NAME;
      Cursor cursor = db.rawQuery(select,null);
        Log.d("db line 58","visited");

      //loop through db
        if(cursor.moveToFirst()){
            do{
                Log.d("db line 62","visited");
              Item item = new Item();
              item.setId(Integer.parseInt(cursor.getString(0)));
              item.setName(cursor.getString(1));
              item.setCost(Integer.parseInt(cursor.getString(2)));
              item.setQuantity(Integer.parseInt(cursor.getString(3)));
              itemList.add(item);
            }while(cursor.moveToNext());
        }
         return itemList;
    }

    public void deleteItem(int id){


//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(params.TABLE_NAME,params.KEY_ID+"=?",new String[]{String.valueOf(id)});
//        db.close();
    }
}
