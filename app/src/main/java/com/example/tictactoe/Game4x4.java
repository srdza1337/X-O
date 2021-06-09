package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Game4x4 extends AppCompatActivity  {
    public Game_Launcher game_launcher = new Game_Launcher(4,Game4x4.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4x4);

        game_launcher.instance();
        game_launcher.buttonInit();
        game_launcher.buttonCreate();
        game_launcher.Time();
    }
}