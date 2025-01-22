package graph;

public class GraphGenericException extends Exception {
    public GraphGenericException(String message) {
        super(message);
    }
}

// import java.util.ArrayList;

// public class Graph implements IGraph {
// ArrayList<Vertex> vertexList;

// public Graph() {
// vertexList = new ArrayList<Vertex>();
// }

// public ArrayList<Vertex> finalVertex(Edge edge) {
// ArrayList<Vertex> vertices = new ArrayList<Vertex>();
// vertices.add(edge.getVertexIn());
// vertices.add(edge.getVertexOut());
// return vertices;
// }

// public Vertex opposite(Vertex vertex, Edge edge) throws GraphGenericException
// {
// if (edge.getVertexIn() != vertex) {
// throw new GraphGenericException("O vértice não é incidente a aresta");
// }
// return edge.getVertexOut();
// }

// public boolean isAdjacent(Vertex first, Vertex second) {
// for (Edge edge : first.edges()) {
// if (edge.getVertexIn() == second || edge.getVertexOut() == second) {
// return true;
// }
// }
// return false;
// }

// public void replaceVertexElement(Vertex vertex, Object element) throws
// GraphGenericException {
// try {
// vertexList.get(vertexList.indexOf(vertex)).setValue(element);
// } catch (IndexOutOfBoundsException e) {
// throw new GraphGenericException("Vértice não encontrado.");
// }
// }

// public void replaceEdgeElement(Edge edge, Object element) throws
// GraphGenericException {
// try {
// edge.setValue(element);
// } catch (IndexOutOfBoundsException e) {
// throw new GraphGenericException("Aresta não encontrada.");
// }
// }

// public Vertex insertVertex(Object element) {
// Vertex vertex = new Vertex(element);
// vertexList.add(vertex);
// return vertex;
// }

// public Edge insertEdge(Vertex first, Vertex second, Object element) {
// Edge edge = new Edge(element, false, first, second);
// first.addEdge(edge);
// second.addEdge(edge);
// return edge;
// }

// public Object removeVertex(Vertex vertex) throws GraphGenericException {
// if (!vertexList.remove(vertex)) {
// throw new GraphGenericException("Vértice não encontrado.");
// }
// for (Edge edge : vertex.edges()) {
// removeEdge(edge);
// }
// return vertex;
// }

// public Object removeEdge(Edge edge) throws GraphGenericException {
// if (!edge.getVertexIn().removeEdge(edge) &&
// !edge.getVertexOut().removeEdge(edge)) {
// throw new GraphGenericException("Aresta não encontrada");
// }
// return edge.getValue();
// }

// public ArrayList<Edge> incidentEdges(Vertex vertex) {
// return vertex.edges();
// }

// public ArrayList<Vertex> vertices() {
// return vertexList;
// }

// public ArrayList<Edge> edges() {
// ArrayList<Edge> edgeList = new ArrayList<Edge>();
// vertexList.forEach((vertex) -> {
// ArrayList<Edge> vertexEdges = vertex.edges();
// for (Edge edge : vertexEdges) {
// if (edge.isDirected()) {
// edgeList.add(edge);
// continue;
// }
// if (!edgeList.contains(edge)) {
// edgeList.add(edge);
// }
// }
// });
// return edgeList;
// }

// public boolean isDirected(Edge edge) {
// return edge.isDirected();
// }

// public void insertDirectedEdge(Vertex vertexOut, Vertex vertexIn, Object
// element) {
// Edge edge = new Edge(element, true, vertexOut, vertexIn);
// vertexIn.edges().add(edge);
// vertexOut.edges().add(edge);
// }
// }
