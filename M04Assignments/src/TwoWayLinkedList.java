import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size = 0;

    public TwoWayLinkedList() {
    }

    public TwoWayLinkedList(E[] elements) {
        for (E element : elements) {
            add(element);
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E e) {
            element = e;
        }
    }

    @Override
    public void add(E e) {
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E e) {
        if (index == 0) {
            Node<E> newNode = new Node<>(e);
            newNode.next = head;
            head = newNode;
            if (tail == null) {
                tail = head;
            }
            size++;
        } else if (index >= size) {
            add(e);
        } else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            (current.next).next = temp;
            (current.next).previous = current;
            size++;
        }
    }

    @Override
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override
    public boolean contains(E e) {
        Node<E> current = head;
        while (current != null) {
            if (current.element.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return head.element;
        } else if (index == size - 1) {
            return tail.element;
        } else {
            Node<E> current = head.next;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
    }

    @Override
    public int indexOf(E e) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element.equals(e)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int lastIndexOf(E e) {
        int lastIndex = -1;
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element.equals(e)) {
                lastIndex = i;
            }
            current = current.next;
        }
        return lastIndex;
    }

    @Override
    public boolean remove(E e) {
        if (head == null) {
            return false;
        }

        if (head.element.equals(e)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return true;
        }

        Node<E> previous = head;
        Node<E> current = head.next;
        while (current != null) {
            if (current.element.equals(e)) {
                previous.next = current.next;
                if (previous.next == null) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        if (index == 0) {
            E temp = head.element;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp;
        }

        Node<E> previous = head;
        for (int i = 1; i < index; i++) {
            previous = previous.next;
        }

        E temp = previous.next.element;
        previous.next = previous.next.next;
        size--;
        if (previous.next == null) {
            tail = previous;
        }
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        E temp = current.element;
        current.element = e;
        return temp;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return new LinkedListIterator(index);
    }

    private class LinkedListIterator implements ListIterator<E> {
        private Node<E> current = head;
        private Node<E> lastReturned = null;
        private int index = 0;

        public LinkedListIterator() {
        }

        public LinkedListIterator(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Invalid index");
            }
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = current.element;
            lastReturned = current;
            current = current.next;
            index++;
            return result;
        }

        @Override
        public boolean hasPrevious() {
            return current != null && current != head;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (current == null) {
                current = tail;
            } else {
                current = current.previous;
            }
            E result = current.element;
            lastReturned = current;
            index--;
            return result;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            TwoWayLinkedList.this.remove(lastReturned.element);
            lastReturned = null;
            index--;
        }

        @Override
        public void set(E e) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.element = e;
        }

        @Override
        public void add(E e) {
            Node<E> newNode = new Node<>(e);
            if (lastReturned == null) {
                if (head == null) {
                    head = tail = newNode;
                } else {
                    newNode.next = head;
                    head.previous = newNode;
                    head = newNode;
                }
            } else {
                newNode.next = current;
                newNode.previous = current.previous;
                if (current == head) {
                    head = newNode;
                } else {
                    current.previous.next = newNode;
                }
                current.previous = newNode;
            }
            lastReturned = null;
            size++;
            index++;
        }
    }
}
