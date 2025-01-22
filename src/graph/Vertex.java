package graph;

import java.util.ArrayList;

public class Vertex {
    private Object value;
    private ArrayList<Edge> incomingEdges;
    private ArrayList<Edge> outgoingEdges;
    private ArrayList<Edge> undirectedEdges;

    public Vertex(Object value) {
        this.value = value;
        this.incomingEdges = new ArrayList<Edge>();
        this.outgoingEdges = new ArrayList<Edge>();
        this.undirectedEdges = new ArrayList<Edge>();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ArrayList<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    public ArrayList<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public ArrayList<Edge> getUndirectedEdges() {
        return undirectedEdges;
    }

    public void addIncomingEdge(Edge edge) {
        incomingEdges.add(edge);
    }

    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
    }

    public void addUndirectedEdge(Edge edge) {
        undirectedEdges.add(edge);
    }

    public void removeIncomingEdge(Edge edge) {
        incomingEdges.remove(edge);
    }

    public void removeOutgoingEdge(Edge edge) {
        outgoingEdges.remove(edge);
    }

    public void removeUndirectedEdge(Edge edge) {
        undirectedEdges.remove(edge);
    }

    public ArrayList<Edge> edges() {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.addAll(incomingEdges);
        edges.addAll(outgoingEdges);
        return edges;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
