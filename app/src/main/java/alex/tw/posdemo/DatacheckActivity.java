package alex.tw.posdemo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DatacheckActivity extends AppCompatActivity {

    Spinner spinner;
    int choice;
    TextView text1;
    TextView text2;

    private PosApp posApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datacheck);
        choice = 0;

        posApp = (PosApp)getApplication();

        spinner = findViewById(R.id.spinner);
        text1 = findViewById(R.id.databoard_pname);
        text2 = findViewById(R.id.databoard_info);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.group_spinner, R.layout.spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("alex", position+"");
                choice = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void generateData(View view) {
        text1.setText("");
        text2.setText("");
        if (choice ==0){

            Cursor cursor =  posApp.menuDb.query("purchase",new String[]{"pname", "price","discount","quantity","tprice","sugar","date"}, null,null,null,null,null);
            while (cursor.moveToNext()) {
                String pname = cursor.getString(0);
                int price = cursor.getInt(1);
                int dis = cursor.getInt(2);
                float discount= dis/10;
                int quantity = cursor.getInt(3);
                int tprice = cursor.getInt(4);
                int s = cursor.getInt(5);
                String sugar="";
                if(s==0){
                    sugar = "全糖";
                }else if(s==1){
                    sugar = "半糖";
                }else if(s==2){
                    sugar = "少糖";
                }else if(s==3){
                    sugar = "無糖";
                }
                String date = cursor.getString(6);
                if(dis ==100){
                    text1.append(pname + "\n");
                    text2.append(price + " " + "元" +"           x" + quantity + "總共" + tprice + "元 " + sugar + " " + date + "\n");
                    Log.v("alex",price + " " + "元" +"           x" + quantity + "總共" + tprice + "元 " + sugar + " " + date + "\n");
                }else {
                    text1.append(pname + "\n");
                    text2.append(price + " " + "元 " + discount + "折 x" + quantity + "總共" + tprice + "元 " + sugar + " " + date + "\n");
                }
            }

        }else if(choice ==1){
            Cursor cursor = posApp.menuDb.rawQuery("select sum(tprice) from purchase ;",null);
            cursor.moveToFirst();
            int totalprice = cursor.getInt(0);
            text1.setText(totalprice+"元");

        }
    }
}
