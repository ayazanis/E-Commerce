package info.androidhive.navigationdrawer.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.NumberPicker;
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
import java.util.List;

import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.DataObject;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.MyRecyclerViewAdapter;
import info.androidhive.navigationdrawer.other.OnBackPressedListener;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.SubDataObject;
import info.androidhive.navigationdrawer.other.VolleyHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Category extends Fragment implements OnBackPressedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static  LinearLayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private static RelativeLayout rlayout;
    int nextrecord;
    private boolean userScrolled = true;
    private static RelativeLayout bottomLayout;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public Handler mHandler;

    int count=0;
    public static String Category_Index;

    public  static ArrayList<Subcategories_Model> array_subcategory_list;

    public Category() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        bottomLayout = (RelativeLayout) v.findViewById(R.id.loadItemsLayout_recyclerView);
        mHandler = new Handler();
        MainActivity.pageIndex = 2;
        array_subcategory_list = new ArrayList<>();
        nextrecord = 0;
        Toast.makeText(getContext(), "" + Category_Index, Toast.LENGTH_SHORT).show();
        rlayout = (RelativeLayout) v.findViewById(R.id.cart_icon);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadItems();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity.toolbar.setVisibility(View.VISIBLE);
    }


    void loadItems()
    {
        bottomLayout.setVisibility(View.GONE);
        if (GlobalClass.isNetworkAvailable(getContext())) {
            String url = GlobalClass.URL + "category_products_list.php?category_id=" + Category_Index + "&next_records=" + nextrecord;
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
                                result = jsonObject.getJSONArray("sub_category");
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject subcategory_data = result.getJSONObject(i);
                                    Subcategories_Model subcategories_model;
                                    subcategories_model = new Subcategories_Model();
                                    subcategories_model.setCategory_id(subcategory_data.getString("category_id"));
                                    subcategories_model.setSubCategory_id(subcategory_data.getString("sub_category_id"));
                                    subcategories_model.setSubCategory_name(subcategory_data.getString("sub_category_name"));
                                    subcategories_model.setSubCategory_description(subcategory_data.getString("sub_category_description"));
                                    subcategories_model.setSubCategory_image(subcategory_data.getString("sub_category_image"));
                                    array_subcategory_list.add(subcategories_model);
                                    Log.i("Sub_category _list", "Sub_category list1234 " + array_subcategory_list.get(i).getSubCategory_name());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mAdapter = new SubCategoryAdapter();
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {

                                @Override
                                public void onClick(View view, int position) {
                                    MainActivity.toolbar.setVisibility(View.GONE);
                                    Sub_list_category sub_list_category = new Sub_list_category();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.frame, sub_list_category).addToBackStack("Category");
                                    ft.commit();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));

                            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                                    visibleItemCount = mLayoutManager.getChildCount();
                                    totalItemCount = mLayoutManager.getItemCount();
                                    count = 1 + mLayoutManager.findLastCompletelyVisibleItemPosition();
                                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

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
                                        mAdapter.notifyDataSetChanged();
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
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {

        MainActivity.toolbar.setVisibility(View.VISIBLE);

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
        }
    }
//--------------------------------------------------------------------------------------------------------------
public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    String src;
    Subcategories_Model SubcategoriesModel;
    private List<Subcategories_Model> SubcategoryList;



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SubcategoriesModel = array_subcategory_list.get(position);
        holder.Items_Title.setText(SubcategoriesModel.getSubCategory_name());
        holder.img.setImageUrl(GlobalClass.URL+array_subcategory_list.get(position).getSubCategory_image(), VolleyHelper.getVolleyHelper(getActivity()).getImageLoader());
    }

    @Override
    public int getItemCount() {

        return array_subcategory_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView Items_Title;
        public NetworkImageView img;
        public MyViewHolder(View view) {
            super(view);
            Items_Title = (TextView) itemView.findViewById(R.id.category_itemname);
            img = (NetworkImageView) itemView.findViewById(R.id.category_itemimage);

        }
    }

    public SubCategoryAdapter()
    {

    }
}
}
