package org.callistasoftware.netcare.video.server;

import java.util.Set;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;


/**
 * Video conference application
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
public class VideoConferenceApplication extends ApplicationAdapter {
	
	@Override
	public boolean appStart(IScope scope) {
		System.out.println("==== NETCARE VIDEO SERVER STARTED ====");
		return super.appStart(scope);
	}
	
	@Override
	public void appStop(IScope scope) {
		System.out.println("==== NETCARE VIDEO SERVER STOPPED ====");
		super.appStop(scope);
	}
	
	@Override
	public boolean appConnect(IConnection connection, Object[] args) {
		System.out.println("-- CLIENT CONNECTION --");
		
		final Set<String> attrs = connection.getAttributeNames();
		for (final String s : attrs) {
			System.out.println(s + ": " + connection.getAttribute(s));
		}
		
		return super.appConnect(connection, args);
	}
	
	@Override
	public void appDisconnect(IConnection connection) {
		System.out.println("-- CLIENT DISCONNECT --");
		super.appDisconnect(connection);
	}
	
	@Override
	public boolean appJoin(IClient client, IScope scope) {
		System.out.println("-- CLIENT JOIN --");
		return super.appJoin(client, scope);
	}
	
	@Override
	public void appLeave(IClient client, IScope arg1) {
		System.out.println("-- CLIENT LEAVE --");
		super.appLeave(client, scope);
	}
}
