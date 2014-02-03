package com.ambergleam.geoquizglass;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizView extends RelativeLayout {

	private TextView mQuestionTextView, mResultTextView;

	private boolean mStarted;
	private boolean mForceStart;
	private boolean mVisible;
	private boolean mRunning;

	private static final long DELAY_MILLIS = 500;

	private ChangeListener mChangeListener;

	public QuizView(Context context) {
		this(context, null, 0);
	}

	public QuizView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QuizView(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		LayoutInflater.from(context).inflate(R.layout.layout_quiz, this);
		mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
		mResultTextView = (TextView) findViewById(R.id.resultTextView);
	}

	/**
	 * Interface to listen for changes on the view layout.
	 */
	public interface ChangeListener {
		/** Notified of a change in the view. */
		public void onChange();
	}

	/**
	 * Set a {@link ChangeListener}.
	 */
	public void setListener(ChangeListener listener) {
		mChangeListener = listener;
	}

	/**
	 * Set whether or not to force the start of the duel when a window has not been attached
	 * to the view.
	 */
	public void setForceStart(boolean forceStart) {
		mForceStart = forceStart;
		updateRunning();
	}

	/**
	 * Start the quiz.
	 */
	public void start() {
		mStarted = true;
		updateRunning();
	}

	/**
	 * Stop the quiz.
	 */
	public void stop() {
		mStarted = false;
		updateRunning();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mVisible = false;
		updateRunning();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = (visibility == VISIBLE);
		updateRunning();
	}

	private final Handler mHandler = new Handler();

	private final Runnable mUpdateTextRunnable = new Runnable() {
		@Override
		public void run() {
			if (mRunning) {
				updateText();
				mHandler.postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
			}
		}
	};

	/**
	 * Update the running state of the quiz.
	 */
	private void updateRunning() {
		boolean running = (mVisible || mForceStart) && mStarted;
		if (running != mRunning) {
			if (running) {
				mHandler.post(mUpdateTextRunnable);
			} else {
				mHandler.removeCallbacks(mUpdateTextRunnable);
			}
			mRunning = running;
		}
	}

	/**
	 * Update the state of the duel.
	 */
	private void updateText() {
		mQuestionTextView.setText(String.valueOf(QuizService.mQuestionText));
		mResultTextView.setText(String.valueOf(QuizService.mResultText));
		if (mChangeListener != null) {
			mChangeListener.onChange();
		}
	}
}
