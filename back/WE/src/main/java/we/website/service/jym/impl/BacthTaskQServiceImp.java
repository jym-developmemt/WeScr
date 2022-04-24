package we.website.service.jym.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import we.website.constant.Constant;
import we.website.dao.JymBatchHdDao;
import we.website.dao.JymBatchDtlDao;
import we.website.model.jym.BatchHdModel;
import we.website.model.jym.BatchDtlModel;
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
	private JymBatchHdDao jymBatchHdDao;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;
	
	@Override
	public boolean execBatchTask(List<Map<String,String>> batchIds) {
		return this.execTaobaoApi(batchIds);
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

		for (Map<String,String> batchIdInfo : batchIds) {
			if (batchIdInfo.get("batch_id").isEmpty()) {
				continue;
			}
			
			 req = new AlibabaJymItemExternalGoodsBatchtaskQueryRequest();
			 commandDto = new GoodsBatchTaskQueryDto();
			
			// Batch ID
			commandDto.setBatchId(Long.valueOf(batchIdInfo.get("batch_id")));
			
			req.setGoodsBatchTaskQuery(commandDto);

			if (!jymExecEnable) {
				return true;
			}

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
					batchDtlModel.setExternalGoodsId(batchIdInfo.get("external_goods_id"));
					batchDtlModel.setExternalGoodsId(CommonUtil.toString(subTask.getGoodsId()));					
					
					// 批处理明细表更新返回参数
					jymBatchDtlDao.updateBatchDtl(batchDtlModel);
				}
								
				// 设置批处理返回参数
				BatchHdModel batchHdModel = new BatchHdModel();
				batchHdModel.isSucceed(rsp.getSucceed());
				batchHdModel.setStateCode(rsp.getStateCode());
				batchHdModel.setMethodId("3");
				batchHdModel.setExternalBatchId(batchIdInfo.get("external_batch_id"));
				
				// 批处理表更新返回参数
				jymBatchHdDao.updateBatchHd(batchHdModel);
	
				logger.info(rsp.getBody());
			} catch (ApiException e) {
				logger.info(e.getErrMsg());
				break;
			}
		}
		return true;
	}

}
