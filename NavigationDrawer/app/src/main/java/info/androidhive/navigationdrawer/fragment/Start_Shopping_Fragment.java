package info.androidhive.navigationdrawer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import info.androidhive.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Start_Shopping_Fragment extends Fragment {

    Button start_shop;

    public Start_Shopping_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.start__shopping_fragment, container, false);;



        return v;
    }

}
