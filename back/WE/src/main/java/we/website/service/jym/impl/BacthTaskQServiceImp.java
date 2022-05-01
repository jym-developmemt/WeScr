package we.website.service.jym.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchtaskQueryRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchtaskQueryRequest.GoodsBatchTaskQueryDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchtaskQueryResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchtaskQueryResponse.GoodsBatchTaskResultDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchtaskQueryResponse.GoodsBatchSubTask;

import we.base.base.BaseService;
import we.base.util.CommonUtil;
import we.base.util.TokenUtils;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.dao.JymBatchHdDao;
import we.website.model.jym.BatchDtlModel;
import we.website.model.jym.BatchHdModel;
import we.website.service.jym.BatchTaskQService;;

/**
 * 
 * @author dell
 *
 */
@Service
public class BacthTaskQServiceImp extends BaseService implements BatchTaskQService {

	protected static Logger logger = LoggerFactory.getLogger(BacthTaskQServiceImp.class);

	@Value("${jym.api_exec_enable}")
	private boolean jymExecEnable;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;
	
	@Autowired
	private JymBatchHdDao jymBatchHdDao;
	
	@Override
	public boolean execBatchTask(List<Map<String,String>> batchIds) {
		return this.execTaobaoApi(batchIds);
	}
	
	/**
	 * 定时查询
	 */
	@Scheduled(cron = "2 * * * * ?")
	public void sendNotice() {
		// 用户认证
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());
		}
		
		List<Map<String, String>> batchIds = new ArrayList<>();
		
		// 批处理明细表更新返回参数
		List<BatchHdModel> result = jymBatchHdDao.selectNoGoodsId();
		
		if (result.size() > 0) {
			for (BatchHdModel tmpData : result) {	
				Map<String, String> tmpIds = new HashMap<>();
				
				tmpIds.put("external_batch_id", tmpData.getExternalBatchId());
				tmpIds.put("batch_id", tmpData.getBatchId());
				batchIds.add(tmpIds);
			}
		}
		
		//this.execTaobaoApi(batchIds); 
	}
	
	/**
	 * 向交易猫批量查询商品
	 * 
	 * @param batchId
	 * @return
	 */
	private boolean execTaobaoApi(List<Map<String,String>> batchIds) {
		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET);

		// 批量发布商品请求参数
		AlibabaJymItemExternalGoodsBatchtaskQueryRequest req;

		GoodsBatchTaskQueryDto commandDto;

		if (!jymExecEnable) {
			return true;
		}
		
		for (Map<String,String> batchIdInfo : batchIds) {
			if (batchIdInfo.get("batch_id").isEmpty()) {
				continue;
			}
			
			 req = new AlibabaJymItemExternalGoodsBatchtaskQueryRequest();
			 commandDto = new GoodsBatchTaskQueryDto();
			
			// Batch ID
			commandDto.setBatchId(Long.valueOf(batchIdInfo.get("batch_id")));
			
			req.setGoodsBatchTaskQuery(commandDto);

			try {
				// 执行商品查询
				AlibabaJymItemExternalGoodsBatchtaskQueryResponse rsp = client.execute(req);
	
				// 获取返回参数
				GoodsBatchTaskResultDto batchResult = rsp.getResult();
				List <GoodsBatchSubTask> subTaskList = batchResult.getGoodsBatchSubTaskList();
				
				for (GoodsBatchSubTask subTask : subTaskList) {
					// 设置批处理明细返回参数
					BatchDtlModel batchDtlModel = new BatchDtlModel();	
					batchDtlModel.setExternalBatchId(batchIdInfo.get("external_batch_id"));
					batchDtlModel.setExternalGoodsId(subTask.getExternalGoodsId());
					batchDtlModel.setGoodsId(CommonUtil.toString(subTask.getGoodsId()));					
					batchDtlModel.setReason(subTask.getReason());
					
					// 批处理明细表更新返回参数
					jymBatchDtlDao.updateBatchDtl(batchDtlModel);
				}
									
				logger.info(rsp.getBody());
			} catch (ApiException e) {
				logger.info(e.getErrMsg());
				break;
			}
		}
		return true;
	}

}
