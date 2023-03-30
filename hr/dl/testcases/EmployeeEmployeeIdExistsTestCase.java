import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
import com.jatin.sahijwani.enums.*;
import java.util.*;
import java.text.*;
public class EmployeeEmployeeIdExistsTestCase
{
	public static void main(String args[])
	{
		String employeeId = args[0];
		try
		{
			EmployeeDAOInterface employeeDAO;
			employeeDAO = new EmployeeDAO();
			boolean x = employeeDAO.employeeIdExists(employeeId);
				System.out.println(x);

		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}