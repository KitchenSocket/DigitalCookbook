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
		
		newStep = new Step();
		newStep.setDescription("only for JUnit test");
		newStep.setRecipeId(1);
		newStep.setStepOrder(8);

		Step step1 = new Step();
		step1.setDescription("The first step of Hong Shao Rou.");
		step1.setRecipeId(1);
		step1.setStepOrder(1);

		Step step2 = new Step();
		step2.setDescription("The second step of Hong Shao Rou.");
		step2.setRecipeId(1);
		step2.setStepOrder(2);

		Step step3 = new Step();
		step3.setDescription("The third step of Hong Shao Rou.");
		step3.setRecipeId(1);
		step3.setStepOrder(3);

		newSteps = new ArrayList<Step>();
		newSteps.add(step1);
		newSteps.add(step2);
		newSteps.add(step3);
		newSteps.add(newStep);

	}

//	@Test
//	public void testGetSession() throws IOException {
//		// TODO how to implement?
//	}

	@Test
	public void testAddStep() {

		stepDAOTestTarget.addStep(newStep);

		assertTrue(stepDAOTestTarget.getStepListByRecipyId(newStep.getRecipeId()).size() > 0);

	}

	@Test
	public void testGetStepListByDescription() throws IOException {

		stepDAOTestTarget.addStep(newStep);
		
		assertTrue(stepDAOTestTarget.getStepListByDescription(newStep.getStepDescription()).size() > 0);

	}

	@Test
	public void testGetStepListByRecipeId() throws IOException {
		
		assertTrue(stepDAOTestTarget.getStepListByRecipyId(newStep.getRecipeId()).size() > 0);

	}

	@Test
	public void testUpdateSteps() throws IOException {
		
		newStep.setDescription(newStep.getStepDescription() + " edit");
		
		newSteps.set(3, newStep);

		stepDAOTestTarget.updateSteps(newSteps);
		
		assertTrue(stepDAOTestTarget.getStepListByDescription(newStep.getStepDescription()).get(0).getStepDescription()
		
					.equals(newStep.getStepDescription()));
		
	}

	@Test
	public void testDeleteStepListByRecipeId() {

		stepDAOTestTarget.deleteStepListByRecipeId(1);

		assertFalse(isExist(1));

	}

	public boolean isExist(int recipeId) {

		ArrayList<Step> steps1 = stepDAOTestTarget.getStepListByRecipyId(recipeId);

		return steps1.size() > 0 ? true : false;
	}

	@After
	public void recover() {

		newSteps.remove(newStep);
		
		stepDAOTestTarget.updateSteps(newSteps);

	}

}
