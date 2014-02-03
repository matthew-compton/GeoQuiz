package com.ambergleam.geoquiz;

import android.app.Activity;
import android.content.Intent;
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
	private static final String KEY_CHEATER = "cheater";

	private Button mTrueButton, mFalseButton, mCheatButton;
	private ImageButton mPreviousButton, mNextButton;
	private TextView mQuestionTextView, mQuestionNumberTextView;

	private Question[] mQuestionBank = new Question[] {
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_turkey, false) };

	private int mCurrentIndex = 0;
	private boolean mIsCheater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate(...) called");

		// Check for saved state
		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
		}

		setContentView(R.layout.layout_quiz);

		mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
		mQuestionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
		
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
					mCurrentIndex = mQuestionBank.length - 1;
				}
				mIsCheater = false;
				updateQuestion();
			}
		});

		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});

		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Start CheatActivity
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex]
						.isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i, 0);
			}
		});

		updateQuestion();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN,
				false);
	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

		int messageResId = 0;

		if (mIsCheater) {
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.toast_judgment;
			} else {
				messageResId = R.string.toast_major_judgment;
			}
		} else {
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.toast_correct;
			} else {
				messageResId = R.string.toast_incorrect;
			}
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
		String questionNumber = (mCurrentIndex+1)+"/"+mQuestionBank.length;
		mQuestionNumberTextView.setText(questionNumber);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
		savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
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
