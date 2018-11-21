package alex.tw.posdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private AlertDialog memberLogin;
    private AlertDialog setPrice;

    private GridView gridSubMenu;
    private ScrollView gridMenuHot;
    private SimpleAdapter simpleAdapter;
    private PosApp posApp;
    private TextView cartNum;

    private GridView gridSetMenu;
    private SimpleAdapter simpleAdapter2;
    String[] menuA = {"A1","A2","A3","A4","A5"}; //Only for example.
    String[] menuB = {"B1","B2","B3","B4","B5","B6","B7"};
    String[] menuC = {"C1","C2","C3","C4","C5","C6","C7","C8","C9"};
    String[] priceA = {"15","20","20","25","35"}; //Only for example.
    String[] priceB = {"30","35","35","40","40","45","50"};
    String[] priceC = {"25","30","30","35","35","40","40","50","55"};
    String[] menuSub = {"咖啡飲","茶飲", "氣泡飲"};
    String menucheck = "";
    //final EditText MInput = new EditText(this);

    String[] from = {"product","price"};
    int[] to = {R.id.product_name,R.id.product_prices};

    private LinkedList<HashMap<String,Object>> subMenuData;
    private LinkedList<HashMap<String,Object>> setMenuData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posApp = (PosApp)getApplication();
        cartNum = findViewById(R.id.cart_num);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



//        if(posApp.cart.size()>0){
//            cartNum.setText(""+posApp.cart.size());
//
//        }else{
//        cartNum.setText("");
//    }

        gridMenuHot = findViewById(R.id.menu_hot);
        gridSubMenu = findViewById(R.id.menu_sub);
        gridSetMenu = findViewById(R.id.menu_set);
        initSubMenu();
        initSetMenu();

        //Log.v("alex", "create");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Log.v("alex", ""+posApp.cart.size());
        if(posApp.cart.size()>0){
            cartNum.setText(""+posApp.cart.size());
            cartNum.setVisibility(View.VISIBLE);

        }else{
            cartNum.setText("");
            cartNum.setVisibility(View.INVISIBLE);
        }
        //Log.v("alex", "start");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.v("alex","resume");
    }

    private void initSubMenu(){
        subMenuData = new LinkedList<>();

        simpleAdapter = new SimpleAdapter(this,subMenuData,R.layout.product_sub,from,to);

        gridSubMenu.setAdapter(simpleAdapter);

        gridSubMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoSelect(view,position);
                Log.v("alex",id+"");

            }
        });
    }
    private void initSetMenu(){
        setMenuData = new LinkedList<>();

        simpleAdapter2 = new SimpleAdapter(this,setMenuData,R.layout.product_setting,from,to);

        gridSetMenu.setAdapter(simpleAdapter2);

        gridSetMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setProduct(view,position,setMenuData.get(position).get("product").toString());
                Log.v("alex", setMenuData.get(position).get("product").toString());

            }
        });


    }
    public void MenuSetting(View view){
        gridMenuHot.setVisibility(View.GONE);
        gridSubMenu.setVisibility(View.GONE);
        gridSetMenu.setVisibility(View.VISIBLE);
            posApp.cursorAll.moveToPosition(-1);


        for(int i= 0; i < menuSub.length; i++) {
            HashMap<String, Object> row = new HashMap<>();
            row.put(from[0], menuSub[i]);
            setMenuData.add(row);
        }

        while (posApp.cursorAll.moveToNext()){

            HashMap<String, Object> row = new HashMap<>();
            row.put(from[0], posApp.cursorAll.getString(0));
            row.put(from[1], (posApp.cursorAll.getInt(1)*posApp.cursorAll.getInt(2))/100 +"元");
            setMenuData.add(row);
        }


        simpleAdapter.notifyDataSetChanged();


    }

    public void MenuFull(View view){
        gridMenuHot.setVisibility(View.GONE);
        gridSubMenu.setVisibility(View.VISIBLE);
        gridSetMenu.setVisibility(View.GONE);
        gridSubMenu.setNumColumns(2);
        subMenuData.clear();
        posApp.cursorAll.moveToPosition(-1);


        while (posApp.cursorAll.moveToNext()){

            HashMap<String, Object> row = new HashMap<>();
            row.put(from[0], posApp.cursorAll.getString(0));
            row.put(from[1],(posApp.cursorAll.getInt(1)*posApp.cursorAll.getInt(2))/100+"元");
            subMenuData.add(row);
        }
        menucheck="All";
        simpleAdapter.notifyDataSetChanged();

    }

    public void subMenuA(View view){
        gridMenuHot.setVisibility(View.GONE);
        gridSubMenu.setVisibility(View.VISIBLE);
        gridSetMenu.setVisibility(View.GONE);
        gridSubMenu.setNumColumns(2);
        subMenuData.clear();

            posApp.cursorA.moveToPosition(-1);


        while (posApp.cursorA.moveToNext()){

            HashMap<String, Object> row = new HashMap<>();
            row.put(from[0], posApp.cursorA.getString(0));
            row.put(from[1],(posApp.cursorA.getInt(1)*posApp.cursorA.getInt(2))/100+"元");
            subMenuData.add(row);
        }
        menucheck="A";
        simpleAdapter.notifyDataSetChanged();

    }

    public void subMenuB(View view){
        gridMenuHot.setVisibility(View.GONE);
        gridSubMenu.setVisibility(View.VISIBLE);
        gridSetMenu.setVisibility(View.GONE);
        gridSubMenu.setNumColumns(2);
        subMenuData.clear();


            posApp.cursorB.moveToPosition(-1);


        while (posApp.cursorB.moveToNext()){

            HashMap<String, Object> row = new HashMap<>();
            row.put(from[0], posApp.cursorB.getString(0));
            row.put(from[1],(posApp.cursorB.getInt(1)*posApp.cursorB.getInt(2))/100+"元");
            subMenuData.add(row);
        }
        menucheck="B";
        simpleAdapter.notifyDataSetChanged();
    }

    public void subMenuC(View view){
        gridMenuHot.setVisibility(View.GONE);
        gridSubMenu.setVisibility(View.VISIBLE);
        gridSetMenu.setVisibility(View.GONE);
        gridSubMenu.setNumColumns(2);

        subMenuData.clear();

            posApp.cursorC.moveToPosition(-1);

        while (posApp.cursorC.moveToNext()){

            HashMap<String, Object> row = new HashMap<>();
            row.put(from[0], posApp.cursorC.getString(0));
            row.put(from[1],(posApp.cursorC.getInt(1)*posApp.cursorC.getInt(2))/100+"元");
            subMenuData.add(row);
        }
        menucheck="C";
        simpleAdapter.notifyDataSetChanged();
    }

    public void gotoSelect(View view){
        Intent intent = new Intent(this, SelectionActivity.class);

        Button b = (Button)view;
        String id = b.getText().toString();



        intent.putExtra("id",id);

        startActivity(intent);

    }

    public void gotoSelect(View view, int position){
        Intent intent = new Intent(this, SelectionActivity.class);

        String id = "";
        if(menucheck=="A") {
            posApp.cursorA.moveToPosition(position);
            id = posApp.cursorA.getString(0);
        }else if (menucheck=="B"){
            posApp.cursorB.moveToPosition(position);
            id = posApp.cursorB.getString(0);
        }else if (menucheck=="C"){
            posApp.cursorC.moveToPosition(position);
            id = posApp.cursorC.getString(0);
        }else if (menucheck=="All"){
            posApp.cursorAll.moveToPosition(position);
            id = posApp.cursorAll.getString(0);
        }

            intent.putExtra("id",id);

        startActivity(intent);

    }

    public void onHot(View view){
        gridMenuHot.setVisibility(View.VISIBLE);
        gridSubMenu.setVisibility(View.GONE);

    }

    public void Member(View view) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this)
                        .setTitle("Enter Phone")
                        .setCancelable(false);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = input.getText().toString();
            }
        });
        builder.setNegativeButton("取消",null);
        memberLogin= builder.create();

        memberLogin.show();




    }

    public void gotoCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);

        startActivity(intent);


    }

    public void setProduct(View view, int position, String name){

     Intent intent = new Intent(this, SetActivity.class);

     intent.putExtra("position", position);
     intent.putExtra("name", name);

     startActivity(intent);




    }

    public void checkData(View view) {
        Intent intent = new Intent(this, DatacheckActivity.class);
        startActivity(intent);
    }


}
