package com.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.MainActivity;
import com.example.admin.testproj.R;

/**
 * Created by admin on 2016-12-19.
 */

public class GameOverActivity extends Activity {

    private static GameOverActivity instance = null;

    public static GameOverActivity getInstance() {
        if(GameOverActivity.instance == null)
            GameOverActivity.instance = new GameOverActivity();
        return GameOverActivity.instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        Intent intent = new Intent(this.getIntent());

        Log.d("GameOverActicity", "onCreate");

        ImageView gameOver = (ImageView)findViewById(R.id.img_game_over);
        if(gameOver != null) {
            gameOver.setVisibility(View.VISIBLE);
        }

        Button restart = (Button)findViewById(R.id.restart);
        if(restart != null) {
            restart.setEnabled(true);
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Save Finsh Time


                    //Go to Game View
                    Intent intent = new Intent(GameOverActivity.this, GameModeActivity.class);

                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    protected GameOverActivity() {

        Log.d("GameOverActicity", "new");
    }


}
