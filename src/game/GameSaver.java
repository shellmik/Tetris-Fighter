package game;

import java.awt.Color;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.io.File;

public class GameSaver {

	private GameController tetris;
	
	private ArrayList<User> list;

	public GameSaver() {
		this.tetris = GameController.getInstance();
	}
	
	public void display() {
		JFrame frame = new JFrame();
		frame.setTitle("Ranking");
		frame.setSize(500, 700);
		frame.setDefaultCloseOperation(2);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setResizable(false);
				
//		if(list!=null)
//			System.out.println("ok");
//		else
//			System.out.println("not ok");
			
		try {

			int listSize = list.size();
			if(list == null || list.size() == 0) {
				JLabel lab = new JLabel("Sorry, there is no record at all!");
				lab.setBounds(30, 60, 400, 50);
				frame.add(lab);
				frame.setVisible(true);
			}else {
				JLabel lab = new JLabel("User        Max        Time");
				lab.setBounds(30, 20, 400, 50);
				frame.add(lab);

				for (int i = 0; i < list.size(); i++) {
					User user = (User) list.get(i);
					JLabel label = new JLabel(user.toString());
					System.out.println(user.toString());
					label.setBounds(30, 20 + (i + 1) * 50, 400, 60);
					frame.add(label);
				}
				frame.setVisible(true);
			}
			
		} catch (Exception e) {
			JLabel lab = new JLabel("Sorry, there is no record at all!");
			lab.setBounds(30, 60, 400, 50);
			frame.add(lab);
			frame.setVisible(true);
		}
	}

	public void save(String name, int score, String date) {
		User newUser = new User();
		newUser.setName(name);
		newUser.setScore(score);
		newUser.setDate(date);
		saveRankingList(newUser);
		System.out.println("saved as: " + name + " " + score + " " + date);
	}

	public void clean() {
		//this.list.clear();
		
		PrintWriter writer;
		try {
			File file = new File("./rank.txt");
			if (file.exists() && file.length() == 0) {
				System.out.println("file.exists() && file.length() == 0");
			} else {
				writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			}
			list.clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write to doc
	public void saveRankingList(User user) {
		try {
			openRankingList();
			if (list != null) {
				list.add(user);

				// sorting to get a list ordered by score
				for (int i = 0; i < list.size() - 1; i++) {
					int index = i;
					for (int j = i + 1; j < list.size(); j++) {
						User use_score = (User) list.get(index);
						User use = (User) list.get(j);
						if (use_score.getScore() <= use.getScore()) {
							index = j;
						}
					}
					if (index != i) {
						User use_score = (User) list.get(index);
						User use = (User) list.get(i);
						list.set(index, use);
						list.set(i, use_score);
					}
				}
			} else {
				list = new ArrayList<User>();
				list.add(user);
			}

			OutputStream os = new FileOutputStream("./rank.txt");
			DataOutputStream dos = new DataOutputStream(os);

			dos.writeInt(list.size());// å†™å…¥ä¿¡æ�¯æ�¡æ•°
			for (int i = 0; i < list.size(); i++) {
				User use = (User) list.get(i);
				dos.writeByte(use.getName().getBytes().length);
				dos.write(use.getName().getBytes());
				dos.writeInt(use.getScore());
				dos.writeByte(use.getDate().getBytes().length);
				dos.write(use.getDate().getBytes());
			}

			dos.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read from doc
	public void openRankingList() {
		System.out.println("open Ranking list");
		try {

			InputStream is = new FileInputStream("./rank.txt");
			DataInputStream d = new DataInputStream(is);

			int size = d.readInt();

			ArrayList<User> listTmp = new ArrayList<User>();
			if (size != -1) {
				for (int i = 0; i < size; i++) {
					byte nSize = d.readByte();
					byte[] b = new byte[nSize];
					is.read(b);
					int score = d.readInt();
					byte n1Size = d.readByte();
					byte[] c = new byte[n1Size];
					is.read(c);

					User use = new User(new String(b), score, new String(c));
					listTmp.add(use);
				}
			}

			d.close();
			is.close();
			list = listTmp;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
}
