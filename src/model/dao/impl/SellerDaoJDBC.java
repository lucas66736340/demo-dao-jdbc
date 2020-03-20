package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	// classse que implementa a interface do vendedor
	// classe que faz as operacoes de obejetos que acessam dados
	// implementa operacaoes de acesso a dados

	// obejeto de conexao com o banco de dados
	private Connection con;

	public SellerDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void isert(Seller vendedor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller vendedor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	// encontra um vendedor
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String comandoSql = "SELECT seller.*,department.Name as DepName " + " FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?";

		try {
			st = con.prepareStatement(comandoSql);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			// caso nao venha nehum vendedor
			if (rs.next()) {
				Department dep = instanciaDepartamento(rs);
				Seller vendedor = instanciaSeller(rs,dep);
				return vendedor;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	//metodo que instancia um vendedor 
	private Seller instanciaSeller(ResultSet rs, Department dep) throws SQLException {
		Seller vendedor = new Seller();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setName(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setBaseSalary(rs.getDouble("BaseSalary"));
		vendedor.setBirthDate(rs.getDate("BirthDate"));
		vendedor.setDepartment(dep);
		return vendedor;
	
	}

	//quando usar   throws quer dizer que o metodo que invocar esse vai ter que tratar a execao
	//quando eu propago a execao o outro metodo trata ela 
	private Department instanciaDepartamento(ResultSet rs) throws SQLException {
	 Department dep =	new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
