package com.ambergleam.geoquizglass;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class QuizActivity extends Activity {

	private static final String TAG = "com.ambergleam.geoquizglass";
	
	public static TextView mQuestionTextView;

	public static TextView mResultTextView;
	public static String mQuestionText;

	public static String mResultText;
	
	public static int mCurrentIndex = 0;
	public static Question[] mQuestionBank = new Question[] {
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_turkey, false) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate(...) called");
		setContentView(R.layout.layout_quiz);

		mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
		mResultTextView = (TextView) findViewById(R.id.resultTextView);
		
		updateQuestion();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		
	    return true;
	}
	
	public static void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		if (userPressedTrue == answerIsTrue) {
			mResultText = "Correct!";
		} else {
			mResultText = "Incorrect!";
		}
		updateUI();
	}
	
	public static void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionText = ((Integer)question).toString();
		mResultText = "";
		updateUI();
	}
	
	private static void updateUI() {
		mQuestionTextView.setText(String.valueOf(mQuestionText));
		mResultTextView.setText(String.valueOf(mResultText));
	}

	
}
