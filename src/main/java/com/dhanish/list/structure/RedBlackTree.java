package com.dhanish.list.structure;


import java.util.Iterator;

/**
 * Self balancing red black tree structure
 * Created by Dhanish on 12 Feb,17.
 */
public class RedBlackTree<E>  {
    private  Node<E> root;
    private final Node<E> nil = new Node<>(); //nil node for the leafs

    public RedBlackTree(){
        root = nil;
        root.setParent(nil);
    }

    public boolean isEmpty(){
        return root == nil;
    }
    public void insert(final int index, final E value){

        Node<E> y = nil;
        Node<E> x = root;
        Node<E> toInsert = new Node<>(index, value);

        while(!isNil(x)){
            y = x;
            if (toInsert.getIndex() < x.getIndex()){
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        toInsert.setParent(y);
        if (isNil(y)){
            root = toInsert;
        } else if (toInsert.getIndex() < y.getIndex()){
            y.setLeft(toInsert);
        } else {
            y.setRight(toInsert);
        }
        toInsert.setLeft(nil);
        toInsert.setRight(nil);
        toInsert.setColor(Node.Color.RED);
        insertFix(toInsert);
    }

    public void insertFix(Node<E> inserted){

        while(inserted.getParent().getColor() == Node.Color.RED){
            if (inserted.getParent() == inserted.getParent().getParent().getLeft()){
                Node<E> y = inserted.getParent().getParent().getRight();
                if (y.getColor() == Node.Color.RED){
                    //case 1 where the parents are red, this violates the property of a red parent
                    // having red child
                    inserted.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);
                    inserted.getParent().getParent().setColor(Node.Color.RED);
                    inserted = inserted.getParent().getParent();
                } else {
                    if (inserted == inserted.getParent().getRight()) {
                        //case 2, here the parents sibling is black and the parent is red and the child is red
                        inserted = inserted.getParent();
                        leftRotate(inserted);
                    }
                    //case 3 now the inserted node is the left child of the parent
                    inserted.getParent().setColor(Node.Color.BLACK);
                    inserted.getParent().getParent().setColor(Node.Color.RED);
                    rightRotate(inserted.getParent().getParent());
                }
            } else {
                Node<E> y = inserted.getParent().getParent().getLeft();
                if (y.getColor() == Node.Color.RED){
                    //case 1
                    inserted.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);
                    inserted.getParent().getParent().setColor(Node.Color.RED);
                    inserted = inserted.getParent().getParent();
                } else {
                    if (inserted == inserted.getParent().getLeft()) {
                        //case 2
                        inserted = inserted.getParent();
                        rightRotate(inserted);
                    }
                    //case 3
                    inserted.getParent().setColor(Node.Color.BLACK);
                    inserted.getParent().getParent().setColor(Node.Color.RED);
                    leftRotate(inserted.getParent().getParent());
                }

            }
        }
        root.setColor(Node.Color.BLACK);
    }


    private boolean isNil(final Node<E> toCheck){
        return toCheck == nil;
    }


    public void leftRotate(final Node<E> x){
        Node<E> y = x.getRight();
        x.setRight(y.getLeft()); //turing y's left subtree to x's right subtree

        if (!isNil(y.getLeft())){ //setting the parent for the now right node of x
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent()); //link x's parent to y

        if (isNil(x.getParent())){
            root = y;
        } else if(x == x.getParent().getLeft()){
            x.getParent().setLeft(y);
        } else{
            x.getParent().setRight(y);
        }
        y.setLeft(x); //putting x on y's left
        x.setParent(y);

    }

    public void rightRotate(final Node<E> y){
        Node<E> x = y.getLeft();
        y.setLeft(y.getRight()); //turing x's left subtree to y's left subtree

        if (!isNil(x.getRight())){ //setting the parent for the now left node of y
            x.getRight().setParent(y);
        }

        x.setParent(y.getParent()); //link y's parent to x

        if (isNil(y.getParent())){
            root = x;
        } else if(y == y.getParent().getRight()){
            y.getParent().setRight(x);
        } else{
            y.getParent().setLeft(x);
        }
        x.setRight(y); //putting y on x's right
        y.setParent(x);
    }

    public E get(final int index){
        Node<E> searcher = root;

        while (searcher != nil){
            if (index == searcher.getIndex()){
                return searcher.getValue();
            } else if (index < searcher.getIndex()){
                searcher = searcher.getLeft();
            } else {
                searcher = searcher.getRight();
            }
        }
        return null;
    }

    public Iterator<E> getIterator(){
        return new Iterator<E>() {
            private Node<E> next = root;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                Node<E> r = next;

                while (!hasNext()){

                }
            }
        };
    }
}
