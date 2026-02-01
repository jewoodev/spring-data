package data.spring.mybatis.application.required.product

import data.spring.mybatis.application.service.product.command.ProductUpdateCommand

interface ProductRepositoryEx {
    fun update(updateCommands: List<ProductUpdateCommand>)
}