/* WUGraph.java */

package graph;

import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

  public HashTableChained adjacencyList;
  public HashTableChained edgeTable;
  public AdjList verticesList;


  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    edgeTable = new HashTableChained();
    adjacencyList = new HashTableChained();
    verticesList = new AdjList();
  }



  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
    return adjacencyList.size;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return edgeTable.size;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    try {
    Object[] vertices = new Object[verticesList.length()];
    AdjListNode holder = (AdjListNode) verticesList.front();
    for(int i = 0; i < verticesList.length(); i++) {
      vertices[i] = holder.item();
      holder = (AdjListNode) holder.next();
    }
    return vertices;
    } catch(InvalidNodeException e) {
      return null;
    }
  }


  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    if(adjacencyList.find(vertex) == null) {
      verticesList.insertBack(vertex);
      AdjListNode holder = (AdjListNode) verticesList.back();
      adjacencyList.insert(vertex, holder);
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    if(isVertex(vertex)) {
      try {
      int d = degree(vertex);
      Entry vertexEntry = adjacencyList.find(vertex);
      AdjListNode vertexNode = (AdjListNode) vertexEntry.value();
      DListNode n = (DListNode) vertexNode.adjList.front();
      
      for (int i = 0; i < d; i++) {
      /*while (n.isValidNode()) {*/
        VertexPair key = new VertexPair(vertex,n.item());
        Entry e = edgeTable.find(key);
        System.out.println("hi1");
        Edge edgeValues = (Edge) e.value();
        System.out.println("hi2");
        DListNode uFront = edgeValues.node1;
        System.out.println("hi3");
        DListNode vFront = edgeValues.node2;
        System.out.println("hi4");
        vFront.remove();
        System.out.println("hi5");
        n = (DListNode) n.next();
        System.out.println("hi6");
        edgeTable.remove(key);
        System.out.println("hi7");
      }

      adjacencyList.remove(vertex);
      vertexNode.remove();
      } catch(InvalidNodeException e) {
      }
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
    try{
    AdjListNode holder = (AdjListNode) verticesList.front();
    while(holder != null) {
      if(holder.item() == vertex) {
        return true;
      } else {
        holder = (AdjListNode) holder.next();
      }
    }
    return false;
    } catch(InvalidNodeException e) {
      return false;
    }
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    if(isVertex(vertex)) {
      return ((AdjListNode) adjacencyList.find(vertex).value()).adjList.length();
    } else {
      return 0;
    }
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    if(degree(vertex) == 0 || isVertex(vertex) != true) {
      return null;
      } else {
      try {
      int size = (((AdjListNode)adjacencyList.find(vertex).value()).adjList.length());
      Neighbors neighbor = new Neighbors();
      neighbor.neighborList = new Object[size];
      neighbor.weightList = new int[size];
      // System.out.println(adjacencyList);
      //System.out.println(((Object[]) ((AdjListNode) adjacencyList.find(vertex).value()).adjList.front().item()).length);
      DListNode holder = (DListNode) ((AdjListNode) adjacencyList.find(vertex).value()).adjList.front();
      for(int i = 0; i < size; i++) {
        VertexPair pair = new VertexPair(vertex, holder.item());
        Edge edge = (Edge) (edgeTable.find(pair).value());
            neighbor.neighborList[i] = holder.item();
            neighbor.weightList[i] = edge.weight;
            holder = (DListNode) holder.next();
      }
      for(int j = 0; j < neighbor.neighborList.length; j++) {
        // System.out.print(neighbor.weightList[j] + " ");
        
      }
      return neighbor;
      } catch(InvalidNodeException e) {
        return null;
      }
    }
  }



  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
    if (isVertex(u) && isVertex(v)) { //makes an edge if u and v are vertices
      VertexPair key = new VertexPair(u,v);
      if(edgeTable.find(key) != null) {
        ((Edge) edgeTable.find(key).value()).weight = weight;
        // System.out.println(weight);
      } else {
        Entry uEntry = adjacencyList.find(u);
        Entry vEntry = adjacencyList.find(v);

        AdjListNode uNode = (AdjListNode) uEntry.value();
        AdjListNode vNode = (AdjListNode) vEntry.value();

        Object uItem = u;
        Object vItem = v;

        if(u.equals(v)) {
          uNode.adjList.insertFront(vItem);
        } else {
          uNode.adjList.insertFront(vItem);
          vNode.adjList.insertFront(uItem);
        }
        // System.out.println(v);

        // try {
        //   System.out.println(((DList) ((AdjListNode) adjacencyList.find(u).value()).adjList).front().item());
        // } catch(InvalidNodeException e) {

        // }

        DListNode uFront = (DListNode) uNode.adjList.front();
        DListNode vFront = (DListNode) vNode.adjList.front();

        Edge edgeValues = new Edge(weight,u,v,uFront,vFront);

        
        edgeTable.insert(key,edgeValues);
      }
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
    try {
    if (isEdge(u,v)) {
      VertexPair key=new VertexPair(u,v);
      Entry e=edgeTable.find(key);
      Edge edgeValues=(Edge) e.value();
      DListNode uFront=edgeValues.node1;
      DListNode vFront=edgeValues.node2;

      uFront.remove();
      vFront.remove();

      edgeTable.remove(key);
    }
    } catch(InvalidNodeException e) {
    }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) { // chedck to see if hash table is null, not if both vertex exists
    VertexPair pair = new VertexPair(u, v);
    if (edgeTable.find(pair) != null) {
      VertexPair key=new VertexPair(u,v);
      Entry e = edgeTable.find(key);
      Edge values=(Edge) e.value();
      if ((values.vertex1)==(u) && (values.vertex2)==(v)){
        System.out.println("true1");
        return true;
      } else if((values.vertex1).equals(v) && (values.vertex2).equals(u)){
        System.out.println("true2");
        return true;
      } else {
        return false;
      }

    } else {
      return false;
    }
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    if (isEdge(u,v)) {
      VertexPair key = new VertexPair(u,v);
      Entry e = edgeTable.find(key);
      Edge values = (Edge) e.value();
      int weight = values.weight;
      return weight;
    } else {
      return 0;
    }
  }

}
