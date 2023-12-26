package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.community.dto.CommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import static com.likelion.boomarble.domain.community.dto.CommentResponseDTO.convertCommentToDto;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentCustomRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CommentResponseDTO> findByBoardId(Long postId) {
        String jpqlQuery =  "SELECT c FROM Comment c " +
                            "LEFT JOIN FETCH c.parent " +
                            "WHERE c.community.id IS NOT NULL AND c.community.id = :postId " +
                            "ORDER BY c.parent.id ASC NULLS FIRST, c.createdAt ASC";

        List<Comment> comments = entityManager.createQuery(jpqlQuery, Comment.class)
                .setParameter("postId", postId)
                .getResultList();
        System.out.println(comments);

        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        Map<Long, CommentResponseDTO> commentDTOHashMap = new HashMap<>();

        for (Comment c : comments){
            CommentResponseDTO commentResponseDTO = convertCommentToDto(c);
            commentDTOHashMap.put(commentResponseDTO.getId(), commentResponseDTO);

            if (c.getParent() != null){
                commentDTOHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDTO);
            } else {
                commentResponseDTOList.add(commentResponseDTO);
            }
        }


        return commentResponseDTOList;
    }

    @Override
    public Optional<Comment> findCommentByBIdWithParent(Long id) {
        String jpql =   "SELECT c FROM Comment c " +
                        "LEFT JOIN FETCH c.parent " +
                        "WHERE c.id = :id";
        Query query = entityManager.createQuery(jpql, Comment.class);
        query.setParameter("id", id);

        Comment selectedComment = (Comment) query.getSingleResult();

        return Optional.ofNullable(selectedComment);
    }
}
