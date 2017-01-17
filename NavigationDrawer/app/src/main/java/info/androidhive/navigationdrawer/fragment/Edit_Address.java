package info.androidhive.navigationdrawer.fragment;


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
public class Edit_Address extends Fragment {

    TextView main_title;
    EditText address,area,city,pincode;
    ImageView bck_img,frwd_img;
    Button update;

    public Edit_Address() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit__address, container, false);
        main_title = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        main_title.setText("Update Address");
        address = (EditText) view.findViewById(R.id.edit_address_address);
        area  = (EditText) view.findViewById(R.id.edit_address_area);
        city = (EditText) view.findViewById(R.id.edit_address_city);
        pincode = (EditText) view.findViewById(R.id.edit_address_pincode);
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

        address.setText(MyAccount.array_address.get(0).getAddress());
        area.setText(MyAccount.array_address.get(0).getArea());
        city.setText(MyAccount.array_address.get(0).getCity());
        pincode.setText(MyAccount.array_address.get(0).getPincode());
        return view;
    }

}
