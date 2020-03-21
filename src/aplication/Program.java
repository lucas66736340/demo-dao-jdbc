package aplication;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		// instanciando um dao a partir da Fabrica de dao
		SellerDao sellerDao = DaoFactory.createSellerDao();

		// usar a interface caso a regra de negocio mude no futuro
		Seller vendedor = sellerDao.findById(3);

		System.out.println(vendedor);

		// encontrando vendedor pelo departamento
		List<Seller> lista = sellerDao.findByDepartment(new Department(2, null));
		System.out.println("Lista de vendedores por departamento");

		for (Seller seller : lista) {
			System.out.println(seller);
		}

		// recebendo uma lista de todos os vendedores
		List<Seller> lista2 = sellerDao.findAll();
		System.out.println("Lista de todos os vendedores ");

		for (Seller seller : lista2) {
			System.out.println(seller);
		}

	}

}
