package com.dhanish.list;

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

    }

    @org.testng.annotations.Test
    public void testIsEmpty() throws Exception {

    }

    @org.testng.annotations.Test
    public void testContains() throws Exception {

    }

    @org.testng.annotations.Test
    public void testIterator() throws Exception {

    }

    @org.testng.annotations.Test
    public void testToArray() throws Exception {

    }

    @org.testng.annotations.Test
    public void testAdd() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(1);
        assertEquals(1, (int)list.get(3));

    }

    @org.testng.annotations.Test
    public void testRemove() throws Exception {

    }

    @org.testng.annotations.Test
    public void testAddAll() throws Exception {

    }

    @org.testng.annotations.Test
    public void testAddAll1() throws Exception {

    }
}