package model.Iterator;

import java.util.*;
import model.Ticket;

public class TicketIterator implements ParkingIterator<Ticket> {

    private List<Ticket> tickets;
    private int index = 0;

    public TicketIterator(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean hasNext() {
        return index < tickets.size();
    }

    @Override
    public Ticket next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more tickets available.");
        }
        return tickets.get(index++);
    }
}
