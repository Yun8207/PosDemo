package alex.tw.posdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReceiptActivity extends AppCompatActivity {
    private TextView txtInfo;
    private TextView txtItem;
    private TextView txtQuan;
    private TextView txtPrice;
    private TextView txtTotal;
    private String pname;
    private int[] price;
    private int totalprice;
    private LinearLayout receipt;

    private PosApp posApp;

    SimpleDateFormat formatter;
    Date currentTime ;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        posApp = (PosApp)getApplication();

        formatter = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");

        currentTime = Calendar.getInstance().getTime();

        time = formatter.format(currentTime);


        Intent intent = getIntent();
        price = new int[50];
        price = intent.getIntArrayExtra("price");

        posApp = (PosApp)getApplication();

        txtInfo = findViewById(R.id.rec_info);
        txtItem = findViewById(R.id.rec_item);
        txtQuan = findViewById(R.id.rec_quan);
        txtPrice = findViewById(R.id.rec_price);
        txtTotal = findViewById(R.id.rec_total);

        receipt = findViewById(R.id.receipt);
        receipt.buildDrawingCache();
        totalprice = 0;

        txtInfo.setText("日期:"+time+"\n單號:00000012");
        txtItem.setText("");
        txtQuan.setText("");
        txtPrice.setText("");
        for(int i=0; i<posApp.cart.size(); i++){
            txtItem.append(posApp.cart.get(i).getName()+"\n");

            txtQuan.append("x"+posApp.cart.get(i).getQuantity()+"\n");

            txtPrice.append((posApp.cart.get(i).getQuantity()*price[i])+"\n");

            totalprice = totalprice + (posApp.cart.get(i).getQuantity()*price[i]);//unused
        }

        txtTotal.setText(totalprice+"");





    }

    public void confirmPurchase(View view) {
        ContentValues values = new ContentValues();
        for(int i=0; i<posApp.cart.size(); i++) {
            pname = posApp.cart.get(i).getName();
            values.put("pname", pname);
            values.put("date", time);
            values.put("price", price[i]);
            Cursor cursor = posApp.menuDb.query("product",new String[]{"discount"}, "pname = ?",new String[]{pname},null,null,null);
            cursor.moveToFirst();
            values.put("discount", cursor.getInt(0));
            values.put("quantity", posApp.cart.get(i).getQuantity());
            values.put("tprice", price[i]*posApp.cart.get(i).getQuantity());
            values.put("sugar", posApp.cart.get(i).getSugar());
            posApp.menuDb.insert("purchase", null, values);
            //totalprice=訂單總價
        }

       Cursor cursor =  posApp.menuDb.query("purchase",new String[]{"pname, date"}, null,null,null,null,null);
        cursor.moveToFirst();
        Log.v("alex",cursor.getString(0)+" : "+cursor.getString(1)+":       "+cursor.getCount()+"purchases");

        Intent intent = new Intent(this, MainActivity.class);
        posApp.cart.clear();
        startActivity(intent);

    }
}
