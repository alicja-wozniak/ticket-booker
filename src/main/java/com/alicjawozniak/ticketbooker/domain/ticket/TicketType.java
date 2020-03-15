package com.alicjawozniak.ticketbooker.domain.ticket;

public enum TicketType {
    ADULT(2500),
    STUDENT(1800),
    CHILD(1250);

    private final long price;

    TicketType(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }
}
