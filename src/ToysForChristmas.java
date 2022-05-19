import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ToysForChristmas {
    private final List<Edge>[] graph;
    private final int toys;

    @SuppressWarnings("unchecked")
    public ToysForChristmas(int toys, int children) {
        this.toys = toys;
        this.graph = new LinkedList[toys + children + 2];

        for (int i = 0; i < graph.length; i++)
            graph[i] = new LinkedList<>();

        for (int i = toys + 1; i < graph.length - 1; i++)
            graph[i].add(new Edge(graph.length - 1, 1));
    }

    public void handleConnectionToys(int toy, int weight) {
        graph[0].add(new Edge(toy + 1, weight));
    }

    public void handleConnectionChildren(int children, int toy) {
        graph[toy].add(new Edge(this.toys + 1 + children, 1));
    }

    private List<Edge>[] buildNetwork(List<Edge>[] graph) {
        List<Edge>[] network = Arrays.copyOf(graph, graph.length);

        for (int node = 0; node < graph.length; node++)
            for (Edge e : graph[node]) {
                int node2 = e.node;
                if (!graph[node2].contains(new Edge(node, 0)))
                    network[node2].add(new Edge(node, 0));
            }

        return network;
    }

    int findPath(List<Edge>[] network, int[][] flow, int source, int sink, int[] via) { //pesquisa em largura
        int numNodes = network.length;
        Queue<Integer> waiting = new LinkedList<>();
        boolean[] found = new boolean[numNodes];
        int[] pathIncr = new int[numNodes];
        waiting.add(source);
        found[source] = true;
        via[source] = source;
        pathIncr[source] = Integer.MAX_VALUE;
        do {
            int origin = waiting.remove();
            for (Edge e : network[origin]) {
                int destin = e.node;
                int residue = e.weight - flow[origin][destin];
                if (!found[destin] && residue > 0) {
                    via[destin] = origin;
                    pathIncr[destin] = Math.min(pathIncr[origin], residue);
                    if (destin == sink)
                        return pathIncr[destin];
                    else {
                        waiting.add(destin);
                        found[destin] = true;
                    }
                }
            }
        } while (!waiting.isEmpty());
        return 0;
    }

    public int edmondsKarp() {
        int source = 0;
        int sink = graph.length - 1;
        List<Edge>[] network = buildNetwork(graph);
        int numNodes = network.length;
        int[][] flow = new int[numNodes][numNodes];

        int[] via = new int[numNodes];
        int flowValue = 0;
        int increment;

        while ((increment = findPath(network, flow, source, sink, via)) != 0) {
            flowValue += increment;
            // Update flow
            int node = sink;
            while (node != source) {
                int origin = via[node];
                flow[origin][node] += increment;
                flow[node][origin] -= increment;
                node = origin;
            }
        }
        return flowValue;
    }

    private static class Edge {
        private final int node;
        private final int weight;

        public Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (!(other instanceof Edge)) return false;
            return ((Edge) other).node == this.node;
        }
    }
}