package graph;

import java.util.ArrayList;

public class Graph { // implements IGraph
    ArrayList<Vertex> vertexList;
    ArrayList<Edge> edgeList;

    public Graph() {
        vertexList = new ArrayList<Vertex>();
        edgeList = new ArrayList<Edge>();
    }

    public Vertex insertVertex(Object element) {
        Vertex vertex = new Vertex(element);
        vertexList.add(vertex);
        return vertex;
    }    

    public Edge insertEdge(Vertex first, Vertex second, Object element) {
        Edge edge = new Edge(element, false, first, second);
        first.addIncomingEdge(edge);
        first.addOutgoingEdge(edge);
        second.addIncomingEdge(edge);
        second.addOutgoingEdge(edge);
        edgeList.add(edge);
        return edge;
    }

    public void insertDirectedEdge(Vertex vertexOut, Vertex vertexIn, Object element) {
        Edge edge = new Edge(element, true, vertexOut, vertexIn);
        vertexIn.addUndirectedEdge(edge);
        vertexOut.addUndirectedEdge(edge);
        edgeList.add(edge);
    }

    public Vertex removeVertex(Vertex vertex) {
        System.out.println(vertex.edges());
        ArrayList<Edge> incidentEdges = new ArrayList<>(vertex.edges());
        for (Edge edge : incidentEdges) {
            removeEdge(edge);
        }
        vertexList.remove(vertex);
        return vertex;
    }

    public Edge removeEdge(Edge edge) {
        if (edge.isDirected()) {
            edge.getVertexIn().removeUndirectedEdge(edge);
            edge.getVertexOut().removeUndirectedEdge(edge);
        } else {
            edge.getVertexIn().removeIncomingEdge(edge);
            edge.getVertexOut().removeOutgoingEdge(edge);
        }
        edgeList.remove(edge);
        return edge;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices:\n");
        for (Vertex vertex : vertexList) {
            sb.append(vertex).append("\n");
        }
        sb.append("Edges:\n");
        for (Edge edge : edgeList) {
            sb.append(edge).append("\n");
        }
        return sb.toString();
    }


    // public ArrayList<Vertex> finalVertex(Edge edge) {
    //     ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    //     vertices.add(edge.getVertexIn());
    //     vertices.add(edge.getVertexOut());
    //     return vertices;
    // }

    // public Vertex opposite(Vertex vertex, Edge edge) throws GraphGenericException {
    //     if (edge.getVertexIn() != vertex) {
    //         throw new GraphGenericException("O vértice não é incidente a aresta");
    //     }
    //     return edge.getVertexOut();
    // }

    // public boolean isAdjacent(Vertex first, Vertex second) {
    //     for (Edge edge : first.edges()) {
    //         if (edge.getVertexIn() == second || edge.getVertexOut() == second) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public void replaceVertexElement(Vertex vertex, Object element) throws GraphGenericException {
    //     try {
    //         vertexList.get(vertexList.indexOf(vertex)).setValue(element);
    //     } catch (IndexOutOfBoundsException e) {
    //         throw new GraphGenericException("Vértice não encontrado.");
    //     }
    // }

    // public void replaceEdgeElement(Edge edge, Object element) throws GraphGenericException {
    //     try {
    //         edge.setValue(element);
    //     } catch (IndexOutOfBoundsException e) {
    //         throw new GraphGenericException("Aresta não encontrada.");
    //     }
    // }

    // public Vertex insertVertex(Object element) {
    //     Vertex vertex = new Vertex(element);
    //     vertexList.add(vertex);
    //     return vertex;
    // }

    // public Edge insertEdge(Vertex first, Vertex second, Object element) {
    //     Edge edge = new Edge(element, false, first, second);
    //     first.addEdge(edge);
    //     second.addEdge(edge);
    //     return edge;
    // }

    // public Object removeVertex(Vertex vertex) throws GraphGenericException {
    //     if (!vertexList.remove(vertex)) {
    //         throw new GraphGenericException("Vértice não encontrado.");
    //     }
    //     for (Edge edge : vertex.edges()) {
    //         removeEdge(edge);
    //     }
    //     return vertex;
    // }

    // public Object removeEdge(Edge edge) throws GraphGenericException {
    //     boolean vertexInRemoved = edge.getVertexIn().removeEdge(edge);
    //     boolean vertexOutRemoved = edge.getVertexOut().removeEdge(edge);
    //     if (!vertexInRemoved || !vertexOutRemoved) {
    //         throw new GraphGenericException("Aresta não encontrada");
    //     }
    //     return edge.getValue();
    // }

    // public ArrayList<Edge> incidentEdges(Vertex vertex) {
    //     return vertex.edges();
    // }

    // public ArrayList<Vertex> vertices() {
    //     return vertexList;
    // }

    // public ArrayList<Edge> edges() {
    //     ArrayList<Edge> edgeList = new ArrayList<Edge>();
    //     vertexList.forEach((vertex) -> {
    //         ArrayList<Edge> vertexEdges = vertex.edges();
    //         for (Edge edge : vertexEdges) {
    //             if (edge.isDirected()) {
    //                 edgeList.add(edge);
    //                 continue;
    //             }
    //             if (!edgeList.contains(edge)) {
    //                 edgeList.add(edge);
    //             }
    //         }
    //     });
    //     return edgeList;
    // }

    // public boolean isDirected(Edge edge) {
    //     return edge.isDirected();
    // }

    // public void insertDirectedEdge(Vertex vertexOut, Vertex vertexIn, Object element) {
    //     Edge edge = new Edge(element, true, vertexOut, vertexIn);
    //     vertexIn.edges().add(edge);
    //     vertexOut.edges().add(edge);
    // }

}
