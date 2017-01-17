package info.androidhive.navigationdrawer.other;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<SubDataObject> mDataset;
    private static MyClickListener myClickListener;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView itemname;
        TextView itemnumbers;
        NetworkImageView itemimage;

        public DataObjectHolder(View itemView) {
            super(itemView);
            itemname = (TextView) itemView.findViewById(R.id.category_itemname);
            itemnumbers = (TextView) itemView.findViewById(R.id.category_itemnumbers);
            itemimage = (NetworkImageView) itemView.findViewById(R.id.category_itemimage);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<SubDataObject> myDataset) {

        this.mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        SubDataObject dataObject = mDataset.get(position);
        holder.itemname.setText(dataObject.getmText1());
        holder.itemnumbers.setText(dataObject.getmText2());
       // holder.itemimage.setImageUrl(GlobalClass.URL+array_subcategory_list.get(position).get, VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
    }

    public void addItem(SubDataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
