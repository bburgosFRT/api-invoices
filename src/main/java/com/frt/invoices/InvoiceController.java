package com.frt.invoices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.frt.model.*;
import com.frt.utility.*;
import com.google.gson.Gson;

@RestController
@RequestMapping("/invoices/api/v1")
public class InvoiceController {

    static final List<ComeBack> listaRespuesta = new ArrayList<>();
    static final List<InvoiceResponse> data = new ArrayList<>();
    static Integer status;

    public static List<ComeBack> getListarespuesta() {
        return listaRespuesta;
    }

    public static List<InvoiceResponse> getData() {
        return data;
    }

    public static Integer getStatus() {
        return status;
    }

    public static void setStatus(Integer status) {
        InvoiceController.status = status;
    }

    @GetMapping("/check")
    public String getUrl(@RequestParam String Url){
        String returnMessage = "Esto es una prueba " + Url;

        return returnMessage;
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<ComeBack>factura(@RequestBody Invoice invoice){
        
        ComeBack comeBack = new ComeBack();
        InvoiceResponse invoiceR = new InvoiceResponse();
        InvoiceResponse invoiceRF = new InvoiceResponse();        

        try {

            status = null;

            //validaciones
            Validate(invoice);
            
            //genera factura cliente
            Timestamp timestampInvoice = new Timestamp(System.currentTimeMillis());

            invoice.fecha = timestampInvoice.toString();
            invoice.firma = DummyFirma.getAlphaNumericString(32);
            invoice.factura = DummyFactura.getInvoiceNumber();
            invoice.serie = DummyFirma.getAlphaNumericString(6).toUpperCase();

            DocumentPDF.GeneratePDF(invoice);
            
            invoiceR.setFecha(invoice.fecha);
            invoiceR.setFirma(invoice.firma);
            invoiceR.setFactura(invoice.factura);
            invoiceR.setSerie(invoice.serie);
            invoiceR.setNombre("Cliente XXX");
            invoiceR.setNit(invoice.nit);
            invoiceR.setMonto(invoice.monto);
            data.add(invoiceR); 

            //genera factura FRT
            Invoice invoiceFRT = new Invoice();

            invoiceFRT.setNit("980928");
            invoiceFRT.setMonto(1.42);
            invoiceFRT.fecha = timestampInvoice.toString();
            invoiceFRT.firma = DummyFirma.getAlphaNumericString(32);
            invoiceFRT.factura = DummyFactura.getInvoiceNumber();
            invoiceFRT.serie = DummyFirma.getAlphaNumericString(6).toUpperCase();
            invoiceFRT.correo = invoice.correo;

            DocumentPDF.InvoiceFRTPDF(invoiceFRT);

            //guarda la factura cliente en la lista data
            invoiceRF.setFecha(invoiceFRT.fecha);
            invoiceRF.setFirma(invoiceFRT.firma);
            invoiceRF.setFactura(invoiceFRT.factura);
            invoiceRF.setSerie(invoiceFRT.serie);
            invoiceRF.setNombre("CONSTRUCTORA MARNHOS, S.A.");
            invoiceRF.setNit(invoiceFRT.nit);
            invoiceRF.setMonto(1.42);
            data.add(invoiceRF);
            
            //crea nota de debiito
            Note note = new Note();
            note.setFecha(timestampInvoice.toString());
            note.setNombre("CONSTRUCTORA MARNHOS, S.A.");
            note.setDireccion("Km 52.5 Autopista Palin Escuintla");
            note.setMonto(4.48);
            note.setNumero(DummyFactura.getInvoiceNumber());
            
            DocumentPDF.NotePDF(note, invoice.correo);
            
            //crea respueta

            comeBack.setCode(HttpStatus.CREATED.value());
            comeBack.setMsg("Operación Exitosa. Correo Enviado");
            comeBack.setStatus("SUCCESS".toLowerCase());
            comeBack.setData(data);
            comeBack.setNote(note);

            //agrega a lista de repuesta las facturas
            String gson = new Gson().toJson(comeBack);            
                
            return new ResponseEntity<ComeBack>(comeBack, null, HttpStatus.ACCEPTED);  

        } catch (Exception e) {
            comeBack.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            comeBack.setMsg(e.getMessage());
            comeBack.setStatus("Error");
            listaRespuesta.add(comeBack);
            return new ResponseEntity<ComeBack>(comeBack, null, HttpStatus.INTERNAL_SERVER_ERROR);            
        }

    }

    private void Validate(Invoice invoice) throws Exception {

        try {

            if (invoice.getCorreo().isEmpty()) {
                throw new Exception("El correo es un dato requerido");
            }

            if (!isValidaMail(invoice.getCorreo())) {
                throw new Exception("El parametro de correo enviado no es valido");
            }

            if (invoice.getNit().isEmpty()) {
                throw new Exception("El parámetro fecha es un dato requerido");
            }

            if (invoice.getMonto() == null) {
                throw new Exception("El parámetro monto es un dato requerido");
            }

            if (invoice.getMonto() == 0) {
                throw new Exception("El parámetro monto enviado no puede ser cero");
            }

        } catch (Exception e) {
            throw e;
        }

    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isValidaMail(String str) {
        return str.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }    
    
}
