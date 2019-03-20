package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {
    private int size = 0;
    private Node root;
    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

        Node node = root;
        int compare;
        while (node != null) {
            compare = key.compareTo(node.key);

            if (compare == 0) {
                return node.value;
            }

            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
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
            root = new Node(key, value);
            size++;
            return null;
        }

        Node parent = root;
        Node node = parent;
        int compare;

        do {
            compare = key.compareTo(node.key);
            if (compare == 0) {
                V temp = node.value;
                node.value = value;
                return temp;
            }

            if (compare > 0) {
                node = parent.right;
                if(parent.right!=null){
                    parent=parent.right;
                }

            } else {
                node=parent.left;
                if (parent.left!=null){
                    parent = parent.left;
                }
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


    private Node replacement(Node node) {
        if (node.left == null) {
            if (node.right == null) {
                return null;
            }
            node = node.right;

            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        node = node.left;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {


        Node node = root;
        V temp;
        int compare;


        do {
            temp = node.value;
            compare = key.compareTo(node.key);

            if (compare == 0) {
                if (size==1){
                    root=null;
                    return temp;
                }
                Node newNode = replacement(node);
                if (newNode == null) {
                    if( node.parent.right==node){
                        node.parent.right=null;
                    }else{
                        node.parent.left=null;
                    }
                    size--;
                    return temp;
                }
                if(newNode.right ==null && newNode.left==null){
                    node.value = newNode.value;
                    if( newNode.parent.right==newNode){
                        newNode.parent.right=null;
                    }else{
                        newNode.parent.left=null;
                    }
                }else if(newNode.right ==null){
                    newNode.parent.right=newNode.left;
                    node.value = newNode.value;

                }else{
                    newNode.parent.left=newNode.right;
                    node.value = newNode.value;
                }
                size--;
                return temp;
            }

            if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        } while (node != null);

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
    public void rotation(boolean isLeftRotation, Node node) {
        Node child;
        if (isLeftRotation) {
            child = node.right;
            node.right = child.left;
            child.left = node;
        } else {
            child = node.left;
            node.left = child.right;
            child.right = node;
        }
        if (node.key.compareTo(node.parent.key) < 0) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }
        node.parent = child;
    }

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private Node parent;

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
