package com.ambergleam.geoquizglass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * SurfaceHolder.Callback used to draw the duel on the timeline LiveCard.
 */
public class QuizDrawer implements SurfaceHolder.Callback {

	private static final String TAG = "com.ambergleam.geoquizglass.quizdrawer";

	private final QuizView mQuizView;
	private SurfaceHolder mHolder;

	public QuizDrawer(Context context) {
		mQuizView = new QuizView(context);
		mQuizView.setListener(new QuizView.ChangeListener() {
			@Override
			public void onChange() {
				draw(mQuizView);
			}
		});
		mQuizView.setForceStart(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Measure and layout the view with the canvas dimensions.
		int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
		int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
		// Alter the layout accordingly
		mQuizView.measure(measuredWidth, measuredHeight);
		mQuizView.layout(0, 0, mQuizView.getMeasuredWidth(), mQuizView.getMeasuredHeight());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "Surface created");
		mHolder = holder;
		mQuizView.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface destroyed");
		mQuizView.stop();
		mHolder = null;
	}

	/**
	 * Draws the view in the SurfaceHolder's canvas.
	 */
	private void draw(View view) {
		Canvas canvas;
		try {
			canvas = mHolder.lockCanvas();
		} catch (Exception e) {
			return;
		}
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);
			view.draw(canvas);
			mHolder.unlockCanvasAndPost(canvas);
		}
	}

}
