package cpc.persistencia;

import java.lang.reflect.TypeVariable;
import java.sql.Types;

import javax.persistence.TemporalType;

import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.TypeNames;


public class DialectoAdaptado extends PostgreSQLDialect {
	
	
	    public DialectoAdaptado() {
	        super();
	       RegistrarTipos();
	        
	    }
	    protected void RegistrarTipos() {
	    	registerColumnType( Types.INTEGER, "integer" );
	        registerColumnType( Types.DOUBLE, "numeric" );
	        registerColumnType(Types.DATE, "date");
	        registerColumnType(Types.TIME, "time");
	        registerColumnType(Types.TIMESTAMP, "timestamp");
	         TypeVariable<Class<TypeNames>>[] a = org.hibernate.dialect.TypeNames.class.getTypeParameters();
	         for (TypeVariable<Class<TypeNames>> typeVariable : a) {
			System.out.println(typeVariable);
			}
	         
	    }
	

}
