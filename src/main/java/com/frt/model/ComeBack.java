package com.frt.model;

import java.util.ArrayList;
import java.util.List;

public class ComeBack {

    private int code;
    private String msg;
    private String status;
    private List<InvoiceResponse> data = new ArrayList<>();
    private Note note;
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<InvoiceResponse> getData() {
        return data;
    }
    public void setData(List<InvoiceResponse> data) {
        this.data = data;
    }
    public Note getNote() {
        return note;
    }
    public void setNote(Note note) {
        this.note = note;
    }

    
}
