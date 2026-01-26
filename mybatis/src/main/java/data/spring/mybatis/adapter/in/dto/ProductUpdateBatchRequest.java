package data.spring.mybatis.adapter.in.dto;

import data.spring.mybatis.application.service.command.ProductUpdateCommand;
import jakarta.validation.Valid;

import java.util.List;

public record ProductUpdateBatchRequest(
        List<@Valid ProductUpdateRequest> updateRequests
) {
    public List<ProductUpdateCommand> toCommands() {
        return this.updateRequests.stream()
                .map(request ->
                        ProductUpdateCommand.of(
                                request.productId(),
                                request.productName(),
                                request.price(),
                                request.quantity()
                ))
                .toList();
    }
}
