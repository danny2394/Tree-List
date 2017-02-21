package com.dhanish.list.structure.interfaces;

import com.dhanish.list.structure.Node;

/**
 * The interface for the RedBlackTree
 * Created by Dhanish on 12 Feb,17.
 */
public interface RedBlackTree<E> {

    /**
     * Inserts the element at the given index
     * @param index the location where to insert the element
     * @param value the value to store
     */
    void insert(final int index, final E value);

    /**
     * Inserts the element at the end of the tree
     * @param value the value to store
     */
    void insert(final E value);
    /**
     * Returns the root of the tree
     * @return root of tree
     */
    Node<E> getRoot();

    /**
     * Returns if the tree is empty
     * @return true if the tree is empty
     */
    boolean isEmpty();

    /**
     * Clears the tree
     */
    void clear();

    /**
     * Returns if the node is nil
     * @param toCheck the node to check
     * @return true if nil
     */
    boolean isNil(final Node<E> toCheck);

    /**
     * Returns the node at the index specified
     * This operation is performed in O(logN) time as the structure is a balancing binary tree
     *
     * @param index the index to get the node at
     * @return the node at the index
     */
    Node<E> get(final int index);

    /**
     * Deletes an element at the given index and updates the remaining indexes
     * @param index index whose node needs to be deleted
     * @return The node which was deleted
     */
    Node<E> delete(final int index);

    /**
     * Finds the node with the given value and returns its index
     * @param value the value to search for
     * @return index of the node with the value or -1
     */
    int getIndex(E value);

    /**
     * Sets the value of the node at the given index with the given value
     * Takes O(logN) to do
     * @param index the index of the node
     * @param value the value with which to replace with
     * @return the Value that was replaced
     */
    E set(final int index, final E value);
}
