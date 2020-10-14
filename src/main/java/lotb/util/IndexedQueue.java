package lotb.util;

import javax.annotation.Nullable;

public class IndexedQueue<E> {

    private final Object[] queue;
    private E previous;
    private int modIndex;
    private int index;

    public IndexedQueue() {
        this(Integer.MAX_VALUE - 8);
    }

    public IndexedQueue(int size) {
        this.queue = new Object[size];
        this.index = 0;
        this.modIndex = 0;
        this.previous = null;
    }

    public int getSize() {
        return this.queue.length;
    }

    public void resetIndex() {
        this.index = 0;
    }

    public void add(E e) {
        Valid.notNull(e, "Can not add element to queue. Element is null");
        int freeElement = this.checkForFreeElement();
        if (freeElement != -1 && freeElement != this.modIndex) {
            queue[freeElement] = e;
        } else {
            if (modIndex >= queue.length)
                throw new IndexOutOfBoundsException("Can not add element to queue. " + modIndex + " >= " + queue.length);
            queue[modIndex++] = e;
        }
    }

    public E getPrevious() {
        if (index == 0) {
            int j = queue.length - 1;
            do {
                E e = (E) this.queue[j];
                if (e != null)
                    break;
                j--;
            } while (j >= 0);
            this.previous = (E) this.queue[j];
            return this.previous;
        } else {
            this.previous = (E) this.queue[this.index - 1];
            return this.previous;
        }
    }

    @Nullable
    public E peek() {
        int j = index;
        if (index >= this.queue.length)
            index = 0;
        E e = (E) this.queue[index];
        index++;
        this.previous = this.getPrevious();
        return e;
    }

    public void remove(int index, boolean resetIndex) {
        Valid.checkTrue(index < 0 || index >= this.queue.length, "Index out of range");
        this.queue[index] = null;
        if (resetIndex)
            this.index = 0;
    }

    public void remove(int index) {
        this.remove(index, true);
    }

    public boolean isEmpty() {
        for (Object o : this.queue)
            if (o != null)
                return false;
        return true;
    }

    public E peekAndRemove(boolean resetIndex) {
        if (index >= this.queue.length)
            index = 0;
        E e = (E) this.queue[index];
        this.remove(index, resetIndex);
        index++;
        return e;
    }

    public E peekAndRemove() {
        return this.peekAndRemove(true);
    }

    public int getCurrentIndex() {
        return this.index;
    }

    public E peekFirst() {
        return (E) this.queue[0];
    }

    public E peekLast() {
        return (E) this.queue[this.queue.length - 1];
    }

    private int checkForFreeElement() {
        for (int i = 0; i < this.queue.length; i++)
            if (this.queue[i] == null)
                return i;
        return -1;
    }

}
