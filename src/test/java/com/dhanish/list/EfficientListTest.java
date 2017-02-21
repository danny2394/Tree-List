package com.dhanish.list;



import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Test class to test implementation of set
 * Created by Dhanish on 12 Feb,17.
 */
public class EfficientListTest {


    @Test
    public void testSet() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        list.set(2, 7);

        assertThat(list.get(2)).isEqualTo(7);
        list.add(1,15);
        list.add(0,16);
        list.add(1,18);
    }

    @Test
    public void testIndexOf() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.indexOf(6)).isEqualTo(2);
    }

    @Test
    public void testLastIndexOf() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.lastIndexOf(5)).isEqualTo(4);
    }


    @Test
    public void testGet() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.get(2)).isEqualTo(6);
    }

    private EfficientList<Integer> list;

    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {
        list = new EfficientList<>();

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
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        assertThat(list.contains(6)).isEqualTo(true);
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
        Integer[] expected = {1,2,3,4,5};
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Integer[] actual = Arrays.copyOf(list.toArray(), list.size(), Integer[].class);
        assertThat(actual).containsOnly(expected);
    }

    @org.testng.annotations.Test
    public void testAdd() throws Exception {

        list.add(10);//0
        list.add(5);//1
        list.add(6);//2
        list.add(2);//3   4
        list.add(5);//4   5
        list.add(1);//5   6
        list.add(2);//6   7   8
        list.add(3);//7   8   9
        list.add(4);//8   9   10
        list.add(5);//9   10  11
        Integer[] actual = Arrays.copyOf(list.toArray(), list.size(), Integer[].class);
        assertThat(actual).contains(2);

        list.add(3, 20);
        assertThat(list.get(3)).isEqualTo(20);

        list.add(7, 25);
        assertThat(list.get(7)).isEqualTo(25);


    }

    @org.testng.annotations.Test
    public void testRemove() throws Exception {
        list.add(10);//0
        list.add(5);//1
        list.add(6);//2
        list.add(2);//3
        list.add(5);//4   3
        list.add(1);//5   4
        list.add(2);//6   5
        list.add(3);//7   6
        list.add(4);//8   7    6
        list.add(5);//9   8    7
        assertThat(list.toArray()).isNotEmpty().hasSize(10);
        list.remove(3); //index
        assertThat(list.get(3)).isEqualTo(5);
        list.remove((Object) 3); //by value
        assertThat(list.get(7)).isEqualTo(5);
    }

    @org.testng.annotations.Test
    public void testSublist() throws Exception{
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        EfficientList<Integer> newList = (EfficientList<Integer>) list.subList(3,6);

        assertThat(newList).hasSize(4);
        assertThat(newList.get(0)).isEqualTo(2);
    }


    @Test
    public void testListIterator() throws Exception {
        list.add(10);
        list.add(5);
        list.add(6);
        list.add(2);
        list.add(5);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        ListIterator<Integer> iterator = list.listIterator();
        assertThat(iterator).hasSize(10);
        iterator = list.listIterator(5);
        assertThat(iterator).hasSize(5);
    }
}