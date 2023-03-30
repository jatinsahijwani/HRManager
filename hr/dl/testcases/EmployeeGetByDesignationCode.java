import com.jatin.sahijwani.hr.dl.excpetions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
import java.util.*;
import java.text.*;
public class EmployeeGetByDesignationCodeTestCase
{
	public static void main(String args[])
	{
		int designationCode = Integer.parseInt(args[0]);
		try
		{
			EmployeeDAOInterface employeeDAO;
			employeeDAO = new EmployeeDAO();
			Set<EmployeeDAOInterface> employees;
			employees=employeeDAO.getByDesignationCode(designationCode);
			System.out.println("----------------------------------------------------------------------------");
			for(EmployeeDTOInterface employeeDTO : employees)
			{
				System.out.println("Employee Id : " + employeeDTO.getEmployeeId());
				System.out.println("Name : " + employeeDTO.getName());
				System.out.println("Salary : " + employeeDTO.getBasicSalary().toPlainString());
				System.out.println("Is Indian : " + employeeDTO.getIsIndian());
				System.out.println("Gender : " + employeeDTO.getGender());
				System.out.println("Aadhar Card : " + employeeDTO.getAadharCardNumber());
				System.out.println("Pan Card Number : " + employeeDTO.getPANNumber());
				System.out.println("Designatino Code : " + employeeDTO.getDesignationCode());
				System.out.println("Date of birth : " + simpleDateFormat.format(employeeDTO.getDateOfBirth()));
				System.out.println("----------------------------------------------------------------------------");
			}			
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}