package com.pojo;

public class Edge {
    private int id;
    private int mark1;
    private int mark2;
    private String distance;
    private String walkTime;
    private String rideTime;

    public Edge() {
    }

    public Edge(int id, int mark1, int mark2, String distance, String walkTime, String rideTime) {
        this.id = id;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.distance = distance;
        this.walkTime = walkTime;
        this.rideTime = rideTime;
    }

    //判断 一个顶点是否与其相连
    public boolean isConnect(Vertex vertex) {
        return vertex != null && (vertex.getMark() == mark1 || vertex.getMark() == mark2);
    }

    //获取weight  以distance
    public int getWeightByDistance() {
        return Integer.parseInt(distance.substring(0, distance.length() - 1));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark1() {
        return mark1;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public void setMark2(int mark2) {
        this.mark2 = mark2;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", mark1=" + mark1 +
                ", mark2=" + mark2 +
                ", distance='" + distance + '\'' +
                ", walkTime='" + walkTime + '\'' +
                ", rideTime='" + rideTime + '\'' +
                '}';
    }

    /*
    如果除了id外其他都相同  则认为相同
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (mark1 != edge.mark1) return false;
        if (mark2 != edge.mark2) return false;
        if (distance != null ? !distance.equals(edge.distance) : edge.distance != null) return false;
        if (walkTime != null ? !walkTime.equals(edge.walkTime) : edge.walkTime != null) return false;
        return rideTime != null ? rideTime.equals(edge.rideTime) : edge.rideTime == null;
    }


}
