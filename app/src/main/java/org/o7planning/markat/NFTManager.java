package org.o7planning.markat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NFTManager  extends SQLiteOpenHelper
{

    private static NFTManager nftManager;

    private static final String DATABASE_NAME = "nftDB";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_NAME_NFT = "nft";
    private static final String COUNTER = "counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "nom";
    private static final String OWNER_FIELD = "possesseur";
    private static final String PRICE_EUR_FIELD = "prix_euro";
    private static final String PRICE_ETH_FIELD = "prix_ether";



    public NFTManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static NFTManager instanceOfDatabase(Context context)
    {
        if(nftManager == null)
            nftManager = new NFTManager(context);

        return  nftManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String script = "CREATE TABLE " + TABLE_NAME_NFT+ "("
                + ID_FIELD + " INTEGER PRIMARY KEY," + NAME_FIELD + " TEXT,"
                + OWNER_FIELD + " INTEGER,"
                + PRICE_EUR_FIELD + " INTEGER,"
                + PRICE_ETH_FIELD + " REAL" +")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NFT);

        // Create tables again
        onCreate(db);
    }

    public void addNFTDataBase(String s_name, int i_own, int i_id, int i_priceEUR, double f_priceETH )
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, i_id);
        contentValues.put(NAME_FIELD, s_name);
        contentValues.put(OWNER_FIELD, i_own);
        contentValues.put(PRICE_EUR_FIELD, i_priceEUR);
        contentValues.put(PRICE_ETH_FIELD, f_priceETH);

        sqLiteDatabase.insert(TABLE_NAME_NFT,null, contentValues);
    }

    public  void populateNFTListeArray()
    {
        int count = this.getUserCount();
        if (count == 0)
        {
            addNFTDataBase("chat 1",0,1, 4,0.00095);
            addNFTDataBase("chat 2",0,2, 20,0.019);
            addNFTDataBase("chat 3",0,3, 40, 0.038);
            addNFTDataBase("chat 4",0,4, 80, 0.076);
            addNFTDataBase("chat 5",0,5, 160,0.15);
            addNFTDataBase("chat 6",0,6, 320,0.31);
        }

    }

    public int getUserCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME_NFT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();


        return 0;
    }

    public List<Nft> getAllNftFree()
    {

        List<Nft> nftList = new ArrayList<Nft>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_NFT +" WHERE possesseur = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Nft nft = new Nft();
                nft.setId(Integer.parseInt(cursor.getString(0)));
                nft.setName(cursor.getString(1));
                nft.setOwner(Integer.parseInt(cursor.getString(2)));
                nft.setPrice(Integer.parseInt(cursor.getString(3)));
                nft.setPriceETH(Double.parseDouble(cursor.getString(4)));
                // Adding note to list
                nftList.add(nft);
            } while (cursor.moveToNext());
        }

        // return note list
        return nftList;
    }

    public List<Nft> getOwnerNft(int id)
    {

        List<Nft> nftList = new ArrayList<Nft>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_NFT +" WHERE possesseur = "+ id ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Nft nft = new Nft();
                nft.setId(Integer.parseInt(cursor.getString(0)));
                nft.setName(cursor.getString(1));
                nft.setOwner(Integer.parseInt(cursor.getString(2)));
                nft.setPrice(Integer.parseInt(cursor.getString(3)));
                nft.setPriceETH(Double.parseDouble(cursor.getString(4)));
                // Adding note to list
                nftList.add(nft);
            } while (cursor.moveToNext());
        }

        // return note list
        return nftList;
    }

    public void setNFTOwner(int owner, int id_nft)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OWNER_FIELD, owner);
        db.update(TABLE_NAME_NFT, values, ID_FIELD + " = ?", new String[] {String.valueOf(id_nft)});
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NFT);
        onCreate(db);
    }

    public Nft getNft(int id)
    {

        Nft nft = new Nft();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_NFT +" WHERE "+ID_FIELD+" = "+ id ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {

                nft.setId(Integer.parseInt(cursor.getString(0)));
                nft.setName(cursor.getString(1));
                nft.setOwner(Integer.parseInt(cursor.getString(2)));
                nft.setPrice(Integer.parseInt(cursor.getString(3)));
                nft.setPriceETH(Double.parseDouble(cursor.getString(4)));

        }

        // return note list
        return nft;
    }

    public void setNFTPrice(int prixEU, double prixETH, int id_nft)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRICE_EUR_FIELD, prixEU);
        values.put(PRICE_ETH_FIELD, prixETH);
        db.update(TABLE_NAME_NFT, values, ID_FIELD + " = ?", new String[] {String.valueOf(id_nft)});
    }

}
