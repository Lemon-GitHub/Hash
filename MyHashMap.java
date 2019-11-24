package MyHashMap;

public class MyHashMap {
    public static class Node {
        public int key;
        public int value;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] array = new Node[101];
    private int size = 0;
    private static final double FACTOR = 0.75;

    // 核心操作1：插入元素 put
    public void put(int key, int value) {
        //根据key调用hash函数获取下标
        int index = key % array.length;
        //根据下标找对应的链表
        Node head = array[index];
        //判断新节点是否在链表中存在
        for (Node cur = head; cur != null; cur = cur.next) {
            if (key == cur.key) {
                cur.value = value; //key已经存在 修改value即可
                return;
            }
        }
        //把新节点插入到链表中 头插
        Node newNode = new Node(key, value);
        newNode.next = head.next;
        array[index] = newNode;
        size++;

        //进行扩容的判定
        if ((double)size / array.length > FACTOR) {
            resize();
        }
    }

    private void resize() {
        Node[] newArray = new Node[array.length * 2 + 1];
        //遍历原来hash表中的每个元素 然后把元素插入到新的newArray
        for (int i = 0; i < array.length; i++) {
            //取到每个链表  再去遍历每个链表
            for (Node cur = array[i]; cur != null; cur = cur.next) {
                Node newNode = new Node(cur.key, cur.value);
                newNode.next = newArray[i];
                newArray[i] = newNode;
            }
        }
        array = newArray;
    }

    // 核心操作2：查找元素 get
    public Node get(int key) {
        //根据key计算下标
        int index = key % array.length;
        //根据下标找对应链表的头节点
        Node head = array[index];
        //在链表中查找
        for (Node cur = head; cur != null; cur = cur.next) {
            if (key == cur.key) {
                return cur;
            }
        }
        return null;
    }
    // 核心操作3：删除元素 remove
    public void remove(int key) {
        //根据key计算下标
        int index = key % array.length;
        //根据下标找对应的链表
        Node head = array[index];
        //在链表查找并且删除
        if(key == head.key){
           array[index] = head.next;
           size--;
           return;
        }

        Node prev = head;
        while (prev != null && prev.next != null) {
            if (prev.next.key == key) {
                prev.next = prev.next.next;
                size--;
                break;
            }
        }
    }
}
