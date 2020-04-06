package edu.duke.ece651.player;

import java.util.*;
import edu.duke.ece651.shared.*;
import javafx.util.Pair;

public class OperateAction {
    private ArrayList<Territory> ownTerritories;
    private ArrayList<Territory> wholeTerritories;
    private Pair<Integer, String> playerInfo;
    private ArrayList<Action> allAction;
    private ArrayList<Action> MoveAction;
    private ArrayList<Action> AttackAction;
    private ArrayList<Upgrade> UpgradeAction;
    private HashMap<String, String> ActionType;


    //------------------ Evolution 1 ------------------------//
    //Function: read the action input from player
    //do some simply check and filled the move, attack, upgrade action list.
    //Ready to send to server


    public OperateAction(Pair<Integer, String> PlayerInfo, HashMap<Integer, ArrayList<Territory>> TerritoryMap) {
        allAction = new ArrayList<>();
        MoveAction = new ArrayList<>();
        AttackAction = new ArrayList<>();
        UpgradeAction = new ArrayList<>();
        wholeTerritories = new ArrayList<>();
        ActionType = new HashMap<>();
        ActionType.put("M", "Move");
        ActionType.put("A", "Attack");
        playerInfo = PlayerInfo;
        //stored territorymap by players own territory and whole territories
        for (HashMap.Entry<Integer, ArrayList<Territory>> entry : TerritoryMap.entrySet()) {
            int PlayerID = PlayerInfo.getKey();
            if (entry.getKey() == PlayerID) {
                ownTerritories = entry.getValue();
            }
            for (int i = 0; i < entry.getValue().size(); i++) {
                wholeTerritories.add(entry.getValue().get(i));
            }
        }
    }

    public ArrayList<Action> getMoveActions() {
        return MoveAction;
    }

    public ArrayList<Action> getAttackActions() {
        return AttackAction;
    }

    public void SeparateAction() {
        //stored move and attack action separately
        for (Action Current : allAction) {
            String CurrType = Current.getType();
            if (CurrType == "Move") {
                MoveAction.add(Current);
            } else {
                AttackAction.add(Current);
            }
        }
    }

    public void readAction() {
        //wait for player to input their action
        Scanner s = new Scanner(System.in);
        while (true) {
            String acttype = readActionType(s);
            //System.out.println("[DEBUG] ActionType = " + acttype);
            if (acttype.equals("D")) {
                System.out.println("Finished Input Actions");
                break;
            }
            if (!acttype.equals("M") && !acttype.equals("A") && !acttype.equals("U")) {
                System.out.println("[Invalid] ActionType");
                continue;
            }
            if (acttype.equals("U")) {
                Upgrade curUpgrade = new Upgrade();
                if (readUpgrade(s, curUpgrade)) {
                    UpgradeAction.add(curUpgrade);
                }
                continue;
            }
            Action CurrentAction = new Action();
            CurrentAction.setType(ActionType.get(acttype));
            //Simple check src, dst, soldiersNum
            if (!readSrc(s, CurrentAction, acttype) || !readDst(s, CurrentAction, acttype) || !readNum(s, CurrentAction)) {
                continue;
            }
            String OwnerName = "player_" + playerInfo.getKey();
            CurrentAction.setOwner(OwnerName);
            //store the input action into list
            allAction.add(CurrentAction);
        }
        SeparateAction();
    }

    public String readActionType(Scanner s) {
        //print prompt + read Action Type
        System.out.println("You are the " + playerInfo.getValue() + " player, what would you like to do?");
        System.out.println(" (U)pgrade");
        System.out.println(" (M)ove");
        System.out.println(" (A)ttack");
        System.out.println(" (D)one");
        return s.nextLine();
    }

    public boolean readSrc(Scanner s, Action curAction, String ActionType) {
        System.out.println("Please input the source territory: ");
        String src = s.nextLine();
        if (ActionType.equals("M")) {
            //check if that src for move action is valid
            for (Territory Temp : ownTerritories) {
                if (Temp.getTerritoryName().equals(src)) {
                    curAction.setSrc(Temp);
                    return true;
                }
            }
        } else {
            //for attack actions
            for (Territory WholeTemp : wholeTerritories) {
                if (WholeTemp.getTerritoryName().equals(src)) {
                    curAction.setSrc(WholeTemp);
                    return true;
                }
            }
        }
        System.out.println("[Invalid] The source territory is invalid!");
        return false;
    }

    public boolean readDst(Scanner s, Action curAction, String ActionType) {
        System.out.println("Please input the destination territory: ");
        String dst = s.nextLine();
        //System.out.println("[DEBUG] Dst Territory: " + dst);
        if (ActionType.equals("M")) {
            for (Territory Temp : ownTerritories) {
                if (Temp.getTerritoryName().equals(dst)) {
                    curAction.setDst(Temp);
                    return true;
                }
            }
        } else {
            for (Territory Temp : wholeTerritories) {
                if (Temp.getTerritoryName().equals(dst) && !ownTerritories.contains(Temp)) {
                    curAction.setDst(Temp);
                    return true;
                }
            }
        }
        System.out.println("[Invalid] The destination territory is invalid!");
        return false;
    }

    public boolean readNum(Scanner s, Action curAction) {
        System.out.println("Please input the number of soldiers: ");
        String Num = s.nextLine();
        //make sure the player only input numbers
        for (int i = 0; i < Num.length(); i++) {
            if (!Character.isDigit(Num.charAt(i))) {
                System.out.println("[Invalid] Soldiers Number needs to be integer!");
                return false;
            }
        }
        int soldiersNum = Integer.valueOf(Num);
        curAction.setSoldierLevel(0, soldiersNum);
        return true;
    }


    public boolean readUpgrade(Scanner s, Upgrade cur) {
        System.out.println("Please input the name of territory: ");
        String name = s.nextLine();
        Territory TargetTerr = new Territory();
        for (Territory Current : ownTerritories) {
            if (Current.getTerritoryName().equals(name)) {
                TargetTerr = Current;
                cur.setOwner(name);
            }
        }
        System.out.println("Please input the level before upgrade: ");
        int preL = Integer.parseInt(s.nextLine());
        if (preL > 6 || preL < 0) {
            System.out.println("The level before upgrade is invalid. Please try again.");
            return false;
        }
        cur.setPrevLevel(preL);

        System.out.println("Please input the level after upgrade: ");
        int afterL = Integer.parseInt(s.nextLine());
        if (afterL > 6 || preL >= afterL) {
            System.out.println("The level after upgrade is invalid. Please try again.");
            return false;
        }
        cur.setNextLevel(afterL);


        System.out.println("Please input the number of soldiers to upgrade: ");
        int num = Integer.parseInt(s.nextLine());
        int existNum = TargetTerr.getSoldierLevel(preL);
        if (existNum <= 0 || existNum < num) {
            System.out.println("The number is invalid.");
            return false;
        }
        cur.setNumber(num);

        String OwnerName = "player_" + playerInfo.getKey();
        cur.setOwner(OwnerName);

        return true;
    }

}
