package io.ikeyit.blankpaper.distributed;

import java.util.Objects;

public class RealNode {
    private String address;

    public RealNode(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getKey() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RealNode realNode = (RealNode) o;
        return address.equals(realNode.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "RealNode{" +
                "address='" + address + '\'' +
                '}';
    }
}
