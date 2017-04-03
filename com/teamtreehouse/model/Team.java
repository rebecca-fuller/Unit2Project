package com.teamtreehouse.model;

import java.util.*;

public class Team {
  private String mTeamName;
  private String mCoach;
  private List<Player> mPlayers; 
 
  public Team(String teamName, String coach) {
    mTeamName = teamName;
    mCoach = coach;
    mPlayers = new ArrayList<>();
  }
  
  public void addPlayer(Player player) {
    mPlayers.add(player);  
  }
  
  public void removePlayer(Player player) {
    mPlayers.remove(player);  
  }
  
  public String getTeamName() {
    return mTeamName;  
  }
  
  public String getCoach() {
    return mCoach;  
  }
  
  public List<Player> getPlayers() {
    return mPlayers;  
  }
  
  
  
  
  
}