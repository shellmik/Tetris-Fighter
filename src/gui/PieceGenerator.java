package gui;

import java.awt.Color;

public class PieceGenerator {
	
	
	public static final Tiles[] piecesCollection = {
			/**
			 * Piece TypeI.
			 */
			new Tiles(new Color(BoardPanel.COLOR_MIN, BoardPanel.COLOR_MAX, BoardPanel.COLOR_MAX), 4, 4, 1, new boolean[][] {
				{
					false,	false,	false,	false,
					true,	true,	true,	true,
					false,	false,	false,	false,
					false,	false,	false,	false,
				},
				{
					false,	false,	true,	false,
					false,	false,	true,	false,
					false,	false,	true,	false,
					false,	false,	true,	false,
				},
				{
					false,	false,	false,	false,
					false,	false,	false,	false,
					true,	true,	true,	true,
					false,	false,	false,	false,
				},
				{
					false,	true,	false,	false,
					false,	true,	false,	false,
					false,	true,	false,	false,
					false,	true,	false,	false,
				}
			}),
			
			/**
			 * Piece TypeO.
			 */
			new Tiles(new Color(BoardPanel.COLOR_MAX, BoardPanel.COLOR_MAX, BoardPanel.COLOR_MIN), 2, 2, 2, new boolean[][] {
				{
					true,	true,
					true,	true,
				},
				{
					true,	true,
					true,	true,
				},
				{	
					true,	true,
					true,	true,
				},
				{
					true,	true,
					true,	true,
				}
			}),

			
			/**
			 * Piece TypeJ.
			 */
			new Tiles(new Color(BoardPanel.COLOR_MIN, BoardPanel.COLOR_MIN, BoardPanel.COLOR_MAX), 3, 3, 2, new boolean[][] {
				{
					true,	false,	false,
					true,	true,	true,
					false,	false,	false,
				},
				{
					false,	true,	true,
					false,	true,	false,
					false,	true,	false,
				},
				{
					false,	false,	false,
					true,	true,	true,
					false,	false,	true,
				},
				{
					false,	true,	false,
					false,	true,	false,
					true,	true,	false,
				}
			}),
			
			/**
			 * Piece TypeL.
			 */
			new Tiles(new Color(BoardPanel.COLOR_MAX, 127, BoardPanel.COLOR_MIN), 3, 3, 2, new boolean[][] {
				{
					false,	false,	true,
					true,	true,	true,
					false,	false,	false,
				},
				{
					false,	true,	false,
					false,	true,	false,
					false,	true,	true,
				},
				{
					false,	false,	false,
					true,	true,	true,
					true,	false,	false,
				},
				{
					true,	true,	false,
					false,	true,	false,
					false,	true,	false,
				}
			}),
			
			
			/**
			 * Piece TypeS.
			 */
			new Tiles(new Color(BoardPanel.COLOR_MIN, BoardPanel.COLOR_MAX, BoardPanel.COLOR_MIN), 3, 3, 2, new boolean[][] {
				{
					false,	true,	true,
					true,	true,	false,
					false,	false,	false,
				},
				{
					false,	true,	false,
					false,	true,	true,
					false,	false,	true,
				},
				{
					false,	false,	false,
					false,	true,	true,
					true,	true,	false,
				},
				{
					true,	false,	false,
					true,	true,	false,
					false,	true,	false,
				}
			}),
			
			/**
			 * Piece TypeT.
			 */
			new Tiles(new Color(128, BoardPanel.COLOR_MIN, 128), 3, 3, 2, new boolean[][] {
				{
					false,	true,	false,
					true,	true,	true,
					false,	false,	false,
				},
				{
					false,	true,	false,
					false,	true,	true,
					false,	true,	false,
				},
				{
					false,	false,	false,
					true,	true,	true,
					false,	true,	false,
				},
				{
					false,	true,	false,
					true,	true,	false,
					false,	true,	false,
				}
			}),
			
			/**
			 * Piece TypeZ.
			 */
			new Tiles(new Color(BoardPanel.COLOR_MAX, BoardPanel.COLOR_MIN, BoardPanel.COLOR_MIN), 3, 3, 2, new boolean[][] {
				{
					true,	true,	false,
					false,	true,	true,
					false,	false,	false,
				},
				{
					false,	false,	true,
					false,	true,	true,
					false,	true,	false,
				},
				{
					false,	false,	false,
					true,	true,	false,
					false,	true,	true,
				},
				{
					false,	true,	false,
					true,	true,	false,
					true,	false,	false,
				}
			})
			  
	};

	private static PieceGenerator instance = null;
	
	public static PieceGenerator getInstance(){
		
		if (instance == null)
			instance = new PieceGenerator();
		
		return instance;
	}
	
	
}
