package data.spring.mybatis.adapter.in.dto;

import jakarta.validation.Valid;

import java.util.List;

public class ProductUpdateBatchRequest {
    List<@Valid ProductUpdateRequest> updateRequests;

    public ProductUpdateBatchRequest(List<ProductUpdateRequest> updateRequests) {
        this.updateRequests = updateRequests;
    }

    public List<ProductUpdateRequest> getUpdateRequests() {
        return this.updateRequests;
    }
}
