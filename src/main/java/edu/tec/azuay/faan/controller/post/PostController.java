package edu.tec.azuay.faan.controller.post;

import edu.tec.azuay.faan.persistence.dto.primary.LikedPost;
import edu.tec.azuay.faan.persistence.dto.primary.SavePost;
import edu.tec.azuay.faan.persistence.utils.PostState;
import edu.tec.azuay.faan.persistence.utils.PostType;
import edu.tec.azuay.faan.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/register-post")
    public ResponseEntity<?> createPost(@RequestPart("post") SavePost newPost, @RequestPart("image") MultipartFile image) throws IOException {
        SavePost saved = postService.save(newPost, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/type")
    public ResponseEntity<Page<SavePost>> findByTypePost(@RequestParam("postType") PostType postType,
                                                         @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByType(postType, pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/state")
    public ResponseEntity<Page<SavePost>> findByState(@RequestParam("state") PostState state,
                                                      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByState(state, pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/type-state")
    public ResponseEntity<Page<SavePost>> findByTypeAndState(@RequestParam("postType") PostType postType, @RequestParam("state") PostState state,
                                                             @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByTypeAndState(postType, state, pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/author-id")
    public ResponseEntity<Page<SavePost>> findByAuthorId(@RequestParam String authorId,
                                                         @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(postService.getPostsByUserId(authorId, pageNumber, pageSize));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/author")
    public ResponseEntity<Page<SavePost>> findByAuthorUsername(@RequestParam String authorId,
                                                               @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByUserId(authorId, pageNumber, pageSize);

        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/type-author-id")
    public ResponseEntity<Page<SavePost>> findByTypePostAndAuthorUsername(@RequestParam("postType") PostType postType, @RequestParam("author") String author,
                                                                          @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByTypeAndUser(postType, author, pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/state-author-id")
    public ResponseEntity<Page<SavePost>> findByStateAndAuthorId(@RequestParam("state") PostState state,
                                                                 @RequestParam("author") String author,
                                                                 @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByStateAndUser(state, author, pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/type-state-author-id")
    public ResponseEntity<Page<SavePost>> findByTypePostAndStateAndAuthorId(@RequestParam("postType") PostType postType,
                                                                            @RequestParam("state") PostState state, @RequestParam("author") String author,
                                                                            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getPostsByTypeAndStateAndUser(postType, state, author, pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/get-dto/{id}")
    public ResponseEntity<SavePost> getPostById(@PathVariable String id) {
        SavePost post = postService.getSavePostById(id);
        return ResponseEntity.ok().body(post);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikedPost likedPost) {
        String status = postService.likePost(likedPost);
        return ResponseEntity.ok(status);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all-dto")
    public ResponseEntity<Page<SavePost>> getAllPostsToSavePost(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<SavePost> posts = postService.getAllPostsToSavePost(pageNumber, pageSize);
        return ResponseEntity.ok().body(posts);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/get-liked-posts")
    public ResponseEntity<Page<SavePost>> getLikedPostsByAuthorId(@RequestParam("authorId") String authorId,
                                                                  @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(postService.getLikedPostsByAuthorId(authorId, pageNumber, pageSize));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePost(@RequestBody SavePost post) {
        postService.updatePost(post);
        return ResponseEntity.ok("Post updated successfully");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update-state/{id}")
    public ResponseEntity<String> updateStatePost(@PathVariable String id, @RequestParam PostState state) {
        postService.updateStatePost(id, state);
        return ResponseEntity.ok("Post state updated successfully");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

}
