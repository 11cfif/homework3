package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {

    private int counter = 0; //Подумать про начальные данные
    private Node root; //Корень

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        if (root != null){
            Node find = find(key, root);
            if (find == null) {
                return null;
            }
            return find.value;
        }else {
            return null;
        }

    }

    public Node find(K key, Node temp) {
        Node resultFind = null;
        if (temp.key.compareTo(key) > 0) {
            if (temp.left == null) {
                return null;
            } else {
                resultFind = find(key, temp.left);
            }
        }
        if (temp.key.compareTo(key) == 0) {
            resultFind = temp;
            return resultFind;
        }
        if (temp.key.compareTo(key) < 0) {

            if (temp.right == null) {
                return null;
            } else {
                resultFind = find(key, temp.right);
            }
        }
        return resultFind;
    }
    /**
     * Добавляем новую пару ключ значение в нашу структуру. Если ключ уже существует -
     * просто меняем в нем значение и возвращаем старое
     */
    public V put(K key, V value) {
        V result = null;
        if (root == null) {
            root = new Node(key, value);
            counter++;
        } else {
            result = add(key, value, root);
        }
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    public V add(K key, V value, Node temp) {
        if (temp.key.compareTo(key) > 0) {
            if (temp.left == null) {
                temp.left = new Node(key, value, temp);
                counter++;
                return null;
            } else {
                add(key, value, temp.left);
            }
        }
        if (temp.key.compareTo(key) == 0) {
            temp.value = value;
            return value;
        }
        if (temp.key.compareTo(key) < 0) {
            if (temp.right == null) {
                temp.right = new Node(key, value, temp);
                counter++;
                return null;
            } else {
                add(key, value, temp.right);
            }
        }
        return null;
    }

    /**
     * Удаляет элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V remove(K key) {
        Node result = find(key, root);//Для начала нужно найти элемент
        if (result == null) {
            return null;
        }
        counter--;
        //1 вариант (последний элемент дерева, без детей)
        if (result.right == null && result.left == null) {
            if (result.parent.left == null) {
                result.parent.right = null;
                return result.value;
            }
            if (result.parent.right == null) {
                result.parent.left = null;
                return result.value;
            }
            if (result.parent.left.key.compareTo(result.key) == 0) { //Строка обозначает (ничего лучше пока не придумал) Что мы идет к родителю и проверяем их детей слева (сначала), если ключ совпадает с необходимым, то обнуляем ссылку на него
                result.parent.left = null;
            } else {
                result.parent.right = null;
            }
            return result.value;
        }
        //2 вариант (с одним ребенком (слева или справа))
        if (result.right == null && result.left != null) {
            if (result.parent.left.key.compareTo(result.key) == 0) {
                result.parent.left = result.left;
            } else {
                result.parent.right = result.left;
                //result
            }
            result.left.parent = result.parent;
            return result.value; //вывод
        }
        if (result.right != null && result.left == null) {
            if (result.parent.left.key.compareTo(result.key) == 0) {
                result.parent.left = result.right;
            } else {
                result.parent.right = result.right;
            }
            result.right.parent = result.parent;
            return result.value; //вывод
        }
        //3 вариант (присутствуют оба ребенка)
        Node nodeMax = result.left;
        for (;;) { //бесконечный цикл для поиска максимального элемента из минимальной ветки
            if (nodeMax.right == null) {
                break;
            } else {
                nodeMax = nodeMax.right;
            }
        }
        //Элемент найден (теперь необходимо изменить ссылки)
        V valueM = result.value; //Для вывода
        result.key = nodeMax.key;
        result.value = nodeMax.value;
        nodeMax.parent.right = nodeMax.left;
        if (nodeMax.left != null) {
            nodeMax.left.parent = nodeMax.parent;
        }
        return valueM;
    }
    /**
     * Возвращаем размер дерева
     */
    public int size() {
        return counter;
    }

    /**
     * Выполняем левый или правый поворот относительно заданного узла
     */
    public void rotation(boolean isLeftRotation, Node node) {

    }

    private class Node {
        private K key;
        private V value;
        private Node left; // left и right — ссылки на узлы, являющиеся детьми данного узла
        private Node right;
        private Node parent; //Cсылка на родительский элемент

        Node(K key, V value) { //конструктор для первого элемента
            this.key = key;
            this.value = value;
        }

        Node(K key, V value, Node parent) { //конструктор для n>1 элемента
            //подумать
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}
