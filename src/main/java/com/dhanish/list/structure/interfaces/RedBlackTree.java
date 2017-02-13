package com.dhanish.list.structure.interfaces;

import com.dhanish.list.structure.Node;

/**
 * Created by Dhanish on 13 Feb,17.
 */
public interface RedBlackTree<E> {

    void insert(final int index, final E value);
    Node<E> getRoot();
    boolean isEmpty();
    void clear();
    boolean isNil(final Node<E> toCheck);
    Node<E> get(final int index);
    Node<E> delete(final int index);


}
