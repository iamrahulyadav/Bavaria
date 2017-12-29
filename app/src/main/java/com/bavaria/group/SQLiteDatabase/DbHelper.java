package com.bavaria.group.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Archirayan on 12-Jul-17.
 * Archirayan Infotech pvt Ltd
 * dilip.bakotiya@gmail.com || info@archirayan.com
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BavariaDatabase";
    private static final int DATABASE_VERSION = 1;
    //table name
    private static final String YEARLY_MEMBERSHIP_TABLE = "yearly_membership_table";
    //table column
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String AMOUNT = "amount";
    public Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE_YEARLY_MEMBERSHIP = "create table " + YEARLY_MEMBERSHIP_TABLE + "("
                + START_DATE + " TEXT,"
                + END_DATE + " TEXT,"
                + AMOUNT + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_YEARLY_MEMBERSHIP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + YEARLY_MEMBERSHIP_TABLE);

    }

    //insert data
//    public void addMembershipData(ArrayList<MembershipPojo> membershipDataa) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        for (int i = 0; i < membershipDataa.size(); i++) {
//            values.put(START_DATE, membershipDataa.get(i).get());
//            values.put(END_DATE, membershipDataa.get(i).getEnd_date());
//            values.put(AMOUNT, membershipDataa.get(i).getAmount());
//
//            // Inserting Row
//            db.insert(YEARLY_MEMBERSHIP_TABLE, null, values);
//        }
//
//        db.close(); // Closing database connection
//    }

    //show table data
//    public ArrayList<MembershipPojo> getYearlyMembershipData() {
//
//        ArrayList<MembershipPojo> membershipDataa = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String MY_QUERY = "SELECT * FROM " + YEARLY_MEMBERSHIP_TABLE;
//        Cursor c = db.rawQuery(MY_QUERY, null);
//
//        if (c.moveToFirst()) {
//            do {
//                MembershipPojo membershipData = new MembershipPojo();
//                membershipData.setStart_date(c.getString(0));
//                membershipData.setEnd_date(c.getString(1));
//                membershipData.setAmount(c.getString(2));
//
//                membershipDataa.add(membershipData);
//
//            } while (c.moveToNext());
//        }
//        return membershipDataa;
//    }

    //delete table
    public void deleteMembershipData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(YEARLY_MEMBERSHIP_TABLE, null, null);
        db.close();
    }

    //check table is empty or not
    public boolean checkMembershipData() {
        boolean hasTables = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + YEARLY_MEMBERSHIP_TABLE, null);

        if (cursor != null && cursor.getCount() > 0) {
            hasTables = true;
            cursor.close();
        }

        return hasTables;
    }
}
