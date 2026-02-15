package model.Iterator;

import java.util.List;
import java.util.NoSuchElementException;
import model.SpotRule;

public class SpotRuleIterator implements ParkingIterator<SpotRule> {
     private List<SpotRule> rules;
    private int index = 0;

    public SpotRuleIterator(List<SpotRule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean hasNext() {
        return index < rules.size();
    }

    @Override
    public SpotRule next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more rules.");
        }
        return rules.get(index++);
    }
}
