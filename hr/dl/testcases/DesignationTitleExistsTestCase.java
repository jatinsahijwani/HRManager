import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationTitleExistsTestCase
{
	public static void main(String args[])
	{
		String title = args[0];
		try
		{
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			if(designationDAO.titleExists(title)==true) System.out.println("title exists");
			else System.out.println("title does not exists");			
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}