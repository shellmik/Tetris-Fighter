package gui;

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
import java.io.File;

public class GameSave {

	private GameController tetris;
	
	private ArrayList<User> list;

	public GameSave() {
		this.tetris = GameController.getInstance();
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
		PrintWriter writer;
		try {
			File file = new File("./rank.txt");
			if (file.exists() && file.length() == 0) {
				System.out.println("文件为空！");
			} else {
				writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write to doc
	public boolean saveRankingList(User user) {
		try {
			list = openRankingList();
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

			dos.writeInt(list.size());// 写入信息条数
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
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 存储游戏排行版信息的方法
	 *
	 * @param user用户信息对象
	 */
	/**
	 * 读取文档中的数据
	 *
	 * @return 返回读取到存档信息
	 */
	// read from doc
	public ArrayList<User> openRankingList() {
		System.out.println("open Ranking list");
		try {

			InputStream is = new FileInputStream("./rank.txt");
			DataInputStream d = new DataInputStream(is);

			int size = d.readInt();

			ArrayList<User> list = new ArrayList<User>();
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
					list.add(use);
				}
			}

			d.close();
			is.close();

			return list;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return null;
	}
}
