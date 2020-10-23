package gui;

public class User {
    private String name;
    private int max;


    public User() {

    }

    public User(String name, int max) {

        this.name = name;
        this.max = max;

    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getMax() {

        return max;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public String toString() {
        return name + "           " + max + "    " ;
    }

}