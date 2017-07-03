package DAO;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.stage.FileChooser;
import model.Book;
import model.Recipe;
import view.Template;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * export the recipes in the favorite folder
 * 
 * 
 * @author CHANDIM
 *
 */
public class ExportPDF {

	private static Font bodyFont;

	private static Font titleFont;

	private static Font subTitleFont;

	public ExportPDF() throws DocumentException, IOException {

		bodyFont = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);

		BaseFont base = BaseFont.createFont("resources/Ormont_Light.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);

		titleFont = new Font(base, 40, Font.NORMAL);

		subTitleFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

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

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Cookbook pdf");

			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);

			File ioFile = fileChooser.showSaveDialog(Template.primaryStage);

			File file = new File(ioFile.getAbsolutePath());

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

			Chapter details = new Chapter(placeHolder, 0);

			details.add(Chunk.NEWLINE);
			
			details.add(rName);
			
			details.setNumberDepth(0);

			// add image
			Image image = null;

			try {

				image = Image.getInstance("src/resources/" + recipe.getThumbnail());

			} catch (FileNotFoundException ex) {

				image = Image.getInstance("src/resources/pizza_img.png");
			}

			image.setAbsolutePosition(400, 680);

			image.scaleToFit(150, 150);

			details.add(image);

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

			details.add(new Paragraph("Ingredient", subTitleFont));

			details.add(new Chunk(new DottedLineSeparator()));

			for (int i = 0; i < page.getIngredients().size(); i++) {

				details.add(new Paragraph(String.valueOf(i + 1) + ". " + page.getIngredients().get(i).getName() + " "

						+ page.getIngredients().get(i).getQuantity() + " "

						+ page.getIngredients().get(i).getUnit() + "\n"));

			}

			// steps
			details.add(Chunk.NEWLINE);

			details.add(new Paragraph("Step", subTitleFont));

			details.add(new Chunk(new DottedLineSeparator()));

			for (int i = 0; i < page.getSteps().size(); i++) {

				details.add(new Paragraph(String.valueOf(i + 1) + ". " + page.getSteps().get(i).getStepDescription()));

			}

			// add recipe information
			document.add(details);

			// add page number
			document.add(pageNumTable);

			// start a new page
			document.newPage();
		}

	}

	/**
	 * mainly for test
	 * @param arg
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void main(String[] arg) throws DocumentException, IOException {

		ExportPDF onePage = new ExportPDF();

		onePage.creatFile();

	}

}
