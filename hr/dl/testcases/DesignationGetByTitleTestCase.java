import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationGetByTitleTestCase
{
	public static void main(String args[])
	{
		String title = args[0];
		try
		{
			DesignationDTOInterface designationDTO;
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			designationDTO = designationDAO.getByTitle(title);
			System.out.printf("Code : %d, Title : %s\n",designationDTO.getCode(),designationDTO.getTitle());
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}