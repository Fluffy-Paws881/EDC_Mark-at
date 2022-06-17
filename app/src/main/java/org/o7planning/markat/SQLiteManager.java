package org.o7planning.markat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "userDB";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_NAME_USER = "user";
    private static final String COUNTER = "counter";

    private static final String ID_FIELD = "id";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";



    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
        sqLiteManager = new SQLiteManager(context);

        return  sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String script = "CREATE TABLE " + TABLE_NAME_USER + "("
                + ID_FIELD + " INTEGER PRIMARY KEY," + USERNAME_FIELD + " TEXT,"
                + PASSWORD_FIELD + " TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);

        // Create tables again
        onCreate(db);
    }

    public void addUserDataBase(String s_user, String s_psw, int i_id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, i_id);
        contentValues.put(USERNAME_FIELD, s_user);
        contentValues.put(PASSWORD_FIELD, s_psw);

        sqLiteDatabase.insert(TABLE_NAME_USER,null, contentValues);
    }

    public  void populateUserListeArray()
    {
        int count = this.getUserCount();
       if (count == 0)
       {
                addUserDataBase("b.riff","123",1);
                addUserDataBase("g.madembo","456",2);
                addUserDataBase("j.zumkir","789",3);
       }

    }

    public int getUserCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();


        return count;
    }

    /*public User getNote(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_USER, new String[] { ID_FIELD,
                        USERNAME_FIELD, PASSWORD_FIELD }, ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return note
        return note;
    }*/


    public List<User> getAllUser()
    {

        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUser_name(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding note to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return note list
        return userList;
    }


}
