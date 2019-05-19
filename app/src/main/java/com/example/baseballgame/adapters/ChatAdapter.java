package com.example.baseballgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.baseballgame.R;
import com.example.baseballgame.datas.Chat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {

    Context mContext;
    List<Chat> mList;
    LayoutInflater inf;

    public ChatAdapter(Context context, List<Chat> list) {
        super(context, R.layout.chat_list_item, list);

        mContext = context;
        mList = list;
        inf = LayoutInflater.from(mContext);

    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {

        if (row == null) {
            row = inf.inflate(R.layout.chat_list_item, null);
        }

        Chat data = mList.get(position);

        FrameLayout userMsgFrameLayout = row.findViewById(R.id.userMsgFrameLayout);
        FrameLayout computerMsgFrameLayout = row.findViewById(R.id.computerMsgFrameLayout);
        TextView userMessageTxt = row.findViewById(R.id.userMessageTxt);
        TextView computerMessageTxt = row.findViewById(R.id.computerMessageTxt);


        if (data.userSalse) {
            userMsgFrameLayout.setVisibility(View.VISIBLE);
            computerMsgFrameLayout.setVisibility(View.GONE);

            userMessageTxt.setText(data.msg);

        }
        else {

            userMsgFrameLayout.setVisibility(View.GONE);
            computerMsgFrameLayout.setVisibility(View.VISIBLE);

            computerMessageTxt.setText(data.msg);

        }

        return row;
    }
}