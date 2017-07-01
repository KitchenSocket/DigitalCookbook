package jTest;

import org.junit.runner.RunWith; 
import org.junit.runners.Suite; 
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	
	//DAO
	RecipeDAOTest.class,
	
	IngredientDAOTest.class, 
	
	StepDAOTest.class,
	
}) 
public class CookbookTestSuite { }