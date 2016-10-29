import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FileManager {
	
	/*public static void main(String[] args)
	{
		readToList();
	}*/
	
	public static ArrayList<BookStore> readToList(){

		ArrayList<BookStore> books = new ArrayList<BookStore>();
		
		BufferedReader in = null;
        URL oracle = null;
        try
        {
        	oracle = new URL("http://www.contribe.se/bookstoredata/bookstoredata.txt");
        	in = new BufferedReader(
        	        new InputStreamReader(oracle.openStream()));

        	        String inputLine;
        	        int t = 1;
        	        while ((inputLine = in.readLine()) != null)
        	            {
        	        		String[] tokens = inputLine.split(";");
        	        		//System.out.println("Token2 " + getNumber(tokens[2]));
        	        		double d = Double.parseDouble(getNumber(tokens[2]));
        	        		//System.out.println("Token3 " + getNumber(tokens[3]));
        	        		int it = Integer.parseInt(getNumber(tokens[3]));
        	        		Book book = new Book(tokens[0],tokens[1],d);
        	        		BookStore bStore = new BookStore(book, it);
        	        		
        	        		books.add(bStore);
        	        		//System.out.println("Added " + inputLine);
        	            }
        	        in.close();
        }
        catch(Exception e)
        {
        	//System.out.println("Exception " + e.getMessage());
        	e.printStackTrace();
        }
        finally
        {
        	try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return books;
    }
	
	private static String getNumber(String s)
	{
		String num = "";
		for(int i = 0; i < s.length(); i++)
		{
			if(Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')
			{
				num += s.charAt(i);
			}
		}
		return num;
	}
	
	
}