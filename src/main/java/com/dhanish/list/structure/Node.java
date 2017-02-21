package com.dhanish.list.structure;

/**
 * Class to handle elements of the tree
 * Created by Dhanish on 12 Feb,17.
 */
public class Node<E> {

    public enum Color{
        RED,BLACK
    }
    private E value;
    private Node<E> left;
    private Node<E> right;
    private Node<E> parent;
    private Color color;
    private int leftChildren;
    private int rightChildren;

    Node(){
        setColor(Color.BLACK);
    }
    Node(final E value){
        setValue(value);
        setColor(Color.RED);
    }

    Node<E> getLeft(){ return left;}

    Node<E> getRight(){
        return right;
    }

    void setLeft(final Node<E> left){
        this.left = left;
    }

    void setRight(final Node<E> right){
        this.right = right;
    }

    Node<E> getParent(){
        return  parent;
    }

    void setParent(final Node<E> parent){
        this.parent = parent;
    }

    public Color getColor() {
        return color;
    }

    void setColor(Color color) {
        this.color = color;
    }

    public E getValue() {
        return value;
    }

    void setValue(E value) {
        this.value = value;
    }


    int getRightChildren() {
        return rightChildren;
    }

    void setRightChildren(int rightChildren) {
        this.rightChildren = rightChildren;
    }

    int getLeftChildren() {
        return leftChildren;
    }

    void setLeftChildren(int leftChildren) {
        this.leftChildren = leftChildren;
    }

}
