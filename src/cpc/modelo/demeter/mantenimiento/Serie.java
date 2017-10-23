package cpc.modelo.demeter.mantenimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_Serie_aud")
@Entity
@Table(name="tbl_dem_Serie" ,schema="mantenimiento")
public class Serie {

	
		private  Integer 		id;
		private  String			descripcion;
		
		@Column(name="seq_idSerie")
		@SequenceGenerator(name="Serie_Gen", sequenceName="mantenimiento.tbl_dem_serie_seq",  allocationSize=1 )
		@Id	@GeneratedValue( generator="Serie_Gen")
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
		
		@Column(name="str_descripcion",nullable=false)
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return descripcion;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((descripcion == null) ? 0 : descripcion.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Serie other = (Serie) obj;
			
			if (id == null)
			{
				if (other.id != null)
					return false;
			} 
			else if (!id.equals(other.id))
			{
				return false;
			}
			return true;
		}

		
}
