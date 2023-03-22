package com.uk.liley.simeon.musicquiz;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 * This is a quiz app that asks 6 questions
 */

public class MainActivity extends AppCompatActivity {

    boolean questionOneAnswer = false;
    boolean questionThreeAnswer = false;
    int submitAnswerButtonPress = 0;
    int scoreTotal = 0;
    MediaPlayer questionThreeSound;

    @Override
    protected void onPause() {
        super.onPause();
        questionThreeSound.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionThreeSound = MediaPlayer.create(this, R.raw.exerpt);
        Button play = (Button) findViewById(R.id.play_music_button);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                questionThreeSound.start();
            }
        });
    }


    /**
     * This method is called when the start button is clicked and starts the first question
     * Then the button submits tha answer for each question
     */
    public void startQuiz(View view) {
        //  Start quiz button is pressed
        if (submitAnswerButtonPress == 0) {
            int questionNo = goToFirstQuestion();
            submitAnswerButtonPress = questionNo;
            return;
        }
        //  Question 1:
        if (submitAnswerButtonPress == 1) {
            int answer1 = answerFirstQuestion();
            submitAnswerButtonPress += answer1;
            return;
        }
        //  Question 2:
        if (submitAnswerButtonPress == 2) {
            int answer2 = answerSecondQuestion();
            submitAnswerButtonPress += answer2;
            return;
        }
        //  Question 3:
        if (submitAnswerButtonPress == 3) {
            int answer3 = answerThirdQuestion();
            submitAnswerButtonPress += answer3;
            return;
        }
        //  Question 4:
        if (submitAnswerButtonPress == 4) {
            int answer4 = answerFourthQuestion();
            submitAnswerButtonPress += answer4;
            return;
        }
        //  Question 5:
        if (submitAnswerButtonPress == 5) {
            int answer5 = answerFifthQuestion();
            submitAnswerButtonPress = submitAnswerButtonPress - answer5;
        }
    }

    /**
     * This method is called when the start button is clicked and doese the setup for the question
     */
    private int goToFirstQuestion() {
        //make question 1 visible
        LinearLayout questionLayout = (LinearLayout) findViewById(R.id.question_text_layout);
        questionLayout.setVisibility(View.VISIBLE);
        //make question 1 answers visible
        LinearLayout question1Options = (LinearLayout) findViewById(R.id.question_1_radio_options);
        question1Options.setVisibility(View.VISIBLE);
        //Change button text to Submit
        Button starttosubmit = (Button) findViewById(R.id.submit_answer);
        starttosubmit.setText(R.string.submit_button_during);
        //Change Heading to current question number
        TextView question1MainHeadder = (TextView) findViewById(R.id.question_title_text);
        question1MainHeadder.setText(getString(R.string.q_1_question_main_headder_text));
        //change question headder to end of quiz
        TextView question1Text = (TextView) findViewById(R.id.question_text);
        question1Text.setText(R.string.q_1_question_text);
        //make sure this is visible on restart
        question1Text.setVisibility(View.VISIBLE);
        //change question headder to end of quiz congrats
        TextView question1SubHeadder = (TextView) findViewById(R.id.question_headder);
        question1SubHeadder.setText(R.string.q_1_question_sub_headder_text);
        //make final score invisible if this is a restart
        LinearLayout endOfQuiz = (LinearLayout) findViewById(R.id.end_of_quiz_layout);
        endOfQuiz.setVisibility(View.GONE);
        //make start headings invisible if this is the first game
        TextView startWelcomeHeadding = (TextView) findViewById(R.id.big_centre_message);
        startWelcomeHeadding.setVisibility(View.GONE);
        //make start sub headings invisible if this is the first game
        TextView startWelcomeSubHeadding = (TextView) findViewById(R.id.big_centre__subtitle_message);
        startWelcomeSubHeadding.setVisibility(View.GONE);
        //update score
        scoreTotal = 0;
        TextView scoreHeadder = (TextView) findViewById(R.id.score_tracker_text);
        scoreHeadder.setText(scoreTotal + R.string.score_default);
        return 1;
    }


    /**
     * This method is called when the Submit button is clicked to give answer to question 5
     */
    private int answerFifthQuestion() {
        //Check correct check boxes are checked
        CheckBox q5Box1 = (CheckBox) findViewById(R.id.q5_box1);
        boolean isBox1Checked = q5Box1.isChecked();
        CheckBox q5Box3 = (CheckBox) findViewById(R.id.q5_box3);
        boolean isBox3Checked = q5Box3.isChecked();
        CheckBox q5Box5 = (CheckBox) findViewById(R.id.q5_box5);
        boolean isBox5Checked = q5Box5.isChecked();
        CheckBox q5Box6 = (CheckBox) findViewById(R.id.q5_box6);
        boolean isBox6Checked = q5Box6.isChecked();
        //Check correct check boxes are not checked
        CheckBox q5Box2 = (CheckBox) findViewById(R.id.q5_box2);
        boolean isBox2Checked = q5Box2.isChecked();
        CheckBox q5Box4 = (CheckBox) findViewById(R.id.q5_box4);
        boolean isBox4Checked = q5Box4.isChecked();
        CheckBox q5Box7 = (CheckBox) findViewById(R.id.q5_box7);
        boolean isBox7Checked = q5Box7.isChecked();
        if (isBox1Checked && isBox3Checked && isBox5Checked && isBox6Checked) {
            if (isBox4Checked || isBox7Checked || isBox2Checked) {
                //fail            //incorrect answer toast
                this.wrongAnswerToast();
                return 0;
            } else { //only the correct answers ticked
                //win
                //correct answer toast
                Toast.makeText(this, R.string.q5_correct_toast, Toast.LENGTH_SHORT).show();
                //score updates
                scoreTotal = scoreTotal + 1;
                TextView scoreHeadder = (TextView) findViewById(R.id.score_tracker_text);
                String score = String.valueOf(scoreTotal);
                scoreHeadder.setText(score);
                //Question 5 is now gone
                this.question5ToFinalScore();
                // send back 5 to reset submitAnswerButton to 0
                return 5;
            }
        } else { //fail            //incorrect answer toast
            this.wrongAnswerToast();
            return 0;
        }
    }

    /**
     * This method is called when the Submit button is clicked to give answer to question 4
     */
    private int answerFourthQuestion() {
        EditText question4Answer = (EditText) findViewById(R.id.question_4_answer);
        String answer4 = question4Answer.getText().toString();
        boolean correct4 = false;
        if (Objects.equals(answer4, getString(R.string.q4_answer1))) {
            correct4 = true;
        }
        if (Objects.equals(answer4, getString(R.string.q4_answer2))){
            correct4 = true;
        }
        if (Objects.equals(answer4, getString(R.string.q4_answer3))) {
            correct4 = true;
        }
        if (Objects.equals(answer4, getString(R.string.q4_answer4))){
            correct4 = true;
        }
        if (correct4) {
            //correct answer toast
            Toast.makeText(this, R.string.q4_correct_toast, Toast.LENGTH_SHORT).show();
            //score updates
            scoreTotal = scoreTotal + 1;
            TextView scoreHeadder = (TextView) findViewById(R.id.score_tracker_text);
            String score = String.valueOf(scoreTotal);
            scoreHeadder.setText(score);
            //
            this.question4To5();
            return 1;
        } else {
            //incorrect answer toast
            this.wrongAnswerToast();
            return 0;
        }
    }

    /**
     * This method is called when the Submit button is clicked to give answer to question 3
     */
    private int answerThirdQuestion() {
        if (questionThreeAnswer) {
            //correct answer toast
            Toast.makeText(this, R.string.q4_correct_toast, Toast.LENGTH_SHORT).show();
            //score updates
            scoreTotal = scoreTotal + 1;
            TextView scoreHeadder = (TextView) findViewById(R.id.score_tracker_text);
            String score = String.valueOf(scoreTotal);
            scoreHeadder.setText(score);
            this.question3To4();
            return 1;
        } else {
            //incorrect answer toast
            this.wrongAnswerToast();
            return 0;
        }
    }

    //Question 3 Readio button options
    public void onRadioButton3Clicked(View view) {
        // Is the button now checked?
        boolean checked3 = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_q3_1:
                if (checked3)
                    // Wrong Answer
                    questionThreeAnswer = false;
                break;
            case R.id.radio_q3_2:
                if (checked3)
                    // Wrong answer
                    questionThreeAnswer = false;
                break;
            case R.id.radio_q3_3:
                if (checked3)
                    // Wrong Answer
                    questionThreeAnswer = false;
                break;
            case R.id.radio_q3_4:
                if (checked3)
                    // Correct Answer
                    questionThreeAnswer = true;
                break;
        }
    }

    /**
     * This method is called when the Submit button is clicked to give answer to question 2
     */
    private int answerSecondQuestion() {
        Spinner spinner = (Spinner) findViewById(R.id.question_2_spinner);
        int value = spinner.getSelectedItemPosition();
        //For correct answer value is 3
        if (value == 3) {
            //correct answer toast
            Toast.makeText(this, R.string.q3_correct_toast, Toast.LENGTH_SHORT).show();
            //score updates
            scoreTotal = scoreTotal + 1;
            TextView scoreHeadder = (TextView) findViewById(R.id.score_tracker_text);
            String score = String.valueOf(scoreTotal);
            scoreHeadder.setText(score);
            this.question2To3();
            return 1;
        } else {
            //incorrect answer toast
            this.wrongAnswerToast();
            return 0;
        }
    }

    /**
     * This method is called when the Submit button is clicked to give answer to question 1
     */
    private int answerFirstQuestion() {
        if (questionOneAnswer) {
            //correct answer toast
            Toast.makeText(this, R.string.q1_correct_toast, Toast.LENGTH_SHORT).show();
            //score updates
            scoreTotal = +1;
            TextView scoreHeadder = (TextView) findViewById(R.id.score_tracker_text);
            String score = String.valueOf(scoreTotal);
            scoreHeadder.setText(score);
            this.question1To2();
            return 1;
        } else {
            //incorrect answer toast
            this.wrongAnswerToast();
            return 0;
        }
    }

    //Question 1 Readio button options
    public void onRadioButton1Clicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_q1_1:
                if (checked)
                    // Wrong Answer
                    questionOneAnswer = false;
                break;
            case R.id.radio_q1_2:
                if (checked)
                    // Correct answer
                    questionOneAnswer = true;
                break;
            case R.id.radio_q1_3:
                if (checked)
                    // Wrong Answer
                    questionOneAnswer = false;
                break;
            case R.id.radio_q1_4:
                if (checked)
                    // Wrong Answer
                    questionOneAnswer = false;
                break;
        }
    }

    //Question 2 dropdown list options
    public void onQuestion2(View view) {
        // Spinner set up
        Spinner spinner = (Spinner) findViewById(R.id.question_2_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.question_2_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    //Press the hint button for a hint, one for each question & before and after quiz
    public void displayHint(View view) {
        //
        if (submitAnswerButtonPress == 0) {
            Toast.makeText(this, R.string.start_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 1:
        if (submitAnswerButtonPress == 1) {
            Toast.makeText(this, R.string.q1_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 2:
        if (submitAnswerButtonPress == 2) {
            Toast.makeText(this, R.string.q2_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 3:
        if (submitAnswerButtonPress == 3) {
            Toast.makeText(this, R.string.q3_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 4:
        if (submitAnswerButtonPress == 4) {
            Toast.makeText(this, R.string.q4_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 5:
        if (submitAnswerButtonPress == 5) {
            Toast.makeText(this, R.string.q5_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 5:
        if (submitAnswerButtonPress == 6) {
            Toast.makeText(this, R.string.final_score_hint_toast, Toast.LENGTH_SHORT).show();
        }
    }


    //Press the skip button to skip the question
    public void skip(View view) {
        //
        if (submitAnswerButtonPress == 0) {
            Toast.makeText(this, R.string.start_hint_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        //  Question 1:
        if (submitAnswerButtonPress == 1) {
            this.question1To2();
            submitAnswerButtonPress = 2;
            return;
        }
        //  Question 2:
        if (submitAnswerButtonPress == 2) {
            this.question2To3();
            submitAnswerButtonPress = 3;
            return;
        }
        //  Question 3:
        if (submitAnswerButtonPress == 3) {
            this.question3To4();
            submitAnswerButtonPress = 4;
            return;
        }
        //  Question 4:
        if (submitAnswerButtonPress == 4) {
            this.question4To5();
            submitAnswerButtonPress = 5;
            return;
        }
        //  Question 5:
        if (submitAnswerButtonPress == 5) {
            this.question5ToFinalScore();
            submitAnswerButtonPress = 0;
        }
    }


    //Actions to take the screen from question 1 to 2
    public void question1To2() {
        RadioGroup question1View = (RadioGroup) findViewById(R.id.question_1_radio_options);
        question1View.setVisibility(View.GONE);
        //change question headder to Question 2
        TextView question2MainHeadder = (TextView) findViewById(R.id.question_title_text);
        question2MainHeadder.setText(R.string.q_2_question_main_headder_text);
        //change question headder to Question 2
        TextView question2SubHeadder = (TextView) findViewById(R.id.question_headder);
        question2SubHeadder.setText(R.string.q_2_question_sub_headder_text);
        //change question text to Question 2
        TextView question2Text = (TextView) findViewById(R.id.question_text);
        question2Text.setText(R.string.q_2_question_text);
        LinearLayout question2Layout = (LinearLayout) findViewById(R.id.question_2_layout);
        question2Layout.setVisibility(View.VISIBLE);
        // Spinner set up
        Spinner spinner = (Spinner) findViewById(R.id.question_2_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.question_2_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    //Actions to take the screen from question 2 to 3
    public void question2To3() {
        LinearLayout question2Layout = (LinearLayout) findViewById(R.id.question_2_layout);
        question2Layout.setVisibility(View.GONE);
        //change question headder to Question 3
        TextView question3MainHeadder = (TextView) findViewById(R.id.question_title_text);
        question3MainHeadder.setText(R.string.q_3_question_main_headder_text);
        //change question headder to Question 3
        TextView question3SubHeadder = (TextView) findViewById(R.id.question_headder);
        question3SubHeadder.setText(R.string.q_2_question_sub_headder_text);
        //change question text to Question 3
        TextView question3Text = (TextView) findViewById(R.id.question_text);
        question3Text.setText(R.string.q_3_question_text);
        LinearLayout question3Layout = (LinearLayout) findViewById(R.id.question_3_layout);
        question3Layout.setVisibility(View.VISIBLE);
    }

    //Actions to take the screen from question 3 to 4
    public void question3To4() {
        //Question 3 is now gone
        LinearLayout question3Layout = (LinearLayout) findViewById(R.id.question_3_layout);
        question3Layout.setVisibility(View.GONE);
        //change question headder to Question 4
        TextView question4MainHeadder = (TextView) findViewById(R.id.question_title_text);
        question4MainHeadder.setText(R.string.q_4_question_main_headder_text);
        //change question headder to Question 4
        TextView question4SubHeadder = (TextView) findViewById(R.id.question_headder);
        question4SubHeadder.setText(R.string.q_4_question_sub_headder_text);
        //change question text to Question 4
        TextView question4Text = (TextView) findViewById(R.id.question_text);
        question4Text.setText(R.string.q_4_question_text);
        LinearLayout question4Layout = (LinearLayout) findViewById(R.id.question_4_layout);
        question4Layout.setVisibility(View.VISIBLE);
    }

    //Actions to take the screen from question 4 to 5
    public void question4To5() {
        //Question 4 is now gone
        LinearLayout question4Layout = (LinearLayout) findViewById(R.id.question_4_layout);
        question4Layout.setVisibility(View.GONE);
        //change question headder to Question 5
        TextView question5MainHeadder = (TextView) findViewById(R.id.question_title_text);
        question5MainHeadder.setText(R.string.q_5_question_main_headder_text);
        //change question headder to Question 5
        TextView question5SubHeadder = (TextView) findViewById(R.id.question_headder);
        question5SubHeadder.setText(R.string.q_5_question_sub_headder_text);
        //change question text to Question 5
        TextView question5Text = (TextView) findViewById(R.id.question_text);
        question5Text.setText(R.string.q_5_question_text);
        LinearLayout question5Layout = (LinearLayout) findViewById(R.id.question_5_layout);
        question5Layout.setVisibility(View.VISIBLE);
    }


    //Actions to take the screen from question 5 to the final score
    public void question5ToFinalScore() {
        //Question 5 is now gone
        LinearLayout question5Layout = (LinearLayout) findViewById(R.id.question_5_layout);
        question5Layout.setVisibility(View.GONE);
        //change question headder to end of quiz
        TextView endQuizMainHeadder = (TextView) findViewById(R.id.question_title_text);
        endQuizMainHeadder.setText(R.string.end_quiz_main_headder_text);
        //change question headder to end of quiz congrats
        TextView endQuizSubHeadder = (TextView) findViewById(R.id.question_headder);
        endQuizSubHeadder.setText(R.string.end_quiz_sub_headder_text);
        //remove text
        TextView question2Text = (TextView) findViewById(R.id.question_text);
        question2Text.setVisibility(View.GONE);
        //change final score for next view
        TextView finalScore = (TextView) findViewById(R.id.final_score);
        String score = String.valueOf(scoreTotal);
        Log.v("MainActivity",score);
        finalScore.setText(score);
        //Change button text to Submit
        Button starttosubmit = (Button) findViewById(R.id.submit_answer);
        starttosubmit.setText(R.string.submit_button_end);
        //change view to final score
        LinearLayout endOfQuiz = (LinearLayout) findViewById(R.id.end_of_quiz_layout);
        endOfQuiz.setVisibility(View.VISIBLE);
    }

    public void wrongAnswerToast() {
        Toast.makeText(this, R.string.try_again_toast, Toast.LENGTH_SHORT).show();
    }


}