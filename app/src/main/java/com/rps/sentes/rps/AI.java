//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rps.sentes.rps;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class AI {
    private static Map<String, Integer> possiblePicks = new HashMap();
    public static String[] categories = new String[]{"ROCK", "PAPER", "SCISSORS"};

    public AI() {
    }

    public static String calculate(List<Game> gamesHistory) {
        possiblePicks.clear();
        Random rng1;
        if(gamesHistory.size() < 4) {
            rng1 = new Random();
            Log.v("AI", "no Pattern found -> returning rng value");
            return categories[rng1.nextInt(3)];
        } else {
            if(gamesHistory.size() > 4) {
                Log.v("AI", "Searching with pattern size: 4");
                searchForPattern(gamesHistory, getPattern(gamesHistory, 4), 4);
            }

            if(possiblePicks.size() == 0) {
                Log.v("AI", "Searching with pattern size: 3");
                searchForPattern(gamesHistory, getPattern(gamesHistory, 3), 3);
            }

            if(possiblePicks.size() == 0) {
                Log.v("AI", "Searching with pattern size: 2");
                searchForPattern(gamesHistory, getPattern(gamesHistory, 2), 2);
            }

            if(possiblePicks.size() <= 0) {
                rng1 = new Random();
                Log.v("AI", "no Pattern found -> returning rng value");
                return categories[rng1.nextInt(3)];
            } else {
                LinkedList rng = new LinkedList(possiblePicks.entrySet());
                Collections.sort(rng, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        return 0;
                    }
                });
                LinkedHashMap result = new LinkedHashMap();
                Iterator possiblePick = rng.iterator();

                while(possiblePick.hasNext()) {
                    Entry entry = (Entry)possiblePick.next();
                    result.put(entry.getKey(), entry.getValue());
                }

                String possiblePick1 = (String)((Entry)result.entrySet().iterator().next()).getKey();
                Log.v("AI", "Pattern found -> returning " + counterPick(possiblePick1));
                return counterPick(possiblePick1);
            }
        }
    }

    private static String counterPick(String possiblePick) {
        return possiblePick.equals("ROCK")?"PAPER":(possiblePick.equals("PAPER")?"SCISSORS":"ROCK");
    }

    private static void searchForPattern(List<Game> gamesHistory, List<Game> pattern, int accordanceMax) {
        int accordance = 0;

        for(int i = 1; i < gamesHistory.size() - 1 - pattern.size(); ++i) {
            int oldVal;
            for(oldVal = 0; oldVal < pattern.size(); ++oldVal) {
                if(!((Game)gamesHistory.get(i + oldVal)).getPlayer_pick().equals(((Game)pattern.get(oldVal)).getPlayer_pick())) {
                    accordance = 0;
                    break;
                }

                ++accordance;
            }

            if(accordance == accordanceMax) {
                if(!possiblePicks.containsKey(((Game)gamesHistory.get(i - 1)).getPlayer_pick())) {
                    possiblePicks.put(((Game)gamesHistory.get(i - 1)).getPlayer_pick(), Integer.valueOf(1));
                } else {
                    oldVal = ((Integer)possiblePicks.get(((Game)gamesHistory.get(i - 1)).getPlayer_pick())).intValue();
                    possiblePicks.remove(((Game)gamesHistory.get(i - 1)).getPlayer_pick());
                    possiblePicks.put(((Game)gamesHistory.get(i - 1)).getPlayer_pick(), Integer.valueOf(oldVal + 1));
                }
            }
        }

    }

    private static List<Game> getPattern(List<Game> games, int size) {
        ArrayList pattern = new ArrayList();

        for(int i = 0; i < size; ++i) {
            pattern.add(games.get(i));
        }

        return pattern;
    }
}
