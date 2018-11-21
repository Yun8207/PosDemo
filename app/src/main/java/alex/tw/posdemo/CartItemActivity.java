package alex.tw.posdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class CartItemActivity extends AppCompatActivity {
    private ViewFlipper vfSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item);

        vfSize = findViewById(R.id.vf_size);
        View size0 = vfSize.getChildAt(0);
        View size1 = vfSize.getChildAt(1);
        View size2 = vfSize.getChildAt(2);
        View size3 = vfSize.getChildAt(3);

        FlipperOnClick listener = new FlipperOnClick();
        size0.setOnClickListener(listener);
        size1.setOnClickListener(listener);
        size2.setOnClickListener(listener);
        size3.setOnClickListener(listener);


    }

    public void ClickHandler(View view) {
    }

    public void ClickHandlerSugar(View view) {
    }

    public void ClickHandlerSize(View view) {
    }

    public void ClickHandlerTemp(View view) {
    }

    private class FlipperOnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int index = vfSize.indexOfChild(v) ;
            vfSize.showNext();

        }
    }

}
