package info.androidhive.navigationdrawer.other;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.androidhive.navigationdrawer.R;


/**
 * Created by Admin on 29-09-2016.
 */
public class SubListRecyclerAdapter extends RecyclerView.Adapter<SubListRecyclerAdapter.ViewHolder>{

    private LayoutInflater inflater;
    List<String> data = Collections.emptyList();
    String[] values;
    Context context1;

    public SubListRecyclerAdapter(Context context2,String[] values2){
        values  =values2;
        context1 = context2;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.sub_list_recycler_items_name);
            imageView = (ImageView) v.findViewById(R.id.sub_list_recycler_items_image);
        }
    }

    @Override
    public SubListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context1).inflate(R.layout.sublist_recycler_items,parent,false);

        ViewHolder viewHolder1 = new ViewHolder(view1);

        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {

        Vholder.imageView.setBackgroundColor(Color.RED);
        Vholder.textView.setText(values[position]);

        Vholder.textView.setBackgroundColor(Color.CYAN);

        Vholder.textView.setTextColor(Color.BLUE);

    }

    @Override
    public int getItemCount() {

        return values.length;
    }
}
