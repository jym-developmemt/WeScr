package we.website.service.jym.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest.ExternalGoodsIdDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest.GoodsOnSaleCommandDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchOnsaleResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchOnsaleResponse.GoodsBatchResultDto;

import we.base.util.CommonUtil;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.model.jym.BatchDtlModel;
import we.website.model.jym.BatchHdModel;
import we.website.service.jym.BatchOnsaleService;

@Service
public class BatchOnsaleServiceImp implements BatchOnsaleService {

	protected static Logger logger = LoggerFactory.getLogger(BatchOnsaleServiceImp.class);

	@Value("${jym.api_exec_enable}")
	private boolean jymExecEnable;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;

	@Override
	public boolean execGoodsOnsale(List<String> externalGoodsId) {

		// 查询上架的商品
		List<BatchDtlModel> batchDtlList = jymBatchDtlDao.selectGoodsidList(externalGoodsId);

		boolean isSucceed = execTaobaoApi(batchDtlList);
		return isSucceed;
	}

	private boolean execTaobaoApi(List<BatchDtlModel> batchDtlList) {

		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET,
				Constant.RESPONSE_FORMAT);

		// 批量上传商品请求参数
		AlibabaJymItemExternalGoodsBatchOnsaleRequest req = new AlibabaJymItemExternalGoodsBatchOnsaleRequest();

		// 业务入参对象
		GoodsOnSaleCommandDto commandDto = new GoodsOnSaleCommandDto();

		// 外部批次ID
		commandDto.setExternalBatchId(CommonUtil.generateKey());

		// 批量上架商品id集合
		List<ExternalGoodsIdDto> goodsIdList = new ArrayList<ExternalGoodsIdDto>();

		// 上架商品数
		int goodsCnt = 0;

		for (BatchDtlModel dtlModel : batchDtlList) {

			if (dtlModel.getGoodsId().isEmpty()) {
				continue;
			}

			ExternalGoodsIdDto goodsIdDto = new ExternalGoodsIdDto();

			// 上架商品ID
			goodsIdDto.setExternalGoodsId(dtlModel.getExternalBatchId());

			// 交易猫商品ID
			goodsIdDto.setGoodsId(Long.valueOf(dtlModel.getGoodsId()));

			goodsIdList.add(goodsIdDto);

			goodsCnt++;
		}

		req.setGoodsOnSaleCommand(commandDto);

		if (!jymExecEnable) {
			return true;
		}

		try {
			AlibabaJymItemExternalGoodsBatchOnsaleResponse rsp = client.execute(req);

			// 获取返回参数
			GoodsBatchResultDto batchResult = rsp.getResult();

			// 设置批处理返回参数
			BatchHdModel batchHdModel = new BatchHdModel();
			batchHdModel.setExternalBatchId(commandDto.getExternalBatchId());
			batchHdModel.setBatchId(String.valueOf(batchResult.getBatchId()));
			batchHdModel.setProductCnt(goodsCnt);
			batchHdModel.isSucceed(rsp.getSucceed());
			batchHdModel.setStateCode(rsp.getStateCode());
			batchHdModel.setMethodId("2");
			batchHdModel.setExtraErrMsg(rsp.getExtraErrMsg());

			logger.info(rsp.getBody());
			return rsp.isSuccess();
		} catch (ApiException e) {

			logger.error(e.getErrMsg());
			return false;
		}
	}
}
