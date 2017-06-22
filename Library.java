import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

class Library
{
	public Accession accobj;
	public LibraryMembers membersobj;
	public IssueReceipt irobj;

    public Library()
    {
		accobj = new Accession();
		membersobj = new LibraryMembers();
		irobj = new IssueReceipt();
        mainMenu();
    }

    public void mainMenu()
    {
        System.out.println();
		System.out.println("Please enter your choice: ");
        System.out.println("1 - Accession");
        System.out.println("2 - Add Library Member");      
		System.out.println("3 - Delete Library Member");
		System.out.println("4 - Delete Library Book"); 
        System.out.println("5 - Search & Issue Books");
        System.out.println("6 - Return Books");
        System.out.println("7 - Display Library Members");
		System.out.println("8 - Display Issue Status");
		System.out.println("9 - Search & Display Library Items");
        System.out.println("10 - Exit");
        System.out.print("> "); 

        int Wn = 0;

        try
        {
            InputStreamReader Wisr = new InputStreamReader(System.in);
            BufferedReader Wbr = new BufferedReader(Wisr);
            Wn = Integer.parseInt(Wbr.readLine().trim());
        }
        catch(IOException err)
        {
            System.out.println("ERROR: Error reading main menu input!");
        }
        catch(NumberFormatException nerr)
        {
            System.out.println("ERROR: Expecting numeric value...");
        }

        switch(Wn)
        {
            case 1:
                accobj.addLibraryItem();
                mainMenu();
                break;
            case 2:
                membersobj.addLibraryMember();
                mainMenu();
                break;
			case 3:
                membersobj.deleteLibraryMember();
                mainMenu();
                break;
            case 4:
                irobj.deleteLibraryBook();
                mainMenu();
                break;
            case 5:
				irobj.bookIssue();
                mainMenu();
                break;
            case 6:
                irobj.bookReturn();
                mainMenu();
                break;
			case 7:
                membersobj.displayLibraryMembers();
                mainMenu();
                break;
			case 8:
                irobj.displayIssuedBooks();
                mainMenu();
                break;
			case 9:
                irobj.displayLibraryBooks();
				 mainMenu();
                break;
            case 10:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input... Try again!");
                mainMenu();
                break;
        }
    }

    public static void main(String args[])
    {
    	System.out.print("\n\n\n");
    	System.out.printf("%50s","**************************\n");
    	System.out.printf("%50s","LIBRARY INFORMATION SYSTEM\n");
    	System.out.printf("%49s","**************************");
    	System.out.print("\n");
        new Library();
    }
}
