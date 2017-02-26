package com.dhanish.list;

import com.dhanish.list.structure.Node;
import com.dhanish.list.structure.RBIterator;
import com.dhanish.list.structure.RedBlackTree;

import java.util.*;

/**
 * Class implementing list with the underlying structure being a self balancing tree
 * Created by Dhanish on 10 Feb,17.
 */
class TreeList<E extends Comparable<E>> implements List<E> {
    private int size = 0;
    private int lastIndex = 0;
    private final RedBlackTree<E> tree;


    /**
     * Constructor to initialize the list
     */
    TreeList(){
        tree = new RedBlackTree<>();
    }

    /**
     * Returns the number of elements in the list
     * @return the number of items
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the list is empty
     * @return true if the list is empty
     */
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * Returns true if the object is within the list
     * @param o the object to check for
     * @return true if it is found
     * @throws NullPointerException if element is null
     */
    @Override
    public boolean contains(Object o) throws NullPointerException {
        if (o == null){
            throw new NullPointerException("object is null");
        }
        for (E e : this) {
            if (o.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator for the list
     * @return Iterator
     */
    @Override
    public Iterator<E> iterator() {
        return  new RBIterator<>(tree);
    }

    /**
     * Returns the elements of the list as an array
     * @return array of elements
     */
    @Override
    public Object[] toArray() {
        Iterator<E> iterator = iterator();
        Object[] toReturn = new Object[size()];
        for (int i=0;i<size();i++){
            toReturn[i] = iterator.next();
        }
        return toReturn;
    }

    /**
     * Returns an array of the list with the runtime type type of T
     * @param a  the array
     * @param <T> the runtime type
     * @return runtime typed array of the list
     */
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


    /**
     * Adds an element to the list
     * Takes O(logN) to do it
     * @param o the object to be added
     * @return true if added successfully
     * @throws NullPointerException if element is null
     */
    public boolean add(E o) throws NullPointerException {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        tree.insert(o);
        lastIndex++;
        size++;
        return true;
    }

    /**
     * Removes an element from the list
     * We have to first find the element which could take O(N)
     * Takes O(logN) time to remove
     * @param o The object to be removed
     * @return true if removed
     * @throws NullPointerException if element is null
     * @throws IndexOutOfBoundsException if index is out of bounds of the array
     */
    @Override
    public boolean remove(Object o) throws NullPointerException,IndexOutOfBoundsException {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        final int index = tree.getIndex((E)o);
        if (index == -1){
            // element not found
            // no change in list, thus return false
            return false;
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

    /**
     * Clears the list
     */
    @Override
    public void clear() {
        tree.clear();
        size = 0;
        lastIndex = 0;
    }

    /**
     * Gets the value at the specified index
     * Takes O(logN) to find the element
     * @param index whose value is needed
     * @return Value at the given index
     * @throws IndexOutOfBoundsException if index is out of bounds of the array
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        return tree.get(index).getValue();
    }

    /**
     * Sets the given element at the given index
     * @param index the location where to set the element
     * @param element the element to set it with
     * @return The value that the element replaced
     * @throws NullPointerException if element is null
     * @throws IndexOutOfBoundsException if index is out of bounds of the array
     */
    @Override
    public E set(int index, E element) throws NullPointerException,IndexOutOfBoundsException {
        if (element == null){
            throw new NullPointerException("Given element is null");
        }
        if (index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        return tree.set(index,element);
    }

    /**
     * Adds a element at the given index and moves the remaining elements to the next cell
     * Takes O(logN) to add an element
     * @param index the index at with element has to be added
     * @param element the element to be added at
     * @throws NullPointerException if element is null
     * @throws IndexOutOfBoundsException if index is out of bounds of the array
     */
    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException,NullPointerException {
        if (element == null){
            throw new NullPointerException("Given element is null");
        }

        if (index < 0 || index >= size() + 1){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        if (index == lastIndex){
            add(element);
        } else {
            tree.insert(index, element);
            lastIndex++;
            size++;
        }

    }

    /**
     * Removes the element from the list
     * Takes O(logN) to remove the element
     * @param index the index at which element needs to be removed
     * @return the value at the given index
     * @throws IndexOutOfBoundsException if index is out of bounds of the array
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        E value = tree.delete(index).getValue();
        size--;
        lastIndex--;
        return value;
    }

    /**
     * Gives the first occurrence of o
     * @param o the object to search for
     * @return the first index found to have the value o
     * @throws NullPointerException if o is null
     */
    @Override
    public int indexOf(Object o) throws NullPointerException {
        if (o == null){
            throw new NullPointerException("Given element is null");
        }
        return tree.getIndex((E) o);

    }

    /**
     * Returns the last occurrence of o
     * @param o the object
     * @return the last index where o was found
     * @throws NullPointerException if o is null
     */
    @Override
    public int lastIndexOf(Object o) throws NullPointerException {
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

    /**
     * Returns the iterator to the list
     * @return ListIterator
     */
    @Override
    public ListIterator<E> listIterator() {
        Iterator<E> iterator =  iterator();

        List<E> asList = new ArrayList<>();
        iterator.forEachRemaining(asList::add);

        return asList.listIterator();
    }

    /**
     * Returns the iterator to the list from the given index
     * @return ListIterator
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public ListIterator<E> listIterator(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        Iterator<E> iterator =  iterator();

        List<E> asList = new ArrayList<>();

        iterator.forEachRemaining(asList::add);

        return asList.listIterator(index);
    }

    /**
     * Returns a sub list from the fromIndex to toIndex
     * @param fromIndex the starting index
     * @param toIndex the ending index
     * @return the list from the two given indexes
     * @throws IndexOutOfBoundsException when index is out of bounds
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (fromIndex < 0 || toIndex >= size()) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        List<E> toReturn = new TreeList<>();

        for (int k = fromIndex; k<=toIndex; k++){
            toReturn.add(get(k));
        }
        return toReturn;
    }


}
