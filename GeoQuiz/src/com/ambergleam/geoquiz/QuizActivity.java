package com.ambergleam.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private static final String TAG = "com.ambergleam.geoquiz";
	private static final String KEY_INDEX = "index";
	
	private Button mTrueButton, mFalseButton;
	private ImageButton mPreviousButton, mNextButton;
	private TextView mQuestionTextView;

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_turkey, false) };

	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate(...) called");
		
		// Check for saved state
		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		}
		
		setContentView(R.layout.activity_quiz);

		mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
		updateQuestion();

		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex - 1);
				if (mCurrentIndex < 0) {
					mCurrentIndex = mQuestionBank.length-1;
				}
				updateQuestion();
			}
		});
		
		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});

	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		if (userPressedTrue == answerIsTrue) {
			messageResId = R.string.correct_toast;
		} else {
			messageResId = R.string.incorrect_toast;
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
	};
	
	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onStart(...) called");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume(...) called");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause(...) called");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop(...) called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy(...) called");
	}

}
