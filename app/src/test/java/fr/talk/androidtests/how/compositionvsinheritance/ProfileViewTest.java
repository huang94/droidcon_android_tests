package fr.talk.androidtests.how.compositionvsinheritance;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import fr.talk.androidtests.CustomRobolectricTestRunner;
import fr.talk.androidtests.R;

@RunWith(CustomRobolectricTestRunner.class)
public class ProfileViewTest {

    ProfileView view;

    TextView textView = Mockito.mock(TextView.class);

    //region composition view manager
    ViewManager viewManager = Mockito.mock(ViewManager.class);
    //endregion

    //region composition messenger
    Messenger messenger = Mockito.mock(Messenger.class);
    //endregion

    @Before
    public void setUp() throws Exception {
        view = new ProfileView(RuntimeEnvironment.application);
        view.nameTextView = textView;

        //region composition view manager
        view.viewManager = viewManager;
        //endregion

        //region messenger
        view.messenger = messenger;
        //endregion
    }

    @SuppressLint("SetTextI18n")
    @Test
    public void testUpdateName_small() throws Exception {
        // Given

        // When
        view.updateName(new Profile("Jo"));

        // Then
        Mockito.verify(textView).setText("A short name: Jo");

        //region inheritance way
        Mockito.verify(textView).setVisibility(View.GONE);
        //endregion

        //region composition way
        Mockito.verify(viewManager).updateView(null, textView);
        //endregion
    }

    @Test
    public void should_show_long_name_message() throws Exception {
        // Given

        // When
        view.showNameInfo(new Profile("John"));

        // Then
        Mockito.verify(messenger).showMessage("A long name");
    }

    @Test
    public void should_show_short_name_message_from_id() throws Exception {
        // Given

        // When
        view.showNameInfo(new Profile("Jo"));

        // Then
        Mockito.verify(messenger).showMessage(R.string.toast_short_name);
    }
}