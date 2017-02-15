package com.dhanish.list;

import com.dhanish.list.structure.Node;
import com.dhanish.list.structure.RBIterator;
import com.dhanish.list.structure.RedBlackTree;

import java.util.*;

/**
 * Class implementing list with the underlying structure being a self balancing tree
 * Created by Dhanish on 10 Feb,17.
 */
public class EfficientList<E extends Comparable<E>> implements List<E> {
    private int size = 0;
    private int lastIndex = 0;
    private final RedBlackTree<E> tree;


    EfficientList(){
        tree = new RedBlackTree<>();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (o.equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return  new RBIterator<>(tree);
    }

    @Override
    public Object[] toArray() {
        Iterator<E> iterator = iterator();
        Object[] toReturn = new Object[size()];
        for (int i=0;i<size();i++){
            toReturn[i] = iterator.next();
        }
        return toReturn;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        }
        System.arraycopy(toArray(), 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }


    public boolean add(E o) {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        tree.insert(lastIndex++, o);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        final int index = tree.getIndex((E)o);
        if (index == -1){
            throw new NullPointerException("Element not found");
        }
        Node<E> deleted = tree.delete(index);
        return !tree.isNil(deleted);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        tree.clear();
        size = 0;
        lastIndex = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > lastIndex){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        return tree.get(index).getValue();
    }

    @Override
    public E set(int index, E element) {
        if (element == null){
            throw new NullPointerException("Given element is null");
        }
        if (index < 0 || index > lastIndex){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        return tree.set(index,element);
    }

    @Override
    public void add(int index, E element) {
        if (element == null){
            throw new NullPointerException("Given element is null");
        }

        if (index < 0 || index > lastIndex){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        Node<E> node = tree.get(index);
        tree.adjustIndexForAdd(node);
        tree.insert(index,element);
        lastIndex++;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > lastIndex){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        E value = tree.delete(index).getValue();
        size--;
        lastIndex--;
        return value;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        return tree.getIndex((E) o);

    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        Iterator<E> iterator = iterator();
        int index = -1;
        int i=0;
        while (iterator.hasNext()){
            if (iterator.next().equals(o)){
                index = i;
            }
            i++;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }


}
