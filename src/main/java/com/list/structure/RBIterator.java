package com.list.structure;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * In Order Iterator for Red Black tree
 * Created by Dhanish Mehta on 2/13/17.
 */
public class RBIterator<E> implements Iterator{
    private Node<E> next; //storing the next node for the iterator
    private final RedBlackTree<E> tree;

    /**
     * Constructor
     * @param tree the tree to which an iterator is required
     */
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

    /**
     * Returns if the iterator has a next element
     * @return true if there is another element
     */
    @Override
    public boolean hasNext() {
        return !tree.isNil(next);
    }

    /**
     * Returns the next element
     * @return the value at the next element
     */
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
