package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_Garantia_aud")
@Entity
@Table(name="tbl_dem_Garantia" ,schema="mantenimiento")
public class Garantia implements Serializable{
	
		private Integer 							id;
		private MaquinariaExterna			 		maquinaria ;
		private EnteExterno 						ente; 
		private Integer 							estatus = new Integer(0); // 0 Nueva  1 ACTIVA 2 ANULADA 3 FINALIZADA  
		private static String[] 					estatusgarantia = {"NUEVA","ACTIVA","ANULADA","FINALIZADA"};
	 
		public Garantia() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		@Column(name="seq_idGarantia")
		@SequenceGenerator(name="Garantia_Gen", sequenceName="mantenimiento.tbl_dem_Garantia_seq",  allocationSize=1 )
		@Id	@GeneratedValue( generator="Garantia_Gen")
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
		
		@OneToOne(cascade=CascadeType.ALL)
		@JoinColumn(name="int_idobjeto",unique=true)
		public MaquinariaExterna getMaquinaria() {
			return maquinaria;
		}
		public void setMaquinaria(MaquinariaExterna maquinaria) {
			this.maquinaria = maquinaria;
		}
		
		@OneToOne(fetch=FetchType.EAGER)
		@JoinColumn(name="int_identeexterno")
		public EnteExterno getEnte() {
			return ente;
		}
		public void setEnte(EnteExterno ente) {
			this.ente = ente;
		}
	 
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
			{	
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			Garantia other = (Garantia) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			}
			else if (!id.equals(other.id))
			{
				return false;			
			}
			return true;
		}
		
		@Transient
		public String getStrPropietario()
		{
			
			if(maquinaria.getPropietario() != null)
			{
				return maquinaria.getPropietario().getNombres();
			}
			return "No Definido";
		}

		@Column(name="estatus",nullable=false)
		public Integer getEstatus() {
			return estatus;
		}


		public void setEstatus(Integer estatus) {
			this.estatus = estatus;
		}
		
		@Transient
		public String getEstatusgarantia()
		{
			return estatusgarantia[estatus];			
		}
}
