package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import static java.lang.Math.max;
import static org.example.MyVertex.VertexColor.*;

public class Graph {
    public void addEdge(int vertex_start, int vertex_end, int route_num) {
        int max_vertex = max(vertex_start, vertex_end);
        // should grow simultaneously
        if(adjList_.size() <= max_vertex) {
            adjList_.ensureCapacity(max_vertex + 1);
            vertexData_.ensureCapacity(max_vertex + 1);
        }

        while (adjList_.size() < max_vertex + 1) {
            adjList_.add(new ArrayList<>());
            vertexData_.add(new MyVertex());
        }

        adjList_.get(vertex_start).add(vertex_end);
        vertexData_.get(vertex_end).addRoute(route_num);
    }

    public void addVertex(int vertex_start, int route_num) {
        if(adjList_.size() <= vertex_start) {
            adjList_.ensureCapacity(vertex_start + 1);
            vertexData_.ensureCapacity(vertex_start + 1);

            while (adjList_.size() < vertex_start + 1) {
                adjList_.add(new ArrayList<>());
                vertexData_.add(new MyVertex());
            }
        }
        vertexData_.get(vertex_start).addRoute(route_num);
    }

    private void prepareGraph()
    {
        for(int i = 0; i < adjList_.size(); ++i )
        {
            vertexData_.get(i).setColor(WHITE);
        }
    }

    // simple bfs alg
    public boolean isPathExists(int start, int end)
    {
        prepareGraph();
        // Create a queue for BFS
        LinkedList<Integer> queue  = new LinkedList<>();
        vertexData_.get(start).setColor(GREY);

        queue.add(start);
        while(!queue.isEmpty()) {

            // Dequeue a vertex from queue and print it
            int next = queue.poll();

            // then mark it visited and enqueue it
            Iterator<Integer> i = adjList_.get(next).listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (vertexData_.get(n).getColor() == WHITE) {
                    vertexData_.get(n).setColor(GREY);
                    if (n == end) {
                        return true;
                    }
                    queue.add(n);
                }
            }

            vertexData_.get(next).setColor(BLACK);
        }

        return false;
    }

    // vertex --> edges
    ArrayList<ArrayList<Integer>> adjList_ = new ArrayList<>();
    // vertex --> vertex_prop
    ArrayList<MyVertex> vertexData_ = new ArrayList<>();
}
