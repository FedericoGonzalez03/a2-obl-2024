package impl;

import adt.ABB;

public class AVLImpl<T extends Comparable<T>> implements ABB<T> {

    private class Node {
        public T data;
        public Node left;
        public Node right;
        public int height;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node rootNode = null;

    private int balanceFactor(Node node) {
        if (node == null) return 0;
        return safeHeight(node.right) - safeHeight(node.left);
    }

    private int safeHeight(Node node) {
        return node != null ? node.height : 0;
    }

    private Node leftRotation(Node node) {
        if (node == null || node.right == null) return node;
        Node z = node;
        Node y = node.right;
        z.right = y.left;
        y.left = z;

        z.height = 1 + Math.max(safeHeight(z.left), safeHeight(z.right));
        y.height = 1 + Math.max(safeHeight(y.left), safeHeight(y.right));
        return y;
    }

    private Node rightRotation(Node node) {
        if (node == null || node.left == null) return node;
        Node z = node;
        Node y = node.left;
        z.left = y.right;
        y.right = z;

        z.height = 1 + Math.max(safeHeight(z.left), safeHeight(z.right));
        y.height = 1 + Math.max(safeHeight(y.left), safeHeight(y.right));
        return y;
    }

    private Node leftRightRotation(Node node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    private Node rightLeftRotation(Node node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    private Node rebalance(Node node) {
        int bfz = balanceFactor(node);
        if (bfz > 1) { // desbalance der-x
            int bfy = balanceFactor(node.right);
            if (bfy < 0) { // desbalance der-izq
                node = rightLeftRotation(node);
            } else { // desbalance der-der
                node = leftRotation(node);
            }
        } else if (bfz < -1) { // desbalance izq-x
            int bfy = balanceFactor(node.left);
            if (bfy < 0) { // desbalance izq-der
                node = leftRightRotation(node);
            } else { // desbalance izq-izq
                node = rightRotation(node);
            }
        }
        return node;
    }

    private Node insert(Node node, T data) {
        if (node == null) return new Node(data);
        if (data.compareTo(node.data) <= 0) 
             node.left  = insert(node.left, data);
        else node.right = insert(node.right, data);

        node.height = 1 + Math.max(safeHeight(node.left), safeHeight(node.right));

        return rebalance(node);
    }

    @Override
    public void insert(T data) {
        rootNode = insert(rootNode, data);
    }

    private Node remove(T data, Node node) {
        if (node == null) return null;
        if      (data.compareTo(node.data) < 0) node.left  = remove(data, node.left);
        else if (data.compareTo(node.data) > 0) node.right = remove(data, node.right);
        else {
            if (node.left == null && node.right == null) return null;
            if (node.left == null)
                node = node.right;
            else if (node.right == null)
                node = node.left;
            else {
                Node minOfRightTree = node.right;
                while (minOfRightTree.left != null) {
                    minOfRightTree = minOfRightTree.left;
                }
                node.data = minOfRightTree.data;
                node.right = remove(minOfRightTree.data, node.right);
            }
        }
        if (node != null) node.height = 1 + Math.max(safeHeight(node.left), safeHeight(node.right));
        return rebalance(node);
    }

    @Override
    public void remove(T data) {
        rootNode = remove(data, rootNode);
    }

    private boolean contains(T data, Node node) {
        if (node == null) return false;
        if (node.data.equals(data)) return true;
        if (data.compareTo(node.data) < 0) return contains(data, node.left);
        return contains(data, node.right);
    }

    @Override
    public boolean contains(T data) {
        return contains(data, rootNode);
    }

    private T get(T data, Node node) {
        if (node == null) return null;
        if (node.data.equals(data)) return node.data;
        if (data.compareTo(node.data) < 0) return get(data, node.left);
        return get(data, node.right);
    }

    @Override
    public T get(T data) {
        return get(data, rootNode);
    }

    @Override
    public T max() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'max'");
    }

    @Override
    public T min() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'min'");
    }

    private int count(Node node) {
        if (node == null) return 0;
        return 1 + count(node.left) + count(node.right);
    }

    @Override
    public int count() {
        return count(rootNode);
    }

    @Override
    public void printInOrder() {
        printInOrder(rootNode);
    }

    private void printInOrder(Node node) {
        if(node == null) return;
        printInOrder(node.left);
        System.out.print(node.data);
        printInOrder(node.right);
    }

    public void printGraph() {
        printGraph(rootNode, 0);
    }

    private void printGraph(Node node, int level) {
        if (node == null) return;
        printGraph(node.right, level + 1);
        for (int i = 0; i < level; i++) System.out.print("        ");
        System.out.println(node.data);
        printGraph(node.left, level + 1);
    }

}
