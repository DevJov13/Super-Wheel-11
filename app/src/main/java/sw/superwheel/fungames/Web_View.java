package sw.superwheel.fungames;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton; // Correct the import statement

public class Web_View extends AppCompatActivity {
    private WebView webView;
    private LinearLayout linear;
    private FloatingActionButton fab; // Correct the FloatingActionButton import

    private boolean onClick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        linear = findViewById(R.id.linearLayout);
        fab = findViewById(R.id.floatingActionButton2);

        if (!AroundConfig.success.equals("success")) {
            fab.setVisibility(View.GONE);

        } else {
            fab.setVisibility(View.VISIBLE);
        }

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(AroundConfig.gameURL);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowContentAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        // Button to open the external policy
        Button dataPolicy = findViewById(R.id.button4);
        dataPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent externalPolicy = new Intent(Intent.ACTION_VIEW);
                externalPolicy.setData(Uri.parse(AroundConfig.policyURL));
                externalPolicy.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(externalPolicy);
            }
        });

        // Button to share the app
        Button shareButton = findViewById(R.id.button5);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = getPackageName();
                String appUrl = "https://play.google.com/store/apps/details?id=" + packageName;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, appUrl);
                startActivity(shareIntent);
            }
        });

        // Button to exit the app
        Button exitButton = findViewById(R.id.button6);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Close all activities in the task
                System.exit(0); // This will exit the app completely
            }
        });


        linear.setVisibility(View.GONE);
        // Initialize the Floating Action Button

        fab = findViewById(R.id.floatingActionButton2);

        if (onClick){


        }

        // Set an OnClickListener for the FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClick){
                    linear.setVisibility(View.VISIBLE);
                    onClick=false;


                }else
                {
                    linear.setVisibility(View.GONE);
                    onClick=true;
                }
            }
        });
    }
}
