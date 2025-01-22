package graph;

import java.util.ArrayList;

public interface IGraph {
    // Retorna um array armazenando os vértices finais da aresta "edge"
    ArrayList<Vertex> finalVertex(Edge edge);

    // Retorna o vértice oposto de "vertex" em "edge", ou sejao vértice final da
    // aresta "edge" separado do vértice "vertex".
    // Um erro ocorre se "edge" não é incidente a "vertex"
    Vertex opposite(Vertex vertex, Edge edge) throws GraphGenericException;

    // Retorna true se "fisrt" e "second" são adjacentes
    boolean isAdjacent(Vertex first, Vertex second);

    // Substitui o elemento armazenado do vértice "vertex" com "element"
    void replaceVertexElement(Vertex vertex, Object element) throws GraphGenericException;

    // Substitui o elemento armazenado da aresta "edge" com "element"
    void replaceEdgeElement(Edge edge, Object element) throws GraphGenericException;

    // Insere e retorna um novo vértice armazenando o elemento "element"
    Vertex insertVertex(Object element);

    // Insere e retorna uma nova aresta não-dirigida (first, second)
    // armazenando o elemento "element"
    Edge insertEdge(Vertex first, Vertex second, Object element);

    // Remove o vértice "vertex" ( e todas as arestas incidentes) e retorna o
    // elemento
    // armazenado em "vertex"
    Object removeVertex(Vertex vertex) throws GraphGenericException;

    // Remove a aresta "edge", retornando o elemento armazenado
    Object removeEdge(Edge edge) throws GraphGenericException;

    // Retorna uma coleção de todas as arestas incidentes sob o
    // vértice v (vértice v)
    ArrayList<Edge> incidentEdges(Vertex vertex);

    // Retorna uma coleção de todos os vértices no grafo.
    ArrayList<Vertex> vertices();

    // Retorna uma coleção de todas as arestas no grafo
    ArrayList<Edge> edges();

    // Testa se a aresta é direcionada
    boolean isDirected(Edge edge);

    // Insirir uma nova aresta dirigida com origem em "first" e destino
    // em "second" e armazenando o elemento "element".
    void insertDirectedEdge(Vertex first, Vertex second, Object element);
}
