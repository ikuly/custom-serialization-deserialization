package entity;

public class Author implements MySerialize {

    private String name;
    private int number;
    private boolean live;

    public Author() {
    }

    public Author(String name, int number, boolean live) {
        this.name = name;
        this.number = number;
        this.live = live;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public boolean isLive() {
        return live;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", live=" + live +
                '}';
    }
}
