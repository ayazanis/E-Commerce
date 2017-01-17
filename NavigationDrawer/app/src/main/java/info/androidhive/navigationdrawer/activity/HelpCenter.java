package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import info.androidhive.navigationdrawer.R;

public class HelpCenter extends Activity {
    Button callus,mailus;
    String help_center_mobile,help_center_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        callus = (Button)findViewById(R.id.button);
        mailus = (Button)findViewById(R.id.button2);
        help_center_mail="aayush.p@impressol.com";
        help_center_mobile = "9691405627";

        mailus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { help_center_mail });
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "Feedback/Complaints regarding Provision Cart (PC).");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }
        });

        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);

                intent.setData(Uri.parse("tel:"
                        + help_center_mobile));
                startActivity(intent);
            }
        });
    }
}
