package com.example.mcc;

import java.util.Comparator;

class ParticipantComparator implements Comparator<Participant> {
    @Override
    public int compare(Participant a, Participant b) {
        int A = Integer.parseInt(a.getGroup());
        int B = Integer.parseInt(b.getGroup());
        return A > B? -1 : A == B ? 0 : 1;
    }
}