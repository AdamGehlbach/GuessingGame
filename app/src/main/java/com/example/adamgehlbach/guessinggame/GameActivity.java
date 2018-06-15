package com.example.adamgehlbach.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Button guessButton;
    private TextView clue;
    private EditText guess;

    private int generatedNumber;
    private int numberOfGuesses = 0;
    private final int MAX_GUESS_COUNT = 4;
    public static final String winningNumber = "WINNING_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guessButton = findViewById(R.id.subment_game_button);
        clue = findViewById(R.id.clue_textview);
        guess = findViewById(R.id.guess_edittext);

        //This generates a random umber between 1 and 100.
        generatedNumber = (int) Math.ceil(Math.random() * 100);

        //Toast.makeText(this, Integer.toString(generatedNumber), Toast.LENGTH_SHORT).show();

        setListener();
    }

    private void setListener() {
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateGuess();
            }
        });
    }

    //Check to make sure the user has put in a valid number
    private void validateGuess() {
        try {
            int userGuess = Integer.parseInt(guess.getText().toString());
            if (userGuess > 100 || userGuess <= 0) {
                clue.setText("Enter a number bewteen 1 and 100");
                clue.setVisibility(View.VISIBLE);
                guess.setText("");
            } else {
                checkGuess(userGuess);
            }
        } catch (NumberFormatException nfe) {
            clue.setText("Enter a number");
            clue.setVisibility(View.VISIBLE);
        }
    }
    //This method will take the input and check it against the generatedNumber. Depending on on the outcome it will change the view accordingly or take us to the Results Activity:
    private void checkGuess(int userGuess) {

        if (userGuess == generatedNumber) {
            // Go to ResultsActivity. user has guessed correctly
            Intent winner = new Intent(this, ResultsActivity.class);
            startActivity(winner);

        } else if (numberOfGuesses == MAX_GUESS_COUNT) {
            // Go to ResultsActivity, user he ran out chances
            Intent loser = new Intent(this, ResultsActivity.class);
            loser.putExtra(winningNumber, generatedNumber);
            startActivity(loser);

        } else if (userGuess < generatedNumber) {
            // Update clue TextView to say higher, set visibility to VISIBLE, set guess EditText to "" and increment numberOfGuesses by 1
            clue.setText(R.string.higher);
            clue.setVisibility(View.VISIBLE);
            guess.setText("");
            numberOfGuesses++;
        }else if (userGuess > generatedNumber) {
            //TODO Update clue TextView to say lower, set visibility to VISIBLE, set guess EditText to "" and increment numberOfGuesses by 1
            clue.setText(R.string.lower);
            clue.setVisibility(View.VISIBLE);
            guess.setText("");
            numberOfGuesses++;
        }

    }


    @Override
    public void onBackPressed() {
        //Removed super.onBackPressed(); to make sure if the back button is pressed nothing will happen.
    }
}

