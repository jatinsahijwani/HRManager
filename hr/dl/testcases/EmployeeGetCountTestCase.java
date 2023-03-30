import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
import java.util.*;
import java.text.*;
public class EmployeeGetCountTestCase
{
	public static void main(String args[])
	{
		try
		{
			EmployeeDAOInterface employeeDAO;
			employeeDAO = new EmployeeDAO();
			int x = employeeDAO.getCount();
			System.out.println("Number of employees : " + x);
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}