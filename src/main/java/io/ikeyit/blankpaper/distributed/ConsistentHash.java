package io.ikeyit.blankpaper.distributed;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A minimal consistent hash implementation using SortedMap as the ring.
 */
public class ConsistentHash {
    private final SortedMap<Long, Node> rings = new TreeMap<>();
    private final KeyHasher keyHasher;

    public ConsistentHash(KeyHasher keyHasher) {
        this.keyHasher = keyHasher;
    }

    public void addNode(RealNode realNode, int replicas) {
        for (int i = 0; i < replicas; i++) {
            Node node = new Node(realNode, i);
            rings.put(keyHasher.hash(node.getKey()), node);
        }
    }

    public void removeNode(RealNode realNode) {
        rings.entrySet().removeIf(e -> e.getValue().getRealNode().equals(realNode));
    }

    public RealNode route(String obj) {
        SortedMap<Long, Node> nodes = rings.tailMap(keyHasher.hash(obj));
        Long hash = nodes.isEmpty() ? rings.firstKey()  : nodes.firstKey();
        Node node = rings.get(hash);
        return node.getRealNode();
    }

    public static void main(String[] args) {
        ConsistentHash consistentHash = new ConsistentHash(new Murmur3KeyHasher());
        consistentHash.addNode(new RealNode("1.1"), 3);
        consistentHash.addNode(new RealNode("1.2"), 5);
        consistentHash.addNode(new RealNode("1.3"), 2);
        for(int i = 0; i < 20; i++)
            System.out.println("user" + i + ": " + consistentHash.route("user" + i));
        consistentHash.removeNode(new RealNode("1.2"));
        System.out.println("*****");
        for(int i = 0; i < 20; i++)
            System.out.println("user" + i + ": " + consistentHash.route("user" + i));
    }



}
