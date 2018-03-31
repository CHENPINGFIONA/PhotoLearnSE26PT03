package sg.edu.nus.se26pt03.photolearn;

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

import java.text.SimpleDateFormat;
import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.service.BaseService;
import sg.edu.nus.se26pt03.photolearn.utility.BindingHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;



/**
 * Created by part time team 3  on 29/3/18.
 */


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class,BindingHelper.class})


public class LearningSessionUnitTest {
    private LearningSession session;
    private DatabaseReference mockedDatabaseReference;
    private BindingHelper mockBindingHelper;
    private BaseService mocKbaseService;


    @Before
    public void setUp() throws Exception{
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);


        session = new LearningSession();
        session.setCourseCode("ABC");
        session.setCourseDate(new Date());
        session.setCourseName("ABC Test Course");
        session.setCreatedBy("pradeep");
        session.setId("ABCTest123");
        session.setModuleName("Data Science");
        session.setModuleNumber(1);
        session.setTimestamp(new Date());

        PowerMockito.mockStatic(BindingHelper.class);
        PowerMockito.when(BindingHelper.fromModuleNumber(session.getModuleNumber())).thenReturn("01");

    }

    @After
    public void tearDown() {
        session = null;
    }

    @Test
    public void Assert_getLearningSessionId() {
        String expectedSession = new SimpleDateFormat("yyyyMMdd").format(new Date())+"-ABC-M01";
        assertEquals(expectedSession,session.getLearningSessionId());
    }

    @Test
    public void Assert_addLearningTitle() {
        assertTrue(session.addLearningTitle(new LearningTitle()));
    }

    @Test
    public void Assert_addQuizTitle() {
        assertTrue(session.addQuizTitle(new QuizTitle()));
    }

}
