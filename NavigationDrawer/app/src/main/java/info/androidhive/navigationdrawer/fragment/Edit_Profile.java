package info.androidhive.navigationdrawer.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_Profile extends Fragment {
    TextView main_title;
    EditText fname,lname,mobile,email;
    ImageView bck_img,frwd_img;
    Button update;
    SharedPreferences sharedPreferences_signup,sharedPreferences_login;

    public Edit_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit__profile, container, false);
        main_title = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        main_title.setText("Update Profile");
        fname = (EditText) view.findViewById(R.id.edit_prof_fname);
        lname  = (EditText) view.findViewById(R.id.edit_prof_lname);
        email = (EditText) view.findViewById(R.id.edit_prof_email);
        mobile = (EditText) view.findViewById(R.id.edit_prof_mobile);
        bck_img = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);
        frwd_img.setVisibility(View.GONE);
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });


        sharedPreferences_signup = getContext().getSharedPreferences("credentials", Context.MODE_PRIVATE);
        sharedPreferences_login = getContext().getSharedPreferences("credentials_new", Context.MODE_PRIVATE);
        String name_login = sharedPreferences_login.getString("Firstname",null);
        if(name_login!=null){
            fname.setText(sharedPreferences_login.getString("Firstname",null));
            lname.setText(sharedPreferences_login.getString("Lastname",null));
            mobile.setText(sharedPreferences_login.getString("Mobile",null));
            email.setText(sharedPreferences_login.getString("Email",null));
        }else {
            fname.setText(sharedPreferences_signup.getString("Firstname", null));
            lname.setText(sharedPreferences_signup.getString("Lastname", null));
            mobile.setText(sharedPreferences_signup.getString("Mobile", null));
            email.setText(sharedPreferences_signup.getString("Email", null));
        }
        return view;
    }

}
