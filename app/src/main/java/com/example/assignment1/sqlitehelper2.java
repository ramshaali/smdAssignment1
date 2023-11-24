package com.example.assignment1;





import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

    public class sqlitehelper2 extends SQLiteOpenHelper {

        public static String DATABASE_NAME = "item.db";
        public static int DATABASE_VERSION = 1;

        Context context;

        public sqlitehelper2(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        public sqlitehelper2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public static class itemtable implements BaseColumns {
            public static String TABLE_NAME = "Item";
            public static String _ID = "item_id";
            public static String _NAME = "name";
            public static String _IMG = "img";
            public static String _PRICE = "price";
            public static String _VIEWS = "views";
            public static String _DATE = "date";
            public static String _OWNER_ID = "ownerid";
            public static String _DESC = "desc";
        }

        public static String CREATE_ITEM_TABLE = "CREATE TABLE " + itemtable.TABLE_NAME + "(" +
                itemtable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                itemtable._NAME + " TEXT, " +
                itemtable._IMG + " TEXT, " +
                itemtable._PRICE + " TEXT, " +
                itemtable._VIEWS + " INTEGER, " +
                itemtable._DATE + " TEXT, " +
                itemtable._OWNER_ID + " TEXT, " +
                itemtable._DESC + " TEXT)";

        public static String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + itemtable.TABLE_NAME;

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
            Toast.makeText(context, "Item table created", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_ITEM_TABLE);
            onCreate(sqLiteDatabase);
        }

        public void insertItem(itemcard item) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(itemtable._NAME, item.getName());
            values.put(itemtable._IMG, item.getImg());
            values.put(itemtable._PRICE, item.getPrice());
            values.put(itemtable._VIEWS, item.getViews());
            values.put(itemtable._DATE, item.getDate());
            values.put(itemtable._OWNER_ID, item.getOwnerid());
            values.put(itemtable._DESC, item.getDesc());

            db.insert(itemtable.TABLE_NAME, null, values);
            db.close();
        }


        public void deleteItem(String itemId) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(itemtable.TABLE_NAME, itemtable._ID + " = ?", new String[]{itemId});
            db.close();
        }
        public void deleteallitems() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(itemtable.TABLE_NAME, null, null);
            db.close();
        }

        public void insertallitems(List<itemcard> items) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values;

            for (itemcard item : items) {
                values = new ContentValues();
                values.put(itemtable._NAME, item.getName());
                values.put(itemtable._IMG, item.getImg());
                values.put(itemtable._PRICE, item.getPrice());
                values.put(itemtable._VIEWS, item.getViews());
                values.put(itemtable._DATE, item.getDate());
                values.put(itemtable._OWNER_ID, item.getOwnerid());
                values.put(itemtable._DESC, item.getDesc());

                db.insert(itemtable.TABLE_NAME, null, values);
            }

            db.close();
        }
        @SuppressLint("Range")
        public List<itemcard> getAllItems() {
            List<itemcard> itemList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + itemtable.TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    itemcard item = new itemcard();
                    item.setName(cursor.getString(cursor.getColumnIndex(itemtable._NAME)));
                    item.setImg(cursor.getString(cursor.getColumnIndex(itemtable._IMG)));
                    item.setPrice(cursor.getString(cursor.getColumnIndex(itemtable._PRICE)));
                    item.setViews(cursor.getInt(cursor.getColumnIndex(itemtable._VIEWS)));
                    item.setDate(cursor.getString(cursor.getColumnIndex(itemtable._DATE)));
                    item.setOwnerid(cursor.getString(cursor.getColumnIndex(itemtable._OWNER_ID)));
                    item.setDesc(cursor.getString(cursor.getColumnIndex(itemtable._DESC)));
                    // Add more fields as required
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return itemList;
        }
    }


