package model;

import java.time.LocalDate;
import java.util.List;

public class CancellationResult {

    private boolean cancelled;

    private int resourceId;
    private int periodId;

    private LocalDate bookingDate;

    private List<String> waitingStaff;


    public CancellationResult(
            boolean cancelled,
            int resourceId,
            int periodId,
            LocalDate bookingDate,
            List<String> waitingStaff
    ) {

        this.cancelled = cancelled;

        this.resourceId = resourceId;
        this.periodId = periodId;

        this.bookingDate = bookingDate;

        this.waitingStaff = waitingStaff;
    }


    public boolean isCancelled() {

        return cancelled;
    }


    public int getResourceId() {

        return resourceId;
    }


    public int getPeriodId() {

        return periodId;
    }


    public LocalDate getBookingDate() {

        return bookingDate;
    }


    public List<String> getWaitingStaff() {

        return waitingStaff;
    }
}