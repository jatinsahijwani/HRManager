import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationGetCountTestCase
{
	public static void main(String args[])
	{
		try
		{
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			System.out.println(designationDAO.getCount());
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}