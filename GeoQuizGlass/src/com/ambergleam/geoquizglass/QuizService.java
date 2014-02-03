package com.ambergleam.geoquizglass;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;
import com.google.android.glass.timeline.TimelineManager;

public class QuizService extends Service {

	private static final String TAG = "com.ambergleam.geoquizglass.quizservice";

	private TimelineManager mTimelineManager;
	private LiveCard mLiveCard;
	private QuizDrawer mCallback;

	public static String mQuestionText, mResultText;
	public static int mCurrentIndex = 0;
	public static Question[] mQuestionBank = new Question[] {
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_turkey, false) };

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate(...) called");
		mTimelineManager = TimelineManager.from(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mLiveCard == null) {
			Log.d(TAG, "Publishing LiveCard");
			mLiveCard = mTimelineManager.createLiveCard(TAG);

			// Keep track of the callback to remove it before unpublishing.
			mCallback = new QuizDrawer(this);
			mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder()
					.addCallback(mCallback);

			Intent menuIntent = new Intent(this, MenuActivity.class);
			menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent,
					0));

			mLiveCard.publish(PublishMode.REVEAL);
			Log.d(TAG, "Done publishing LiveCard");
		} else {
			// TODO(alainv): Jump to the LiveCard when API is available.
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (mLiveCard != null && mLiveCard.isPublished()) {
			Log.d(TAG, "Unpublishing LiveCard");
			if (mCallback != null) {
				mLiveCard.getSurfaceHolder().removeCallback(mCallback);
			}
			mLiveCard.unpublish();
			mLiveCard = null;
		}
		super.onDestroy();
	}
	
	public static void checkAnswer(Context context, boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		if (userPressedTrue == answerIsTrue) {
			mResultText = context.getString(R.string.result_correct);
			
		} else {
			mResultText = context.getString(R.string.result_incorrect);
		}
	}

	public static void updateQuestion(Context context) {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionText = context.getString(question);
	}

}
