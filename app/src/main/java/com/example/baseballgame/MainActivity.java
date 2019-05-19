package com.example.baseballgame;


import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.baseballgame.adapters.ChatAdapter;
import com.example.baseballgame.databinding.ActivityMainBinding;
import com.example.baseballgame.datas.Chat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    int[] computerExamArray = new int[3];

    List<Chat> chatList = new ArrayList<>();
    ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        act.inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chatList.add(new Chat(true, act.userInputEdt.getText().toString()));
                mChatAdapter.notifyDataSetChanged();
                act.messageListView.smoothScrollToPosition(chatList.size()-1);
                checkStrikeAndBalls();
            }
        });
    }

    void checkStrikeAndBalls() {

        int[] userInputArray = new int[3];
        int number = Integer.parseInt(act.userInputEdt.getText().toString());

        userInputArray[0] = number / 100;
        userInputArray[1] = number / 10 % 10;
        userInputArray[2] = number % 10;

        int strikeCount = 0;
        int ballCount = 0;

        for (int i=0 ; i < 3 ; i++) {
            for (int j=0 ; j < 3 ; j++) {

                if (userInputArray[i] == computerExamArray[j]) {
                    if (i == j) {
                        strikeCount++;
                    }
                    else {
                        ballCount++;
                    }
                }
            }
        }

        final int strikeFinalCount = strikeCount;
        final int ballFinalCount = ballCount;

        if (strikeCount == 3) {
//            Toast.makeText(mContext, "정답입니다! 축하합니다!", Toast.LENGTH_SHORT).show();
            chatList.add(new Chat(false,  String.format("정답입니다! 축하합니다! %s 번에 마추었습니다.", (int)(chatList.size()/2  + 1) ) ) );

        }
        else {
//            Toast.makeText(mContext, String.format("%dS, %dB 입니다.", strikeCount, ballCount), Toast.LENGTH_SHORT).show();
            chatList.add(new Chat(false, String.format("%dS, %dB 입니다.", strikeFinalCount, ballFinalCount)));
        }

        act.userInputEdt.setText("");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mChatAdapter.notifyDataSetChanged();
                act.messageListView.smoothScrollToPosition(chatList.size()-1);

            }
        }, 1000);
    }

    @Override
    public void setValues() {
        makeExam();

        mChatAdapter = new ChatAdapter(mContext, chatList);
        act.messageListView.setAdapter(mChatAdapter);

    }

    void makeExam() {

        while (true) {
            int randomNumber = (int) (Math.random() * 899 + 100); // Ex. 747

            int[] tempNumber = new int[3];

            tempNumber[0] = randomNumber / 100;
            tempNumber[1] = randomNumber / 10 % 10;
            tempNumber[2] = randomNumber % 10;

            boolean isDuplOk = true;
            if (tempNumber[0] == tempNumber[1] || tempNumber[1] == tempNumber[2] || tempNumber[0] == tempNumber[2]) {
                isDuplOk = false;
            }

            boolean isZeroOk = true;
            if (tempNumber[0] == 0 || tempNumber[1] == 0 || tempNumber[2] == 0) {
                isZeroOk = false;
            }

            if (isDuplOk && isZeroOk) {
                computerExamArray[0] = tempNumber[0];
                computerExamArray[1] = tempNumber[1];
                computerExamArray[2] = tempNumber[2];

                Log.d("정답 숫자", randomNumber+" 입니다.");

                break;
            }

        }


    }


    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}