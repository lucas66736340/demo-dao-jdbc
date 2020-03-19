package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	//insere um departamento no banco de dados
	void isert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	//busca no banco de dados um departamento pelo id e retorna o mesmo
	Department findById(Integer id);
	//retorna uma lista com todos os departamentos 
	List<Department> findAll();
	
	
	
	
	
}
