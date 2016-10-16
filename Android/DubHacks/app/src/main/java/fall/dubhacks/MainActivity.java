package fall.dubhacks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import static android.R.attr.button;
import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    Button startBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBTN = (Button)findViewById(R.id.launch);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PriorityActivity.class);
                startActivity(i);
            }
        });
    }
}
