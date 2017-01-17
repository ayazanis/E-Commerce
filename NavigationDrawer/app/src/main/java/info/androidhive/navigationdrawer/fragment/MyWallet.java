package info.androidhive.navigationdrawer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWallet extends Fragment {


    public MyWallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.pageIndex = 2;
        return inflater.inflate(R.layout.fragment_my_wallet, container, false);
    }

}
