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
 * Created by aniket on 11-Oct-16.
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<String> data = Collections.emptyList();
    String[] values;
    Context context1;


    public CartListAdapter(Context context,String[] values2){
        values  =values2;
        context1 = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        private LayoutInflater inflater;
        List<String> data = Collections.emptyList();
        String[] values;
        Context context1;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.cartlist_text);
        }
    }

    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context1).inflate(R.layout.cartlist_layout,parent,false);

        CartListAdapter.ViewHolder viewHolder1 = new CartListAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(CartListAdapter.ViewHolder Vholder, int position) {

        Vholder.textView.setText(values[position]);

        Vholder.textView.setBackgroundColor(Color.CYAN);

        Vholder.textView.setTextColor(Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return values.length;
    }
}