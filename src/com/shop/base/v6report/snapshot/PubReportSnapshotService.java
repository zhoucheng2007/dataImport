package com.shop.base.v6report.snapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import com.shop.base.service.BaseServiceImpl;
/**
 * @title: 报表快照 Service 实现类
 * @author: sun_zhen
 * @data: 2013-06-19
 * @修改:
 *
 */
public class PubReportSnapshotService  extends BaseServiceImpl implements IPubReportSnapshotService {
	
	private static Log log = LogFactory.getLog(PubReportSnapshotService.class);
	
	
	private static IPubReportSnapshotDomain pubReportSnapshotDomain = (IPubReportSnapshotDomain) ComponentFactory
			.getBean("pubReportSnapshotDomain");

	@Override
	protected void initService() {
		// TODO Auto-generated method stub
		
	}

	public Map insertReportSnapshot(final Map map) {
		getTransactionTemplate().execute(
				new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(
							TransactionStatus arg0) {
						pubReportSnapshotDomain.insertReportSnapshot(map);
					}
		});
		return map;
	}



 

}

