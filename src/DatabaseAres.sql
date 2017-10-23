
//-------------------------------------------------------------
	
UPDATE tbl_ares_funcionalidad SET bol_estado = FALSE 
 WHERE str_comando='cva.pc.demeter.comando.ComandoServicio' OR str_comando='cpc.demeter.comando.ComandoServicio';	
	
/* En Produccion de Central ---------------------------------------------------------------------*/
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Control Sede', 31, 'cpc.demeter.comando.ComandoControlSede', 
	'/iconos/32x32/sedes.png', true, '0101000000000000', 1);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Bancos', 31, 'cpc.demeter.comando.ComandoBanco', 
	'/iconos/32x32/banco.png', true, '1111000000000000', 3);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Cuentas Bancarias', 31, 'cpc.demeter.comando.ComandoCuentaBancaria', 
	'/iconos/32x32/cuentaBancaria.png', true, '1111000000000000', 4);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Cuentas Contables', 31, 'cpc.demeter.comando.ComandoCuentaContable', 
	'/iconos/32x32/cuentaContable.png', true, '1111000000000000', 5);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Configurar Tipos Servicios', 31, 'cpc.demeter.comando.ComandoConfigurarTiposServicios', 
	'/iconos/32x32/configCuentaTiposServicios.png', true, '1111000000000000', 7);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Factura', 32, 'cpc.demeter.comando.ComandoFactura', 
	'/iconos/32x32/factura.png', true, '1001001101000000', 1);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Recibo', 32, 'cpc.demeter.comando.ComandoRecibo', 
	'/iconos/32x32/recibo.png', true, '1001001101000000', 2);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Nota Credito', 32, 'cpc.demeter.comando.ComandoNotaCredito', 
	'/iconos/32x32/notaCredito.png', true, '1001001101000000', 3);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Deposito', 32, 'cpc.demeter.comando.ComandoDeposito', 
	'/iconos/32x32/deposito.png', true, '1111000000000000', 4);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Nota Debito', 32, 'cpc.demeter.comando.ComandoNotaDebito', 
	'/iconos/32x32/notaDebito.png', true, '1001001101000000', 5);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Debito Interno', 32, 'cpc.demeter.comando.ComandoDebitoInterno', 
	'/iconos/32x32/notaDebito.png', true, '1001001101000000', 6);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Cierre Diario', 33, 'cpc.demeter.comando.ComandoCierreDiario', 
	'/iconos/32x32/cierre.png', true, '1000001100000000', 0);	

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Libro Venta', 33, 'cpc.demeter.comando.ComandoLibroVenta', 
	'/iconos/32x32/libroVenta.png', true, '1011001000000000', 1);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Correcion Forma Pago', 33, 'cpc.demeter.comando.ComandoFormaPago', 
	'/iconos/32x32/formaPago.png', true, '0101000000000000', 2);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Respaldo Datos', 34, 'cpc.demeter.comando.ComandoRespaldoDB', 
	'/iconos/32x32/respaldo.png', true, '1000000000000000', 1);

UPDATE tbl_ares_funcionalidad SET bol_estado = FALSE 
 WHERE seq_idfuncionalidad = 180;

/* ----------------------------------------------------------------------------------------------*/


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo vialidad', 31, 'cpc.demeter.comando.ministerio.ComandoTipoVialidad', 
	'/iconos/32x32/vialidad.png', true, '1111000000000000', 5);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo Suelo', 31, 'cpc.demeter.comando.ministerio.ComandoTipoSuelo', 
	'/iconos/32x32/tipoSuelo.png', true, '1111000000000000', 6);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo Riego', 31, 'cpc.demeter.comando.ministerio.ComandoTipoRiego', 
	'/iconos/32x32/tipoRiego.png', true, '1111000000000000', 7);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo Productor', 31, 'cpc.demeter.comando.ministerio.ComandoTipoProductor', 
	'/iconos/32x32/tipoCliente.png', true, '1111000000000000', 8);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Grado Instruccion', 31, 'cpc.demeter.comando.ministerio.ComandoGradoInstruccion', 
	'/iconos/32x32/gradoInstruccion.png', true, '1111000000000000', 9);            

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo Tenencia Tierra', 31, 'cpc.demeter.comando.ministerio.ComandoTipoTenenciaTierra', 
	'/iconos/32x32/tenenciaTierra.png', true, '1111000000000000', 10);     


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Pais', 31, 'cpc.demeter.comando.ministerio.ComandoPais', 
	'/iconos/32x32/pais.png', true, '1111000000000000', 11);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Eje', 31, 'cpc.demeter.comando.ministerio.ComandoEje', 
	'/iconos/32x32/eje.png', true, '1111000000000000', 12);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Estado', 31, 'cpc.demeter.comando.ministerio.ComandoEstado', 
	'/iconos/32x32/estado.png', true, '1111000000000000', 13);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Municipio', 31, 'cpc.demeter.comando.ministerio.ComandoMunicipio', 
	'/iconos/32x32/municipio.png', true, '1111000000000000', 14);            

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Parroquia', 31, 'cpc.demeter.comando.ministerio.ComandoParroquia', 
	'/iconos/32x32/parroquia.png', true, '1111000000000000',15);     	

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Sector', 31, 'cpc.demeter.comando.ministerio.ComandoSector', 
	'/iconos/32x32/sector.png', true, '1111000000000000', 16);   
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Nacionalidad', 31, 'cpc.demeter.comando.ministerio.ComandoNacionalidad', 
	'/iconos/32x32/nacionalidad.png', true, '1111000000000000', 17);   
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Productor', 31, 'cpc.demeter.comando.ministerio.ComandoProductor', 
	'/iconos/32x32/productor.png', true, '1111000000000000', 19); 


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Unidad Productiva', 31, 'cpc.demeter.comando.ministerio.ComandoUnidadProductiva', 
	'/iconos/32x32/unidadProductiva.png', true, '1111000000000000', 18); 
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo Financiamiento', 31, 'cpc.demeter.comando.ministerio.ComandoTipoFinanciamientoExt', 
	'/iconos/32x32/tipoFinanciaExt.png', true, '1111000000000000', 20); 


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Institucion Financiera', 31, 'cpc.demeter.comando.ministerio.ComandoInstitucionCrediticia', 
	'/iconos/32x32/institucionCrediticia.png', true, '1111000000000000', 21); 


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Tipo Organizacion', 31, 'cpc.demeter.comando.ministerio.ComandoTipoOrganizacion', 
	'/iconos/32x32/tipoOrganizacion.png', true, '1111000000000000', 22); 


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Organizacion', 31, 'cpc.demeter.comando.ministerio.ComandoOrganizacion', 
	'/iconos/32x32/organizacion.png', true, '1111000000000000', 23); 
	
	

/*INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Servicio', 31, 'cpc.demeter.comando.ministerio.ComandoServicio', 
	'/iconos/32x32/servicios.png', true, '1111000000000000', 24);
	

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Labor', 31, 'cpc.demeter.comando.ministerio.ComandoLabor', 
	'/iconos/32x32/labor.png', true, '1111000000000000', 26);*/ 		

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Articulo Venta', 31, 'cpc.demeter.comando.ministerio.ComandoArticuloVenta', 
	'/iconos/32x32/Articulo.png', true, '1111000000000000', 27); 		
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Unidad Medida', 31, 'cpc.demeter.comando.ministerio.ComandoUnidadMedida', 
	'/iconos/32x32/unidadMedida.png', true, '1111000000000000', 25);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Representante', 31, 'cpc.demeter.comando.ministerio.ComandoRepresentante', 
	'/iconos/32x32/representante.png', true, '1111000000000000', 28);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Clase Articulo', 31, 'cpc.demeter.comando.ministerio.ComandoClaseArticulo', 
	'/iconos/32x32/claseArticulo.png', true, '1111000100000000', 2);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Unidad Funcional', 31, 'cpc.demeter.comando.ministerio.ComandoUnidadFuncional', 
	'/iconos/32x32/unidadFuncional.png', true, '1111000100000000', 2);
	
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Ubicacion Fisica', 31, 'cpc.demeter.comando.ministerio.ComandoUbicacionFisica', 
	'/iconos/32x32/direccion.png', true, '1111000100000000', 2);


INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Consumo Credito', 32, 'cpc.demeter.comando.ComandoConsumoCredito', 
	'/iconos/32x32/consumoCredito.png', true, '1001001101000000', 6);

		
DELETE FROM tbl_ares_funcionalidad WHERE str_nombre like '%Cliente Administrativo%';

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Cliente Administrativo', 33, 'cpc.demeter.comando.ComandoClienteAdministrativo', 
	'/iconos/32x32/cliente.png', true, '1001001000000000', 6);

/*Hasta este punto ha sido ejecutado en produccion servidor 172.16.0.21 con el script_ares_12.sql*/
	
INSERT INTO tbl_ares_modulo(str_nombre,  int_idsistema, bol_estado)
    VALUES ('Gestion', 6, true);	
		
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Solicitud Mecanizado', 35, 'cpc.demeter.comando.gestion.ComandoSolicitudMecanizado', 
	'/iconos/32x32/mecanizado.png', true, '1111000100000000', 1);
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Contrato Mecanizado', 32, 'cpc.demeter.comando.ComandoContratoMecanizado', 
	'/iconos/32x32/contrato.png', true, '1001001101000000', 1);	
	
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Trabajador', 31, 'cpc.demeter.comando.ministerio.ComandoTrabajador', 
	'/iconos/32x32/trabajador.png', true, '1111000100000000', 1);

/* HASTA ESTE PUNTO SE EJECUTO EN EL SERVIDOR DE URACHICHE CON EL SCRIPT script_ares_12.sql */

		
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Orden Trabajo M.A.', 32, 'cpc.demeter.comando.gestion.ComandoOrdenTrabajoMecanizado', 
	'/iconos/32x32/ordenmecanizado.png', true, '1001111011000000', 2);

INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Trabajo Mecanizado', 32, 'cpc.demeter.comando.gestion.ComandoTrabajoMecanizado', 
	'/iconos/32x32/controlServicio.png', true, '1001000100000000', 3);
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Silo', 31, 'cpc.demeter.comando.ComandoSilo', 
	'/iconos/32x32/silo.png', true, '1111000100000000', 10);	
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Orden Transporte Cosecha', 32, 'cpc.demeter.comando.gestion.ComandoOrdenTransporteProduccion', 
	'/iconos/32x32/transporteCosecha.png', true, '1001111011000000', 3);
	
INSERT INTO tbl_ares_funcionalidad(
            str_nombre, int_idmodulo, str_comando, str_icono, 
            bol_estado, str_masaccmax, int_orden)
    VALUES ('Codigo Telefono', 31, 'cpc.demeter.comando.ministerio.ComandoCodigoArea', 
	'/iconos/32x32/codigoArea.png', true, '1111000000000000', 3);	
