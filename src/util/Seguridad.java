package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cpc.modelo.demeter.sincronizacion.SedeDemeter;

public class Seguridad {

	public static String getMD5(String input) 
	{
	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      byte[] messageDigest = md.digest(input.getBytes());
	      BigInteger number = new BigInteger(1, messageDigest);
	      String hashtext = number.toString(16);
	      while (hashtext.length() < 32) {
	          hashtext = "0" + hashtext;
	      }
	      return hashtext;
	    }
	    catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	}
	
	
	
	public static  String   getMD5Sede(SedeDemeter sede)
	{
		String md5 = new String("PEDROCAMEJO-");
		md5.concat(sede.getIdSede().concat(sede.getSede()).trim());
		md5 = md5.toUpperCase();
		return getMD5(md5);
	}
	
}
