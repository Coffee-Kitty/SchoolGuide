package com.pojo;

public class Vertex {
    private int id;
    private String name;
    private int mark;
    private String data;
    private int left;
    private int top;

    public Vertex() {
    }

    public Vertex(int id, String name, int mark, String data) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.data = data;
    }

    public Vertex(String name, int mark, String data) {
        this.name = name;
        this.mark = mark;
        this.data = data;
    }

    public Vertex(int id, String name, int mark, String data, int left, int top) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.data = data;
        this.left = left;
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                ", data='" + data + '\'' +
                ", left=" + left +
                ", top=" + top +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (id != vertex.id) return false;
        if (mark != vertex.mark) return false;
        if (left != vertex.left) return false;
        if (top != vertex.top) return false;
        if (!name.equals(vertex.name)) return false;
        return data.equals(vertex.data);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + mark;
        result = 31 * result + data.hashCode();
        result = 31 * result + left;
        result = 31 * result + top;
        return result;
    }
}
