package io.ikeyit.blankpaper.alg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.*;

/**
 * A simple implementation to demonstrate the Aho-Corasick algorithm.
 * Reference: https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm
 */
public class AhoCorasick {
    static class Node {
        private Map<Character, Node> children = new HashMap<>();
        private String word;
        private char charater;
        private Node failureNode;
        private Node parent;
        private Object attachment;
        private Node followDictNode;

        public Node() {
        }

        public Node(char charater) {
            this.charater = charater;
        }

        public Node(String word) {
            this.word = word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getWord() {
            return word;
        }

        @JsonIgnore
        public Node getFailureNode() {
            return failureNode;
        }

        public void setFailureNode(Node failureNode) {
            this.failureNode = failureNode;
        }

        public char getCharater() {
            return charater;
        }

        public Collection<Node> getChildren() {
            return children.values();
        }

        public Node getParent() {
            return parent;
        }

        public void addChild(Node child) {
            children.put(child.charater, child);
            child.parent = this;
        }

        public Node find(Character character) {
            return children.get(character);
        }

        public Object getAttachment() {
            return attachment;
        }

        public void setAttachment(Object attachment) {
            this.attachment = attachment;
        }

        public Node getFollowDictNode() {
            return followDictNode;
        }

        public void setFollowDictNode(Node followDictNode) {
            this.followDictNode = followDictNode;
        }
    }

    public static Node buildTrie( String[] dictionary) {
        Node root = new Node();
        for (String word : dictionary) {
            Node currentNode = root;
            for (int i = 0 ;i < word.length(); i++) {
                char character = word.charAt(i);
                Node node = currentNode.find(character);
                if (node == null) {
                    node = new Node(character);
                    currentNode.addChild(node);
                }
                currentNode = node;
            }
            currentNode.setWord(word);
        }

        for (Node node: root.getChildren()) {
            node.setFailureNode(root);
        }

        Queue<Node> queue = new LinkedList<>();
        for (Node node: root.getChildren()) {
            node.setFailureNode(root);
            queue.addAll(node.getChildren());
        }

        Node node = null;
        while((node = queue.poll()) != null) {
            queue.addAll(node.getChildren());
            Node failureNode = node.parent.failureNode;
            while (failureNode != null) {
                Node found = failureNode.find(node.charater);
                if (found != null) {
                    node.setFailureNode(found);
                    break;
                }
                failureNode = failureNode.failureNode;
            }
            if (failureNode == null) {
                node.setFailureNode(root);
            }
        }

        // 记录后缀路径中存在于字典中的节点。 加快查询效率
        queue.addAll(root.getChildren());
        while((node = queue.poll()) != null) {
            queue.addAll(node.getChildren());
            Node failureNode = node.failureNode;
            while (failureNode != null) {
                if (failureNode.word != null) {
                    node.setFollowDictNode(failureNode);
                    break;
                } else {
                    failureNode = failureNode.failureNode;
                }
            }
        }
        return root;
    }

    // 输出当前节点
    public static void outputWord(Node node, Set<String> output) {
        System.out.println("Current: " + node.charater);
        if (node.word !=  null) {
            System.out.println("Output[C]: " + node.word);
            output.add(node.word);
        }
        Node dictNode = node.followDictNode;
        while(dictNode != null) {
            System.out.println("Output[S]: " + dictNode.word);
            output.add(dictNode.word);
            dictNode = dictNode.followDictNode;
        }
    }

    // 匹配字符串
    public static Set<String> match(Node root, String input) {
        Set<String> output = new HashSet<>();
        int len = input.length();
        Node currentNode = root;
        for (int i = 0; i < len; i++) {
            outputWord(currentNode, output);
            char character = input.charAt(i);
            Node findNode = currentNode;
            while (findNode != null) {
                Node found = findNode.find(character);
                if (found != null) {
                    currentNode = found;
                    break;
                }
                findNode = findNode.failureNode;
            }
            if (findNode == null) {
                currentNode = root;
            }
        }
        outputWord(currentNode, output);
        return output;
    }

    private static Font font = new Font("TimesRoman", Font.PLAIN, 20);

    private static int NODE_HEIGHT = 30;

    private static int NODE_WIDTH = 80;

    private static int LEVEL_HEIGHT = 80;

    private static int END_ARROW_SIZE = 8;

    public static void main(String[] args) throws JsonProcessingException {
        String[] dictionary =  {"a", "ab", "bab", "bc", "bca", "c", "caa"};
        Node root = buildTrie(dictionary);
        Set<String> output = match(root, "abccab");
        System.out.println(output);
        // visualize the tries
        JFrame f = new JFrame();
        f.setSize(1024, 1024);
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(font);
                g2.setStroke(new BasicStroke(2));
                Rectangle rectangle = new Rectangle(0, 0, getWidth(), LEVEL_HEIGHT);
                g2.drawRect((int)rectangle.getCenterX() - NODE_WIDTH / 2, (int) rectangle.getCenterY() - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
                root.setAttachment(rectangle);
                drawTries(g2, root);
                drawFailureLine(g2, root);
            }
        };
        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private static void drawTries(Graphics2D g2, Node parent) {
        Rectangle parentRect = (Rectangle) parent.getAttachment();
        Collection<Node> children = parent.getChildren();
        int childrenCount = children.size();
        int childRectWidth = (int) (parentRect.getWidth() / childrenCount);
        int i = 0;
        for (Node child : children) {
            Rectangle childRect = new Rectangle((int)parentRect.getX() + childRectWidth * i, (int)parentRect.getY() + LEVEL_HEIGHT, childRectWidth, LEVEL_HEIGHT);
            if (child.word != null) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.BLACK);
            }
            g2.drawRect((int)childRect.getCenterX() - NODE_WIDTH / 2, (int) childRect.getCenterY() - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
            g2.drawString(child.charater +  (child.word != null ? ":" + child.word : ""), (int)childRect.getCenterX() - NODE_WIDTH / 2 + 8, (int) childRect.getCenterY() + 5);
            g2.drawLine((int)parentRect.getCenterX(), (int) parentRect.getCenterY() + NODE_HEIGHT / 2, (int)childRect.getCenterX(), (int) childRect.getCenterY() - NODE_HEIGHT / 2);
            child.setAttachment(childRect);
            drawTries(g2, child);
            i++;
        }
    }

    private static void drawFailureLine(Graphics2D g2, Node parent) {
        Collection<Node> children = parent.getChildren();
        for (Node child : children) {
            Node failureNode = child.failureNode;
            Rectangle failureRect = (Rectangle) failureNode.getAttachment();
            Rectangle childRect = (Rectangle) child.getAttachment();
            g2.setColor(Color.BLUE);
            g2.drawLine((int)childRect.getCenterX() - NODE_WIDTH / 2, (int) childRect.getCenterY() - NODE_HEIGHT / 2, (int)failureRect.getCenterX() + NODE_WIDTH / 2, (int) failureRect.getCenterY() - NODE_HEIGHT / 2);
            g2.fillOval((int)failureRect.getCenterX() + NODE_WIDTH / 2 - END_ARROW_SIZE / 2, (int) failureRect.getCenterY() - NODE_HEIGHT / 2 - END_ARROW_SIZE / 2, END_ARROW_SIZE, END_ARROW_SIZE);
            if (child.followDictNode != null) {
                g2.setColor(Color.GREEN);
                Rectangle dictRect = (Rectangle) child.followDictNode.getAttachment();
                g2.drawLine((int) childRect.getCenterX() - NODE_WIDTH / 2, (int) childRect.getCenterY() + NODE_HEIGHT / 2, (int) dictRect.getCenterX() + NODE_WIDTH / 2, (int) dictRect.getCenterY() + NODE_HEIGHT / 2);
                g2.fillOval((int) dictRect.getCenterX() + NODE_WIDTH / 2 - END_ARROW_SIZE / 2, (int) dictRect.getCenterY() + NODE_HEIGHT / 2 - END_ARROW_SIZE / 2, END_ARROW_SIZE, END_ARROW_SIZE);
            }

            drawFailureLine(g2, child);
        }
    }

}
