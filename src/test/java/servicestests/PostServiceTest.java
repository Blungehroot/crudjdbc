package servicestests;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.jdbc.JdbcLabelRepositoryImpl;
import com.crudjdbc.app.repository.jdbc.JdbcPostRepositoryImpl;
import com.crudjdbc.app.service.serviceimpl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private JdbcLabelRepositoryImpl labelRepository;

    @Mock
    private JdbcPostRepositoryImpl postRepository;

    @Test
    void savePost_shouldBeSuccess() {
        Label label = new Label();
        Post post = new Post();
        List<Label> labels = new ArrayList<>();

        label.setId(1);
        label.setName("Test label");

        labels.add(label);

        post.setId(1);
        post.setContent("Test content");
        post.setName("Test post");
        post.setLabels(labels);

        when(postRepository.save(post)).thenReturn(post);

        Post postActual = postRepository.save(post);

        assertNotNull(postActual);
        assertEquals(post, postActual);

        verify(postRepository).save(post);
    }

    @Test
    void deletePost_shouldBeSuccess() {
        Post post = new Post();

        post.setId(1);
        post.setContent("Test content");
        post.setName("Test post");

        doNothing().when(postRepository).deleteById(post.getId());

        postRepository.deleteById(post.getId());

        verify(postRepository).deleteById(post.getId());
    }

    @Test
    void updateLabel_shouldBeSuccess() {
        Post postExist = new Post();
        Post postUpdated = new Post();
        Label label = new Label();
        List<Label> labels = new ArrayList<>();

        label.setId(1);
        label.setName("Test label");
        labels.add(label);

        postExist.setId(1);
        postExist.setName("Test Content");
        postExist.setContent("Test post content");
        postExist.setLabels(labels);

        postUpdated.setId(1);
        postUpdated.setName("Test post update");
        postUpdated.setContent("Test post content update");
        postUpdated.setLabels(labels);

        lenient().when(postRepository.save(postExist)).thenReturn(postExist);
        lenient().when(postRepository.update(postUpdated)).thenReturn(postUpdated);

        Post result = postRepository.update(postUpdated);

        assertNotNull(result);
        assertNotEquals(postExist.getName(), result.getName());

        verify(postRepository).update(postUpdated);
    }

    @Test
    void getPostById_shouldBeSuccess() {
        Label label = new Label();
        Post post = new Post();
        List<Label> labels = new ArrayList<>();

        label.setId(1);
        label.setName("Test label");

        labels.add(label);

        post.setId(1);
        post.setContent("Test content");
        post.setName("Test post");
        post.setLabels(labels);

        when(postRepository.getById(post.getId())).thenReturn(post);

        Post result = postRepository.getById(post.getId());

        assertNotNull(result);
        assertEquals(post, result);

        verify(postRepository).getById(post.getId());
    }

    @Test
    void getAll_shouldBeSuccess() {
        Label label = new Label();
        Post post = new Post();
        List<Label> labels = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        label.setId(1);
        label.setName("Test label");

        labels.add(label);

        post.setId(1);
        post.setContent("Test content");
        post.setName("Test post");
        post.setLabels(labels);

        posts.add(post);

        when(postRepository.getAll()).thenReturn(posts);

        assertEquals(posts, postRepository.getAll());

        verify(postRepository).getAll();
    }
}
