package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 *
 */
public class BinaryTree<K extends Comparable<K>, V> {

    private Node root;
    private int count = 0;

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {

        if (root == null){
           return null;
        }

        Node current = root;

        for ( ; ; ) {

            int compareResult = key.compareTo(current.key);
            if (compareResult > 0){

                if (current.rightChild == null){
                    return null;
                } else {
                    current = current.rightChild;
                }

            } else if (compareResult < 0){

                if (current.leftChild == null){
                    return null;
                } else {
                    current = current.leftChild;
                }

            } else {
                return current.value;
            }

        }
    }

    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {

        if (root == null){
        root = new Node(key, value);
        count++;
        }

        Node current = root;

        for ( ; ; ) {

            int compareResult = key.compareTo(current.key);
            if (compareResult > 0){

                if (current.rightChild == null){
                    current.rightChild = new Node(key, value);
                    current.rightChild.parent = current;
                    count++;
                    return null;
                } else {
                    current = current.rightChild;
                }

            } else if (compareResult < 0){

                if (current.leftChild == null){
                    current.leftChild = new Node(key, value);
                    current.leftChild.parent = current;
                    count++;
                    return null;
                } else {
                    current = current.leftChild;
                }

            } else {

                V previousValue = current.value;
                current.value = value;
                return previousValue;
            }

        }
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {

        if (root == null){
            return null;
        }

        Node current = root;

        for ( ; ; ) {

            int compareResult = key.compareTo(current.key);
            if (compareResult > 0){

                if (current.rightChild == null){
                    return null;
                } else {
                    current = current.rightChild;
                }

            } else if (compareResult < 0){

                if (current.leftChild == null){
                    return null;
                } else {
                    current = current.leftChild;
                }

            } else {

                V removedValue = current.value;

                if (current.parent.leftChild == current){
                    if (current.rightChild == null && current.leftChild == null) {
                        current.parent.leftChild = null;
                        count--;
                        return removedValue;
                    }
                    if (current.rightChild == null) {
                        current.parent.leftChild = current.leftChild;
                        current.leftChild.parent = current.parent;
                        count--;
                        return removedValue;
                    }
                    if (current.leftChild == null) {
                        current.parent.leftChild = current.rightChild;
                        current.rightChild.parent = current.parent;
                        count--;
                        return removedValue;
                    }
                } else {
                    if (current.rightChild == null && current.leftChild == null){
                        current.parent.rightChild = null;
                        count--;
                        return removedValue;
                    }
                    if (current.rightChild == null) {
                        current.parent.rightChild = current.leftChild;
                        current.leftChild.parent = current.parent;
                        count--;
                        return removedValue;
                    }
                    if (current.leftChild == null) {
                        current.parent.rightChild = current.rightChild;
                        current.rightChild.parent = current.parent;
                        count--;
                        return removedValue;
                    }
                }

                Node closestToKeyChild = current.rightChild;

                while (closestToKeyChild.leftChild != null) {
                    closestToKeyChild = closestToKeyChild.leftChild;
                }

                if (closestToKeyChild.parent.leftChild == closestToKeyChild){
                    closestToKeyChild.parent.leftChild = closestToKeyChild.rightChild;
                } else {
                    closestToKeyChild.parent.rightChild = closestToKeyChild.rightChild;
                }
                current.key = closestToKeyChild.key;
                current.value = closestToKeyChild.value;
                count--;

                return removedValue;
            }

        }
    }

    /**
     * Возвращаем размер дерева
     */
    public int size() {

        return count;
    }

    /**
     * Выполняем левый или правый поворот относительно заданного узла
     */
    public void rotation(boolean isLeftRotation, Node node) {

        if (isLeftRotation){
            if (node.rightChild == null){
                return;
            }

            if (node.rightChild.leftChild == null){
                node.rightChild.parent = node.parent;
                node.parent = node.rightChild;
                node.rightChild.leftChild = node;
                node.rightChild = null;
            }else{
                Node temp = node.rightChild.leftChild;

                node.rightChild.parent = node.parent;
                node.parent = node.rightChild;
                node.rightChild.leftChild = node;
                node.rightChild = temp;
                temp.parent = node;
            }

        }else{
            if (node.leftChild == null){
                return;
            }

            if (node.leftChild.rightChild == null){
                node.leftChild.parent = node.parent;
                node.parent = node.leftChild;
                node.leftChild.rightChild = node;
                node.leftChild = null;
            }else{
                Node temp = node.leftChild.rightChild;

                node.leftChild.parent = node.parent;
                node.parent = node.leftChild;
                node.leftChild.rightChild = node;
                node.leftChild = temp;
                temp.parent = node;
            }
        }
    }

    private class Node {

        K key;
        V value;
        Node parent;
        Node leftChild;
        Node rightChild;

        Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

}
