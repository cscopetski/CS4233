/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright ©2016-2017 Gary F. Pollice
 *******************************************************************************/
package ocr;

import java.util.HashMap;

/**
 * This class has a single method that will translate OCR digits to a string of
 * text digits that correspond to the OCR digits.
 *
 * @version Mar 13, 2019
 */
public class OCRTranslator
{

	//Lookup table for OCR digits, keys follow top+middle+bottom order
	private HashMap<String,String> ocrLookupTable= new HashMap<>();

	/**
	 * Default constructor. You may not add parameters to this. This is
	 * the only constructor for this class and is what the master tests will use.
	 */
	public OCRTranslator()
	{
		// TODO Auto-generated constructor stub
		ocrLookupTable.put(" _ | ||_|","0");
		ocrLookupTable.put(" ||","1");
		ocrLookupTable.put(" _  _||_ ","2");
		ocrLookupTable.put("_ _|_|","3");
		ocrLookupTable.put("   |_|  |","4");
		ocrLookupTable.put(" _ |_  _|","5");
        ocrLookupTable.put(" _ |_ |_|","6");
        ocrLookupTable.put("_  | |","7");
        ocrLookupTable.put(" _ |_||_|","8");
        ocrLookupTable.put(" _ |_|  |","9");
	}

	/**
	 * Translate a string of OCR digits to a corresponding string of text
	 * digits. OCR digits are represented as three rows of characters (|, _, and space).
	 * @param top the top row of the OCR input
	 * @param middle the middle row of the OCR input
	 * @param bottom the third row of the OCR input
	 * @return a String containing the digits corresponding to the OCR input
	 * @throws OCRException on error as noted in the specification
	 */
	public String translate(String top, String middle, String bottom)
	{

		if(validateInput(top, middle, bottom)){
			throw new OCRException("Error: Invalid input");
		}

		return translateOCRSequence(top,middle,bottom);
	}

	/**
	 * Loops through the OCR input and translates each digit
	 * @param top the top string of the OCR input
	 * @param middle the middle string of the OCR input
	 * @param bottom the bottom string of the OCR input
	 * @return the OCR input translated to digits
	 */
	private String translateOCRSequence(String top,String middle,String bottom){
		StringBuilder translation = new StringBuilder();

		int startIndex = 0;

		for (int i = 0; i < top.length(); i++) {
			char topChar = top.charAt(i);
			char middleChar = middle.charAt(i);
			char bottomChar = bottom.charAt(i);

			if(isSpace(topChar,middleChar,bottomChar)){

				if (i!=startIndex) {

					translation.append(lookupDigit(top,middle,bottom, startIndex, i));

				}
				startIndex = i+1;

			} else if(i == top.length()-1){

				translation.append(lookupDigit(top,middle,bottom, startIndex, i+1));

			}

		}

		return translation.toString();
	}

	/**
	 * Finds an OCR representation of a digit in hashtable if one exists
	 * @param top the top string of the OCR input
	 * @param middle the middle string of the OCR input
	 * @param bottom the bottom string of the OCR input
	 * @param startIndex the starting index of the current digit
	 * @param endIndex the ending index of the current digit
	 * @return a string representation of the OCR digit if one exists
	 * @throws OCRException if the digit is not found in the lookup table
	 */
	private String lookupDigit(String top, String middle, String bottom, int startIndex, int endIndex){

		String currentTopSubstring = top.substring(startIndex, endIndex);
		String currentMiddleSubstring = middle.substring(startIndex, endIndex);
		String currentBottomSubstring = bottom.substring(startIndex, endIndex);

		String digit = ocrLookupTable.get(currentTopSubstring+currentMiddleSubstring+currentBottomSubstring);

		if(digit == null){
			throw new OCRException("Error: Invalid digit");
		}

		return digit;
	}

	/**
	 * Checks if the input is invalid
	 * @param top top input string
	 * @param middle middle input string
	 * @param bottom bottom input string
	 * @return true if the input is invalid, false otherwise
	 */
	private boolean validateInput(String top, String middle, String bottom){
		return checkNullInput(top, middle, bottom) || checkLengthOfInput(top, middle, bottom);
	}

	/**
	 * Checks if the input contains a null string
	 * @param top top input string
	 * @param middle middle input string
	 * @param bottom bottom input string
	 * @return true if the input contains a null string, false otherwise
	 */
	private boolean checkNullInput(String top, String middle, String bottom){
		return top == null || middle == null || bottom == null;
	}

	/**
	 * Checks if the input strings are the same length
	 * @param top top input string
	 * @param middle middle input string
	 * @param bottom bottom input string
	 * @return true if the input strings are the same length, false otherwise
	 */
	private boolean checkLengthOfInput(String top, String middle, String bottom){
		return top.length() != middle.length() || top.length()!=bottom.length();
	}

	/**
	 * Checks if the current input slice is a space
	 * @param topChar top character of the input
	 * @param middleChar middle character of the input
	 * @param bottomChar bottom character of the input
	 * @return true if all characters are a ' ' char, false otherwise
	 */
	private boolean isSpace(char topChar, char middleChar, char bottomChar){
		return topChar == ' ' && middleChar == ' ' && bottomChar == ' ';
	}
}
