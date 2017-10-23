package cpc.modelo.demeter.transporte;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;



@Audited
@AuditTable(value="tbl_dem_Gerencias_aud",schema="auditoria") //vista 
@Entity
@Table(name="tbl_dem_Gerencias",schema="transporte")
public class Gerencia implements Serializable{ // Depende de sigesp  amarrado con la tabla smo_departamento :D  Notas al final del Tomo :D

	private String id; // coddep <--sigesp
		private String descripcion; // demdep <--sigesp
		private String gerente; // codpdepp  <--sigesp

		@Id
		@Column(name="id")
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		@Column(name="descripcion")
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
		@Column(name="gerente")
		public String getGerente() {
			return gerente;
		}
		public void setGerente(String gerente) {
			this.gerente = gerente;
		}

}
