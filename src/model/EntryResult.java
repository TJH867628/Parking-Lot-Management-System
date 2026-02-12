package model;

public class EntryResult {
    private boolean success;
    private String message;
    private Ticket ticket;

    private EntryResult(boolean success, String message, Ticket ticket) {
        this.success = success;
        this.message = message;
        this.ticket = ticket;
    }

    public static EntryResult success(String message, Ticket ticket) {
        return new EntryResult(true, message, ticket);
    }

    public static EntryResult failure(String message) {
        return new EntryResult(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
