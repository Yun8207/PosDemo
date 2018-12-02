package alex.tw.posdemo;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PosApp extends Application {

    private TempDbHelper myHelper;
    public static SQLiteDatabase menuDb;
    public Cursor cursorHot;
    public Cursor cursorA ;
    public Cursor cursorB ;
    public Cursor cursorC ;
    public Cursor cursorAll;
    public int menuVersion = 0;
    private Connect connect;


    public static ArrayList<DataModel> cart;




    String[] menuA = {"A1","A2","A3","A4","A5"}; //Only for example.
    String[] menuB = {"B1","B2","B3","B4","B5","B6","B7"};
    String[] menuC = {"C1","C2","C3","C4","C5","C6","C7","C8","C9"};
    int[] priceA = {15,20,20,25,35}; //Only for example.
    int[] priceB = {30,35,35,40,40,45,50};
    int[] priceC = {25,30,30,35,35,40,40,50,55};

    @Override
    public void onCreate() {


        super.onCreate();



        cart = new ArrayList<>();



        myHelper = new TempDbHelper(this, "alex", null, 1);
        menuDb = myHelper.getReadableDatabase();
        //menuDb.delete("product",null,null);
        menuDb.delete("purchase",null,null);


        if (!menuDb.query("product",new String[]{"pname"}, null,null,null,null,null).moveToFirst()) {//check if table exist
            connect = new Connect();
            connect.execute("");

//            for (int i = 0; i < menuA.length; i++) {
//                values.put("pname", menuA[i]);
//                values.put("price", priceA[i]);
//                values.put("discount", 100);
//                menuDb.insert("product", null, values);
//            }





        }


        cursorA = menuDb.query("product",new String[]{"pname", "price","discount"}, "cid = ?",new String[]{"A"},null,null,null);
        cursorB = menuDb.query("product",new String[]{"pname", "price","discount"}, "cid = ?",new String[]{"B"},null,null,null);
        cursorC = menuDb.query("product",new String[]{"pname", "price","discount"}, "cid = ?",new String[]{"C"},null,null,null);
        cursorHot = menuDb.query("product",new String[]{"pname", "price","discount"}, "discount != 100",null,null,null,null);
        cursorAll = menuDb.query("product",new String[]{"pname", "price","discount"}, null,null,null,null,null);

        int count = cursorAll.getCount();
        while (cursorHot.moveToNext()) {
            String pname = cursorHot.getString(0);
            int price = cursorHot.getInt(1);//cursor.getString(cursor.getColumenIndex("price");


            Log.v("alex",  pname +":NT" +price);


        }



        //Log.v("alex", "count:" +count);


//        cart.add(new DataModel("Tea",1,1,1));
//        cart.add(new DataModel("Coffee",2,0,3));
    }

    private class Connect extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            for (String url : urls) {
                try {
                    ContentValues values = new ContentValues();
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.203:3306/pos_system", "root", "");

                    PreparedStatement pstmt = conn.prepareStatement("SELECT pname, price, category.CID as id FROM product LEFT JOIN pcid ON product.PID = pcid.PID LEFT JOIN category ON pcid.CID = category.CID;");
                    ResultSet rs = pstmt.executeQuery();
                    while(rs.next()) {
                        values.put("pname", rs.getString("pname"));
                        values.put("price", rs.getInt("price"));
                        values.put("discount", 100);
                        values.put("cid", rs.getString("id"));
                        menuDb.insert("product", null, values);
                        Log.v("alex",rs.getString("id"));
                    }


//                    for (int i = 0; i < menuB.length; i++) {
//                        values.put("pname", menuB[i]);
//                        values.put("price", priceB[i]);
//                        values.put("discount", 100);
//                        menuDb.insert("product", null, values);
//                    }
//
//                    for (int i = 0; i < menuC.length; i++) {
//                        values.put("pname", menuC[i]);
//                        values.put("price", priceC[i]);
//                        values.put("discount", 100);
//                        menuDb.insert("product", null, values);
//                    }
                    // c.getString(0);








                } catch (Exception e) {
                    Log.v("alex", e + "");
                }
            }
            return null;
        }

    }


}

