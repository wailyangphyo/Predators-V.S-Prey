package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

import static huglife.HugLifeUtils.randomEntry;

/**
 * An implementation of Clorus.
 *
 * @author William Phyo
 */
public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }
    /*clorus always have same color*/
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }
    /*gain Phlips energy*/
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * clorus should lose 0.03 when moving
     */
    public void move() {
        energy -= 0.03;
    }


    /**
     * clorus lose 0.01 when staying
     */
    public void stay() {
        energy -= 0.01;
    }

    /**
     * clorus and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * clorus.
     */
    public Clorus replicate() {
        Clorus p = new Clorus(energy /2);
        energy /= 2;
        return p;
    }
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> neighborPhilips = new ArrayDeque<>();
        for(Map.Entry<Direction,Occupant> entry: neighbors.entrySet()){
            if(entry.getValue().name() != "empty") {
                if(entry.getValue().name() == "plip"){
                    neighborPhilips.addFirst(entry.getKey());
                }
            }else {
                emptyNeighbors.addFirst(entry.getKey());
            }
        }
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        if (emptyNeighbors.size() ==0) { // FIXM
            return new Action(Action.ActionType.STAY);
        }
        // Rule 2
        /*
        * if Phlips are scene Clorus will attack one of them randomly
        * */
        if(neighborPhilips.size()>0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(neighborPhilips));
        }
        //Rule 3
        if(energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        // Rule 4
        return new Action(Action.ActionType.MOVE,randomEntry(emptyNeighbors));
    }
}
