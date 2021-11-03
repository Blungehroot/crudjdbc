package labelservicetests;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.repository.LabelRepository;
import com.crudjdbc.app.repository.jdbc.JdbcLabelRepositoryImpl;
import com.crudjdbc.app.service.LabelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {

    @InjectMocks
    private LabelServiceImpl labelServiceImpl;

    @Mock
    private JdbcLabelRepositoryImpl labelRepository;

    @Test
    void saveLabel_shouldBeSuccess() {
        Label label = new Label();
        label.setId(1);
        label.setName("Test label");

        when(labelRepository.save(label)).thenReturn(label);

        labelRepository.save(label);
        Label labelActual =labelServiceImpl.save(label);

        assertNotNull(label);
        assertEquals(label, labelActual);

        verify(labelRepository).save(ArgumentMatchers.eq(label));

    }
}
