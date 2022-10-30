package DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//dbhelper
public class dbhelper extends SQLiteOpenHelper {

    public dbhelper(Context context) {
        super(context,"LoginNew1.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create Table usersNew(username Text primary key, password Text, role Text)");
        sqLiteDatabase.execSQL("create Table userVehicleNew(username Text primary key, password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop Table if exists usersNew");
        sqLiteDatabase.execSQL("drop Table if exists userVehicleNew");
    }

    public boolean insertData(String username, String password, String role){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("username",username);
        contentvalues.put("password",password);
        contentvalues.put("role",role);

        long result = myDB.insert("usersNew",null,contentvalues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertDataVehicle(String username, String password){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("username",username);
        contentvalues.put("password",password);
//        contentvalues.put("role",role);

        long result = myDB.insert("userVehicleNew",null,contentvalues);

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkUser(String username){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from usersNew where username = ?",new String[] {username});

        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkUserVehicle(String username){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from userVehicleNew where username = ?",new String[] {username});

        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }



    public Boolean checkCredentials(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from usersNew where username = ? and password = ?", new String[]{username, password});

        if (cursor.getCount() > 0) {

            return true;
        }
        else {
            return false;
        }

    }

    public Boolean checkCredentialsVehicle(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from userVehicleNew where username = ? and password = ?", new String[]{username, password});

        if (cursor.getCount() > 0) {

            return true;
        }
        else {
            return false;
        }

    }

    public String getUsernameVehicle(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String user = String.valueOf(myDB.rawQuery("select username from userVehicleNew where username = ?", new String[]{username}));
//        String userRole = String.valueOf(cursorRole);
        Log.i("UserName of this user is", "getUser: "+user);
        return user;

    }


    public Cursor getRole(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursorRole = myDB.rawQuery("select role from usersNew where username = ?", new String[]{username});
        String userRole = String.valueOf(cursorRole);
        Log.i("UserRole", "getRole: "+userRole);
        return cursorRole;

    }


    public String getUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String user = String.valueOf(myDB.rawQuery("select username from usersNew where username = ?", new String[]{username}));
        return user;


    }









}
