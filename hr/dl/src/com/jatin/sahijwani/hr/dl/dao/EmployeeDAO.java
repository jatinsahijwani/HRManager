package com.jatin.sahijwani.hr.dl.dao;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
import com.jatin.sahijwani.hr.dl.exceptions.*;
import com.jatin.sahijwani.hr.dl.dto.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.enums.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
	private static final String FILE_NAME = "employee.data";
	public void add(EmployeeDTOInterface employeeDTO) throws DAOException
	{
		if(employeeDTO==null) throw new DAOException("Employee is null");
		String employeeId;
		String name = employeeDTO.getName();
		if(name==null) throw new DAOException("Employee is null");
		name=name.trim();
		if(name.length()==0) throw new DAOException("Length of name is 0");
		int designationCode = employeeDTO.getDesignationCode();
		if(designationCode<=0) throw new DAOException("Invalid Designation Code : "+designationCode);
		DesignationDAOInterface designationDAO = new DesignationDAO();
		boolean designationExists = false;
		designationExists = designationDAO.codeExists(designationCode);
		if(designationExists==false) throw new DAOException("Invalid designation code : "+designationCode);
		Date dateOfBirth = employeeDTO.getDateOfBirth();
		if(dateOfBirth==null) throw new DAOException("Date of birth is null");
		char gender = employeeDTO.getGender();
		if(gender==' ') throw new DAOException("Gender not set to male/female");
		boolean isIndian = employeeDTO.getIsIndian();
		BigDecimal basicSalary = employeeDTO.getBasicSalary();
		if(basicSalary==null) throw new DAOException("Basic Salary is null");
		if(basicSalary.signum()==-1) throw new DAOException("Basic Salary cannot be negative");		
		String panNumber = employeeDTO.getPANNumber();
		if(panNumber==null) throw new DAOException("Pan number is null");
		panNumber = panNumber.trim();
		if(panNumber.length()==0) throw new DAOException("Lenght of pan number is 0");
		String aadharCardNumber = employeeDTO.getAadharCardNumber();
		if(aadharCardNumber==null) throw new DAOException("aadhar card number is null");
		aadharCardNumber=aadharCardNumber.trim();
		if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is 0");
		try
		{
			int lastGeneratedEmployeeId=10000000;
			String lastGeneratedEmployeeIdString="";
			int recordCount=0;
			String recordCountString="";
			File file = new File(FILE_NAME);
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0)
			{
				lastGeneratedEmployeeIdString = "10000000";
				lastGeneratedEmployeeIdString = String.format("%-10s",lastGeneratedEmployeeIdString);
				randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
				recordCountString = String.format("%-10s","0");
				randomAccessFile.writeBytes(recordCountString+"\n");
			}
			else
			{
				lastGeneratedEmployeeId = Integer.parseInt(randomAccessFile.readLine().trim());
				recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			}
			boolean panNumberExists,aadharCardNumberExists;
			panNumberExists=false;
			aadharCardNumberExists=false;
			String fPanNumber = "";
			String faadharCardNumber;
			int x;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				for(x=1;x<=7;x++) randomAccessFile.readLine();
				fPanNumber = randomAccessFile.readLine();
				faadharCardNumber = randomAccessFile.readLine();
				if(panNumberExists==false && fPanNumber.equalsIgnoreCase(panNumber))
				{
					panNumberExists=true;
				}
				if(aadharCardNumberExists==false && faadharCardNumber.equalsIgnoreCase(aadharCardNumber))
				{
					aadharCardNumberExists = true;
				}
				if(aadharCardNumberExists == true && panNumberExists == true) break;
			}
			if(panNumberExists && aadharCardNumberExists) 
			{
				randomAccessFile.close();
				throw new DAOException("Pan Number as well as aadhar card number exists");
			}
			if(panNumberExists)
			{
				randomAccessFile.close();
				throw new DAOException("Pan Number already exists");	
			}
			if(aadharCardNumberExists) 
			{
				randomAccessFile.close();
				throw new DAOException("Aadhar card number already exists");
			}
			employeeId = "A"+String.format("%-10d",lastGeneratedEmployeeId+1);
			lastGeneratedEmployeeId++;
			recordCount++;
			employeeDTO.setEmployeeId(employeeId);
			randomAccessFile.writeBytes(employeeId+"\n");
			randomAccessFile.writeBytes(name+"\n");
			randomAccessFile.writeBytes(designationCode+"\n");
			SimpleDateFormat simpleDateFormat;
			simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
			randomAccessFile.writeBytes(gender+"\n");
			randomAccessFile.writeBytes(isIndian+"\n");
			randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
			randomAccessFile.writeBytes(panNumber+"\n");
			randomAccessFile.writeBytes(aadharCardNumber+"\n");
			randomAccessFile.seek(0);
			lastGeneratedEmployeeIdString = String.format("%-10d",lastGeneratedEmployeeId);
			recordCountString = String.format("%-10d",recordCount);
			randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
			randomAccessFile.writeBytes(recordCountString+"\n");
			randomAccessFile.close();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public void update(EmployeeDTOInterface employeeDTO) throws DAOException
	{
		if(employeeDTO==null) throw new DAOException("Employee is null");
		String employeeId = employeeDTO.getEmployeeId();
		if(employeeId==null) throw new DAOException("Employee id is null");
		if(employeeId.length()==0) throw new DAOException("Length of employee id is 0");
		String name = employeeDTO.getName();
		if(name==null) throw new DAOException("Employee is null");
		name=name.trim();
		if(name.length()==0) throw new DAOException("Length of name is 0");
		int designationCode = employeeDTO.getDesignationCode();
		if(designationCode<=0) throw new DAOException("Invalid Designation Code : "+designationCode);
		DesignationDAOInterface designationDAO = new DesignationDAO();
		boolean designationExists = false;
		designationExists = designationDAO.codeExists(designationCode);
		if(designationExists==false) throw new DAOException("Invalid designation code : "+designationCode);
		Date dateOfBirth = employeeDTO.getDateOfBirth();
		if(dateOfBirth==null) throw new DAOException("Date of birth is null");
		char gender = employeeDTO.getGender();
		if(gender==' ') throw new DAOException("Gender not set to male/female");
		boolean isIndian = employeeDTO.getIsIndian();
		BigDecimal basicSalary = employeeDTO.getBasicSalary();
		if(basicSalary==null) throw new DAOException("Basic Salary is null");
		if(basicSalary.signum()==-1) throw new DAOException("Basic Salary cannot be negative");		
		String panNumber = employeeDTO.getPANNumber();
		if(panNumber==null) throw new DAOException("Pan number is null");
		panNumber = panNumber.trim();
		if(panNumber.length()==0) throw new DAOException("Lenght of pan number is 0");
		String aadharCardNumber = employeeDTO.getAadharCardNumber();
		if(aadharCardNumber==null) throw new DAOException("aadhar card number is null");
		aadharCardNumber=aadharCardNumber.trim();
		if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is 0");
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid EmployeeId : " + employeeId);
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid EmployeId : " + employeeId);
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fEmployeeId;
			String fName;
			int fdesignationCode;
			Date fDateOfBirth;
			char fGender;
			boolean fIsIndian;
			BigDecimal fBasicSalary;
			String faadharCardNumber;
			String fPanNumber;
			int x;
			boolean employeeIdFound = false;
			boolean panNumberFound = false;
			boolean aadharCardNumberFound = false;
			String panNumberFoundAgainstEmployeeId="";
			String aadharCardNumberFoundAgainstEmployeeId="";
			long foundAt=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
				fEmployeeId = randomAccessFile.readLine();
				for(x=1;x<=6;x++) randomAccessFile.readLine();
				fPanNumber = randomAccessFile.readLine();
				faadharCardNumber = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				faadharCardNumber = faadharCardNumber.trim();
				fPanNumber = fPanNumber.trim();
				if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId)) employeeIdFound = true;
				if(panNumberFound==false && fPanNumber.equalsIgnoreCase(panNumber)) 
				{
					panNumberFound = true;
					panNumberFoundAgainstEmployeeId = fEmployeeId;
				}
				if(aadharCardNumberFound==false && faadharCardNumber.equalsIgnoreCase(aadharCardNumber))
				{
					aadharCardNumberFound = true;
					aadharCardNumberFoundAgainstEmployeeId = fEmployeeId;
				}
				if(panNumberFound && aadharCardNumberFound && employeeIdFound) break;
			}
			if(employeeIdFound==false)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid EmployeeId : " + employeeId);
			}
			boolean panNumberExists=false;
			if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
			{
				panNumberExists=true;
			}
			boolean aadharCardNumberExists=false;
			if(aadharCardNumberExists && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
			{
				aadharCardNumberExists=true;
			}
			if(panNumberExists && aadharCardNumberExists) 
			{
				randomAccessFile.close();
				throw new DAOException("Pan Number as well as aadhar card number exists");
			}
			if(panNumberExists)
			{
				randomAccessFile.close();
				throw new DAOException("Pan Number already exists");	
			}
			if(aadharCardNumberExists) 
			{
				randomAccessFile.close();
				throw new DAOException("Aadhar card number already exists");
			}
			randomAccessFile.seek(foundAt);
			for(x=1;x<=9;x++) randomAccessFile.readLine();
			File tmpFile = new File("tmp.tmp");
			if(tmpFile.exists()) tmpFile.delete();
			RandomAccessFile tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
			}
			randomAccessFile.seek(foundAt);
			randomAccessFile.writeBytes(employeeId+"\n");
			randomAccessFile.writeBytes(name+"\n");
			randomAccessFile.writeBytes(designationCode+"\n");
			randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
			randomAccessFile.writeBytes(gender+"\n");
			randomAccessFile.writeBytes(isIndian+"\n");
			randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
			randomAccessFile.writeBytes(panNumber+"\n");
			randomAccessFile.writeBytes(aadharCardNumber+"\n");
			tmpRandomAccessFile.seek(0);
			while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
			{
				randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
			}
			randomAccessFile.setLength(randomAccessFile.getFilePointer());  
			tmpRandomAccessFile.setLength(0);
			tmpRandomAccessFile.close();
			randomAccessFile.close();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public void delete(String employeeId) throws DAOException
	{
		if(employeeId==null) throw new DAOException("Employee id is null");
		if(employeeId.length()==0) throw new DAOException("Length of employee id is 0");
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid EmployeeId : " + employeeId);
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid EmployeId : " + employeeId);
			}
			randomAccessFile.readLine();
			int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fEmployeeId;
			int x;
			boolean employeeIdFound = false;
			long foundAt=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				foundAt=randomAccessFile.getFilePointer();
				fEmployeeId = randomAccessFile.readLine();
				for(x=1;x<=8;x++) randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				if(fEmployeeId.equalsIgnoreCase(employeeId)) 
				{
					employeeIdFound = true;
					break;
				}
			}
			if(employeeIdFound==false)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid EmployeeId : " + employeeId);
			}
			File tmpFile = new File("tmp.tmp");
			if(tmpFile.exists()) tmpFile.delete();
			RandomAccessFile tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
			}
			randomAccessFile.seek(foundAt);
			tmpRandomAccessFile.seek(0);
			while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
			{
				randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
			}
			randomAccessFile.setLength(randomAccessFile.getFilePointer());  
			randomAccessFile.seek(0);
			recordCount--;
			String recordCountString = String.format("%-10d",recordCount);
			randomAccessFile.readLine();
			randomAccessFile.writeBytes(recordCountString+"\n");
			tmpRandomAccessFile.setLength(0);
			tmpRandomAccessFile.close();
			randomAccessFile.close();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public Set<EmployeeDTOInterface> getAll() throws DAOException
	{
		Set<EmployeeDTOInterface> employees = new TreeSet<EmployeeDTOInterface>();
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return employees;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return employees;
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			EmployeeDTOInterface employeeDTO;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			char fGender;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				employeeDTO = new EmployeeDTO();
				employeeDTO.setEmployeeId(randomAccessFile.readLine());
				employeeDTO.setName(randomAccessFile.readLine());
				employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
				try
				{
					employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
				}catch(ParseException pe)
				{
					throw new DAOException(pe.getMessage());
				}
				fGender = randomAccessFile.readLine().charAt(0);
				if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
				else employeeDTO.setGender(GENDER.FEMALE);
				employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
				employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
				employeeDTO.setPANNumber(randomAccessFile.readLine());
				employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
				employees.add(employeeDTO);
			}
			randomAccessFile.close();
			return employees;
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
	{
		Set<EmployeeDTOInterface> employees = new TreeSet<EmployeeDTOInterface>();
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return employees;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return employees;
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			EmployeeDTOInterface employeeDTO;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fName,fEmployeeId;
			int fdesignationCode;
			char fGender;
			int x;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fName = randomAccessFile.readLine();
				fdesignationCode = Integer.parseInt(randomAccessFile.readLine());
				if(fdesignationCode != designationCode) 
				{
					for(x=1;x<=6;x++) randomAccessFile.readLine(); 
					continue;
				}
				employeeDTO = new EmployeeDTO();
				employeeDTO.setEmployeeId(fEmployeeId);
				employeeDTO.setName(fName);
				employeeDTO.setDesignationCode(fdesignationCode);
				try
				{
					employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
				}catch(ParseException pe)
				{
					throw new DAOException(pe.getMessage());
				}
				fGender = randomAccessFile.readLine().charAt(0);
				if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
				else employeeDTO.setGender(GENDER.FEMALE);
				employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
				employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
				employeeDTO.setPANNumber(randomAccessFile.readLine());
				employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
				employees.add(employeeDTO);
			}
			randomAccessFile.close();
			return employees;
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public boolean isDesignationAlotted(int designationCode) throws DAOException
	{
		Set<EmployeeDTOInterface> employees = new TreeSet<EmployeeDTOInterface>();
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return false;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return false;
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			EmployeeDTOInterface employeeDTO;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fName,fEmployeeId;
			int fdesignationCode;
			int x;
			char fGender;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fName = randomAccessFile.readLine();
				fdesignationCode = Integer.parseInt(randomAccessFile.readLine());
				if(fdesignationCode != designationCode) 
				{
					for(x=1;x<=6;x++) randomAccessFile.readLine(); 
					continue;
				}
				employeeDTO = new EmployeeDTO();
				employeeDTO.setEmployeeId(fEmployeeId);
				employeeDTO.setName(fName);
				employeeDTO.setDesignationCode(fdesignationCode);
				try
				{
					employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
				}catch(ParseException pe)
				{
					throw new DAOException(pe.getMessage());
				}
				fGender = randomAccessFile.readLine().charAt(0);
				if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
				else employeeDTO.setGender(GENDER.FEMALE);
				employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
				employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
				employeeDTO.setPANNumber(randomAccessFile.readLine());
				employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
				employees.add(employeeDTO);
			}
			randomAccessFile.close();
			return (employees.size()!=0);
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}	
	}
	public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
	{
		if(employeeId==null) throw new DAOException("Invalid Employee ID : " + 0);
		employeeId = employeeId.trim();
		if(employeeId.length()==0) throw new DAOException("Invalid Employee ID : " + "length of id is 0");
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid Employee ID : " + employeeId);
			EmployeeDTOInterface employeeDTO;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) throw new DAOException("Invalid Employee ID : " + employeeId);
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			String fEmployeeId;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			int x=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				if(fEmployeeId.equalsIgnoreCase(employeeId))
				{
					employeeDTO = new EmployeeDTO();
					employeeDTO.setEmployeeId(fEmployeeId);
					employeeDTO.setName(randomAccessFile.readLine());
					employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
					try
					{
						employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
					}catch(ParseException pe)
					{
						throw new DAOException(pe.getMessage());
					}
					employeeDTO.setGender(randomAccessFile.readLine().charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
					employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
					employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
					employeeDTO.setPANNumber(randomAccessFile.readLine());
					employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
					randomAccessFile.close();
					return employeeDTO;
				}
				for(x=1;x<=8;x++) randomAccessFile.readLine();
			}
			randomAccessFile.close();
			throw new DAOException("Invalid Employee ID : " + employeeId);
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
	{
		if(panNumber==null) throw new DAOException("Invalid Pan Card Number : " + panNumber);
		panNumber = panNumber.trim();
		if(panNumber.length()==0) throw new DAOException("Invalid Pan Card Number : " + panNumber);
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid Pan Card Number : " + panNumber);
			EmployeeDTOInterface employeeDTO;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) throw new DAOException("Invalid Pan Card Number : " + panNumber);
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			String fEmployeeId;
			String fName;
			String fDesignationCode;
			String fDateOfBirth;
			String fGender;
			String fIsIndian;
			String fSalary;
			String fPanNumber,faadharCardNumber;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			int x=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				fName = randomAccessFile.readLine();
				fName = fName.trim();
				fDesignationCode = randomAccessFile.readLine();
				fDesignationCode = fDesignationCode.trim();
				fDateOfBirth = randomAccessFile.readLine();
				fDateOfBirth = fDateOfBirth.trim();
				fGender = randomAccessFile.readLine();
				fGender = fGender.trim();
				fIsIndian = randomAccessFile.readLine();
				fIsIndian = fIsIndian.trim();
				fSalary = randomAccessFile.readLine();
				fSalary = fSalary.trim();
				fPanNumber = randomAccessFile.readLine();
				fPanNumber = fPanNumber.trim();
				faadharCardNumber = randomAccessFile.readLine();
				faadharCardNumber = faadharCardNumber.trim();
				if(fPanNumber.equalsIgnoreCase(panNumber))
				{
					employeeDTO = new EmployeeDTO();
					employeeDTO.setEmployeeId(fEmployeeId);
					employeeDTO.setName(fName);
					employeeDTO.setDesignationCode(Integer.parseInt(fDesignationCode));
					try
					{
						employeeDTO.setDateOfBirth(simpleDateFormat.parse(fDateOfBirth));
					}catch(ParseException pe)
					{
						throw new DAOException(pe.getMessage());
					}
					employeeDTO.setGender(fGender.charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
					employeeDTO.setIsIndian(Boolean.parseBoolean(fIsIndian));
					employeeDTO.setBasicSalary(new BigDecimal(fSalary));
					employeeDTO.setPANNumber(fPanNumber);
					employeeDTO.setAadharCardNumber(faadharCardNumber);
					randomAccessFile.close();
					return employeeDTO;
				}
			}
			randomAccessFile.close();
			throw new DAOException("Invalid Pan Card Number : " + panNumber);
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}	
	}
	public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
	{
		if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar Card Number : " + aadharCardNumber);
		aadharCardNumber = aadharCardNumber.trim();
		if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar Card Number : " + aadharCardNumber);
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid Aadhar Card Number : " + aadharCardNumber);
			EmployeeDTOInterface employeeDTO;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) throw new DAOException("Invalid Aadhar Card Number : " + aadharCardNumber);
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			String fEmployeeId;
			String fName;
			String fDesignationCode;
			String fDateOfBirth;
			String fGender;
			String fIsIndian;
			String fSalary;
			String fPanNumber,faadharCardNumber;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			int x=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				fName = randomAccessFile.readLine();
				fName = fName.trim();
				fDesignationCode = randomAccessFile.readLine();
				fDesignationCode = fDesignationCode.trim();
				fDateOfBirth = randomAccessFile.readLine();
				fDateOfBirth = fDateOfBirth.trim();
				fGender = randomAccessFile.readLine();
				fGender = fGender.trim();
				fIsIndian = randomAccessFile.readLine();
				fIsIndian = fIsIndian.trim();
				fSalary = randomAccessFile.readLine();
				fSalary = fSalary.trim();
				fPanNumber = randomAccessFile.readLine();
				fPanNumber = fPanNumber.trim();
				faadharCardNumber = randomAccessFile.readLine();
				faadharCardNumber = faadharCardNumber.trim();
				if(faadharCardNumber.equalsIgnoreCase(aadharCardNumber))
				{
					employeeDTO = new EmployeeDTO();
					employeeDTO.setEmployeeId(fEmployeeId);
					employeeDTO.setName(fName);
					employeeDTO.setDesignationCode(Integer.parseInt(fDesignationCode));
					try
					{
						employeeDTO.setDateOfBirth(simpleDateFormat.parse(fDateOfBirth));
					}catch(ParseException pe)
					{
						throw new DAOException(pe.getMessage());
					}
					employeeDTO.setGender(fGender.charAt(0)=='M'?GENDER.MALE:GENDER.FEMALE);
					employeeDTO.setIsIndian(Boolean.parseBoolean(fIsIndian));
					employeeDTO.setBasicSalary(new BigDecimal(fSalary));
					employeeDTO.setPANNumber(fPanNumber);
					employeeDTO.setAadharCardNumber(faadharCardNumber);
					randomAccessFile.close();
					return employeeDTO;
				}
			}
			randomAccessFile.close();
			throw new DAOException("Invalid Aadhar Card Number : " + aadharCardNumber);
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}	
	}
	public boolean employeeIdExists(String employeeId) throws DAOException
	{
		if(employeeId==null) return false;
		employeeId = employeeId.trim();
		if(employeeId.length()==0) return false;
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return false;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) return false;
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			String fEmployeeId;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			int x=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				if(fEmployeeId.equalsIgnoreCase(employeeId))
				{
					return true;
				}
				for(x=1;x<=8;x++) randomAccessFile.readLine();
			}
			randomAccessFile.close();
			return false;
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public boolean panNumberExists(String panNumber) throws DAOException
	{
		if(panNumber==null) return false;
		panNumber = panNumber.trim();
		if(panNumber.length()==0) return false;
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return false;
			EmployeeDTOInterface employeeDTO;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) return false;
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			String fEmployeeId;
			String fName;
			String fDesignationCode;
			String fDateOfBirth;
			String fGender;
			String fIsIndian;
			String fSalary;
			String fPanNumber,faadharCardNumber;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			int x=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				fName = randomAccessFile.readLine();
				fName = fName.trim();
				fDesignationCode = randomAccessFile.readLine();
				fDesignationCode = fDesignationCode.trim();
				fDateOfBirth = randomAccessFile.readLine();
				fDateOfBirth = fDateOfBirth.trim();
				fGender = randomAccessFile.readLine();
				fGender = fGender.trim();
				fIsIndian = randomAccessFile.readLine();
				fIsIndian = fIsIndian.trim();
				fSalary = randomAccessFile.readLine();
				fSalary = fSalary.trim();
				fPanNumber = randomAccessFile.readLine();
				fPanNumber = fPanNumber.trim();
				faadharCardNumber = randomAccessFile.readLine();
				faadharCardNumber = faadharCardNumber.trim();
				if(fPanNumber.equalsIgnoreCase(panNumber))
				{
					return true;
				}
			}
			randomAccessFile.close();
			return false;
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}	
	}
	public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
	{
		if(aadharCardNumber==null) return false;
		aadharCardNumber = aadharCardNumber.trim();
		if(aadharCardNumber.length()==0) return false;
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return false;
			EmployeeDTOInterface employeeDTO;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) return false;
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			String fEmployeeId;
			String fName;
			String fDesignationCode;
			String fDateOfBirth;
			String fGender;
			String fIsIndian;
			String fSalary;
			String fPanNumber,faadharCardNumber;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			int x=0;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fEmployeeId = fEmployeeId.trim();
				fName = randomAccessFile.readLine();
				fName = fName.trim();
				fDesignationCode = randomAccessFile.readLine();
				fDesignationCode = fDesignationCode.trim();
				fDateOfBirth = randomAccessFile.readLine();
				fDateOfBirth = fDateOfBirth.trim();
				fGender = randomAccessFile.readLine();
				fGender = fGender.trim();
				fIsIndian = randomAccessFile.readLine();
				fIsIndian = fIsIndian.trim();
				fSalary = randomAccessFile.readLine();
				fSalary = fSalary.trim();
				fPanNumber = randomAccessFile.readLine();
				fPanNumber = fPanNumber.trim();
				faadharCardNumber = randomAccessFile.readLine();
				faadharCardNumber = faadharCardNumber.trim();
				if(faadharCardNumber.equalsIgnoreCase(aadharCardNumber))
				{
					return true;
				}
			}
			randomAccessFile.close();
			return false;
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}	
	}
	public int getCount() throws DAOException
	{
		Set<EmployeeDTOInterface> employees = new TreeSet<EmployeeDTOInterface>();
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return employees.size();
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return employees.size();
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			EmployeeDTOInterface employeeDTO;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			char fGender;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				employeeDTO = new EmployeeDTO();
				employeeDTO.setEmployeeId(randomAccessFile.readLine());
				employeeDTO.setName(randomAccessFile.readLine());
				employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
				try
				{
					employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
				}catch(ParseException pe)
				{
					throw new DAOException(pe.getMessage());
				}
				fGender = randomAccessFile.readLine().charAt(0);
				if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
				else employeeDTO.setGender(GENDER.FEMALE);
				employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
				employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
				employeeDTO.setPANNumber(randomAccessFile.readLine());
				employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
				employees.add(employeeDTO);
			}
			randomAccessFile.close();
			return employees.size();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public int getCountByDesignationCode(int designationCode) throws DAOException	
	{
		Set<EmployeeDTOInterface> employees = new TreeSet<EmployeeDTOInterface>();
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return employees.size();
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return employees.size();
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			EmployeeDTOInterface employeeDTO;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fName,fEmployeeId;
			int fdesignationCode;
			int x;
			char fGender;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fEmployeeId = randomAccessFile.readLine();
				fName = randomAccessFile.readLine();
				fdesignationCode = Integer.parseInt(randomAccessFile.readLine());
				if(fdesignationCode != designationCode) 
				{
					for(x=1;x<=6;x++) randomAccessFile.readLine(); 
					continue;
				}
				employeeDTO = new EmployeeDTO();
				employeeDTO.setEmployeeId(fEmployeeId);
				employeeDTO.setName(fName);
				employeeDTO.setDesignationCode(fdesignationCode);
				try
				{
					employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
				}catch(ParseException pe)
				{
					throw new DAOException(pe.getMessage());
				}
				fGender = randomAccessFile.readLine().charAt(0);
				if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
				else employeeDTO.setGender(GENDER.FEMALE);
				employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
				employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
				employeeDTO.setPANNumber(randomAccessFile.readLine());
				employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
				employees.add(employeeDTO);
			}
			randomAccessFile.close();
			return employees.size();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}	
	}
}	