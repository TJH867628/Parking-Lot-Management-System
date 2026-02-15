package model.Iterator;

import java.util.List;
import java.util.NoSuchElementException;
import model.FineScheme;

public class FineSchemeIterator {

    private List<FineScheme> schemes;
    private int index = 0;

    public FineSchemeIterator(List<FineScheme> schemes) {
        this.schemes = schemes;
    }

    public boolean hasNext() {
        return index < schemes.size();
    }

    public FineScheme next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more fine schemes.");
        }
        return schemes.get(index++);
    }
}
