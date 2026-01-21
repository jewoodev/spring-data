package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.adapter.in.dto.ProductSearchCond;
import data.spring.mybatis.adapter.in.dto.ProductUpdateDto;
import data.spring.mybatis.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {
    void save(Product product);

    void update(@Param("productId") Long productId, @Param("updateParam") ProductUpdateDto updateDto);

    Optional<Product> findById(Long productId);

    List<Product> findAll(ProductSearchCond searchCond);

    int deleteAll();
}
