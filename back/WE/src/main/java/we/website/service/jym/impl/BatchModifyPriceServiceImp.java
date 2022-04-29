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
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchModifypriceRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchModifypriceRequest.ExternalGoodsIdDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchModifypriceRequest.GoodsPriceModifyCommandDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchModifypriceRequest.GoodsPriceDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchModifypriceResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchModifypriceResponse.GoodsBatchResultDto;

import we.base.util.CommonUtil;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.dao.JymBatchHdDao;
import we.website.model.jym.BatchDtlModel;
import we.website.model.jym.BatchHdModel;
import we.website.service.jym.BatchModifyPriceService;

@Service
public class BatchModifyPriceServiceImp implements BatchModifyPriceService {

	protected static Logger logger = LoggerFactory.getLogger(BatchModifyPriceServiceImp.class);

	@Value("${jym.api_exec_enable}")
	private boolean jymExecEnable;

	@Autowired
	private JymBatchHdDao jymBatchHdDao;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;

	@Override
	public boolean execGoodsModifyPrice(List<Map<String, String>> goodsIdList) {

		// 执行交易猫外部商家批量上架商品接口
		return this.execTaobaoApi(goodsIdList);
	}

	/**
	 * 
	 * @param externalBatchId
	 * @param goodsIdList
	 * @return
	 */
	private boolean execTaobaoApi(List<Map<String, String>> goodsIdList) {

		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET);
		
		// 外部批次ID
		String externalBatchId = CommonUtil.generateKey();
		
		// 批量上传商品请求参数
		AlibabaJymItemExternalGoodsBatchModifypriceRequest req = new AlibabaJymItemExternalGoodsBatchModifypriceRequest();

		// 业务入参对象
		GoodsPriceModifyCommandDto commandDto = new GoodsPriceModifyCommandDto();

		// 批量上架商品id集合
		List<ExternalGoodsIdDto> dtoList = new ArrayList<ExternalGoodsIdDto>();

		// 上架商品数
		int goodsCnt = 0;
		List<GoodsPriceDto> goodsPriceDtoList = new ArrayList<GoodsPriceDto>();
		
		for (Map<String, String> goodsIdMap : goodsIdList) {

			ExternalGoodsIdDto goodsIdDto = new ExternalGoodsIdDto();
			GoodsPriceDto goodsPriceDto = new GoodsPriceDto();

			// 上架商品ID
			goodsIdDto.setExternalGoodsId(goodsIdMap.get("external_goods_id"));

			// 交易猫商品ID
			goodsIdDto.setGoodsId(Long.valueOf(goodsIdMap.get("goods_id")));

			goodsPriceDto.setExternalGoodsId(goodsIdDto);
			goodsPriceDto.setPrice(goodsIdMap.get("price"));
			
			goodsPriceDtoList.add(goodsPriceDto);
			dtoList.add(goodsIdDto);

			goodsCnt++;
		}
		
		// 外部批次ID
		commandDto.setExternalBatchId(externalBatchId);
		// 商品价格传输对象
		commandDto.setGoodsPriceList(goodsPriceDtoList);

		req.setGoodsPriceModifyCommand(commandDto);

		if (!jymExecEnable) {
			return true;
		}

		try {
			AlibabaJymItemExternalGoodsBatchModifypriceResponse rsp = client.execute(req);

			// 获取返回参数
			GoodsBatchResultDto batchResult = rsp.getResult();

			// 设置批处理返回参数
			BatchHdModel batchHdModel = new BatchHdModel();
			batchHdModel.setExternalBatchId(commandDto.getExternalBatchId());
			batchHdModel.setBatchId(String.valueOf(batchResult.getBatchId()));
			batchHdModel.setProductCnt(goodsCnt);
			batchHdModel.isSucceed(rsp.getSucceed());
			batchHdModel.setStateCode(rsp.getStateCode());
			batchHdModel.setMethodId("1");
			batchHdModel.setExtraErrMsg(rsp.getExtraErrMsg());

			// 批处理表添加返回参数
			jymBatchHdDao.insertBatchHd(batchHdModel);

			// 批处理表明细更新新的external_goods_id
			List<BatchDtlModel> dtlList = new ArrayList<>();
			for (Map<String, String> goodsIdMap : goodsIdList) {

				BatchDtlModel dtlModel = new BatchDtlModel();

				// 上架商品ID
				dtlModel.setExternalGoodsId(goodsIdMap.get("external_goods_id"));

				// 外部批次ID
				dtlModel.setExternalBatchId(externalBatchId);

				dtlList.add(dtlModel);
			}

			jymBatchDtlDao.updateExternalBatchId(dtlList);
			
			logger.info(rsp.getBody());			
			return rsp.isSuccess();
		} catch (ApiException e) {

			logger.error(e.getErrMsg());
			return false;
		}
	}
}
