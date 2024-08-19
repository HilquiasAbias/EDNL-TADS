import java.util.ArrayList;

public class Vertex {
    private Object value;
    private ArrayList<Edge> edgeList;

    public Vertex(Object value) {
        this.value = value;
        this.edgeList = new ArrayList<Edge>();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ArrayList<Edge> edges() {
        return edgeList;
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public boolean removeEdge(Edge edge) {
        return edgeList.remove(edge);
    }

    public String toString() {
        return value.toString();
    }
}
