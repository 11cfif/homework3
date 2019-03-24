package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {

    private Node root;
    private int count = 0;

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {

        if (key == null) {
            return null;
        }

        Node current = findElement(key);
        return (current != null) ? current.value : null;
    }

    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {

        if (key == null) {
            return null;
        }

        Node current = findElement(key, true);

        if (current == null) {
            root = new Node(key, value, null);
            count++;
            return null;
        }

        int compareResult = key.compareTo(current.key);
        if (compareResult > 0) {
            current.rightChild = new Node(key, value, current);
            count++;
            return null;
        } else if (compareResult < 0) {
            current.leftChild = new Node(key, value, current);
            count++;
            return null;
        } else {
            V previousValue = current.value;
            current.value = value;
            return previousValue;
        }
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {

        if (key == null) {
            return null;
        }

        Node current = findElement(key);

        if (current == null) {
            return null;
        }

        V removedValue = current.value;

        if (current.parent.leftChild == current) {
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
            if (current.rightChild == null && current.leftChild == null) {
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

        if (closestToKeyChild.parent.leftChild == closestToKeyChild) {
            closestToKeyChild.parent.leftChild = closestToKeyChild.rightChild;
        } else {
            closestToKeyChild.parent.rightChild = closestToKeyChild.rightChild;
        }
        if (closestToKeyChild.rightChild != null) {
            closestToKeyChild.rightChild.parent = closestToKeyChild.parent;
        }
        current.key = closestToKeyChild.key;
        current.value = closestToKeyChild.value;
        count--;

        return removedValue;
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

        if (node == null) {
            return;
        }

        if (isLeftRotation) {
            if (node.rightChild == null) {
                return;
            }

            Node temp = node.rightChild.leftChild;

            if (node.parent != null) {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = node.rightChild;
                } else {
                    node.parent.rightChild = node.rightChild;
                }
            }
            node.rightChild.parent = node.parent;
            node.parent = node.rightChild;
            node.rightChild.leftChild = node;
            node.rightChild = temp;
            if (temp != null) {
                temp.parent = node;
            }

        } else {
            if (node.leftChild == null) {
                return;
            }

            Node temp = node.leftChild.rightChild;

            if (node.parent != null) {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = node.leftChild;
                } else {
                    node.parent.rightChild = node.leftChild;
                }
            }
            node.leftChild.parent = node.parent;
            node.parent = node.leftChild;
            node.leftChild.rightChild = node;
            node.leftChild = temp;
            if (temp != null) {
                temp.parent = node;
            }
        }
    }

    private Node findElement(K key) {
        return findElement(key, false);
    }

    private Node findElement(K key, boolean isForPut) {

        if (root == null) {
            return null;
        }

        Node current = root;

        for ( ; ; ) {

            int compareResult = key.compareTo(current.key);
            if (compareResult > 0) {

                if (current.rightChild == null) {
                    return (isForPut) ? current : null;
                } else {
                    current = current.rightChild;
                }

            } else if (compareResult < 0) {

                if (current.leftChild == null) {
                    return (isForPut) ? current : null;
                } else {
                    current = current.leftChild;
                }

            } else {
                return current;
            }
        }
    }

    private class Node {

        private K key;
        private V value;
        private Node parent;
        private Node leftChild;
        private Node rightChild;

        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}