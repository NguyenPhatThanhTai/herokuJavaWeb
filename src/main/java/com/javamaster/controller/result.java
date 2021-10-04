package com.javamaster.controller;

public class result {
    private String status;
    private String result;

    public result() {
    }

    public result(String status, String result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
