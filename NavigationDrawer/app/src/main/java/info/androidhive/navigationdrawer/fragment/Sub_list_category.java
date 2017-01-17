package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.activity.SplashActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.OnBackPressedListener;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.RecyclerViewPositionHelper;
import info.androidhive.navigationdrawer.other.SubListRecyclerAdapter;
import info.androidhive.navigationdrawer.other.VolleyHelper;

public class Sub_list_category extends Fragment implements OnBackPressedListener{

    RecyclerView recyclerView;
    private  static  RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static RelativeLayout bottomLayout;
    public static LinearLayout layout_toolbar2;
    RecyclerView.Adapter recyclerView_Adapter;
    //Toolbar sublist_toolbar;
    Context context;
    FragmentManager fm;
    FragmentTransaction ft;
    ImageView search_img,back_btn;
    NetworkImageView sublist_image;
    int pastVisiblesItems, visibleItemCount, totalItemCount,firstVisibleItem;
    RelativeLayout cart_layout;
    public static String Category_Index;
    public  static ArrayList<Subcategories_Model> array_subcategory_list;
    public Handler mHandler;
    int nextrecord;
    RecyclerViewPositionHelper  mRecyclerViewHelper;
    Bitmap bitmap;

    int count=0;
    private boolean userScrolled = true;

    public Sub_list_category() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sub_list_category, container, false);
        MainActivity.pageIndex=2;
        Productdescription.pagenumber=1;
        array_subcategory_list = new ArrayList<>();
        nextrecord = 0;
        MainActivity.imgBack.setVisibility(View.VISIBLE);




        //sublist_toolbar = (Toolbar) v.findViewById(R.id.toolbar_2);
        context = getContext();
        fm = getFragmentManager();
        sublist_image = (NetworkImageView) v.findViewById(R.id.sublist_imageview);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view1);
        bottomLayout = (RelativeLayout) v.findViewById(R.id.loadItemsLayout_recyclerView);
        // Inflate the layout for this fragment
        recyclerViewLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        loadItems();

       /* recyclerView_Adapter = new SubListRecyclerAdapter(context,numbers);

        recyclerView.setAdapter(recyclerView_Adapter);
*/
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),recyclerView,new RecyclerTouchListener.ClickListener(){

            @Override
            public void onClick(View view, int position) {
               // Bundle b = new Bundle();
                Productdescription.index = position;
                //Productdescription.numbers_pd = numbers;
               // String text = (String) getText(position);
               // b.putString("Name",text);
                Productdescription productdescription = new Productdescription();
                //productdescription.setArguments(b);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame,productdescription).addToBackStack("");
                ft.commit();

            }
            @Override
            public void onLongClick(View view, int position) {

            }

        }));


*/
        MainActivity.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, new HomeFragment(), "");
                ft.commit();


                if (MainActivity.pageIndex == 2) {
                    while (MainActivity.pageIndex == 1) {

                        fm.popBackStack();
                        ft.replace(R.id.frame, new HomeFragment(), "");
                        ft.commit();
                    }
                    MainActivity.textView.setText("Home");
                    MainActivity.Drawer.setDrawerListener(MainActivity.mDrawerToggle);
                    MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);



               /* else
                {
                    fm.popBackStack();
                }*/
                    if (MainActivity.Drawer.isDrawerOpen(GravityCompat.START)) {
                        MainActivity.Drawer.closeDrawers();
                        return;
                    }
                }
            }
        });
        return v;
    }

    void loadItems()
    {
        bottomLayout.setVisibility(View.GONE);
        if (GlobalClass.isNetworkAvailable(getContext())) {
            String url = GlobalClass.URL + "product_list.php?category_id=" + Category_Index + "&next_records=" + nextrecord;
            final StringRequest stringRequest_category = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.i("@@@@@@@@@","@@@@@@@@@@@@@@ response="+response);
                        if(response.trim().equals("No products are available")){
                            bottomLayout.setVisibility(View.GONE);
                        }
                        else {

                            JSONObject json = new JSONObject(response);

                            //MainActivity.category_lists.clear();
                            JSONArray result = null;
                            JSONObject jsonObject = json;
                            try {
                                result = jsonObject.getJSONArray("product_list");
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject subcategory_data = result.getJSONObject(i);
                                    Subcategories_Model subcategories_model;
                                    subcategories_model = new Subcategories_Model();
                                    subcategories_model.setCategory_id(subcategory_data.getString("category_id"));
                                    subcategories_model.setSubCategory_id(subcategory_data.getString("product_id"));
                                    subcategories_model.setSubCategory_name(subcategory_data.getString("product_name"));
                                    subcategories_model.setSubCategory_description(subcategory_data.getString("product_description"));
                                    subcategories_model.setSubCategory_image(subcategory_data.getString("product_image"));
                                    subcategories_model.setSubCategory_price(subcategory_data.getString("price"));
                                    array_subcategory_list.add(subcategories_model);
                                    Log.i("Sub_category _list", "Sub_category list1234 " + array_subcategory_list.get(i).getSubCategory_name());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            recyclerView_Adapter = new SubCategoryAdapter();
                            recyclerView.setAdapter(recyclerView_Adapter);
                            recyclerView_Adapter.notifyDataSetChanged();
                            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

                                @Override
                                public void onClick(View view, int position) {
                                    Subcategories_Model subcategories_model = array_subcategory_list.get(position);
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
                            }));

                            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        userScrolled = true;
                                    }
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);

                                    visibleItemCount = recyclerView.getChildCount();
                                    totalItemCount = mRecyclerViewHelper.getItemCount();
                                    firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

                                    count = mRecyclerViewHelper.findLastCompletelyVisibleItemPosition();
                                    pastVisiblesItems = mRecyclerViewHelper.findFirstVisibleItemPosition();

                                    //Toast.makeText(getContext(), "Pastvisibleitems " + pastVisiblesItems, Toast.LENGTH_SHORT).show();
                                    Log.i("#####", "####################@@@@@@@@@@@@@@@@@@ count=" + count + " totalItemCount=" + totalItemCount);

                                    if (userScrolled && count == totalItemCount) {
                                        userScrolled = false;

                                        bottomLayout.setVisibility(View.VISIBLE);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadmoreitems();
                                            }
                                        },1000);
                                 recyclerView_Adapter.notifyDataSetChanged();
                                    }
                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            Log.i("String Request", "" + stringRequest_category);

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest_category);
            Log.i("Strin Request2", "" + stringRequest_category);
        } else {
            Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadmoreitems() {

        bottomLayout.setVisibility(View.VISIBLE);
        nextrecord++;
        loadItems();
        bottomLayout.setVisibility(View.GONE);
        recyclerView_Adapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {

    }

    public class SubCategoryAdapter extends RecyclerView.Adapter<Sub_list_category.SubCategoryAdapter.MyViewHolder> {

        String src;
        Subcategories_Model SubcategoriesModel;
        private List<Subcategories_Model> SubcategoryList;



        @Override
        public Sub_list_category.SubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sublist_recycler_items,parent,false);
            return new Sub_list_category.SubCategoryAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            SubcategoriesModel = array_subcategory_list.get(position);
            holder.Items_Title.setText(SubcategoriesModel.getSubCategory_name());
            holder.img.setImageUrl(GlobalClass.URL+array_subcategory_list.get(position).getSubCategory_image(), VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
            holder.Items_price.setText(SubcategoriesModel.getSubCategory_price());
        }



        @Override
        public int getItemCount() {

            return array_subcategory_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView Items_Title;
            public NetworkImageView img;
            public TextView Items_price;
            public MyViewHolder(View view) {
                super(view);
                Items_Title = (TextView) itemView.findViewById(R.id.sub_list_recycler_items_name);
                img = (NetworkImageView) itemView.findViewById(R.id.sub_list_recycler_items_image);
                Items_price = (TextView) itemView.findViewById(R.id.sub_list_recycler_items_price);
            }
        }

        public SubCategoryAdapter()
        {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.toolbar.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(false);
    }
}
