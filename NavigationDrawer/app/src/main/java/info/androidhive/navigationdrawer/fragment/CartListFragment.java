package info.androidhive.navigationdrawer.fragment;


import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.Model.ProductModel;
import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.CartListAdapter;
import info.androidhive.navigationdrawer.other.DataHelper;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.ItemsAdapter;
import info.androidhive.navigationdrawer.other.VolleyHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {

    Toolbar bottom_toolbar;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView checkout_btn,back_arrow;
    ArrayList<ProductModel> dbData;
    public  static  List<ProductModel> list;
    public static ArrayList<ProductModel> arrayList_cartproducts;
    ProductModel productModel;
    DataHelper dataHelper;
    SharedPreferences sharedPreferences;
    int count;
    TextView startshoppingTextView;
    LinearLayout _cartListLinearLayout,_start_shoppping_fragment_layout;
    public static float totalSum=0;
    TextView totalcost_txt;

    public CartListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.list_fragment, container, false);
        bottom_toolbar = (Toolbar) view.findViewById(R.id.cartlist_bottomtoolbar);
        totalcost_txt = (TextView) view.findViewById(R.id.cart_total_text);
        recyclerView = (RecyclerView) view.findViewById(R.id.cart_recycler_view);
        productModel = new ProductModel();
        _cartListLinearLayout=(LinearLayout)view.findViewById(R.id.cartListLinearLayout);
        _start_shoppping_fragment_layout=(LinearLayout)view.findViewById(R.id.start_shoppping_fragment_layout);
        layoutManager = new LinearLayoutManager(getContext());
        checkout_btn = (ImageView) view.findViewById(R.id.checkout_text);
        back_arrow = (ImageView)view.findViewById(R.id.toolbar_3_back_image);
        //backbuttonLinearLayout = (LinearLayout)view.findViewById(R.id.backbuttonlayout) ;
        startshoppingTextView = (TextView)view.findViewById(R.id.startShopping_textview);
        dbData = new ArrayList<>();
        arrayList_cartproducts = new ArrayList<>();
        dataHelper = new DataHelper(getContext());
        MainActivity.pageIndex=3;
        arrayList_cartproducts = dataHelper.getDataFromDB();

        if(arrayList_cartproducts.isEmpty()){
           _cartListLinearLayout.setVisibility(View.GONE);
            _start_shoppping_fragment_layout.setVisibility(View.VISIBLE);
            checkout_btn.setVisibility(View.GONE);
        }

        totalSum=0;
        for(int i=0;i<arrayList_cartproducts.size();i++){

                totalSum = totalSum+Float.valueOf(arrayList_cartproducts.get(i).getProd_price())*Float.valueOf(arrayList_cartproducts.get(i).getProd_quantity());
        }
        totalcost_txt.setText(""+totalSum);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter  = new CartListAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        back_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.imgBack.setVisibility(View.VISIBLE);
                        android.support.v4.app.FragmentManager fm =getFragmentManager();
                        fm.popBackStack();

            }
        });

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences_signup = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
                String name_signup = sharedPreferences_signup.getString("Firstname",null);

                SharedPreferences sharedPreferences_login = getActivity().getSharedPreferences("credentials_new",Context.MODE_PRIVATE);
                String name_login = sharedPreferences_login.getString("Firstname",null);
                if(name_signup!=null){
                    Toast.makeText(getContext(), "Not Null Name in signup", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new ShowAddress();
                    android.support.v4.app.FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame,fragment).addToBackStack("Login");
                    ft.commit();
                }
                else{
                    if(name_login!=null){
                        Toast.makeText(getContext(),"Not NULL Name in Login",Toast.LENGTH_SHORT).show();
                        Fragment fragment = new ShowAddress();
                        android.support.v4.app.FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame,fragment).addToBackStack("Login");
                        ft.commit();
                    }
                    else{
                        Toast.makeText(getContext(),"Login to continue",Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.login_alert_dialog);
                        TextView txt_msg = (TextView)dialog.findViewById(R.id.login_alert_text);
                        txt_msg.setText("Please Login to checkout");
                        Button btn_ok = (Button)dialog.findViewById(R.id.login_alert_btn_ok);
                        Button btn_cancel = (Button)dialog.findViewById(R.id.login_alert_btn_cancel);
                        dialog.show();

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Fragment fragment = new LoginPage();
                                android.support.v4.app.FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.frame,fragment);
                                ft.commit();
                            }
                        });

                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }
        });

        startshoppingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        return view;
    }

    public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{

        @Override
        public CartListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if(arrayList_cartproducts.isEmpty())
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
        public void onBindViewHolder(final CartListAdapter.MyViewHolder holder, final int position) {

            holder.cartlist_addlayout.setVisibility(View.VISIBLE);
            holder.name_tv.setText(arrayList_cartproducts.get(position).getProd_name());
            holder.price_tv.setText(arrayList_cartproducts.get(position).getProd_price());
            holder.prod_image.setImageUrl(GlobalClass.URL+arrayList_cartproducts.get(position).getProd_image(),VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
            holder.cartlist_itemrow_quantext.setText(arrayList_cartproducts.get(position).getProd_quantity());
            count=Integer.parseInt(holder.cartlist_itemrow_quantext.getText().toString());

            holder.cartlist_itemrow_addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cartlist_itemrow_addbutton.setVisibility(View.GONE);
                    holder.cartlist_addlayout.setVisibility(View.VISIBLE);
                    count++;
                    holder.cartlist_itemrow_quantext.setText(""+count);
                }
            });

            holder.cartlist_itemrow_incbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataHelper = new DataHelper(getContext());
                    dbData=dataHelper.getDataFromDB();
                    int cv=Integer.parseInt(holder.cartlist_itemrow_quantext.getText().toString());
                    cv++;
                    holder.cartlist_itemrow_quantext.setText(""+cv);

                    dbData=dataHelper.getDataFromDB();
                    arrayList_cartproducts=dbData;
                    productModel.setProd_id(arrayList_cartproducts.get(holder.getLayoutPosition()).getProd_id());
                    productModel.setProd_name(holder.name_tv.getText().toString());
                    productModel.setProd_quantity(holder.cartlist_itemrow_quantext.getText().toString());
                    productModel.setProd_price(holder.price_tv.getText().toString());
                    totalSum = totalSum+1*Float.valueOf(arrayList_cartproducts.get(position).getProd_price());
                    totalcost_txt.setText(""+totalSum);

                    dataHelper = new DataHelper(getContext());
                    dbData=dataHelper.getDataFromDB();
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
                        dataHelper.updateDetails(productModel.getProd_id(), productModel.getProd_quantity());
                        ii=0;
                    }
                    Log.i("BBBBBBBBBBBBBBB","CCCCCCCCCCCCCCCCCCCC");
                }
            });

            holder.cartlist_itemrow_decbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataHelper = new DataHelper(getContext());
                    dbData=dataHelper.getDataFromDB();

                    totalSum = totalSum-Float.valueOf(arrayList_cartproducts.get(position).getProd_price());
                    totalcost_txt.setText(""+totalSum);

                    int cv=Integer.parseInt(holder.cartlist_itemrow_quantext.getText().toString());
                    if(cv>0){
                        cv--;
                        holder.cartlist_itemrow_quantext.setText(""+cv);

                        Log.i("~~~~~~~~~######","~~~~~~~~~~~~~~~~"+cv);

                    }
                    dataHelper = new DataHelper(getContext());
                    dbData=dataHelper.getDataFromDB();
                    arrayList_cartproducts=dbData;
                    if(dbData.size()!= 0) {
                        productModel.setProd_id(arrayList_cartproducts.get(holder.getLayoutPosition()).getProd_id());
                        productModel.setProd_name(holder.name_tv.getText().toString());
                        productModel.setProd_quantity(holder.cartlist_itemrow_quantext.getText().toString());
                        productModel.setProd_price(holder.price_tv.getText().toString());
                    }

                    if(cv==0)
                    {
                        dataHelper = new DataHelper(getContext());
                        dbData=dataHelper.getDataFromDB();
                        Log.i("size of dbData","size of dbData="+dbData.size()+" and position="+holder.getAdapterPosition());
                        Log.i("size of array_cartlist","size of array_cartlist="+arrayList_cartproducts.size()+" and position="+holder.getLayoutPosition());

                        dataHelper.deleteData(dbData.get(holder.getAdapterPosition()).getProd_id());

                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        arrayList_cartproducts.clear();
                        dbData=dataHelper.getDataFromDB();
                        arrayList_cartproducts=dbData;

                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        Log.i("~~~~~~~~~@@@@@","~~~~~~~~~~~~~~~~"+cv);
                    }
                    dataHelper = new DataHelper(getContext());
                    dbData=dataHelper.getDataFromDB();
                    int i,ii=0;
                    for (i = 0; i < dbData.size(); i++) {
                        if (productModel.getProd_id().equals(dbData.get(i).getProd_id())) {
                            ii=1;
                            break;
                        }
                    }

                    if (ii==1) {
                        Log.i("AAAAAAAAAAAA","CCCCCCCCCCCCCCCCCCCC");
                        dataHelper.updateDetails(productModel.getProd_id(), productModel.getProd_quantity());
                        ii=0;
                    }
                    if(dbData.size()==0)
                    {
                        checkout_btn.setVisibility(View.GONE);
                        _cartListLinearLayout.setVisibility(View.GONE);
                        _start_shoppping_fragment_layout.setVisibility(View.VISIBLE);
                    }
                }

            });

            holder.cartlist_itemrow_delbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataHelper = new DataHelper(getContext());
                    dbData=dataHelper.getDataFromDB();
                    Log.i("size of dbData","size of dbData="+dbData.size()+" and position="+holder.getAdapterPosition());
                    Log.i("size of array_cartlist","size of array_cartlist="+arrayList_cartproducts.size()+" and position="+holder.getLayoutPosition());
                    totalSum = totalSum-(Float.valueOf(arrayList_cartproducts.get(position).getProd_quantity())*Float.valueOf(arrayList_cartproducts.get(position).getProd_price()));
                    totalcost_txt.setText(""+totalSum);

                    dataHelper.deleteData(dbData.get(holder.getAdapterPosition()).getProd_id());

                    Toast.makeText(getActivity(), " after deleting product from cart list ", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    arrayList_cartproducts.clear();
                    dbData=dataHelper.getDataFromDB();
                    arrayList_cartproducts=dbData;
                    recyclerView.setAdapter(mAdapter);

                    mAdapter.notifyDataSetChanged();

                    if(dbData.size()==0)
                    {
                        checkout_btn.setVisibility(View.GONE);
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

            return arrayList_cartproducts.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView name_tv,price_tv,quantity_tv;
            public NetworkImageView prod_image;
            public  LinearLayout cartlist_addlayout;
            public TextView cartlist_itemrow_quantext;
            public Button cartlist_itemrow_addbutton,cartlist_itemrow_incbutton,cartlist_itemrow_decbutton,cartlist_itemrow_delbutton;

            public MyViewHolder(View itemView) {
                super(itemView);
                cartlist_addlayout= (LinearLayout) itemView.findViewById(R.id.cartlistrow_addlayout);
                cartlist_itemrow_addbutton = (Button) itemView.findViewById(R.id.cartlistrow_addbutton);
                cartlist_itemrow_incbutton = (Button) itemView.findViewById(R.id.cartlistrow_incbutton);
                cartlist_itemrow_decbutton = (Button) itemView.findViewById(R.id.cartlistrow_decbutton);
                cartlist_itemrow_delbutton = (Button) itemView.findViewById(R.id.cartlistrow_delbutton);
                cartlist_itemrow_quantext = (TextView) itemView.findViewById(R.id.cartlistrow_counttext);
                name_tv = (TextView) itemView.findViewById(R.id.cartlist_prod_name);
                price_tv = (TextView) itemView.findViewById(R.id.cartlist_prod_price);
                prod_image = (NetworkImageView) itemView.findViewById(R.id.cartlist_prod_img);
            }
        }
        public CartListAdapter(){
        }
    }
}
