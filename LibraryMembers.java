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

class LibraryMembers
{
	public void addLibraryMember()
	{
		long LMemberID;
		String LEnrollDate;
		String LMemberType;
		String LName;
		String LClass="";
		String LRollNo="";
		String LDesignation="";
		String LDepartment="";

		Scanner Lin = new Scanner(System.in);
		try
        {
			FileWriter Ffw = new FileWriter("data/library_members.dat", true);
            BufferedWriter Fbw = new BufferedWriter(Ffw);
            PrintWriter FoutFile = new PrintWriter(Fbw);
		
			LMemberID = generateEnrollID();
			System.out.println();
			System.out.println("Member ID: " + LMemberID);
            System.out.println("Enter membership start date: ");
			System.out.print("> ");
			LEnrollDate = Lin.nextLine();
			System.out.println();
			System.out.println("Choose member type:");
			System.out.println("S-Student \t D-Staff ");
			System.out.print("> ");
			LMemberType = Lin.nextLine().toUpperCase();
			
			if(!(LMemberType.equals("S") || LMemberType.equals("D") ))
				throw new Exception("Invalid member type choosen!");

			System.out.println();
			System.out.println("Enter member name: ");
			System.out.print("> ");
			LName = Lin.nextLine();
			
			if(LMemberType.equals("S"))
			{
				System.out.println();
				System.out.println("Enter class of student: ");
				System.out.print("> ");
				LClass = Lin.nextLine();

				System.out.println();
				System.out.println("Enter roll no. of student: ");
				System.out.print("> ");
				LRollNo = Lin.nextLine();
			}
			else
			{
				System.out.println();
				System.out.println("Enter designation of staff: ");
				System.out.print("> ");
				LDesignation = Lin.nextLine();

				System.out.println();
				System.out.println("Enter department of staff: ");
				System.out.print("> ");
				LDepartment = Lin.nextLine();
			}
			
			if(LMemberType.equals("S"))
				FoutFile.println(LMemberID + "," + LEnrollDate + "," + LMemberType + "," + LName + "," + LClass + "," + LRollNo);	
			else
				FoutFile.println(LMemberID + "," + LEnrollDate + "," + LMemberType + "," + LName + "," + LDesignation + "," + LDepartment);	

			FoutFile.close();
            Fbw.close();
            Ffw.close();
            System.out.println("NEW LIBRARY MEMBER ADDED SUCCESSFULLY!");
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

	public void displayLibraryMembers()
    {
        FileReader FfileReader;
	    BufferedReader Fbr;
        Scanner Lin = new Scanner(System.in);
		Scanner Lobj;
		String Ltext;

        try
        {
            File Ff = new File("data/library_members.dat");
            FfileReader = new FileReader(Ff);
            Fbr = new BufferedReader(FfileReader);
			System.out.println();	
			System.out.println("Choose type of members to display:");
			System.out.println("S-Students \t D-Staff");	
			System.out.print("> ");
			String Ltype = Lin.nextLine().toUpperCase();
			if(!(Ltype.equals("S") || Ltype.equals("D") || Ltype.equals("A")))
				throw new Exception("Invalid member type choosen!");
	
            if (Ff.isFile())
            {
                
				if(Ltype.equals("S"))
				{
					System.out.println("\nDISPLAYING LIST OF MEMBER STUDENTS\n");
                	System.out.printf("%4s%15s%30s%20s%12s\n", "ID", "Enroll Date", "Name","Class", "Roll No");
                	System.out.printf("%4s%15s%30s%20s%12s\n", "--", "-----------", "----","-----", "-------");
				}
				else if(Ltype.equals("D"))
				{
					System.out.println("\nDISPLAYING LIST OF MEMBER STAFF\n");
                	System.out.printf("%4s%15s%30s%20s%12s\n", "ID", "Enroll Date", "Name","Designation", "Department");
                	System.out.printf("%4s%15s%30s%20s%12s\n", "--", "-----------", "----","-----------", "----------");
				}
                while((Ltext = Fbr.readLine()) != null)
                {
                    Lobj = new Scanner(Ltext);
					Lobj.useDelimiter(",");
					String Lmember_data[] = new String[6]; 
					byte Lindx = 0;
					while(Lobj.hasNext())
					{
						Lmember_data[Lindx++] = Lobj.next();
					}

					if(Lmember_data[2].equals(Ltype))
					{
						System.out.printf("%4s%15s%30s%20s%12s\n",Lmember_data[0],Lmember_data[1],Lmember_data[3],Lmember_data[4],Lmember_data[5]);
					}
                }
                System.out.println("\n");

                Fbr.close();
                FfileReader.close();
            }
            else
                System.out.println("NO LIBRARY MEMBERS FOUND IN THE MEMBERS REGISTER");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("ERROR: No library members exist!");
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR: " + ioe.toString());
        }
        catch(Exception e)
        {
            System.out.println("ERROR: " + e.toString());
        }
    }

	private static long generateEnrollID()
    {
        long Wgreatest_EnrollID = 0;
		Scanner Wobj;
        try
        {
            File Fforg = new File("data/library_members.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);

            String Wtext;
            while((Wtext = Fbr.readLine()) != null)
            {
				Wobj = new Scanner(Wtext);
				Wobj.useDelimiter(",");
				String Wmember_data[] = new String[6]; 
				byte Windx = 0;
				while(Wobj.hasNext())
				{
					Wmember_data[Windx++] = Wobj.next();
				}

				for (int Wi = 0; Wi < Wmember_data.length; Wi++)
                {
                    String Witem = Wmember_data[Wi];
                    if(Wi == 0)
                    {
                        
                        try
                        {
                            long WEnrollID = Long.parseLong(Witem);
                            if(WEnrollID > Wgreatest_EnrollID)
                                Wgreatest_EnrollID = WEnrollID;
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
            return Wgreatest_EnrollID + 1;
        }
    }

	public boolean memberExists(String MemID)
	{
		boolean memIDExists = false;
		try
		{
			File Fforg = new File("data/library_members.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
			Scanner Lobj;
			String Ltext;

            while((Ltext = Fbr.readLine()) != null)
            {
				Lobj = new Scanner(Ltext);
				Lobj.useDelimiter(",");
				String Lmember_data[] = new String[6]; 
				byte Lindx = 0;
				while(Lobj.hasNext())
				{
					Lmember_data[Lindx++] = Lobj.next();
				}
				if(Lmember_data[0].equals(MemID))
				{
					memIDExists = true;
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
			return memIDExists;
		}
	}

	public String getMemberName(String MemID)
	{
		String mem_name = "";
		
		try
		{
			File Fforg = new File("data/library_members.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
			Scanner Lobj;
			String Ltext;

            while((Ltext = Fbr.readLine()) != null)
            {
				Lobj = new Scanner(Ltext);
				Lobj.useDelimiter(",");
				String Lmember_data[] = new String[6]; 
				byte Lindx = 0;
				while(Lobj.hasNext())
				{
					Lmember_data[Lindx++] = Lobj.next();
				}
				if(Lmember_data[0].equals(MemID))
				{
					mem_name = Lmember_data[3];
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
			return mem_name;
		}
	}
	
	public String getMemberType(String MemID)
	{
		String mem_type = "";
		try
		{
			File Fforg = new File("data/library_members.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
			Scanner Lobj;
			String Ltext;

            while((Ltext = Fbr.readLine()) != null)
            {
				Lobj = new Scanner(Ltext);
				Lobj.useDelimiter(",");
				String Lmember_data[] = new String[6]; 
				byte Lindx = 0;
				while(Lobj.hasNext())
				{
					Lmember_data[Lindx++] = Lobj.next();
				}
				if(Lmember_data[0].equals(MemID))
				{
					mem_type = Lmember_data[2];
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
			if(mem_type.equals("S"))
				mem_type = "Student";
			else
				mem_type = "Staff";
				
			return mem_type;
		}
	}
	
	public String getClassOrDesig(String MemID)
	{
		String class_or_desig = "";
		try
		{
			File Fforg = new File("data/library_members.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
			Scanner Lobj;
			String Ltext;

            while((Ltext = Fbr.readLine()) != null)
            {
				Lobj = new Scanner(Ltext);
				Lobj.useDelimiter(",");
				String Lmember_data[] = new String[6]; 
				byte Lindx = 0;
				while(Lobj.hasNext())
				{
					Lmember_data[Lindx++] = Lobj.next();
				}
				if(Lmember_data[0].equals(MemID))
				{
					class_or_desig = Lmember_data[4];
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
			return class_or_desig;
		}
	}
	
	public String getRollNoOrDept(String MemID)
	{
		String roll_or_dept = "";
		try
		{
			File Fforg = new File("data/library_members.dat");
            FileReader FfileReader = new FileReader(Fforg);
            BufferedReader Fbr = new BufferedReader(FfileReader);
			Scanner Lobj;
			String Ltext;

            while((Ltext = Fbr.readLine()) != null)
            {
				Lobj = new Scanner(Ltext);
				Lobj.useDelimiter(",");
				String Lmember_data[] = new String[6]; 
				byte Lindx = 0;
				while(Lobj.hasNext())
				{
					Lmember_data[Lindx++] = Lobj.next();
				}
				if(Lmember_data[0].equals(MemID))
				{
					roll_or_dept = Lmember_data[5];
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
			return roll_or_dept;
		}
	}
	
	public void deleteLibraryMember()
	{
		try
		{
			System.out.println();
			System.out.println("DELETE LIBRARY MEMBER");
			Scanner Lin = new Scanner(System.in);
			String LMemberID;
			boolean LmemberFound = false;
			boolean LmemberExists = false;
			
			System.out.println();
			System.out.println("Enter Member ID: ");
			System.out.print("> ");
			LMemberID = Lin.nextLine();
			
			/* First check whether there are any books in issue against this member */
			byte LnoOfBooks = new IssueReceipt().noOfBooksWithMember(LMemberID);
			if(LnoOfBooks > 0)
				throw new Exception("ERROR: Can't delete member. " + LnoOfBooks + " no(s). of book(s) in issue to this member!"); 
			
			System.out.println();
			System.out.println("Member ID: " + LMemberID + " will be deleted! Are you sure? (y/n): ");
			System.out.print("> ");
			String Ldelchoice = Lin.nextLine().toUpperCase();
			
			if(Ldelchoice.equals("Y"))
			{
				File Fforg = new File("data/library_members.dat");
				FileReader FfileReader = new FileReader(Fforg);
				BufferedReader Fbr = new BufferedReader(FfileReader);
				
				File Fmemberstmp = new File("data/library_members_tmp.dat");
				FileWriter Ffw = new FileWriter(Fmemberstmp, false);////////////////////////////////////////////////////////////
				BufferedWriter Fbw = new BufferedWriter(Ffw);
				PrintWriter FoutFile = new PrintWriter(Fbw);
				
		
				String Wtext;
				while((Wtext = Fbr.readLine()) != null)
				{
					Lin = new Scanner(Wtext);
					Lin.useDelimiter(",");
					String Wmember_data[] = new String[6]; 
					byte Windx = 0;
					while(Lin.hasNext())
					{
						Wmember_data[Windx++] = Lin.next();
					}

					for (int Wi = 0; Wi < Wmember_data.length; Wi++)
					{
						String Witem = Wmember_data[Wi];
						if(Wi == 0 && Witem.equals(LMemberID))
						{
							LmemberFound = true;
							LmemberExists = true;
						}
					}
					if(!LmemberFound)
					{					
						FoutFile.println(Wmember_data[0] + "," + Wmember_data[1] + "," + Wmember_data[2] + "," + Wmember_data[3] + "," + Wmember_data[4] + "," + Wmember_data[5]);
					}
					else
					{
						LmemberFound = false;
					}
				}
				
				Fbr.close();
				Fforg.delete();
				Fbw.close();
				Ffw.close();
				Fmemberstmp.renameTo(Fforg);
				FoutFile.close();
				FfileReader.close();
				
				if(LmemberExists == true)
					System.out.println("Member deleted successfully!");
				else
					System.out.println("Member ID: " + LMemberID + " doesn't exist!");
			}
			else
			{
				System.out.println("Ok!... Aborting delete member.");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
