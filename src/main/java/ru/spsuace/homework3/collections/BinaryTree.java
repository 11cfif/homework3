package ru.spsuace.homework3.collections;



/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 *
 */
public class BinaryTree<K extends Comparable<K>, V> {

    private Node root;
    private int size;
    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        return getNode(key).value;
    }

    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {
        Node node = root;

        if (node == null) {
            root = new Node(key, value, null);
            size++;
            return null;
        }

        int compare;
        Node parent;

        do {
            compare = key.compareTo(node.key);

            if (compare == 0) {
                V temp = node.value;
                node.value = value;
                return temp;
            }
            parent = node;
            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }

        } while (node != null);

        Node newNode = new Node(key, value, parent);

        if (compare > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        return null;
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {
        Node node = getNode(key);

        if (node == null) {
            return null;
        }

        size--;
        V preValue = node.value;

        if (node.left != null && node.right != null) {
            Node heir = searchForHeir(node);
            node.key = heir.key;
            node.value = heir.value;
            node = heir;
        }

        Node replacement = (node.left != null ? node.left : node.right);
        Node parent = node.parent;

        if (replacement != null) {
            replacement.parent = parent;

            if (parent == null) {
                root = replacement;
            } else if (node == parent.left) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
        } else if (parent == null) {
            root = null;
        } else {
            if (node == parent.left) {
                parent.left = null;
            } else if (node == parent.right) {
                parent.right = null;
            }
        }

        return preValue;
    }

    private Node searchForHeir(Node node) {

        Node heir = node.right;
        while (heir.left != null) {
            heir = heir.left;
        }
        return heir;
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
    public void rotation(boolean isLeftRotation, Node node) {

        if (node != null) {

            if (!isLeftRotation) {
                rotateRight(node);
            } else {
                rotateLeft(node);
            }
        }
    }


    private void rotateLeft(Node node) {
        Node rightNode = node.right;/////////////////////
        node.right = rightNode.left;

        if (rightNode.left != null) {
            rightNode.left.parent = node;
        }

        rightNode.parent = node.parent;

        if (node.parent == null) {
            root = rightNode;
        } else if (node.parent.left == node) {
            node.parent.left = rightNode;
        } else {
            node.parent.right = rightNode;
        }

        rightNode.left = node;
        node.parent = rightNode;
    }

    private void rotateRight(Node node) {
        Node leftNode = node.left;
        node.left = leftNode.left;

        if (leftNode.right != null) {
            leftNode.right.parent = node;
        }

        leftNode.parent = node.parent;

        if (node.parent == null) {
            root = leftNode;
        } else if (node.parent.right == node) {
            node.parent.right = leftNode;
        } else {
            node.parent.right = leftNode;
        }

        leftNode.right = node;
        node.parent = leftNode;
    }

    private Node getNode(K key) {

        if (key == null) {
            throw new NullPointerException();
        }

        Node node = root;
        int compare;

        while (node != null) {
            compare = key.compareTo(node.key);

            if (compare == 0) {
                return node;
            }

            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private Node parent;

        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }

}
