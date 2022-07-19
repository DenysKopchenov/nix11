package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductComparator;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.impl.PhoneRepositoryListImpl;

import java.io.PrintStream;

public class SimpleTree<E extends Product> {

    private Node<E> root;
    private final ProductComparator<E> productComparator = new ProductComparator<>();

    private Node<E> addRecursive(Node<E> current, E value) {
        if (current == null) {
            return new Node<E>(value);
        }

        if (productComparator.compare((E) current.item, value) < 0) {
            current.left = addRecursive(current.left, value);
        } else if (productComparator.compare((E) current.item, value) > 0) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }
        return current;
    }

    public void add(E item) {
        root = addRecursive(root, item);
    }

    public long sumLeftBranch() {
        if (root.left == null) {
            return 0;
        }
        return sum(root.left);
    }

    public long sumRightBranch() {
        if (root.right == null) {
            return 0;
        }
        return sum(root.right);
    }

    private long sum(Node<E> root) {
        if (root == null)
            return 0;
        return (root.item.getPrice() + sum(root.left) +
                sum(root.right));
    }

    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node<E> node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.item.getTitle());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = "├──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }

    public String traversePreOrder(Node<E> root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.item.getTitle());

        String pointerRight = "└──";
        String pointerLeft = "├──";

        traverseNodes(sb, "", pointerLeft, root.left, root.right != null);
        traverseNodes(sb, "", pointerRight, root.right, false);

        return sb.append("\n").toString();
    }

    public void print(PrintStream os) {
        os.print(traversePreOrder(root));
    }

    private class Node<T extends Product> {
        T item;
        Node<T> left;
        Node<T> right;

        Node(T item) {
            this.item = item;
            right = null;
            left = null;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }
    }

    public static void main(String[] args) {
        PhoneService phoneService = new PhoneService(new PhoneRepositoryListImpl());
        phoneService.createAndFillRepository(20);
        SimpleTree<Phone> phoneTree = new SimpleTree<>();
        for (Phone phone : phoneService.findAll()) {
            phoneTree.add(phone);
        }

        phoneTree.print(System.out);

        System.out.println("---".repeat(10));
        System.out.println("Left branch sum: " + phoneTree.sumLeftBranch());
        System.out.println("Right branch sum: " + phoneTree.sumRightBranch());
        System.out.println("---".repeat(10));
    }
}
