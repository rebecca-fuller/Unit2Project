package com.teamtreehouse.model;

import java.util.*;
import java.util.Collections;

public class Team {
  private String mTeamName;
  private String mCoach;
  private List<Player> mPlayers; 
  private List<Team> teams;
 
  public Team(String teamName, String coach) {
    mTeamName = teamName;
    mCoach = coach;
    mPlayers = new ArrayList<>();
  }
  
  public void addPlayer(Player player) {
    if (!mPlayers.contains(player)) {
      mPlayers.add(player);
    } else {
      System.out.println("Player already on this team.");  
    }
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