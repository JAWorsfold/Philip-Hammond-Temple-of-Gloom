Very good attempt.

```

        HashSet<Node> traversedNodes = new HashSet<>();
        HashSet<Node> unTraversedNodes = new HashSet<>();
        HashMap<Node, Integer> distances = new HashMap<>();
        HashMap<Node, Node> routes = new HashMap<>();
        distances.put(exit, 0);
        unTraversedNodes.add(exit);
        for (N
```
Prefer interfaces and general classes to specific implementations; even if you need to use type coercion.

```
   LinkedList<Node> routeToTake = new LinkedList<>();
        Node start = state.getCurrentNode();
        while (routes.get(start) != null) {
            start = routes.get(start);
            routeToTake.add(start);
        }
        for (Node move : routeToTake) {
            if (state.getCurrentNode().getTile().getGold() > 0) {
           
```
Similar comment.
