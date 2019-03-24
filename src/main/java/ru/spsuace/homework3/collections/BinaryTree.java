package ru.spsuace.homework3.collections;


/**
 * Реализовать основные методы бинарного дерева поиска. Перебалансировку делать не нужно.
 * Считаем, что null не может быть в качестве ключа.
 * В качестве дополнительного задания, надо реализовать поворот дерева.
 */
public class BinaryTree<K extends Comparable<K>, V> {

    int counter = 0; //Подумать про начальные данные
    Node root; //Корень

    /**
     * Находит элемент с заданным ключом и возвращает его. Если ключа нет, то мы возвращаем null
     */
    public V get(K key) {
        if (find(key, root) == null) {
            return null;
        }
        return find(key, root).value;
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

            if (temp.rigth == null) {
                return null;
            } else {
                resultFind = find(key, temp.rigth);
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
            if (temp.rigth == null) {
                temp.rigth = new Node(key, value, temp);
                counter++;
                return null;
            } else {
                add(key, value, temp.rigth);
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
        if (result.rigth == null && result.left == null) {
            if (result.parent.left == null) {
                result.parent.rigth = null;
                return result.value;
            }
            if (result.parent.rigth == null) {
                result.parent.left = null;
                return result.value;
            }
            if (result.parent.left.key.compareTo(result.key) == 0) { //Строка обозначает (ничего лучше пока не придумал) Что мы идет к родителю и проверяем их детей слева (сначала), если ключ совпадает с необходимым, то обнуляем ссылку на него
                result.parent.left = null;
            } else {
                result.parent.rigth = null;
            }
            return result.value;
        }
        //2 вариант (с одним ребенком (слева или справа))
        if (result.rigth == null && result.left != null) {
            if (result.parent.left.key.compareTo(result.key) == 0) {
                result.parent.left = result.left;
            } else {
                result.parent.rigth = result.left;
            }
            return result.value; //вывод
        }
        if (result.rigth != null && result.left == null) {
            if (result.parent.left.key.compareTo(result.key) == 0) {
                result.parent.left = result.rigth;
            } else {
                result.parent.rigth = result.rigth;
            }
            return result.value; //вывод
        }
        //3 вариант (присутствуют оба ребенка)
        Node nodeMax = result.left;
        for (;;) { //бесконечный цикл для поиска максимального элемента из минимальной ветки
            if (nodeMax.rigth == null) {
                break;
            } else {
                nodeMax = nodeMax.rigth;
            }
        }
        //Элемент найден (теперь необходимо изменить ссылки)
        Node temp = new Node(result.key, result.value); // для вывода
        result.key = nodeMax.key;
        result.value = nodeMax.value;
        nodeMax.parent.rigth = nodeMax.left;
        if (nodeMax.left != null) {
            nodeMax.left.parent = nodeMax.parent;
        }
        return temp.value;
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
        private Node rigth;
        private Node parent; //Cсылка на родительский элемент

        public Node(K key, V value) { //конструктор для первого элемента
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node parent) { //конструктор для n>1 элемента
            //подумать
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}
