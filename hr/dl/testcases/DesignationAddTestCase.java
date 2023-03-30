import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationAddTestCase
{
	public static void main(String args[])
	{
		String title = args[0];
		try
		{
			DesignationDTOInterface designationDTO;
			designationDTO = new DesignationDTO();
			designationDTO.setTitle(title);
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			designationDAO.add(designationDTO);
			System.out.println("Designation : "+title+" added with code : "+designationDTO.getCode());
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}