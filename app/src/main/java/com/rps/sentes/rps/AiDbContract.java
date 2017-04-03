package com.rps.sentes.rps;
import android.provider.BaseColumns;

public class AiDbContract {
    public AiDbContract() {
    }



    public class AiDbContract$AiEntry implements BaseColumns {
        public static final String TABLE_NAME = "aidb";
        public static final String _ID = "_id";
        public static final String COLUMN_PLAYER_PICK = "PLAYER_PICK";
        public static final String COLUMN_AI_PICK = "AI_PICK";
        public static final String COLUMN_RESULT = "RESULT";

        public AiDbContract$AiEntry() {
        }
    }
}