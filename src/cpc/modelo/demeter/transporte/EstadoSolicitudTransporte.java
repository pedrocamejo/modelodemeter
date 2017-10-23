package cpc.modelo.demeter.transporte;

import java.io.Serializable;

//Espera 1  Atendida 2 termianda 3  Cancelada 4   

public class EstadoSolicitudTransporte  implements Serializable{
 
	
  static public  String		ESTADOS[] = {"nueva","rechazada","aceptada","procesada","anulada"};

  static public  Integer 	NUEVA  	=  1;
  static public  Integer 	CANCELADA  	=  2;
  static public  Integer 	ACEPTADA  	=  3;
  static public  Integer 	PROCESADA 	=  4;
  static public  Integer 	ANULADA 	=  5;
  
 static public  String estado(Integer i)
  {
	  return ESTADOS[i-1];
  }
}
