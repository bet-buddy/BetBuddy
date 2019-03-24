package e.iot.betbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class AnswerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();

        String home_team_name = intent.getStringExtra("home_team");
        String away_team_name = intent.getStringExtra("away_team");
        String game_title = home_team_name + " : "+away_team_name;

        TextView homeTextView = findViewById(R.id.game_text_view);
        homeTextView.setText(game_title);

    }

}
