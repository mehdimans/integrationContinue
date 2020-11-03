package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;


@SpringBootTest
public class EmployeServiceImplTest {

	@Autowired 
	IEmployeService ems;
	private static final Logger l = Logger.getLogger(EmployeServiceImplTest.class); 
	@Autowired
	EmployeRepository employeRepository;

	@Test
	public void testajouterContrat()  throws ParseException {

		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		Contrat e = new Contrat (date1,"MENSUEL",999);
		e.setReference(1);
		ems.ajouterContrat(e);
		
		assertEquals(1,e.getReference());
	}

	
	@Test
	public void testGetAllEmployes() {
		List<Employe> le=ems.getAllEmployes();
		le.forEach(e->l.info(e+"\n"));
			
		
	}
	
	
	@Test
	public void testAuthenticate() {
		
		String username_innexistant = "qq";
		String password = "";
		
		Employe e = ems.authenticate(username_innexistant, password);
		assertEquals(e, null); // Vrai 

	}
	
	
	@Test
	public void testMettreAjourEmailByEmployeId(){
		Employe emp = new Employe("Aymen", "Aymen","aymen.benfarhat@esprit.tn", true,Role.CHEF_DEPARTEMENT);
		Employe empAdded = ems.addOrUpdateEmploye(emp);
		 
		Employe empUpdateted =ems.mettreAjourEmailByEmployeId("nouveau@email.test",empAdded.getId());
		 
		assertNotEquals(empAdded.getEmail(),empUpdateted.getEmail());
	}
	
	@Test
	public void testGetNombreEmployeJPQL(){
		assertNotEquals(0,ems.getNombreEmployeJPQL());
	}
	
	@Test
	public void testAddEmployee()throws ParseException {
		Employe emp = new Employe("ben", "Farhat","aymen.benfarhat@esprit.tn", true,Role.INGENIEUR);
		Employe empAdded = ems.addOrUpdateEmploye(emp);
		assertEquals(emp.getEmail(), empAdded.getEmail());
	}
}
