//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rps.sentes.rps;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Game> {
    public HistoryAdapter(Activity act, ArrayList<Game> games) {
        super(act, 0, games);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        Game currentGame = (Game)this.getItem(position);
        View listItemView = convertView;
        if(convertView == null) {
            listItemView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView aiPick = (TextView)listItemView.findViewById(R.id.item_aipick);
        aiPick.setText(currentGame.getAi_pick());
        TextView playerPick = (TextView)listItemView.findViewById(R.id.item_playerPick);
        playerPick.setText(currentGame.getPlayer_pick());
        TextView result = (TextView)listItemView.findViewById(R.id.item_result);
        result.setText(currentGame.getResult());
        if(currentGame.getResult().equals("WIN")) {
            result.setBackgroundColor(Color.GREEN);
        } else if(currentGame.getResult().equals("DRAW")) {
            result.setBackgroundColor(Color.YELLOW);
        } else {
            result.setBackgroundColor(Color.RED);
        }

        return listItemView;
    }
}
