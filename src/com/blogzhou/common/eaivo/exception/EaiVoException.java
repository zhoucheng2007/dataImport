package com.blogzhou.common.eaivo.exception;

import com.blogzhou.common.exception.CustomException;


/**
 * <p>�걨�ļ���ݶ�������쳣</p>
 * @author <a href="mailto:lindahai@foresee.com.cn">Lin Dahai</a>
 * @version $LastChangedRevision: 5030 $
 */
public class EaiVoException extends CustomException {

	/**
	 * ���л��汾��ʶID
	 */
	private static final long serialVersionUID = 3451324253745739280L;
	
	/**
	 * @see FstaxException
	 */
	public EaiVoException(String arg0, String arg1, Exception arg2) {
		super(arg0, arg1, arg2);
	}

}
