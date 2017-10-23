package cpc.modelo.demeter.sincronizacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
 



@Entity
@Table(name = "tbl_dem_exportartdetalle",schema = "sincronizacion")

public class DetalleExport implements Serializable {
	
	
		private Integer  id;
		private byte[] idorigen;
		private byte[] iddestino;
		private String   nameclass;
	//(	private boolean  trabajado = false;
	//	private Object 	 objeto;
		private byte[]  objeto;
		private Exportar exportar;
		private boolean isupdate;
		private boolean completado;
		
		


		@SequenceGenerator(name="Seqexportdetalle", sequenceName="sincronizacion.tbl_dem_exportartdetalle_id_seq", allocationSize=1)
		@Id
		@Column(name="id")
		@GeneratedValue(generator="Seqexportdetalle")
		public Integer getId() {
			return id;
		}
		
	
		public void setId(Integer id) {
			this.id = id;
		}


		@Column
		public byte[] getIdorigen() {
			return idorigen;
		}


	
		public void setIdorigen(byte[] idorigen) {
			this.idorigen = idorigen;
		}


		@Column
		public byte[] getIddestino() {
			return iddestino;
		}



		public void setIddestino(byte[] iddestino) {
			this.iddestino = iddestino;
		}


		@Column
		public String getNameclass() {
			return nameclass;
		}



		public void setNameclass(String nameclass) {
			this.nameclass = nameclass;
		}


		@Column
		public byte[] getObjeto() {
			return objeto;
		}



		public void setObjeto(byte[] objeto) {
			this.objeto = objeto;
		}


		@Column
		public boolean isIsupdate() {
			return isupdate;
		}


		public void setIsupdate(boolean isupdate) {
			this.isupdate = isupdate;
		}
		
		@Column
		public boolean isCompletado() {
			return completado;
		}


		public void setCompletado(boolean completo) {
			this.completado = completo;
		}


		@OneToOne
		public Exportar getExportar() {
			return exportar;
		}
		public void setExportar(Exportar exportar) {
			this.exportar = exportar;
		} 

		
		
}
