package com.example.baseballgame;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.baseballgame.adapters.ChatAdapter;
import com.example.baseballgame.databinding.ActivityMainBinding;
import com.example.baseballgame.datas.Chat;

import java.util.List;

public class MainActivity extends BaseActivity {

    int[] computerExamList = new int[3];

    ActivityMainBinding act;

    List<Chat> chatList;

    ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setValues();
        setBinding();
        setEvnet();
    }

    void chkNum(int chkNum){

        int[] chkNums = new int[]{ (int)chkNum / 100, (int)chkNum / 10 % 10, (int)chkNum % 10 };

        int s = 0;
        int b = 0;

        for (int ei = 0 ; ei <= computerExamList.length  ; ei++ ){

            int eNum = computerExamList[ei];

            for (int ci = 0 ; ci <= computerExamList.length  ; ci++ ){
                if(eNum == computerExamList[ci]){
                    if(ei == ci){
                        s++;
                    }else{
                        b++;
                    }
                }
            }
        }

        if(s == 3){
            //Toast.makeText(this, "정답입니다. 축하합니다.", Toast.LENGTH_SHORT).show();
            chatList.add(new Chat(false, "정답입니다. 축하합니다." ));

        }else{
            Toast.makeText(this, String.format("%dS, %sB ", s, b), Toast.LENGTH_SHORT).show();
            chatList.add(new Chat(false,String.format("%dS, %sB ", s, b) ));
        }

        mChatAdapter.notifyDataSetChanged();
        act.messageListView.smoothScrollToPosition(chatList.size() - 1);
    }

    void makeExam(){
        int radomNumber = (int)Math.random() * 899 + 100;

        int[] tmpNum = new int[3];

        tmpNum[0] = radomNumber/ 100;
        tmpNum[1] = radomNumber/ 10 % 10;
        tmpNum[2] = radomNumber % 10;

//        boolean isDupOlk = true;
        //조건

//        boolean isDupOlk = true;

        computerExamList[0] = makeRandom();
        computerExamList[1] = makeRandom();
        computerExamList[2] = makeRandom();

        Log.d("정답", computerExamList.toString());
    }

    public int makeRandom(){

        int tmpNum = (int)(Math.random() * 89 + 10) /10;
        boolean isPass = true;
        for (int examNum : computerExamList){
            if(tmpNum == examNum){
                isPass = false;
            }
        }
        if(isPass){
            return tmpNum;
        }else{
            return makeRandom();
        }
    }

    @Override
    public void setValues() {

        makeExam();

        mChatAdapter = new ChatAdapter(this, chatList);
        act.messageListView.setAdapter(mChatAdapter);

    }

    @Override
    public void setBinding() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void setEvnet() {
        act.inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatList.add(new Chat(true,act.inputEdt.getText().toString() ));
                chkNum(Integer.getInteger(act.inputEdt.getText().toString()));
            }
        });
    }
}
