package com.example.demouser.scarnesdice;

import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //score variables for user and computer
    private int userOverallScore;
    private int userTurnScore;
    private int computerOverallScore;
    private int computerTurnScore;
    private int diceValue;

    private ImageView dice;
    private Button rollButton;
    private Button holdButton;
    private Button resetButton;

    private TextView extraScore;
    private TextView yourScore;
    private TextView comScore;

    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize UI components in main code
        yourScore = findViewById(R.id.yourScore);   //textviews
        comScore = findViewById(R.id.comScore);
        extraScore = findViewById(R.id.extra);

        dice = findViewById(R.id.oneDice);  //images

        rollButton = findViewById(R.id.roll);   //buttons
        holdButton = findViewById(R.id.hold);
        resetButton = findViewById(R.id.reset);



        //initialize action listeners & define functionality for buttons
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);

                doARoll();
                //if 1
                if (diceValue==1) {
                    //reset turn score and reset text label
                    userTurnScore = 0;
                    extraScore.setText("Your Turn Score = " + userTurnScore);
                    userOverallScore += userTurnScore;
                    yourScore.setText("Your Score = " + userOverallScore);

                    //computer's turn
                    computerTurn();

                }
                else{
                    userTurnScore += diceValue; //update the turn score
                    extraScore.setText("Your Turn Score = " + userTurnScore);   //update the label
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reset all scores to 0
                userOverallScore = 0;
                userTurnScore = 0;
                computerOverallScore = 0;
                computerTurnScore= 0;
                //reset text labels to reflect reset scores
                yourScore.setText("Your score = " + userOverallScore);
                comScore.setText("Computer score = " + computerOverallScore);
                extraScore.setText("Play a turn!");

                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update user's overall score by adding turn score & update label to reflect that
                userOverallScore += userTurnScore;
                if (userOverallScore >= 100){
                    extraScore.setText("YOU WIN! Press reset to play again.");
                    rollButton.setEnabled(false);
                    holdButton.setEnabled(false);

                }
                else {
                    yourScore.setText("Your Score = " + userOverallScore);
                    //reset turn score and reset text label
                    userTurnScore = 0;
                    extraScore.setText("Turn Score = " + userTurnScore);
                    //now it's computer's turn
                    computerTurn();
                }

            }
        });

    }

    /**
     * helper method when it's the computer's turn
     * disables roll and hold buttons & while loop to loop over computer's turns
     */
    private void computerTurn() {

        rollButton.setEnabled(false);
        holdButton.setEnabled(false);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                doARoll();
                computerTurnScore += diceValue;

                if (computerOverallScore < 100) {
                    if (computerTurnScore >= 20) {
                        //hold there
                        extraScore.setText("Turn Score = " + 0);
                        computerOverallScore += computerTurnScore;
                        comScore.setText("Computer score = " + computerOverallScore);
                        //reset turn score and reset text label
                        computerTurnScore = 0;
                        //computerTurn = false;
                    } else if (diceValue == 1) {
                        computerTurnScore = 0;
                        extraScore.setText("Turn Score = " + computerTurnScore);
                        computerOverallScore += computerTurnScore;
                        comScore.setText("Computer score = " + computerOverallScore);
                        // computerTurn = false;
                    } else if (diceValue > 1) {
                        //pause to see image change
                        extraScore.setText("Turn Score = " + computerTurnScore);
                        handler.postDelayed(this, 500);
                    }

                }
                if (computerOverallScore >= 100){
                    extraScore.setText("Computer Wins! Press reset for new game.");
                    //re-enable buttons
                    rollButton.setEnabled(false);
                    holdButton.setEnabled(false);
                }

            }
        };
        handler.postDelayed(r, 500);

        //re-enable buttons
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);

    }

    /**
     * Method invoked when the roll button is pressed.
     * Contains rules of the game, when dice rolls a 1, turn gets 0, else turn gets # rolled.
     */
    private void doARoll() {
        //dice rolls a random #
        diceValue = random.nextInt(6-1) + 1;

        switch(diceValue) {
            case 1:
                dice.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.dice1, null));
                diceValue = 1;  //update score
                break;

            case 2:
                dice.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.dice2, null));
                break;

            case 3:
                dice.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.dice3, null));
                break;

            case 4:
                dice.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.dice4, null));
                break;

            case 5:
                dice.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.dice5, null));
                break;
            case 6:
                dice.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.dice6, null));
                break;
        }

    }


}
