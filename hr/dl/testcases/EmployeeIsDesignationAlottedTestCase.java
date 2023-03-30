import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
import java.util.*;
import java.text.*;
public class EmployeeIsDesignationAlottedTestCase
{
	public static void main(String args[])
	{
		int designationCode = Integer.parseInt(args[0]);
		try
		{
			EmployeeDAOInterface employeeDAO;
			employeeDAO = new EmployeeDAO();
			boolean x = employeeDAO.isDesignationAlotted(designationCode);
			System.out.println("Allocation status of designation code : " + x);
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}