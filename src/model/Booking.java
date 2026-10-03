package model;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private int resourceId;
    private int periodId;
    private String staffName;
    private LocalDate bookingDate;
    private String purpose;
    private String status;

    public Booking(
            int bookingId,
            int resourceId,
            int periodId,
            String staffName,
            LocalDate bookingDate,
            String purpose,
            String status
    ) {

        this.bookingId = bookingId;
        this.resourceId = resourceId;
        this.periodId = periodId;
        this.staffName = staffName;
        this.bookingDate = bookingDate;
        this.purpose = purpose;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getPeriodId() {
        return periodId;
    }

    public String getStaffName() {
        return staffName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStatus() {
        return status;
    }
}