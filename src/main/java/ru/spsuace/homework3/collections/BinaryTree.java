package ru.spsuace.homework3.collections;

/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {
    private Node root;
    private int size = 0;

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        Node current = lookFor(key, false);
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
        Node current = lookFor(key, true);
        if (current == null) {
            root = new Node(key, value, null);
            size++;
            return null;
        }
        int compare = key.compareTo(current.key);
        if (compare > 0) {
            current.rightBranch = new Node(key, value, current);
            size++;
            return null;
        } else if (compare < 0) {
            current.leftBranch = new Node(key, value, current);
            size++;
            return null;
        } else {
            V temp = current.value;
            current.value = value;
            return temp;
        }
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        Node current = lookFor(key, false);
        if (current == null) {
            return null;
        }
        size--;
        V removed = current.value;
        //у удаляемого есть обе ветви
        if (current.leftBranch != null && current.rightBranch != null) {
            Node closestChild = current.rightBranch;
            //находим самый левый узел правого поддерева
            while (closestChild.leftBranch != null) {
                closestChild = closestChild.leftBranch;
            }
            current.key = closestChild.key;
            current.value = closestChild.value;
            current = closestChild;
        }
        Node substitution = (current.leftBranch != null ? current.leftBranch : current.rightBranch);
        Node fixedSplit = current.split;
        //если есть хотя бы одна ветвь
        if (substitution != null) {
            substitution.split = fixedSplit;
            if (fixedSplit == null) {
                root = substitution;
            } else if (current == fixedSplit.leftBranch) {
                fixedSplit.leftBranch = substitution;
            } else {
                fixedSplit.rightBranch = substitution;
            }
            //нет ветвей и корня
        } else if (fixedSplit == null) {
            root = null;
            //нет ветвей, но есть корень
        } else {
            if (current == fixedSplit.leftBranch) {
                fixedSplit.leftBranch = null;
            } else if (current == fixedSplit.rightBranch) {
                fixedSplit.rightBranch = null;
            }
        }
        return removed;
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
        if (node == null) {
            return;
        }
        //left rotate
        if (isLeftRotation) {
            if (node.rightBranch == null) {
                return;
            }
            Node rightNode = node.rightBranch;
            node.rightBranch = rightNode.leftBranch;
            if (rightNode.leftBranch != null) {
                rightNode.leftBranch.split = node;
            }
            rightNode.split = node.split;
            if (node.split == null) {
                root = rightNode;
            } else if (node.split.leftBranch == node) {
                node.split.leftBranch = rightNode;
            } else {
                node.split.rightBranch = rightNode;
            }
            rightNode.leftBranch = node;
            node.split = rightNode;
            //right rotate
        } else {
            if (node.leftBranch == null) {
                return;
            }
            Node leftNode = node.leftBranch;
            node.leftBranch = leftNode.leftBranch;
            if (leftNode.rightBranch != null) {
                leftNode.rightBranch.split = node;
            }
            leftNode.split = node.split;
            if (node.split == null) {
                root = leftNode;
            } else if (node.split.rightBranch == node) {
                node.split.rightBranch = leftNode;
            } else {
                node.split.rightBranch = leftNode;
            }
            leftNode.rightBranch = node;
            node.split = leftNode;
        }
    }

    /**
     * Поиск элемента по ключу
     */
    private Node lookFor(K key, boolean added) {
        if (root == null) {
            return null;
        }
        Node current = root;
        for (; ; ) {
            int compare = key.compareTo(current.key);
            if (compare > 0) {
                if (current.rightBranch == null) {
                    return (added) ? current : null;
                } else {
                    current = current.rightBranch;
                }
            } else if (compare < 0) {
                if (current.leftBranch == null) {
                    return (added) ? current : null;
                } else {
                    current = current.leftBranch;
                }
            } else {
                return current;
            }
        }
    }

    private class Node {
        private K key;
        private V value;
        private Node leftBranch;
        private Node rightBranch;
        private Node split;

        Node(K key, V value, Node split) {
            this.key = key;
            this.value = value;
            this.split = split;
        }
    }
}
