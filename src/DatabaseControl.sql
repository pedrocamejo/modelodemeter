
alter table administracion.tbl_dem_control_sede add  CONSTRAINT fk_tbl_dem_control_series2
unique(seq_control);

ALTER TABLE administracion.tbl_dem_control_sede add str_cuentaclientepago character(25);
/****************************Script nuevo*************************************/
/*************demeter****************/
ALTER table administracion.tbl_dem_control_sede add str_cuentaCaja character varying(25);
ALTER table administracion.tbl_dem_control_sede drop dte_fecha_cierre_dia;
ALTER table administracion.tbl_dem_control_sede add int_notacredito integer;
ALTER table administracion.tbl_dem_control_sede add int_notadebito integer;
ALTER TABLE administracion.tbl_dem_cierrediario add int_control integer
ALTER TABLE administracion.tbl_dem_cierrediario add int_control integer;


ALTER TABLE administracion.tbl_dem_control_sede
  add int_factura integer,
  add int_controlnotadebito integer,
  add int_controlnotacredito integer;
  
alter table administracion.tbl_dem_control_sede 
add int_controlcontrato integer default 0;

ALTER TABLE administracion.tbl_dem_control_sede
ADD int_idcontroldocumento int,
ADD bol_controlunico boolean default true;
    