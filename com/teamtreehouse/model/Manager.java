package com.teamtreehouse.model;

import java.util.*;

public class Manager {
  private Scanner scanner = new Scanner(System.in);

  private Map<String, String> menuMap;
  private List<Player> mTeamPlayers;
  private List<Player> allPlayers;
  private List<Team> teams;
  private List<Player> playersUnteamed;

  private int choice;
  private int i;
  private String playerInfo;

  private Team team;
  private String mTeamName;
  private String mCoach;
  
  private Player player;
  private String mFirstName;
  private String mLastName;
  private int mHeight;
  private boolean mExperience;  
  
  //Set values for the menu, and seat lists of teams and load players
  public void setMenu() {
    menuMap = new LinkedHashMap<String, String>();
    menuMap.put("1.) create", "Create a new team");
    menuMap.put("2.) add", "Add a player to an existing team");
    menuMap.put("3.) remove", "Remove a player from an existing team");
    menuMap.put("4.) height", "View a report of a team grouped by height");
    menuMap.put("5.) experience report", "View report of experience for every team");
    menuMap.put("6.) roster", "Print a roster of all players on the team");
    menuMap.put("7.) quit", "Exit the program");
    
    allPlayers = new ArrayList<>(Arrays.asList(Players.load()));
    teams = new ArrayList<>();
    mTeamPlayers = new ArrayList<>();
    playersUnteamed = new ArrayList<>(allPlayers);
  }
  
  //Print out the menu and prompt for choice
  private void menuPrint() {
    System.out.printf("%n---------------%n");
    for (Map.Entry<String, String> option : menuMap.entrySet()) {
      System.out.printf("%s -- %s%n", 
      option.getKey(), 
      option.getValue());
    }
    System.out.printf("---------------%nWhat do you want to do? ");
    choice = scanner.nextInt();
  }
  
  //Call method to print menu and make choice
  public void choice() {
    menuPrint();
    switch(choice) {
    case 1:
      createTeam();        
      break;
    case 2:
      addPlayer();
      break;
    case 3:
      removePlayer();
      break;
    case 4:
      height();
      break;
    case 5:
      experienceReport();
      break;
    case 6:
      roster();
      break;
    case 7:
      System.exit(0);
    default:
    System.out.printf("Unkown key: %s", choice); 
    }
  }
  
  //Pick a team
  private void pickTeam() {
    i = 1;
    Collections.sort(teams, new TeamComparator());
    //loop through each team
    for (Team team : teams) {
      //get team info
      mTeamName = team.getTeamName();
      mCoach = team.getCoach();
      //print team info
      System.out.println(i + ".) " + mTeamName + " Coached by: " + mCoach);
      i++;
    }
    //choose team
    System.out.print("Choose a team: "); 
    choice = scanner.nextInt();
    choice -= 1;
    team = teams.get(choice);
    mTeamName = team.getTeamName();
    mCoach = team.getCoach();
    mTeamPlayers = team.getPlayers();
  }
  
  //Pick a player from a team
  private void pickPlayerFromTeam(List<Player> teamPlayers) {
    mTeamPlayers = teamPlayers;
    i = 1;
    Collections.sort(teamPlayers, new PlayerComparatorByLastName());
    //loop through players in team
    for (Player player : mTeamPlayers) {
      //print player info
      System.out.println(i + ".) " + playerInfoToString(player));
      i++;
    }
    //choose player
    System.out.print("Choose a player: "); 
    choice = scanner.nextInt();
    choice -= 1;
    player = mTeamPlayers.get(choice);
    mFirstName = player.getFirstName();
    mLastName = player.getLastName();  
  }
  
  //Pick a player from all unteamed players
  private void pickPlayerFromUnteamed(List<Player> playersUnteamed) {
    this.playersUnteamed = playersUnteamed;
    Collections.sort(playersUnteamed, new PlayerComparatorByLastName());
    i = 1;
    //loop through all players
    for (Player player : playersUnteamed) {
      //get player info
      mFirstName = player.getFirstName();
      mLastName = player.getLastName();
      mHeight = player.getHeightInInches();
      mExperience = player.isPreviousExperience();
      //print player info
      System.out.println(i + ".) " + playerInfoToString(player));
      i++;
    }
    //choose player
    System.out.print("Choose a player: "); 
    choice = scanner.nextInt();
    choice -= 1;
    player = playersUnteamed.get(choice);
    mFirstName = player.getFirstName();
    mLastName = player.getLastName(); 
  }
  
  //get player info in string format
  private String playerInfoToString(Player player) {
    this.player = player;
    mFirstName = player.getFirstName();
    mLastName = player.getLastName();
    mHeight = player.getHeightInInches();
    mExperience = player.isPreviousExperience();
    playerInfo = String.format("%s %s Height: %d inches Experience: %b", mFirstName, mLastName, 
                               mHeight, mExperience);
    return playerInfo;
    
  }
  
  //Create a team, only if there are less then 3 teams
  private void createTeam() {
    if (teams.size() < 3) {
      System.out.printf("%n---------------%n");
      System.out.println("-Create a Team-");
      System.out.print("Enter new team name: ");
      mTeamName = scanner.next();
      System.out.printf("Who will coach %s? ", mTeamName);
      mCoach = scanner.next();
      Team team = new Team(mTeamName, mCoach);
      teams.add(team);
    } else {
      System.out.println("Already have enough teams.");  
    }
    choice();
  }
   
    //Add a player
  public void addPlayer() {
    if (teams.size() > 0) {
      System.out.printf("%n----------------%n");
      System.out.println("---Add a Player---");
      pickTeam();
      mTeamPlayers = team.getPlayers();
      
      if (mTeamPlayers.size() < 11) {
      
        pickPlayerFromUnteamed(playersUnteamed);
        
        //add player to team
        team.addPlayer(player);
        mTeamPlayers = team.getPlayers();
        //remove player from unteamed players
        playersUnteamed.remove(player);
        //print add player info
        System.out.printf("%s %s added to %s%nTeam has %d players.%n", mFirstName, mLastName, mTeamName, mTeamPlayers.size());
      } else {
        System.out.println("Pick a different team. Team already has enough players.");  
      }
    } else {
      System.out.println("No teams.Please create some teams first.");  
    }
    choice();  
  }
  
  //Remove a player
  public void removePlayer() {
    if (teams.size() > 0) {
      System.out.printf("%n---------------%n");
      System.out.println("Remove a Player");
      
      pickTeam();
      
      pickPlayerFromTeam(mTeamPlayers);
      
      //remove player from team
      team.removePlayer(player);
      //add player back to unteamed list
      playersUnteamed.add(player);
      //print player removed info
      System.out.printf("%s %s removed from %s%n", mFirstName, mLastName, mTeamName);
    } else {
      System.out.println("No teams. Please create some teams.");
    }
    choice();  
  }
  
  //Print team by height
  public void height() {
    if (teams.size() > 0) {
      System.out.printf("%n---------------%n");
      System.out.println("---By Height---");
      
      pickTeam();
      mTeamPlayers = team.getPlayers();
      
      List<Player> small = new ArrayList<>();
		  List<Player> medium = new ArrayList<>();
		  List<Player> large = new ArrayList<>();
      List<Player> unplaced = new ArrayList<>();
      
      Collections.sort(mTeamPlayers, new PlayerComparatorByLastName());
      for (Player player : mTeamPlayers) {
        int x = player.getHeightInInches();
        if (x < 41) {
          small.add(player);
        } else if (40 < x && x < 47) {
          medium.add(player);
        } else if (46 < x) {
          large.add(player);
        } 
      }
      
      System.out.printf("%n---------------%n");
      System.out.printf("%s Coached by: %s", mTeamName, mCoach);
      System.out.printf("%n---------------%n");
      
      if (small.size() > 0) {
        System.out.printf("%n---------------%n");
        System.out.println("Players Under 40 Inches");
        System.out.printf("%n---------------%n");
        for (Player player : small) {
          System.out.println(playerInfoToString(player));  
        }
      }
      
      if (medium.size() > 0) {
        System.out.printf("%n---------------%n");
        System.out.println("Players 41 to 46 Inches");
        System.out.printf("%n---------------%n");
        for (Player player : medium) {
          System.out.println(playerInfoToString(player));  
        }  
      }
      
      if (large.size() > 0) {
        System.out.printf("%n---------------%n");
        System.out.println("Players Above 50 Inches");
        System.out.printf("%n---------------%n");
        for (Player player : large) {
          System.out.println(playerInfoToString(player));  
        }  
      }
    } else {
      System.out.println("No teams. Please create some teams.");  
    }
    choice();
  }
  

  //Print league balance by experience
  private void experienceReport() {
    if (teams.size() > 0) {
      System.out.printf("%n---------------%n");
      System.out.println("Experience Report");
      
      int experienced = 0;
      int inexperienced = 0;
      Collections.sort(teams, new TeamComparator());
      for (Team team : teams) {
        System.out.printf("%n---------------%n");
        System.out.printf("%s Coached by: %s%n", team.getTeamName(), team.getCoach());
        mTeamPlayers = team.getPlayers();
        for (Player player : mTeamPlayers) {
          if (player.isPreviousExperience()) {
            experienced++;  
          } else {
            inexperienced++;  
          }
        }
        System.out.printf("Experienced players: %s%n", experienced);
        System.out.printf("Inexperienced players: %s%n", inexperienced);
      }
      
    } else {
      System.out.println("No teams.Please create some teams first.");    
    }
    choice();  
  }
  
  //Roster of team
  private void roster() {
    if (teams.size() > 0) {
      System.out.printf("%n---------------%n");
      System.out.println("--Team Roster--");
       
      pickTeam();
      System.out.printf("%n---------------%n");
      System.out.printf("%s Coached by: %s", team.getTeamName(), team.getCoach());
      System.out.printf("%n---------------%n");
      Collections.sort(mTeamPlayers, new PlayerComparatorByLastName());
      for (Player player : mTeamPlayers) {
        System.out.println(playerInfoToString(player));  
      }
    } else {
      System.out.println("No teams.Please create some teams first.");    
    }
    choice();  
  }

}