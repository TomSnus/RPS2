package com.rps.sentes.rps;

/**
 * Created by Faßreiter on 08.04.2017.
 */

public class Pick {
    private String name;
    private int resource;

    public Pick(String name, int resource){
        this.resource = resource;
        this.name = name;
    }


    public boolean isPaper() {
        return  (this.name.equals("PAPER"));
    }

    public boolean isRock() {
        return  (this.name.equals("ROCK"));
    }
    public boolean isScissors() {
        return  (this.name.equals("SCISSORS"));
    }

    public static int getImageId(String category) {
        switch (category) {
            case "ROCK": return R.drawable.rock;
            case  "PAPER": return R.drawable.paper;
            case "SCISSORS": return R.drawable.scissors;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getResource() {
        return resource;
    }
}
