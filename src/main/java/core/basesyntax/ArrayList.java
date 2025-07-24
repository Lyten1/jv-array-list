package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_SIZE = 10;

    private Object[] data;
    private int size;

    public ArrayList() {
        this.data = new Object[DEFAULT_SIZE];
    }

    private void grow() {
        int newSize = data.length + (data.length >> 1);
        this.data = Arrays.copyOf(data, newSize);
    }

    @Override
    public String toString() {
        return "ArrayList{"
                + "data=" + Arrays.toString(data)
                + '}';
    }

    private void innerAdd(T value, int s) {
        if (size == data.length) {
            grow();
        }
        if (s < size) {
            System.arraycopy(data, s, data, s + 1, size - s);
        }
        try {
            data[s] = value;
            size++;
        } catch (IndexOutOfBoundsException e) {
            throw new ArrayListIndexOutOfBoundsException(
                    "Adding new element at index is failed: index out of bounds", e);
        }
    }

    @Override
    public void add(T value) {
        innerAdd(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        innerAdd(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("list can't be null");
        }

        while (data.length < size + list.size()) {
            grow();
        }

        Object[] inputArray = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            inputArray[i] = list.get(i);
        }

        System.arraycopy(inputArray, 0, data, size, list.size());
        size += list.size();
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return data[index] != null ? (T) data[index] : null;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        data[index] = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldVar;
        oldVar = (T) data[index];
        data[index] = null;
        size--;
        if (index < size) {
            System.arraycopy(data, index + 1, data, index, size - index);
        }
        return oldVar;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element == null ? data[i] == null : element.equals(data[i])) {
                T elem = (T) data[i];
                remove(i);
                return elem;
            }
        }
        throw new NoSuchElementException("No such element in the List");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException(
                    "Index is out of bounds");
        }
        return true;
    }

}
