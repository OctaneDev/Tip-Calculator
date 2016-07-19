package biz.bigtooth.tipcalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    EditText bill;
    TextView ten;
    TextView fifteen;
    TextView twenty;
    TextView twentyfive;
    Button submit;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bill = (EditText)findViewById(R.id.bill);
        ten = (TextView)findViewById(R.id.ten);
        fifteen = (TextView)findViewById(R.id.fifteen);
        twenty = (TextView)findViewById(R.id.twenty);
        twentyfive = (TextView)findViewById(R.id.twentyfive);
        submit = (Button)findViewById(R.id.calc);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5164171001589422/8253764595");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5164171001589422/5617598593");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                calc(null);
            }
        });

        requestNewInterstitial();

        bill.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.restaurant:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle(R.string.restaurant_faq)
                        .setMessage(R.string.rest_tipping)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.hotel:

                return true;
            case R.id.about:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("5C6759521D465119182182A234FF5CE3")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @SuppressLint("DefaultLocale")
    public void calc(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Double price = Double.parseDouble(bill.getText().toString());

            Double calculatedvalue1 = price * .10;
            Double calculatedvalue2 = price * .15;
            Double calculatedvalue3 = price * .20;
            Double calculatedvalue4 = price * .25;

            ten.setText(String.format("10 percent = $%.2f", calculatedvalue1));
            fifteen.setText(String.format("15 percent = $%.2f", calculatedvalue2));
            twenty.setText(String.format("20 percent = $%.2f", calculatedvalue3));
            twentyfive.setText(String.format("25 percent = $%.2f", calculatedvalue4));
        }
    }
}
