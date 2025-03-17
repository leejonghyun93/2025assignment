package sec05;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.ToString;
//
//@Data
//@AllArgsConstructor
public class Book {

	private String isbn;
	private String title;
	private int price;
	private String author;
	private String publicationDate;
	

	public Book(String isbn, String title, int price, String author, String publicationDate) {
		this.isbn = isbn;
		this.title = title;
		this.price = price;
		this.author = author;
		this.publicationDate = publicationDate;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", price=" + price + ", author=" + author
				+ ", publicationDate=" + publicationDate + "]";
	}
	
	
	
}