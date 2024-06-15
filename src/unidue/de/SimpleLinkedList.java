package unidue.de;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedList<E> implements Iterable<E>, Serializable {
    private static final long serialVersionUID = 1L;
    private Entry head, tail;
    private int size;

    public SimpleLinkedList() {
    this.size = 0;
    }

    public void prepend(E data) {
        head = new Entry(null, data, head);
        if (head.next != null) {
            head.next.previous = head;
        }
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void append(E data) {
        tail = new Entry(tail, data, null);
        if (tail.previous != null) {
            tail.previous.next = tail;
        }
        if (head == null) {
            head = tail;
        }
        size++;
    }

    public E getFirst() {
        if(head == null) throw new NoSuchElementException();
        return head.data;
    }

    public E getLast() {
        if(tail==null) throw new NoSuchElementException();
        return tail.data;
    }

    public E getAndRemoveFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        E item = head.data;
        if (head.next == null) {
            tail = null;
        } else {
            head.next.previous = null;
        }
        head = head.next;
        size--;
        return item;
    }

    public E getAndRemoveLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        E item = tail.data;
        if (tail.previous == null) {
            head = null;
        } else {
            tail.previous.next = null;
        }
        tail = tail.previous;
        size--;
        return item;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SimpleLinkedList<?>)) {
            return false;
        }

        SimpleLinkedList<?> that = (SimpleLinkedList<?>) o;

        if (size != that.size) {
            return false;
        }

        Iterator<E> iter;
        Iterator<?> otherIter;
        iter = iterator();
        otherIter = that.iterator();

        while (iter.hasNext() && otherIter.hasNext()) {
            if (!iter.next().equals(otherIter.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        for (E element : this) {
            result = 31 * result + (element != null ? element.hashCode() : 0 );
        }
        return result;
    }

    private class Entry {
        E data;
        Entry previous, next;

        Entry(Entry previous, E data, Entry next) {
            this.previous = previous;
            this.data = data;
            this.next = next;
        }
    }

    // implementation of Iterable<E>, enables the class to be iterated via foreach

    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {
        private Entry next;

        public Iter() {
            this.next = head;
        }


        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            if (next == null) {
                throw new NoSuchElementException();
            }
            E data = next.data;
            next = next.next;
            return data;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeInt(size);
        for(E item : this) {
            out.writeObject(item);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException {
        in.defaultReadObject();
        int size = in.readInt();
        for(int i=0; i < size; i++) {
            append((E) in.readObject());
        }
    }
}