import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node<T> {
        T item;
        Node<T> nxt;
        Node<T> prv;
    }

    private Node<Item> _dummy = new Node<Item>();
    private Node<Item> _first = null;
    private Node<Item> _last = null;

    private int _count;

    public Deque() {
        _first = _dummy;
        _last = _dummy;
        _count = 0;
    }

    public boolean isEmpty() {
        return (_count == 0);
    }

    public int size() {
        return _count;
    }

    public void addFirst(Item item) {
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

        if (_last == _dummy) {
            _last = _first;
        }

        _count += 1;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> node = new Node<Item>();
        node.item = item;

        Node<Item> tmp = _last;

        if (isEmpty()) {
            tmp = _dummy;
        }

        tmp.nxt = node;
        node.prv = tmp;

        _last = node;

        if (_first == _dummy) {
            _first = _last;
        }

        _count += 1;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item tmp = _first.item;

        if (size() == 1) {
            _first.prv = null;
            _first.nxt = null;

            _first = _dummy;
            _last = _dummy;
        }
        else {
            _dummy.nxt = _first.nxt;

            if (_first.nxt != null) {
                _first.nxt.prv = _dummy;
            }

            _first.prv = null;
            _first.nxt = null;

            _first = _dummy.nxt;
        }

        _count -= 1;

        return tmp;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item tmp = _last.item;

        if (size() == 1) {
            _first.prv = null;
            _first.nxt = null;

            _first = _dummy;
            _last = _dummy;
        }
        else {
            Node<Item> tmpLast = _last.prv;
            tmpLast.nxt = null;
            _last.prv = null;
            _last.nxt = null;

            _last = tmpLast;
        }

        _count -= 1;

        return tmp;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        public boolean hasNext() {
            return !isEmpty();
        }

        public Item next() {
            return removeFirst();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
