package we.website.service.jym.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaJymItemExternalGoodsStatusBatchQueryRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsStatusBatchQueryRequest.BatchGoodsStatusQueryDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse.BatchGoodsStatusResultDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse.GoodsStatusDto;

import we.base.base.BaseService;
import we.base.util.CommonUtil;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.model.jym.BatchDtlModel;
import we.website.service.jym.StautsBatchQService;

/**
 * 
 * @author dell
 *
 */
@Service
public class StatusBacthQServiceImp extends BaseService implements StautsBatchQService {

	protected static Logger logger = LoggerFactory.getLogger(StatusBacthQServiceImp.class);

	@Value("${jym.api_exec_enable}")
	private boolean jymExecEnable;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;
	
	@Override
	public boolean execStatusBatch(List<String> goodsIds) {
		return this.execTaobaoApi(goodsIds);
	}

	/**
	 * 向交易猫批量查询商品状态
	 * 
	 * @param goodsIds
	 * @return
	 */
	private boolean execTaobaoApi(List<String> goodsIds) {
		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET);

		// 批量发布商品请求参数
		AlibabaJymItemExternalGoodsStatusBatchQueryRequest req = new AlibabaJymItemExternalGoodsStatusBatchQueryRequest();

		BatchGoodsStatusQueryDto commandDto = new BatchGoodsStatusQueryDto();

		if (!jymExecEnable) {
			return true;
		}
		
		List<Long> goodsIdList = goodsIds.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
		
		commandDto.setGoodsIdList(goodsIdList);
		
		req.setBatchGoodsStatusQuery(commandDto);
		
		try {
			// 执行商品查询
			AlibabaJymItemExternalGoodsStatusBatchQueryResponse rsp = client.execute(req);

			// 获取返回参数
			BatchGoodsStatusResultDto batchResult = rsp.getResult();
			List <GoodsStatusDto> goodsStatusList = batchResult.getGoodsStatusList();
			
			for (GoodsStatusDto goodsStauts : goodsStatusList) {
				// 设置批处理明细返回参数
				BatchDtlModel batchDtlModel = new BatchDtlModel();
				batchDtlModel.setGoodsId(CommonUtil.toString(goodsStauts.getGoodsId()));
				batchDtlModel.setGoodsStatus(Math.toIntExact(goodsStauts.getStatus()));
							
				// 批处理明细表更新返回参数
				jymBatchDtlDao.updateBatchDtl(batchDtlModel);
			}
							
			logger.info(rsp.getBody());
			return true;
		} catch (ApiException e) {
			logger.info(e.getErrMsg());
			return false;
		}
	}

}
