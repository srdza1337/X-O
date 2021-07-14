package com.example.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Game_Launcher {

    private int n;
    private AppCompatActivity ac;
    TextView timer;
    String pl1;
    String pl2;
    TextView textViewPlayer1;
    TextView textViewPlayer2;
    private CountDownTimer countDownTimer;
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points=0;
    private int player2Points=0;
    public final Button[][] buttons;
    Button buttonpa;

    public Game_Launcher(int n, AppCompatActivity ac) {
        this.n = n;
        this.ac = ac;
        buttons = new Button[this.n][this.n];
    }

    public  boolean checkWin(){
        ArrayList<List<String>> field1 = new ArrayList<>();
        for (int i = 0; i < n+4; i++)
            field1.add(Arrays.asList("","","","","","","","","",""));

        for (int x = 0; x < n; x++)
            for (int y = 0; y < n; y++)
                if (!(field1.get(x+2).get(y+2).equals(buttons[x][y].getText().toString())) && !(buttons[x][y].getText().toString().equals(""))) {
                    field1.get(x+2).set(y+2, buttons[x][y].getText().toString());
                    String flag = buttons[x][y].getText().toString();



                    //kolona
                    if  ((field1.get(x-1+2).get(y+2).equals(flag)) &&
                            ((field1.get(x - 2+2).get(y+2).equals(flag))|| (field1.get(x+1+2).get(y+2).equals(flag)))) {

                        return true;

                    }else if ((field1.get(x+1+2).get(y+2).equals(flag)) && (field1.get(x+2+2).get(y+2).equals(flag))){
                        return true;}


                    //red
                    if ((field1.get(x+2).get(y-1+2).equals(flag)) &&
                            ((field1.get(x+2).get(y-2+2).equals(flag))|| (field1.get(x+2).get(y+1+2).equals(flag)))) {

                        return true;
                    }else if ((field1.get(x+2).get(y+1+2).equals(flag)) && (field1.get(x+2).get(y+2+2).equals(flag))){

                        return true;}


                    //dijagonala
                    if  ((field1.get(x-1+2).get(y-1+2).equals(flag)) &&
                            ((field1.get(x+1+2).get(y+1+2).equals(flag))||(field1.get(x-2+2).get(y-2+2).equals(flag)) )){

                        return true;
                    }else if ((field1.get(x+1+2).get(y+1+2).equals(flag))||(field1.get(x+2+2).get(y+2+2).equals(flag))){

                        return true;}


                    //suprotna dijagonala
                    if  ((field1.get(x+1+2).get(y-1+2).equals(flag)) &&
                            ( (field1.get(x-1+2).get(y+1+2).equals(flag))|| (field1.get(x+2+2).get(y-2+2).equals(flag)))){

                        return true;
                    }else if ((field1.get(x-1+2).get(y+1+2).equals(flag)) && (field1.get(x-2+2).get(y+2+2).equals(flag))){

                        return true;}
                }
        return false;

    }

    public  void instance(){
        timer = (TextView) ac.findViewById(R.id.timer);

        Intent intent = ac.getIntent();

        pl1 = intent.getStringExtra("pl1");
        pl2 = intent.getStringExtra("pl2");

        textViewPlayer1 = (TextView) ac.findViewById(R.id.text_view_p1);
        textViewPlayer1.setTextColor(Color.parseColor("blue"));
        textViewPlayer2 = (TextView) ac.findViewById(R.id.text_view_p2);

        textViewPlayer1.setText(pl1 + ": 0");
        textViewPlayer2.setText(pl2 + ": 0");

    }

    public void buttonCreate(){
        Button buttonReset = ac.findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
                enableButtons();
                timer.setText("10");

            }
        });

        buttonpa = (Button) ac.findViewById(R.id.buttonpa);//novo
        buttonpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                enableButtons();
                timer.setText("10");

            }
        });

        Button buttonbtm = (Button) ac.findViewById(R.id.buttonbtm);//novo
        buttonbtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac.startActivity(new Intent(ac, First_Page.class));
                countDownTimer.cancel();
            }
        });
    }

    public void buttonInit() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String buttonID = "button_" + i + j;
                int resID = ac.getResources().getIdentifier(buttonID, "id", ac.getPackageName());
                buttons[i][j] = ac.findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        countDownTimer.start();
                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }
                        if (player1Turn) {
                            textViewPlayer1.setTextColor(Color.parseColor("gray"));//novo
                            textViewPlayer2.setTextColor(Color.parseColor("red"));//novo
                            ((Button) v).setText("X");
                            ((Button) v).setBackgroundColor(ac.getResources().getColor(R.color.blue));

                        } else {
                            ((Button) v).setText("O");
                            textViewPlayer1.setTextColor(Color.parseColor("blue"));//novo
                            textViewPlayer2.setTextColor(Color.parseColor("gray"));//novo
                            ((Button) v).setBackgroundColor(ac.getResources().getColor(R.color.red));


                        }
                        roundCount++;

                        if (checkWin()) {
                            disableButtons();
                            if (player1Turn) {
                                player1Wins();

                            } else {
                                player2Wins();

                            }
                            int sum=player1Points+player2Points;
                            if(sum%2==0){
                                player1Turn=true;
                            }
                            else{ player1Turn = !player1Turn;}
                           sum++;

                             endGame();
                        } else if (roundCount == Math.pow(n, 2)) {
                            draw();
                            disableButtons();
                        } else {
                            player1Turn = !player1Turn;
                        }
                    }
                });
            }
        }
    }

    public void endGame(){
        if (player1Points==3)
        {
            Toast.makeText(ac, "Game is over! " +pl1+ " has won.", Toast.LENGTH_LONG).show();
            buttonpa.setEnabled(false);
        }else if (player2Points==3){
            Toast.makeText(ac, "Game is over! " +pl2+ " has won.", Toast.LENGTH_LONG).show();
            buttonpa.setEnabled(false);
        }

    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(ac, pl1 + " wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        countDownTimer.cancel();//novo

    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(ac, pl2 + " wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        countDownTimer.cancel();//novo

    }

    private void draw() {
        Toast.makeText(ac, "Draw!", Toast.LENGTH_SHORT).show();
        countDownTimer.cancel();//novo
    }

    private void updatePointsText() {
        textViewPlayer1.setText(pl1 + ": " + player1Points);
        textViewPlayer2.setText(pl2 + ": " + player2Points);
        textViewPlayer1.setTextColor(Color.parseColor("blue"));//novo
        textViewPlayer2.setTextColor(Color.parseColor("gray"));//novo


    }

    private void resetBoard() {
        countDownTimer.start();//novo
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(ac.getResources().getColor(R.color.gray));//novo

            }
        }
        roundCount = 0;
        //player1Turn = true;
        if (player1Turn){
            textViewPlayer1.setTextColor(Color.parseColor("blue"));//novo
            textViewPlayer2.setTextColor(Color.parseColor("gray"));//novo

        }else {
            textViewPlayer1.setTextColor(Color.parseColor("gray"));//novo
            textViewPlayer2.setTextColor(Color.parseColor("red"));//novo

        }
        countDownTimer.cancel();//novo
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
        player1Turn=true;
        textViewPlayer1.setTextColor(Color.parseColor("blue"));//novo
        textViewPlayer2.setTextColor(Color.parseColor("gray"));//novo
        buttonpa.setEnabled(true);

    }

    public void Time() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timer.setText(millisUntilFinished / 1000 + "");

            }

            @Override
            public void onFinish() {
                player1Turn = !player1Turn;
                if (player1Turn) {
                    Toast.makeText(ac, pl1 + "'s turn.", Toast.LENGTH_SHORT).show();
                    textViewPlayer1.setTextColor(Color.parseColor("blue"));
                    textViewPlayer2.setTextColor(Color.parseColor("gray"));
                } else {
                    Toast.makeText(ac, pl2 + "'s turn.", Toast.LENGTH_SHORT).show();
                    textViewPlayer1.setTextColor(Color.parseColor("gray"));
                    textViewPlayer2.setTextColor(Color.parseColor("red"));
                }
                countDownTimer.start();
            }
        };
    }

    private void enableButtons(){
        for (int i = 0; i < n; i++)
            for (int j = 0; j <n ; j++) {
                buttons[i][j].setEnabled(true);
            }



    }

    private void disableButtons() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                buttons[i][j].setEnabled(false);
            }
    }
}
