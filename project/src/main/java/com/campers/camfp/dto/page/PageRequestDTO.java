package com.campers.camfp.dto.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
   
   private int page;
   private int size;
//   private String type;   search 관련 내용
//   private String keyword;
   
   public PageRequestDTO() {
      this.page = 1;
      this.size = 15;
   }
   
   public Pageable getPageable(Sort sort) {
      return PageRequest.of(page -1, size, sort);
   }

}