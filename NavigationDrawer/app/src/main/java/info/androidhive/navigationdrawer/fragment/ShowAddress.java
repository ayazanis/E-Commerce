package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
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
import java.util.List;

import info.androidhive.navigationdrawer.Model.AddressModelClass;
import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;
import info.androidhive.navigationdrawer.other.VolleyHelper;

public class ShowAddress extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static LinearLayoutManager mLayoutManager;
    private static String LOG_TAG = "AddressFragment";
    private static RelativeLayout rlayout;
    public Handler mHandler;
    Boolean isSelected;
    public static  AddressModelClass address_model;
    public AddressModelClass addressModelClass2;
    ImageView checkout_btn,back_arrow;
    public static String customer_id;
    SharedPreferences sharedPreferences_login,sharedPreferences_signup;
    Toolbar bottom_toolbar;
    TextView total_cost,title_txt;

    public  static ArrayList<AddressModelClass> array_addressList;
    public ArrayList<AddressModelClass> arrayList_addressModel;
    Button add_new_address;
    RecyclerView recyclerView;
    public ShowAddress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.show_address_fragment, container, false);
        bottom_toolbar = (Toolbar) v.findViewById(R.id.cartlist_bottomtoolbar);
        title_txt = (TextView) v.findViewById(R.id.toolbar_3_main_title);
        title_txt.setText("Address");
        total_cost = (TextView) v.findViewById(R.id.cart_total_text);
        isSelected=false;
        add_new_address  = (Button)v.findViewById(R.id.show_address_fragment_button);
        MainActivity.pageIndex=3;
        mHandler = new Handler();
        array_addressList = new ArrayList<>();
        rlayout = (RelativeLayout) v.findViewById(R.id.cart_icon);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.address_recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        checkout_btn = (ImageView)v.findViewById(R.id.checkout_text);
        back_arrow = (ImageView)v.findViewById(R.id.toolbar_3_back_image);
        addressModelClass2  =new AddressModelClass();
        arrayList_addressModel  =new ArrayList<>();

        sharedPreferences_signup = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        sharedPreferences_login = getActivity().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);

        if(sharedPreferences_signup.getString("Firstname",null)!=null){
            customer_id=sharedPreferences_signup.getString("customer_id",null);
        }
        else {
            if (sharedPreferences_login.getString("Firstname",null)!=null){
                customer_id=sharedPreferences_login.getString("Customer_id",null);
            }
        }
        Log.i("!!!!!!Cust_id!!!","!!!!!Cust_id!!!"+customer_id);

        total_cost.setText(""+CartListFragment.totalSum);
        array_addressList.clear();
        loadAddress();


        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FinalFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.commit();
            }
        });

        add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Address_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment).addToBackStack("address_fragment ");
                fragmentTransaction.commit();
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.imgBack.setVisibility(View.VISIBLE);
                android.support.v4.app.FragmentManager fm =getFragmentManager();
                fm.popBackStack();

            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("++++++++++++++","++++++++++ onResume +++++++++++++++");
    }

    private void loadAddress() {
            if (GlobalClass.isNetworkAvailable(getContext())) {
                String url = GlobalClass.URL + "all_customer_address.php?customer_id="+customer_id;
                final StringRequest stringRequest_category = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("@@@@@@@@@","@@@@@@@@@@@@@@ response="+response);
                            if(response.trim().equals("customer address detail are not available")){
                            }
                            else {

                                JSONObject json = new JSONObject(response);

                                //MainActivity.category_lists.clear();
                                JSONArray result = null;
                                JSONObject jsonObject = json;
                                try {
                                    result = jsonObject.getJSONArray("customer_address_details");
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject address_data = result.getJSONObject(i);
                                       /* Subcategories_Model subcategories_model;
                                        subcategories_model = new Subcategories_Model();*/

                                        AddressModelClass addressModelClass  = new AddressModelClass();
                                        addressModelClass.setFirstname(address_data.getString("firstname"));
                                        addressModelClass.setLastname(address_data.getString("lastname"));
                                        addressModelClass.setArea(address_data.getString("area"));
                                        addressModelClass.setCity(address_data.getString("city"));
                                        addressModelClass.setPincode(address_data.getString("pincode"));
                                        addressModelClass.setAddress(address_data.getString("address"));
                                        addressModelClass.setContact_number(address_data.getString("contact_number"));
                                        addressModelClass.setCustomer_id(address_data.getString("customer_id"));
                                        addressModelClass.setAddress_id(address_data.getString("address_id"));

                                        array_addressList.add(addressModelClass);



                                       /* subcategories_model.setCategory_id(subcategory_data.getString("category_id"));
                                        subcategories_model.setSubCategory_id(subcategory_data.getString("sub_category_id"));
                                        subcategories_model.setSubCategory_name(subcategory_data.getString("sub_category_name"));
                                        subcategories_model.setSubCategory_description(subcategory_data.getString("sub_category_description"));
                                        subcategories_model.setSubCategory_image(subcategory_data.getString("sub_category_image"));
                                        array_addressList.add(subcategories_model);*/
                                        Log.i("Sub_category _list", "Sub_category list1234 " + array_addressList.get(i).getAddress());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                mAdapter = new ShowAddress.AddressAdapter();
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {

                                    @Override
                                    public void onClick(View view, int position) {
                                        CardView cardView;
                                        cardView = (CardView) view.findViewById(R.id.card_view);
                                        Log.i("@@@@@@@@@","@@@@@@@@@@@@@@ add on click listener arraylist_addressList"+array_addressList.size());
                                         /*for(int i=0;i<array_addressList.size();i++) {
                                             addressModelClass.setAddress(array_addressList.get(position).getAddress());
                                             addressModelClass.setAddress_id(array_addressList.get(position).getAddress_id());
                                             addressModelClass.setContact_number(array_addressList.get(position).getContact_number());
                                             addressModelClass.setFirstname(array_addressList.get(position).getFirstname());
                                             addressModelClass.setLastname(array_addressList.get(position).getLastname());
                                             addressModelClass.setCustomer_id(array_addressList.get(position).getCustomer_id());
                                             addressModelClass.setArea(array_addressList.get(position).getArea());
                                             addressModelClass.setCity(array_addressList.get(position).getCity());
                                             addressModelClass.setPincode(array_addressList.get(position).getPincode());
                                             addressModelClass.setState(array_addressList.get(position).getState());

                                             //arrayList_addressModel.add(addressModelClass);
                                         }*/

                                        AddressModelClass addressModelClass1  = array_addressList.get(position);
                                        addressModelClass2.setAddress(addressModelClass1.getAddress());
                                        addressModelClass2.setAddress_id(addressModelClass1.getAddress_id());
                                        addressModelClass2.setContact_number(addressModelClass1.getContact_number());
                                        addressModelClass2.setFirstname(addressModelClass1.getFirstname());
                                        addressModelClass2.setLastname(addressModelClass1.getLastname());
                                        addressModelClass2.setCustomer_id(addressModelClass1.getCustomer_id());
                                        addressModelClass2.setArea(addressModelClass1.getArea());
                                        addressModelClass2.setCity(addressModelClass1.getCity());
                                        addressModelClass2.setPincode(addressModelClass1.getPincode());


                                        FinalFragment.addressModelClass = addressModelClass2;

                                        Log.i("+++++address+++","address = "+FinalFragment.addressModelClass.getAddress());
                                        Log.i("+++++name++++","name = "+FinalFragment.addressModelClass.getFirstname());
                                        Log.i("++++contact+++","address = "+FinalFragment.addressModelClass.getContact_number());





                                       // -----------------------------------------------------------





                                        /*for(int i=0;i<array_addressList.size();i++) {
                                            if (i == position) {
                                                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                                isSelected= false;
                                            } else
                                            {
                                                cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                                                isSelected = true;
                                            }

                                        }*/


                                           /* if(i==position)
                                            {
                                                Log.i("@@@@@@@@@","@@@@@@@@@@@@@@ add on click listener if block position"+position);
                                                if(isSelected)
                                                {
                                                    cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                                    isSelected=false;
                                                }
                                                else
                                                {
                                                    cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                                                    isSelected=true;
                                                }
                                            }
                                            else
                                            {
                                                Log.i("@@@@@@@@@","@@@@@@@@@@@@@@ add on click else part listener position"+position);
                                                cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                                            }*/

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

    public class AddressAdapter extends RecyclerView.Adapter<ShowAddress.AddressAdapter.MyViewHolder> {

        String src;
        int mSelectedpos=0;
        MyViewHolder mholder;
        AddressModelClass addressModelClass;

        @Override
        public ShowAddress.AddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_row_xml,parent,false);
            return new  ShowAddress.AddressAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            addressModelClass = array_addressList.get(position);

            holder.fname_txt.setText(addressModelClass.getFirstname());
            holder.lname_txt.setText(addressModelClass.getLastname());
            holder.address_text.setText(addressModelClass.getAddress());
            holder.area_text.setText(addressModelClass.getArea());
            holder.pincode_text.setText(addressModelClass.getPincode());
            holder.city_text.setText(addressModelClass.getCity());
            holder.contact_txt.setText(addressModelClass.getContact_number());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mholder = new MyViewHolder(v);
                    if(position!=mSelectedpos && mholder!=null) {
                        mholder.cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                        Log.i("selected address","selected address = "+array_addressList.get(position).getAddress());
                    }
                    mSelectedpos=position;
                    notifyDataSetChanged();
                }
            });
            if(position==mSelectedpos){
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                address_model  = new AddressModelClass();
                address_model.setFirstname(array_addressList.get(position).getFirstname());
                address_model.setLastname(array_addressList.get(position).getLastname());
                address_model.setCustomer_id(array_addressList.get(position).getCustomer_id());
                address_model.setAddress_id(array_addressList.get(position).getAddress_id());
                address_model.setContact_number(array_addressList.get(position).getContact_number());
                address_model.setAddress(array_addressList.get(position).getAddress());
                address_model.setCity(array_addressList.get(position).getCity());
                address_model.setArea(array_addressList.get(position).getArea());
                address_model.setPincode(array_addressList.get(position).getPincode());


                FinalFragment.addressModelClass=address_model;


                FinalFragment.shipping_fname = array_addressList.get(position).getFirstname();
                FinalFragment.shipping_lname = array_addressList.get(position).getLastname();
                FinalFragment.shipping_address = array_addressList.get(position).getAddress();
                FinalFragment.shipping_address_id = array_addressList.get(position).getAddress_id();
                FinalFragment.shipping_contact_no = array_addressList.get(position).getContact_number();
                FinalFragment.customer_id = array_addressList.get(position).getCustomer_id();
                FinalFragment.shipping_area = array_addressList.get(position).getArea();
                FinalFragment.shipping_city = array_addressList.get(position).getCity();
                FinalFragment.shipping_pincode = array_addressList.get(position).getPincode();

                Log.i("^^^^^^^address^^^^","address = "+FinalFragment.shipping_address);
                Log.i("^^^^^^^fname^^^^","fname = "+FinalFragment.shipping_fname);
                Log.i("^^^^^^^lname^^^^","lname = "+FinalFragment.shipping_lname);

            }
            else
            {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.cardview_light_background));
            }
        }

        @Override
        public int getItemCount() {

            return array_addressList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public CardView cardView;
            public TextView address_text,area_text,pincode_text,city_text,fname_txt,lname_txt,contact_txt;

            public MyViewHolder(View view) {
                super(view);
                cardView = (CardView)itemView.findViewById(R.id.address_row_card_view);
                address_text = (TextView) itemView.findViewById(R.id.shipping_address_textview);
                area_text=(TextView)itemView.findViewById(R.id.shipping_area_textview);
                pincode_text=(TextView)itemView.findViewById(R.id.shipping_pincode_textview);
                city_text=(TextView)itemView.findViewById(R.id.shipping_city_textview);
                fname_txt = (TextView) itemView.findViewById(R.id.shipping_fname);
                lname_txt = (TextView) itemView.findViewById(R.id.shipping_lname);
                contact_txt = (TextView) itemView.findViewById(R.id.shipping_contact);
            }
        }
    }

}
