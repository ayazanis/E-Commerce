package info.androidhive.navigationdrawer.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import info.androidhive.navigationdrawer.Model.AddressModelClass;
import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends Fragment {
    Button b;
    ImageView bck_img,frwd_img,profile_edit_img,pswd_edit_img,address_edit_img;
    EditText fname,lname,mobile,email,landmark,area,city,pincode;
    TextView main_title_txt,pswd;
    String TITLES[] = {""};
    String ICONS[] = {""};
    SharedPreferences.Editor editor,editor1;
    SharedPreferences sharedPreferences_signup,sharedPreferences_login;
    public static ArrayList<AddressModelClass> array_address;


    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Ecommerce";
    String EMAIL = "www.ecommerce.com";
    int PROFILE = R.drawable.ic_profile;

    final String Category_url = GlobalClass.URL + "category_list.php";
    public MyAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        MainActivity.pageIndex = 2;
        MainActivity.toolbar.setVisibility(View.GONE);
        bck_img = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);
        profile_edit_img = (ImageView) view.findViewById(R.id.img_profile_edit);
        pswd_edit_img = (ImageView) view.findViewById(R.id.img_pswd_edit);
        address_edit_img = (ImageView) view.findViewById(R.id.edit_default_adrs);
        main_title_txt = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        fname = (EditText) view.findViewById(R.id.txt_fname);
        lname = (EditText) view.findViewById(R.id.txt_lname);
        email = (EditText) view.findViewById(R.id.txt_email);
        mobile = (EditText) view.findViewById(R.id.txt_mobile);
        landmark = (EditText) view.findViewById(R.id.txt_landmark);
        area = (EditText) view.findViewById(R.id.txt_area);
        city = (EditText) view.findViewById(R.id.txt_city);
        pincode = (EditText) view.findViewById(R.id.txt_pincode);
        b=(Button)view.findViewById(R.id.logout);
        frwd_img.setVisibility(View.GONE);
        main_title_txt.setText("Account Info");
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MainActivity.textView.setText("Home");
                fm.popBackStack();
            }
        });
        array_address = new ArrayList<>();

        fname.setFocusable(false);
        fname.setEnabled(true);
        lname.setFocusable(false);
        lname.setEnabled(true);
        mobile.setFocusable(false);
        mobile.setEnabled(true);
        email.setFocusable(false);
        email.setEnabled(true);
        area.setFocusable(false);
        area.setEnabled(true);
        landmark.setFocusable(false);
        landmark.setEnabled(true);
        city.setFocusable(false);
        city.setEnabled(true);
        pincode.setFocusable(false);
        pincode.setEnabled(true);


        sharedPreferences_signup = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        sharedPreferences_login = getContext().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);
        String name_signup = sharedPreferences_signup.getString("Firstname",null);
        String name_login = sharedPreferences_login.getString("Firstname",null);
        editor = sharedPreferences_signup.edit();
        editor1 = sharedPreferences_login.edit();

        if(name_login!=null){
            getUserAddress();
            fname.setText(sharedPreferences_login.getString("Firstname",null));
            lname.setText(sharedPreferences_login.getString("Lastname",null));
            mobile.setText(sharedPreferences_login.getString("Mobile",null));
            email.setText(sharedPreferences_login.getString("Email",null));


        }else {
            fname.setText(sharedPreferences_signup.getString("Firstname",null));
            lname.setText(sharedPreferences_signup.getString("Lastname",null));
            mobile.setText(sharedPreferences_signup.getString("Mobile",null));
            email.setText(sharedPreferences_signup.getString("Email",null));
            area.setText(sharedPreferences_signup.getString("area",null));
            landmark.setText(sharedPreferences_signup.getString("address",null));
            city.setText(sharedPreferences_signup.getString("city",null));
            pincode.setText(sharedPreferences_signup.getString("pincode",null));
        }

        profile_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Profile edit_profile = new Edit_Profile();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame,edit_profile).addToBackStack("MyAccount");
                ft.commit();
            }
        });

        pswd_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_password edit_password = new Edit_password();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame,edit_password).addToBackStack("MyAccount");
                ft.commit();
            }
        });

        address_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Address edit_address = new Edit_Address();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame,edit_address).addToBackStack("MyAccount");
                ft.commit();
            }
        });

        islogin_signup();



        return view;
    }

    void islogin_signup(){


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor1.clear();
                editor.commit();
                editor1.commit();

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.order_sent);

                final TextView txt_order_sent = (TextView) dialog
                        .findViewById(R.id.txt_order_sent);

                txt_order_sent.setText("You have been logged out successfully.");
                dialog.show();
                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                btn_ok.setVisibility(View.VISIBLE);

                // if button is clicked, close the custom dialog
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    }
                });
            }
        });
    }


    private void getUserAddress() {
        if (GlobalClass.isNetworkAvailable(getContext())) {
            String url = GlobalClass.URL + "all_customer_address.php?customer_id="+sharedPreferences_login.getString("Customer_id", null);
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

                                    array_address.add(addressModelClass);



                                       /* subcategories_model.setCategory_id(subcategory_data.getString("category_id"));
                                        subcategories_model.setSubCategory_id(subcategory_data.getString("sub_category_id"));
                                        subcategories_model.setSubCategory_name(subcategory_data.getString("sub_category_name"));
                                        subcategories_model.setSubCategory_description(subcategory_data.getString("sub_category_description"));
                                        subcategories_model.setSubCategory_image(subcategory_data.getString("sub_category_image"));
                                        array_addressList.add(subcategories_model);*/
                                    Log.i("Sub_category _list", "Sub_category list1234 " + array_address.get(i).getAddress());
                                }
                                landmark.setText(array_address.get(0).getAddress());
                                area.setText(array_address.get(0).getArea());
                                city.setText(array_address.get(0).getCity());
                                pincode.setText(array_address.get(0).getPincode());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



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
}
