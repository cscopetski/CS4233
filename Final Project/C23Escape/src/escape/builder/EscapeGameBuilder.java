/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright ©2016-2023 Gary F. Pollice
 *******************************************************************************/

package escape.builder;

import econfig.*;
import escape.*;
import escape.board.*;
import escape.board.Board;
import escape.coordinate.HexagonalCoordinate;
import escape.coordinate.SquareCoordinate;
import escape.piece.EscapePieceImpl;
import escape.piece.PathChecker;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;
import escape.required.Rule;
import org.antlr.v4.runtime.*;

import javax.xml.bind.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class builds an instance of an EscapeGameManager from a configuration
 * file (.egc). This uses the EscapeConfigurator for XML to turn the .egc file
 * into a valid XML string that is then unmarshalled into an EscapeGameInitializer
 * file.
 *
 * The Escape Configuration Tool JAR file is required for this class to compile and
 * run correctly. The file also requires the ANTLR 4 JAR file. Both of these JAR files are
 * in the lib directory of this IntelliJ project.
 *
 * NOTE: The Escape Configuration Tool was built with Java 17.
 * 
 * MODIFIABLE: YES
 * MOVEABLE: NO
 * REQUIRED: YES
 * 
 * You must change this class to be able to get the data from the configurtion
 * file and implement the makeGameManager() method. You may not change the signature
 * of that method or the constructor for this class. You can change the file any 
 * other way that you need to.
 * 
 * You don't have to use the EscapeGameInitializer object if
 * you have a way that better suits your design and capabilities. Don't go down
 * a rathole, however, in order to use something different. This implementation
 * works and will not take much time to modify the EscapeGameInitializer to create
 * your game instance. Just creating the game instance should take as little time
 * as possible to implement.
 */
public class EscapeGameBuilder
{
    private final EscapeGameInitializer gameInitializer;

    /**
     * The constructor takes a file that points to the Escape game
     * configuration file. It should get the necessary information 
     * to be ready to create the game manager specified by the configuration
     * file and other configuration files that it links to.
     * @param fileName the file for the Escape game configuration file (.egc).
     * @throws Exception on any errors
     */
    public EscapeGameBuilder(String fileName) throws Exception
    {
    	String xmlConfiguration = getXmlConfiguration(fileName);
    	// Uncomment the next instruction if you want to see the XML
    	// System.err.println(xmlConfiguration);
        gameInitializer = unmarshalXml(xmlConfiguration);
    }

	/**
	 * Take the .egc file contents and turn it into XML.
	 * @param fileName the input configuration (.egc) file
	 * @return the XML data needed to 
	 * @throws IOException
	 */
	private String getXmlConfiguration(String fileName) throws IOException
	{
		EscapeConfigurator configurator = new EscapeConfigurator();
    	return configurator.makeConfiguration(CharStreams.fromFileName(fileName));
	}

	/**
	 * Unmarshal the XML into an EscapeGameInitializer object.
	 * @param xmlConfiguration
	 * @throws JAXBException
	 */
	private EscapeGameInitializer unmarshalXml(String xmlConfiguration) throws JAXBException
	{
		JAXBContext contextObj = JAXBContext.newInstance(EscapeGameInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        return (EscapeGameInitializer)mub.unmarshal(
            	new StreamSource(new StringReader(xmlConfiguration)));
	}
	
	/**
	 * Getter for the gameInitializer. Can be used to examine it after the builder
	 * creates it.
	 * @return the gameInitializer
	 */
	public EscapeGameInitializer getGameInitializer()
	{
		return gameInitializer;
	}
    
    /***********************************************************************
     * Once the EscapeGameIniializer is constructed, this method creates the
     * EscapeGameManager instance. You use the gameInitializer object to get
	 * all of the information you need to create your game.
     * @return the game instance
     ***********************************************************************/
    public EscapeGameManager makeGameManager()
    {
		EscapeGameManagerImpl gameManager = null;
		Board board = null;
		Coordinate.CoordinateType coordinateType = gameInitializer.getCoordinateType();

		switch (coordinateType){
			case SQUARE -> {
				gameManager = new EscapeGameManagerImpl<SquareCoordinate>();
				board = new Board<SquareCoordinate>(gameInitializer.getxMax(), gameInitializer.getyMax());
			}
			case HEX -> {
				gameManager = new EscapeGameManagerImpl<HexagonalCoordinate>();
				board = new Board<HexagonalCoordinate>(gameInitializer.getxMax(), gameInitializer.getyMax());
			}
		}

		gameManager.setCoordinateType(gameInitializer.getCoordinateType());
		gameManager.setxMax(gameInitializer.getxMax());
		gameManager.setyMax(gameInitializer.getyMax());
		gameManager.setPlayers(gameInitializer.getPlayers());

		Map<Rule.RuleID, Integer> ruleMap = new HashMap<>();

		if(gameInitializer.getRules() !=null){
			for (RuleDescriptor rule: gameInitializer.getRules()) {
				ruleMap.put(rule.ruleId, rule.ruleValue);
			}
		}

		gameManager.setRules(ruleMap);

		if(gameInitializer.getLocationInitializers() == null){
			return gameManager;
		}

		Map<EscapePiece.PieceName, PieceTypeDescriptor> pieceMap = new HashMap<>();

		if(gameInitializer.getPieceTypes() != null){
			for (PieceTypeDescriptor piece: gameInitializer.getPieceTypes()) {
				pieceMap.put(piece.getPieceName(), piece);
			}
		}



		for(LocationInitializer init : gameInitializer.getLocationInitializers()){

			PieceTypeDescriptor pieceDescriptor = pieceMap.get(init.pieceName);

			EscapePieceImpl piece = null;

			if(pieceDescriptor !=null){
				 piece = new EscapePieceImpl(init.pieceName, init.player, pieceDescriptor.getMovementPattern(), pieceDescriptor.getAttributes());
			}

			switch (coordinateType){
				case SQUARE -> {
					board.addLocation(new SquareCoordinate(init.x, init.y), new LocationImpl(piece, init.locationType != null ? init.locationType : LocationType.CLEAR));
				}
				case HEX -> {
					board.addLocation(new HexagonalCoordinate(init.x, init.y), new LocationImpl(piece, init.locationType != null ? init.locationType : LocationType.CLEAR));
				}
			}


		}

		gameManager.setBoard(board);
		PathChecker.setBoard(board);

    	return gameManager;
    }
}
