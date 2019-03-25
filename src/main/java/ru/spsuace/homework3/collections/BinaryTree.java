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

    private Node findElement(K key) {
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
                node = node.rightChild;
            } else {
                node = node.leftChild;
            }
        }
        return null;
    }
    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {
        if (key == null) {
            return null;
        }

        Node current = findElement(key);
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

        if (current.leftChild != null && current.rightChild != null) {
            Node heir = searchForHeir(current);
            current.key = heir.key;
            current.value = heir.value;
            current = heir;
        }

        Node replacement = (current.leftChild != null ? current.leftChild : current.rightChild);
        Node parent = current.parent;

        if (replacement != null) {
            replacement.parent = parent;

            if (parent == null) {
                root = replacement;
            } else if (current == parent.leftChild) {
                parent.leftChild = replacement;
            } else {
                parent.rightChild = replacement;
            }
        } else if (parent == null) {
            root = null;
        } else {
            if (current == parent.leftChild) {
                parent.leftChild = null;
            } else if (current == parent.rightChild) {
                parent.rightChild = null;
            }
        }

        return removedValue;

    }


    private Node searchForHeir(Node current) {

        Node heir = current.rightChild;
        while (heir.leftChild != null) {
            heir = heir.leftChild;
        }
        return heir;
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

    private class Node {
        private K key;
        private V value;
        private Node parent;
        private Node leftChild;
        private Node rightChild;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

    }
}