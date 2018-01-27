package com.pvanshah.sjsuquizapplication.student.model;

public class Options {
    private String D;

    private String A;

    private String B;

    private String C;

    public String getD() {
        return D;
    }

    public void setD(String D) {
        this.D = D;
    }

    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

    @Override
    public String toString() {
        return "ClassPojo [D = " + D + ", A = " + A + ", B = " + B + ", C = " + C + "]";
    }
}