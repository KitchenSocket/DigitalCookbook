package model;

import java.sql.*;
import javafx.application.Application;
import view.Template;

/**
 * A class for the program entry point and some test recipes.
 *
 * @author Shi Wenbin, Gang Shao
 * 
 */
public class CookBook {
	
    /**
     * Program entry point.
     *
     * @param args  command line arguments; not used.
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
    	
      Application.launch(Template.class, args);

    }

}