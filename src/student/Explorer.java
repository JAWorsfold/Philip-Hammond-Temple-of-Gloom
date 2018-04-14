package student;

import game.EscapeState;
import game.ExplorationState;
import game.NodeStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        //TODO:

        //USEFUL METHODS:
        //long state.getCurrentLocation(): return a unique identifier for the tile the explorer is currently on
        //int state.getDistanceToTarget(): return the distance from the explorers current location to the Orb
        //Collection<NodeStatus> state.getNeighbours(): return information about the tiles to which the explorer can move from their current location
        //void state.moveTo(long id): move the explorer to the tile with ID id. This fails if that tile is not adjacent to the current location.

        ArrayList<Long> explored = new ArrayList<>(); // List of ID's I've been too
        explored.add(state.getCurrentLocation());
        int stepBackCount = 1;
        while (!(state.getDistanceToTarget() == 0)) { // keep exploring until the orb is reached
            ArrayList<NodeStatus> nodes = new ArrayList<>();
            for (NodeStatus node : state.getNeighbours()) {
                long nodeID = node.getId();
                if (!explored.contains(nodeID)) {
                    nodes.add(node);
                }
            }
            if (nodes.size() > 0) {
                nodes.sort(NodeStatus::compareTo);
                NodeStatus node = nodes.get(0);
                state.moveTo(node.getId());
                explored.add(node.getId());
            } else {
                state.moveTo(explored.get(explored.size() - stepBackCount));
                stepBackCount++; // doesn't work as stepBack will pick a location I can't reach
            }
        }
    }


    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */
    public void escape(EscapeState state) {
        //TODO: Escape from the cavern before time runs out

        //USEFUL METHODS:
        //Node getCurrentNode(): return the Node corresponding to the explorers location
        //Node getExit(): return the Node corresponding to the exit to the cavern (the destination).
        //Collection<Node> getVertices(): return a collection of all traversable nodes in the graph.
        //int getTimeRemaining(): return the number of steps the explorer has left before the ceiling collapses.
        //void moveTo(Node n): move the explorer to node n. this will fail if the given node is not adjacent to the explorers current location. Calling this function will decrement the time remaining.
        //void pickUpGold(): collect all gold on the current tile. This will fail if there is no gold on the current tile or it has already been collected.

    }
}
