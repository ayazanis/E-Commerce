package info.androidhive.navigationdrawer.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.Model.My_Orders_Total_Details;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Orders_Details extends Fragment {
    Button btn_summary, btn_items;

    LinearLayout lin1, lin2;

    TextView txt_name, txt_address, txt_mobile,main_title;

    ListView list_items;

    ImageView img_back,frwd_img;

    public Dialog dialog;

    int nextRecordValue = 0;

    TextView finaltotal, deliverycharges, subtotal, order_items, payment_by;

    TextView txt_paymentby, txt_order_items, txt_sub_total,
            txt_delivery_charges, txt_final_total, txt_order_status;

    public static ArrayList<Order_Product_Details_Model> My_Order_Product_Details = new ArrayList<Order_Product_Details_Model>();

    public static ArrayList<My_Orders_Total_Details> My_Order_Total_cost_Details = new ArrayList<My_Orders_Total_Details>();

    Typeface tf;

    LinearLayout lin_back;

    boolean flag_loading = false;



    public Orders_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders__details, container, false);

        btn_summary = (Button) view.findViewById(R.id.btn_summary);

        btn_items = (Button) view.findViewById(R.id.btn_items);

        img_back = (ImageView) view.findViewById(R.id.toolbar_3_back_image);

        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);

        main_title = (TextView) view.findViewById(R.id.toolbar_3_main_title);

        finaltotal = (TextView) view.findViewById(R.id.finaltotal);

        deliverycharges = (TextView) view.findViewById(R.id.deliverycharges);

        subtotal = (TextView) view.findViewById(R.id.subtotal);

        order_items = (TextView) view.findViewById(R.id.order_items);

        payment_by = (TextView) view.findViewById(R.id.payment_by);



        lin1 = (LinearLayout) view.findViewById(R.id.lin1);

        lin2 = (LinearLayout) view.findViewById(R.id.lin2);
        txt_order_status = (TextView) view.findViewById(R.id.txt_order_status);

        txt_name = (TextView) view.findViewById(R.id.txt_name);

        txt_address = (TextView) view.findViewById(R.id.txt_address);

        txt_mobile = (TextView) view.findViewById(R.id.txt_mobile);

        txt_paymentby = (TextView) view.findViewById(R.id.txt_paymentby);

        txt_order_items = (TextView) view.findViewById(R.id.txt_order_items);

        txt_sub_total = (TextView) view.findViewById(R.id.txt_sub_total);

        txt_delivery_charges = (TextView) view.findViewById(R.id.txt_delivery_charges);

        txt_final_total = (TextView) view.findViewById(R.id.txt_final_total);

        list_items = (ListView) view.findViewById(R.id.list_items);

        // **** Setting the verdana typeface to all variables *****//

        txt_name.setTypeface(tf);

        txt_address.setTypeface(tf);

        txt_mobile.setTypeface(tf);

        txt_paymentby.setTypeface(tf);

        txt_order_items.setTypeface(tf);

        txt_sub_total.setTypeface(tf);

        txt_delivery_charges.setTypeface(tf);

        txt_final_total.setTypeface(tf);

        finaltotal.setTypeface(tf);
        deliverycharges.setTypeface(tf);
        subtotal.setTypeface(tf);

        order_items.setTypeface(tf);
        payment_by.setTypeface(tf);

        txt_order_status.setTypeface(tf);

        frwd_img.setVisibility(View.GONE);

        main_title.setText("Order Details");

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        btn_summary.setBackgroundColor(Color.parseColor("#E94F57"));

        btn_items.setBackgroundColor(Color.parseColor("#D8D8D8"));


        btn_summary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                lin1.setVisibility(View.VISIBLE);

                lin2.setVisibility(View.GONE);

                btn_summary.setBackgroundColor(Color.parseColor("#E94F57"));

                btn_items.setBackgroundColor(Color.parseColor("#D8D8D8"));

            }
        });
        // ***** Showing of Items in listview*****//
        btn_items.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                lin1.setVisibility(View.GONE);

                lin2.setVisibility(View.VISIBLE);

                btn_items.setBackgroundColor(Color.parseColor("#E94F57"));

                btn_summary.setBackgroundColor(Color.parseColor("#D8D8D8"));

            }
        });

        // ***** Setting the values from orders page to this in summary
        txt_name.setText(MyOrders.get_firstname);
        txt_order_status.setText("OrderStatus:" + MyOrders.get_order_status);

        String addrees = MyOrders.get_payment_address + ","
                + MyOrders.get_payment_area + "," + MyOrders.get_payment_city;

        txt_address.setText(addrees);

        txt_mobile.setText(MyOrders.get_mobile);
        txt_paymentby.setText(MyOrders.get_payment_method);

        txt_order_items.setText(MyOrders.get_ordered_products_count + " Items");

		/*
		 * list_items.setOnScrollListener(new OnScrollListener() {
		 *
		 * public void onScrollStateChanged(AbsListView view, int scrollState) {
		 *
		 * }
		 *
		 * public void onScroll(AbsListView view, int firstVisibleItem, int
		 * visibleItemCount, int totalItemCount) {
		 *
		 * if (firstVisibleItem + visibleItemCount == totalItemCount &&
		 * totalItemCount != 0) { if (flag_loading == false) {
		 *
		 * dialog.dismiss(); flag_loading = true; // additems();
		 * nextRecordValue++; new Loading_OrdersProductDetails().execute(""); }
		 * } } });
		 */

        list_items.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // stub
                int threshold = 1;
                int count = list_items.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (list_items.getLastVisiblePosition() >= count
                            - threshold) {

                        //		dialog.dismiss();

                        // Execute LoadMoreDataTask AsyncTask
                        nextRecordValue++;
                        // loadItems();
                        new Loading_OrdersProductDetails().execute("");

                    }

                    // ((BaseActivity) getActivity()).hideProgress();

                    dialog.dismiss();
					/*flag_loading = true;
					// additems();
					nextRecordValue++;
					new Loading_OrdersProductDetails().execute("");*/

                } else {

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }

        });

        // ***Loading Order_Product_Details *****//

        if (GlobalClass.isNetworkAvailable(getContext())) {

            new Loading_OrdersProductDetails().execute("");
        } else {

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

        return view;
    }
    private class Loading_OrdersProductDetails extends
            AsyncTask<String, String, String> {

        // new Loading_ProductDetails().execute("");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = GlobalClass.showSpinner(getContext(), "Wait...");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            getProductDetails();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dialog.dismiss();
            try {
                // *** setting the Adapter values to listview ****//

				/*runOnUiThread(new Runnable() {
					public void run() {
						// adapter.notifyDataSetChanged();
						list_items.setAdapter(new Adapter_Values());

					}
				});*/

                Parcelable state = list_items.onSaveInstanceState();

                // *** Setting the orders details to listview ********//
                list_items.setAdapter(new Adapter_Values());

                list_items.onRestoreInstanceState(state);



                // **** Checking the Internet Connections ***//

                if (GlobalClass.isNetworkAvailable(getContext())) {

                    // *** Loading the order cost details ***//
                    new Loading_Orders_Total_Cost_Details().execute("");
                } else {

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

    // **** calling this method from aprx line 239 ****//

    public void getProductDetails() {

        JSONArray contacts = null;
        JSONParser jParser = new JSONParser();

        // http://provisioncart.com/provision_cart_app/customer_ordered_product_details.php?order_id=28&next_records=0

        JSONObject json = jParser.getJSONFromUrl(GlobalClass.URL
                + "customer_ordered_product_details.php?order_id="
                + MyOrders.get_order_id + "&next_records=" + nextRecordValue);

        Log.i("sdfdsfsd", "Next Record Value after url" + nextRecordValue);
        try {
            contacts = json.getJSONArray("customer_ordered_product_details");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String input = JSONParser.json.replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                String output = "No ordered product are available"
                        .replace(" ", "").replace("\n", "").replace("\t", "")
                        .toLowerCase();

                if (input.equals(output)) {

                    flag_loading = true;

                } else {

                    String datafromserver = JSONParser.json;

                    Log.i("dsfdsf", "Rsponse" + datafromserver);

                    Order_Product_Details_Model order_product_details;
                    order_product_details = new Order_Product_Details_Model();

                    // ** Setting the values to model class from url ***//

                    order_product_details.setProduct_name(c
                            .getString("product_name"));

                    order_product_details.setProduct_quantity(c
                            .getString("product_quantity"));

                    order_product_details.setProduct_price(c
                            .getString("product_price"));

                    order_product_details.setProduct_weight(c
                            .getString("product_weight"));

                    order_product_details.setUnit(c.getString("unit"));

                    order_product_details.setProduct_total(c
                            .getString("product_total"));
                    // **** Adding the values to Mian model ****//

                    My_Order_Product_Details.add(order_product_details);

                    flag_loading = false;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class Loading_Orders_Total_Cost_Details extends
            AsyncTask<String, String, String> {

        // new Loading_ProductDetails().execute("");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

			/*
			 * dialog = GlobalClass.showSpinner(Orders_Details.this, "Wait...");
			 * dialog.show();
			 */

        }

        @Override
        protected String doInBackground(String... params) {
            getcostDetails();

            // loadingMore = true;

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            // *** Setting the values to total ,subtotal, delivery charges
            // ****//

            try {

                for (int i = 0; i < My_Order_Total_cost_Details.size(); i++) {

                    if (My_Order_Total_cost_Details.get(i).getSort_order()
                            .equals("1"))

                    {
                        txt_sub_total
                                .setText(new StringBuffer()
                                        .append("Rs.")
                                        .append(Double
                                                .parseDouble(My_Order_Total_cost_Details
                                                        .get(i).getValue())));

                    } else if (My_Order_Total_cost_Details.get(i)
                            .getSort_order().equals("3")) {
                        txt_delivery_charges.setText(new StringBuffer().append(
                                "Rs.").append(
                                Double.parseDouble(My_Order_Total_cost_Details
                                        .get(i).getValue())));

                    } else if (My_Order_Total_cost_Details.get(i)
                            .getSort_order().equals("9")) {
                        txt_final_total
                                .setText(new StringBuffer()
                                        .append("Rs.")
                                        .append(Double
                                                .parseDouble(My_Order_Total_cost_Details
                                                        .get(i).getValue())));

                    }

                }
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

    // *** Calling this methos from aprx line 337 ****//

    public void getcostDetails() {

        JSONArray contacts = null;
        JSONParser jParser = new JSONParser();

        // http://indianleafsol.com/provision_cart_app/order_total_details.php?order_id=81

        JSONObject json = jParser.getJSONFromUrl(GlobalClass.URL
                + "order_total_details.php?order_id=" + MyOrders.get_order_id);

        Log.i("sdfdsfsd", "Next Record Value after url" + nextRecordValue);
        try {
            contacts = json.getJSONArray("order_total_details");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String input = JSONParser.json.replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                String output = "No ordered totals available".replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                if (input.equals(output)) {

                } else {

                    String datafromserver = JSONParser.json;

                    Log.i("dsfdsf", "Rsponse" + datafromserver);

                    My_Orders_Total_Details order_total_details;
                    order_total_details = new My_Orders_Total_Details();

                    // *** Storing the values from url to model class ****//

                    order_total_details.setOrder_total_id(c
                            .getString("order_total_id"));

                    order_total_details.setCode(c.getString("code"));

                    order_total_details.setValue(c.getString("value"));

                    order_total_details
                            .setSort_order(c.getString("sort_order"));

                    My_Order_Total_cost_Details.add(order_total_details);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // **** Setting the adapter values to listview *****//

    public class Adapter_Values extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return My_Order_Product_Details.size();

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
                rowView = inflater.inflate(R.layout.orders_items_details,
                        parent, false);

                holder.name = (TextView) rowView
                        .findViewById(R.id.txt_product_name);

                holder.weight = (TextView) rowView
                        .findViewById(R.id.txt_product_weight);

                holder.price = (TextView) rowView
                        .findViewById(R.id.txt_product_price);

                rowView.setTag(holder);
            } else {

                holder = (ViewHolder) rowView.getTag();
            }

            holder.name.setText(My_Order_Product_Details.get(position)
                    .getProduct_name());

            String unit_str = My_Order_Product_Details.get(position).getUnit();

            holder.weight.setText("Weight :"
                    + My_Order_Product_Details.get(position)
                    .getProduct_weight() + unit_str);

            String str_price = My_Order_Product_Details.get(position)
                    .getProduct_price();

            Double d = Double.parseDouble(str_price);

            int x = d.intValue();

            holder.price.setText("Total : " + "Rs." + x);

           // tf = Typeface.createFromAsset(getAssets(), "verdana.ttf");

            holder.name.setTypeface(tf);

            holder.weight.setTypeface(tf);

            holder.price.setTypeface(tf);

            return rowView;

        }

    }

    private class ViewHolder {

        TextView name;
        TextView weight;
        TextView price;

    }

    // **** Alert Method for Checking the internet Connections ******//
    public void AlertMethod() {

        AlertDialog.Builder builder = new AlertDialog.Builder(
               getContext());
        builder.setTitle("Check internet connection !");
        builder.setInverseBackgroundForced(true);
        builder.setCancelable(false);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

}

