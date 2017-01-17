package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.Model.ProductModel;
import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.DataHelper;
import info.androidhive.navigationdrawer.other.DataHelper_FavoriteList;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.VolleyHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishList extends Fragment {

    ImageView bck_img,frwd_img;
    TextView main_title;
    RecyclerView recyclerView;
    TextView startshoppingTextView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<ProductModel> dbData;
    RecyclerView.LayoutManager layoutManager;
    public ArrayList<ProductModel> FavoriteList;
    DataHelper_FavoriteList dataHelper_favoriteList;
    ProductModel productModel;
    int count;
    LinearLayout _cartListLinearLayout,_start_shoppping_fragment_layout;

    public MyWishList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        MainActivity.pageIndex=2;
        Productdescription.pagenumber=3;
        MainActivity.toolbar.setVisibility(View.GONE);
        bck_img = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);
        main_title = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        main_title.setText("My WishList");
        frwd_img.setVisibility(View.GONE);
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MainActivity.textView.setText("Home");
                fm.popBackStack();
            }
        });

        startshoppingTextView = (TextView)view.findViewById(R.id.startShopping_textview);
        recyclerView = (RecyclerView) view.findViewById(R.id.myfav_recycler_view);
        productModel = new ProductModel();
        _cartListLinearLayout=(LinearLayout)view.findViewById(R.id.cartListLinearLayout);
        _start_shoppping_fragment_layout=(LinearLayout)view.findViewById(R.id.start_shoppping_fragment_layout);
        layoutManager = new LinearLayoutManager(getContext());
        dbData = new ArrayList<>();
        FavoriteList = new ArrayList<>();
        dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
        FavoriteList = dataHelper_favoriteList.getDataFromDB();
        if(FavoriteList.isEmpty()){
            _cartListLinearLayout.setVisibility(View.GONE);
            _start_shoppping_fragment_layout.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(layoutManager);
        mAdapter  = new FavListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();

        startshoppingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.textView.setText("Home");
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

      /*  recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                MainActivity.toolbar.setVisibility(View.GONE);
                Productdescription productdescription = new Productdescription();
                Productdescription.product_index = position;
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, productdescription).addToBackStack("Category");
                ft.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
        return view;
    }

    public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.MyViewHolder>{

        private AdapterView.OnItemClickListener onItemClickListener;
        private LayoutInflater layoutInflater;
        private Context context;



        @Override
        public FavListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if(FavoriteList.isEmpty())
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start__shopping_fragment,parent,false);
            }
            else
            {
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist_itemrow,parent,false);
            }

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final FavListAdapter.MyViewHolder holder, final int position) {
            //holder.cartlist_addlayout.setVisibility(View.VISIBLE);
            holder.name_tv.setText(FavoriteList.get(position).getProd_name());
            holder.price_tv.setText(FavoriteList.get(position).getProd_price());
            holder.prod_image.setImageUrl(GlobalClass.URL+FavoriteList.get(position).getProd_image(), VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
            holder.cartlist_addlayout.setVisibility(View.GONE);
            holder.cartlist_itemrow_addbutton.setVisibility(View.GONE);

            holder.cartlist_textlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.toolbar.setVisibility(View.GONE);
                    Productdescription productdescription = new Productdescription();
                    Productdescription.product_index = position;
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame, productdescription).addToBackStack("Category");
                    ft.commit();
                }
            });

            holder.name_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.toolbar.setVisibility(View.GONE);
                    Productdescription productdescription = new Productdescription();
                    Productdescription.product_index = position;
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame, productdescription).addToBackStack("Category");
                    ft.commit();
                }
            });


           // holder.cartlist_itemrow_quantext.setText(FavoriteList.get(position).getProd_quantity());
            //count=Integer.parseInt(holder.cartlist_itemrow_quantext.getText().toString());

         /*   holder.cartlist_itemrow_addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cartlist_itemrow_addbutton.setVisibility(View.GONE);
                    holder.cartlist_addlayout.setVisibility(View.VISIBLE);
                    count++;
                    holder.cartlist_itemrow_quantext.setText(""+count);
                }
            });
*/
           /* holder.cartlist_itemrow_incbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                    dbData=dataHelper_favoriteList.getDataFromDB();
                    int cv=Integer.parseInt(holder.cartlist_itemrow_quantext.getText().toString());
                    cv++;
                    holder.cartlist_itemrow_quantext.setText(""+cv);

                    dbData=dataHelper_favoriteList.getDataFromDB();
                    FavoriteList=dbData;
                    productModel.setProd_id(FavoriteList.get(holder.getLayoutPosition()).getProd_id());
                    productModel.setProd_name(holder.name_tv.getText().toString());
                    productModel.setProd_quantity(holder.cartlist_itemrow_quantext.getText().toString());
                    productModel.setProd_price(holder.price_tv.getText().toString());


                    dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                    dbData=dataHelper_favoriteList.getDataFromDB();
                    int i,ii=0;
                    for (i = 0; i < dbData.size(); i++) {
                        Log.i("ProductModel ID","productmodel ID = "+productModel.getProd_id());
                        Log.i("DBdata ID","DBdata ID = "+dbData.get(i).getProd_id());
                        if (productModel.getProd_id().equals(dbData.get(i).getProd_id())) {
                            Log.i("ProductModel ID","YYYYYYYYYYYYYYYYYYYYYYYYYY"+productModel.getProd_id());
                            ii=1;
                            break;
                        }
                    }
                    if (ii==1) {
                        Log.i("AAAAAAAAAAAA","CCCCCCCCCCCCCCCCCCCC");
                        dataHelper_favoriteList.updateDetails(productModel.getProd_id(), productModel.getProd_quantity());
                        ii=0;
                    }
                    Log.i("BBBBBBBBBBBBBBB","CCCCCCCCCCCCCCCCCCCC");
                }
            });*/

           /* holder.cartlist_itemrow_decbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                    dbData=dataHelper_favoriteList.getDataFromDB();


                    int cv=Integer.parseInt(holder.cartlist_itemrow_quantext.getText().toString());
                    if(cv>0){
                        cv--;
                        holder.cartlist_itemrow_quantext.setText(""+cv);

                        Log.i("~~~~~~~~~######","~~~~~~~~~~~~~~~~"+cv);

                    }
                    dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                    dbData=dataHelper_favoriteList.getDataFromDB();
                    FavoriteList=dbData;
                    if(dbData.size()!= 0) {
                        productModel.setProd_id(FavoriteList.get(holder.getLayoutPosition()).getProd_id());
                        productModel.setProd_name(holder.name_tv.getText().toString());
                        productModel.setProd_quantity(holder.cartlist_itemrow_quantext.getText().toString());
                        productModel.setProd_price(holder.price_tv.getText().toString());
                    }

                    if(cv==0)
                    {
                        dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                        dbData=dataHelper_favoriteList.getDataFromDB();
                        Log.i("size of dbData","size of dbData="+dbData.size()+" and position="+holder.getAdapterPosition());
                        Log.i("size of array_cartlist","size of array_cartlist="+FavoriteList.size()+" and position="+holder.getLayoutPosition());

                        dataHelper_favoriteList.deleteData(dbData.get(holder.getAdapterPosition()).getProd_id());

                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        FavoriteList.clear();
                        dbData=dataHelper_favoriteList.getDataFromDB();
                        FavoriteList=dbData;

                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        Log.i("~~~~~~~~~@@@@@","~~~~~~~~~~~~~~~~"+cv);
                    }
                    dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                    dbData=dataHelper_favoriteList.getDataFromDB();
                    int i,ii=0;
                    for (i = 0; i < dbData.size(); i++) {
                        if (productModel.getProd_id().equals(dbData.get(i).getProd_id())) {
                            ii=1;
                            break;
                        }
                    }

                    if (ii==1) {
                        Log.i("AAAAAAAAAAAA","CCCCCCCCCCCCCCCCCCCC");
                        dataHelper_favoriteList.updateDetails(productModel.getProd_id(), productModel.getProd_quantity());
                        ii=0;
                    }
                    if(dbData.size()==0)
                    {
                        _cartListLinearLayout.setVisibility(View.GONE);
                        _start_shoppping_fragment_layout.setVisibility(View.VISIBLE);
                    }
                }

            });*/


            holder.cartlist_itemrow_delbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataHelper_favoriteList = new DataHelper_FavoriteList(getContext());
                    dbData=dataHelper_favoriteList.getDataFromDB();
                    Log.i("size of dbData","size of dbData="+dbData.size()+" and position="+holder.getAdapterPosition());
                    Log.i("size of array_cartlist","size of array_cartlist="+FavoriteList.size()+" and position="+holder.getLayoutPosition());


                    dataHelper_favoriteList.deleteData(dbData.get(holder.getAdapterPosition()).getProd_id());

                    Toast.makeText(getActivity(), " after deleting product from cart list ", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    FavoriteList.clear();
                    dbData=dataHelper_favoriteList.getDataFromDB();
                    FavoriteList=dbData;
                    recyclerView.setAdapter(mAdapter);

                    mAdapter.notifyDataSetChanged();

                    if(dbData.size()==0)
                    {
                        _cartListLinearLayout.setVisibility(View.GONE);
                        _start_shoppping_fragment_layout.setVisibility(View.VISIBLE);
                    }

                    MainActivity.cart_textView.setText(""+dbData.size());
                }
            });


            // MainActivity.cart_textView.setText(arrayList_cartproducts.size());
        }

        @Override
        public int getItemCount() {

            return FavoriteList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            private FavListAdapter parent;
            private CardView cardView;
            public TextView name_tv,price_tv,quantity_tv;
            public NetworkImageView prod_image;
            public  LinearLayout cartlist_addlayout,cartlist_textlayout;
            public TextView cartlist_itemrow_quantext;
            public Button cartlist_itemrow_addbutton,cartlist_itemrow_incbutton,cartlist_itemrow_decbutton,cartlist_itemrow_delbutton;

            public MyViewHolder(View itemView) {
                super(itemView);
                cartlist_addlayout= (LinearLayout) itemView.findViewById(R.id.cartlistrow_addlayout);
                cartlist_itemrow_addbutton = (Button) itemView.findViewById(R.id.cartlistrow_addbutton);
                cartlist_textlayout = (LinearLayout) itemView.findViewById(R.id.cartlist_item_textlayout);
                //cartlist_itemrow_incbutton = (Button) itemView.findViewById(R.id.cartlistrow_incbutton);
                //cartlist_itemrow_decbutton = (Button) itemView.findViewById(R.id.cartlistrow_decbutton);
                cartlist_itemrow_delbutton = (Button)itemView.findViewById(R.id.cartlistrow_delbutton);
                //cartlist_itemrow_quantext = (TextView) itemView.findViewById(R.id.cartlistrow_counttext);
                name_tv = (TextView) itemView.findViewById(R.id.cartlist_prod_name);
                price_tv = (TextView) itemView.findViewById(R.id.cartlist_prod_price);
                prod_image = (NetworkImageView) itemView.findViewById(R.id.cartlist_prod_img);

            }


        }
    }

}
