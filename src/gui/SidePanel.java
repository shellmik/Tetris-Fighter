package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The {@code SidePanel} class is responsible for displaying various information
 * on the game such as the next piece, the score and current level, and controls.
 * @author Brendan Jones
 *
 */
public class SidePanel extends JPanel implements Panel{
	
	private float gameSpeed=5.0f;//1-5
	private float gameAcc=0.14f;//0.04/6/8/0.1/12
	private int gameTileCnt=2;//2,4,5,7
	private Level sideLevel=Level.getInstance();
	
	
	public Level getLevel() {
	return this.sideLevel;
	}
	
	
	
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2181495598854992747L;

	/**
	 * The dimensions of each tile on the next piece preview.
	 */
	private static final int TILE_SIZE = BoardPanel.TILE_SIZE >> 1;
	
	/**
	 * The width of the shading on each tile on the next piece preview.
	 */
	private static final int SHADE_WIDTH = BoardPanel.SHADE_WIDTH >> 1;
	
	/**
	 * The number of rows and columns in the preview window. Set to
	 * 5 because we can show any piece with some sort of padding.
	 */
	private static final int TILE_COUNT = 5;
	
	/**
	 * The center x of the next piece preview box.
	 */
	private static final int SQUARE_CENTER_X = 130;
	
	/**
	 * The center y of the next piece preview box.
	 */
	private static final int SQUARE_CENTER_Y = 65;
	
	/**
	 * The size of the next piece preview box.
	 */
	private static final int SQUARE_SIZE = (TILE_SIZE * TILE_COUNT >> 1);
	
	/**
	 * The number of pixels used on a small insets (generally used for categories).
	 */
	private static final int SMALL_INSET = 20;
	
	/**
	 * The number of pixels used on a large insets.
	 */
	private static final int LARGE_INSET = 40;
	
	/**
	 * The y coordinate of the stats category.
	 */
	private static final int STATS_INSET = 175;
	
	/**
	 * The y coordinate of the controls category.
	 */
	private static final int CONTROLS_INSET = 300;
	
	/**
	 * The number of pixels to offset between each string.
	 */
	private static final int TEXT_STRIDE = 25;
	
	/**
	 * The small font.
	 */
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
	
	/**
	 * The large font.
	 */
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
	
	/**
	 * The color to draw the text and preview box in.
	 */
	private static final Color DRAW_COLOR = new Color(128, 192, 128);
	
	/**
	 * The Tetris instance.
	 */
	private GameController tetris;
	
	private drawer draw = new drawer(TILE_SIZE, SHADE_WIDTH);
	/**
	 * Creates a new SidePanel and sets it's display properties.
	 * @param tetris The Tetris instance to use.
	 */
	private JLabel label;
	private JTextField textField;

	public SidePanel(GameController tetris) {
		this.tetris = tetris;

		setPreferredSize(new Dimension(200, BoardPanel.PANEL_HEIGHT));
		setBackground(Color.BLACK);

		label = new JLabel("Name: Please Input User Name");
		label.setFont(new Font("acefont-family", Font.BOLD, 10));
		label.setForeground(Color.red);
		label.setBounds(18, 15, 30, 4);

		Dimension d = label.getPreferredSize();
		label.setPreferredSize(new Dimension(d.width + 6, d.height));

		textField = new JTextField(20);
		textField.setFont(new Font("acefont-family", Font.BOLD, 10));
		textField.setBounds(18, 20, 25, 4);

		JButton b1 = new JButton("submit user name");
		add(b1);

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText("Name: " + textField.getText());
				tetris.requestFocus();
			}
		});

		add(label);
		add(textField);
		
		JLabel chooseType = new JLabel("Type count：");
		chooseType.setForeground(Color.red);
        add(chooseType);
        
        
        int[] listType = new int[]{2, 4, 5, 7};
        JComboBox<Integer> cmbType=new JComboBox<Integer>();
        for(int i=0;i<listType.length;i++) {
        	cmbType.addItem(listType[i]);
        }
        cmbType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	
                    gameTileCnt=(int) cmbType.getSelectedItem();
                    System.out.println("select: "+gameTileCnt);
                    sideLevel.setTileCnt(gameTileCnt);
                    
                }
            }
        });
        cmbType.setSelectedIndex(1);
        add(cmbType);
        
		JLabel chooseSpeed = new JLabel("Speed：");
		chooseSpeed.setForeground(Color.red);
        add(chooseSpeed);
        
        
        float[] listSpeed = new float[]{1.0f,2.0f,3.0f,4.0f,5.0f};
        JComboBox cmbSpeed=new JComboBox();
        for(int i=0;i<listSpeed.length;i++) {
        	cmbSpeed.addItem(listSpeed[i]);
        }
        cmbSpeed.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    gameSpeed=(float) cmbSpeed.getSelectedItem();
                    System.out.println("select: "+gameSpeed);
                    sideLevel.setSpeed(gameSpeed);
                }
            }
        });
        cmbSpeed.setSelectedIndex(1);
        add(cmbSpeed);
        
		JLabel chooseAcc = new JLabel("Acceleration：");
		chooseAcc.setForeground(Color.red);
        add(chooseAcc);
        
        
        float[] listAcc = new float[]{0.04f,0.06f,0.08f,0.1f,0.12f};
        JComboBox cmbAcc=new JComboBox();
        for(int i=0;i<listAcc.length;i++) {
        	cmbAcc.addItem(listAcc[i]);
        }
        cmbAcc.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    gameAcc=(float) cmbAcc.getSelectedItem();
                    System.out.println("select: "+gameAcc);
                    sideLevel.setAccelaration(gameAcc);
                    
                }
            }
        });
        cmbAcc.setSelectedIndex(1);
        add(cmbAcc);
        
        
        
        
        	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Set the color for drawing.
		g.setColor(DRAW_COLOR);
		
		/*
		 * This variable stores the current y coordinate of the string.
		 * This way we can re-order, add, or remove new strings if necessary
		 * without needing to change the other strings.
		 */
		int offset;
		
		/*
		 * Draw the "Stats" category.
		 */
		g.setFont(LARGE_FONT);
		g.drawString("Stats", SMALL_INSET, offset = STATS_INSET);
		g.setFont(SMALL_FONT);
		//g.drawString("Level: " + tetris.getLevel(), LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("Score: " + tetris.getScore(), LARGE_INSET, offset += TEXT_STRIDE);
		
		/*
		 * Draw the "Controls" category.
		 */
		g.setFont(LARGE_FONT);
		g.drawString("Controls", SMALL_INSET, offset = CONTROLS_INSET);
		g.setFont(SMALL_FONT);
		g.drawString("A - Move Left", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("D - Move Right", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("Q - Rotate Anticlockwise", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("E - Rotate Clockwise", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("S - Drop", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("P - Pause Game", LARGE_INSET, offset += TEXT_STRIDE);
		
		/*
		 * Draw the next piece preview box.
		 */
		g.setFont(LARGE_FONT);
		g.drawString("Next Piece:", SMALL_INSET, 70);
		g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);
		
		/*
		 * Draw a preview of the next piece that will be spawned. The code is pretty much
		 * identical to the drawing code on the board, just smaller and centered, rather
		 * than constrained to a grid.
		 */
		Tiles type = tetris.getNextPieceType();
		if(!tetris.isGameOver() && type != null) {
			/*
			 * Get the size properties of the current piece.
			 */
			int cols = type.getCols();
			int rows = type.getRows();
			int dimension = type.getDimension();
		
			/*
			 * Calculate the top left corner (origin) of the piece.
			 */
			int startX = (SQUARE_CENTER_X - (cols * TILE_SIZE / 2));
			int startY = (SQUARE_CENTER_Y - (rows * TILE_SIZE / 2));
		
			/*
			 * Get the insets for the preview. The default
			 * rotation is used for the preview, so we just use 0.
			 */
			int top = type.getTopInset(0);
			int left = type.getLeftInset(0);
		
			/*
			 * Loop through the piece and draw it's tiles onto the preview.
			 */
			for(int row = 0; row < dimension; row++) {
				for(int col = 0; col < dimension; col++) {
					if(type.isTile(col, row, 0)) {
						draw.drawTile(type, startX + ((col - left) * TILE_SIZE), startY + ((row - top) * TILE_SIZE), g);
					}
				}
			}
		}
	}
}
