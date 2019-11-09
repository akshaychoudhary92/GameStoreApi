package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.ProcessingFee;

public interface ProcessingFeeDao {
    ProcessingFee getProcessingFee(String productType);
}
