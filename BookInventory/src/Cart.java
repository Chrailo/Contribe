import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Window.Type;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cart extends JFrame {

	private JPanel contentPane;

	ArrayList<Book> booksBt = new ArrayList<>();
	
	public String []removed;
	ArrayList<Integer> qts = new ArrayList<Integer>();
	int i = 0;
	
	int total;
	private JTable table;
	private JButton btnRemove;
	
	public Cart() {
		setType(Type.POPUP);
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 414, 211);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sel = table.getSelectedRow();
				TableModel model = table.getModel();
				String t = (String) model.getValueAt(sel, 0);
				//System.out.println(a);
				String a = (String) model.getValueAt(sel, 1);
				////System.out.println(t);
				double price = Double.parseDouble((String) model.getValueAt(sel, 2));
				int qt = Integer.parseInt((String)model.getValueAt(sel, 3));
				for( int i = 0; i < booksBt.size(); i++)
				{
					System.out.println(a + "  " + booksBt.get(i).getAuthor());
					if(booksBt.get(i).getAuthor().equals(a) && booksBt.get(i).getPrice() == price)
					{
						booksBt.remove(i);
						System.out.println("Removed");
					}
				}
				fillTable();
				removed[i++] += a + ";" + t + ";" + price + ";" + qt;
			}
		});
		btnRemove.setBounds(10, 11, 89, 23);
		contentPane.add(btnRemove);
		
	}
	
	public void add(Book b, int qt)
	{
		total += b.getPrice() * qt;
		qts.add(qt);
		booksBt.add(b);
		System.out.println(booksBt.size() + "   cart size");
		removed = new String[qts.size()];
	}
	
	public void fillTable()
	{
		String[][] tableData = new String[booksBt.size()][4];
		int i = 0;
		for(Book b : booksBt)
		{
			
			tableData[i][0] = b.getTitle();
			tableData[i][1] = b.getAuthor();
			tableData[i][2] = b.getPrice() + "";
			tableData[i][3] = qts.get(i) + "";
			i++;
		}
		Object[] titles = {"Title", "Author", "Price", "In Cart"};
		TableModel model = new DefaultTableModel(tableData, titles );
		table.setModel(model);
	}

}