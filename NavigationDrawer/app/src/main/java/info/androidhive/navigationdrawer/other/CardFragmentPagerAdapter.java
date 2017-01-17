package info.androidhive.navigationdrawer.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.fragment.CardFragment;

/**
 * Created by Admin on 29-12-2016.
 */

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragment> fragments;
    private float baseElevation;
    int imagesArray[]={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};

    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;

        for(int i = 0; i< imagesArray.length; i++){
            CardFragment.cardimage=i;
            addCardFragment(new CardFragment());
        }
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.getInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {

        fragments.add(fragment);
    }

}
