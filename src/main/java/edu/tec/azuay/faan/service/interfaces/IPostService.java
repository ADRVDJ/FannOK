package edu.tec.azuay.faan.service.interfaces;

import edu.tec.azuay.faan.persistence.dto.primary.LikedPost;
import edu.tec.azuay.faan.persistence.dto.primary.SavePost;
import edu.tec.azuay.faan.persistence.entity.Post;
import edu.tec.azuay.faan.persistence.utils.PostState;
import edu.tec.azuay.faan.persistence.utils.PostType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;

public interface IPostService {

    SavePost save(SavePost newPost, MultipartFile file) throws IOException;

    SavePost updatePostReference(String id, MultipartFile file) throws IOException;

    SavePost update(SavePost newPost);

    public Page<SavePost> getPosts(int pageNumber, int pageSize);

    public Post getPostById(String id);

    public void deletePost(String id);

    public void updatePost(SavePost post);

    public void updateStatePost(String id, PostState state);

    public String likePost(LikedPost likedPost);

    public Page<SavePost> getLikedPostsByAuthorId(String id, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByType(PostType type, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByState(PostState state, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByTypeAndState(PostType type, PostState state, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByUser(String user, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByUserId(String userId, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByTypeAndUser(PostType type, String user, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByStateAndUser(PostState state, String user, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByTypeAndStateAndUser(PostType type, PostState state, String user, int pageNumber, int pageSize);

    public Page<SavePost> getPostsByTypeAndStateAndDate(PostType type, PostState state, LocalDateTime date, int pageNumber, int pageSize);

    public SavePost getSavePostById(String id);

    public Page<SavePost> getAllPostsToSavePost(int pageNumber, int pageSize);
}
