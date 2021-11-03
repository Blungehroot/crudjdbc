package labelservicetests;

import com.crudjdbc.app.model.Label;
import com.crudjdbc.app.repository.jdbc.JdbcLabelRepositoryImpl;
import com.crudjdbc.app.service.LabelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {

    @InjectMocks
    private LabelServiceImpl labelServiceImpl;

    @Mock
    private JdbcLabelRepositoryImpl jdbcLabelRepository;

    @Test
    void saveLabel_shouldBeSuccess() {
        Label label = new Label();
        label.setId(1);
        label.setName("Test label");

        lenient().when(jdbcLabelRepository.save(label)).thenReturn(label);

        labelServiceImpl.save(label);
        verify(jdbcLabelRepository).save(label);

    }
}
