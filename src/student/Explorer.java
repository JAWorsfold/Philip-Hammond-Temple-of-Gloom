package student;

import game.*;

import java.util.*;
import java.util.Map.*;

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

        ArrayList<Long> explored = new ArrayList<>(); // List of ID's I've been too
        Stack<Long> route = new Stack<>(); // Exact route I've taken will be stored in a Stack
        explored.add(state.getCurrentLocation());
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
                route.add(node.getId());
            } else {
                route.pop();
                state.moveTo(route.peek());
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

        // Use Dijkastra's algorithm to find the shortest path and save this to a list of Nodes. Traverse the list of nodes in order to exit
        // Then Do the same as above but to the nearest gold over a certain amount (not the most in case it is too far, adjust over time)
        // Do both in turn and make sure that the shortest path is always within the time limit to exit the cavern.

        // while my nodePath to exit is < timeRemaining. do...
        // do the algorithm for finding nearest gold over a certain amount, go to it, pick it up
        // every location, stop check if there is gold to pick up regardless
        // then from the new location, recreate the map of shortest distance to exit.

        // is nodePath is equal to timeRemaining, follow it.

        // Thanks to wikipedia page on Dijkstra algorithm for initial outline.
        Collection<Node> cavern = state.getVertices();
        Node source = state.getCurrentNode();

        HashSet<Node> traversedNodes = new HashSet<>();
        HashSet<Node> unTraversedNodes = new HashSet<>();
        HashMap<Node, Integer> distances = new HashMap<>();
        HashMap<Node, Node> routes = new HashMap<>();
        distances.put(source, 0);
        unTraversedNodes.add(source);
        for (Node node : cavern) {
            if (!node.equals(source)) {
                distances.put(node, Integer.MAX_VALUE);
            }
            unTraversedNodes.add(node);
        }
        while (!unTraversedNodes.isEmpty()) {
            Node minValueKey = null;
            int minValue = Integer.MAX_VALUE;
            for (Node key : distances.keySet()) {
                int value = distances.get(key);
                if (unTraversedNodes.contains(key)) {
                    if (value < minValue) {
                        minValue = value;
                        minValueKey = key;
                    }
                }
            }
            Node node = minValueKey;
            unTraversedNodes.remove(node);
            traversedNodes.add(node);

            for (Node n : node.getNeighbours()) {
                int alt = distances.get(node) + node.getEdge(n).length();
                if (alt < distances.get(n)) {
                    distances.put(n, alt);
                    routes.put(n, node);
                    unTraversedNodes.add(n);
                }
            }
        }

        LinkedList<Node> routeToTake = new LinkedList<>();
        Node exit = state.getExit();
        while (routes.get(exit) != null) {
            exit = routes.get(exit);
            routeToTake.add(exit);
        }
        Collections.reverse(routeToTake);
        for (Node move : routeToTake) {
            if (state.getCurrentNode().getTile().getGold() > 0) {
                state.pickUpGold();

            }
            state.moveTo(move);
        }
    }
}
