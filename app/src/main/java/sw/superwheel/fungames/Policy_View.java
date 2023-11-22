package sw.superwheel.fungames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.Manifest;

public class Policy_View extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 987654;
    private WebView webview;

    private static final String runOnce = "runOnce";
    private static final String permitSendData = "permitSendData";
    private SharedPreferences GamePreferences;
    private SharedPreferences.Editor editGamePreferences;

    public static void setOnClickListener(View.OnClickListener onClickListener) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_view);

        getWindow().setFlags(1024, 1024);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        GamePreferences = getSharedPreferences(AroundConfig.appCode, MODE_PRIVATE);
        editGamePreferences = GamePreferences.edit();

        webview = findViewById(R.id.policy_view);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(AroundConfig.policyURL);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowContentAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        Button btnAccept = findViewById(R.id.button);
        Button btnRefuse = findViewById(R.id.button2);

        btnAccept.setOnClickListener(view -> {
            if (!checkPermission()) {
                requestPermissions();
            } else {
                showConsentUser();
            }
        });

        btnRefuse.setOnClickListener(v -> {
            editGamePreferences.putBoolean(runOnce, false);
            editGamePreferences.apply();
            finishAffinity();
        });
    }

    private boolean checkPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return locationPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                editGamePreferences.putBoolean("locationGranted", true);
                editGamePreferences.putBoolean(runOnce, true);
            } else {
                editGamePreferences.putBoolean("locationGranted", false);
                editGamePreferences.putBoolean(runOnce, false);
            }
            editGamePreferences.apply();
        }
    }

    private void showConsentUser() {
        if (!GamePreferences.getBoolean(permitSendData, false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Data Consent of User");
            builder.setMessage("We need your permission to send data for analytics and provide a better user experience.");
            builder.setPositiveButton("Accept", (dialog, which) -> {
                editGamePreferences.putBoolean(permitSendData, true);
                editGamePreferences.apply();
                startApp();
            });
            builder.setNegativeButton("Don't Send Data", (dialog, which) -> {
                editGamePreferences.putBoolean(permitSendData, false);
                editGamePreferences.apply();
                startApp();
            });
            builder.show();
        } else {
            startApp();
        }
    }

    private void startApp() {
        Intent contentUI = new Intent(this, AlertConfig.class);
        contentUI.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(contentUI);
        finish();
    }
}
