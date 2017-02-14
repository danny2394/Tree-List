package com.dhanish.list;



import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import static org.assertj.core.api.Assertions.assertThat;

import static org.testng.Assert.*;

/**
 * Created by Dhanish on 12 Feb,17.
 */
public class EfficientListTest {

    EfficientList<Integer> list;

    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {
        list = new EfficientList<>();

    }

    @org.testng.annotations.AfterMethod
    public void tearDown() throws Exception {

    }

    @org.testng.annotations.Test
    public void testSize() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.toArray()).isNotEmpty().hasSize(5);
    }

    @org.testng.annotations.Test
    public void testIsEmpty() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.toArray()).isNotEmpty().hasSize(5);
        list.clear();
        assertThat(list.toArray()).isEmpty();


    }

    @org.testng.annotations.Test
    public void testContains() throws Exception {

    }

    @org.testng.annotations.Test
    public void testIterator() throws Exception {

        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        Iterator iter = list.iterator();
        assertThat(iter).isNotEmpty().hasSize(5);
        iter = list.iterator();
        assertEquals(10, iter.next());
        assertEquals(5, iter.next());
        assertEquals(6, iter.next());
        assertEquals(2, iter.next());
        assertEquals(5, iter.next());

    }

    @org.testng.annotations.Test
    public void testToArray() throws Exception {
        Integer[] expected = {10,5,6,2,5};
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Integer[] actual = Arrays.copyOf(list.toArray(), list.size(), Integer[].class);
        assertThat(actual).containsOnly(expected);
    }

    @org.testng.annotations.Test(priority = 1)
    public void testAdd() throws Exception {

        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        Integer[] actual = Arrays.copyOf(list.toArray(), list.size(), Integer[].class);
        assertThat(actual).containsOnly(2);


    }

    @org.testng.annotations.Test
    public void testRemove() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.toArray()).isNotEmpty().hasSize(5);
        list.remove(3);
        assertThat(list.get(3)).isEqualTo(5);


    }

    @org.testng.annotations.Test
    public void testAddAll() throws Exception {

    }



    @Test
    public void testRemoveAll() throws Exception {

    }
}