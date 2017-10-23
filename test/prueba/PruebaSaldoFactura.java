package prueba;

import org.zkoss.zul.ListModelArray;

public class PruebaSaldoFactura {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Double factura = new Double( 10528);
		Double recibo = new Double(150001306.24);
		
		//calcular el monto aportador por dicho recibo 
		Double diferencia =  (double) (factura - recibo);
		//calculos 
		System.out.println("Factura : "+(diferencia<= 0 ? 0: diferencia));
		//saldo nuevo del recibo lo que le queda 
		Double montorecibo =(diferencia< 0 ? diferencia * -1: 0);
		// la diferencia aplicada a la factura 
		System.out.println("Recibo  :"+ montorecibo);

		System.out.println("Recibo Aplicado  :"+ (recibo - montorecibo));

		
		/*		Double diferencia =  getModelo().getMontoSaldo() - recibo.getRecibo().getSaldo();
		//calculos 
		saldo.setValue((diferencia< 0 ? 0: diferencia));
		//saldo nuevo del recibo lo que le queda 
		Double montorecibo =(diferencia< 0 ? diferencia * -1: 0);
		recibo.getRecibo().setSaldo(montorecibo);
		// la diferencia aplicada a la factura 
		recibo.setMonto(recibo.getRecibo().getSaldo() - montorecibo);
*/
		
	}

}
