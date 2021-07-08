package com.frt.utility;

import com.frt.model.Invoice;
import com.frt.model.Note;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DocumentPDF {
    
    public static void GeneratePDF(Invoice invoice) throws Exception {
        try {
                      
            ByteArrayOutputStream outputStream = null;
            outputStream = new ByteArrayOutputStream();

            // Initialize PDF document
            //PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));

            // Initialize document
            Document document = new Document(pdf, PageSize.A8);
            document.setMargins(5, 5, 5, 5);

            Paragraph enc = AgregarParrafo("CONSTRUCTORA MARNHOS\n", TextAlignment.CENTER);
            enc.add(AgregarTexto("SOCIEDAD ANONIMA DE\n CAPITAL VARIABLE\n"));

            document.add(enc);

            float[] columnWidths = {5, 5};

            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.setFontSize(2);

            table.addCell(AgregarCelda("Km 52.5 Autopista Palin Escuintla", TextAlignment.LEFT));
            table.addCell(AgregarCelda("Nit 980928", TextAlignment.RIGHT));

            table.addCell(AgregarCelda("Factura Electronica", TextAlignment.LEFT));
            table.addCell(AgregarCelda("Fel", TextAlignment.RIGHT));

            document.add(table);

            Paragraph titulo = AgregarParrafo("DOCUMENTO TRIBUTARIO ELECTRONICO\n", TextAlignment.CENTER);

            document.add(titulo);

            columnWidths[0] = 12;
            columnWidths[1] = 12;

            Table tdFactura = new Table(UnitValue.createPercentArray(columnWidths));
            tdFactura.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdFactura.setFontSize(2);

            tdFactura.addCell(AgregarCelda("Serie " + invoice.serie, TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("NO " + invoice.factura, TextAlignment.RIGHT));

            tdFactura.addCell(AgregarCelda("IDENTIFICADOR INTERNO", TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("C114022611532", TextAlignment.RIGHT));

            tdFactura.addCell(AgregarCelda("Nombre XXX", TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("NIT " + invoice.nit, TextAlignment.RIGHT));

            tdFactura.addCell(AgregarCelda("Dirección", TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("Ciudad", TextAlignment.RIGHT));

            document.add(tdFactura);

            columnWidths[0] = 12f;
            columnWidths[1] = 12f;

            Table tdDetalle = new Table(UnitValue.createPercentArray(columnWidths));
            tdDetalle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdDetalle.setFontSize(2);
                               
            Timestamp timestamp = Timestamp.valueOf(invoice.getFecha());

            LocalDateTime now = timestamp.toLocalDateTime();

            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");  
         
                    
            tdDetalle.addCell(AgregarCelda("Fecha " + now.format(formatDate), TextAlignment.LEFT));
            tdDetalle.addCell(AgregarCelda("Hora " + now.format(formatTime), TextAlignment.RIGHT));
            tdDetalle.addCell(AgregarCelda("Numero ID 000001", TextAlignment.LEFT));
            tdDetalle.addCell(AgregarCelda("Via C11 ", TextAlignment.RIGHT));
            tdDetalle.addCell(AgregarCelda("Garita Escuintla-Palin", TextAlignment.LEFT));
            tdDetalle.addCell(AgregarCelda("Clase T0AC ", TextAlignment.RIGHT));
            tdDetalle.addCell(AgregarCelda("Automóvil", TextAlignment.LEFT));

            document.add(tdDetalle);

            Table tdTotal = new Table(UnitValue.createPercentArray(columnWidths));
            tdTotal.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdTotal.setFontSize(2);

            tdTotal.addCell(AgregarCelda("Tarifa de peaje", TextAlignment.LEFT));
            tdTotal.addCell(AgregarCelda("Q" + invoice.monto, TextAlignment.RIGHT));

            tdTotal.addCell(AgregarCelda("Paga Tarjeta", TextAlignment.LEFT));
            tdTotal.addCell(AgregarCelda("Q" + invoice.monto, TextAlignment.RIGHT));

            document.add(tdTotal);

            Paragraph pie = AgregarParrafo(invoice.firma  + "\n", TextAlignment.CENTER);
            pie.add(AgregarTexto("Autoriza pago Directo ISH resolucion 52871982220137\n"));
            pie.add(AgregarTexto("Agente Retenedor"));
            document.add(pie);

            //Close document
            document.close();

            Mail.sendMail(invoice, outputStream);


        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static void InvoiceFRTPDF(Invoice invoice) throws Exception {
        
        try {
            
                    
            ByteArrayOutputStream outputStream = null;
            outputStream = new ByteArrayOutputStream();

            // Initialize PDF document
            //PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));

            // Initialize document
            Document document = new Document(pdf, PageSize.A8);
            document.setMargins(5, 5, 5, 5);

            Paragraph enc = AgregarParrafo("Flowing Rivers Technologies", TextAlignment.CENTER);
                                    
            document.add(enc);

            float[] columnWidths = {5, 5};

            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.setFontSize(2);

            table.addCell(AgregarCelda("20 calle 5-36 zona 10", TextAlignment.LEFT));
            table.addCell(AgregarCelda("Nit 8999489-2", TextAlignment.RIGHT));

            table.addCell(AgregarCelda("Factura Electronica", TextAlignment.LEFT));
            table.addCell(AgregarCelda("Fel", TextAlignment.RIGHT));

            document.add(table);

            Paragraph titulo = AgregarParrafo("DOCUMENTO TRIBUTARIO ELECTRONICO\n", TextAlignment.CENTER);

            document.add(titulo);

            columnWidths[0] = 12;
            columnWidths[1] = 12;

            Table tdFactura = new Table(UnitValue.createPercentArray(columnWidths));
            tdFactura.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdFactura.setFontSize(2);

            tdFactura.addCell(AgregarCelda("Serie " + invoice.serie, TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("NO " + invoice.factura, TextAlignment.RIGHT));

            tdFactura.addCell(AgregarCelda("IDENTIFICADOR INTERNO", TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("C114022611532", TextAlignment.RIGHT));

            tdFactura.addCell(AgregarCelda("Nombre CONSTRUCTORA MARNHOS, S.A.", TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("NIT " + invoice.nit, TextAlignment.RIGHT));

            tdFactura.addCell(AgregarCelda("Dirección", TextAlignment.LEFT));
            tdFactura.addCell(AgregarCelda("Km 52.5 Autopista Palin Escuintla", TextAlignment.RIGHT));

            document.add(tdFactura);

            columnWidths[0] = 12f;
            columnWidths[1] = 12f;

            Table tdDetalle = new Table(UnitValue.createPercentArray(columnWidths));
            tdDetalle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdDetalle.setFontSize(2);
                               
            Timestamp timestamp = Timestamp.valueOf(invoice.getFecha());

            LocalDateTime now = timestamp.toLocalDateTime();

            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");  
         
                    
            tdDetalle.addCell(AgregarCelda("Fecha " + now.format(formatDate), TextAlignment.LEFT));
            tdDetalle.addCell(AgregarCelda("Hora " + now.format(formatTime), TextAlignment.RIGHT));

            document.add(tdDetalle);

            Table tdTotal = new Table(UnitValue.createPercentArray(columnWidths));
            tdTotal.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdTotal.setFontSize(2);

            tdTotal.addCell(AgregarCelda("Monto", TextAlignment.LEFT));
            tdTotal.addCell(AgregarCelda("Q" + invoice.monto, TextAlignment.RIGHT));

            document.add(tdTotal);

            Paragraph pie = AgregarParrafo(invoice.firma  + "\n", TextAlignment.CENTER);
            pie.add(AgregarTexto("Autoriza pago Directo ISH resolucion 52871982220137\n"));
            pie.add(AgregarTexto("Agente Retenedor"));
            document.add(pie);

            //Close document
            document.close();

            Mail.sendMail(invoice, outputStream);


        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public static void NotePDF(Note note, String correo) throws Exception {
        
        try {
            
                    
            ByteArrayOutputStream outputStream = null;
            outputStream = new ByteArrayOutputStream();

            // Initialize PDF document
            //PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
            PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));

            // Initialize document
            Document document = new Document(pdf, PageSize.A8);
            document.setMargins(5, 5, 5, 5);

            Paragraph enc = AgregarParrafo("Nota de Debito", TextAlignment.CENTER);
                                    
            document.add(enc);

            float[] columnWidths = {5, 5};

            Table tdDetalle = new Table(UnitValue.createPercentArray(columnWidths));
            tdDetalle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdDetalle.setFontSize(2);
                               
            Timestamp timestamp = Timestamp.valueOf(note.getFecha());

            LocalDateTime now = timestamp.toLocalDateTime();

            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");  
         
            tdDetalle.addCell(AgregarCelda("Nombre " + note.getNombre(), TextAlignment.LEFT));
            tdDetalle.addCell(AgregarCelda("Dirección " + note.getDireccion(), TextAlignment.RIGHT));
                    
            tdDetalle.addCell(AgregarCelda("Fecha " + now.format(formatDate), TextAlignment.LEFT));
            tdDetalle.addCell(AgregarCelda("Hora " + now.format(formatTime), TextAlignment.RIGHT));

            document.add(tdDetalle);

            Table tdTotal = new Table(UnitValue.createPercentArray(columnWidths));
            tdTotal.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tdTotal.setFontSize(2);

            tdTotal.addCell(AgregarCelda("Monto", TextAlignment.LEFT));
            tdTotal.addCell(AgregarCelda("Q" + note.getMonto(), TextAlignment.RIGHT));

            document.add(tdTotal);

            //Close document
            document.close();

            Mail.sendMail(correo, outputStream);


        } catch (Exception ex) {
            throw ex;
        }
    }
            
    private static Paragraph AgregarParrafo(String texto, TextAlignment alineacion) {

        Paragraph preface = new Paragraph(texto);
        preface.setTextAlignment(alineacion);
        preface.setFontSize(4);

        return preface;
    }

    private static Text AgregarTexto(String texto) {
        return new Text(texto);
    }

    private static Cell AgregarCelda(String texto, TextAlignment alineacion) {
        return new Cell()
                .add(new Paragraph(texto))
                .setFontSize(4)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(alineacion);
    }      

}
