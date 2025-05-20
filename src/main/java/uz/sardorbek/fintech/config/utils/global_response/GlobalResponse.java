package uz.sardorbek.fintech.config.utils.global_response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.sardorbek.fintech.user.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Component
public class GlobalResponse {

    public ApiResponse responseGetById(Optional<?> optional) {
        return optional.map(o -> new ApiResponse(HttpStatus.OK, new ResponseObject("Success", o))).orElseGet(() -> new ApiResponse(HttpStatus.NOT_FOUND, new ResponseObject("Not Found", "Element Not Found")));
    }

    public ApiResponse responseCreatedStatus(Object object) {
        return new ApiResponse(HttpStatus.CREATED, new ResponseObject("Created", object));
    }

    public ApiResponse responseCreatedStatus() {
        return new ApiResponse(HttpStatus.CREATED, new ResponseObject("Created"));
    }

    public ApiResponse responseEditedStatus(Object object) {
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Success Updated", object));
    }

    public ApiResponse responseEditedStatus() {
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Success Updated"));
    }

    public ApiResponse responseOKStatus(Object o) {
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Success", o));
    }

    public ApiResponse responseOKStatus() {
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Success"));
    }

    public ApiResponse responseSuccessDelete(Long id) {
        return new ApiResponse(HttpStatus.OK, new ResponseObject("Element Deleted", "Element with id : [" + id + "] is deleted"));
    }

    public String getAuthEmployeeName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "SYSTEM";
    }

    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return (User) auth.getPrincipal();
        }
        return null;
    }

    public ApiResponse responseBadRequest(Object o) {
        return new ApiResponse(HttpStatus.BAD_REQUEST, new ResponseObject("Bad Request", o));
    }
    public ApiResponse responseMethodNotAllowed(Object o) {
        return new ApiResponse(HttpStatus.METHOD_NOT_ALLOWED, new ResponseObject("Method is not allowed", o));
    }

    public ApiResponse responseBadRequest() {
        return new ApiResponse(HttpStatus.BAD_REQUEST, new ResponseObject("Bad Request"));
    }

    public ApiResponse responseNotFound(Object o) {
        return new ApiResponse(HttpStatus.NOT_FOUND, new ResponseObject("Not found", o));
    }

    public ApiResponse responseNotFound() {
        return new ApiResponse(HttpStatus.NOT_FOUND, new ResponseObject("Not found"));
    }

    public ApiResponse responseNotFound(Long id) {
        return new ApiResponse(HttpStatus.NOT_FOUND, new ResponseObject("Not found", new Object[]{"Element with id : [" + id + "] is not found"}));
    }

    public ApiResponse responseAlreadyExist(Object o) {
        return new ApiResponse(HttpStatus.BAD_REQUEST, new ResponseObject("Already exist", o));
    }

    public ApiResponse responseAlreadyExist() {
        return new ApiResponse(HttpStatus.BAD_REQUEST, new ResponseObject("Already exist"));
    }

    public PageRequest pageRequest(Integer page, String column, Integer itemsPerPage) {
        return PageRequest.of(
                page == null ? 0 : page,
                itemsPerPage != null ? itemsPerPage : 15,
                Sort.by(column != null ? column : "id").descending()
        );
    }

    public PageRequest pageRequestAsc(Integer page, String column, Integer itemsPerPage) {
        return PageRequest.of(
                page == null ? 0 : page,
                itemsPerPage != null ? itemsPerPage : 15,
                Sort.by(column != null ? column : "id").ascending()
        );
    }

    public PageRequest pageRequest(Integer page, Integer itemsPerPage) {
        return PageRequest.of(
                page == null ? 0 : page,
                itemsPerPage != null ? itemsPerPage : 15
        );
    }

    public Page<?> pageable(List<?> list, Integer page, Integer itemsPerPage) {
        if (page == null) page = 0;

        Pageable pageable;
        pageable = PageRequest.of(
                page,
                Objects.requireNonNullElse(itemsPerPage, 15), Sort.by("id").descending()
        );
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public ApiResponse responseWithList(List<?> list) {
        if (!list.isEmpty()) {
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Success", new ContentObject(list)));
        } else {
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Empty", new ContentObject(new ArrayList<>())));
        }
    }

    public ApiResponse responseWithList(Page<?> list) {
        if (!list.isEmpty()) {
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Success", list));
        } else {
            return new ApiResponse(HttpStatus.OK, new ResponseObject("Empty", new ContentObject(new ArrayList<>())));
        }
    }

    public Pageable makeSorted(Pageable pageable) {
        if (pageable == null || pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable == null ? 0 : pageable.getPageNumber(),
                    pageable == null ? 15 : pageable.getPageSize(),
                    Sort.by("id").descending()
            );
        }
        return pageable;
    }

}
