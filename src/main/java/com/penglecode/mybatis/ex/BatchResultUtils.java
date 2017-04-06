package com.penglecode.mybatis.ex;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;

public class BatchResultUtils {

	public static void extractBatchResult(List<Integer> batchResultList, List<BatchResult> currentBatchResults) {
		if(currentBatchResults != null && !currentBatchResults.isEmpty()){
			for(BatchResult batchResult : currentBatchResults){
				int[] updateCounts = batchResult.getUpdateCounts();
				if(updateCounts != null && updateCounts.length > 0){
					for(int n = 0; n < updateCounts.length; n++){
						batchResultList.add(updateCounts[n]);
					}
				}
			}
		}
	}
	
	public static int[] toPrimitive(List<Integer> batchResultList) {
		int len = batchResultList.size();
		int[] updates = new int[len];
		for(int i = 0; i < len; i++){
			updates[i] = batchResultList.get(i);
		}
		batchResultList = null;
		return updates;
	}
	
}
