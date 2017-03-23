package com.example.pavanshah.mortgagecalculator;


public class PropertyDetails
{
    String PropertyType;
    String Address;
    String City;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    String price;

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String downPayment;

    String zip;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    String ID;
    Double LoanAmount, APR, MonthlyPayment;
    Double Latitude, Longitude;

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Double getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        LoanAmount = loanAmount;
    }

    public Double getAPR() {
        return APR;
    }

    public void setAPR(Double APR) {
        this.APR = APR;
    }

    public Double getMonthlyPayment() {
        return MonthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        MonthlyPayment = monthlyPayment;
    }
}
