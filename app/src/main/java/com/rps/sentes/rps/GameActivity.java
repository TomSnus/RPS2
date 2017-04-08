

package com.rps.sentes.rps;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    public static Categories[] categories;
    private TextView resultView;
    private TextView playerpickView;
    private TextView aipickView;
    private TextView winpercentageView;
    private AiDbHelper mDbHelper;
    private List<Game> playedGames;
    private ImageView playerPickImage;
    private ImageView aiPickImage;
    private TextView resultViewBig;
    public GameActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        //Init Picks
        final Pick paper = new Pick("PAPER", R.drawable.paper);
        final Pick rock = new Pick("ROCK", R.drawable.rock);
        final Pick scissors = new Pick("SCISSORS", R.drawable.scissors);

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_game);
        ImageButton btn_paper = (ImageButton)this.findViewById(R.id.btn_paper);
        ImageButton btn_rock = (ImageButton)this.findViewById(R.id.btn_rock);
        ImageButton btn_scissors = (ImageButton)this.findViewById(R.id.btn_scissors);
        this.resultView = (TextView)this.findViewById(R.id.tv_result);
        this.playerpickView = (TextView)this.findViewById(R.id.tv_playerpick);
        this.aipickView = (TextView)this.findViewById(R.id.tv_aipick);
        this.winpercentageView = (TextView)this.findViewById(R.id.tv_winpercentage);
        this.mDbHelper = new AiDbHelper(this);
        this.playerPickImage = (ImageView) findViewById(R.id.image_playerpick);
        this.aiPickImage = (ImageView) findViewById(R.id.image_aipick);
        this.resultViewBig = (TextView) findViewById(R.id.tv_result_big);

        this.playerPickImage.setVisibility(View.GONE);
        this.aiPickImage.setVisibility(View.GONE);
        this.resultViewBig.setVisibility(View.GONE);

        this.fillList();
        this.displayDatabaseInfo();
        btn_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            calculateResult(paper);
            }
        });
        btn_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(rock);
            }
        });
        btn_scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(scissors);
            }
        });
    }

    private void fillList() {
        if(this.playedGames == null) {
            this.playedGames = new ArrayList();
        }

        SQLiteDatabase db = this.mDbHelper.getReadableDatabase();
        String[] projection = new String[]{"_id", "PLAYER_PICK", "AI_PICK", "RESULT"};
        Cursor cursor = db.query("aidb", projection, (String)null, (String[])null, (String)null, (String)null, (String)null);

        try {
            while(cursor.moveToNext()) {
                int idColumnIndex = cursor.getColumnIndex("_id");
                int playerPickColumnIndex = cursor.getColumnIndex("PLAYER_PICK");
                int aiPickColumnIndex = cursor.getColumnIndex("AI_PICK");
                int resultColumnIndex = cursor.getColumnIndex("RESULT");
                int currentID = cursor.getInt(idColumnIndex);
                String currentPlayerPick = cursor.getString(playerPickColumnIndex);
                String currentAiPick = cursor.getString(aiPickColumnIndex);
                String currentResult = cursor.getString(resultColumnIndex);
                this.playedGames.add(0, new Game(currentID, currentPlayerPick, currentAiPick, currentResult));
            }
        } finally {
            cursor.close();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_insert_dummy_data:
                Intent i = new Intent(this, HistoryActivity.class);
                this.startActivity(i);
                return true;
            case R.id.action_delete_all_entries:
                this.resetDatabase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetDatabase() {
        SQLiteDatabase db = this.mDbHelper.getReadableDatabase();
        db.delete("aidb", (String)null, (String[])null);
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = this.mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select count(*) from aidb where result = \'WIN\'", (String[])null);
        c.moveToNext();
        int wins = c.getInt(0);
        Cursor cursorTotal = db.rawQuery("Select count(*) from aidb", (String[])null);
        cursorTotal.moveToNext();
        int total = cursorTotal.getInt(0);
        double winpercentage = (double)wins / (double)total * 100.0D;
        this.winpercentageView.setText("Win percentage: \n" + Math.round(winpercentage) + "%");
    }

    private void calculateResult(Pick pick) {
        Results result = Results.DRAW;
        Pick aiPick = AI.calculate(this.playedGames);
        if(pick.isPaper()) {
            if(aiPick.isPaper()) {
                result = Results.DRAW;
            } else if(aiPick.isRock()) {
                result = Results.WIN;
            } else {
                result = Results.LOSS;
            }
        } else if(pick.isRock()) {
            if(aiPick.isPaper()) {
                result = Results.LOSS;
            } else if(aiPick.isRock()) {
                result = Results.DRAW;
            } else {
                result = Results.WIN;
            }
        } else if(pick.isScissors()) {
            if (aiPick.isScissors()) {
                result = Results.DRAW;
            } else if (aiPick.isRock()) {
                result = Results.LOSS;
            } else {
                result = Results.WIN;
            }
        }
        switch(result) {
            case DRAW:
                this.resultView.setText("Draw");
                this.resultViewBig.setText("Draw");
                this.resultViewBig.setTextColor(Color.YELLOW);
                break;
            case LOSS:
                this.resultView.setText("Loss");
                this.resultViewBig.setText("Loss");
                this.resultViewBig.setTextColor(Color.RED);
                break;
            case WIN:
                this.resultView.setText("Win");
                this.resultViewBig.setText("Win");
                this.resultViewBig.setTextColor(Color.GREEN);
                break;
        }

        this.insertResult(pick, aiPick, result);
        this.playerpickView.setText(pick.getName());
        this.aipickView.setText(aiPick.getName());
        this.aiPickImage.setImageResource(aiPick.getResource());
        this.playerPickImage.setImageResource(pick.getResource());
        this.aiPickImage.refreshDrawableState();


        this.playerPickImage.setVisibility(View.VISIBLE);
        this.aiPickImage.setVisibility(View.VISIBLE);
        this.resultViewBig.setVisibility(View.VISIBLE);
    }

    private void insertResult(Pick pick, Pick maiPick, Results gameResult) {
        String playerPick = pick.getName();
        String aiPick = maiPick.getName();
        String gameResultString = gameResult.toString();
        AiDbHelper mDbHelper = new AiDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PLAYER_PICK", playerPick);
        values.put("AI_PICK", aiPick);
        values.put("RESULT", gameResultString);
        long newRodId = db.insert("aidb", (String)null, values);
        Log.v("MainActivity", "new rod id" + newRodId);
        if(newRodId == -1L) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Result saved",Toast.LENGTH_LONG).show();
        }

        this.playedGames.add(0, new Game(0, playerPick, aiPick, gameResultString));
        this.displayDatabaseInfo();
    }

    static {
        categories = new Categories[]{Categories.ROCK, Categories.PAPER, Categories.SCISSORS};
    }
}
