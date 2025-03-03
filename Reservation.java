class Reservation implements java.io.Serializable {
    private int reservationId;
    private int workspaceId;
    private String customerName;
    private String date;
    private String startTime;
    private String endTime;

    public Reservation(int reservationId, int workspaceId, String customerName, String date, String startTime, String endTime) {
        this.reservationId = reservationId;
        this.workspaceId = workspaceId;
        this.customerName = customerName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String toString() {
        return "Reservation ID: " + reservationId + ", Workspace ID: " + workspaceId + ", Customer: " + customerName + ", Date: " + date + ", Time: " + startTime + " - " + endTime;
    }
}