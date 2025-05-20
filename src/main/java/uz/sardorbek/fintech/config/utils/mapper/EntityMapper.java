package uz.sardorbek.fintech.config.utils.mapper;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    @Named("isNotNullMethod")
    default boolean isNotNull(Object value) {
        return value != null;
    }

    default Page<E> toEntityPage(List<E> entityList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), entityList.size());
        List<E> subList = entityList.subList(start, end);
        return new PageImpl<>(subList, pageable, entityList.size());
    }
}
