package com.coworking.dto;

import java.util.Date;
import java.util.Map;

public class BookingRequest {
    private String workspaceId;
    private Date startDate;
    private Date endDate;
    private String paymentMethod;
    private Map<String, String> paymentDetails;

    public BookingRequest() {
    }

    public BookingRequest(String workspaceId, Date startDate, Date endDate,
            String paymentMethod, Map<String, String> paymentDetails) {
        this.workspaceId = workspaceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Map<String, String> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Map<String, String> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}