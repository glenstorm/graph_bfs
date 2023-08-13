package org.example;

import java.util.ArrayList;
import java.util.List;

enum VertexColor {
    WHITE,
    GREY,
    BLACK,
}

public class MyVertex {

    public MyVertex() {
        color = VertexColor.WHITE;
    }

    public void addRoute( int route, int start )
    {
        myRoutes.add(route);
        fromVertexes.add(start);
    }

    public VertexColor getColor() {
        return color;
    }

    public void setColor(VertexColor color) {
        this.color = color;
    }

    public boolean isPathExists(int route, int from)
    {
        int routeidx = myRoutes.indexOf(route);
        if (routeidx != -1) {
            return fromVertexes.get(routeidx).equals(from);
        }

        return false;
    }

    public List<Integer> getMyRoutes() {
        return myRoutes;
    }

    private final List<Integer> myRoutes = new ArrayList<>();
    private final List<Integer> fromVertexes = new ArrayList<>(); // identify edge to traverse
    private VertexColor color;
}
