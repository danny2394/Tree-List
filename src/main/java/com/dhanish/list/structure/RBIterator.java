package com.dhanish.list.structure;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * In Order Iterator for Red Black tree
 * Created by Dhanish Mehta on 2/13/17.
 */
public class RBIterator<E> implements Iterator{
    private Node<E> next; //storing the next node for the iterator
    private final RedBlackTree<E> tree;
    public RBIterator(RedBlackTree<E> tree){
        this.tree = tree;
        next = tree.getRoot();
        if (tree.isNil(next)){
            return;
        }

        //going to the left most node
        while(next.getLeft() != tree.nil){
            next = next.getLeft();
        }
    }

    @Override
    public boolean hasNext() {
        return !tree.isNil(next);
    }

    @Override
    public Object next() {
        if(!hasNext()) throw new NoSuchElementException();
        Node toReturn = next;

        //follow the in order paradigm
        if( !tree.isNil(next.getRight())){
            next = next.getRight();
            while (next.getLeft() != tree.nil)
                next = next.getLeft();
            return toReturn.getValue();
        }else while(true){
            if(tree.isNil(next.getParent())){
                next = tree.nil;
                return toReturn.getValue();
            }
            if(next.getParent().getLeft() == next){
                next = next.getParent();
                return toReturn.getValue();
            }
            next = next.getParent();
        }

    }
}
