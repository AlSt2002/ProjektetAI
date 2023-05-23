package com.dfs;
import java.util.*;


          public class UnweightedGraph<V> implements Graph<V> {
  protected List<V> vertices = new ArrayList<>(); // Store vertices
  protected List<List<Edge>> neighbors
  = new ArrayList<>(); // Adjacency Edge lists

          /** Construct an empty graph */
          protected UnweightedGraph() {
             }

          /** Construct a graph from vertices and edges stored in arrays */
          protected UnweightedGraph(V[] vertices, int[][] edges) {
             for (int i = 0; i < vertices.length; i++)
                 addVertex(vertices[i]);

             createAdjacencyLists(edges, vertices.length);
             }

          /** Construct a graph from vertices and edges stored in List */
          protected UnweightedGraph(List<V> vertices, List<Edge> edges) {
             for (int i = 0; i < vertices.size(); i++)


             addVertex(vertices.get(i));

             createAdjacencyLists(edges, vertices.size());
             }


              protected UnweightedGraph(List<Edge> edges, int numberOfVertices) {
                   for (int i = 0; i < numberOfVertices; i++)
                       addVertex((V)(new Integer(i))); // vertices is {0, 1, . . . }

                   createAdjacencyLists(edges, numberOfVertices);
                   }

          /** Construct a graph from integer vertices 0, 1, and edge array */
          protected UnweightedGraph(int[][] edges, int numberOfVertices) {
                   for (int i = 0; i < numberOfVertices; i++)
                       addVertex((V)(new Integer(i))); // vertices is {0, 1, . . . }

                   createAdjacencyLists(edges, numberOfVertices);
                   }

          /** Create adjacency lists for each vertex */
          private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
                   for (int i = 0; i < edges.length; i++) {
                       addEdge(edges[i][0], edges[i][1]);
                       }
                   }

          /** Create adjacency lists for each vertex */
          private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
                   for (Edge edge: edges) {
                       addEdge(edge.u, edge.v);
                       }
                   }

                @Override /** Return the number of vertices in the graph */
                public int getSize() {
                   return vertices.size();
                   }

                @Override
                /** Return the vertices in the graph */
                public List<V> getVertices() {
                   return vertices;
                   }

                 @Override
                 /** Return the object for the specified vertex */
                public V getVertex(int index) {
                   return vertices.get(index);
                   }

                @Override
                /** Return the index for the specified vertex object */
                public int getIndex(V v) {
                   return vertices.indexOf(v);
                   }

                @Override
                /** Return the neighbors of the specified vertex */
                public List<Integer> getNeighbors(int index) {
                   List<Integer> result = new ArrayList<>();
                    for (Edge e: neighbors.get(index))
                         result.add(e.v);

                     return result;
                     }

                @Override
                /** Return the degree for a specified vertex */
                public int getDegree(int v) {
                   return neighbors.get(v).size();
                   }

                @Override
                /** Print the edges */
                public void printEdges() {
                   for (int u = 0; u < neighbors.size(); u++) {
                       System.out.print(getVertex(u) + " (" + u + "): ");
                       for (Edge e: neighbors.get(u)) {
                           System.out.print("(" + getVertex(e.u) + ", " +
                                   getVertex(e.v) + ") ");
                           }
                       System.out.println();
                       }
                   }

                @Override
                /** Clear the graph */
                public void clear() {
                   vertices.clear();
                   neighbors.clear();
                   }

                @Override
                /** Add a vertex to the graph */
                public boolean addVertex(V vertex) {
                   if (!vertices.contains(vertex)) {
                       vertices.add(vertex);
                       neighbors.add(new ArrayList<Edge>());
                       return true;
                       }
                   else {
                       return false;
                       }
                   }

                @Override
                /** Add an edge to the graph */
                public boolean addEdge(Edge e) {
                   if (e.u < 0 || e.u > getSize() − 1)
                   throw new IllegalArgumentException("No such index: " + e.u);

                   if (e.v < 0 || e.v > getSize() − 1)
                   throw new IllegalArgumentException("No such index: " + e.v);

                   if (!neighbors.get(e.u).contains(e)) {
                       neighbors.get(e.u).add(e);
                       return true;
                       }
                   else {
                       return false;
                       }
                   }


              @Override /** Add an edge to the graph */
                 public boolean addEdge(int u, int v) {

                   return addEdge(new Edge(u, v));
                   }

         @Override /** Obtain a DFS tree starting from vertex v */

         public SearchTree dfs(int v) {
                   List<Integer> searchOrder = new ArrayList<>();
                   int[] parent = new int[vertices.size()];
                   for (int i = 0; i < parent.length; i++)
                       // Initialize parent[i] to −1
                       parent[i] = −1;

                   // Mark visited vertices
                   boolean[] isVisited = new boolean[vertices.size()];

                   // Recursively search
                   dfs(v, parent, searchOrder, isVisited);

                   // Return a search tree
                   return new SearchTree(v, parent, searchOrder);
                   }

         private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited) {
                   // Store the visited vertex
                   searchOrder.add(v);
                   isVisited[v] = true; // Vertex v visited

                   for (Edge e : neighbors.get(v)) {// e.u is v
                       if (!isVisited[e.v]) {// e.v is w in Listing 28.8
                           parent[e.v] = v; // The parent of vertex w is v
                           dfs(e.v, parent, searchOrder, isVisited); // Recursive search
                           }
                       }
                   }

              public class SearchTree {
                private int root; // The root of the tree
                private int[] parent; // Store the parent of each vertex
                private List<Integer> searchOrder; // Store the search order

         /** Construct a tree with root, parent, and searchOrder */
         public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
                       this.root = root;
                       this.parent = parent;
                       this.searchOrder = searchOrder;
                       }

         /** Return the root of the tree */
         public int getRoot() {
                       return root;
                       }

         /** Return the parent of vertex v */
         public int getParent(int v) {
                       return parent[v];
                       }

         /** Return an array representing search order */
         public List<Integer> getSearchOrder() {
                       return searchOrder;
                       }

         /** Return number of vertices found */
         public int getNumberOfVerticesFound() {
                       return searchOrder.size();
                       }

         /** Return the path of vertices from a vertex to the root */
         public List<V> getPath(int index) {
                       ArrayList<V> path = new ArrayList<>();

                       do {
                           path.add(vertices.get(index));
                           index = parent[index];
                           }
                       while (index != −1);

                       return path;
                       }

         /** Print a path from the root to vertex v */
         public void printPath(int index) {
                       List<V> path = getPath(index);
                       System.out.print("A path from " + vertices.get(root) + " to " +
                              260 vertices.get(index) + ": ");
                       for (int i = path.size() − 1; i >= 0; i−−)
                       System.out.print(path.get(i) + " ");
                       }

/** Print the whole tree */
 public void printTree() {
                   System.out.println("Root is: " + vertices.get(root));
                   System.out.print("Edges: ");
                   for (int i = 0; i < parent.length; i++) {
                       if (parent[i] != −1) {
                           // Display an edge
                           System.out.print("(" + vertices.get(parent[i]) + ", " +
                                   vertices.get(i) + ") ");
                           }
                       }
                   System.out.println();
                   }
 }

         @Override
         /** Remove vertex v and return true if successful */
            public boolean remove(V v) {
                return true;
         }

         @Override
         /** Remove edge (u, v) and return true if successful */
        public boolean remove(int u, int v) {
         return true;
         }
         }

