package io.ikeyit.blankpaper.distributed;

public class Node {
    private RealNode realNode;

    private int index;

    public Node(RealNode node, int index) {
        this.realNode = node;
        this.index = index;
    }

    public RealNode getRealNode() {
        return realNode;
    }

    public String getKey() {
        return realNode.getKey() + "#" + index;
    }
}
