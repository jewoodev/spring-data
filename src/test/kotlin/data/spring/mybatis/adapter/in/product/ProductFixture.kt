package data.spring.mybatis.adapter.`in`.product

import data.spring.mybatis.domain.product.Price
import data.spring.mybatis.domain.product.Product
import data.spring.mybatis.domain.product.ProductName
import data.spring.mybatis.domain.product.Quantity

class ProductFixture {
    companion object {
        fun createProducts(
            startIdx: Int = 1,
            number: Int // 생성할 상품의 개수
        ): List<Product> {
            val products = mutableListOf<Product>()
            for (i in startIdx..startIdx + number) {
                products.add(
                    Product(i.toLong(),ProductName("testProduct$i"), Price(1000 * i), Quantity(100 * i))
                )
            }
            return products
        }
    }
}