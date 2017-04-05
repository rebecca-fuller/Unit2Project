package com.teamtreehouse.model;

import java.util.*;

public class PlayerComparatorByLastName implements Comparator<Player> {
  @Override
  public int compare(Player o1, Player o2) {
    return o1.getLastName().compareTo(o2.getLastName());  
  }
}