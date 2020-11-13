package com.example.mvoting.model;

public class VotingModel {
    String name;
    String party;
    int vote;

    public VotingModel(String name, String party, int vote) {
        this.name = name;
        this.party = party;
        this.vote = vote;
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
}
