package info.androidhive.navigationdrawer.other;

/**
 * Created by Admin on 03-11-2016.
 */

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import info.androidhive.navigationdrawer.Model.AddressModelClass;
import info.androidhive.navigationdrawer.Model.ProductModel;

public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDatabase";
    public static final String TABLE_NAME = "add_to_cart";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, name TEXT, price TEXT, quantity TEXT, image TEXT);";
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private SQLiteDatabase s;

    public static ArrayList<ProductModel> prodlist;
    public static ArrayList<AddressModelClass> address_List;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL(DELETE_TABLE);
        //Create tables again
        onCreate(db);

    }

    public void insertprod_details(Integer prod_id,String Name, String Price, String Quantity, String Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id",prod_id);
        values.put("name", Name);
        values.put("price", Price);
        values.put("quantity", Quantity);
        values.put("image",Image);

       /* // Convert the image into byte array
       ByteArrayOutputStream out = new ByteArrayOutputStream();
        Image.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer = out.toByteArray();
        // Open the database for writing
*/
        // Start the transaction.
        db.beginTransaction();
    /*    db.insert(TABLE_NAME, null, values);*/

        try {
           //values.put("image", buffer);
            // Insert Row
            long i = db.insert(TABLE_NAME, null, values);
            Log.i("Insert", i + "");
            // Insert into database successfully.
            db.setTransactionSuccessful();

            Log.i("^^^^^^^^^^^","^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        } catch (SQLiteException e) {

            Log.i("AAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAA");
            e.printStackTrace();

        } finally {

            Log.i("BBBBBBBBBBB","BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
            Log.i("CCCCCCCCCCCC","CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        }
    }

    public void updateDetails(Integer Product_id,String Quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.beginTransaction();
        values.put("quantity",Quantity);
        Log.i("YYYYYYYYYYYY","YYYYYYYYYYYY"+Quantity);
        db.update(TABLE_NAME,values,"product_id=?",new String[] {Product_id.toString()});
        String str = "SELECT * FROM " +TABLE_NAME + " where product_id = "+Product_id;
        Cursor c = getReadableDatabase().rawQuery(str, null);
        if (c.moveToNext()) {
            do {
                String quant = c.getString(4);

                Log.i("LLLLLLLLLLLLL", "LLLLLLLLLLLLLLLLLLLLLLLLLLLLL" + quant);
            } while (c.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

 public void deleteData(Integer product_id){
     Log.i("delete data","delete data product id"+product_id);
     SQLiteDatabase db = this.getWritableDatabase();
     db.delete(TABLE_NAME,"product_id=?",new String[]{product_id.toString()});
 }

    public  ArrayList<ProductModel> getDataFromDB(){
        Log.i("$$$$$$$$$$$","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        prodlist = new ArrayList<>();
        if (!TABLE_NAME.isEmpty()) {
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor c = getReadableDatabase().rawQuery(selectQuery, null);

            if (c.moveToNext()) {
                do {
                    ProductModel model = new ProductModel();
                    model.setProd_id(c.getInt(1));
                    model.setProd_name(c.getString(2));
                    model.setProd_price(c.getString(3));
                    model.setProd_quantity(c.getString(4));
                   
                    model.setProd_image(c.getString(5));
                    prodlist.add(model);
                    Log.i("!!!!!!!!!!!!!!", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + prodlist);
                } while (c.moveToNext());
            }
            return prodlist;
        }else{
            return null;
        }
    }

    public Cursor retrieveprod_details(){
        Cursor c = (s=this.getWritableDatabase()).query(TABLE_NAME,null,null,null,null,null,null,null);
        return c;
    }


}

