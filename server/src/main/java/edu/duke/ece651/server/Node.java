package edu.duke.ece651.server;
import edu.duke.ece651.shared.*;
import java.io.*;
import java.util.*;

public class Node implements Comparator<Node> {
  private String territoryName;
  private int totalSize;

  public Node() {}

  public Node(String myName, int mySize) {
    this.territoryName = myName;
    ;
    this.totalSize = mySize;
  }

  public int getTotalSize() {
    return this.totalSize;
  }

  public String getTerritoryName() {
    return this.territoryName;
  }

  @Override
  public int compare(Node node1, Node node2) {
    if (node1.getTotalSize() < node2.getTotalSize()) {
      return -1;
    } else if (node1.getTotalSize() > node2.getTotalSize()) {
      return 1;
    } else {
      return 0;
    }
  }
}
