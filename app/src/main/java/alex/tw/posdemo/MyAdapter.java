package alex.tw.posdemo;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class MyAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayList<DataModel> dataSet;
    Context mContext;
    private float y1, y2;






    public MyAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.activity_cart_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView txtName;

        ViewFlipper flipSize;
        ViewFlipper flipSugar;
        ViewFlipper flipTemp;
        Button delete;
        EditText quantityTxt;

        Button addQuan;
        Button minusQuan;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;
//
//        switch (v.getId())
//        {
//            case R.id.cancel:
//                Toast.makeText(mContext, "bye", Toast.LENGTH_LONG)
//                        .show();
//                break;
//        }

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final DataModel dataModel = getItem(position);
        final int index = position;
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        final String quan;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_cart_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.flipSize = (ViewFlipper) convertView.findViewById(R.id.vf_size);
            viewHolder.flipSugar = (ViewFlipper) convertView.findViewById(R.id.vf_sugar);
            viewHolder.flipTemp = (ViewFlipper) convertView.findViewById(R.id.vf_temp);
            viewHolder.delete = (Button)convertView.findViewById(R.id.delete);
            viewHolder.quantityTxt = (EditText) convertView.findViewById(R.id.item_quan);

            viewHolder.minusQuan = (Button)convertView.findViewById(R.id.minus);
            viewHolder.addQuan = (Button)convertView.findViewById(R.id.plus);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        //lastPosition = position;

//        viewHolder.txtName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        viewHolder.txtName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DataModel dm = new DataModel(dataModel.name,dataModel.size,dataModel.sugar,dataModel.temp,dataModel.quantity);
                dataSet.add(dm);

                notifyDataSetChanged();
                return false;
            }
        });


        viewHolder.flipSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.flipSize.showNext();
                if(dataModel.size<2) {
                    dataModel.size++;
                }else{
                    dataModel.size = 0;
                }

            }
        });

        viewHolder.flipSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.flipSugar.showNext();
                if(dataModel.sugar<3) {
                    dataModel.sugar++;
                }else{
                    dataModel.sugar = 0;
                }

            }
        });

        viewHolder.flipTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.flipTemp.showNext();
                if(dataModel.temp<4) {
                    dataModel.temp++;
                }else{
                    dataModel.temp = 0;
                }

            }
        });


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.remove(index);
                notifyDataSetChanged();
            }
        });

        viewHolder.minusQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataModel.quantity>1) {
                    dataModel.quantity--;
                }
                viewHolder.quantityTxt.setText(""+dataModel.getQuantity());
                notifyDataSetChanged();
            }


        });

        viewHolder.addQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataModel.quantity<100){
                    dataModel.quantity++;
                }
                viewHolder.quantityTxt.setText(""+dataModel.getQuantity());
                notifyDataSetChanged();
            }
        });



        viewHolder.quantityTxt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                        try {
                            dataModel.quantity = parseInt(((EditText) v).getText().toString());
                            notifyDataSetChanged();
                        }catch (NumberFormatException e){
                            dataModel.quantity = 1;
                            notifyDataSetChanged();
                        }
                        if (dataModel.quantity >100){
                            dataModel.quantity = 100;
                            ((EditText) v).setText(100+"");
                        }
                }
            }
        });





        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.flipSize.setDisplayedChild(dataModel.getSize());
        viewHolder.flipSugar.setDisplayedChild(dataModel.getSugar());
        viewHolder.flipTemp.setDisplayedChild(dataModel.getTemp());
        viewHolder.quantityTxt.setText(""+dataModel.getQuantity());
        // Return the completed view to render on screen
        return convertView;


    }


}
