package com.dhanish.list.structure;

/**
 * Self balancing red black tree structure
 * Created by Dhanish on 12 Feb,17.
 */
public class RedBlackTree<E> implements com.dhanish.list.structure.interfaces.RedBlackTree<E> {
    private Node<E> root;
    final Node<E> nil = new Node<>(); //nil node for the leafs
    int size = 0;

    public RedBlackTree() {
        root = nil;
        root.setParent(nil);
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == nil;
    }

    @Override
    public void clear() {
        root = nil;
        root.setParent(nil);
    }

    @Override
    public void insert(final int index, final E value) {

        Node<E> y = nil;
        Node<E> x = root;
        Node<E> toInsert = new Node<>(index, value);

        while (!isNil(x)) {
            y = x;
            if (toInsert.getIndex() < x.getIndex()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        toInsert.setParent(y);
        if (isNil(y)) {
            root = toInsert;
        } else if (toInsert.getIndex() < y.getIndex()) {
            y.setLeft(toInsert);
        } else {
            y.setRight(toInsert);
        }
        toInsert.setLeft(nil);
        toInsert.setRight(nil);
        toInsert.setColor(Node.Color.RED);
        insertFix(toInsert);
        size++;
    }

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

    @Override
    public boolean isNil(final Node<E> toCheck) {
        return toCheck == nil;
    }


    private void leftRotate(final Node<E> x) {
        Node<E> y = x.getRight();
        x.setRight(y.getLeft()); //turing y's left subtree to x's right subtree

        if (!isNil(y.getLeft())) { //setting the parent for the now right node of x
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent()); //link x's parent to y

        if (isNil(x.getParent())) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x); //putting x on y's left
        x.setParent(y);

    }

    private void rightRotate(final Node<E> y) {
        Node<E> x = y.getLeft();
        y.setLeft(x.getRight()); //turing x's left subtree to y's left subtree

        if (!isNil(x.getRight())) { //setting the parent for the now left node of y
            x.getRight().setParent(y);
        }

        x.setParent(y.getParent()); //link y's parent to x

        if (isNil(y.getParent())) {
            root = x;
        } else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
        } else {
            y.getParent().setLeft(x);
        }
        x.setRight(y); //putting y on x's right
        y.setParent(x);
    }

    /**
     * Returns the node at the index specified
     * This operation is performed in O(logn) time as the structure is a balancing binary tree
     *
     * @param index the index to get the node at
     * @return the node at the index
     */
    @Override
    public Node<E> get(final int index) {
        Node<E> searcher = root;

        while (!isNil(searcher)) {
            if (index == searcher.getIndex()) {
                return searcher;
            } else if (index < searcher.getIndex()) {
                searcher = searcher.getLeft();
            } else {
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
        } else {
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }

    private Node<E> getMinimumNode(Node<E> node) {
        while (!isNil(node) &&  !isNil(node.getLeft())) {
            node = node.getLeft();
        }
        return node;
    }

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
                y.getRight().setParent(y);
            }
            transplant(toDelete, y);
            y.setLeft(toDelete.getLeft());
            y.getLeft().setParent(y);
            y.setColor(toDelete.getColor());
        }
        if (yColor == Node.Color.BLACK) {
            //if color is black then moving around y or removing y could cause violations
            // of red black property
            deleteFix(x);
        }
        reduceIndexes(get(toDelete.getIndex() + 1));
        size--;
        return toDelete;
    }

    private void reduceIndexes(Node<E> reduce) {

        reduce.setIndex(reduce.getIndex()-1);
        for (int i=reduce.getIndex()+2; i<size;i++){
            Node<E> temp = get(i);
            temp.setIndex(temp.getIndex()-1);
        }

    }

    private Node<E> getInOrderSuccessor(Node<E> precurse){
        if (!isNil(precurse.getRight())){
            return getMinimumNode(precurse.getRight());
        }
        Node parent = precurse.getParent();
        while (!isNil(parent) && precurse == parent.getRight()){
            precurse = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    private void deleteFix(Node<E> x) {
        Node w;
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

    public int getIndex(E value) {
        Node<E> current = root;

        current = getIndex(current,value);
        if (isNil(current)){
            return -1;
        } else {
           return current.getIndex();
        }


    }

    private Node<E> getIndex(Node<E> current, E value) {

        Node<E> found = nil;
        if ( !isNil(current.getLeft())) {
            found = getIndex(current.getLeft(), value);
        }
        if (current.getValue().equals(value)) {
            found = current;
            return found;
        }
        if (isNil(found) &&  !isNil(current.getRight())) {
            found = getIndex(current.getRight(), value);
        }
        return found;
    }


    @Override
    public E set(final int index, final E value){
        Node<E> node = get(index);
        E valReturn = node.getValue();
        node.setValue(value);
        return valReturn;
    }

    public void adjustIndexForAdd(Node<E> node){

        //current.setIndex(current.getIndex() + 1);
        //node.setIndex(node.getIndex() + 1);
        for (int i = size-1; i>=node.getIndex();i--){
            Node<E> temp = get(i);
            temp.setIndex(temp.getIndex() + 1);
        }
//        if (!isNil(node.getLeft())) {
//            adjustIndexForAdd(node.getLeft());
//        }
//        node.setIndex(node.getIndex()+1);
//        if ( !isNil(node.getRight())) {
//            adjustIndexForAdd(node.getRight());
//        }
    }



}
