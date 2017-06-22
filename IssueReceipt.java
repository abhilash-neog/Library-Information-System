import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

class IssueReceipt
{
    private static byte Cindx;          // index of data item that will be searched in the file
    private static String Ccriteria;    // data item at the selected index
    
    public IssueReceipt()
    {
    }
    
    public void setBookSearchCriteria()
    {
        
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String Lchoice; // user's choice by which book searching will be done
            Scanner Lsobj = new Scanner(System.in);
            System.out.println();
            System.out.println("LIBRARY ITEM SEARCH CRITERIA");
            System.out.println();
            System.out.println("Display by:");
            System.out.println();
            System.out.println("N-Accession No. \t A-Author \t T-Book Title");
            System.out.println("S-Subject \t\t P-Publisher \t I-ISBN ");
            System.out.println("Y-Item Type");
            System.out.println();
            System.out.print("> ");
            Lchoice = Lsobj.next().toUpperCase();
            switch(Lchoice.charAt(0))
            {
                case 'N':
                    Cindx = 0;
                    System.out.println();
                    System.out.println("Enter Accession No.:");
                    System.out.print("> ");
                    Ccriteria = Lsobj.next().toUpperCase();
                    break;
                case 'S':
                    Cindx = 3;
                    System.out.println();
                    System.out.println("Enter book subject:");
                    System.out.print("> ");
                    Ccriteria = Lsobj.next().toUpperCase();
                    break;
                case 'A':
                    Cindx = 4;
                    System.out.println();
                    System.out.println("Enter author name:");
                    System.out.print("> ");
                    Ccriteria = br.readLine();
                    Ccriteria.toUpperCase();
                    break;
                case 'T':
                    Cindx = 5;
                    System.out.println();
                    System.out.println("Enter book title:");
                    System.out.print("> ");
                    Ccriteria = br.readLine();
                    Ccriteria.toUpperCase();
                    break;
                case 'P':
                    Cindx = 6;
                    System.out.println();
                    System.out.println("Choose publisher name:");
                    System.out.println("S-S.Chand & Co. Ltd. \t A-Ane Books India \t L-Laxmi Publications Pvt Ltd");
                    System.out.println("T-National Book Trust. \t G-Gyan Books (P) Ltd. \t E-Eastern Book Company");
                    System.out.println("P-Assam Publications \t B-Goyal Brothers Prakashans");
                    System.out.println("N-Not Specified");
                    System.out.print("> ");
                    Ccriteria = Lsobj.next().toUpperCase();
                    if(!(Ccriteria.equals("S") || Ccriteria.equals("A") || Ccriteria.equals("L") || Ccriteria.equals("T") || Ccriteria.equals("G") || Ccriteria.equals("E") || Ccriteria.equals("N") || Ccriteria.equals("P") || Ccriteria.equals("B")))
                        throw new Exception("Invalid publisher name!");     
                    break;
                case 'I':
                    Cindx = 7;
                    System.out.println();
                    System.out.println("Enter ISBN:");
                    System.out.print("> ");
                    Ccriteria = Lsobj.next().toUpperCase();
                    break;
                case 'Y':
                    Cindx = 2;
                    System.out.println();
                    System.out.println("Choose Type:");
                    System.out.println("B-Book \t J-Journal");
                    System.out.print("> ");
                    Ccriteria = Lsobj.next().toUpperCase();
                    if(!(Ccriteria.equals("B") || Ccriteria.equals("J")))
                        throw new Exception("Invalid type choosen!");
                    break;
                default:
                    throw new Exception("Invalid search criteria!");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void bookIssue()
    {
        try
        {
            String LIssueAccNo;         // Acc No. of book to issue
            String LIssueMemberID;      // member id to whom book is to be issued
            Scanner Lsobj = new Scanner(System.in);
            
            setBookSearchCriteria();
            
            char status = 'O';
            searchAndDisplayByCriteria(Cindx,Ccriteria,status);
            System.out.println();
            System.out.println("Proceed to issue book ? (y/n): ");
            System.out.print("> ");
            if(!(Lsobj.next().toUpperCase().equals("Y")))
                throw new Exception("Ok... Aborting book issue!");
            
            System.out.println();
            System.out.println("Enter Accession No. for issue:");
            System.out.print("> ");
            LIssueAccNo = Lsobj.next().toUpperCase();

            System.out.println();
            System.out.println("Enter Member ID for issue:");
            System.out.print("> ");
            LIssueMemberID = Lsobj.next().toUpperCase();

            boolean bookissued = issueAccessionNo(LIssueAccNo,LIssueMemberID);
            System.out.println();
            if(bookissued)
                System.out.println("Book issued successfully!");
            else
                System.out.println("Book issue failed!");
        }
        catch(Exception obj)
        {
            System.out.println(obj.toString());
        }
    }
    
    public void searchAndDisplayByCriteria(byte indx, String criteria, char status)
    {
        if(indx == 2 || indx == 3 || indx == 4 || indx == 5 || indx == 6)
            criteria = criteria.toUpperCase();
            
        long Wgreatest_accno = 0;
        Scanner Wobj;
        try
        {
            File Fforg = new File("data/library_books.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);

            String Wtext;
            boolean Lmatched = false;
            System.out.println();
            System.out.println("Displaying list of searched books:");
            System.out.printf("%8s%5s%24s%35s%50s%12s\n", "Acc No.", "Type", "Subject", "Author","Title","ISBN");
            System.out.printf("%8s%5s%24s%35s%50s%12s\n", "-------", "----", "-------", "------","-----","----");
            while((Wtext = Fbr.readLine()) != null)
            {
                Wobj = new Scanner(Wtext);
                Wobj.useDelimiter(",");
                String Wbook_data[] = new String[9]; 
                byte Windx = 0;
                while(Wobj.hasNext())
                {
                    Wbook_data[Windx++] = Wobj.next();
                }

                for (int Wi = 0; Wi < Wbook_data.length; Wi++)
                {
                    String Witem = Wbook_data[Wi];
                    if(Wi == indx && Witem.equals(criteria))
                    {
                        Lmatched = true;
                    }
                }
                if(Lmatched)
                {
                    if(status == 'A')
                    {
                        System.out.printf("%8s%5s%24s%35s%50s%12s\n", Wbook_data[0], Wbook_data[2], Wbook_data[3],Wbook_data[4],Wbook_data[5],Wbook_data[7]);
                        Lmatched = false;
                    }
                    else if(Wbook_data[8].charAt(0)==status)
                    {
                        System.out.printf("%8s%5s%24s%35s%50s%12s\n", Wbook_data[0], Wbook_data[2], Wbook_data[3],Wbook_data[4],Wbook_data[5],Wbook_data[7]);
                        Lmatched = false;
                    }
                    
                }
            }

            Fbr.close();
            FfileReader.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("ERROR: No library items exist!");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public boolean issueAccessionNo(String AccNo, String MemID)
    {
        boolean AccNoFound = false;    // to update the Issued status in library_books.dat
        boolean bookIssued = false;     // to return status if book is found
        boolean bookFound = false;
        boolean memberExists = false;
        byte noOfBooks = 0;

        Scanner Wobj;
        try
        {
            /*  SEARCH IF THE MEMBER ID EXISTS AND ALSO WHETHER 
                MORE THAN ONE BOOK IS UNDER ISSUE TO THIS MEMBER ID */
            memberExists = new LibraryMembers().memberExists(MemID);
            if(memberExists == false)
                throw new Exception("ERROR: Member ID doesn't exists!");

            noOfBooks = noOfBooksWithMember(MemID);
            if(noOfBooks > 1)
                throw new Exception("ERROR: Two books are already under issue against Member ID: " + MemID + " !");
            /*  END OF SEARCH FOR THE MEMBER ID TO WHOM BOOK IS TO BE ISSUED */

            File Fforg = new File("data/library_books.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
            
            File Fbookstmp = new File("data/library_books_tmp.dat");
            FileWriter Ffw = new FileWriter(Fbookstmp, false);
            BufferedWriter Fbw = new BufferedWriter(Ffw);
            PrintWriter FoutFile = new PrintWriter(Fbw);

            File Fbi = new File("data/book_issue.dat");
            FileWriter Ffwbi = new FileWriter(Fbi, true);
            BufferedWriter Fbwbi = new BufferedWriter(Ffwbi);
            PrintWriter Fpwbi = new PrintWriter(Fbwbi);

            String Wtext;
            while((Wtext = Fbr.readLine()) != null)
            {
                Wobj = new Scanner(Wtext);
                Wobj.useDelimiter(",");
                String Wbook_data[] = new String[9]; 
                byte Windx = 0;
                while(Wobj.hasNext())
                {
                    Wbook_data[Windx++] = Wobj.next();
                }

                for (int Wi = 0; Wi < Wbook_data.length; Wi++)
                {
                    String Witem = Wbook_data[Wi];
                    if(Wi == 0 && Witem.equals(AccNo))
                    {
                        AccNoFound = true;
                        bookFound = true;
                    }
                }
                if(AccNoFound)
                {
                    String book_status = Wbook_data[8];
                    if(book_status.equals("O"))
                    {
                        Fpwbi.println(Wbook_data[0] + "," + MemID + "," + Wbook_data[5] + "," + Wbook_data[4] + "," + Wbook_data[3]);
                        FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + "I");
                        bookIssued = true;
                    }
                    else if(book_status.equals("I"))
                    {
                        FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + Wbook_data[8]);
                        System.out.println();
                        System.out.println("Book is already under issue.");
                    }
                    AccNoFound = false;
                }
                else
                {
                    FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + Wbook_data[8]);
                }
            }


            Fbwbi.close();
            Ffwbi.close();
            Fpwbi.close();

            Fbr.close();
            Fforg.delete();
            Fbw.close();
            Ffw.close();
            Fbookstmp.renameTo(Fforg);
            FoutFile.close();
            FfileReader.close();
            
            if(bookFound==false)
            {
                System.out.println();
                System.out.println("Requested Accession No. doesn't exist!");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            return bookIssued;
        }
    }

    public byte noOfBooksWithMember(String MemID)
    {
        byte noOfBooksIssued = 0;
        try
        {
            File Fforg = new File("data/book_issue.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
            Scanner Lobj;
            String Ltext;

            while((Ltext = Fbr.readLine()) != null)
            {
                Lobj = new Scanner(Ltext);
                Lobj.useDelimiter(",");
                String Lissue_data[] = new String[5]; 
                byte Lindx = 0;
                while(Lobj.hasNext())
                {
                    Lissue_data[Lindx++] = Lobj.next();
                }
                if(Lissue_data[1].equals(MemID))
                {
                    noOfBooksIssued++;
                    if(noOfBooksIssued > 1)
                        break;
                }
            }

            Fbr.close();
            FfileReader.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            return noOfBooksIssued;
        }
    }
    
    public void bookReturn()
    {
        Scanner Lsobj;
        String accno;
        boolean AccNoFound = false;
        boolean bookFound = false;
        boolean bookInIssue = false;
        try
        {
            Lsobj = new Scanner(System.in);
            System.out.println();
            System.out.println("BOOK RETURN");
            System.out.println();
            System.out.println("Enter Accession No. of returned book: ");
            System.out.print("> ");
            accno = Lsobj.next().toUpperCase();
            /* UPDATE THE library_books.dat FILE */
            File Fforg = new File("data/library_books.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
            
            File Fbookstmp = new File("data/library_books_tmp.dat");
            FileWriter Ffw = new FileWriter(Fbookstmp, false);
            BufferedWriter Fbw = new BufferedWriter(Ffw);
            PrintWriter FoutFile = new PrintWriter(Fbw);
            
            String Wtext;
            while((Wtext = Fbr.readLine()) != null)
            {
                Lsobj = new Scanner(Wtext);
                Lsobj.useDelimiter(",");
                String Wbook_data[] = new String[9]; 
                byte Windx = 0;
                while(Lsobj.hasNext())
                {
                    Wbook_data[Windx++] = Lsobj.next();
                }

                for (int Wi = 0; Wi < Wbook_data.length; Wi++)
                {
                    String Witem = Wbook_data[Wi];
                    if(Wi == 0 && Witem.equals(accno))
                    {
                        AccNoFound = true;
                        bookFound = true;
                    }
                }
                if(AccNoFound)
                {
                    String book_status = Wbook_data[8];
                    if(book_status.equals("I"))
                    {
                        bookInIssue = true;
                        FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + "O");
                    }
                    else
                    {
                        FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + Wbook_data[8]);
                    }
                    AccNoFound = false;
                }
                else
                {
                    FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + Wbook_data[8]);
                }
            }
            
            Fbr.close();
            Fforg.delete();
            Fbw.close();
            Ffw.close();
            Fbookstmp.renameTo(Fforg);
            FoutFile.close();
            FfileReader.close();
            
            if(bookFound==false)
            {
                System.out.println();
                throw new Exception("Requested Accession No. doesn't exist!");
            }
            else if(bookInIssue == false)
            {
                System.out.println();
                throw new Exception("Requested Accession No. is not in issue!");
            }
            
            /* UPDATING THE book_issue.dat FILE */
            File FforgBI = new File("data/book_issue.dat");
            FileReader FfileReaderBI = new FileReader(FforgBI);
            BufferedReader FbrBI = new BufferedReader(FfileReaderBI);
            
            File FbookstmpBI = new File("data/book_issue_tmp.dat");
            FileWriter FfwBI = new FileWriter(FbookstmpBI, false);
            BufferedWriter FbwBI = new BufferedWriter(FfwBI);
            PrintWriter FoutFileBI = new PrintWriter(FbwBI);
            
            while((Wtext = FbrBI.readLine()) != null)
            {
                Lsobj = new Scanner(Wtext);
                Lsobj.useDelimiter(",");
                String WBI_data[] = new String[5]; 
                byte Windx = 0;
                while(Lsobj.hasNext())
                {
                    WBI_data[Windx++] = Lsobj.next();
                }

                for (int Wi = 0; Wi < WBI_data.length; Wi++)
                {
                    String Witem = WBI_data[Wi];
                    if(Wi == 0 && Witem.equals(accno))
                    {
                        AccNoFound = true;
                    }
                }
                if(AccNoFound == false)
                {
                    FoutFileBI.println(WBI_data[0] + "," + WBI_data[1] + "," + WBI_data[2] + "," + WBI_data[3] + "," + WBI_data[4]);
                }
                else
                    AccNoFound = false;

            }
            
            FbrBI.close();
            FforgBI.delete();
            FbwBI.close();
            FfwBI.close();
            FbookstmpBI.renameTo(FforgBI);
            FoutFileBI.close();
            FfileReaderBI.close();
            System.out.println();
            System.out.println("Book receipt successful!");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void displayIssuedBooks()
    {
        try
        {
            System.out.println("\nDISPLAYING LIST OF BOOKS IN ISSUE\n");
            System.out.printf("%8s%35s%20s%20s%12s%20s \n", "Acc. No.", "Book Name", "Author", "Subject", "Member ID", "Member Name");
            System.out.printf("%8s%35s%20s%20s%12s%20s \n",  "--------", "---------", "------", "-------", "---------", "-----------");
            
            File Fforg = new File("data/book_issue.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
            Scanner Wobj;
            String Wtext;
            while((Wtext = Fbr.readLine()) != null)
            {
                Wobj = new Scanner(Wtext);
                Wobj.useDelimiter(",");
                String Wbook_data[] = new String[5]; 
                byte Windx = 0;
                while(Wobj.hasNext())
                {
                    Wbook_data[Windx++] = Wobj.next();
                }

                System.out.printf("%8s%35s%20s%20s%12s%20s \n", Wbook_data[0], Wbook_data[2], Wbook_data[3], Wbook_data[4], Wbook_data[1], new LibraryMembers().getMemberName(Wbook_data[1]));
            }
            Fbr.close();
            FfileReader.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public void displayLibraryBooks()
    {
        try
        {
            Scanner Lsobj = new Scanner(System.in);
            String Lchoice;
            System.out.println();
            System.out.println("DISPLAY LIBRARY ITEMS");
            System.out.println();
            System.out.println("Choose type of books to display:");
            System.out.println("A-All Items \t I-Issued Items");
            System.out.print("> ");
            Lchoice = Lsobj.next().toUpperCase();
            if(!(Lchoice.equals("A") || Lchoice.equals("I")))
                throw new Exception("ERROR: Invalid choice for display!");
            setBookSearchCriteria();
            char status = Lchoice.charAt(0);
            searchAndDisplayByCriteria(Cindx,Ccriteria,status);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void deleteLibraryBook()
    {
        try
        {
            System.out.println();
            System.out.println("DELETE LIBRARY BOOK");
            Scanner Lin = new Scanner(System.in);
            String LAccNo;
            boolean LbookFound = false;
            boolean LbookExists = false;
            boolean LbookDeleted = false;
            
            System.out.println();
            System.out.println("Enter Accession No.: ");
            System.out.print("> ");
            LAccNo = Lin.nextLine().toUpperCase();
            
            System.out.println();
            System.out.println("Acc. No.: " + LAccNo + " will be deleted! Are you sure? (y/n): ");
            System.out.print("> ");
            String Ldelchoice = Lin.nextLine().toUpperCase();
            
            if(Ldelchoice.equals("Y"))
            {
                File Fforg = new File("data/library_books.dat");
                FileReader FfileReader = new FileReader(Fforg);
                BufferedReader Fbr = new BufferedReader(FfileReader);
                
                File Fbookstmp = new File("data/library_books_tmp.dat");
                FileWriter Ffw = new FileWriter(Fbookstmp, false);/////////////false?//
                BufferedWriter Fbw = new BufferedWriter(Ffw);
                PrintWriter FoutFile = new PrintWriter(Fbw);
                
        
                String Wtext;
                while((Wtext = Fbr.readLine()) != null)
                {
                    Lin = new Scanner(Wtext);
                    Lin.useDelimiter(",");
                    String Wbook_data[] = new String[9]; 
                    byte Windx = 0;
                    while(Lin.hasNext())
                    {
                        Wbook_data[Windx++] = Lin.next();
                    }

                    for (int Wi = 0; Wi < Wbook_data.length; Wi++)
                    {
                        String Witem = Wbook_data[Wi];
                        if(Wi == 0 && Witem.equals(LAccNo))
                        {
                            LbookFound = true;
                            LbookExists = true;
                        }
                    }
                    if(LbookFound)
                    {   
                        if(Wbook_data[8].equals("I"))
                        {
                            FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + Wbook_data[8]);
                            System.out.println("Requested book is in issue. Can't be deleted!");
                        }
                        else
                        {
                            LbookDeleted = true;
                        }
                        LbookFound = false;
                    }
                    else
                    {
                        FoutFile.println(Wbook_data[0] + "," + Wbook_data[1] + "," + Wbook_data[2] + "," + Wbook_data[3] + "," + Wbook_data[4] + "," + Wbook_data[5] + "," + Wbook_data[6] + "," + Wbook_data[7] + "," + Wbook_data[8]);
                    }
                }
                
                Fbr.close();
                Fforg.delete();
                Fbw.close();
                Ffw.close();
                Fbookstmp.renameTo(Fforg);
                FoutFile.close();
                FfileReader.close();
                
                if(LbookDeleted == true)
                    System.out.println("Book entry deleted successfully!");
                if(LbookExists == false)
                    System.out.println("ERROR: Requested Acc. No. doesn't exists!");
            }
            else
            {
                System.out.println("Ok!... Aborting delete book entry.");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
