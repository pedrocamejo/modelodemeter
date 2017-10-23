package cpc.persistencia.demeter.implementacion.transporte;


import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
 

import cpc.modelo.demeter.transporte.SolicitudTransporte;

import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerSolicitudTransporte extends DaoGenerico<SolicitudTransporte,Integer> {

	public PerSolicitudTransporte( ) {
		super(SolicitudTransporte.class);
		// TODO Auto-generated constructor stub
	}


}