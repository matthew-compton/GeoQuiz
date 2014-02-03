package com.ambergleam.geoquiz;

public class Question {

	private int mQuestion;
	private boolean mTrueQuestion;
	
	public Question(int question, boolean trueQuestion) {
		mQuestion = question;
		mTrueQuestion = trueQuestion;
	}
	
	public int getQuestion() {
		return mQuestion;
	}

	public void setQuestion(int question) {
		this.mQuestion = question;
	}

	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}
	
	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
	
}
