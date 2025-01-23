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
        second.addUndirectedEdge(edge);
        first.addUndirectedEdge(edge);
        edgeList.add(edge);
        return edge;
    }
    
    public Edge insertDirectedEdge(Vertex vertexOut, Vertex vertexIn, Object element) {
        Edge edge = new Edge(element, true, vertexOut, vertexIn);
        vertexOut.addOutgoingEdge(edge);
        vertexIn.addIncomingEdge(edge);
        edgeList.add(edge);
        return edge;
    }

    public Vertex removeVertex(Vertex vertex) {
        for (Edge edge : vertex.getIncomingEdges()) {
            edge.getVertexOut().removeOutgoingEdge(edge);
            edgeList.remove(edge);
        }

        for (Edge edge : vertex.getOutgoingEdges()) {
            edge.getVertexIn().removeIncomingEdge(edge);
            edgeList.remove(edge);
        }

        for (Edge edge : vertex.getUndirectedEdges()) {
            if (edge.getVertexIn() == vertex) {
                edge.getVertexOut().removeUndirectedEdge(edge);
            } else {
                edge.getVertexIn().removeUndirectedEdge(edge);
            }

            edgeList.remove(edge);
        }

        vertexList.remove(vertex);

        return vertex;
    }

    public Edge removeEdge(Edge edge) {
        if (edge.isDirected()) {
            edge.getVertexIn().removeIncomingEdge(edge);
            edge.getVertexOut().removeOutgoingEdge(edge);
        } else {
            edge.getVertexIn().removeUndirectedEdge(edge);
            edge.getVertexOut().removeUndirectedEdge(edge);
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
}
