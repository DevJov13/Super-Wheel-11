package sw.superwheel.fungames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AlertConfig extends AppCompatActivity {

    private ImageView splash; // Declare the ImageView variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreeen); // Replace "your_layout" with your actual layout XML file

        // Initialize the ImageView
        splash = findViewById(R.id.imageView);

        // Using a Handler to delay starting the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AlertConfig.this, Web_View.class);
                startActivity(intent); // Use 'intent' instead of 'Intent'
                finish();
            }
        }, 3000); // Delay for 3000 milliseconds (3 seconds)
    }
}
