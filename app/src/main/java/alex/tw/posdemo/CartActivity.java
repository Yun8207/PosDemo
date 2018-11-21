package alex.tw.posdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView listView;
    private MyAdapter myAdapter;
    private ArrayList<DataModel> alist;
    private LinkedList<HashMap<String,Object>> item;
    private PosApp posApp;
    private TextView itemPrice;
    private TextView totalPrice;
    private int tprice;
    private String name;
    private int[] cartprice;






//    String[] from = {"pname", "quan","size"};
//    int[] to  = {R.id.item_name,R.id.item_quan};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        posApp = (PosApp)getApplication();

        listView = findViewById(R.id.cart_list);
        itemPrice = findViewById(R.id.item_price);
        totalPrice = findViewById(R.id.total_price);


        itemPrice.setText("");

        cartprice = new int[PosApp.cart.size()];

        for(int i = 0; i<PosApp.cart.size();i++) {

            name = PosApp.cart.get(i).name;
            Cursor cursor = PosApp.menuDb.query("product",new String[]{"price","discount"}, "pname =" + "'"+name+"'",null,null,null,null);
            cursor.moveToFirst();
            cartprice[i] = (cursor.getInt(0)*cursor.getInt(1))/100;


        }

        tprice = 0;

        for(int i =0; i<cartprice.length; i++){
            tprice = tprice + cartprice[i]*PosApp.cart.get(i).quantity;
            itemPrice.append(cartprice[i]*PosApp.cart.get(i).quantity+"\n");

        }

        totalPrice.setText(tprice+"元");



        initListView();

    }


    private void initListView(){




        myAdapter = new MyAdapter(posApp.cart,this);


        listView.setAdapter(myAdapter);


        myAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {

                itemPrice.setText("");

                cartprice = new int[PosApp.cart.size()];

                for(int i = 0; i<PosApp.cart.size();i++) {

                    name = PosApp.cart.get(i).name;
                    Cursor cursor = PosApp.menuDb.query("product",new String[]{"price","discount"}, "pname =" + "'"+name+"'",null,null,null,null);
                    cursor.moveToFirst();
                    cartprice[i] = (cursor.getInt(0)*cursor.getInt(1))/100;


                }

                tprice = 0;

                for(int i =0; i<cartprice.length; i++){
                    tprice = tprice + cartprice[i]*PosApp.cart.get(i).quantity;
                    itemPrice.append(cartprice[i]*PosApp.cart.get(i).quantity+"\n");

                }

                totalPrice.setText(tprice+"元");

            }
        });


    }

    public void gotoReceipt(View view){
        Intent intent = new Intent(this, ReceiptActivity.class);
        intent.putExtra("price", cartprice);

        startActivity(intent);



    }


    public void goBack(View view) {


        onBackPressed();
    }
}
