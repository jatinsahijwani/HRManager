import java.util.*;
import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationGetAllTestCase
{
	public static void main(String args[])
	{
		try
		{
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			Set<DesignationDTOInterface> set =  designationDAO.getAll();
			set.forEach( (k) -> {System.out.printf("Code : %d, Title : %s\n",k.getCode(),k.getTitle());} );
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}