//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rps.sentes.rps;

public class Game {
    private int id;
    private String player_pick;
    private String ai_pick;
    private String result;

    public Game(int id, String player_pick, String ai_pick, String result) {
        this.id = id;
        this.player_pick = player_pick;
        this.ai_pick = ai_pick;
        this.result = result;
    }

    public String getPlayer_pick() {
        return this.player_pick;
    }

    public void setPlayer_pick(String player_pick) {
        this.player_pick = player_pick;
    }

    public String getAi_pick() {
        return this.ai_pick;
    }

    public void setAi_pick(String ai_pick) {
        this.ai_pick = ai_pick;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
