package info.androidhive.navigationdrawer.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Admin on 14-10-2016.
 */

public class VolleyHelper {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context context;
    private String TAG = "VolleyHelper";
    private static VolleyHelper mInstance;

    private VolleyHelper() {
        // StringRequest
        // JsonArrayRequest
        // JsonObjectRequest
        // NetWorkImageView
    }

    public static synchronized VolleyHelper getVolleyHelper(Context con) {

        context = con;
        if (mInstance == null) {
            mInstance = new VolleyHelper();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {

            int cacheSize = 1024 * 1024 * 10; // 10MB Cache
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache(cacheSize));
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(IConstants.VOLLEY_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    // check for netwrok availability
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

}
