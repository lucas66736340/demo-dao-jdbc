package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	//classe que instancia obejetos que acesssam dados
	
	//expor somente a interface que � um tipo mais generico
	public static SellerDao createSellerDao() {
		//internamente vai instanciar uma implementa��o 
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	
	
}
