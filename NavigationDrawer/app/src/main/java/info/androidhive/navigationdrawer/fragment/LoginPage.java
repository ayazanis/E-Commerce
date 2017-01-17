package info.androidhive.navigationdrawer.fragment;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.Model.AddressModelClass;
import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.OnBackPressedListener;
import info.androidhive.navigationdrawer.other.RecyclerTouchListener;

import static info.androidhive.navigationdrawer.R.id.login_username_edittext;
import static info.androidhive.navigationdrawer.fragment.SignupPage.KEY_EMAIL;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LoginPage extends Fragment {

    public String Customer_Id = "";

    public String Address_ID = "";

    public String emailId = "";

    public static String First_Name = "";

    public String Last_Name = "";

    public String Mobile = "";

    public String balance = "";

    public static boolean login_boolean = false;

    public String used_referral_id = "";

    public String LOGIN_URL = GlobalClass.URL + "/login.php";

    public String signup_Customer_Id, signup_Address_ID, signup_First_Name,
            signup_Last_Name, signup_Address, signup_City, signup_pincode,
            signup_area;

    public String signup_Customer_Id1, signup_Address_ID1, signup_First_Name1,
            signup_Last_Name1, signup_Address1, signup_City1, signup_pincode1,
            signup_area1;

    EditText user_edit, pswn_edit;
    Button signup_btn, login_btn;
    JSONArray contacts;
    ImageView bck_btn, frwd_btn;
    TextView title_txt;
    SharedPreferences sharedPreferences, sharedpref;

    public LoginPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_login_page, container, false);

        MainActivity.pageIndex = 2;
        MainActivity.toolbar.setVisibility(View.GONE);
        login_btn = (Button) v.findViewById(R.id.login_login_button);
        signup_btn = (Button) v.findViewById(R.id.login_signup_button);
        user_edit = (EditText) v.findViewById(R.id.login_username_edittext);
        pswn_edit = (EditText) v.findViewById(R.id.login_password_edittext);
        bck_btn = (ImageView) v.findViewById(R.id.toolbar_3_back_image);
        frwd_btn = (ImageView) v.findViewById(R.id.checkout_text);
        title_txt = (TextView) v.findViewById(R.id.toolbar_3_main_title);
        title_txt.setText("Login");
        sharedpref = getActivity().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);

        frwd_btn.setVisibility(View.GONE);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String pass = pswn_edit.getText().toString().trim();
                String pswd_length = pswn_edit.getText().toString().trim();

                if (user_edit.getText().toString().length() != 0
                        && pswn_edit.getText().toString().length() != 0) {
                    Log.i("User_edit", "" + user_edit.getText().toString().trim());
                    Log.i("Password_edit", "" + pswn_edit.getText().toString().trim());


                    if (user_edit.getText().toString().matches(emailPattern)) {

                        if (GlobalClass
                                .isNetworkAvailable(getContext())) {
                            Log.i("User_edit", "" + user_edit.getText().toString().trim());
                            Log.i("Password_edit", "" + pswn_edit.getText().toString().trim());

                            if (GlobalClass
                                    .isNetworkAvailable(getContext())) {
                                loginUser();
                            } else {
                                Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
                            }
                        } else {


                        }

                    } else {


                    }

                } else {
                    Toast.makeText(getContext(), "enter valid data", Toast.LENGTH_SHORT).show();
                }
            }

        });

        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.textView.setText("Home");
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignupPage signupPage = new SignupPage();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, signupPage).addToBackStack("");
                ft.commit();
            }
        });
        return v;
    }

    private void loginUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               /* Log.i("response",""+response);*/

                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray result = null;
                JSONObject jsonObject = json;
                try {
                    result = jsonObject.getJSONArray("customer_details");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject category_data = result.getJSONObject(i);
                        String res_customer_id = category_data.getString("customer_id");
                        String res_first = category_data.getString("firstname");
                        String res_last = category_data.getString("lastname");
                        String res_mobile = category_data.getString("mobile");
                        String res_email = category_data.getString("email");
                        String res_pswd = category_data.getString("password");

                        Log.i("response from server", "response from server customer_id=" + res_customer_id);
                        Log.i("response from server", "response from server first Name =" + res_first);
                        Log.i("response from server", "response from server last name=" + res_last);
                        Log.i("response from server", "response from server mobile=" + res_mobile);
                        Log.i("response from server", "response from server email=" + res_email);
                        Log.i("response from server", "response from server password=" + res_pswd);

                        SharedPreferences.Editor et = sharedpref.edit();
                        et.putString("Customer_id", res_customer_id);
                        et.putString("Firstname", res_first);
                        et.putString("Lastname", res_last);
                        et.putString("Mobile", res_mobile);
                        et.putString("Email", res_email);
                        et.putString("Password", res_pswd);
                        et.commit();
                        MainActivity.login_signup_number = 2;
                        SharedPreferences spf = getActivity().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);
                        Toast.makeText(getActivity(), "Welcome, " + spf.getString("Firstname", null), Toast.LENGTH_LONG).show();

                    }

                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame, new HomeFragment());
                    ft.commit();
                    MainActivity.textView.setText("Home");

                } catch (Exception e) {
                    Log.i("result&&&", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.i("User_edit", "" + user_edit.getText().toString().trim());
                Log.i("Password_edit", "" + pswn_edit.getText().toString().trim());
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", user_edit.getText().toString());
                params.put("password", pswn_edit.getText().toString());
                Log.i("params", "" + params);
                return params;
            }
        };
        Log.i("String Request", "" + stringRequest);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.i("Strin Request2", "" + stringRequest);

        // code for saving in shared preference
    }


}
