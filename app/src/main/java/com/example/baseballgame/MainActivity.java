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
                //유저가 입력 값 등록
                chatList.add(new Chat(true, act.userInputEdt.getText().toString()));

                mChatAdapter.notifyDataSetChanged();
                act.messageListView.smoothScrollToPosition(chatList.size()-1);

                //정답 확인 로직 호출
                checkStrikeAndBalls();
            }
        });
    }

    void checkStrikeAndBalls() {

        //유저가 입력값 배열
        int[] userInputArray = new int[3];
        // 유저 입력 값
        int number = Integer.parseInt(act.userInputEdt.getText().toString());

        // 유저 입력값 배열에 담는작업
        userInputArray[0] = number / 100;
        userInputArray[1] = number / 10 % 10;
        userInputArray[2] = number % 10;

        int strikeCount = 0;
        int ballCount = 0;

        // 정답 확인 로직
        for (int i=0 ; i < 3 ; i++) {
            for (int j=0 ; j < 3 ; j++) {
                //숫자 존재 확인
                if (userInputArray[i] == computerExamArray[j]) {
                    //숫자 존재 & 위치 확인
                    if (i == j) {
                        strikeCount++;
                    }
                    else {
                        ballCount++;
                    }
                }
            }
        }

        //run 에 들어갈경우 final 처리 필요
        final int strikeFinalCount = strikeCount;
        final int ballFinalCount = ballCount;

        if (strikeCount == 3) {
            //모두 마출 경우 축하 메세지 등록
//            Toast.makeText(mContext, "정답입니다! 축하합니다!", Toast.LENGTH_SHORT).show();
            chatList.add(new Chat(false,  String.format("정답입니다! 축하합니다! %s 번에 마추었습니다.", (int)(chatList.size()/2  + 1) ) ) );

        }
        else {
            //몇개 마추었는지 메세지 등록
//            Toast.makeText(mContext, String.format("%dS, %dB 입니다.", strikeCount, ballCount), Toast.LENGTH_SHORT).show();
            chatList.add(new Chat(false, String.format("%dS, %dB 입니다.", strikeFinalCount, ballFinalCount)));
        }

        // 사용자 입력 창 초기화
        act.userInputEdt.setText("");

        // 일부로 딜레이 주어 컴퓨터가 계산하고 있느 척 하기
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //리스트 뷰 채인치 처리
                mChatAdapter.notifyDataSetChanged();

                //리스트 뷰 맨 아래로 위치 변경
                act.messageListView.smoothScrollToPosition(chatList.size()-1);

            }
        }, 800);
    }

    @Override
    public void setValues() {
        makeExam();

        mChatAdapter = new ChatAdapter(mContext, chatList);
        act.messageListView.setAdapter(mChatAdapter);

    }

    // 램덤 숫자 생성
    void makeExam() {

        // 중복 0 없는 경우에 탈출 예정
        while (true) {

            // 랜덤 숫자 생성
            int randomNumber = (int) (Math.random() * 899 + 100); // Ex. 747

            // 랜덤 숫자 담을 배열
            int[] tempNumber = new int[3];

            tempNumber[0] = randomNumber / 100;
            tempNumber[1] = randomNumber / 10 % 10;
            tempNumber[2] = randomNumber % 10;

            // 중복 값 존재 여부 확인
            boolean isDuplOk = true;

            if (tempNumber[0] == tempNumber[1] || tempNumber[1] == tempNumber[2] || tempNumber[0] == tempNumber[2]) {
                isDuplOk = false;
            }

            // 0 있는지 확인
            boolean isZeroOk = true;

            if (tempNumber[0] == 0 || tempNumber[1] == 0 || tempNumber[2] == 0) {
                isZeroOk = false;
            }

            // 중복 0 없는 경우에 탈출!!
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