
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
    menuMap.put("5.) balance", "League Balance Report");
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
      balance();
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
  
  
  //Create a team, only if there are less then 3 teams
  private void createTeam() {
    if (teams.size() < 3) {
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
      pickTeam();
      
      i = 1;
      //loop through all players
      for (Player player : playersUnteamed) {
        //get player info
        mFirstName = player.getFirstName();
        mLastName = player.getLastName();
        mHeight = player.getHeightInInches();
        mExperience = player.isPreviousExperience();
        //print player info
        System.out.println(i + ".) " + mLastName + ", " + mFirstName + " Height: " + mHeight + "in Experience: " + mExperience);
        i++;
      }
      //choose player
      System.out.print("Choose a player: "); 
      choice = scanner.nextInt();
      choice -= 1;
      player = playersUnteamed.get(choice);
      mFirstName = player.getFirstName();
      mLastName = player.getLastName(); 
      
      //add player to team
      team.addPlayer(player);
      //remove player from unteamed players
      playersUnteamed.remove(player);
      //print add player info
      System.out.printf("%s %s added to %s%n", mFirstName, mLastName, mTeamName);
    } else {
      System.out.println("No teams.Please create some teams first.");  
    }
    choice();  
  }
  
  //Remove a player
  public void removePlayer() {
    if (teams.size() > 0) {
      pickTeam();
      
      i = 1;
      //loop through players in team
      for (Player player : mTeamPlayers) {
        //get player info
        mFirstName = player.getFirstName();
        mLastName = player.getLastName();
        mHeight = player.getHeightInInches();
        mExperience = player.isPreviousExperience();
        //print player info
        System.out.println(i + ".) " + mLastName + ", " + mFirstName + " Height: " + mHeight + "in Experience: " + mExperience);
        i++;
      }
      //choose player
      System.out.print("Choose a player: "); 
      choice = scanner.nextInt();
      choice -= 1;
      player = mTeamPlayers.get(choice);
      mFirstName = player.getFirstName();
      mLastName = player.getLastName();
      
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
      pickTeam();
      
      //get team players
      mTeamPlayers = team.getPlayers();
      //create team map
      TreeMap teamHeight = new TreeMap();
      //for each player
      for (Player player : mTeamPlayers) {
        //set player name
        String mPlayerName = (player.getFirstName() + " " + player.getLastName());
        //set player height
        mHeight = player.getHeightInInches();  
        //put into map
        teamHeight.put(mHeight, mPlayerName);
      }
      
      Set set = teamHeight.entrySet();
      
      Iterator itr = set.iterator();
      
      while (itr.hasNext()) {
        Map.Entry entry = (Map.Entry)itr.next();
        System.out.print(entry.getValue() + " Height: ");
        System.out.println(entry.getKey() + " inches");
      }
                           
    } else {
      System.out.println("No teams. Please create some teams.");   
    }
    choice();
  }
  

  //Print league balance by experience
  private void balance() {
    if (teams.size() > 0) {
      System.out.println("League Balance by Experience");
      int experiencedInt = 0;
      int inexperiencedInt = 0;
      //Loop through each team
      for (Team team : teams) {
        mTeamPlayers = team.getPlayers();
        mTeamName = team.getTeamName();
        //loop through players in team
        for (Player player : mTeamPlayers) {
          mExperience = player.isPreviousExperience();
          //see if player is experienced
          //see if player is inexperienced
          if(mExperience == true) {
            experiencedInt++;    
          } else if (mExperience == false) {
            inexperiencedInt++;    
          }
        }
      //print teams info
      System.out.println(mTeamName);
      System.out.println("Experienced: " + experiencedInt);
      System.out.println("Inexperienced: " + inexperiencedInt);
      } 
    } else {
      System.out.println("No teams.Please create some teams first.");    
    }
    choice();  
  }
  
  //Roster of team
  private void roster() {
    if (teams.size() > 0) {
      pickTeam();
      
      //get team info
      mTeamName = team.getTeamName();
      mCoach = team.getCoach();
      mTeamPlayers = team.getPlayers();
      
      //loop through players in team
      for (Player player : mTeamPlayers) {
        //get player info
        mFirstName = player.getFirstName();
        mLastName = player.getLastName();
        mHeight = player.getHeightInInches();
        mExperience = player.isPreviousExperience();
        //print player info
        System.out.println(mLastName + " " + mFirstName + " Height: " + mHeight + "inches Experience: " + mExperience);
        i++;
      }
    } else {
      System.out.println("No teams.Please create some teams first.");    
    }
    choice();  
  }

}