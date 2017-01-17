package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.other.GlobalClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class Signup_address extends Fragment {

    EditText address, area, city, pincode;
    ImageView bck_img;
    TextView save_txt;
    public String REGISTER_URL = GlobalClass.URL + "customer_address_insert.php?";
    SharedPreferences sharedPref;
    String cust_id, fname, lname, contact_no;

    public Signup_address() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup_address, container, false);
        address = (EditText) v.findViewById(R.id.signup_address_edittext);
        area = (EditText) v.findViewById(R.id.signup_area_Edittext);
        city = (EditText) v.findViewById(R.id.signup_city_editText);
        pincode = (EditText) v.findViewById(R.id.signup_pincode_editText);
        save_txt = (TextView) v.findViewById(R.id.signup_address_save_button);
        bck_img = (ImageView) v.findViewById(R.id.toolbar_3_back_image);
        sharedPref = getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        cust_id = sharedPref.getString("customer_id", null);
        fname = sharedPref.getString("Firstname", null);
        lname = sharedPref.getString("Lastname", null);
        contact_no = sharedPref.getString("Mobile", null);
        Log.i("######cust_id####", "########cust_id##########" + cust_id + fname + lname + contact_no);

        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        save_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().length() != 0
                        && area.getText().toString().length() != 0

                        && city.getText().toString().length() != 0

                        && pincode.getText().toString().length() != 0)

                {
                    if (GlobalClass
                            .isNetworkAvailable(getContext())) {

                        //senddatatoserver(v);
                        sendAddress();

                    } else {


                        Toast.makeText(getContext(), "Check Network Connection", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void sendAddress() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", "" + response);
                        //----------------------------------------------------------------------------
                        Log.i("if block ------", "######################################################################");
                        try {
                            JSONObject json = new JSONObject(response);
                            saveinDB(json);
                            FragmentManager fm = getFragmentManager();
                            fm.popBackStack();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.frame, new HomeFragment());
                            ft.commit();
                        } catch (Exception e) {

                        }

                        //----------------------------------------------------------------------------

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", "" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_id", cust_id);
                params.put("firstname", fname);
                params.put("lastname", lname);
                params.put("address", address.getText().toString());
                params.put("area", area.getText().toString());
                params.put("city", city.getText().toString());
                params.put("pincode", pincode.getText().toString());
                params.put("contact_number", contact_no);
                Log.i("params", "" + params);
                return params;
            }
        };
        Log.i("String Request", "" + stringRequest);


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.i("Strin Request2", "" + stringRequest);
    }

    private void saveinDB(JSONObject jsonObject) {
        JSONArray result = null;
        JSONObject jsonObject1 = jsonObject;
        try {
            result = jsonObject1.getJSONArray("customer_address_details");
            for (int i = 0; i < result.length(); i++) {
                JSONObject category_data = result.getJSONObject(i);
                String res_address_id = category_data.getString("address_id");
                String res_address = category_data.getString("address");
                String res_area = category_data.getString("area");
                String res_city = category_data.getString("city");
                String res_pincode = category_data.getString("pincode");

                SharedPreferences.Editor et = sharedPref.edit();
                et.putString("address_id", res_address_id);
                et.putString("address", res_address);
                et.putString("area", res_area);
                et.putString("city", res_city);
                et.putString("pincode", res_pincode);
                et.commit();
                Log.i("%%%%%address_id%%%","%%%%%%%address%%%%%%%%"+sharedPref.getString("address_id",null));
                Log.i("!!!!!area!!!!!!!!","!!!!!!!!area!!!!!!!!"+sharedPref.getString("area",null));
                Log.i("@@@@city@@@", "@@@@@@@@@city@@@@@@@@" + sharedPref.getString("city", null));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}