//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rps.sentes.rps;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private AiDbHelper mDbHelper;
    private ListView mListView;

    public HistoryActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_history);
        this.mListView = (ListView)this.findViewById(R.id.list);
        this.mDbHelper = new AiDbHelper(this);
        ArrayList games = new ArrayList();
        SQLiteDatabase db = this.mDbHelper.getReadableDatabase();
        String[] projection = new String[]{"_id", "PLAYER_PICK", "AI_PICK", "RESULT"};
        Cursor cursor = db.query("aidb", projection, (String)null, (String[])null, (String)null, (String)null, (String)null);
        Cursor mCount = db.rawQuery("select count(*) from aidb where result=\'" + Results.WIN.toString() + "\'", (String[])null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);

        try {
            while(cursor.moveToNext()) {
                int ha = cursor.getColumnIndex("_id");
                int nameColumnIndex = cursor.getColumnIndex("PLAYER_PICK");
                int breedColumnIndex = cursor.getColumnIndex("AI_PICK");
                int weightColumnIndex = cursor.getColumnIndex("RESULT");
                int currentID = cursor.getInt(ha);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                String currentWeight = cursor.getString(weightColumnIndex);
                games.add(new Game(currentID, currentName, currentBreed, currentWeight));
            }
        } finally {
            cursor.close();
        }

        HistoryAdapter ha1 = new HistoryAdapter(this, games);
        if(this.mListView != null) {
            this.mListView.setAdapter(ha1);
        }

    }
}
