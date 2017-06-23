package jTest;

import org.junit.runner.RunWith; 
import org.junit.runners.Suite; 
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	
	//DAO
	IngredientDAOTest.class, 
	
	BookDAOTest.class,
	
	CorrectTest.class,
	
	RecipeDAOTest.class,
	
	//controller
	
	MainPageControllerTest.class,
	
	
}) 
public class CookbookTestSuite { }