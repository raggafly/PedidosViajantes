/*
 * $Id: Logo.java 3838 2009-04-07 18:34:15Z mstorer $
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.combo.pedidosviajantes.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.combo.pedidosviajantes.R;
import com.combo.pedidosviajantes.vo.ArticuloVO;
import com.combo.pedidosviajantes.vo.ClientVO;
import com.combo.pedidosviajantes.vo.CompanyVO;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Draws the iText logo.
 */
public class CreatePDF {
	private static String ImageHeader = "";
	/**
	 * Draws the iText logo.
	 * 
	 * @param args
	 *            no arguments needed
	 */
	private static String FILE = "";

	public Vector generatePDF(HashMap<Integer, ArticuloVO> mapArtVO,
			CompanyVO company, ClientVO cliente, String nameBussinessTraveler,Image img) {
		FILE ="";
		FILE =Environment.getExternalStorageDirectory() + "/";
		Date actuallyDate = new Date();
		Long dateLong = actuallyDate.getTime();
		Vector<String> v = new Vector<String>();
		String srcFilePath = FILE + nameBussinessTraveler.toLowerCase() + "/";
		String desFileName = cliente.getName().trim() + "_"
				+ dateLong.toString() + ".pdf";
		String desDirectory = "/pedidosViajantes/HistoricoPDF/"+nameBussinessTraveler.toLowerCase();

		FILE = FILE +"PedidosViajantes/"+ nameBussinessTraveler.toLowerCase() +"/" + "_"
				+ dateLong.toString() + ".pdf";

		v.add(FILE);
		v.add(desFileName);
		v.add(desDirectory);

		// step 1: creation of a document-object
		Document document = new Document();

		try {

			// step 2: creation of the writer
			// PdfWriter writer = PdfWriter.getInstance(document, new
			// FileOutputStream(Environment.getExternalStorageDirectory() +
			// java.io.File.separator + "droidtext" + java.io.File.separator +
			// "logo.pdf"));
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(FILE));

			// step 3: we open the document
			document.open();
			
//			ImageView view = (ImageView) findViewById(R.id.);
//			img = Image.getInstance(bitmapHeader);

			
			if (img.getScaledWidth() > 200 || img.getScaledHeight() > 200) {
				img.scaleToFit(200, 200);
			}
			img.setAlignment(Element.ALIGN_LEFT);
			document.add(img);
			// step 4:
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent();

			cb.setLineWidth(1f);
			cb.moveTo(555, 790);
			cb.lineTo(555, 735);
			cb.moveTo(555, 735);
			cb.lineTo(378, 735);
			cb.moveTo(378, 735);
			cb.lineTo(378, 790);
			cb.moveTo(378, 790);
			cb.lineTo(555, 790);
			cb.stroke();
			cb.setLineWidth(1f);
			cb.moveTo(45, 710);
			cb.lineTo(45, 635);
			cb.moveTo(45, 635);
			cb.lineTo(280, 635);
			cb.moveTo(280, 635);
			cb.lineTo(280, 710);
			cb.moveTo(280, 710);
			cb.lineTo(45, 710);
			cb.stroke();
			// we tell the ContentByte we're ready to draw text
			cb.beginText();

			bf = BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.CP1252,
					BaseFont.NOT_EMBEDDED);
			cb.setFontAndSize(bf, 10);
			String text = "Sample text for alignment";
			// we show some text starting on some absolute position with a given
			// alignment
			Date fechaActual = new Date();
			SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			String date = "";
			date = dt.format(fechaActual).toString();
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Fecha: " + date,
					550, 800, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, company.getName(),
					550, 780, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Direccion: "
					+ company.getAddress(), 550, 760, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
					"Tlf: " + company.getPhone(), 550, 740, 0);

			// DATOS CLIENTE
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, cliente.getName(),
					50, 700, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, cliente.getAddress(),
					50, 680, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, cliente.getPhone(),
					50, 660, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, cliente.getTown(),
					50, 640, 0);
			// cb.showText( cliente.getName());
			// cb.showText(cliente.getAddress());
			// cb.showText(cliente.getPhone());
			// cb.showText(cliente.getTown());
			// we draw some text on a certain position

			cb.endText();

			cb.sanityCheck();

			createTable(mapArtVO, document, writer);

			Rectangle rect = writer.getBoxSize("art");
			switch (writer.getPageNumber() % 2) {
			case 0:
				ColumnText
						.showTextAligned(writer.getDirectContent(),
								Element.ALIGN_RIGHT, new Phrase("even header"),
								rect.getBorderWidthRight(),
								rect.getBorderWidthTop(), 0);
				break;
			case 1:
				ColumnText
						.showTextAligned(
								writer.getDirectContent(),
								Element.ALIGN_CENTER,
								new Phrase(String.format("%d",
										writer.getPageNumber())), 300f, 62f, 0);
				break;
			}
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		// step 5: we close the document
		document.close();
		return v;
	}

	private static void createTable(HashMap<Integer, ArticuloVO> mapArtVO,
			Document document, PdfWriter writer) throws BadElementException {
		Paragraph preface = new Paragraph();
		float width = PageSize.A4.getWidth();
		float height = PageSize.A4.getHeight();
		BaseFont bf;
		try {
			bf = BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.CP1252,
					BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Double totalFactura = 0D;
		// addEmptyLine(preface, 20);
		PdfPTable table = new PdfPTable(5);
		table.setHorizontalAlignment(100);
		// float width = PageSize.A4.getWidth();
		float customWidth = width / 5;
		Font subFont = new Font(Font.COURIER, 9, Font.BOLD);
		Font fontHeaderTable = new Font(Font.COURIER, 11, Font.BOLD);
		float[] columnDefinitionSize = { customWidth * 0.4F,
				customWidth * 0.1F, customWidth * 0.2F, customWidth * 0.1F,
				customWidth * 0.2F };
		table = new PdfPTable(columnDefinitionSize);
		table.setTotalWidth(width - 100);

		Phrase frase = new Phrase("DESCRIPCION", fontHeaderTable);
		PdfPCell c1 = new PdfPCell(frase);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		frase = new Phrase("CANT", fontHeaderTable);
		c1 = new PdfPCell(frase);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		frase = new Phrase("PRECIO", fontHeaderTable);
		c1 = new PdfPCell(frase);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		frase = new Phrase("DTO", fontHeaderTable);
		c1 = new PdfPCell(frase);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		frase = new Phrase("TOTAL", fontHeaderTable);
		c1 = new PdfPCell(frase);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		Iterator it = mapArtVO.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			System.out.println(e.getKey() + " " + e.getValue());
			ArticuloVO artVO = new ArticuloVO();
			artVO = (ArticuloVO) e.getValue();
			c1 = new PdfPCell(new Phrase(artVO.getDescripcion().toString(),
					subFont));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);

			// cantidad alineacion centro
			c1 = new PdfPCell(new Phrase(artVO.getCantidad().toString(),
					subFont));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			// precio alineacion derecha
			c1 = new PdfPCell(new Phrase(artVO.getPrecio().toString(), subFont));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(c1);

			// dto alineacion centro
			c1 = new PdfPCell(new Phrase(artVO.getDto().toString(), subFont));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			// total alineacion derecha
			String total = calcularTotal(artVO.getCantidad(),
					artVO.getPrecio(), artVO.getDto());
			totalFactura += Double.parseDouble(total);
			c1 = new PdfPCell(new Phrase(total + " €", subFont));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(c1);

		}

		for (int i = 0; i < 4; i++) {
			c1 = new PdfPCell();
			c1.setBorder(0);
			table.addCell(c1);
		}

		c1 = new PdfPCell(new Phrase(totalFactura + " €", subFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(c1);
		int pos = 550;
		table.writeSelectedRows(0, -1, 50, pos, writer.getDirectContent());

		String observaciones = "hola 223435345 ljojoh oh jñokljdfaslffkñladfjñalsdfj dlñfañjfkl en la casa de pepito ia ia ooooo hasñlfj";
		table = new PdfPTable(1);
		table.setHorizontalAlignment(100);

		table = new PdfPTable(1);
		table.setTotalWidth(width - 100);

		frase = new Phrase("Observaciones: " + observaciones, subFont);
		c1 = new PdfPCell(frase);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		pos = 100;
		table.writeSelectedRows(0, -1, 50, pos, writer.getDirectContent());

	}

	private static String calcularTotal(String cantidad, Double precio,
			Double dto) {
		String total = "0";
		Double number;
		Double totalProd = 0D;
		Double totalD = 0D;
		if (Pattern.matches("[a-zA-Z]+", cantidad) == false
				&& cantidad.length() > 0) {
			if (cantidad.matches("[0-9]+")) {
				number = Double.parseDouble(cantidad);
				totalProd = (precio * number);
				totalD = totalProd * (dto / 100);
				total = String.valueOf(totalProd - totalD);
			}
		}
		return total;
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}