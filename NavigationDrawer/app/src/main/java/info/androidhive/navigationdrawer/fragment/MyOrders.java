package info.androidhive.navigationdrawer.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.Model.MyOrders_Model;
import info.androidhive.navigationdrawer.Model.Order_Status_Model;
import info.androidhive.navigationdrawer.Model.Subcategories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.JSONParser;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment {
    ListView orders_list;
    ImageView bck_img,frwd_img;
    TextView main_title;
    public Dialog dialog;

    int nextRecordValue = 0;

    boolean flag_loading = false;

    static String stauts;

    static String get_order_id, get_firstname, get_email, get_mobile,
            get_payment_address, get_payment_city, get_payment_area,
            get_payment_method, get_ordered_products_count, get_order_status;

    public static ArrayList<MyOrders_Model> My_Orders_Details = new ArrayList<MyOrders_Model>();

    public static ArrayList<Order_Status_Model> My_Orders_Status = new ArrayList<Order_Status_Model>();

    Typeface tf;
    SharedPreferences.Editor editor1,editor2;
    SharedPreferences sharedPreferences_signup,sharedPreferences_login;
    private static String SHARED_PREF_NAME = "";

    public static String sp_cust_id;

    Button start_shopping;
    LinearLayout lin_back;

    LinearLayout lin1, lin_list;



    public MyOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        ;
        MainActivity.pageIndex = 2;
        MainActivity.toolbar.setVisibility(View.GONE);
        bck_img = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);
        main_title = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        main_title.setText("My Orders");
        frwd_img.setVisibility(View.GONE);
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MainActivity.textView.setText("Home");
                fm.popBackStack();
            }
        });

        orders_list = (ListView) view.findViewById(R.id.orders_list);

        lin_list = (LinearLayout) view.findViewById(R.id.lin_list);
        lin1 = (LinearLayout) view.findViewById(R.id.lin1);

        start_shopping = (Button) view.findViewById(R.id.start_shopping);


        sharedPreferences_signup = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        sharedPreferences_login = getActivity().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);
        String sp_cust_id_signup = sharedPreferences_signup.getString("customer_id", null);
        String sp_cust_id_login = sharedPreferences_login.getString("Customer_id", null);

        if (sp_cust_id_login == null) {
            sp_cust_id = sp_cust_id_signup;
        } else {
            sp_cust_id = sp_cust_id_login;
        }


        editor1 = sharedPreferences_signup.edit();

        editor2 = sharedPreferences_login.edit();


        My_Orders_Details.clear();

        My_Orders_Status.clear();

        start_shopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        // ***Checking the internet connection is there or not **********//
        if (GlobalClass.isNetworkAvailable(getContext())) {

            new Loading_OrdersStatus().execute("");

			/*
			 * // Creating a button - Load More btnLoadMore = new
			 * Button(Orders.this); btnLoadMore.setText("Load More");
			 *
			 * // Adding button to listview at footer
			 *
			 * orders_list.addFooterView(btnLoadMore);
			 */

        } else {

            GlobalClass.AlertMethod(getContext(), GlobalClass.check_internet);

        }

	/*	orders_list.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (firstVisibleItem + visibleItemCount == totalItemCount
						&& totalItemCount != 0) {
					if (flag_loading == false) {

						dialog.dismiss();
						flag_loading = true;
						// additems();
						nextRecordValue++;
						new Loading_OrdersDetails().execute("");
					}
				}
			}
		});*/


        orders_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = orders_list.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (orders_list.getLastVisiblePosition() >= count
                            - threshold) {

						/*
						 * int index = listview.getFirstVisiblePosition();
						 * // do your update stuff
						 * listview.smoothScrollToPosition(index);
						 */

                        // Execute LoadMoreDataTask AsyncTask
                        nextRecordValue++;
                        //	loadItems();
                        new Loading_OrdersDetails().execute("");

						/*
						 * // Save ListView state Parcelable state =
						 * listview.onSaveInstanceState();
						 *
						 *
						 *
						 * // Restore previous state (including selected
						 * item index and scroll position)
						 * listview.onRestoreInstanceState(state);
						 */

                    }/*
					 * else{ // listview.removeFooterView(rowView);
					 *
					 * }
					 */

                    //((BaseActivity) getActivity()).hideProgress();

                    dialog.dismiss();
					/*flag_loading = true;
					// additems();
					nextRecordValue++;
					new Loading_OrdersDetails().execute("");*/

                } else {

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


        if (GlobalClass.isNetworkAvailable(getContext())) {

            orders_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    // TODO Auto-generated method stub

                    get_order_id = My_Orders_Details.get(position)
                            .getOrder_id();

                    get_firstname = My_Orders_Details.get(position)
                            .getFirstname();

                    get_payment_address = My_Orders_Details.get(position)
                            .getShipping_address();

                    get_payment_city = My_Orders_Details.get(position)
                            .getShipping_city();

                    get_payment_area = My_Orders_Details.get(position)
                            .getShipping_area();

                    get_mobile = My_Orders_Details.get(position)
                            .getShipping_contact_number();

                    get_payment_method = My_Orders_Details.get(position)
                            .getPayment_method();

                    get_ordered_products_count = My_Orders_Details
                            .get(position).getOrdered_products_count();
                    get_order_status = stauts;

                    for (int i = 0; i < My_Orders_Status.size(); i++) {

                        if (My_Orders_Details
                                .get(position)
                                .getOrder_status_id()
                                .equals(My_Orders_Status.get(i)
                                        .getOrder_status_id())) {
                            get_order_status = (My_Orders_Status.get(i)
                                    .getOrder_status_name());

                        }
                    }

                  FragmentManager fm = getFragmentManager();
                    Orders_Details orders_details = new Orders_Details();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame,orders_details).addToBackStack("Hpme Fragment");
                    ft.commit();

                }
            });
        } else {
            GlobalClass.AlertMethod(getContext(), GlobalClass.check_internet);
        }

       // myOrders();

        return view;
    }

    private class Loading_OrdersDetails extends
            AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

			/*
			 * dialog = GlobalClass.showSpinner(Orders.this, "Wait...");
			 * dialog.show();
			 */

        }

        @Override
        protected String doInBackground(String... params) {
            getProductDetails();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

            try {

                if (My_Orders_Details.size() >= 1) {
                    lin_list.setVisibility(View.VISIBLE);

                    lin1.setVisibility(View.GONE);

                } else {

                    lin1.setVisibility(View.VISIBLE);

                    lin_list.setVisibility(View.GONE);

                }




                Parcelable state = orders_list.onSaveInstanceState();

                // *** Setting the orders details to listview ********//
                orders_list.setAdapter(new Adapter_Values());

                orders_list.onRestoreInstanceState(state);

            } catch (Exception e) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.cart_login);

                // set the custom dialog components - text, image and
                // button
                final TextView txt_message = (TextView) dialog
                        .findViewById(R.id.txt_message);

                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

                btn_ok.setVisibility(View.VISIBLE);

                txt_message.setText(GlobalClass.check_internet);

                // if button is clicked, close the custom dialog
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    }
                });

                dialog.show();

            }
        }

    }

    public void getProductDetails() {

        // sub_cat_name.clear();

        // flag_loading=false;

        JSONArray contacts = null;
        JSONParser jParser = new JSONParser();

        // http://indianleafsol.com/provision_cart_app//customer_orders_list.php?customer_id=3&next_records=0

        JSONObject json = jParser.getJSONFromUrl(GlobalClass.URL
                + "customer_order_list.php?customer_id=" + sp_cust_id
                + "&next_records=" + nextRecordValue);

        try {
            contacts = json.getJSONArray("customer_orders_details");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String input = JSONParser.json.replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                String output = "No orders are available".replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                if (input.equals(output)) {

                    Log.i("equal", "equal");
                    flag_loading =true;

                } else {

                    String datafromserver = JSONParser.json;

                    Log.i("dsfdsf", "Rsponse" + datafromserver);

                    MyOrders_Model myorders_model;
                    myorders_model = new MyOrders_Model();

                    myorders_model.setOrder_id(c.getString("order_id"));

                    myorders_model.setFirstname(c.getString("firstname"));
                    myorders_model.setLastname(c.getString("lastname"));
                    myorders_model.setEmail(c.getString("email"));
                    myorders_model.setMobile(c.getString("mobile"));

                    myorders_model.setPayment_address(c
                            .getString("payment_address"));
                    myorders_model.setPayment_city(c.getString("payment_city"));
                    myorders_model.setPayment_pincode(c
                            .getString("payment_pincode"));
                    myorders_model.setPayment_area(c.getString("payment_area"));
                    myorders_model.setPayment_method(c
                            .getString("payment_method"));

                    myorders_model.setShipping_firstname(c
                            .getString("shipping_firstname"));
                    myorders_model.setShipping_lastname(c
                            .getString("shipping_lastname"));
                    myorders_model.setShipping_contact_number(c
                            .getString("shipping_contact_number"));
                    myorders_model.setShipping_address(c
                            .getString("shipping_address"));
                    myorders_model.setShipping_city(c
                            .getString("shipping_city"));
                    myorders_model.setShipping_pincode(c
                            .getString("shipping_pincode"));
                    myorders_model.setShipping_area(c
                            .getString("shipping_area"));

                    myorders_model.setOrder_status_id(c
                            .getString("order_status_id"));

                    myorders_model.setDelivery_slot(c
                            .getString("delivery_slot"));
                    myorders_model.setOrdered_products_count(c
                            .getString("ordered_products_count"));

                    My_Orders_Details.add(myorders_model);

                    Log.i("22222222222222222222","My order details(order history)"+My_Orders_Details.get(i).getOrder_id());

                    flag_loading =false;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class Adapter_Values extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return My_Orders_Details.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View rowView, ViewGroup parent) {
            final ViewHolder holder;
            if (rowView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.orders_text, parent, false);

                holder.slot_date = (TextView) rowView
                        .findViewById(R.id.txt_date);

                holder.status = (TextView) rowView
                        .findViewById(R.id.txt_status);

                holder.items = (TextView) rowView.findViewById(R.id.txt_items);

                rowView.setTag(holder);
            } else {

                holder = (ViewHolder) rowView.getTag();
            }

            String str_date = My_Orders_Details.get(position)
                    .getDelivery_slot();

            if (str_date == null) {

                holder.slot_date.setText(" ");

            } else {

                // holder.slot_date.setText(delivery_slot.get(position));

                holder.slot_date.setText(My_Orders_Details.get(position)
                        .getDelivery_slot());

            }

            for (int i = 0; i < My_Orders_Status.size(); i++) {

                Log.i("My Order Detais id"
                                + My_Orders_Details.get(position).getOrder_status_id(),
                        "Status Id"
                                + My_Orders_Status.get(i).getOrder_status_id());

                if (My_Orders_Details.get(position).getOrder_status_id()
                        .equals(My_Orders_Status.get(i).getOrder_status_id())) {
                    holder.status.setText(My_Orders_Status.get(i)
                            .getOrder_status_name());

                    stauts = (My_Orders_Status.get(i).getOrder_status_name());

                }
            }

            holder.items.setText(My_Orders_Details.get(position)
                    .getOrdered_products_count() + " Items");

            //tf = Typeface.createFromAsset(getAssets(), "verdana.ttf");

            holder.slot_date.setTypeface(tf);

            holder.status.setTypeface(tf);

            holder.items.setTypeface(tf);

            return rowView;

        }

    }

    private class ViewHolder {
        TextView slot_date;
        TextView status;
        TextView items;
        TextView price;

    }
    private class Loading_OrdersStatus extends
            AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = GlobalClass.showSpinner(getContext(), "Wait...");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            getOrderStatusDetails();

            // loadingMore = true;

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dialog.dismiss();

            try {

                new Loading_OrdersDetails().execute("");

            } catch (Exception e) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.cart_login);

                // set the custom dialog components - text, image and
                // button
                final TextView txt_message = (TextView) dialog
                        .findViewById(R.id.txt_message);

                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

                btn_ok.setVisibility(View.VISIBLE);

                txt_message.setText(GlobalClass.check_internet);

                // if button is clicked, close the custom dialog
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    }
                });

                dialog.show();
            }

        }
    }

    // **** get the order status values and stores into model ****//
    public void getOrderStatusDetails() {

		/*
		 * JSONArray contacts = null; JSONParser jParser = new JSONParser();
		 *
		 * // http://indianleafsol.com/provision_cart_app/order_status_list.php
		 *
		 * JSONObject json = jParser.getJSONFromUrl(GlobalClass.Url +
		 * "order_status_list.php");
		 *
		 * try { contacts = json.getJSONArray("order_status_list"); for (int i =
		 * 0; i < contacts.length(); i++) { JSONObject c =
		 * contacts.getJSONObject(i);
		 *
		 * String datafromserver = JSONParser.json;
		 *
		 * Order_Status_Model order_status; order_status = new
		 * Order_Status_Model();
		 *
		 * order_status.setOrder_status_id(c.getString("order_status_id"));
		 * order_status.setOrder_status_name(c .getString("order_status_name"));
		 *
		 * My_Orders_Status.add(order_status); }
		 *
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

        JSONArray contacts = null;

        JSONParser jparser = new JSONParser();

        JSONObject json = jparser.getJSONFromUrl(GlobalClass.URL
                + "order_status_list.php");

        try {

            contacts = json.getJSONArray("order_status_list");

            for (int i = 0; i < contacts.length(); i++) {

                JSONObject c = contacts.getJSONObject(i);

                String datafromserver = JSONParser.json;

                Order_Status_Model order_status;
                order_status = new Order_Status_Model();

                order_status.setOrder_status_id(c.getString("order_status_id"));

                order_status.setOrder_status_name(c
                        .getString("order_status_name"));

                My_Orders_Status.add(order_status);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
