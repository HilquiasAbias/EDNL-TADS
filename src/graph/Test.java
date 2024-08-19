class Main {
    public static void main(String[] args) {
        // Criação de um grafo
        Graph graph = new Graph();
        // Inserção de vértices
        Vertex v1 = graph.insertVertex("v1");
        Vertex v2 = graph.insertVertex("v2");
        Vertex v3 = graph.insertVertex("v3");
        Vertex v4 = graph.insertVertex("v4");
        Vertex v5 = graph.insertVertex("v5");
        // Inserção de arestas
        Edge e1 = graph.insertEdge(v1, v2, "e1");
        Edge e2 = graph.insertEdge(v2, v3, "e2");
        Edge e3 = graph.insertEdge(v3, v4, "e3");
        Edge e4 = graph.insertEdge(v4, v5, "e4");
        Edge e5 = graph.insertEdge(v5, v1, "e5");
        // Teste de métodos
        // isAdjacent
        System.err.println("isAdjacent");
        System.out.println(graph.isAdjacent(v1, v2)); // true
        System.out.println(graph.isAdjacent(v2, v3)); // true
        System.out.println(graph.isAdjacent(v3, v4)); // true
        System.out.println(graph.isAdjacent(v4, v5)); // true
        System.out.println(graph.isAdjacent(v5, v1)); // true
        // replaceVertexElement
        System.err.println("replaceVertexElement");
        try {
            graph.replaceVertexElement(v1, "v1.1");
            System.out.println(v1.getValue()); // v1.1
        } catch (GraphGenericException e) {
            System.out.println(e.getMessage());
        }
        // replaceEdgeElement
        System.err.println("replaceEdgeElement");
        try {
            graph.replaceEdgeElement(e1, "e1.1");
            System.out.println(e1.getValue()); // e1.1
        } catch (GraphGenericException e) {
            System.out.println(e.getMessage());
        }
        // removeVertex
        System.err.println("removeVertex");
        try {
            System.out.println(graph.removeVertex(v1)); // v1.1
        } catch (GraphGenericException e) {
            System.out.println(e.getMessage());
        }
        // removeEdge
        System.err.println("removeEdge");
        try {
            System.out.println(graph.removeEdge(e1)); // e1.1
        } catch (GraphGenericException e) {
            System.out.println(e.getMessage());
        }
        // incidentEdges
        System.err.println("incidentEdges");
        System.out.println(graph.incidentEdges(v2)); // [e2]
        System.out.println(graph.incidentEdges(v3)); // [e2, e3]
        System.out.println(graph.incidentEdges(v4)); // [e3, e4]
        System.out.println(graph.incidentEdges(v5)); // [e4, e5]
        // vertices
        System.err.println("vertices");
        System.out.println(graph.vertices()); // [v2, v3, v4, v5]
        // edges
        System.err.println("edges");
        System.out.println(graph.edges()); // [e2, e3, e4, e5]
        // isDirected
        System.err.println("isDirected");
        System.out.println(graph.isDirected(e1)); // false
        // insertDirectedEdge
        System.err.println("insertDirectedEdge");
        graph.insertDirectedEdge(v2, v1, "e1.1");
        System.out.println(graph.edges()); // [e2, e3, e4, e5, Edge@1b6d3586]
        // finalVertex
        System.err.println("finalVertex");
        System.out.println(graph.finalVertex(e2)); // [v3]
        // opposite
        try {
            System.out.println(graph.opposite(v2, e2)); // v3
        } catch (GraphGenericException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(graph.opposite(v1, e2)); // v2
        } catch (GraphGenericException e) {
            System.out.println(e.getMessage());
        }
    }
}