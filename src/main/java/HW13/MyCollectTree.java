package HW13;

import java.util.*;

public class MyCollectTree implements Set {
    private Node root;

    @Override
    public boolean add(Object o) {
        if (o == null) return false;
        if (root == null) {
            root = new Node((Integer) o);
            return true;
        } else {
            return adding((Integer) o, root);
        }
    }

/*
    //@Override //для объектов они должны быть сравнимые, тогда веде в операторах сравнения пейлоад использовать compareTo
    public boolean OtherAdd(Comparable <? extends Object> o) {
        if (o == null) return false;
        if (!(o instanceof Comparable)) throw new IllegalArgumentException("payload must be comparable");
        if (root == null) {
            root = new Node((Integer) o);
            return true;
        } else {
            return adding((Integer) o, root);
        }
    }
*/

    private boolean adding(int o, Node node) {
        if (node.payload == o) {
            return false;
        } else if (node.payload > o) {
            if (node.left == null) {
                node.left = new Node(o);
            } else {
                adding(o, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = new Node(o);
            } else {
                adding(o, node.right);
            }
        }
        return true;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public int size() {
        return sizeInt(root);
    }

    private int sizeInt(Node node) {
        if (node == null) return 0;
        return 1 + sizeInt(node.left) + sizeInt(node.right);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object o) {
        return containsIn((Integer) o, root);
    }

    private boolean containsIn(int o, Node node) {
        if (node == null) return false;
        if (node.payload == o) {
            return true;
        }
        return o < node.payload
                ? containsIn(o, node.left)
                : containsIn(o, node.right);
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        int sizeBefore = this.size();
        root = deleteRecursive(root, (Integer) o);
        return this.size() == sizeBefore - 1;
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.payload) {

            if (current.left == null && current.right == null) {
                return null;
            }

            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            int smallestValue = findSmallestValue(current.right);
            current.payload = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;

        }
        if (value < current.payload) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }
        current.right = deleteRecursive(current.right, value);
        return current;
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.payload : findSmallestValue(root.left);
    }

    @Override
    public boolean addAll(Collection collection) {
        if (collection == null) return false;
        int sizeBefore = this.size();
        if (collection.size() == 0) return false;
        Object[] tmp = collection.toArray();
        Collections.addAll(this, tmp);
        return sizeBefore != this.size();
    }

    @Override
    public boolean retainAll(Collection collection) {
        //пересечение множеств
        if (collection == null) return false;
        int sizeBefore = this.size();
        if (sizeBefore == 0) return false;
        Object[] tmp = this.toArray();
        for (Object cur : tmp) {
            if (!collection.contains(cur)) this.remove(cur);
        }
        return sizeBefore != this.size();
    }

    @Override
    public boolean removeAll(Collection collection) {
        //удалить из этой все, что есть во второй
        if (collection == null) return false;
        int sizeBefore = this.size();
        Object[] tmp = collection.toArray();
        for (Object other : tmp) {
            this.remove(other);
        }
        return sizeBefore != this.size();
    }

    @Override
    public boolean containsAll(Collection collection) {
        //содержит ли эта все, что есть во второй
        if (collection == null) return false;
        Object[] tmp = collection.toArray();
        for (Object other : tmp) {
            if (!this.contains(other)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            Stack<Node> stack;

            {
                Node rootIt = root;
                stack = new Stack<>();
                while (rootIt != null) {
                    stack.push(rootIt);
                    rootIt = rootIt.left;
                }
            }

            public boolean hasNext() {
                return !stack.isEmpty();
            }

            public Object next() {
                Node node = stack.pop();
                int result = node.payload;
                if (node.right != null) {
                    node = node.right;
                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
                return result;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size()];
        if (result.length != 0) toArrayInt(root, result, 0);
        return result;
    }

    @Override
    public Object[] toArray(Object[] result) {
        if (this.size() > 0) toArrayInt(root, result, 0);
        return result;
    }

    private int toArrayInt(Node node, Object[] result, int index) {

        if (node.left != null) {
            index = toArrayInt(node.left, result, index);
        }

        if (node.right != null) {
            index = toArrayInt(node.right, result, index);
        }

        result[index] = node.payload;
        return index + 1;
    }

    @Override
    public String toString() {
        String result = toStringInt(root);
        result = "[" + ((!result.equals("")) ? result.substring(0, result.length() - 2) : "EMPTY") + "]";
        return result;
    }

    private String toStringInt(Node node) {
        String result = "";
        if (node == null) return "";
        result += toStringInt(node.left);
        result += node.payload + ", ";
        result += toStringInt(node.right);

        return result;
    }

    public class Node implements Comparable {

        private int payload;
        private Node right, left;

        public Node(int payload) {
            this.payload = payload;
        }

        @Override
        public String toString() {
            return "[node.payload: " + payload + ']';
        }

        @Override
        //попытка прикрутить сравнимые для перехода с инт на обжект, пока неудачно
        public int compareTo(Object o) {
            Node other = (Node) o;
            return (this.payload) - (other.payload);//если полезная нагрузка - обжект, то она должна быть сравнимая
        }
    }//Node

}//MyCollectTree