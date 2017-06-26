package jTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import DAO.Correct;

public class CorrectTest {


//	@Before
//	public void setUp() throws Exception {
//	}

	@Test
	public void testCorrect() throws IOException {
		ArrayList<String> testList = new ArrayList<String>();

		ArrayList<String> expectedList =  new ArrayList<String>();
		
		//testList.add(null);
		testList.add("%");
		testList.add("yu xiang");
		testList.add("sao");

		expectedList.add("%");
		expectedList.add("yuxiang");
		expectedList.add("shao");
		
		for(int i = 0; i<testList.size();i++) {
			assertEquals(expectedList.get(i), new Correct("words.txt").correct(testList.get(i)));
		}

	}

	@Test
	public void testCorrect1() throws IOException {
		String word = "prok";
		String expected = "pork";
		assertEquals(expected,new Correct("words.txt").correct(word));
	}

	@Test
	public void testCorrect2() throws IOException {
		String word = "poork";
		String expected = "pork";
		assertEquals(expected, new Correct("words.txt").correct(word));
	}
	
	@Test
	public void testCorrect3() throws IOException {
		String word = "yellow shao rou";
		String expected = "hong shao rou";
		assertEquals(expected, new Correct("words.txt").correct(word));
	}
	
}
