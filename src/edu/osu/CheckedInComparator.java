package edu.osu;

import java.util.Comparator;

public class CheckedInComparator implements Comparator<FourSquareVenue> {
    public int compare(FourSquareVenue venue1, FourSquareVenue venue2) {
        return venue1.herenow - venue2.herenow;
    }
}