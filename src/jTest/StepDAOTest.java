package jTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.StepDAO;
import model.Step;

/** 
 * JUnit test for StepDAO
 * 
 * @author CHANDIM
 *
 */
public class StepDAOTest {

	private StepDAO stepDAOTestTarget;

	private Step newStep;

	private ArrayList<Step> newSteps;

	@Before
	public void setUp() throws Exception {

		stepDAOTestTarget = new StepDAO();

		newSteps = new ArrayList<Step>();

		newSteps = stepDAOTestTarget.getStepListByRecipyId(2);
		
		newSteps.add(newStep);
		
		newStep = new Step();
		newStep.setDescription("only for JUnit test");
		newStep.setRecipeId(2);
		newStep.setStepOrder(4);
		

	}

	@Test
	public void testAddStep() {

		ArrayList<Step> tempAL = stepDAOTestTarget.getStepListByRecipyId(newStep.getRecipeId());

		assertFalse(tempAL.get(tempAL.size()-1).toString().equals(newStep.toString()));
		
		stepDAOTestTarget.addStep(newStep);
		
		tempAL = stepDAOTestTarget.getStepListByRecipyId(newStep.getRecipeId());

		assertNotNull(tempAL);
		
		assertEquals(newStep.toString(),tempAL.get(tempAL.size()-1).toString());

	}

	@Test
	public void testGetStepListByRecipeId() throws IOException {
		
		ArrayList<Step> tempAL = stepDAOTestTarget.getStepListByRecipyId(newStep.getRecipeId());

		assertNotNull(tempAL);
		
		for(Step step : tempAL) {
			assertEquals(newStep.getRecipeId(), step.getRecipeId());
		}
		
		
		
	}

	@Test
	public void testUpdateSteps() throws IOException {

		newStep.setDescription(newStep.getStepDescription() + " edit");

		newSteps.set(3, newStep);

		stepDAOTestTarget.updateSteps(newSteps);

		assertTrue(
				
				stepDAOTestTarget.getStepListByRecipyId(newStep.getRecipeId()).toString().equals(newSteps.toString()));

	}

	@Test
	public void testDeleteStepListByRecipeId() {

		stepDAOTestTarget.deleteStepListByRecipeId(2);

		assertFalse(isExist(2));

	}

	public boolean isExist(int recipeId) {

		ArrayList<Step> steps1 = stepDAOTestTarget.getStepListByRecipyId(recipeId);

		return steps1.size() > 0 ? true : false;
	}

	@After
	public void recover() {

		Step step1 = new Step();
		step1.setDescription("The first step of Kung Pao chicken.");
		step1.setRecipeId(2);
		step1.setStepOrder(1);

		Step step2 = new Step();
		step2.setDescription("The second step of Kung Pao chicken.");
		step2.setRecipeId(2);
		step2.setStepOrder(2);

		Step step3 = new Step();
		step3.setDescription("The third step of Kung Pao chicken.");
		step3.setRecipeId(2);
		step3.setStepOrder(3);

		newSteps = new ArrayList<Step>();
		newSteps.add(step1);
		newSteps.add(step2);
		newSteps.add(step3);

		stepDAOTestTarget.updateSteps(newSteps);

	}

}
