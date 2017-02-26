package com.list.structure;

import java.util.Stack;

/**
 * Self balancing red black tree structure
 * The tree uses the Node class which holds the value and the number
 * of children on the left and right,
 *
 * I use the number of children to calculate the index
 * to emulate a list so I can keep track of the order of insertion
 * The value is used to store the actual value
 * Insert and delete operations take O(logN)
 * Created by Dhanish on 12 Feb,17.
 */
public class RedBlackTree<E> implements com.list.structure.interfaces.RedBlackTree<E> {
    private Node<E> root;
    final Node<E> nil = new Node<>(); //nil node for the leafs

    /**
     * Constructor to initialize the structure
     */
    public RedBlackTree() {
        root = nil;
        root.setParent(nil);
    }

    /**
     * Returns the root of the tree
     * @return root of tree
     */
    @Override
    public Node<E> getRoot() {
        return root;
    }

    /**
     * Returns if the tree is empty
     * @return true if the tree is empty
     */
    @Override
    public boolean isEmpty() {
        return root == nil;
    }

    /**
     * Clears the tree
     */
    @Override
    public void clear() {
        root = nil;
        root.setParent(nil);
    }

    /**
     * Inserts a element to the end of the tree
     * @param value the value to store
     */
    @Override
    public void insert(final E value) {
        int indexesVisited = 0;
        Node<E> y = nil;
        Node<E> x = root;
        Node<E> toInsert = new Node<>(value);
        final int index = getSizeAtNode(root);

        while (!isNil(x)) {
            y = x;
            if (index <= x.getLeftChildren() + indexesVisited) {
                x = x.getLeft();
            } else {
                indexesVisited += x.getLeftChildren() + 1;
                x = x.getRight();
            }
        }
        indexesVisited -= 1;
        toInsert.setParent(y);
        if (isNil(y)) {
            root = toInsert;
        } else if (index < indexesVisited) {
            y.setLeft(toInsert);
            y.setLeftChildren(toInsert.getLeftChildren() + 1);
        } else if (index > indexesVisited){
            y.setRight(toInsert);
            y.setRightChildren(toInsert.getRightChildren() + 1);
        }
        toInsert.setLeft(nil);
        toInsert.setRight(nil);
        toInsert.setColor(Node.Color.RED);
        fixParentsChildren(toInsert);
        insertFix(toInsert);
    }

    /**
     * Inserts the element at the given index
     * @param index the location where to insert the element
     * @param value the value to store
     */
    public void insert(final int index, final E value){

        Node<E> toInsert = new Node<>(value);

        Node<E> current = get(index);

        if (isNil(current.getParent())){
            //the current element we are replacing is the root
            root = toInsert;
            toInsert.setParent(nil);
        } else {
            toInsert.setParent(current.getParent());
        }
        //getting the currents left children and setting them to the new ones left
        toInsert.setLeft(current.getLeft());
        toInsert.setLeftChildren(getSizeAtNode(current.getLeft()));
        //setting the currents left to nil so no repeated elements
        current.setLeft(nil);
        current.setLeftChildren(0);

        toInsert.setRight(current);
        toInsert.setRightChildren(getSizeAtNode(current));

        if (current == current.getParent().getLeft()){
            current.getParent().setLeft(toInsert);
        } else if (current == current.getParent().getRight()) {
            current.getParent().setRight(toInsert);
        }

        current.setParent(toInsert);

        toInsert.setColor(Node.Color.RED);
        fixParentsChildren(toInsert);
        insertFix(toInsert);
    }

    /**
     * When insertions are made, the number of children in
     * the parent nodes might become outdated and need to be updated
     * @param node the node that was inserted
     */
    private void fixParentsChildren(Node<E> node){
        while (!isNil(node)){
            if (node == node.getParent().getLeft()){
                node.getParent().setLeftChildren(getSizeAtNode(node));
            } else if (node == node.getParent().getRight()){ //this if condition is to get rid of
                // the algorithm setting children for nil nodes
                node.getParent().setRightChildren(getSizeAtNode(node));
            }
            node = node.getParent();
        }
    }
    /**
     * Inserting an element in the tree can cause various violations of the red-black
     * property of the tree
     * @param inserted the node that was inserted
     */
    private void insertFix(Node<E> inserted) {

        while (inserted.getParent().getColor() == Node.Color.RED) {
            if (inserted.getParent() == inserted.getParent().getParent().getLeft()) {
                Node<E> y = inserted.getParent().getParent().getRight();
                if (y.getColor() == Node.Color.RED) {
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
                if (y.getColor() == Node.Color.RED) {
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

    /**
     * Returns if the node is nil
     * @param toCheck the node to check
     * @return true if nil
     */
    @Override
    public boolean isNil(final Node<E> toCheck) {
        return toCheck == nil;
    }


    /**
     * Rotates the given node x and its parent in a left orientation
     * @param x the node to rotate
     */
    private void leftRotate(final Node<E> x) {
        Node<E> y = x.getRight();
        x.setRight(y.getLeft()); //turing y's left subtree to x's right subtree
        x.setRightChildren(getSizeAtNode(y.getLeft()));

        if (!isNil(y.getLeft())) { //setting the parent for the now right node of x
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent()); //link x's parent to y

        y.setLeft(x); //putting x on y's left
        y.setLeftChildren(getSizeAtNode(x));


        if (isNil(x.getParent())) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
            x.getParent().setLeftChildren(getSizeAtNode(y));
        } else {
            x.getParent().setRight(y);
            x.getParent().setRightChildren(getSizeAtNode(y));
        }

        x.setParent(y);

    }

    /**
     * Gets the size of the left subtree plus right subtree plus the node itself
     * if its not nil
     * @param node whose size is needed
     * @return the size of the tree at node
     */
    private int getSizeAtNode(final Node<E> node){
        return node.getLeftChildren() + node.getRightChildren() + (isNil(node) ? 0:1);
    }

    /**
     * Rotates the given node y and its parent in a right orientation
     * @param y the node to rotate
     */
    private void rightRotate(final Node<E> y) {
        Node<E> x = y.getLeft();
        y.setLeft(x.getRight()); //turing x's left subtree to y's left subtree
        y.setLeftChildren(getSizeAtNode(y.getLeft()));

        if (!isNil(x.getRight())) { //setting the parent for the now left node of y
            x.getRight().setParent(y);
        }

        x.setParent(y.getParent()); //link y's parent to x
        x.setRight(y); //putting y on x's right
        x.setRightChildren(getSizeAtNode(y));


        if (isNil(y.getParent())) {
            root = x;
        } else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
            y.getParent().setRightChildren(getSizeAtNode(x));
        } else {
            y.getParent().setLeft(x);
            y.getParent().setLeftChildren(getSizeAtNode(x));
        }

        y.setParent(x);
    }

    /**
     * Returns the node at the index specified
     * This operation is performed in O(logN) time as the structure is a balancing binary tree
     *
     * @param index the index to get the node at
     * @return the node at the index
     */
    @Override
    public Node<E> get(final int index) {
        Node<E> searcher = root;
        int indexesVisited = 0;
        while (!isNil(searcher)) {
            if (index == searcher.getLeftChildren() + indexesVisited) {
                return searcher;
            } else if (index < searcher.getLeftChildren() + indexesVisited) {
                searcher = searcher.getLeft();
            } else {
                indexesVisited += searcher.getLeftChildren() + 1;
                searcher = searcher.getRight();
            }
        }
        return null;
    }

    /**
     * Replaces subtree rooted at node u with subtree rooted at node v and node u's parent becomes node v's
     * parent
     *
     * @param u Node which will be replaced
     * @param v Node which will be used for replacement
     */
    private void transplant(Node<E> u, Node<E> v) {
        if (u.getParent() == nil) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
            u.getParent().setLeftChildren(getSizeAtNode(v));
        } else {
            u.getParent().setRight(v);
            u.getParent().setRightChildren(getSizeAtNode(v));
        }
        v.setParent(u.getParent());
    }

    /**
     * Gets the minimum node by index from the given node
     * @param node the node from where to find the minimum node
     * @return the node with the least index from the given node
     */
    private Node<E> getMinimumNode(Node<E> node) {
        while (!isNil(node) &&  !isNil(node.getLeft())) {
            node = node.getLeft();
        }
        return node;
    }


    /**
     * Deletes an element at the given index and updates the remaining indexes
     * @param index index whose node needs to be deleted
     * @return The node which was deleted
     */
    @Override
    public Node<E> delete(final int index) {
        Node<E> toDelete = get(index);
        Node<E> y = toDelete;
        Node.Color yColor = y.getColor(); //saving original color to test at the end
        Node<E> x;
        if (isNil(toDelete.getLeft())) {
            x = toDelete.getRight();
            transplant(toDelete, toDelete.getRight());
        } else if (isNil(toDelete.getRight())) {
            x = toDelete.getLeft();
            transplant(toDelete, toDelete.getLeft());
        } else {
            y = getMinimumNode(toDelete.getRight());

            yColor = y.getColor();
            x = y.getRight();
            if (y.getParent() == toDelete) {
                x.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRight(toDelete.getRight());
                y.setRightChildren(getSizeAtNode(toDelete.getRight()));
                y.getRight().setParent(y);
            }
            transplant(toDelete, y);
            y.setLeft(toDelete.getLeft());
            y.setLeftChildren(getSizeAtNode(toDelete.getLeft()));
            y.getLeft().setParent(y);
            y.setColor(toDelete.getColor());
        }
        if (yColor == Node.Color.BLACK) {
            //if color is black then moving around y or removing y could cause violations
            // of red black property
            deleteFix(x);
        }

        return toDelete;
    }


    /**
     * Deleting an element from the tree can cause several violations of the red-black
     * properties which need to be fixed
     * @param x the node that was deleted
     */
    private void deleteFix(Node<E> x) {
        Node<E> w;
        while (x != root && x.getColor() == Node.Color.BLACK) {
            if (x == x.getParent().getLeft()) {
                w = x.getParent().getRight();
                if (w.getColor() == Node.Color.RED) {
                    //case 1
                    w.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                if (w.getLeft().getColor() == Node.Color.BLACK &&
                        w.getRight().getColor() == Node.Color.BLACK) {
                    //case 2
                    w.setColor(Node.Color.RED);
                    x = x.getParent();
                } else if (w.getRight().getColor() == Node.Color.BLACK) {
                    //case 3
                    w.getLeft().setColor(Node.Color.BLACK);
                    w.setColor(Node.Color.RED);
                    rightRotate(w);
                    w = x.getParent().getRight();
                }
                //case 4
                w.setColor(x.getParent().getColor());
                x.getParent().setColor(Node.Color.BLACK);
                w.getRight().setColor(Node.Color.BLACK);
                leftRotate(x.getParent());
                x = root;

            } else {
                //right left interchanged
                w = x.getParent().getLeft();
                if (w.getColor() == Node.Color.RED) {
                    //case 1
                    w.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                if (w.getRight().getColor() == Node.Color.BLACK &&
                        w.getLeft().getColor() == Node.Color.RED) {
                    //case 2
                    w.setColor(Node.Color.RED);
                    x = x.getParent();
                } else if (w.getLeft().getColor() == Node.Color.BLACK) {
                    //case 3
                    w.getRight().setColor(Node.Color.BLACK);
                    w.setColor(Node.Color.RED);
                    leftRotate(w);
                    w = x.getParent().getLeft();
                }
                //case 4
                w.setColor(x.getParent().getColor());
                x.getParent().setColor(Node.Color.BLACK);
                w.getLeft().setColor(Node.Color.BLACK);
                rightRotate(x.getParent());
                x = root;
            }
        }
        x.setColor(Node.Color.BLACK);
    }

    /**
     * Finds the first node with the given value and returns its index
     * @param value the value to search for
     * @return index of the node with the value or -1
     */
    public int getIndex(E value) {
        Node<E> current = root;
        int indexesVisited = -1;
        //stack to keep track of the nodes to visit to find the value
        Stack<Node<E>> stack = new Stack<>();

        while (!isNil(current)){
            stack.push(current);
            current = current.getLeft();
        }

        //traversing the tree
        while(stack.size() > 0){
            current = stack.pop();
            indexesVisited++;
            if (value == current.getValue()){
                return indexesVisited;
            }
            if (!isNil(current.getRight())){
                current = current.getRight();
                while (!isNil(current)){
                    stack.push(current);
                    current = current.getLeft();
                }
            }
        }
        return -1;
    }

    /**
     * Sets the value of the node at the given index with the given value
     * Takes O(logN) to do
     * @param index the index of the node
     * @param value the value with which to replace with
     * @return the Value that was replaced
     */
    @Override
    public E set(final int index, final E value){
        Node<E> node = get(index);
        E valReturn = node.getValue();
        node.setValue(value);
        return valReturn;
    }

}
