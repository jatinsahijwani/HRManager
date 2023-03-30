package com.jatin.sahijwani.hr.dl.interfaces.dao;
import java.util.*;
import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
public interface EmployeeDAOInterface
{
	public void add(EmployeeDTOInterface employeeDTO) throws DAOException;
	public void update(EmployeeDTOInterface employeeDTO) throws DAOException;
	public void delete(String EmployeeId) throws DAOException;
	public Set<EmployeeDTOInterface> getAll() throws DAOException;
	public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException;
	public boolean isDesignationAlotted(int designationCode) throws DAOException;
	public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException;
	public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException;
	public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException;
	public boolean employeeIdExists(String employeeId) throws DAOException;
	public boolean panNumberExists(String panNumber) throws DAOException;
	public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException;
	public int getCount() throws DAOException;
	public int getCountByDesignationCode(int designationCode) throws DAOException;
}