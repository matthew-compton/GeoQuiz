package com.ambergleam.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.ambergleam.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_IS_SHOWN = "com.ambergleam.geoquiz.answer_is_shown";
	
	private boolean mAnswerIsTrue;
	
	private TextView mAnswerTextView;
	private Button mShowAnswer, mBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView) findViewById(R.id.textViewAnswer);
	
		setAnswerShownResult(false);
		
		mShowAnswer = (Button) findViewById(R.id.buttonShowAnswer);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.button_true);
				} else {
					mAnswerTextView.setText(R.string.button_false);
				}
				setAnswerShownResult(true);
			}
		});
		
		mBack = (Button) findViewById(R.id.buttonBack);
		mBack.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}

}
