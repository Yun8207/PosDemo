package alex.tw.posdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

public class SetActivity extends AppCompatActivity {
    private int position;
    private String name;
    private TextView settingName;
    private TextView price;
    private EditText discount;

    private PosApp posApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        posApp = (PosApp)getApplication();



        Intent intent = getIntent();

        settingName = findViewById(R.id.setting_name);
        price = findViewById(R.id.setting_price);
        discount = findViewById(R.id.dis_value);

        position = intent.getIntExtra("position", -1);
        name = intent.getStringExtra("name");

        settingName.setText(name);



        if(position >=3) {
            posApp.cursorAll.moveToPosition(position-3);
            price.setText(posApp.cursorAll.getInt(1)+"元");
            if (posApp.cursorAll.getInt(2)==100){
                discount.setText("");

            }else {
                discount.setText(posApp.cursorAll.getInt(2)+"");
            }
        }else if (position ==0){
            posApp.cursorA.moveToPosition(0);

            if(checkMenu(posApp.cursorA)){
                if (posApp.cursorA.getInt(2)==100) {
                    discount.setText("");
                }else{
                    discount.setText(posApp.cursorA.getInt(2) + "");
                }
            }



        }else if (position ==1){
            posApp.cursorB.moveToPosition(0);

            if(checkMenu(posApp.cursorB)){
                if (posApp.cursorB.getInt(2)==100) {
                    discount.setText("");
                }else{
                    discount.setText(posApp.cursorB.getInt(2) + "");
                }
            }



        }else if (position ==2){
            posApp.cursorC.moveToPosition(0);

            if(checkMenu(posApp.cursorC)){
                if (posApp.cursorC.getInt(2)==100) {
                    discount.setText("");
                }else{
                    discount.setText(posApp.cursorC.getInt(2) + "");
                }
            }



        }


    }

    public void gotoMain(View view){
        Intent intent = new Intent(this, MainActivity.class);

        String input;
        input = discount.getText().toString();

        if(input.matches("")) {
            input = "100";
        }
        if (position ==0){
            ContentValues cv = new ContentValues();
            cv.put("discount", parseInt(input));
            Log.v("alex", input);
            posApp.menuDb.update("product", cv, "cid = 'A' ",null );


        }else if (position ==1){
            ContentValues cv = new ContentValues();
            cv.put("discount", parseInt(input));
            Log.v("alex", input);
            posApp.menuDb.update("product", cv, "cid = 'B' ",null );


        }else if (position ==2){
            ContentValues cv = new ContentValues();
            cv.put("discount", parseInt(input));
            Log.v("alex", input);
            posApp.menuDb.update("product", cv, "cid = 'C' ",null );


        }else {


            ContentValues cv = new ContentValues();
            cv.put("discount", parseInt(input));
            Log.v("alex", input);
            posApp.menuDb.update("product", cv, "pname='" + name + "'", null);
        }


        posApp.cursorA = posApp.menuDb.query("product",new String[]{"pname", "price","discount"}, "cid = ?",new String[]{"A"},null,null,null);
        posApp.cursorB = posApp.menuDb.query("product",new String[]{"pname", "price","discount"}, "cid = ?",new String[]{"B"},null,null,null);
        posApp.cursorC = posApp.menuDb.query("product",new String[]{"pname", "price","discount"}, "cid = ?",new String[]{"C"},null,null,null);
        posApp.cursorHot = posApp.menuDb.query("product",new String[]{"pname", "price","discount"}, "discount != 100",null,null,null,null);
        posApp.cursorAll = posApp.menuDb.query("product",new String[]{"pname", "price","discount"}, null,null,null,null,null);




        startActivity(intent);

    }

    public void backtoMain(View view){
        onBackPressed();

    }

    public boolean checkMenu(Cursor cursor){
        int d1;
        int d2;
        boolean a = false;

        for( int i=1; i<cursor.getCount(); i++) {

            cursor.moveToPosition(i);
            d1 = cursor.getInt(2);
            cursor.moveToPosition(i-1);
            d2 = cursor.getInt(2);

            if( d1 == d2){
                a = true;
            }else{
                a = false;
                break;
            }

        }
        return a;
    }


}
