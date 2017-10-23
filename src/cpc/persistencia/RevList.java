package cpc.persistencia;

import org.hibernate.envers.RevisionListener;
 

import org.springframework.transaction.support.TransactionSynchronizationManager;

import cpc.zk.componente.interfaz.IZkAplicacion;


public class RevList implements RevisionListener{

	
	private IZkAplicacion app,appold;

	public synchronized void  newRevision(Object arg0)
	{
		
		
		RevAuditoria revision = (RevAuditoria) arg0;
		int a = revision.getId();
		 
		app = (IZkAplicacion) TransactionSynchronizationManager.getResource("obj");
	if (app!=null){
		String  usuario= app.getNombreUsuario();
		String  ip = app.getIp();
		
		if(usuario != null)
		{
			revision.setUsuario(usuario); 
		    revision.setIp(ip);
		}
		System.out.println("id de revision" +revision.getId());
		}
		revision.setId(a+1);
	}
	
	
	public IZkAplicacion getApp() {
	return app;
	}
	public void setApp(IZkAplicacion appp) {
		this.app = (IZkAplicacion)appp;
	}
	
}
	

