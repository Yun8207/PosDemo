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
    TextView textView;

    private PosApp posApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datacheck);

        posApp = (PosApp)getApplication();

        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.databoard);

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
        textView.setText("");
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
                    textView.append(pname + " " + price + " " + "元" +"           x" + quantity + "總共" + tprice + "元 " + sugar + " " + date + "\n");
                }else {
                    textView.append(pname + " " + price + " " + "元 " + discount + "折 x" + quantity + "總共" + tprice + "元 " + sugar + " " + date + "\n");
                }
            }

        }else if(choice ==1){
            Cursor cursor = posApp.menuDb.rawQuery("select sum(tprice) from purchase ;",null);
            cursor.moveToFirst();
            int totalprice = cursor.getInt(0);
            textView.setText(totalprice+"元");

        }
    }
}
