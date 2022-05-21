package we.website.service.jym.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
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
import com.taobao.api.request.AlibabaJymItemExternalGoodsStatusBatchQueryRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsStatusBatchQueryRequest.BatchGoodsStatusQueryDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse.BatchGoodsStatusResultDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse.GoodsStatusDto;

import we.base.base.BaseService;
import we.base.util.CommonUtil;
import we.base.util.TokenUtils;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.dao.JymGoodsEntityDao;
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
	
	@Value("${jym.auto_updStatus_enable}")
	private boolean jymAutoUpdStatusEnable;
	
	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;
	
	@Autowired
	private JymGoodsEntityDao jymGoodsEntityDao;
	
	@Override
	public boolean execStatusBatch(List<String> goodsIds) {
		return this.execTaobaoApi(goodsIds);
	}
	
	/**
	 * 定时查询
	 */
	@Scheduled(cron = "10 * * * * ?")
	public void autoBatchTaskQ() {
		
		if (!jymAutoUpdStatusEnable) {
			return;
		}
		
		// 用户认证
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());
		}
		
		List<String> goodsIds = new ArrayList<>();
		
		// 批量查询商品对象
		goodsIds = jymGoodsEntityDao.selectGoodsIdData();
		
		if (goodsIds.size() > 0) {
			this.execTaobaoApi(goodsIds); 
		}
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

		List<Long> goodsIdList = goodsIds.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
		
		commandDto.setGoodsIdList(goodsIdList);
		
		req.setBatchGoodsStatusQuery(commandDto);
		
		if (!jymExecEnable) {
			return true;
		}
		
		try {
			// 执行商品查询
			AlibabaJymItemExternalGoodsStatusBatchQueryResponse rsp = client.execute(req);

			// 获取返回参数
			BatchGoodsStatusResultDto batchResult = rsp.getResult();
			List <GoodsStatusDto> goodsStatusList = batchResult.getGoodsStatusList();
			
			for (GoodsStatusDto goodsStauts : goodsStatusList) {
				// 设置批处理明细返回参数
				BatchDtlModel batchDtlModel = new BatchDtlModel();
				batchDtlModel.setWhereGoodsId(CommonUtil.toString(goodsStauts.getGoodsId()));
				
				int goodsStatus = Math.toIntExact(goodsStauts.getStatus());
				batchDtlModel.setGoodsStatus(goodsStatus);
				
				switch(goodsStatus ) {
					case 1:
						batchDtlModel.setReason("公司审核");
						break;
					case 2:
						batchDtlModel.setReason("待运营审核");
						break;
					case 3:
						batchDtlModel.setReason("已上架");
						break;
					case 4:
						batchDtlModel.setReason("您⾃⼰操作下架");
						break;
					case 5:
						batchDtlModel.setReason("超出商品有效期");
						break;
					case 6:
						batchDtlModel.setReason("商品已经销售完毕");
						break;
					case 7:
						batchDtlModel.setReason("商品信息审核不通过");
						break;
					case 8:
						batchDtlModel.setReason("商品审核下架");
						break;
					case 9:
						batchDtlModel.setReason("商品暂停交易");
						break;
					case 10:
						batchDtlModel.setReason("商品发布撤回");
						break;
					case 11:
						batchDtlModel.setReason("待提交");
						break;
					case 12:
						batchDtlModel.setReason("已删除");
						break;
					case 13:
						batchDtlModel.setReason("账号安全审核不通过");
						break;
					case 14:
						batchDtlModel.setReason("系统补全信息超时下架");
						break;
					case 15:
						batchDtlModel.setReason("系统补全信息不完整下架");
						break;
					case 16:
						batchDtlModel.setReason("待系统补全");
						break;
						
				}
				
				batchDtlModel.setUpdateAt(DateTime.now().toDate());
							
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
