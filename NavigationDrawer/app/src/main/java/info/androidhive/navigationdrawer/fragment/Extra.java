package info.androidhive.navigationdrawer.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.Model.AddressModelClass;
import info.androidhive.navigationdrawer.Model.Shipping_Charges_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Extra extends Fragment {

    LinearLayout itemscount;
    String payment_method;
    ImageView back_btn, checkout_btn;
    boolean ischecked_onlinepayment, ischecked_paybycash, ischecked;
    TextView title_txt, totalamount, noofitems, checkout_txt,delivery_txt,shipping_txt;
    RadioButton paybycash, onlinepayment;
    public static AddressModelClass addressModelClass;
    public ArrayList<Shipping_Charges_Model> ShippingChargesModel = new ArrayList<Shipping_Charges_Model>();
    Toolbar bottom_toolbar;
    private static final String ADD_TO_CART = GlobalClass.URL + "add_to_cart.php?";
    public static String KEY_CUST_ID = "customer_id";
    public static String KEY_PROD_ID = "product_id";
    public static String KEY_QUANTITY = "quantity";
    public static String email,shipping_fname, shipping_lname, shipping_contact_no, shipping_address, shipping_address_id,customer_id, shipping_area, shipping_city, shipping_pincode;
    public static String payment_fname, payment_lname,payment_contact_no,payment_address,payment_address_id,payment_area,payment_city,payment_pincode;
    int i;
    public static String Order_id;
    String DatafromServer,dataFromServer_product,str_ship_amount,dataFromServer;
    SharedPreferences sharedPreference_login,sharedPreference_signup;
    Dialog dialog_spinner;
    Double ship_charges,ship_amount,cart_total;
    String ship_string;
    String str_total = "";
    Double total = 0.00;

    public Extra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final, container, false);

        totalamount = (TextView) view.findViewById(R.id.finalfrag_totalAmount);
        itemscount = (LinearLayout) view.findViewById(R.id.final_frag_no_of_items_layout);
        delivery_txt = (TextView) view.findViewById(R.id.finalfrag_deliverytxt);
        shipping_txt = (TextView) view.findViewById(R.id.finalfrag_shipping_txt);
        noofitems = (TextView) view.findViewById(R.id.final_frag_no_of_items_text);
        checkout_txt = (TextView) view.findViewById(R.id.finalfrag_checkout);
        paybycash = (RadioButton) view.findViewById(R.id.paybycash);
        checkout_btn = (ImageView) view.findViewById(R.id.checkout_text);
        onlinepayment = (RadioButton) view.findViewById(R.id.onlinepayment);
        ischecked = false;
        ischecked_onlinepayment = false;
        MainActivity.pageIndex = 3;
        ischecked_paybycash = false;
        back_btn = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        title_txt = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        ship_string = ""+CartListFragment.totalSum;
        ship_charges = Double.parseDouble(ship_string);
        sharedPreference_login = getActivity().getSharedPreferences("credentials_new",Context.MODE_PRIVATE);
        sharedPreference_signup = getActivity().getSharedPreferences("credentials",Context.MODE_PRIVATE);

        if(sharedPreference_login.getString("Firstname",null)!=null){
            email = sharedPreference_login.getString("email",null);
            payment_fname = sharedPreference_login.getString("Firstname",null);
            payment_lname = sharedPreference_login.getString("Lastname",null);
            payment_contact_no = sharedPreference_login.getString("Mobile",null);
            payment_address = ShowAddress.array_addressList.get(0).getPayment_address();
            payment_address_id =  ShowAddress.array_addressList.get(0).getPayment_address_id();
            payment_area =  ShowAddress.array_addressList.get(0).getPayment_area();
            payment_city =  ShowAddress.array_addressList.get(0).getPayment_city();
            payment_pincode =  ShowAddress.array_addressList.get(0).getPayment_pincode();
        }
        else{
            email = sharedPreference_signup.getString("email",null);
            payment_fname = sharedPreference_signup.getString("firstname",null);
            payment_lname = sharedPreference_signup.getString("lastname",null);
            payment_contact_no = sharedPreference_signup.getString("contact_number",null);
            payment_address = sharedPreference_signup.getString("address",null);
            payment_address_id = sharedPreference_signup.getString("address_id", null);
            payment_area = sharedPreference_signup.getString("area",null);
            payment_city = sharedPreference_signup.getString("city",null);
            payment_pincode = sharedPreference_signup.getString("pincode",null);
        }
        title_txt.setText("Order Summary");


        Log.i("+++++address+++++", "address = " + shipping_address);
        Log.i("+++++fname++++", "fname = " + shipping_fname);
        Log.i("+++++lname++++", "lname = " + shipping_lname);



        itemscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_list_of_items);

                TextView txt_standard = (TextView) dialog
                        .findViewById(R.id.txt_standard);

                TextView txt_items_count = (TextView) dialog
                        .findViewById(R.id.txt_items_count);
                ListView lv = (ListView) dialog.findViewById(R.id.lv);
                TextView txt_dismiss = (TextView) dialog
                        .findViewById(R.id.dismiss);

                TextView txt_conitue = (TextView) dialog
                        .findViewById(R.id.continue_shopping);


                txt_items_count.setText(CartListFragment.arrayList_cartproducts.size() + " Items");

                lv.setAdapter(new DialogAdapter());

                dialog.show();


                txt_conitue.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        Fragment fragment = new HomeFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame, fragment).addToBackStack("FinalFragment");
                        ft.commit();
                    }
                });


                txt_dismiss.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();


                    }
                });

            }
        });

        noofitems.setText("" + CartListFragment.arrayList_cartproducts.size());

        totalamount.setText("" + CartListFragment.totalSum);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.imgBack.setVisibility(View.VISIBLE);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                checkout_btn.setVisibility(View.VISIBLE);
                fm.popBackStack();
            }
        });

        paybycash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischecked = true;
                paybycash.setChecked(true);
                onlinepayment.setChecked(false);
                ischecked_paybycash = false;
                payment_method = "cod";
            }
        });

        onlinepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischecked = false;
                onlinepayment.setChecked(true);
                paybycash.setChecked(false);
                ischecked_onlinepayment = false;
                payment_method = "online payment";

            }
        });

        checkout_btn.setVisibility(View.GONE);

        checkout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_finalconf);
                dialog.show();

                TextView noofprod = (TextView) dialog.findViewById(R.id.no_of_items);
                TextView totalprice = (TextView) dialog.findViewById(R.id.total_price);
                TextView paymentmethod = (TextView) dialog.findViewById(R.id.payment_method);
                TextView deliveryaddress = (TextView) dialog.findViewById(R.id.delivery_address);
                TextView deliverytime = (TextView) dialog.findViewById(R.id.delivery_time);
                Button cancel_btn = (Button) dialog.findViewById(R.id.btn_cancel);
                Button cnfm_btn = (Button) dialog.findViewById(R.id.btn_confirm);

                noofprod.setText("" + CartListFragment.arrayList_cartproducts.size());
                totalprice.setText("" + CartListFragment.totalSum);
                deliveryaddress.setText(shipping_address + "," + shipping_area + "," + shipping_city + "," + shipping_pincode);
                if (ischecked) {
                    paymentmethod.setText("Online payment");
                } else {
                    paymentmethod.setText("Pay By Cash");
                }
                deliverytime.setText("");
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                cnfm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        try {
                            if (GlobalClass.isNetworkAvailable(getContext())) {

                                CartAsy cartAsy = new CartAsy();
                                cartAsy.execute("");
                            } else {
                                Toast.makeText(getContext(), "Network not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //addtocart();


                        Toast.makeText(getContext(), "Cart orders sent", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        if(GlobalClass.isNetworkAvailable(getContext())){
            new Shipping_Charges().execute("");
        }

        return view;
    }

    public class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return CartListFragment.arrayList_cartproducts.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }
        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            final ViewHolder holder1;
            Float singleproductprice;

            if(rowView == null){
                holder1 = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.dialog_noofitems_row,parent,false);

                holder1.prod_name = (TextView) rowView.findViewById(R.id.list_child_name);
                holder1.prod_quantity = (TextView) rowView.findViewById(R.id.list_child_weight);
                holder1.prod_price = (TextView) rowView.findViewById(R.id.list_child_cost);
                rowView.setTag(holder1);
            }
            else{
                holder1 = (ViewHolder) rowView.getTag();
            }
            try{
                holder1.prod_name.setText(CartListFragment.arrayList_cartproducts.get(position).getProd_name());
                holder1.prod_quantity.setText(CartListFragment.arrayList_cartproducts.get(position).getProd_quantity()+"*"
                        +CartListFragment.arrayList_cartproducts.get(position).getProd_price());
                singleproductprice = ((Float.valueOf(CartListFragment.arrayList_cartproducts.get(position).getProd_quantity()))*
                        (Float.valueOf(CartListFragment.arrayList_cartproducts.get(position).getProd_price())));
                Log.i("singleproductprice","singleproductprice"+singleproductprice);
                holder1.prod_price.setText(""+singleproductprice);

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return rowView;
        }
        private class ViewHolder{
            TextView prod_name;
            TextView prod_quantity;
            TextView prod_price;
        }
    }

    private class Shipping_Charges extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            getShippingCharges();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (ShippingChargesModel.size() == 0) {

                // custom dialog
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
                    }
                });

                dialog.show();

            }

            else {

                for (int i = 0; i < ShippingChargesModel.size(); i++) {

                    Double str_total_ship_amount = 0.0;

                    str_total_ship_amount = ShippingChargesModel.get(i)
                            .getTotal_amount();

                    if (ship_charges <= ShippingChargesModel.get(i)
                            .getTotal_amount()) {

                        delivery_txt.setText(ShippingChargesModel
                                .get(i).getShipping_charge());

                        String sub = delivery_txt.getText()
                                .toString();

                        ship_amount = Double.parseDouble(sub);

                        total = cart_total + ship_amount;

                        DecimalFormat df = new DecimalFormat("0.00");

                        df.setMaximumFractionDigits(2);

                        str_ship_amount = df.format(ship_amount);
                        str_total = df.format(total);

                        totalamount.setText((str_total));

                        shipping_txt.setText("Total amount >Rs. "
                                + str_total_ship_amount
                                + ",No shipping charges");

                    } else {

                        delivery_txt.setText("0.00");

                        String sub = delivery_txt.getText()
                                .toString();

                        ship_amount = Double.parseDouble(sub);


                        total = cart_total + ship_amount;

                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);

                        str_ship_amount = df.format(ship_amount);
                        str_total = df.format(total);

                        totalamount.setText((str_total));

                        shipping_txt.setText("Total amount >Rs. "
                                + str_total_ship_amount
                                + ",No shipping charges");

                    }

                }

                // new Promo_Code_details().execute("");

            }


            // custom dialog
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

                }
            });

            dialog.show();
        }
    }

    public void getShippingCharges() {

        JSONArray contacts = null;
        JSONParser jParser = new JSONParser();

// http://indianleafsol.com/Provisioncart/provision_cart_app/shipping_charges_details.php
        JSONObject json = jParser.getJSONFromUrl(GlobalClass.URL
                + "shipping_charges_details.php");

        try {
            contacts = json.getJSONArray("shipping_charges_details");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                Shipping_Charges_Model shipping_charges;
                shipping_charges = new Shipping_Charges_Model();

                shipping_charges.setShipping_charge_id(c
                        .getString("shipping_charge_id"));

                shipping_charges.setTotal_amount(c.getDouble("total_amount"));
                shipping_charges.setShipping_charge(c
                        .getString("shipping_charge"));

                ShippingChargesModel.add(shipping_charges);

                Log.i("shipping charges",""+shipping_charges);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private class CartAsy extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog_spinner = GlobalClass.showSpinner(getContext(), "wait.....");
                dialog_spinner.show();
                dialog_spinner.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            cart_details();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            try {

                String input = DatafromServer.replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                String output = "product added to cart sucessfully"
                        .replace(" ", "").replace("\n", "").replace("\t", "")
                        .toLowerCase();

                Log.i("sad " + input, "output " + output);

                if (input.equals(output)) {

                    OrderSend orderSend = new OrderSend();
                    orderSend.execute("");


                    Toast.makeText(getContext(), "product added to cart sucessfully", Toast.LENGTH_SHORT).show();
                  /*  Refferal_Cashback_fields reffaral_details = new Refferal_Cashback_fields();
                    reffaral_details.execute("");
                    Log.i("sad2 " + input, "output2 " + output);*/

                } else {
                    Toast.makeText(getContext(), " No product added to cart sucessfully", Toast.LENGTH_SHORT).show();

                 /*   Refferal_Cashback_fields reffaral_details = new Refferal_Cashback_fields();
                    reffaral_details.execute("");
                    Log.i("sad3 " + input, "output3 " + output);
*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cart_details() {

        try {
            for (int i = 0; i < CartListFragment.arrayList_cartproducts.size(); i++) {
                URL get = new URL(GlobalClass.URL
                        + "add_to_cart.php?customer_id="
                        + customer_id

                        + "&product_id="
                        + CartListFragment.arrayList_cartproducts.get(i).getProd_id()

                        + "&quantity="
                        + CartListFragment.arrayList_cartproducts.get(i).getProd_quantity());

                HttpURLConnection urlConnection = (HttpURLConnection) get.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in), 1024);
                StringBuffer sb = new StringBuffer();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                DatafromServer = sb.toString().replace("\\", "").substring(1, sb.toString().replace("\\", "").length() - 1);

//Log.i("Data From Server (carts_details)",""+DatafromServer);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private class OrderSend extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {

            order_send();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog_spinner.dismiss();
            Log.i("@@@@@@Order_id@@@@","@@@@@@@@@@@@@@@@@@@@"+Order_id);
            try{

                if(Order_id.equals(null)){
                    Log.i("%%%%Inside If","%%%%%%%%%%%%%%%%%%%%%%%%%");
                }else{
                    Log.i("####Inside else####","######################");

                    OrdersProduct_Asy ordersProduct_asy = new OrdersProduct_Asy();
                    ordersProduct_asy.execute("");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void order_send(){
        try {

            JSONArray contacts = null;
            JSONParser jsonParser = new JSONParser();

            JSONObject json = jsonParser.getJSONFromUrl(GlobalClass.URL
                    + "order_send.php?imi_number="
                    + MainActivity.IMEI_NO

                    + "&customer_id="
                    + customer_id

                    + "&firstname="
                    + payment_fname

                    + "&lastname="
                    + payment_lname

                    + "&email="
                    + email

                    + "&mobile="
                    + payment_contact_no

                    + "&payment_firstname="
                    + payment_fname

                    + "&payment_lastname="
                    + payment_lname

                    + "&payment_address"
                    + payment_address

                    + "&payment_city="
                    + payment_city

                    +"&payment_pincode="
                    +payment_pincode

                    +"&payment_area="
                    +payment_area

                    +"&payment_method="
                    +payment_method

                    +"&shipping_firstname="
                    +shipping_fname

                    +"&shipping_lastname="
                    +shipping_lname

                    +"&shipping_contact_number="
                    +shipping_contact_no

                    +"&shipping_address="
                    +shipping_address

                    +"&shipping_city="
                    +shipping_city

                    +"&shipping_pincode="
                    +shipping_pincode

                    +"&shipping_area="
                    +shipping_area

                    +"&order_status_id="
                    +""+1

                    +"&payment_address_id="
                    +payment_address_id

                    +"&shipping_address_id="
                    +shipping_address_id

                    +"&delivery_slot="
                    +""+1

                    +"&amount="
                    +""+CartListFragment.totalSum);


            contacts = json.getJSONArray("order_details");

            JSONObject c = contacts.getJSONObject(0);

            Order_id = c.getString("order_id");

//Log.i("Data From Server (carts_details)",""+DatafromServer);

        }
        catch (Exception e) {
//Log.i("Data From Server (carts_details)",""+e);
            Log.i("$$$$$$$$$$$$$$$$$$$$$","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        }

    }

    private class OrdersProduct_Asy extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            order_Product();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
// ***** Checking the condition for Order_iproduct_id is null or
// not *******//

                String input = dataFromServer_product.replace(" ", "")
                        .replace("\n", "").replace("\t", "").toLowerCase();

                String output = "inserted".replace(" ", "").replace("\n", "")
                        .replace("\t", "").toLowerCase();

                if (input.equals(output)) {

                    OrdersAdmin orders_admin = new OrdersAdmin();
                    orders_admin.execute("");

                } else {

                    OrdersAdmin orders_admin = new OrdersAdmin();
                    orders_admin.execute("");

                }

            } catch (Exception e) {
                GlobalClass.AlertMethod(getContext(),
                        GlobalClass.check_internet);
            }

        }

    }
    private void  order_Product(){

        try {
            DefaultHttpClient client = new DefaultHttpClient();

// http://indianleafsol.com/pavani/provision_cart/forgot_password.php?email=pavani@impressol.com
            for (int i = 0; i < CartListFragment.arrayList_cartproducts.size(); i++) {
                HttpGet get = new HttpGet(
// http://songnetwork.com/songnet_app/forgot_password.php?
                        GlobalClass.URL
                                + "order_product.php?order_id="
                                + (Order_id)
                                + "&product_id="
                                + URLEncoder.encode(
                                CartListFragment.arrayList_cartproducts.get(i)
                                        .getProd_id().toString(), "UTF-8")

                                + "&name="
                                + URLEncoder.encode(
                                CartListFragment.arrayList_cartproducts.get(i)
                                        .getProd_name(), "UTF-8")

                                + "&quantity="
                                + CartListFragment.arrayList_cartproducts.get(i)
                                .getProd_quantity()

                                + "&price="
                                + CartListFragment.arrayList_cartproducts.get(i)
                                .getProd_price()

                                + "&total_price="

                                + Double.parseDouble(CartListFragment.arrayList_cartproducts
                                .get(i).getProd_price())

                                * Integer.parseInt(CartListFragment.arrayList_cartproducts
                                .get(i).getProd_quantity())
                );
                HttpResponse resp = client.execute(get);
                InputStream in = resp.getEntity().getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in), 1024);
                StringBuffer sb = new StringBuffer();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                dataFromServer_product = sb
                        .toString()
                        .replace("\\", "")
                        .substring(1,
                                sb.toString().replace("\\", "").length() - 1);

                Log.i("Products" +CartListFragment.arrayList_cartproducts.size(), "Names"
                        + CartListFragment.arrayList_cartproducts.get(i).getProd_name());

            }
        } catch (Exception e) {

        }
    }

    private class OrdersAdmin extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            orders_admin();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }
    }

    public void orders_admin() {

        // http://indianleafsol.com/provision_cart_app/order_conformation_mail.php?customer_id=25
        // &products_count=3&address=pavani%20reddy,%3Cbr/%3E123,Maruthi%20nagar,%3Cbr/%3Ebanalore,phno:7453444444
        // &sub_total=496&shipping_charges=20&total_price=20&shipping_method=Cash%20On%20Delivery
        // &delivery_slot=27%20Sep%202015,%20Sunday%209:30%20AM%20-%2012:00%20PM&order_id=11

      /*  String input = DatafromServer_promo.replace(" ", "").replace("\n", "")
                .replace("\t", "").toLowerCase();

        String output = "Customer is already used promo code".replace(" ", "")
                .replace("\n", "").replace("\t", "").toLowerCase();

        if (for_check_wallet != 0) {

            str_total1 = str_total;

            Log.i("ss", "not equal to zero");

        }

        if (input.equals("") || input.equals(output)) {
*/
        try {

            DefaultHttpClient client = new DefaultHttpClient();

            // http://indianleafsol.com/pavani/provision_cart/forgot_password.php?email=pavani@impressol.com


            HttpGet get = new HttpGet(GlobalClass.URL
                    + "order_conformation_mail.php?customer_id="
                    + URLEncoder.encode(customer_id.replaceAll(" ", "%20"),
                    "UTF-8")

                    + "&customer_email="

                    + URLEncoder.encode(
                    email.replaceAll(" ", "%20"), "UTF-8")

                    + "&products_count="
                    + (CartListFragment.arrayList_cartproducts.size())

                    + "&address="
                    + URLEncoder.encode(shipping_fname
                            + " " + shipping_lname + ","
                            + "<br/>" + shipping_contact_no
                            + "," + "<br/>" + shipping_address
                            + "," + "<br/>" + shipping_area + ","
                            + "<br/>" + shipping_city + ","
                            + "<br/>" + shipping_pincode + ".",
                    "UTF-8")

                    + "&sub_total="
                    + CartListFragment.totalSum

                    + "&shipping_charges="
                    + str_ship_amount

                    + "&total_price="
                    + str_total

                    + "&shipping_method="
                    + URLEncoder.encode("Cash on Delivery", "UTF-8")

                    + "&delivery_slot="
                    + URLEncoder.encode("1", "UTF-8")

                    + "&order_id=" + URLEncoder.encode(Order_id, "UTF-8")

                    + "&cod_total=" + str_total

                    + "&used_wallet_amount=" + 1

                    + "&total_amount_after_wallet="
                    + str_total

                    + "&actual_total_amount_with_shipping_amount="
                    + str_total

                    + "&actual_total_amount=" + str_total

                    +"&vat_amount="+0

            );
            Log.i("************","shipping chrg="+str_ship_amount+",total price="+str_total);
            Log.i("************","&actual_total_amount=" + cart_total);
            HttpResponse resp = client.execute(get);
            InputStream in = resp.getEntity().getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in), 1024);
            StringBuffer sb = new StringBuffer();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            dataFromServer = sb
                    .toString()
                    .replace("\\", "")
                    .substring(1,
                            sb.toString().replace("\\", "").length() - 1);

            Log.i("data form server", "" + sb.toString() .replace("\\",
                    "") .substring( 1, sb.toString().replace("\\", "") .length()
                    - 1));

        } catch (Exception e) {

         /*
          * Toast.makeText(getApplicationContext(), "Login failed",
          * Toast.LENGTH_SHORT).show();

            }

        } else {

            try {

                DefaultHttpClient client = new DefaultHttpClient();

                // http://indianleafsol.com/pavani/provision_cart/forgot_password.php?email=pavani@impressol.com

                HttpGet get = new HttpGet(GlobalClass.Url
                        + "order_conformation_mail.php?customer_id="
                        + URLEncoder.encode(sp_cust_id.replaceAll(" ", "%20"),
                        "UTF-8")

                        + "&customer_email="

                        + URLEncoder.encode(
                        sp_cust_email.replaceAll(" ", "%20"), "UTF-8")

                        + "&products_count="
                        + (MainActivity.cartOrder.size())

                        + "&address="
                        + URLEncoder.encode(Select_Address.shipping_firstname
                                + " " + Select_Address.shipping_lastname + ","
                                + "<br/>" + Select_Address.shipping_mobile
                                + "," + "<br/>" + Select_Address.s_address1
                                + "," + "<br/>" + Select_Address.s_area1 + ","
                                + "<br/>" + Select_Address.s_city1 + ","
                                + "<br/>" + Select_Address.s_pincode1 + ".",
                        "UTF-8")

                        + "&sub_total="
                        + cart_total

                        + "&shipping_charges="
                        + str_ship_amount

                        + "&total_price="
                        + str_total

                        + "&shipping_method="
                        + URLEncoder.encode("Cash on Delivery", "UTF-8")

                        + "&delivery_slot="
                        + URLEncoder.encode(txt_deliverytime.getText()
                        .toString(), "UTF-8")

                        + "&order_id=" + URLEncoder.encode(Order_id, "UTF-8")

                        + "&cod_total=" + final_price

                        + "&used_wallet_amount=" + used_wallet_amount

                        + "&total_amount_after_wallet="
                        + str_total_amount_after_wallet

                        + "&actual_total_amount_with_shipping_amount="
                        + str_total

                        + "&actual_total_amount=" + cart_total

                        +"&vat_amount="+vat_amount_str

                );
                Log.i("************","sub total="+cart_total+",shipping chrg="+str_ship_amount+",total price="+str_total);
                Log.i("************","COD total="+final_price+",used vallet amount="+used_wallet_amount+",total_amount_after_wallet="+str_total_amount_after_wallet+"&actual_total_amount=" + cart_total
                        +"&vat_amount="+vat_amount_str);
                HttpResponse resp = client.execute(get);
                InputStream in = resp.getEntity().getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in), 1024);
                StringBuffer sb = new StringBuffer();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                dataFromServer = sb
                        .toString()
                        .replace("\\", "")
                        .substring(1,
                                sb.toString().replace("\\", "").length() - 1);
                Log.i("data form server",
                        ""
                                + sb.toString()
                                .replace("\\", "")
                                .substring(
                                        1,
                                        sb.toString().replace("\\", "")
                                                .length() - 1));
            } catch (Exception e) {

         /*
          * Toast.makeText(getApplicationContext(), "Login failed",
          * Toast.LENGTH_SHORT).show();
          */

        }
    }
}






