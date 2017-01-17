package info.androidhive.navigationdrawer.other;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.Model.Categories_Model;
import info.androidhive.navigationdrawer.Model.ProductModel;
import info.androidhive.navigationdrawer.R;

/**
 * Created by Admin on 30-09-2016.
 */
public class GlobalClass {


    public static String URL = "http://50.116.82.221/~indianle/E-Commerce/";

    public static String check_internet = "No internet connection !" + System.getProperty ("line.separator")+"Please check internet connection.";
    public static String order_sent = "Order Sent Successfully";
    public static ArrayList<Categories_Model> Category_array_list = new ArrayList<>();

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
    public static ProgressDialog showSpinner(Context context, String message) {
        ProgressDialog pd;
        pd = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.custom_spinner));
        return pd;
    }

    public static void AlertMethod(Context context, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.alert_dialog);

        TextView txt_message = (TextView) dialog.findViewById(R.id.txt_message);

        txt_message.setText(message);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

        dialog.show();

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub

                dialog.dismiss();
            }
        });
    }
}
