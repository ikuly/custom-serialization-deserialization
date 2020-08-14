package entity;

public class Book implements MySerialize {

    private String name;
    private int pages;
    private Author author;


    public Book() {
    }

    public Book(String name, int pages, Author author) {
        this.name = name;
        this.pages = pages;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public int getPages() {
        return pages;
    }

    public Author getAuthor() {
        return author;
    }


    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                ", author=" + author +
                '}';
    }
}
