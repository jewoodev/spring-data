package data.spring.mybatis.application.provided.product

import data.spring.mybatis.application.service.product.command.ProductUpdateCommand

interface ProductUseCaseEx {
    fun updateList(updateCommands: List<ProductUpdateCommand>)
}