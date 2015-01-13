package graph;

import list.*;

public class Edge {

	protected int weight;
	protected Object vertex1;
	protected Object vertex2;
	protected DListNode node1;
	protected DListNode node2;

	public Edge(int weight, Object vertex1, Object vertex2, DListNode node1, DListNode node2) {
		this.weight = weight;
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.node1 = node1;
		this.node2 = node2;
	}

}