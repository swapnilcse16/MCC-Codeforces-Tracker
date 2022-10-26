package com.example.mcc;

import java.util.Comparator;

public class ResultComparator implements Comparator<Result> {
    @Override
    public int compare(Result a, Result b) {
        int A = a.getRank();
        int B = b.getRank();
        return A < B? -1 : A == B ? 0 : 1;
    }
}
