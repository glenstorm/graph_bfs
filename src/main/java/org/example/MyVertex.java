package org.example;

import java.util.Set;
import java.util.TreeSet;



public class MyVertex {

    enum VertexColor {
        WHITE,
        GREY,
        BLACK,
    };

    public MyVertex() {
        color = VertexColor.WHITE;
    }

    public void addRoute( int route )
    {
        myRoutes.add(route);
    }

    public VertexColor getColor() {
        return color;
    }

    public void setColor(VertexColor color) {
        this.color = color;
    }
    private final Set<Integer> myRoutes = new TreeSet<>();
    private VertexColor color;
}
