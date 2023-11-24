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

public class sqlitehelper extends SQLiteOpenHelper {







        public static String DATABASE_NAME = "chat.db";
        public static int DATABASE_VERSION = 1;

        Context context;

    public sqlitehelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context=context;
    }

    public static class chattable implements BaseColumns {

            public static String TABLE_NAME = "Chat";
            public static String _ID = "message_id";
            public static String _MSG = "msg";
            public static String _DPURL = "dpurl";
            public static String _IURL = "imageurl";
            public static String _TIMESTAMP = "timestamp";

        }

        public static String CREATE_CHAT_TABLE = "CREATE TABLE " + chattable.TABLE_NAME + "(" +
                chattable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                chattable._MSG + " TEXT, " +
                chattable._DPURL + " TEXT, " +
                chattable._IURL + " TEXT, " +
                chattable._TIMESTAMP + " TEXT)";

        public static String DROP_CHAT_TABLE = "DROP TABLE IF EXISTS " + chattable.TABLE_NAME;



        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(CREATE_CHAT_TABLE);
            Toast.makeText(context, "table created", Toast.LENGTH_SHORT).show();

        }

        @Override                                           //OLDV, NEWV V= VERSION
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            //CALL: WHEN VERSION CHANGE E.G ADD ANY COLUMN

            sqLiteDatabase.execSQL(DROP_CHAT_TABLE);
            onCreate(sqLiteDatabase);

        }


    public void deleteallitems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(chattable.TABLE_NAME, null, null);
        db.close();
    }

    public void insertallitems(List<Model> items) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;

        for (Model item : items) {
            values = new ContentValues();
            values.put(chattable._MSG, item.getMsg());
            values.put(chattable._DPURL, item.getDp());
            values.put(chattable._TIMESTAMP, item.getTimestamp());

            db.insert(chattable.TABLE_NAME, null, values);
        }

        db.close();
    }


    public void insertMessage(Model message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(chattable._MSG, message.getMsg());
        values.put(chattable._DPURL, message.getDp());
        values.put(chattable._TIMESTAMP, message.getTimestamp());

        db.insert(chattable.TABLE_NAME, null, values);
        db.close();
    }


    public void deleteItem(String messageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(chattable.TABLE_NAME, chattable._ID + " = ?", new String[]{messageId});
        db.close();
    }


    @SuppressLint("Range")
    public List<Model> getAllMessages() {
        List<Model> messageList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + chattable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Model message = new Model();
                message.setMsg(cursor.getString(cursor.getColumnIndex(chattable._MSG)));
                message.setDp(cursor.getString(cursor.getColumnIndex(chattable._DPURL)));
                message.setTimestamp(cursor.getString(cursor.getColumnIndex(chattable._TIMESTAMP)));
                // Add more fields as required
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messageList;
    }



}
