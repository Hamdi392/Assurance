package com.outsider.lanalaassurance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class WebPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_payment);

        TextView textView = findViewById(R.id.textViewHtml);
       // textView.setText(Html.fromHtml(getString(R.string.nice_html)));
    }
}
