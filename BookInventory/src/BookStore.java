public class BookStore {
	Book b;
	int totalInStock;
	
	public BookStore(Book b, int t)
	{
		this.b = b;
		totalInStock = t;
	}
	
	public Book getB() {
		return b;
	}
	public void setB(Book b) {
		this.b = b;
	}
	public int getTotalInStock() {
		return totalInStock;
	}
	public void setTotalInStock(int totalInStock) {
		this.totalInStock = totalInStock;
	}
	
	
	
	
}