import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;
	
	
	int[][] index = { 
			{ 1, 2, 3, 4 }, 
			{ 5, 6, 7, 8 }, 
			{ 9, 10, 11, 12 }, 
			{ 13, 14, 15, 0 }, 
		};
	int[][] won = { 
			{ 1, 2, 3, 4 }, 
			{ 5, 6, 7, 8 }, 
			{ 9, 10, 11, 12 }, 
			{ 13, 14, 15, 0 }, 
		};
	
	int row; // 0 element row index
	int col; // 0 element column index
	int count;//steps counter

	public MainFrame() {
		this.addKeyListener(this);
		
		initFrame();
		initData();
		paintView();
		initMenu();
		
		setVisible(true);// Put in the end
	}
	
	//Initial data
	public void initData(){
		Random r = new Random();
		
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index[i].length; j++) {
				int randomX = r.nextInt(4);
				int randomY = r.nextInt(4);
				
				int temp = index[i][j];
				index[i][j] = index[randomX][randomY];
				index[randomX][randomY] = temp;	
			}			
		}	
		
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index[i].length; j++) {
				if(index[i][j] == 0) {
					row = i;
					col = j;
				}
				
			}
		}
		
	}
	
	// Initial Frame
	public void initFrame() {
		setSize(414, 480);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Stone Puzzle Game V1.0");
		setLayout(null);// Cancel the default layout
		
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		
	}

	public void initMenu() {
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_G);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("New Game");
		menu.add(menuItem);
		menuItem.addActionListener(e-> {
			count = 0;
			initData();
			paintView();			
		});
		
		submenu = new JMenu("Instruction");
		submenu.setMnemonic(KeyEvent.VK_I);

		menuItem = new JMenuItem("Move Left: left-key");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(
//		        KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Move Up: up-key");
		submenu.add(menuItem);
 
		menuItem = new JMenuItem("Move Right: right-key");
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("Move Down: down-key");
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("End Game: 'z'");
		submenu.add(menuItem);
		
		menu.add(submenu);

		menuItem = new JMenuItem("Exit");
		menu.add(menuItem);
		menuItem.addActionListener(e-> {
			System.exit(0);			
		});
		
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	// Paint the frame
	public void paintView() {
		getContentPane().removeAll();
		
		if(won()) {
			//load won picture
			JLabel wonLabel = new JLabel(new ImageIcon("C:\\Users\\jspzh\\eclipseJEE-workspace\\JavaDemo\\src\\img\\won.png"));
			wonLabel.setBounds(0,20,400,402);
			getContentPane().add(wonLabel);
			
		}
		
		JLabel stepLabel = new JLabel("Steps count: " + count);
		stepLabel.setBounds(10, 0, 100, 20);
		getContentPane().add(stepLabel);
		
		JButton btn = new JButton("Start New Game");
		btn.setBounds(250, 0, 130, 20);
		getContentPane().add(btn);
		btn.setFocusable(false);
		
		btn.addActionListener(e-> {
			count = 0;
			initData();
			paintView();			
		});
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				JLabel image = new JLabel(new ImageIcon("C:\\Users\\jspzh\\eclipseJEE-workspace\\JavaDemo\\src\\img\\img" + index[i][j] + ".png"));
				image.setBounds(40 + 80 * j, 70 + 80 * i, 80, 80);
				getContentPane().add(image);
			}
		}

		JLabel jl = new JLabel(new ImageIcon("C:\\Users\\jspzh\\eclipseJEE-workspace\\JavaDemo\\src\\img\\bkground.png"));
		jl.setBounds(0, 20, 400, 400);
		getContentPane().add(jl);
		
		getContentPane().repaint();
	}


	//Tell if the game is won
	public boolean won() {
		
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index[i].length; j++) {
				if(index[i][j] != won[i][j]) {
					return false;
				}		
			}			
		}
		return true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		move(keyCode);
		//Repaint after every move
		paintView();
	}

	private void move(int keyCode) {
		if(won()) {
			return;
		}
		
		if (keyCode == 37) {
			if(col == 3) {
				return;
			}
			System.out.println("Left arrow clicked...");
			
			int temp = index[row][col];
			index[row][col] = index[row][col+1];
			index[row][col+1] = temp;
			col++;
			count++;
			
		} else if (keyCode == 38) {
			if(row == 3) {
				return;
			}
			System.out.println("Up arrow clicked...");
			int temp = index[row][col];
			index[row][col] = index[row+1][col];
			index[row+1][col] = temp;
			row++;
			count++;
		} else if (keyCode == 39) {
			if(col == 0) {
				return;
			}
			System.out.println("Right arrow clicked...");
			int temp = index[row][col];
			index[row][col] = index[row][col-1];
			index[row][col-1] = temp;
			col--;
			count++;
		} else if (keyCode == 40) {
			if(row == 0) {
				return;
			}
			System.out.println("Down arrow clicked...");
			int temp = index[row][col];
			index[row][col] = index[row-1][col];
			index[row-1][col] = temp;
			row--;
			count++;
		} else if(keyCode == 90) {// key: z
			index = new int[][] {
				{ 1, 2, 3, 4 }, 
				{ 5, 6, 7, 8 }, 
				{ 9, 10, 11, 12 }, 
				{ 13, 14, 15, 0 },
			};
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
