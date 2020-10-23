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
import java.util.ArrayList;

public class GameSave {
	
	private GameController tetris;
	public GameSave(GameController tetris) {
		this.tetris = tetris;
	}
	
	public void save(String name, int score) {
		User newUser = new User();
		newUser.setName(name);
		newUser.setMax(score);
		saveRankingList(newUser);
		System.out.println("save");
	}

    /**
     * @param array要存档的数据
     */
    public boolean saveInfo(int[][] array) {
        try {
            // 实例化一个输出流对象
            OutputStream os = new FileOutputStream("./output.txt");

            os.write(array.length);// 写入数组的行数
            os.write(array[0].length);// 写入数组的列数

            // 将数组中的元素写入到文件中
            for (int r = 0; r < array.length; r++) {
                for (int c = 0; c < array.length; c++) {
                    os.write(array[r][c]);
                }
            }

            os.close();//关文件
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取文档中的数据
     *
     * @return 返回读取到存档信息
     */
    @SuppressWarnings("resource")
    public int[][] openInfo() {
        try {
            // 实例化一个输入流对象
            InputStream is = new FileInputStream("./output.txt");

            int row = is.read();
            int column = is.read();
            if (row != -1 && column != -1) {
                int[][] array = new int[row][column];

                for (int r = 0; r < array.length; r++) {
                    for (int c = 0; c < array.length; c++) {
                        array[r][c] = is.read();
                    }
                }

                is.close();

                return array;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储游戏排行版信息的方法
     *
     * @param user用户信息对象
     */
    public boolean saveRankingList(User user) {
        try {
            ArrayList<User> list = openRankingList();
            if (list != null) {
                list.add(user);// 将新的用户信息(名字，最高分)添加到数组队列中
                for (int i = 0; i < list.size() - 1; i++) {
                    int index = i;
                    for (int j = i + 1; j < list.size(); j++) {
                        User use_max = (User) list.get(index);
                        User use = (User) list.get(j);
                        if (use_max.getMax() <= use.getMax()) {
                            index = j;
                        }
                    }
                    if (index != i) {
                        User use_max = (User) list.get(index);
                        User use = (User) list.get(i);
                        list.set(index, use);
                        list.set(i, use_max);
                    }
                }
            } else {
                list = new ArrayList<User>();
                list.add(user);
            }
            // 实例化一个输出流对象
            OutputStream os = new FileOutputStream("./rank.txt");
            DataOutputStream dos = new DataOutputStream(os);

            dos.writeInt(list.size());// 写入信息条数
            for (int i = 0; i < list.size(); i++) {
                User use = (User) list.get(i);
                dos.writeByte(use.getName().getBytes().length);
                dos.write(use.getName().getBytes());
                dos.writeInt(use.getMax());

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
     * 读取文档中的数据
     *
     * @return 返回读取到存档信息
     */
    public ArrayList<User> openRankingList() {
    	System.out.println("open Ranking list");
        try {
            // 实例化一个输入流对象
            InputStream is = new FileInputStream("./rank.txt");
            DataInputStream d = new DataInputStream(is);

            // 读取信息的条数
            int size = d.readInt();

            //System.out.println("===="+size);
            // 实例化ArrayList的数组队列对象
            ArrayList<User> list = new ArrayList<User>();
            if (size != -1) {
                for (int i = 0; i < size; i++) {
                    byte nSize = d.readByte();//这里没太懂
                    byte[] b = new byte[nSize];
                    is.read(b);
                    int max = d.readInt();

                    User use = new User(new String(b), max); 
                    list.add(use);
                }
            }

            d.close();
            is.close();

            return list;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
