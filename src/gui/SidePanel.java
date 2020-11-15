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

import game.GameController;
import game.LevelCustom;
import game.LevelHigh;
import game.LevelLow;
import game.LevelMid;

public class SidePanel extends JPanel implements Panel{
	
	private static final long serialVersionUID = 2181495598854992747L;
	
	
    //preview window
	private static final int TILE_SIZE = BoardPanel.getTILE_SIZE() -5;//set tile size smaller than in board//used to be >> 1
	private static final int SHADE_WIDTH = BoardPanel.getSHADE_WIDTH() >> 1;
	
	private static final int TILE_COUNT = 5;//preview window square total col/row num
	private static final int SQUARE_CENTER_X = 65;//center x y of the next piece preview box.
	private static final int SQUARE_CENTER_Y = 65;
	
	private static final int SQUARE_SIZE = (TILE_SIZE * TILE_COUNT >> 1);//size of the next piece preview box
	
	//side panel
	private static final int SIDE_WIDTH = 250;
	private static final int SIDE_HEIGHT = BoardPanel.getPANEL_HEIGHT();
	
	//left, right part layout
	private static final int LEFT_START = 20;
	private static final int RIGHT_START=125;
	
	private static final int UNIT_WIDTH=100;
	private static final int UNIT_HEIGHT=30;
	
	//font
	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);
	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
	
	private static final Color DRAW_COLOR = Color.BLACK;
	private Drawer draw = new Drawer(TILE_SIZE, SHADE_WIDTH);
	
	
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
	
	private JLabel chooseLevel ;
	private JLabel chooseType ;
	private JLabel chooseSpeed ;
	private JLabel chooseAcc ;
	private JLabel line ;
	private JLabel note1 ;
	
    //store game record & show rank
	private JButton store;
	private JButton show;
	private JButton clear;
	private JButton submit;
	private JButton endGame;
	
    //boolean
	private boolean isSubmit=false;
	private boolean isCustom=false;
	private boolean isStore=false;
	
	//getter and setter
	public String getUserName() {
		return userName;
	}
	
	//other functions
	
	public void submitOperation() {
		String str = textField.getText();
		if(str.length()==0)
			JOptionPane.showMessageDialog(null, "Please input a username within 10 characters!", "alert", JOptionPane.ERROR_MESSAGE);//msg title
		else if(str.length()>10)
			JOptionPane.showMessageDialog(null, "Username must not exceed 10 characters!", "alert", JOptionPane.ERROR_MESSAGE);//msg title
		else {
			isSubmit=true;
			labelUser.setText("Name" + textField.getText());
			userName = textField.getText();
			lockSetting();
			repaint();
		}
	}

	public void endOperation() {
		
		tetris.setPause(false);
		tetris.setGameOver(true);
		tetris.pauseTime();
		isSubmit=false;
		textField.setText("");
	}
	
	public void storeOperation() {
		if(!tetris.isGameOver()) {
			JOptionPane.showMessageDialog(null, "You can store score only after GAMEOVER", "alert", JOptionPane.ERROR_MESSAGE);//msg title
		}
		else if(isStore==true) {
			JOptionPane.showMessageDialog(null, "Score already stored", "alert", JOptionPane.ERROR_MESSAGE);//msg title
		}
		else {
			tetris.saveCurrent();
			isStore=true;
		}
	}

	
	public void showLayout() {
		//layout 
        //setBounds x, y, width, height
        int offset= 185;
        
		store.setBounds(RIGHT_START, 80, UNIT_WIDTH, UNIT_HEIGHT);
		
		line.setBounds(0, 130, 800, 20);
        
        textField.setBounds(this.RIGHT_START, 155, UNIT_WIDTH, UNIT_HEIGHT);
		labelUser.setBounds(this.LEFT_START, 155, 200, UNIT_HEIGHT);// need to be longer
		
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
		
		Font FONT = new Font("Impact", Font.PLAIN, 19);
		submit.setFont(FONT);
		submit.setForeground(new Color(255, 182, 193));
		submit.setBackground(Color.black);
	}
	
	public void inputUsername() {
		//input user name
		labelUser = new JLabel("Name");
		labelUser.setFont(LARGE_FONT);
		labelUser.setForeground(Color.BLACK);
		Dimension d = labelUser.getPreferredSize();
		labelUser.setPreferredSize(new Dimension(d.width + 6, d.height));

		textField = new JTextField(20);
		textField.setFont(new Font("acefont-family", Font.BOLD, 10));
		
		add(labelUser);
		add(textField);
	}
	
	public void showInfo() {
		line = new JLabel(" --------------Information--------------");
		line.setFont(LARGE_FONT);
		line.setForeground(Color.black);
        add(line);
		
		//all 4 labels
		chooseLevel = new JLabel("Level");
		chooseLevel.setFont(LARGE_FONT);
		chooseLevel.setForeground(Color.black);
        add(chooseLevel);
		
		chooseType = new JLabel("Type Count");
		chooseLevel.setFont(LARGE_FONT);
		chooseType.setForeground(Color.black);
        add(chooseType);
        
		chooseSpeed = new JLabel("Speed");
		chooseLevel.setFont(LARGE_FONT);
		chooseSpeed.setForeground(Color.black);
        add(chooseSpeed);
        
		chooseAcc = new JLabel("Acceleration");
		chooseLevel.setFont(LARGE_FONT);
		chooseAcc.setForeground(Color.black);
        add(chooseAcc);        
        
        //level details label
        labelType=new JLabel("");
        add(labelType);
        labelSpeed=new JLabel("");
        add(labelSpeed);
        labelAcc=new JLabel("");
        add(labelAcc);
            
        note1 = new JLabel("Details for level can be customized");
		note1.setForeground(Color.black);
        add(note1);
	}
		
	public void showComboBox() {
	     
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
            
                		tetris.setLevel(LevelLow.getInstance());
                	}
                		
                	else if(levelStr=="Mid") {
                		isCustom=false;
                		tetris.setLevel(LevelMid.getInstance());
                		
                	}
                		
                	else if(levelStr=="High") {
                		isCustom=false;
                		tetris.setLevel(LevelHigh.getInstance());
                		
                	}
                	else if(levelStr=="Custom") {
                		isCustom=true;
                		tetris.setLevel(LevelCustom.getInstance());
                		
                	}
                	
                	
                	cmbType.setSelectedItem(tetris.getLevel().getTileCnt());
                	cmbSpeed.setSelectedItem(tetris.getLevel().getSpeed());
                	cmbAcc.setSelectedItem(tetris.getLevel().getAccelaration());
                    
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
                    tetris.getLevel().setTileCnt((int) cmbType.getSelectedItem());
         
                }
            }
        });
        
        cmbSpeed.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tetris.getLevel().setSpeed((float) cmbSpeed.getSelectedItem());
                    
                }
            }
        });
        
        cmbAcc.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                     tetris.getLevel().setAccelaration((float) cmbAcc.getSelectedItem());
                    
                    
                }
            }
        });
		
	}
	
	public void showButton() {

  		store = new JButton("StoreScore");
  		show = new JButton("ShowRank");
  		clear = new JButton("ClearRank");
		submit = new JButton("SUBMIT");
		
		endGame = new JButton("EndGame");

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitOperation();			
			}
		});

		endGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endOperation();
			
			}
		});
		
		store.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				storeOperation();
			}
		});
		
		show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetris.showRank();		
			}
		});
		
		clear.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
		    tetris.clearStorage();
		   
		   }
		});
	
		add(submit);
		add(endGame);
		add(store);
		add(show);
		add(clear);
	
	}

	public SidePanel(GameController tetris) {
		this.tetris = tetris;
		setLayout(null);
		setPreferredSize(new Dimension(SIDE_WIDTH, SIDE_HEIGHT));
		setBackground(new Color(255,182,193));		

		inputUsername();
		
		showInfo();
		
		showComboBox();
		
		showButton();
		
		showLayout();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.setColor(DRAW_COLOR);	
		g.setFont(LARGE_FONT);
		g.drawString("Score   " + tetris.getScore(), 140, 60);
		
		//Draw the next piece preview box
		g.setFont(LARGE_FONT);
		g.drawString("Next Piece ", 140, UNIT_HEIGHT);
		g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);
		
		
		Tile type = tetris.getNextPieceType();
		
		if(!(tetris.isGameOver()||tetris.isNewGame()))
			isStore=false;
		
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
			labelType.setText(Integer.toString(tetris.getLevel().getTileCnt()));
			cmbType.setVisible(false);
			
			labelSpeed.setVisible(true);
			labelSpeed.setText(Float.toString(tetris.getLevel().getSpeed()));
			cmbSpeed.setVisible(false);
			
			
			labelAcc.setVisible(true);
			labelAcc.setText(Float.toString(tetris.getLevel().getAccelaration()));
			cmbAcc.setVisible(false);
		}
				
		if(tetris.isGameOver() || tetris.isNewGame()) {
			    unlockSetting();
			    if(isSubmit==true) {
			    	lockSetting();
			    }
		}
		
		if(!tetris.isGameOver() && type != null) {
			//Get the size properties of the current piece
			int cols = type.getCols();
			int rows = type.getRows();
			int dimension = type.getDimension();
		
			//Calculate the top left corner (origin) of the piece.
			int startX = (SQUARE_CENTER_X - (cols * TILE_SIZE / 2));
			int startY = (SQUARE_CENTER_Y - (rows * TILE_SIZE / 2));
		
			//default rotation 0
			int top = type.getTopInset(0);
			int left = type.getLeftInset(0);
					
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
    	
    	labelUser.setText("Name");
    	textField.setVisible(true);

    	cmbLevel.setEnabled(true);
    	cmbType.setEnabled(true);
    	cmbSpeed.setEnabled(true);
    	cmbAcc.setEnabled(true);
    	
    }
    
    public void lockSetting() {
    	
	 labelUser.setText("Name " + textField.getText());
     textField.setVisible(false);
     cmbLevel.setEnabled(false);
     cmbType.setEnabled(false);
     cmbSpeed.setEnabled(false);
     cmbAcc.setEnabled(false);
     
     tetris.requestFocus();
    	
    }
    
    public void setIsCustom(boolean b) {
    	this.isCustom = b;
    }

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
}
