package com.example.mvoting.model;

public class CandidateModel {
    String name;
    String party;
    int vote;
    int unvote;

    public CandidateModel(String name, String party, int vote, int unvote) {
        this.name = name;
        this.party = party;
        this.vote = vote;
        this.unvote = unvote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getUnvote() {
        return unvote;
    }

    public void setUnvote(int unvote) {
        this.unvote = unvote;
    }
}
