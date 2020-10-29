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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SidePanel extends JPanel implements Panel{
	
	private static final long serialVersionUID = 2181495598854992747L;
	
	//level
	private Level sideLevel;
	private int levelTileCnt;
	private float levelSpeed;
	private float levelAcc;
	
    //preview window
	private static final int TILE_SIZE = BoardPanel.TILE_SIZE -5;//set tile size smaller than in board//used to be >> 1
	private static final int SHADE_WIDTH = BoardPanel.SHADE_WIDTH >> 1;
	
	private static final int TILE_COUNT = 5;//preview window square total col/row num
	private static final int SQUARE_CENTER_X = 65;//center x y of the next piece preview box.
	private static final int SQUARE_CENTER_Y = 65;
	
	private static final int SQUARE_SIZE = (TILE_SIZE * TILE_COUNT >> 1);//size of the next piece preview box
	
	//side panel
	private static final int SIDE_WIDTH = 250;
	private static final int SIDE_HEIGHT = BoardPanel.PANEL_HEIGHT;
	
	//left, right part layout
	private static final int LEFT_START = 20;
	private static final int RIGHT_START=125;
	
	private static final int UNIT_WIDTH=100;
	private static final int UNIT_HEIGHT=30;
	
	//font
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
	
	private static final Color DRAW_COLOR = Color.BLACK;
	private drawer draw = new drawer(TILE_SIZE, SHADE_WIDTH);
	
	
	private String userName;
	private GameController tetris;
	
	//JPanel elements
	private JLabel labelUser;
	private JLabel labelType;
	private JLabel labelSpeed;
	private JLabel labelAcc;
	
	private JTextField textField ;
	
	private JComboBox cmbLevel;
	private JComboBox<Integer> cmbType;
	private JComboBox cmbSpeed;
	private JComboBox cmbAcc;
	
    //store game record & show rank
	JButton store;
	JButton show;
	JButton clear;
	
    //flags
	public boolean isSubmit=false;
	public boolean isCustom=false;
	
	//public int storeCount = 0;
	
	public String getUserName() {
		return userName;
	}

	public SidePanel(GameController tetris) {
		this.tetris = tetris;
		setLayout(null);
		setPreferredSize(new Dimension(SIDE_WIDTH, SIDE_HEIGHT));
		setBackground(new Color(255,182,193));
		
		//input user name
		labelUser = new JLabel("Name:");
		labelUser.setFont(LARGE_FONT);
		labelUser.setForeground(Color.BLACK);
		Dimension d = labelUser.getPreferredSize();
		labelUser.setPreferredSize(new Dimension(d.width + 6, d.height));

		textField = new JTextField(20);
		textField.setFont(new Font("acefont-family", Font.BOLD, 10));
		
		add(labelUser);
		add(textField);
		
		JLabel line = new JLabel(" --------------Information--------------");
		line.setFont(LARGE_FONT);
		line.setForeground(Color.black);
        add(line);
		
		//all 4 labels
		JLabel chooseLevel = new JLabel("Level：");
		chooseLevel.setFont(LARGE_FONT);
		chooseLevel.setForeground(Color.black);
        add(chooseLevel);
		
		JLabel chooseType = new JLabel("Type Count：");
		chooseLevel.setFont(LARGE_FONT);
		chooseType.setForeground(Color.black);
        add(chooseType);
        
		JLabel chooseSpeed = new JLabel("Speed：");
		chooseLevel.setFont(LARGE_FONT);
		chooseSpeed.setForeground(Color.black);
        add(chooseSpeed);
        
		JLabel chooseAcc = new JLabel("Acceleration：");
		chooseLevel.setFont(LARGE_FONT);
		chooseAcc.setForeground(Color.black);
        add(chooseAcc);
        

        //store game record & show rank
  		store = new JButton("StoreScore");
  		show = new JButton("ShowRank");
  		clear = new JButton("ClearRank");
        
        
        //level details label
        labelType=new JLabel("");
        add(labelType);
        labelSpeed=new JLabel("");
        add(labelSpeed);
        labelAcc=new JLabel("");
        add(labelAcc);
        
        //combo boxes
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
        //4
        String[] listLevel = new String[]{"Low","Mid","High","Custom"};
        cmbLevel=new JComboBox();
        for(int i=0;i<listLevel.length;i++) {
        	cmbLevel.addItem(listLevel[i]);
        }
        cmbLevel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	String levelStr=(String) cmbLevel.getSelectedItem();
                	if(levelStr=="Low") {
                		isCustom=false;
                		sideLevel=LevelLow.getInstance();
                	}
                		
                	else if(levelStr=="Mid") {
                		isCustom=false;
                		sideLevel=LevelMid.getInstance();
                	}
                		
                	else if(levelStr=="High") {
                		isCustom=false;
                		sideLevel=LevelHigh.getInstance();
                	}
                	else if(levelStr=="Custom") {
                		isCustom=true;
                		sideLevel=LevelLow.getInstance();
                	}
                	tetris.setLevel(sideLevel);
                	
                	cmbType.setSelectedItem(sideLevel.getTileCnt());
                	cmbSpeed.setSelectedItem(sideLevel.getSpeed());
                	cmbAcc.setSelectedItem(sideLevel.getAccelaration());
                    
                }
            }
        });
        cmbLevel.setSelectedIndex(1);
        add(cmbLevel);
 
		//cmb listeners
        cmbType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	
                    levelTileCnt=(int) cmbType.getSelectedItem();
                    System.out.println("select: "+levelTileCnt);
                    sideLevel.setTileCnt(levelTileCnt);
         
                }
            }
        });
        
        cmbSpeed.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    levelSpeed=(float) cmbSpeed.getSelectedItem();
                    System.out.println("select: "+levelSpeed);
                    sideLevel.setSpeed(levelSpeed);
                }
            }
        });
        
        cmbAcc.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    levelAcc=(float) cmbAcc.getSelectedItem();
                    System.out.println("select: "+levelAcc);
                    sideLevel.setAccelaration(levelAcc);
                    
                }
            }
        });
        JLabel note1 = new JLabel("Details for level can be customized");
		note1.setForeground(Color.black);
        add(note1);
        
        
        
        
        //submit button
		JButton submit = new JButton("Submit");
		
		

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str=textField.getText();
				if(str.length()==0)
					JOptionPane.showMessageDialog(null, "please fill in a user name", "alert", JOptionPane.ERROR_MESSAGE);//msg title
				else {
					isSubmit=true;
					revalidate();
					repaint();
					System.out.println("submit clicked");
					labelUser.setText("Name:" + textField.getText());
					userName = textField.getText();
					
					lockSetting();
					repaint();
				}
			
			}
		});
		
        //kill game button
		JButton endGame = new JButton("EndGame");

		endGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				//these 2 lines: to change pause page to game over page
				if(tetris.isPaused) {
					tetris.isPaused = !tetris.isPaused;
					tetris.logicTimer.setPaused(tetris.isPaused);
				}
				
				tetris.isGameOver=true;
				tetris.logicTimer.setPaused(true);
				isSubmit=false;
				textField.setText("");
				
			}
		});
        		
		
		add(submit);
		add(endGame);

		add(store);
		add(show);
		add(clear);
		
		store.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetris.saveCurrent();
				System.out.println("sb");
				tetris.requestFocus();
				//store.setEnabled(false);
				//storeCount = 0;
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

		
		//layout 
        //setBounds x, y, width, height
        int offset= 185;
        
		store.setBounds(RIGHT_START, 80, UNIT_WIDTH, UNIT_HEIGHT);
		
		line.setBounds(0, 130, 800, 20);
        
        textField.setBounds(this.RIGHT_START, 155, UNIT_WIDTH, UNIT_HEIGHT);
		labelUser.setBounds(this.LEFT_START, 155, UNIT_WIDTH, UNIT_HEIGHT);
		
        chooseLevel.setBounds(LEFT_START, 200, UNIT_WIDTH, UNIT_HEIGHT);
        cmbLevel.setBounds(this.RIGHT_START, 200, UNIT_WIDTH, UNIT_HEIGHT);
        
        
        note1.setBounds(LEFT_START, 240, 300, UNIT_HEIGHT);
        
        chooseType.setBounds(LEFT_START, 280, UNIT_WIDTH, UNIT_HEIGHT);
        cmbType.setBounds(this.RIGHT_START, 280, UNIT_WIDTH, UNIT_HEIGHT);
        labelType.setBounds(this.RIGHT_START, 280, UNIT_WIDTH, UNIT_HEIGHT);
                
        chooseSpeed.setBounds(LEFT_START, 315, UNIT_WIDTH, UNIT_HEIGHT);
        cmbSpeed.setBounds(this.RIGHT_START, 315, UNIT_WIDTH, UNIT_HEIGHT);
        labelSpeed.setBounds(this.RIGHT_START, 315, UNIT_WIDTH, UNIT_HEIGHT);
        
        chooseAcc.setBounds(LEFT_START, 350, UNIT_WIDTH, UNIT_HEIGHT);
        cmbAcc.setBounds(this.RIGHT_START, 350, UNIT_WIDTH, UNIT_HEIGHT);
        labelAcc.setBounds(this.RIGHT_START, 350, UNIT_WIDTH, UNIT_HEIGHT);
        
        submit.setBounds(LEFT_START-5, 400, UNIT_WIDTH, UNIT_HEIGHT);
		endGame.setBounds(RIGHT_START, 400, UNIT_WIDTH, UNIT_HEIGHT);
		
		show.setBounds(LEFT_START-5, 440, UNIT_WIDTH, UNIT_HEIGHT);
		clear.setBounds(RIGHT_START, 440, UNIT_WIDTH, UNIT_HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(DRAW_COLOR);	
		g.setFont(LARGE_FONT);
		g.drawString("Score :  " + tetris.getScore(), 140, 60);
		
		//Draw the next piece preview box
		g.setFont(LARGE_FONT);
		g.drawString("Next Piece ", 140, UNIT_HEIGHT);
		g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);
		
		
		Tile type = tetris.getNextPieceType();
		if(isCustom==true) {
			labelType.setVisible(false);
			cmbType.setVisible(true);
			
			labelSpeed.setVisible(false);
			cmbSpeed.setVisible(true);
			
			labelAcc.setVisible(false);
			cmbAcc.setVisible(true);

		}
		if(isCustom==false) {
			labelType.setVisible(true);
			labelType.setText(Integer.toString(sideLevel.getTileCnt()));
			cmbType.setVisible(false);
			
			labelSpeed.setVisible(true);
			labelSpeed.setText(Float.toString(sideLevel.getSpeed()));
			cmbSpeed.setVisible(false);
			
			
			labelAcc.setVisible(true);
			labelAcc.setText(Float.toString(sideLevel.getAccelaration()));
			cmbAcc.setVisible(false);
		}
		
//		if(tetris.isGameOver()) {
//			showGameOverPanel();
//		}
		
		if(tetris.isGameOver() || tetris.isNewGame()) {
//				storeCount--;
//				if(storeCount >= 0) {
//					store.setEnabled(true);
//				}
			    unlockSetting();
			    if(isSubmit==true) {
			    	lockSetting();
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

    public void unlockSetting() {
    	
    	labelUser.setText("Name:");
    	textField.setVisible(true);

    	cmbLevel.setEnabled(true);
    	cmbType.setEnabled(true);
    	cmbSpeed.setEnabled(true);
    	cmbAcc.setEnabled(true);
    	
    }
    
    public void lockSetting() {
    	
	 labelUser.setText("Name: " + textField.getText());
     textField.setVisible(false);
     cmbLevel.setEnabled(false);
     cmbType.setEnabled(false);
     cmbSpeed.setEnabled(false);
     cmbAcc.setEnabled(false);
     
     tetris.requestFocus();
    	
    }
}
