import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.dao.*;
import com.jatin.sahijwani.hr.dl.dto.*;
public class DesignationUpdateTestCase
{
	public static void main(String args[])
	{
		String title = args[0];
		int code = Integer.parseInt(args[1]);
		try
		{
			DesignationDTOInterface designationDTO;
			designationDTO = new DesignationDTO();
			designationDTO.setTitle(title);
			designationDTO.setCode(code);
			DesignationDAOInterface designationDAO;
			designationDAO = new DesignationDAO();
			designationDAO.update(designationDTO);
		}catch(DAOException daoe)
		{
			System.out.println(daoe.getMessage());
		}
	}
}