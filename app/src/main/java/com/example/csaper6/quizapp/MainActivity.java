package com.example.csaper6.quizapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

public class MainActivity extends AppCompatActivity {

    private Button trueButton, falseButton, nextButton;
    private TextView questionTextView, pointText;
    private int points;

    private MovieQuestionFactory questionDB;
    private Question currentQuestion;

    private ViewGroup transitionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_app);

        questionDB = new MovieQuestionFactory();
        currentQuestion = questionDB.nextQuestion();

        //1. Wire the buttons & the textview
        trueButton = (Button) findViewById(R.id.button_true);
        falseButton = (Button) findViewById(R.id.button_false);
        nextButton = (Button) findViewById(R.id.button_next);
        questionTextView = (TextView) findViewById(R.id.textview_question);
        points = 0;
        pointText = (TextView) findViewById(R.id.point_text);
        transitionContainer = (ViewGroup) findViewById(R.id.transition_container);

        //2. Create a new Question object from
        //   the String resources
        //   Make a Question object & pass the string resource
        //   & answer in the constructor
        //Question q1 = new Question(R.string.question_cats,true);

        //3. Set the textView's text to the Question's tex
        questionTextView.setText(currentQuestion.getQuestion());

        //4. Make a View.OnClickListener for each button
        //using the anonymous inner class way of doing things
        //Inside each button, call the question's checkAnswer method
        //and make an appropriate toast.
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                falseButton.setEnabled(false);
                trueButton.setEnabled(false);
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                falseButton.setEnabled(true);
                trueButton.setEnabled(true);
                updateQuestion();
            }
        });


        //cheatButton.setVisibility();
    }

    private void updateQuestion() {
        currentQuestion = questionDB.nextQuestion();
        TransitionManager.beginDelayedTransition(transitionContainer, new Recolor());
        transitionContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlank));
        TransitionManager.beginDelayedTransition(transitionContainer,
                new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));
        questionTextView.setText(currentQuestion.getQuestion());
    }

    private void checkAnswer(boolean userResponse) {
        TransitionManager.beginDelayedTransition(transitionContainer, new Recolor());
        if(currentQuestion.checkAnswer(userResponse)) {
            Toast.makeText(MainActivity.this,
                    R.string.toast_correct,
                    Toast.LENGTH_SHORT).show();
            points++;
            pointText.setText(R.string.point_text);
            pointText.append("" + points);
            transitionContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRight));
        } else {
            Toast.makeText(MainActivity.this,
                    R.string.toast_incorrect,
                    Toast.LENGTH_SHORT).show();
            points--;
            pointText.setText(R.string.point_text);
            pointText.append("" + points);
            transitionContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWrong));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cheat:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}