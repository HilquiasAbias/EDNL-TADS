package graph;

class Main {
    public static void main(String[] args) {
        // Criação do grafo
        Graph graph = new Graph();
        
        // Inserção de vértices
        Vertex v1 = graph.insertVertex("A");
        Vertex v2 = graph.insertVertex("B");
        Vertex v3 = graph.insertVertex("C");
        Vertex v4 = graph.insertVertex("D");

        System.out.println("Vértices após inserção:");
        for (Vertex v : graph.vertexList) {
            System.out.println(v);
        }

        // Inserção de arestas
        Edge e1 = graph.insertEdge(v1, v2, "A-B");
        Edge e2 = graph.insertEdge(v2, v3, "B-C");
        Edge e3 = graph.insertEdge(v3, v4, "C-D");
        Edge e4 = graph.insertEdge(v4, v1, "D-A");
        graph.insertDirectedEdge(v1, v3, "A->C");

        System.out.println("\nArestas após inserção:");
        for (Edge e : graph.edgeList) {
            System.out.println(e);
        }

        // Verificação de conexões
        System.out.println("\nConexões de cada vértice:");
        for (Vertex v : graph.vertexList) {
            System.out.println("Vértice: " + v + ", Entradas: " + v.getIncomingEdges() + ", Saídas: " + v.getOutgoingEdges());
        }

        // Remoção de uma aresta
        graph.removeEdge(e1);
        System.out.println("\nArestas após remover A-B:");
        for (Edge e : graph.edgeList) {
            System.out.println(e);
        }

        // Remoção de um vértice
        graph.removeVertex(v2);
        System.out.println("\nVértices após remover B:");
        for (Vertex v : graph.vertexList) {
            System.out.println(v);
        }

        System.out.println("\nArestas após remover B:");
        for (Edge e : graph.edgeList) {
            System.out.println(e);
        }

        // Verificação final
        System.out.println("\nConexões finais:");
        for (Vertex v : graph.vertexList) {
            System.out.println("Vértice: " + v + ", Entradas: " + v.getIncomingEdges() + ", Saídas: " + v.getOutgoingEdges());
        }
    }
}
