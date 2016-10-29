import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UI extends JFrame implements BookList {

	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	ArrayList<BookStore> store;
	Cart c = new Cart();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSearchByName = new JLabel("Search By Name or Author");
		lblSearchByName.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchByName.setBounds(10, 11, 150, 21);
		contentPane.add(lblSearchByName);
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblOr.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOr.setBounds(20, 44, 51, 21);
		contentPane.add(lblOr);
		
		searchField = new JTextField();
		searchField.setBounds(170, 11, 117, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton btnListAll = new JButton("List All");
		btnListAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillTable(store);
			}
		});
		btnListAll.setBounds(71, 43, 89, 23);
		contentPane.add(btnListAll);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Searching for " + searchField.getText());
				Book[] books = list(searchField.getText());
				System.out.println(books.length + " book/s found");
				String [][]tableData = new String[books.length][3];
				int i = 0;
				for(Book b : books)
				{
					
					tableData[i][0] = b.getTitle();
					tableData[i][1] = b.getAuthor();
					tableData[i++][2] = b.getPrice() + "";
				}
				fillTable(tableData);
				
			}
		});
		btnSearch.setBounds(290, 10, 89, 23);
		contentPane.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 514, 205);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton btnAddToCart = new JButton("Add to Cart");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String in = JOptionPane.showInputDialog("Enter Quantity");
				int sel = table.getSelectedRow();
				
				TableModel model = table.getModel();
				String a = (String) model.getValueAt(sel, 0);
				//System.out.println(a);
				String t = (String) model.getValueAt(sel, 1);
				////System.out.println(t);
				double price = Double.parseDouble((String) model.getValueAt(sel, 2));
				//System.out.println(price);
				int qt = Integer.parseInt(in);
				add(new Book(a, t, price), qt);
			}
		});
		btnAddToCart.setBounds(180, 45, 117, 23);
		contentPane.add(btnAddToCart);
		
		JButton btnShowCart = new JButton("Show Cart");
		btnShowCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.show();
				c.fillTable();
				
				
				
			}
		});
		btnShowCart.setBounds(406, 66, 118, 23);
		contentPane.add(btnShowCart);
		store = FileManager.readToList();
	}
	
	
	private void fillTable(ArrayList<BookStore> store)
	{
		String[][] tableData = new String[store.size()][4];
		int i = 0;
		for(BookStore b : store)
		{
			
			tableData[i][0] = b.getB().getTitle();
			tableData[i][1] = b.getB().getAuthor();
			tableData[i][2] = b.getB().getPrice() + "";
			tableData[i][3] = b.getTotalInStock() + "";
			i++;
		}
		Object[] titles = {"Title", "Author", "Price", "InStock"};
		TableModel model = new DefaultTableModel(tableData, titles );
		table.setModel(model);
	}
	
	private void fillTable(Object[][] tableData)
	{
		
		
		Object[] titles = {"Title", "Author", "Price"};
		TableModel model = new DefaultTableModel(tableData, titles );
		table.setModel(model);
	}

	@Override
	public Book[] list(String searchString) {
		
		ArrayList<Book> bks = null;
		if(searchString.length() != 0)
		{
			System.out.println("Starting ");
			bks = new ArrayList<>();
			
			for(BookStore b : store)
			{
				Book book = b.getB();
				if(book.getAuthor().toLowerCase().equals(searchString.toLowerCase()) ||
						book.getTitle().toLowerCase().equals(searchString.toLowerCase()))
				{
					bks.add(book);
				}
			}
			
		}
		else
		{
			
		}
		Book [] ar = new Book[bks.size()];
		int i = 0;
		for(Book b : bks)
		{
			ar[i] = b;
		}
		
		return ar;
	}

	@Override
	public boolean add(Book book, int quantity) {
		boolean added = false;
		int st = getStock(book);
		System.out.println("Quantity " + st);
		if(st - quantity >= 0)
		{
			
			for(int i = 0; i < store.size(); i++)
			{
				if(store.get(i).getB().getAuthor().toLowerCase().equalsIgnoreCase(book.getAuthor().toLowerCase())
						&& store.get(i).getB().getPrice() == book.getPrice())
				{
					//JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE); 
					store.get(i).setTotalInStock(store.get(i).getTotalInStock() - quantity);
					added = true;
					c.add(book, quantity);
				}
			}
			fillTable(store);
			
		}
		else
		{
			JOptionPane.showMessageDialog(null, " Book is short", "alert", JOptionPane.ERROR_MESSAGE); 
		}
		return added;
	}

	@Override
	public int[] buy(Book... books) {
		int[] statusCodes = new int[books.length];
		int i = 0;
		for(Book b : books)
		{
			if(getStock(b) > 0)
			{
				statusCodes[i] = 0;
			}
			else if(getStock(b) == 0)
			{
				statusCodes[i] = 1;
			}
			else
			{
				statusCodes[i] = 2;
			}
		}
		return statusCodes;
	}
	
	public int getStock(Book b)
	{
		int stock = -1;
		for (BookStore st : store)
		{
			//System.out.println("Match" + st.getB().getAuthor() + " and " + b.getAuthor());
			if(st.getB().getAuthor().toLowerCase().equalsIgnoreCase(b.getAuthor().toLowerCase())
					&& st.getB().getPrice() == b.getPrice())
			{
				System.out.println("Match Found");
				stock = st.getTotalInStock();
				 break;
			}
		}
		return stock;
	}
	
}