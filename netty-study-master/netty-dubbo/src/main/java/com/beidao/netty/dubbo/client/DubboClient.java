package com.beidao.netty.dubbo.client;

import com.beidao.netty.dubbo.facade.api.IUserFacade;

/**
 * dubbo客户端(消费者)
 * @author 0200759
 *
 */
public class DubboClient {
 
	public static void main(String[] args){
		IUserFacade userFacade = DubboProxy.getProxyInstance(IUserFacade.class);
		
		System.out.println(userFacade.getUserName(520L));
		System.out.println(userFacade.getUserName(1314L));
		System.out.println(userFacade.getUserName(1314520L));
	}
}
