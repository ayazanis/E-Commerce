package info.androidhive.navigationdrawer.other;

/**
 * Created by Admin on 29-12-2016.
 */
import android.support.v7.widget.CardView;

public interface CardAdapter {

    public final int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
