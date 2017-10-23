package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.annotations.Cascade;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Modelo;
import cva.pc.componentes.CompEncabezado;

@Entity
@Audited
@AuditTable(schema="auditoria",value="tbl_dem_maquinariaexterna_aud")
@Table(name = "tbl_dem_maquinariaexterna", schema = "mantenimiento")
public class MaquinariaExterna implements Serializable{

	
	private   Integer			id;
	private   TipoGarantia  	tipo;

	private	  Integer			anofabricacion;
	private   Cliente			propietario;
	private   Cliente			contacto;   // en el caso de ser una empresa .-D 
	
	private   Garantia			garantia;
	
	private   String 		serialcarroceria;
	private   String 		serialMotor;
	private   String 		localidad;   
	
	
	
	@Column(name="seq_idmaquinariaExterna")
	@SequenceGenerator(name="MaquinariaExterna_Gen", sequenceName="mantenimiento.tbl_dem_MaquinariaExterna_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="MaquinariaExterna_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="int_anoFabricacion")
	public Integer getAnofabricacion() {
		return anofabricacion;
	}
	public void setAnofabricacion(Integer anoFabricacion) {
		this.anofabricacion = anoFabricacion;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	 @JoinColumn(name="int_propietario")
	public Cliente getPropietario() {
		return propietario;
	}
	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}
	
	@OneToOne(mappedBy="maquinaria")
	public Garantia getGarantia() {
		return garantia;
	}
	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}

	
	@Column(name="str_serialCarroceria",unique=true)
	public String getSerialcarroceria() {
		return serialcarroceria;
	}
	public void setSerialcarroceria(String serialcarroceria) {
		this.serialcarroceria = serialcarroceria;
	}
	
	@Column(name="str_serialMotor",unique=true)
	public String getSerialMotor() {
		return serialMotor;
	}
	public void setSerialMotor(String serialMotor) {
		this.serialMotor = serialMotor;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	 @JoinColumn(name="int_contacto")
	public Cliente getContacto() {
		return contacto;
	}
	public void setContacto(Cliente contacto) {
		this.contacto = contacto;
	}
	
	@Column(name="text_localidad")
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	@Transient
	 public JRDataSource getPropietariosDS()   
    { 
		List<Cliente> lista = new ArrayList<Cliente>();
		if(propietario != null)
		{	lista.add(propietario);}
		if(contacto != null)
			{lista.add(contacto);}  
	    return new JRBeanCollectionDataSource(lista) ;
    }
	
	@Override
	public String toString() {
		
		return this.serialcarroceria;
	}
	
	@Transient
	public String getStrPropietario()
	{
		return (propietario != null ?propietario.getIdentidadLegal():"No Definido");
	}
	 
	@Transient
	public String getStr_Marca()
	{
		return  tipo.getModelo().getMarca().toString();
	}

	@Transient
	public String getDescripcionModelo()
	{
		return tipo.getModelo().getDescripcionModelo();
	};
	
	 
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="int_idTipo")
	public TipoGarantia getTipo() {
		return tipo;
	}

	public void setTipo(TipoGarantia tipoGarantia) {
		this.tipo = tipoGarantia;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaquinariaExterna other = (MaquinariaExterna) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	};
	
	@Transient
	public String getStrSerie()
	{
		return tipo.getSerie().getDescripcion();
	}
	
	
}
