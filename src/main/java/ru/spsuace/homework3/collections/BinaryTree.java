package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {

    private Node root;
    private int size;

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }

        Node current = root;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                return current.value;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {
        if (root == null) {
            root = new Node(key, value, null);
            size++;
            return null;
        }
        Node parent = root;
        Node current = parent;
        int comparison;

        do {
            comparison = key.compareTo(current.key);
            if (comparison == 0) {
                V temp = current.value;
                current.value = value;
                return temp;
            } else if (comparison > 0) {
                current = parent.right;
                if (current != null) {
                    parent = parent.right;
                }
            } else {
                current = parent.left;
                if (current != null) {
                    parent = parent.left;
                }
            }
        } while (current != null);

        Node newNode = new Node(key, value, parent);
        if (comparison > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        return null;
    }

    private Node replacement(Node current) {
        if (current.left == null) {
            if (current.right == null) {
                return null;
            }
            current = current.right;

            while (current.left != null) {
                current = current.left;
            }
            return current;
        }
        current = current.left;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }

        Node current = root;
        V temp;
        int comparison;

        while (current != null) {
            temp = current.value;
            comparison = key.compareTo(current.key);

            if (comparison == 0) {
                if (size == 1) {
                    root = null;
                    size--;
                    return temp;
                }
                Node newNode = replacement(current);
                if (newNode == null) {
                    if (current.parent.right == current) {
                        current.parent.right = null;
                    } else {
                        current.parent.left = null;
                    }
                    size--;
                    return temp;
                }
                if (newNode.right == null && newNode.left == null) {
                    current.value = newNode.value;
                    if (newNode.parent.right == newNode) {
                        newNode.parent.right = null;
                    } else {
                        newNode.parent.left = null;
                    }
                } else if (newNode.right == null) {
                    newNode.parent.right = newNode.left;
                    current.value = newNode.value;

                } else {
                    newNode.parent.left = newNode.right;
                    current.value = newNode.value;
                }
                size--;
                return temp;
            }

            if (comparison > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return null;
    }

    /**
     * Возвращаем размер дерева
     */
    public int size() {
        return size;
    }

    /**
     * Выполняем левый или правый поворот относительно заданного узла
     */
    public void rotation(boolean isRightRotation, Node current) {
        if (current == null) {
            return;
        }

        Node temp;

        if (!isRightRotation) {
            if (current.right == null) {
                return;
            }
            temp = current.right;
            current.right = temp.left;
            temp.left = current;
        } else {
            if (current.left == null) {
                return;
            }
            temp = current.left;
            current.left = temp.right;
            temp.right = current;
        }
        if (current.key.compareTo(current.parent.key) < 0) {
            current.parent.left = temp;
        } else {
            current.parent.right = temp;
        }
        current.parent = temp;
    }

    private class Node {
        private K key;
        private V value;
        private Node parent;
        private Node left;
        private Node right;

        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}
