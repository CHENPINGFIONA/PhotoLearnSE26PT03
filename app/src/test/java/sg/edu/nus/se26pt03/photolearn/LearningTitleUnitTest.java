package sg.edu.nus.se26pt03.photolearn;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.Date;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.service.BaseService;
import sg.edu.nus.se26pt03.photolearn.utility.BindingHelper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by part time team 3 on 3/13/2018.
 */

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class, BindingHelper.class})


public class LearningTitleUnitTest {
    private LearningTitle title;
    private DatabaseReference mockedDatabaseReference;
    private BindingHelper mockBindingHelper;
    private BaseService mocKbaseService;

    @Before
    public void setUp() throws Exception {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);


        title = new LearningTitle();
        title.setTitle("Test title");
        title.setCreatedBy("chen ping");
        title.setId("TestTitle123");
        title.setTimestamp(new Date());

        PowerMockito.mockStatic(BindingHelper.class);
    }

    @After
    public void tearDown() {
        title = null;
    }

    @Test
    public void Assert_Copy() {
        LearningTitle testTitle = new LearningTitle();
        testTitle.setTitle("Test title");
        testTitle.setCreatedBy("chen ping");
        testTitle.setId("TestTitle123");

        title.copy(testTitle);

        assertEquals(testTitle.getId(), title.getId());
        assertEquals(testTitle.getCreatedBy(), title.getCreatedBy());
        assertEquals(testTitle.getTitle(), title.getTitle());
    }
}
