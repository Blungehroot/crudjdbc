package labelservicetests;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.model.Post;
import com.crudjdbc.app.repository.jdbc.JdbcLabelRepositoryImpl;
import com.crudjdbc.app.repository.jdbc.JdbcPostRepositoryImpl;
import com.crudjdbc.app.service.PostServiceImpl;
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
    void deleteLabel_shouldBeSuccess() {
        Label label = new Label();
        label.setId(1);
        label.setName("Test label");

        doNothing().when(labelRepository).deleteById(label.getId());

        labelRepository.deleteById(label.getId());

        verify(labelRepository).deleteById(label.getId());
    }

    @Test
    void updateLabel_shouldBeSuccess() {
        Label labelExist = new Label();
        Label labelUpdated = new Label();

        labelExist.setId(1);
        labelExist.setName("Test label");

        labelUpdated.setName("Updated");
        labelUpdated.setId(1);

        lenient().when(labelRepository.save(labelExist)).thenReturn(labelExist);
        lenient().when(labelRepository.update(labelUpdated)).thenReturn(labelUpdated);

        Label result = labelRepository.update(labelUpdated);

        assertNotNull(result);
        assertNotEquals(labelExist.getName(), result.getName());

        verify(labelRepository).update(labelUpdated);
    }

    @Test
    void getLabelById_shouldBeSuccess() {
        Label label = new Label();
        label.setId(1);
        label.setName("Test label");

        when(labelRepository.getById(label.getId())).thenReturn(label);

        Label result = labelRepository.getById(label.getId());

        assertNotNull(result);
        assertEquals(label, result);

        verify(labelRepository).getById(label.getId());
    }

    @Test
    void getAll_shouldBeSuccess() {
        Label label = new Label();
        label.setId(1);
        label.setName("Test label");

        List<Label> labels = new ArrayList<>();
        labels.add(label);

        when(labelRepository.getAll()).thenReturn(labels);

        assertEquals(labels, labelRepository.getAll());

        verify(labelRepository).getAll();
    }
}
