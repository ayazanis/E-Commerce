package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.Model.AddressModelClass;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class Address_Fragment extends Fragment {

    EditText fname_txt,lname_txt,contact_txt,address_txt,pincode_txt,area_txt,city_txt;
    TextView save_btn;
    ArrayList<AddressModelClass> addressList;
    SharedPreferences sharedPreferences_login,sharedPreferences_signup;
    AddressModelClass addressModelClass;
    private static final String address_url= GlobalClass.URL+"add_shipping_address.php?";
    String address,area,pincode,city;
    String fname,lname,customer_id,contact;
    ImageView checkout_btn,back_arrow;
    public Address_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.address_layout_fragment, container, false);
        fname_txt = (EditText) v.findViewById(R.id.fname_edit);
        lname_txt = (EditText) v.findViewById(R.id.lname_edit);
        contact_txt = (EditText) v.findViewById(R.id.contact_edittext);
        address_txt=(EditText)v.findViewById(R.id.address_edittext);
        area_txt=(EditText)v.findViewById(R.id.area_Edittext);
        pincode_txt= (EditText) v.findViewById(R.id.pincode_editText);
        city_txt=(EditText)v.findViewById(R.id.city_editText);
        MainActivity.pageIndex=3;

        back_arrow = (ImageView)v.findViewById(R.id.toolbar_3_back_image);
        checkout_btn = (ImageView)v.findViewById(R.id.checkout_text);

        save_btn= (TextView) v.findViewById(R.id.save_address_button);
        addressList=new ArrayList<>();
        addressModelClass  = new AddressModelClass();

        checkout_btn.setVisibility(View.GONE);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences_signup = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
                sharedPreferences_login = getActivity().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);

                if(sharedPreferences_login.getString("Customer_id",null)!=null){
                    customer_id=sharedPreferences_login.getString("Customer_id",null);
                    Log.i("####inside if#####","####inside if#######"+customer_id);
                }
                else{
                    customer_id=sharedPreferences_signup.getString("customer_id",null);
                    Log.i("####inside else#####","####inside else########"+customer_id);
                }
                address = address_txt.getText().toString().trim();
                area = area_txt.getText().toString().trim();
                pincode=pincode_txt.getText().toString().trim();
                city  = city_txt.getText().toString().trim();

                addressModelClass.setCustomer_id(customer_id);
                addressModelClass.setShipping_firstname(fname_txt.getText().toString());
                addressModelClass.setShipping_lastname(lname_txt.getText().toString());
                addressModelClass.setShipping_contact_number(contact_txt.getText().toString());
                //addressModelClass.setAddress_id();

                addressModelClass.setShipping_address(address_txt.getText().toString());
                addressModelClass.setShipping_area(area_txt.getText().toString());
                addressModelClass.setShipping_city(city_txt.getText().toString());
                addressModelClass.setShipping_pincode(pincode_txt.getText().toString());

                addressList.add(addressModelClass);

                // for sending data to server
                saveAddress();

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();

            }
        });



        return v;
    }
public void saveAddress(){
    final StringRequest stringRequest = new StringRequest(Request.Method.POST, address_url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response",""+response);

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error",""+error.toString());
                }
            })
    {
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();
            params.put("customer_id",addressModelClass.getCustomer_id());
            params.put("shipping_firstname",addressModelClass.getShipping_firstname());
            params.put("shipping_lastname",addressModelClass.getShipping_lastname());
            params.put("shipping_address",addressModelClass.getShipping_address());
            params.put("shipping_city",addressModelClass.getShipping_city());
            params.put("shipping_pincode",addressModelClass.getShipping_pincode());
            params.put("shipping_area",addressModelClass.getShipping_area());
            params.put("shipping_contact_number",addressModelClass.getShipping_contact_number());
            Log.i("params",""+params);
            return params;
        }
    };
    Log.i("String Request",""+stringRequest);

    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
    requestQueue.add(stringRequest);
    Log.i("Strin Request2",""+stringRequest);

}
}
