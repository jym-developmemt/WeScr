package we.website.service.jym.impl;

import java.util.ArrayList;
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
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest.ExternalGoodsIdDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest.GoodsOnSaleCommandDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchOnsaleResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchOnsaleResponse.GoodsBatchResultDto;

import we.base.util.CommonUtil;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.dao.JymBatchHdDao;
import we.website.model.jym.BatchDtlModel;
import we.website.model.jym.BatchHdModel;
import we.website.service.jym.BatchOnsaleService;

@Service
public class BatchOnsaleServiceImp implements BatchOnsaleService {

	protected static Logger logger = LoggerFactory.getLogger(BatchOnsaleServiceImp.class);

	@Value("${jym.api_exec_enable}")
	private boolean jymExecEnable;

	@Autowired
	private JymBatchHdDao jymBatchHdDao;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;

	@Override
	public boolean execGoodsOnsale(List<Map<String, String>> goodsIdList) {

		// 外部批次ID
		String externalBatchId = CommonUtil.generateKey();

		// 执行交易猫外部商家批量上架商品接口
		boolean isSucceed = execTaobaoApi(externalBatchId, goodsIdList);

		// 批处理执行成功
		if (isSucceed) {

			List<BatchDtlModel> dtlList = new ArrayList<>();

			for (Map<String, String> goodsIdMap : goodsIdList) {

				BatchDtlModel dtlModel = new BatchDtlModel();

				// 交易猫商品ID
				dtlModel.setGoodsId(goodsIdMap.get("goods_id"));

				// 上架商品ID
				dtlModel.setExternalGoodsId(goodsIdMap.get("external_goods_id"));

				// 外部批次ID
				dtlModel.setExternalBatchId(externalBatchId);

				dtlList.add(dtlModel);
			}

			jymBatchDtlDao.updateExternalBatchId(dtlList);
		}

		return isSucceed;
	}

	/**
	 * 
	 * @param externalBatchId
	 * @param goodsIdList
	 * @return
	 */
	private boolean execTaobaoApi(String externalBatchId, List<Map<String, String>> goodsIdList) {

		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET);

		// 批量上传商品请求参数
		AlibabaJymItemExternalGoodsBatchOnsaleRequest req = new AlibabaJymItemExternalGoodsBatchOnsaleRequest();

		// 业务入参对象
		GoodsOnSaleCommandDto commandDto = new GoodsOnSaleCommandDto();

		// 外部批次ID
		commandDto.setExternalBatchId(externalBatchId);

		// 批量上架商品id集合
		List<ExternalGoodsIdDto> dtoList = new ArrayList<ExternalGoodsIdDto>();

		// 上架商品数
		int goodsCnt = 0;

		for (Map<String, String> goodsIdMap : goodsIdList) {

			ExternalGoodsIdDto goodsIdDto = new ExternalGoodsIdDto();

			// 上架商品ID
			goodsIdDto.setExternalGoodsId(goodsIdMap.get("external_goods_id"));

			// 交易猫商品ID
			goodsIdDto.setGoodsId(Long.valueOf(goodsIdMap.get("goods_id")));

			dtoList.add(goodsIdDto);

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

			// 批处理表添加返回参数
			jymBatchHdDao.insertBatchHd(batchHdModel);

			logger.info(rsp.getBody());
			return rsp.isSuccess();
		} catch (ApiException e) {

			logger.error(e.getErrMsg());
			return false;
		}
	}
}
