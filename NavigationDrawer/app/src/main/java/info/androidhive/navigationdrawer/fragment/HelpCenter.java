package info.androidhive.navigationdrawer.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.activity.MainActivity;
import info.androidhive.navigationdrawer.other.GlobalClass;
import info.androidhive.navigationdrawer.other.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpCenter extends Fragment {

    Dialog dialog;
    ImageView bck_img,frwd_img;
    TextView main_title_txt;
    String help_center_email, help_center_id, help_center_mobile;
    public HelpCenter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_center, container, false);
        MainActivity.pageIndex = 2;
        MainActivity.toolbar.setVisibility(View.GONE);
        bck_img = (ImageView) view.findViewById(R.id.toolbar_3_back_image);
        frwd_img = (ImageView) view.findViewById(R.id.checkout_text);
        main_title_txt = (TextView) view.findViewById(R.id.toolbar_3_main_title);
        main_title_txt.setText("Help Center");
        frwd_img.setVisibility(View.GONE);
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MainActivity.textView.setText("Home");
                fm.popBackStack();
            }
        });
        HelpAsy helpAsy = new HelpAsy();
        helpAsy.execute("");
        return view;
    }

    private class HelpAsy extends AsyncTask<String, String, String> {

        @Override
        // Make Progress Bar visible
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = GlobalClass.showSpinner(getContext(), "Wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            help_details();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub super.onPostExecute(result);
            dialog.dismiss();
            super.onPostExecute(result);

            // custom dialog


            // set the custom dialog components - text, image and button
            final TextView txt_mobile = (TextView) dialog
                    .findViewById(R.id.txt_mobile);

            Button txt_mail = (Button) dialog.findViewById(R.id.txt_mail);

            try {

                if (help_center_id.equals(null)) {
                    // ** if condition is true show the below dialog **//
                    help_center_mobile = "08042007523";

                    help_center_email = "support@provisioncart.com";

                    txt_mobile.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub

                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"
                                    + help_center_mobile));
                            startActivity(intent);

                            dialog.dismiss();

                        }
                    });

                    txt_mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("plain/text");
                            intent.putExtra(Intent.EXTRA_EMAIL,
                                    new String[] { help_center_email });
                            intent.putExtra(Intent.EXTRA_SUBJECT,
                                    "Feedback/Complaints regarding Provision Cart (PC).");
                            intent.putExtra(Intent.EXTRA_TEXT, "");
                            startActivity(intent);

                            dialog.dismiss();
                        }
                    });

                } else {

                    // ** if condition is false add the string values to
                    // textviews **//

                   txt_mobile.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent = new Intent(Intent.ACTION_DIAL);
                           intent.setData(Uri.parse("tel:"
                                   + help_center_mobile));
                           startActivity(intent);

                           dialog.dismiss();
                       }
                   });

                    txt_mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("plain/text");
                            intent.putExtra(Intent.EXTRA_EMAIL,
                                    new String[] { help_center_email });
                            intent.putExtra(Intent.EXTRA_SUBJECT,
                                    "Feedback/Complaints regarding Provision Cart (PC).");
                            intent.putExtra(Intent.EXTRA_TEXT, "");
                            startActivity(intent);

                            dialog.dismiss();

                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();

         /*
          * GlobalClass.AlertMethod(MainActivity.this,
          * GlobalClass.check_internet);
          */

            }
        }

    }

    // ** call this method from aprx line 193 **//
    public void help_details() {

        try {

            // http://54.187.105.112/provision_cart/customer_address.php?address_id=4

            JSONArray contacts = null;
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(GlobalClass.URL
                    + "help_center.php");
            try {
                contacts = json.getJSONArray("help_center_details");
                JSONObject c = contacts.getJSONObject(0);

                // ** get the detalis from server and stored into strings **//
                help_center_id = c.getString("help_center_id");

                help_center_email = c.getString("email");

                help_center_mobile = c.getString("telephone");

                Log.i("help" + help_center_id, "sadsa" + help_center_email);

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
