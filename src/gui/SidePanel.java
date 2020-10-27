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

public class SidePanel extends JPanel implements Panel{
	
	private float gameSpeed=5.0f;//1-5
	private float gameAcc=0.14f;//0.04/6/8/0.1/12
	private int gameTileCnt=2;//2,4,5,7
	//private Level sideLevel=Level.getInstance();
	private Level sideLevel;
	
	private static final long serialVersionUID = 2181495598854992747L;

	private static final int TILE_SIZE = BoardPanel.TILE_SIZE >> 1;//dimensions of each tile on the next piece preview
	private static final int SHADE_WIDTH = BoardPanel.SHADE_WIDTH >> 1;
	
	/**
	 * The number of rows and columns in the preview window. Set to
	 * 5 because we can show any piece with some sort of padding.
	 */
	private static final int TILE_COUNT = 5;
	
	private static final int SQUARE_CENTER_X = 170;//center x y of the next piece preview box.
	private static final int SQUARE_CENTER_Y = 150;
	
	private static final int SQUARE_SIZE = (TILE_SIZE * TILE_COUNT >> 1);//size of the next piece preview box
	
	private static final int SMALL_INSET = 20;//pixel number used on a small insets (generally used for categories
	private static final int LARGE_INSET = 40;//number of pixels used on a large insets
	
	private static final int STATS_INSET = 230;//y coordinate of the stats category
	private static final int CONTROLS_INSET = 300;//y coordinate of the controls category
	private static final int TEXT_STRIDE = 25;//number of pixels to offset between each string
	
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
	private static final Color DRAW_COLOR = Color.BLACK;// for text and preview box 
	private drawer draw = new drawer(TILE_SIZE, SHADE_WIDTH);
	
	private String userName;
	private GameController tetris;
	
	public JLabel labelUser;
	
	JComboBox cmbLevel;
	JComboBox<Integer> cmbType;
	JComboBox cmbSpeed;
	JComboBox cmbAcc;
	JTextField textField ;
	
	public boolean isSubmit=false;
	public String getUserName() {
		return userName;
	}

	public SidePanel(GameController tetris) {
		this.tetris = tetris;
		setLayout(null);
		setPreferredSize(new Dimension(340, BoardPanel.PANEL_HEIGHT));
		setBackground(new Color(255,182,193));
		
		//input user name
		labelUser = new JLabel("Name: Please Input A User Name  ");
		labelUser.setFont(LARGE_FONT);
		labelUser.setForeground(Color.BLACK);
		labelUser.setBounds(SMALL_INSET, 20, 300, 30);//x, y, width, height

		Dimension d = labelUser.getPreferredSize();
		labelUser.setPreferredSize(new Dimension(d.width + 6, d.height));

		textField = new JTextField(20);
		textField.setFont(new Font("acefont-family", Font.BOLD, 10));
		textField.setBounds(SMALL_INSET, 60, 200, 30);//这个大小为什么改不了



		add(labelUser);
		add(textField);
		
		//all 4 labels
		JLabel chooseLevel = new JLabel("Level：");
		chooseLevel.setFont(LARGE_FONT);
		chooseLevel.setForeground(Color.black);
        add(chooseLevel);
		
		JLabel chooseType = new JLabel("Type count：");
		chooseType.setForeground(Color.black);
        add(chooseType);
        
		JLabel chooseSpeed = new JLabel("Speed：");
		chooseSpeed.setForeground(Color.black);
        add(chooseSpeed);
        
		JLabel chooseAcc = new JLabel("Acceleration：");
		chooseAcc.setForeground(Color.black);
        add(chooseAcc);
        //labels end
   
        //1
        int[] listType = new int[]{2, 4, 5, 7};
        cmbType=new JComboBox<Integer>();
        for(int i=0;i<listType.length;i++) {
        	cmbType.addItem(listType[i]);
        }
        add(cmbType);
        //2
        float[] listSpeed = new float[]{1.0f,2.0f,3.0f,4.0f,5.0f};
        cmbSpeed=new JComboBox();
        for(int i=0;i<listSpeed.length;i++) {
        	cmbSpeed.addItem(listSpeed[i]);
        }
        add(cmbSpeed);
        //3
        float[] listAcc = new float[]{0.04f,0.06f,0.08f,0.1f,0.12f};
        cmbAcc=new JComboBox();
        for(int i=0;i<listAcc.length;i++) {
        	cmbAcc.addItem(listAcc[i]);
        }
        add(cmbAcc);
        
        String[] listLevel = new String[]{"Low","Mid","High"};
        cmbLevel=new JComboBox();
        for(int i=0;i<listLevel.length;i++) {
        	cmbLevel.addItem(listLevel[i]);
        }
        cmbLevel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	String levelStr=(String) cmbLevel.getSelectedItem();
                	if(levelStr=="Low")
                		sideLevel=LevelLow.getInstance();
                	else if(levelStr=="Mid")
                		sideLevel=LevelMid.getInstance();
                	else if(levelStr=="High")
                		sideLevel=LevelHigh.getInstance();
                	
                	tetris.setLevel(sideLevel);
                	
                	cmbType.setSelectedItem(sideLevel.getTileCnt());
                	cmbSpeed.setSelectedItem(sideLevel.getSpeed());
                	cmbAcc.setSelectedItem(sideLevel.getAccelaration());
                    
                }
            }
        });
        cmbLevel.setSelectedIndex(1);
        add(cmbLevel);
 
		
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
           
        //layout for level choices
        chooseLevel.setBounds(SMALL_INSET, 250, 100, 25);//x, y, width, height
        cmbLevel.setBounds(130, 250, 80, 30);//x, y, width, height
        
        JLabel note = new JLabel("Details for each level are shown below.");
		note.setForeground(Color.black);
        add(note);
        note.setBounds(40, 280, 300, 25);
        JLabel note1 = new JLabel("You can customize the values if you want to.");
		note1.setForeground(Color.black);
        add(note1);
        note1.setBounds(40, 300, 300, 25);
        
        
        chooseType.setBounds(40, 330, 100, 25);//x, y, width, height
        cmbType.setBounds(130, 330, 90, 30);//x, y, width, height
        chooseSpeed.setBounds(40, 365, 100, 25);//x, y, width, height
        cmbSpeed.setBounds(130, 365, 90, 30);//x, y, width, height
        chooseAcc.setBounds(40, 400, 100, 25);//x, y, width, height
        cmbAcc.setBounds(130, 400, 90, 30);//x, y, width, height
        
        
        //submit button
		JButton b1 = new JButton("submit");
		b1.setBounds(50, 440, 80, 40);//x, y, width, height
		add(b1);

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSubmit=true;
				revalidate();
				repaint();
				System.out.println("b1 clicked");
				labelUser.setText("Name: " + textField.getText());
				userName = textField.getText();
				textField.setVisible(false);
				 
                //disable level choosing
				cmbLevel.setEnabled(false);
				cmbType.setEnabled(false);
				cmbSpeed.setEnabled(false);
				cmbAcc.setEnabled(false);
				
				//b1.setVisible(false);
				tetris.requestFocus();
				repaint();
			}
		});
		
        //kill game button
		JButton endGame = new JButton("End Game");
		endGame.setBounds(170, 440, 80, 40);//x, y, width, height
		add(endGame);

		endGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetris.isGameOver=true;
				tetris.logicTimer.setPaused(true);
				isSubmit=false;
			}
		});
        
        
        //store game record & show rank
		JButton sb = new JButton("store score");
		JButton show = new JButton("show rank");
		JButton clear = new JButton("clear storage");
		
		sb.setBounds(10, 210, 100, 30);//x, y, width, height
		show.setBounds(105, 210, 100, 30);
		clear.setBounds(200, 210, 130, 30);
		add(sb);
		add(show);
		add(clear);
		
		sb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetris.saveCurrent();
				System.out.println("sb");
				tetris.requestFocus();
			}
		});
		
		show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetris.showRank();
				System.out.println("show");
				tetris.requestFocus();
			}
		});
		
		clear.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
		    tetris.clearStorage();
		    tetris.requestFocus();
		   }
		});

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(DRAW_COLOR);
		
		/*
		 * This variable stores the current y coordinate of the string.
		 * This way we can re-order, add, or remove new strings if necessary
		 * without needing to change the other strings.
		 */
		int offset= STATS_INSET;
				
		g.setFont(LARGE_FONT);
		//g.drawString("Level: " + tetris.getLevel(), LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("Score :  " + tetris.getScore(), SMALL_INSET, 170);
		
		//Draw the next piece preview box
		g.setFont(LARGE_FONT);
		g.drawString("Next Piece :", SMALL_INSET, 140);
		g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);
		
		/*
		 * Draw a preview of the next piece that will be spawned. The code is pretty much
		 * identical to the drawing code on the board, just smaller and centered, rather
		 * than constrained to a grid.
		 */
		Tile type = tetris.getNextPieceType();
		
		if(tetris.isGameOver()) {
			resetPanel();
		}
		
		if(tetris.isGameOver() || tetris.isNewGame()) {
			   if(isSubmit==true) {
			    textField.setVisible(false);
			       cmbLevel.setEnabled(false);
			       cmbType.setEnabled(false);
			       cmbSpeed.setEnabled(false);
			       cmbAcc.setEnabled(false);
			   }
		}
		
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

    public void resetPanel() {
    	//labelUser.setText("Name: Please Input A User Name  ");
    	textField.setVisible(true);
    	cmbLevel.setEnabled(true);
    	cmbType.setEnabled(true);
    	cmbSpeed.setEnabled(true);
    	cmbAcc.setEnabled(true);
    	
    }
}
