package com.campers.camfp.repository.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.QBoard;
import com.campers.camfp.entity.board.QReply;
import com.campers.camfp.entity.member.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BoardQuerydslImpl extends QuerydslRepositorySupport implements BoardQuerydsl {
   
   public BoardQuerydslImpl() {
      super(Board.class);
   }

   @Override
   public Board searchBoard() {
      log.info("search------");
      
      return null;
   }

   @Override
   public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
      log.info("--------------- searchPage() -----------------");
      
      QBoard board = QBoard.board;
      QReply reply = QReply.reply;
      QMember member = QMember.member;
      
      JPQLQuery<Board> jpqlQuery = from(board);
      jpqlQuery.leftJoin(member).on(board.member.eq(member));
      jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
      
      JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
      
      BooleanBuilder booleanBuilder = new BooleanBuilder();
      BooleanExpression expression = board.bno.gt(0L); // board의 bno가 0L보다 클 때
      
      booleanBuilder.and(expression);
      
      if (type == null || "".equals("")) {
         type = "twc";
      }
      
      String[] typeArr = type.split("");
      BooleanBuilder conditionBuilder = new BooleanBuilder();
      for(String t : typeArr) {
         switch(t) {
         case "t" :
            conditionBuilder.or(board.title.contains(keyword));
            break;
         case "w" :
            conditionBuilder.or(member.nickname.contains(keyword));
            break;
         case "c" :
            conditionBuilder.or(board.content.contains(keyword));
            break;
         }
         
      }
      
      booleanBuilder.and(conditionBuilder);
      
      tuple.where(booleanBuilder);
      
      Sort sort = pageable.getSort();
      sort.stream().forEach(order -> {
       Order derection = order.isAscending() ? Order.ASC : Order.DESC; // 정렬 기준 조건
       String prop = order.getProperty();
       
       PathBuilder orderByExpression = new PathBuilder<>(Board.class, "board");
       
       tuple.orderBy(new OrderSpecifier<>(derection, orderByExpression.get(prop)));
      });
      
      tuple.groupBy(board);
//      jpqlQuery.select(board,member.nickname, reply.count()).groupBy(board);
      
      tuple.offset((pageable.getOffset()));
      tuple.limit(pageable.getPageSize());
      
      log.info("-----------------------------");
      log.info("tuple : " + tuple);
      log.info("-----------------------------");
      
      List<Tuple> result = tuple.fetch(); // 검색 결과 리스트
      log.info("result : " + result);   
      
      long count = tuple.fetchCount();
      log.info("count : " + count);
      
      return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
   }
   
}