package DAO;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.application.HostServices;
import model.Book;
import model.Ingredient;
import model.Recipe;
import model.Step;

public class BookDAO {

	private static String FILE;

	private static Font bodyFont;

	private static Font titleFont;

	private static Font subTitleFont;

	// private static Font catFont;
	// private static Font redFont;
	// private static Font subFont;
	// private static Font smallBold;
	// private static Font courier;

	public BookDAO() throws DocumentException, IOException {

		FILE = "myCookBook.pdf";

		bodyFont = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);

		BaseFont base = BaseFont.createFont("resources/Ormont_Light.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);

		titleFont = new Font(base, 40, Font.NORMAL);
		
		subTitleFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

		// TODO set font
		// catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		// redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
		// BaseColor.RED);
		// subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
		// smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		// courier = new Font(Font.FontFamily.COURIER, 18, Font.ITALIC |
		// Font.UNDERLINE);
	}
	
	/**
	 * create a file and add content
	 * 
	 * @author CHANDIM
	 */
	public void showFile() {
		try {
			
			 File pdfFile = new File("C:\\Users\\asus\\git\\DigitalCookbook\\myCookBook.pdf");


			Desktop.getDesktop().open(pdfFile);


		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * create a file and add content
	 * 
	 * @author CHANDIM
	 */
	public void creatFile() {
		try {

			Document document = new Document();

			File file = new File(FILE);

			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			addMetaData(document);

			addTitlePage(document);

			addContent(document);

			document.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * add metadata to the PDF which can be viewed in Adobe
	 * 
	 * @author CHANDIM
	 * @param document
	 */
	private static void addMetaData(Document document) {

		document.addTitle("My cook book");

	}

	/**
	 * add title page with title "my cook book"
	 * 
	 * @author CHANDIM
	 * @param document
	 * @throws DocumentException
	 */
	private static void addTitlePage(Document document) throws DocumentException {

		Paragraph title = new Paragraph("My cook book", titleFont);

		PdfPCell cell = new PdfPCell();

		// add title to a cell
		cell.addElement(title);

		title.setAlignment(Element.ALIGN_CENTER);

		cell.setNoWrap(false);

		cell.setPaddingTop(300);

		// make the border invisible
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setVerticalAlignment(Element.ALIGN_CENTER);

		PdfPTable table = new PdfPTable(1);

		// add cell to a table
		table.addCell(cell);

		// add the table to the page
		document.add(table);

		// start new page
		document.newPage();
	}

	/**
	 * add recipes to each page
	 * 
	 * @author CHANDIM
	 * @param document
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void addContent(Document document) throws DocumentException, IOException {

		RecipeDAO recipeDAO = new RecipeDAO();

		// page number starts from 1
		int pageNum = 1;

		// get all recipes from favorite folder
		for (Recipe recipe : recipeDAO.getRecipeListByNameInFavorite("%")) {

			// add page number
			Paragraph num = new Paragraph(String.valueOf(pageNum));

			num.setAlignment(Element.ALIGN_CENTER);

			PdfPCell pageNumCell = new PdfPCell();

			// add page number to cell
			pageNumCell.addElement(num);

			pageNumCell.setVerticalAlignment(Element.ALIGN_BOTTOM);

			pageNumCell.setBorder(Rectangle.NO_BORDER);

			PdfPTable pageNumTable = new PdfPTable(1);

			// add cell to table
			pageNumTable.addCell(pageNumCell);

			pageNumTable.setExtendLastRow(true);

			// increase the page number
			pageNum++;

			// get information of a recipe
			Book page = new Book(recipe);

			// set the title of this page(recipe name)
			Paragraph placeHolder = new Paragraph(Chunk.NEWLINE);
			Paragraph rName = new Paragraph(page.getRecipeName(), bodyFont);

			// rName.setSpacingBefore(1000);

			// rName.setAlignment(Element.ALIGN_CENTER);

			Chapter details = new Chapter(placeHolder, 0);

			details.add(Chunk.NEWLINE);
			details.add(rName);
			details.setNumberDepth(0);

			// add image TODO 
			// ------------------------------------------
			// Image image = Image.getInstance(page.getRecipeThumbnail());
			Image image = Image.getInstance("src/resources/" + recipe.getThumbnail());
			
			image.setAbsolutePosition(400, 680);
			
			image.scaleToFit(150, 150);
			
			details.add(image);
			// -------------------------------------------

			// preparation time and cooking time
			details.add(Chunk.NEWLINE);

			details.add(new Paragraph(String.valueOf("Preparation time: " + page.getRecipePreTime())));

			details.add(new Paragraph(String.valueOf("Cooking time: " + page.getRecipeCookTime())));

			details.add(new Chunk(new LineSeparator()));

			// description
			details.add(Chunk.NEWLINE);

			details.add(new Paragraph(page.getRecipeDescrip()));

			details.add(Chunk.NEWLINE);

			// ingredients
			details.add(Chunk.NEWLINE);

			details.add(new Paragraph("Ingredient",subTitleFont));

			details.add(new Chunk(new DottedLineSeparator()));

			for (int i = 0; i < page.getIngredients().size(); i++) {

				details.add(new Paragraph(String.valueOf(i+1) + ". " + page.getIngredients().get(i).getName() + " "

						+ page.getIngredients().get(i).getQuantity() + " "

						+ page.getIngredients().get(i).getUnit() + "\n"));

			}

			// steps
			details.add(Chunk.NEWLINE);

			details.add(new Paragraph("Step",subTitleFont));

			details.add(new Chunk(new DottedLineSeparator()));

			for (int i = 0; i < page.getSteps().size(); i++) {

				details.add(new Paragraph(String.valueOf(i+1) + ". " + page.getSteps().get(i).getStepDescription()));

			}

			// add recipe information
			document.add(details);

			// add page number
			document.add(pageNumTable);

			// start a new page
			document.newPage();
		}

	}

	public static void main(String[] arg) throws DocumentException, IOException {

		BookDAO onePage = new BookDAO();

		onePage.creatFile();

	}

}
