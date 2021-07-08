package com.frt.utility;

public class DummyFactura {
    
    public DummyFactura() {

    }

    public static int getInvoiceNumber() {

        int randomNum = (int)(Math.random()*((1000000-100000)+1))+100000;

        return randomNum;

    }

}
