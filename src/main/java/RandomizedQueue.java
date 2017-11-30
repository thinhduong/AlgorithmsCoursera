import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class Node<Item> {
        Item item;
        Node<Item> nxt;
        Node<Item> prv;
    }

    private Node<Item> _dummy = new Node<Item>();
    private Node<Item> _first = null;
    private int _count;
    private Object[] _indexes;

    public RandomizedQueue() {
        _first = _dummy;
        _count = 0;
        _indexes = new Object[1];
    }

    public boolean isEmpty() {
        return (_count == 0);
    }

    public int size() {
        return _count;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> node = new Node<Item>();
        node.item = item;

        if (!isEmpty()) {
            node.nxt = _first;
            _first.prv = node;

        }

        node.prv = _dummy;
        _dummy.nxt = node;

        _first = node;

        if (_count >= _indexes.length) {
            resize(2 * _indexes.length);
        }

        _indexes[_count] = node;

        _count += 1;
    }

    private void resize(int capacity) {
        Object[] tmp = new Object[capacity];

        for (int i=0; i<_count; i++)
            tmp[i] = _indexes[i];

        _indexes = tmp;
    }

    public Item dequeue() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        int idx = StdRandom.uniform(_count);

        Node<Item> node = (Node<Item>) _indexes[idx];

        Item tmp = node.item;

        Node<Item> prev = node.prv;
        Node<Item> next = node.nxt;

        prev.nxt = next;

        if (next != null) {
            next.prv = prev;
        }

        if (idx != _count) {
            _indexes[idx] = _indexes[_count - 1];
            _indexes[_count - 1] = null;
        }

        _count -= 1;

        if (_count < _indexes.length / 4) {
            resize(_indexes.length / 2);
        }

        return tmp;
    }

    public Item sample() {
        if (_count == 0) {
            throw new NoSuchElementException();
        }

        int idx = StdRandom.uniform(_count + 1);

        return ((Node<Item>)_indexes[idx]).item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        public boolean hasNext() {
            return !isEmpty();
        }

        public Item next() {
            return dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
