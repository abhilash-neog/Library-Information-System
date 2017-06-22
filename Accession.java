import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

class Accession
{

    public void addLibraryItem()
    {
        String Laccno;
        String LAccDate;
        String LType;
        String LSubject;
        String LAuthor;
        String LTitle;
        String LPublisher;
        String LISBN;
        String LStatus;
        Scanner Lin = new Scanner(System.in);
        try
        {
            FileWriter Ffw = new FileWriter("data/library_books.dat", true);
            BufferedWriter Fbw = new BufferedWriter(Ffw);
            PrintWriter FoutFile = new PrintWriter(Fbw);

            Laccno = generateAccessionNo();
            System.out.println();
            System.out.println("ACCESSION NO.: " + Laccno);
            System.out.println("Enter accession date: ");
            System.out.print("> ");
            LAccDate = Lin.nextLine();
            System.out.println();
            System.out.println("Choose Accession Type:");
            System.out.println("B-Book \t J-Journal");
            System.out.print("> ");
            LType = Lin .nextLine().toUpperCase();
            
            if(!(LType.equals("B") || LType.equals("J")))
                throw new Exception("Invalid type choosen!");
            
            String Str = "";
            if(LType.equals("B"))
                Str = "book";
            if(LType.equals("J"))
                Str = "journal";
                
            System.out.println();
            System.out.println("Enter " + Str + " subject:");
            System.out.print("> ");
            LSubject = Lin.nextLine();

            System.out.println();
            System.out.println("Enter author name:");
            System.out.print("> ");
            LAuthor = Lin.nextLine();
            System.out.println();
            System.out.println("Enter " + Str + " title:");
            System.out.print("> ");
            LTitle = Lin.nextLine();
            System.out.println();
            System.out.println("Choose publisher name:");
            System.out.println("S-S.Chand & Co. Ltd. \t A-Ane Books India \t L-Laxmi Publications Pvt Ltd");
            System.out.println("T-National Book Trust. \t G-Gyan Books (P) Ltd. \t E-Eastern Book Company");
            System.out.println("P-Assam Publications \t B-Goyal Brothers Prakashans");
            System.out.println("N-Not Specified");
            System.out.print("> ");
            LPublisher = Lin.nextLine().toUpperCase();

            if(!(LPublisher.equals("S") || LPublisher.equals("P") || LPublisher.equals("G")|| LPublisher.equals("B") || LPublisher.equals("A") || LPublisher.equals("L") || LPublisher.equals("T") || LPublisher.equals("E") || LPublisher.equals("N")))
                throw new Exception("Invalid publisher name!");         

            System.out.println();
            System.out.print("Enter ISBN no.:");
            System.out.print("> ");
            LISBN = Lin.nextLine();
            LStatus = "O";              // LStatus (O - on shelf, C - Circulated, R - Removed from library)

            FoutFile.println(Laccno + "," + LAccDate + "," + LType + "," + LSubject.toUpperCase() + "," + LAuthor.toUpperCase() + "," + LTitle.toUpperCase() + "," + LPublisher.toUpperCase() + "," + LISBN.toUpperCase() + "," + LStatus);
            
            FoutFile.close();
            Fbw.close();
            Ffw.close();
            System.out.println("ACCESSION DONE SUCCESSFULLY!");
        }
        catch(IOException e)
        {
            System.out.println("ERROR : " + e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("ERROR : " + e.toString());
        }
    }

    private static String generateAccessionNo()
    {
        long Wgreatest_accno = 0;
        Scanner Wobj;
        try
        {
            File Fforg = new File("data/library_books.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);

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
                    if(Wi == 0)
                    {
                        String Wnum_part_accno = Witem.substring(3);
                        try
                        {
                            long Wlong_num_part_accno = Long.parseLong(Wnum_part_accno.trim());
                            if(Wlong_num_part_accno > Wgreatest_accno)
                                Wgreatest_accno = Wlong_num_part_accno;
                        }
                        catch(NumberFormatException nfe)
                        {
                            System.out.println("ERROR: " + nfe.getMessage());
                        }
                    }
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
            return "ACC" + String.valueOf(Wgreatest_accno + 1);
        }
    }
}
