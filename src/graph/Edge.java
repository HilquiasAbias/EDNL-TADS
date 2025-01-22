package graph;

public class Edge {
    private Object value;
    private Vertex vertexOut;
    private Vertex vertexIn;
    private boolean isDirected;

    public Edge(Object value, boolean isDirected, Vertex vertexOut, Vertex vertexIn) {
        this.value = value;
        this.isDirected = isDirected;
        this.vertexOut = vertexOut;
        this.vertexIn = vertexIn;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setIfIsDirected(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public Vertex getVertexIn() {
        return vertexIn;
    }

    public void setVertexIn(Vertex vertexIn) {
        this.vertexIn = vertexIn;
    }

    public Vertex getVertexOut() {
        return vertexOut;
    }

    public void setVertexOut(Vertex vertexOut) {
        this.vertexOut = vertexOut;
    }

    public String toString() {
        return value.toString();
    }
}
