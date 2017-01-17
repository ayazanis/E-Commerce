package info.androidhive.navigationdrawer.fragment;


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
public class Edit_password extends Fragment {

    ImageView bck_img,frwd_img;
    TextView main_title;
    EditText old_pswd,new_pswd,cnfm_pswd;
    Button update_btn;

    public Edit_password() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_password, container, false);
        main_title = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        bck_img = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);
        old_pswd = (EditText) view.findViewById(R.id.edit_pswd_old);
        new_pswd = (EditText) view.findViewById(R.id.edit_pswd_new);
        cnfm_pswd = (EditText) view.findViewById(R.id.edit_pswd_cnfrm);
        update_btn = (Button) view.findViewById(R.id.edit_pswd_update);

        frwd_img.setVisibility(View.GONE);
        main_title.setText("Edit Password");
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        return view;
    }

}
