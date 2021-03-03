package com.tevo.frutas.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfBoolean;
//import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tevo.frutas.models.entity.ordencompra.OrdenCompra;
import com.tevo.frutas.models.entity.ordencompra.OrdenCompraDetalle;
import com.tevo.frutas.models.entity.ordencompra.OrdenCompraEmpaquetado;

@Component("compra/pdf")
public class OrdenCompraPdfView extends AbstractPdfView {
	private Document doc;
	private PdfWriter wrt;
	//private PdfContentByte cb;
	private String titulo = "Error";
	private Long idOrdenConpra = 0L;
	private OrdenCompra ordenCompra;
	private List<OrdenCompraDetalle> detalle = null;
	private PdfPTable table1,table2,table3,table4;
	private PdfPCell cell;
	private byte[] bytes;
	private Integer numOrdenCompraEmpaqueTotal = 0;
	private Integer numOrdenCompraEmpaque = 0;
	private DecimalFormat df = new DecimalFormat("0.00");
	private String aux;
	private Integer cont= 0;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ordenCompra = null;
		ordenCompra = (OrdenCompra) model.get("ordenCompra");
		doc = document;
		wrt = writer;
		
		initializeVariables();
		
		if(ordenCompra != null) {
			detalle = ordenCompra.getDetalle();
			idOrdenConpra = ordenCompra.getId();
			titulo = "Orden de Compra - " + idOrdenConpra.toString();
			for(int i=0; i<detalle.size();i++) {
				numOrdenCompraEmpaqueTotal += detalle.get(i).getEmpaques().size();
			}
		}		
		setDatosGenerales();
					
		if((ordenCompra!= null & detalle!=null) && detalle.size()>0 && numOrdenCompraEmpaqueTotal>0) {
			
			for(int i=0; i<detalle.size() ;i++) {
				numOrdenCompraEmpaque = detalle.get(i).getEmpaques().size();
				for(int j=0; j<detalle.get(i).getEmpaques().size(); j++) {
					if(cont % 6 == 0) {
						if (cont != 0) {
							table1.completeRow();
							doc.add(table1);						
						}				
						doc.setPageSize(PageSize.A4.rotate());
						doc.newPage();						
						table1 = new PdfPTable(new float[] { 50, 50 });
						table1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table1.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
						table1.setWidthPercentage(100);		
					}
						insertDetalle(i,j);
						cont++;						
				}
			}
			
			table1.completeRow();
			doc.add(table1);
								
		} else {
			doc.newPage();
			doc.add(new Paragraph("Error al seleccionar orden de compra"));
		}
		doc.close();
	}
	
	private void initializeVariables() {
		//cb = wrt.getDirectContent();
		cell = new PdfPCell();
		cont=0;
		titulo = "Error";
		idOrdenConpra = 0L;
		detalle = null;
		aux="";
		bytes = null;
		numOrdenCompraEmpaqueTotal = 0;
		numOrdenCompraEmpaque = 0;
		aux="";		
	}
	
	private void setDatosGenerales() {
		doc.addTitle(titulo);
		doc.addCreationDate();
		if(idOrdenConpra == 0L) {
			doc.addHeader("Content-Disposition", "attachment;filename=OrdenCompra_"+idOrdenConpra.toString()+".pdf");
			doc.addHeader("Content-Disposition", "inline;filename=OrdenCompra_"+idOrdenConpra.toString()+".pdf");
		}else {
			doc.addHeader("Content-Disposition", "attachment;filename=Error.pdf");
			doc.addHeader("Content-Disposition", "inline;filename=Error.pdf");
		}
		doc.addSubject("Detalle de Orden de compra");
		doc.addAuthor("Tevology");
		doc.addCreator("Tevology");
		
		wrt.addViewerPreference(PdfName.DISPLAYDOCTITLE, new PdfBoolean(true));		
		doc.open();
	}
	
	private void insertDetalle(Integer i, Integer j) {
		bytes = null;
		String info = "";
		
		OrdenCompraDetalle ordenDetalle = ordenCompra.getDetalle().get(i);
		OrdenCompraEmpaquetado empaquetado = ordenDetalle.getEmpaques().get(j);
		
		table2 = new PdfPTable(1);
		table2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	    table2.setHorizontalAlignment(Element.ALIGN_LEFT);
	    
		table3 = new PdfPTable(new float[] { 70, 30 });
		table3.getDefaultCell().setBorder(PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
		table3.setHorizontalAlignment(Element.ALIGN_LEFT);	
		
		table4 = new PdfPTable(1);
		table4.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	    table4.setHorizontalAlignment(Element.ALIGN_LEFT);
	    
	    //Liquidacion de Compra
	    aux = "LIQ. COMPRA : " + ordenCompra.getNroOrden();
	    info = aux;
	    cell = new PdfPCell(new Phrase(aux));
	    cell.setBorder(PdfPCell.LEFT | PdfPCell.TOP);
	    cell.setPadding(5);
	    table4.addCell(cell);
	    
	    //Tipo de fruta
	    String frutaNombre = ordenDetalle.getFruta()!=null?ordenDetalle.getFruta().getNombre():" ";
	    String frutaCategoria= ordenDetalle.getCategoriaFruta()!=null?ordenDetalle.getCategoriaFruta().getNombre():" ";
	    String frutaVariedad = ordenDetalle.getFrutaVariedad()!=null?ordenDetalle.getFrutaVariedad().getDescripcion():" ";
	    String frutaTamanio  = ordenDetalle.getTamanoFruta()!=null?ordenDetalle.getTamanoFruta().getNombre():" ";
	    
	    aux =    frutaNombre + "-" 
	    		+ frutaVariedad + "-"
	    		+ frutaCategoria + "-" 
	    		+ frutaTamanio;
	    info = info + "\n" + aux;
		cell = new PdfPCell(new Phrase(aux));
	    cell.setBorder(PdfPCell.LEFT);
	    cell.setPadding(5);
	    table4.addCell(cell);
	    
	    //Tipo de empaque
	    aux = "TIPO " + empaquetado.getSubTipoEmpaque().getTipoEmpaque().getNombre() + " : " 
	    				+ empaquetado.getSubTipoEmpaque().getTipoEmpaque().getNombre() + " " + empaquetado.getSubTipoEmpaque().getNombre();
	    info = info + "\n" + aux;
	    cell = new PdfPCell(new Phrase(aux));
	    cell.setBorder(PdfPCell.LEFT);
	    cell.setPadding(5);
	    table4.addCell(cell);
	    //Numero Correlativo
	    aux = "CORRELATIVO " + empaquetado.getSubTipoEmpaque().getTipoEmpaque().getNombre()  + " : " 
	    		+ String.format("%02d", j+1) + "/" + String.format("%02d", numOrdenCompraEmpaque);
	    info = info + "\n" + aux;
	    cell = new PdfPCell(new Phrase(aux));
	    cell.setBorder(PdfPCell.LEFT);
	    cell.setPadding(5);
	    table4.addCell(cell);
	    
	    //Peso Inicial
	    aux = "PESO INICIAL : " + df.format(empaquetado.getPesoCompra()) + " " + ordenDetalle.getUnidadCompra().getNombre();
	    info = info + "\n" + aux;
	    cell = new PdfPCell(new Phrase(aux));
	    cell.setBorder(PdfPCell.LEFT | PdfPCell.BOTTOM);
	    cell.setPadding(5);
	    table4.addCell(cell);
	    table4.completeRow();
	    
	    String auxCodJava = "COD. JAVA: " +  empaquetado.getIdentificador();
	    info = info + "\n" + auxCodJava;
	    
		cell = new PdfPCell(table4);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPadding(0);
	    table3.addCell(cell);
	    
	    bytes = getQRCodeImage(info, 15 , 15 );

	    if(bytes!= null) {
			try {
				Image qrImage = Image.getInstance(bytes);
				cell = new PdfPCell(qrImage,true);
			    cell.setBorder(PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
			    cell.setPadding(5); 
			    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table3.addCell(cell);
			} catch (Exception e) {}	
	    }

	    table3.completeRow();
	       
		cell = new PdfPCell(table3);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPadding(0);
	    table2.addCell(cell);
	    
	    //Codigo Generado
		cell = new PdfPCell(new Phrase(auxCodJava));
		cell.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
	    cell.setPadding(5);
	    table2.addCell(cell);
	    
		cell = new PdfPCell(table2);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPaddingBottom(20);
	    cell.setPaddingLeft(5);
	    cell.setPaddingRight(5);
	    
	    table1.addCell(cell);
	}
			
	private byte[] getQRCodeImage(String text, int width, int height){
		byte[] pngData;
		try {
		    QRCodeWriter qrCodeWriter = new QRCodeWriter();
		    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		    
		    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		    pngData = pngOutputStream.toByteArray(); 			
		}catch(WriterException w) {
			pngData = null;
		}catch(IOException e) { 
			pngData = null;
		}

	    return pngData;
	}

}
