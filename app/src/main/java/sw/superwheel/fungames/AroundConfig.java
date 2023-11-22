package sw.superwheel.fungames;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class AroundConfig extends Application {
    private static AroundConfig aroundCFG;
    public static String gameURL = "";
    public static String policyURL = "";
    public static String urlAPI = "";
    public static String appCode = "TG12105";
    public static String success = "";

    @Override
    public void onCreate() {
        super.onCreate();
        aroundCFG = this;

        // Initialize FirebaseApp only once in your Application class
        FirebaseApp.initializeApp(aroundCFG);

        initConfig();
    }

    public static synchronized AroundConfig getInstance() {
        return aroundCFG;
    }

    private void initConfig() {
        FirebaseRemoteConfig remoteCFG = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings settingsCFG = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();

        remoteCFG.setConfigSettingsAsync(settingsCFG);

    }
}
