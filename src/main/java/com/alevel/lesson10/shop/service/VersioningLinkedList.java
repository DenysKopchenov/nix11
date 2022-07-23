package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;

import java.time.LocalDateTime;
import java.util.*;

import java.util.function.Consumer;

public class VersioningLinkedList<E extends Product> implements Iterable<E> {
    private int size = 0;
    private int productVersion = 1;
    private final Set<Integer> versions;
    private Node<E> first;
    private Node<E> last;

    public VersioningLinkedList() {
        versions = new HashSet<>();
    }

    public void addFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        versions.add(newNode.version);
    }

    public LocalDateTime getFirstVersionDate() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.addingDate;
    }

    public LocalDateTime getLastVersionDate() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.addingDate;
    }

    public int getVersionCount() {
        return versions.size();
    }

    public E findByVersion(int version) {
        if (isValidVersion(version)) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.version == version) {
                    return x.item;
                }
            }
        }
        throw new IllegalArgumentException("No such version");
    }

    public boolean deleteByVersion(int version) {
        if (isValidVersion(version)) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.version == version) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setByVersion(int version, E product) {
        if (isValidVersion(version)) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.version == version) {
                    x.item = product;
                    return true;
                }
            }
        }
        return false;
    }

    private E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        versions.remove(x.version);
        size--;
        return element;
    }

    private boolean isValidVersion(int version) {
        return versions.contains(version);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new VersioningLinkedListIterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }

    private class VersioningLinkedListIterator implements Iterator<E> {
        private Node<E> next;
        private int nextIndex = 0;

        public VersioningLinkedListIterator() {
            next = (first == null) ? null : first;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<E> lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }
    }

    private class Node<T> {
        T item;
        int version;
        LocalDateTime addingDate;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
            addingDate = LocalDateTime.now();
            version = productVersion++;
        }
    }
}