package com.openclassrooms.topquizz.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        // Shuffle the question list before storing it
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
        mNextQuestionIndex=0;
    }

    public Question getCurrentQuestion() {
        int mCurrentQuestionIndex;
        if(mNextQuestionIndex==0){
            mCurrentQuestionIndex=mQuestionList.size()-1;
        }
        else{
            mCurrentQuestionIndex=mNextQuestionIndex-1;
        }

        return mQuestionList.get(mCurrentQuestionIndex);
    }

    public Question getNextQuestion() {
        // Loop over the questions and return a new one at each call
        if(mNextQuestionIndex == mQuestionList.size()){
            mNextQuestionIndex=0;
        }
        return mQuestionList.get(mNextQuestionIndex++);
    }

}