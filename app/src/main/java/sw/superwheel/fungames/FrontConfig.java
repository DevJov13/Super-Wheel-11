package sw.superwheel.fungames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class FrontConfig extends AppCompatActivity {

    private FirebaseRemoteConfig remoteCFG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreeen);

        remoteCFG = FirebaseRemoteConfig.getInstance();
        remoteCFG.fetchAndActivate().addOnCompleteListener(task -> {
            // Check if task is successful or not
            if (task.isSuccessful()) {
                Log.d("FirebaseCFG:", "Loading Successful");
                AroundConfig.urlAPI = remoteCFG.getString("urlAPI");

                String endPoint = AroundConfig.urlAPI + "?request&appid=TG12105";
                Log.d("WZ", AroundConfig.urlAPI);
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, endPoint,
                        response -> {
                            Gson parseValue = new Gson();
                            JsonObject jsonObject = parseValue.fromJson(response, JsonObject.class);
                            AroundConfig.gameURL = jsonObject.get("gameURL").getAsString();
                            AroundConfig.success = jsonObject.get("status").getAsString();

                            AroundConfig.policyURL = jsonObject.get("policyURL").getAsString();



                            if (!AroundConfig.success.equals("success")) {
                                Intent gameWeb = new Intent(FrontConfig.this, AlertConfig.class);

                                startActivity(gameWeb);

                            } else {

                                Intent gameWeb = new Intent(FrontConfig.this, Policy_View.class);

                                startActivity(gameWeb);
                            }


                            Log.d("gameUrl:", AroundConfig.gameURL);
                            Log.d("policyURL:", AroundConfig.policyURL);


                        }, error -> {
                    // Handle the error here
                });
                requestQueue.add(stringRequest);

            } else {
                Log.e("FirebaseCFG:", "Loading not Successful" + task.getException());
            }

        });
    }
}
