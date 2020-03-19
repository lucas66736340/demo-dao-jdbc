package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		// instanciando um dao a partir da Fabrica de dao
		SellerDao sellerDao = DaoFactory.createSellerDao();

		// usar a interface caso a regra de negocio mude no futuro
		Seller vendedor = sellerDao.findById(3);

		System.out.println(vendedor);

	}

}
