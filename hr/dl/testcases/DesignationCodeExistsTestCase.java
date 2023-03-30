import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationCodeExistsTestCase
{
	public static void main(String args[])
	{
		int code = Integer.parseInt(args[0]);
		try
		{
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			if(designationDAO.codeExists(code)==true) System.out.println("Code exists");
			else System.out.println("Code does not exists");			
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}