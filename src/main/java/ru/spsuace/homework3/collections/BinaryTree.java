package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {
    private Node root;
    private int high = 0;

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        Node temp = root;
        for (; ; ) {
            int compareResult = key.compareTo(temp.key);
            if (compareResult > 0) {
                if (temp.right == null) {
                    return null;
                } else {
                    temp = temp.right;
                }
            } else if (compareResult < 0) {
                if (temp.left == null) {
                    return null;
                } else {
                    temp = temp.left;
                }
            } else {
                return temp.value;
            }
        }
    }

    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            high++;
        }
        Node temp = root;
        for (; ; ) {
            int compareResult = key.compareTo(temp.key);
            if (compareResult > 0) {
                if (temp.right == null) {
                    temp.right = new Node(key, value);
                    temp.right.parent = temp;
                    high++;
                    return null;
                } else {
                    temp = temp.right;
                }
            } else if (compareResult < 0) {
                if (temp.left == null) {
                    temp.left = new Node(key, value);
                    temp.left.parent = temp;
                    high++;
                    return null;
                } else {
                    temp = temp.left;
                }
            } else {
                V previousValue = temp.value;
                temp.value = value;
                return previousValue;
            }
        }
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    private Node replacement(Node temp) {
        if (temp.left == null) {
            if (temp.right == null) {
                return null;
            }
            temp = temp.right;

            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }
        temp = temp.left;
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp;
    }

    public V remove(K key) {
        if (key == null) {
            return null;
        }
        Node temp = root;
        V current;
        int comp;
        while (temp != null) {
            current = temp.value;
            comp = key.compareTo(temp.key);
            if (comp == 0) {
                if (high == 1) {
                    root = null;
                    high--;
                    return current;
                }
                Node node = replacement(temp);
                if (node == null) {
                    if (temp.parent.right == temp) {
                        temp.parent.right = null;
                    } else {
                        temp.parent.left = null;
                    }
                    high--;
                    return current;
                }
                if (node.right == null && node.left == null) {
                    temp.value = node.value;
                    if (node.parent.right == node) {
                        node.parent.right = null;
                    } else {
                        node.parent.left = null;
                    }
                } else if (node.right == null) {
                    node.parent.right = node.left;
                    temp.value = node.value;

                } else {
                    node.parent.left = node.right;
                    temp.value = node.value;
                }
                high--;
                return current;
            }
            if (comp > 0) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }
        return null;
    }

    /**
     * Возвращаем размер дерева
     */
    public int size() {
        return high;
    }

    /**
     * Выполняем левый или правый поворот относительно заданного узла
     */
    public void rotation(boolean isLeftRotation, Node node) {
        if (node == null) {
            return;
        }
        Node temp;
        if (isLeftRotation) {
            if (node.right == null) {
                return;
            }
            temp = node.right;
            node.right = temp.left;
            temp.left = node;
            temp.parent = node.parent;
            node.parent = temp;
        } else {
            if (node.left == null) {
                return;
            }
            temp = node.left;
            node.left = temp.right;
            temp.right = node;
            temp.parent = node.parent;
            node.parent = temp;
        }
        if (temp.key.compareTo(temp.parent.key) < 0) {
            temp.parent.left = temp;
        } else {
            temp.parent.right = temp;
        }
    }

    private class Node {
        private K key;
        private V value;
        private Node parent;
        private Node left;
        private Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
