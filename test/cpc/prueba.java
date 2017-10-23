package cpc;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cpc.modelo.demeter.ConfiguracionModelo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={ConfiguracionModelo .class})
public class prueba {
	
	@Autowired
	public String idsede;
	
	@Test
	public void test() {
		assertNotNull(idsede);
	}

} 


		