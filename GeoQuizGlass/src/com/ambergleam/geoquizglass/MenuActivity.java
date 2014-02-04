package com.ambergleam.geoquizglass;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Activity showing the options menu.
 */
public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
            case R.id.menu_true:
            	QuizActivity.checkAnswer(true);
                return true;
            case R.id.menu_false:
            	QuizActivity.checkAnswer(false);
                return true;
            case R.id.menu_next:
            	QuizActivity.mCurrentIndex = (QuizActivity.mCurrentIndex + 1) % QuizActivity.mQuestionBank.length;
            	QuizActivity.updateQuestion();
                return true;
            case R.id.menu_previous:
            	QuizActivity.mCurrentIndex = (QuizActivity.mCurrentIndex - 1);
				if (QuizActivity.mCurrentIndex < 0) {
					QuizActivity.mCurrentIndex = QuizActivity.mQuestionBank.length-1;
				}
				QuizActivity.updateQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // Nothing else to do, closing the Activity.
        finish();
    }
}
