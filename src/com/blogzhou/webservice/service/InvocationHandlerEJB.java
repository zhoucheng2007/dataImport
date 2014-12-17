package com.blogzhou.webservice.service;

import java.lang.reflect.*;

public class InvocationHandlerEJB implements InvocationHandler {
	private Object homeStub;

	public InvocationHandlerEJB(Object obj) {
		homeStub = obj;
	}

	public Object invoke(Object proxy, Method method, Object[] args) {
		Object returnValue = null;
		Class[] argTypes = null;
		try {
			Object remoteStub = homeStub.getClass().getMethod("create", null)
					.invoke(homeStub, null);
			// �õ�remote stub�ϵ�Ҫ���õ�ҵ�񷽷��Ĳ�������
			if (args != null) {
				argTypes = new Class[args.length];
				for (int i = 0; i < args.length; i++) {
					argTypes[i] = args[i].getClass();
				}
			}
			// ����remote stub�ϵ�ҵ�񷽷�
			returnValue = remoteStub.getClass().getMethod(method.getName(),
					argTypes).invoke(remoteStub, args);
			// ����remote stub�ϵ�remove����
			remoteStub.getClass().getMethod("remove", null).invoke(remoteStub,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}