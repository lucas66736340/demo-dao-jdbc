package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

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

	// indesere um vendedor na base de dados
	@Override
	public void insert(Seller vendedor) {

		PreparedStatement st = null;
		ResultSet rs = null;

		String comandoSql = "INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId) " + "VALUES "
				+ "(?, ?, ?, ?, ?)";
		try {

			st = con.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, vendedor.getName());
			st.setString(2, vendedor.getEmail());
			st.setDate(3, new java.sql.Date(vendedor.getBirthDate().getTime()));
			st.setDouble(4, vendedor.getBaseSalary());
			st.setInt(5, vendedor.getDepartment().getId());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					vendedor.setId(id);
				}
			} else {
				throw new DbException("Erro inesperado nenhuma linha foi afetada");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}

	// atualizando os dados de um vendedor no banco dados
	@Override
	public void update(Seller vendedor) {
		PreparedStatement st = null;

		String comandoSql = "UPDATE seller "
				+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + "WHERE Id = ?";
		try {

			st = con.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, vendedor.getName());
			st.setString(2, vendedor.getEmail());
			st.setDate(3, new java.sql.Date(vendedor.getBirthDate().getTime()));
			st.setDouble(4, vendedor.getBaseSalary());
			st.setInt(5, vendedor.getDepartment().getId());
			st.setInt(6, vendedor.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(st);
		}

	}

	//metodo que deleta um vendedor do banco
	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;

		String comandoSql = "DELETE FROM seller WHERE Id = ?";

		try {

			st = con.prepareStatement(comandoSql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(st);
		}

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
				Seller vendedor = instanciaSeller(rs, dep);
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

	// metodo que instancia um vendedor
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

	// quando usar throws quer dizer que o metodo que invocar esse vai ter que
	// tratar a execao
	// quando eu propago a execao o outro metodo tera que tratar
	private Department instanciaDepartamento(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		String comandoSql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id ";

		try {
			st = con.prepareStatement(comandoSql);

			rs = st.executeQuery();

			List<Seller> listaDeVendedores = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanciaDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller vendedor = instanciaSeller(rs, dep);
				listaDeVendedores.add(vendedor);

			}
			return listaDeVendedores;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findByDepartment(Department departement) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String comandoSql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ?";

		try {
			st = con.prepareStatement(comandoSql);
			st.setInt(1, departement.getId());
			rs = st.executeQuery();

			List<Seller> listaDeVendedores = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanciaDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller vendedor = instanciaSeller(rs, dep);
				listaDeVendedores.add(vendedor);

			}
			return listaDeVendedores;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
