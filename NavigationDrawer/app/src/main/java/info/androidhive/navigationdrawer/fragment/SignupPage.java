package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.OnBackPressedListener;
import info.androidhive.navigationdrawer.other.RegisterUserClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupPage extends Fragment {

    private static EditText signup_fedit, signup_ledit, signup_eedit, signup_pedit, signup_pswdedit, signup_cnfrpswdedit;
    Button signup_signup_button;
    ImageView signup_fimage, signup_limage, signup_eimage, signup_pimage, signup_pswdimage, signup_cnfrpswdimage, signup_bckimage,signup_frwdimage;
    TextView title_txt;

    SharedPreferences sp;
    String fname, lname, email, phone, password, cnfrpassword;

    private static final String REGISTER_URL = GlobalClass.URL+"customer_insert.php?";

    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PASSWORD = "password";

    public SignupPage() {


        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("Signup Page","onCreate");
        View v = inflater.inflate(R.layout.fragment_signup_page, container, false);
        MainActivity.pageIndex = 3;
        signup_fedit = (EditText) v.findViewById(R.id.signup_fname_edittext);
        signup_ledit = (EditText) v.findViewById(R.id.signup_lname_edittext);

        signup_eedit = (EditText) v.findViewById(R.id.signup_email_edittext);
        signup_pedit = (EditText) v.findViewById(R.id.signup_phone_edittext);
        signup_pswdedit = (EditText) v.findViewById(R.id.signup_password_edittext);
        signup_cnfrpswdedit = (EditText) v.findViewById(R.id.signup_cnfrm_password_edittext);
        signup_fimage = (ImageView) v.findViewById(R.id.signup_fname_imageview);
        signup_limage = (ImageView) v.findViewById(R.id.signup_lname_imageview);
        signup_eimage = (ImageView) v.findViewById(R.id.signup_email_imageview);
        signup_pimage = (ImageView) v.findViewById(R.id.signup_phone_imageview);
        signup_pswdimage = (ImageView) v.findViewById(R.id.signup_cnfrm_password_imageview);
        signup_cnfrpswdimage = (ImageView) v.findViewById(R.id.signup_cnfrm_password_imageview);
        signup_signup_button = (Button) v.findViewById(R.id.signup_signup_button);
        signup_bckimage = (ImageView) v.findViewById(R.id.toolbar_3_back_image);
        signup_frwdimage = (ImageView) v.findViewById(R.id.checkout_text);
        title_txt = (TextView) v.findViewById(R.id.toolbar_3_main_title);
        sp= getActivity().getSharedPreferences("credentials", Context.MODE_PRIVATE);

        title_txt.setText("Sign Up");
        signup_frwdimage.setVisibility(View.GONE);
        signup_bckimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm  =getFragmentManager();
                fm.popBackStack();
            }
        });


        fname = signup_fedit.getText().toString().trim();
        lname = signup_ledit.getText().toString().trim();
        email = signup_eedit.getText().toString().trim();
        phone = signup_pedit.getText().toString().trim();
        password = signup_pswdedit.getText().toString().trim();
        cnfrpassword = signup_cnfrpswdedit.getText().toString().trim();
            Log.d("nme",""+fname+lname);

        signup_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (signup_eedit.getText().toString().length() != 0
                        && signup_fedit.getText().toString().length() != 0

                        && signup_ledit.getText().toString().length() != 0

                        && signup_pedit.getText().toString().length() != 0

                        && signup_pswdedit.getText().toString().length() != 0
                        && signup_cnfrpswdedit.getText().toString().length() != 0) {

                    Log.d("first name","first = " + signup_fedit.getText().toString().trim());
                    Log.d("first name","first = " + signup_fedit.getText().toString().trim().length());
                    if (signup_eedit.getText().toString().matches(emailPattern)) {

                        if (signup_pswdedit.getText().toString().trim()
                                .equals(signup_cnfrpswdedit.getText().toString().trim())) {

                            if (GlobalClass
                                    .isNetworkAvailable(getContext())) {

                                //senddatatoserver(v);
                                registerUser();

                            } else {


                                Toast.makeText(getContext(), "Check Network connection", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(getContext(), "Check password and cnfrm password are not same", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Check Mail", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getContext(), "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void registerUser(){

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",""+response);
                        //----------------------------------------------------------------------------
                        if(response.trim().equals("The user already registered")){
                            Log.i("if block ------","######################################################################");
                            Toast.makeText(getContext(), "User Already Registered", Toast.LENGTH_LONG).show();
                            FragmentManager fm= getFragmentManager();
                            fm.popBackStack();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.frame,new HomeFragment());
                            ft.commit();
                            MainActivity.textView.setText("Home");

                        }
                        else
                        {
                            Log.i("else block -------","######################################################################");
                            try {
                                JSONObject json  = new JSONObject(response);
                                getUserDetails(json);
                                FragmentManager fm= getFragmentManager();
                                fm.popBackStack();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.frame,new Signup_address());
                                ft.commit();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //----------------------------------------------------------------------------

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
                params.put(KEY_FIRSTNAME,signup_fedit.getText().toString());
                params.put(KEY_LASTNAME,signup_ledit.getText().toString());
                params.put(KEY_MOBILE,signup_pedit.getText().toString());
                params.put(KEY_EMAIL,signup_eedit.getText().toString());
                params.put(KEY_PASSWORD,signup_pswdedit.getText().toString());
                Log.i("params",""+params);
                return params;
            }
        };
        Log.i("String Request",""+stringRequest);



        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.i("Strin Request2",""+stringRequest);
    }
    public void getUserDetails(JSONObject jsonObject){
        JSONArray result=null;
        JSONObject jsonObject1 = jsonObject;
        try {
            result = jsonObject1.getJSONArray("customer_details");
            for(int i=0;i<result.length();i++) {
                JSONObject category_data = result.getJSONObject(i);
                String res_customer_id = category_data.getString("customer_id");
                String res_first = category_data.getString("firstname");
                String res_last  = category_data.getString("lastname");
                String res_mobile = category_data.getString("mobile");
                String res_email  = category_data.getString("email");
                String res_pswd  = category_data.getString("password");

                SharedPreferences.Editor et = sp.edit();
                et.putString("customer_id",res_customer_id);
                et.putString("Firstname",res_first);
                et.putString("Lastname",res_last);
                et.putString("Mobile",res_mobile);
                et.putString("Email",res_email);
                et.putString("Password",res_pswd);
                et.commit();
                MainActivity.login_signup_number=1;

                Toast.makeText(getContext(),"Welcome,"+sp.getString("Firstname",null),Toast.LENGTH_SHORT).show();
                Log.i("@@@@contact_no@@@","@@@@@@@@@@@@@@@contact_no@@@@@@@@"+sp.getString("Mobile",null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
