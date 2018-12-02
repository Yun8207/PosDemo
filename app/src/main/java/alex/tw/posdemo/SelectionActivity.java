package alex.tw.posdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

public class SelectionActivity extends AppCompatActivity {
    private TextView textView;
    private String itemInfo;

    private EditText quan;

    private int size;
    private int sugar;
    private int temp;
    private String id;
    private int quantity;

    private String txtSize;
    private String txtSugar;
    private String txtTemp;

    private PosApp posApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        posApp = (PosApp)getApplication();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        quan = findViewById(R.id.quan);

        textView = findViewById(R.id.purchase_info);
        size = 1;
        temp = 2;
        sugar = 1;
        quantity = 1;

        txtSize = "M";
        txtTemp = "半冰";
        txtSugar = "半糖";

        itemInfo = id + " "+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(id + "\n"+txtSize+" "+txtTemp+" "+txtSugar);

        quan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    quantity = parseInt(quan.getText().toString());
                }catch(NumberFormatException e){
                    quantity = 0;
                }
                if (quantity<0){
                    quantity =0;
                }else if (quantity >100){
                    quantity = 100;
                    quan.setText(100+"");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void gotoMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        if(quantity>0) {
            posApp.cart.add(new DataModel(id, size, sugar, temp, quantity));
        }else{
            backtoMain(view);
        }


    }

    public void backtoMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void large(View view) {
        size = 0;
        txtSize="L";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void medium(View view) {
        size = 1;
        txtSize="M";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void small(View view) {
        size = 2;
        txtSize="S";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }







    public void hot(View view) {
        temp = 0;
        txtTemp = "熱";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);

    }

    public void fullIce(View view) {
        temp = 1;
        txtTemp = "全冰";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void halfIce(View view) {
        temp = 2;
        txtTemp = "半冰";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void fewIce(View view) {
        temp = 3;
        txtTemp = "少冰";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void noIce(View view) {
        temp = 4;
        txtTemp = "去冰";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }


    public void fullSugar(View view) {
        sugar = 0;
        txtSugar = "全糖";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void halfSugar(View view) {
        sugar = 1;
        txtSugar = "半糖";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void fewSugar(View view) {
        sugar = 2;
        txtSugar = "少糖";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void noSugar(View view) {
        sugar = 3;
        txtSugar = "無糖";
        itemInfo = id + "\n"+txtSize+" "+txtTemp+" "+txtSugar;
        textView.setText(itemInfo);
    }

    public void add(View view) {
        if(quantity < 100){

            quantity++;

            quan.setText(quantity + "");
        }
    }

    public void minus(View view) {
        if(quantity>0) {
            quantity--;
            quan.setText(quantity + "");
        }
    }
}
