package info.androidhive.navigationdrawer.other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.fragment.HomeFragment;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder>{
    private List<Items> ItemList;
    //private List<Categories_Model> CategoryList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Items items= ItemList.get(position);
        holder.Items_Title.setText(items.getItem_Title());
        holder.Item_ImageView.setImageResource((items.getItem_Image()));
       /* Categories_Model categoriesModel = CategoryList.get(0);
        holder.Items_Title.setText(categoriesModel.getCategory_name());
        holder.Item_ImageView.setImageResource("#000000");*/
    }

    @Override
    public int getItemCount() {
        //return  MainActivity.category_lists.size();
        return ItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Items_Title;
        public ImageView Item_ImageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            Items_Title = (TextView) itemView.findViewById(R.id.title);
            //Item_ImageView = (ImageView) itemView.findViewById(R.id.items_image);


        }
    }
    public ItemsAdapter(List<Items> itemList){

        this.ItemList=itemList;
    }
   /* public ItemsAdapter(List<Categories_Model>  categoryList)
    {
        this.CategoryList=categoryList;
    }*/
}