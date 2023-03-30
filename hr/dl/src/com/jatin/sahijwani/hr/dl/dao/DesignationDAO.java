package com.jatin.sahijwani.hr.dl.dao;
import com.jatin.sahijwani.hr.dl.dto.*;
import java.util.*;
import com.jatin.sahijwani.hr.dl.interfaces.dao.*;
import com.jatin.sahijwani.hr.dl.interfaces.dto.*;
import com.jatin.sahijwani.hr.dl.exceptions.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface
{
	private final static String FILE_NAME = "designation.data";
	public void add(DesignationDTOInterface designationDTO) throws DAOException 
	{
		if(designationDTO==null) throw new DAOException("Designation is null");
		String title=designationDTO.getTitle();
		if(title==null) throw new DAOException("Designation is null");
		title=title.trim();
		if(title.length()==0) throw new DAOException("Length of designation is zero");
		try
		{
			File file = new File(FILE_NAME);
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");	
			int lastGeneratedCode = 0;
			int recordCount = 0;
			String lastGeneratedCodeString = "";
			String recordCountString = ""; 
			if(randomAccessFile.length()==0)
			{
				lastGeneratedCode = 0;
				recordCount = 0;
				lastGeneratedCodeString = "0";
				while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
				recordCountString = "0";
				while(recordCountString.length()<10) recordCountString+=" ";
				randomAccessFile.writeBytes(lastGeneratedCodeString);
				randomAccessFile.writeBytes("\n");
				randomAccessFile.writeBytes(recordCountString);
				randomAccessFile.writeBytes("\n");
			}
			else
			{
				lastGeneratedCodeString = randomAccessFile.readLine().trim();
				recordCountString = randomAccessFile.readLine().trim();
				lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
				recordCount = Integer.parseInt(recordCountString);
			}
			int fCode;
			String fTitle;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine());
				fTitle = randomAccessFile.readLine();
				if(fTitle.equalsIgnoreCase(title))
				{
					randomAccessFile.close();
					throw new DAOException("Designation already exists : "+title);
				}
			}
			int code = lastGeneratedCode + 1;
			randomAccessFile.writeBytes(String.valueOf(code)+"\n");
			randomAccessFile.writeBytes(title+"\n");
			designationDTO.setCode(code);
			randomAccessFile.seek(0);
			lastGeneratedCode++;
			recordCount++;
			lastGeneratedCodeString = String.valueOf(lastGeneratedCode);
			recordCountString = String.valueOf(recordCount);
			while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
			while(recordCountString.length()<10) recordCountString+=" ";
			randomAccessFile.writeBytes(lastGeneratedCodeString);
			randomAccessFile.writeBytes("\n");
			randomAccessFile.writeBytes(recordCountString);
			randomAccessFile.writeBytes("\n");
			randomAccessFile.close();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public void update(DesignationDTOInterface designationDTO) throws DAOException
	{
		try
		{
			if(designationDTO==null) throw new DAOException("Designation is null");
			int code = designationDTO.getCode();
			if(code<=0) throw new DAOException("Invalid code : "+code);
			String title = designationDTO.getTitle();
			if(title==null) throw new DAOException("Designation is null");
			title=title.trim();
			if(title.length()==0) throw new DAOException("Designation is null");
			File file = new File(FILE_NAME);
			if(!file.exists()) throw new DAOException("Invalid code : "+code);
			int fCode;
			String fTitle;
			boolean found = false;
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				throw new DAOException("Invalid code : "+code);
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine());
				fTitle = randomAccessFile.readLine();
				if(fCode == code)
				{
					found = true;
					break;
				}
			}
			if(found==false)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid code : "+code);	
			}
			randomAccessFile.seek(0);
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine());
				fTitle = randomAccessFile.readLine();
				if(fCode!=code && title.equalsIgnoreCase(fTitle)==true)
				{
					randomAccessFile.close();
					throw new DAOException("Title : "+title+" exists");
				}
			}
			File tmpfile = new File("tmp.tmp");
			if(tmpfile.exists()) tmpfile.delete();
			RandomAccessFile tmpRandomAccessFile;
			tmpRandomAccessFile = new RandomAccessFile(tmpfile,"rw");
			randomAccessFile.seek(0);
			tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
			tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine());
				fTitle = randomAccessFile.readLine();
				if(code!=fCode)
				{
					tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
					tmpRandomAccessFile.writeBytes("\n");
					tmpRandomAccessFile.writeBytes(fTitle);
					tmpRandomAccessFile.writeBytes("\n");
				}
				else
				{
					System.out.println(title);
					tmpRandomAccessFile.writeBytes(String.valueOf(code));
					tmpRandomAccessFile.writeBytes("\n");
					tmpRandomAccessFile.writeBytes(title);
					tmpRandomAccessFile.writeBytes("\n");
				}
			}
			randomAccessFile.seek(0);
			tmpRandomAccessFile.seek(0);
			while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
			{
				randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
			}
			randomAccessFile.setLength(tmpRandomAccessFile.length());
			tmpRandomAccessFile.setLength(0);
			randomAccessFile.close();
			tmpRandomAccessFile.close();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public void delete(int code) throws DAOException
	{
		try
		{

			if(code<=0) throw new DAOException("Invalid code : "+code);
			File file = new File(FILE_NAME);
			if(!file.exists()) throw new DAOException("Invalid code : "+code);
			int fCode=0;
			String fTitle="";
			boolean found = false;
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				throw new DAOException("Invalid code : "+code);
			}
			randomAccessFile.readLine();
			int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine());
				fTitle = randomAccessFile.readLine();
				if(fCode == code)
				{
					found = true;
					break;
				}
			}
			if(found==false)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid code : "+code);	
			}
			if(new EmployeeDAO().isDesignationAlotted(code))
			{
				randomAccessFile.close();
				throw new DAOException("Employee exists with designation : " + fTitle);
			}
			File tmpfile = new File("tmp.tmp");
			if(tmpfile.exists()) tmpfile.delete();
			RandomAccessFile tmpRandomAccessFile;
			tmpRandomAccessFile = new RandomAccessFile(tmpfile,"rw");
			randomAccessFile.seek(0);
			tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
			tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine());
				fTitle = randomAccessFile.readLine();
				if(code!=fCode)
				{
					tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
					tmpRandomAccessFile.writeBytes("\n");
					tmpRandomAccessFile.writeBytes(fTitle);
					tmpRandomAccessFile.writeBytes("\n");
				}
			}
			randomAccessFile.seek(0);
			tmpRandomAccessFile.seek(0);
			randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
			tmpRandomAccessFile.readLine();
			String recordCountString = String.valueOf(recordCount-1);
			while(recordCountString.length()<10) recordCountString+=" ";
			randomAccessFile.writeBytes(recordCountString+"\n");
			while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
			{
				randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
			}
			randomAccessFile.setLength(tmpRandomAccessFile.length());
			tmpRandomAccessFile.setLength(0);
			randomAccessFile.close();
			tmpRandomAccessFile.close();
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public Set<DesignationDTOInterface> getAll() throws DAOException
	{
		Set<DesignationDTOInterface> set;
		set = new TreeSet<>();
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return set;
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0)
			{
				randomAccessFile.close();
				return set;
			}
			randomAccessFile.readLine();
			randomAccessFile.readLine();
			int fCode;
			String fTitle;
			DesignationDTOInterface designationDTO;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				designationDTO = new DesignationDTO();
				fCode = Integer.parseInt(randomAccessFile.readLine().trim());
				fTitle = randomAccessFile.readLine();
				designationDTO.setTitle(fTitle);
				designationDTO.setCode(fCode);
				set.add(designationDTO);
			}
			randomAccessFile.close();
			return set;	
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public DesignationDTOInterface getByCode(int code) throws DAOException
	{
		if(code<=0) throw new DAOException("Invalid code : "+code);
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid code : "+code);
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				throw new DAOException("Invalid code : "+code);
			}
			randomAccessFile.readLine();
			int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			if(recordCount==0)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid code : "+code);	
			}
			int fCode;
			String fTitle;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine().trim());
				fTitle = randomAccessFile.readLine();
				if(fCode == code)
				{
					randomAccessFile.close();
					DesignationDTOInterface designationDTO;
					designationDTO = new DesignationDTO();
					designationDTO.setCode(code);
					designationDTO.setTitle(fTitle);
					return designationDTO;
				}
			}
			randomAccessFile.close();
			throw new DAOException("Invalid code : "+code);	
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public DesignationDTOInterface getByTitle(String title) throws DAOException
	{
		if(title.length()==0) throw new DAOException("Invalid title : "+title);
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) throw new DAOException("Invalid title : "+title);
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				throw new DAOException("Invalid title : "+title);
			}
			randomAccessFile.readLine();
			int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			if(recordCount==0)
			{
				randomAccessFile.close();
				throw new DAOException("Invalid title : "+title);	
			}
			int fCode;
			String fTitle;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine().trim());
				fTitle = randomAccessFile.readLine();
				if(fTitle.equalsIgnoreCase(title))
				{
					randomAccessFile.close();
					DesignationDTOInterface designationDTO;
					designationDTO = new DesignationDTO();
					designationDTO.setCode(fCode);
					designationDTO.setTitle(fTitle);
					return designationDTO;
				}
			}
			randomAccessFile.close();
			throw new DAOException("Invalid title : "+title);	
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
	public boolean codeExists(int code) throws DAOException
	{
		if(code<=0) return false;
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return false;
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return false;
			}
			randomAccessFile.readLine();
			int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			if(recordCount==0)
			{
				randomAccessFile.close();
				return false;
			}
			int fCode;
			String fTitle;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine().trim());
				fTitle = randomAccessFile.readLine();
				if(fCode == code)
				{
					randomAccessFile.close();
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
	public boolean titleExists(String title) throws DAOException
	{
		if(title.length()==0) return false;
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return false;
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0) 
			{
				randomAccessFile.close();
				return false;
			}
			randomAccessFile.readLine();
			int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			if(recordCount==0)
			{
				randomAccessFile.close();
				return false;
			}
			int fCode;
			String fTitle;
			while(randomAccessFile.getFilePointer()<randomAccessFile.length())
			{
				fCode = Integer.parseInt(randomAccessFile.readLine().trim());
				fTitle = randomAccessFile.readLine();
				if(fTitle.equalsIgnoreCase(title))
				{
					randomAccessFile.close();
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
		try
		{
			File file = new File(FILE_NAME);
			if(file.exists()==false) return 0;
			RandomAccessFile randomAccessFile;
			randomAccessFile = new RandomAccessFile(file,"rw");
			if(randomAccessFile.length()==0)
			{
				randomAccessFile.close();
				return 0;
			}
			int recordCount = 0;
			randomAccessFile.readLine();
			recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
			randomAccessFile.close();
			return recordCount;
		}catch(IOException ioe)
		{
			throw new DAOException(ioe.getMessage());
		}
	}
}