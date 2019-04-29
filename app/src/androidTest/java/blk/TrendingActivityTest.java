package blk;

import android.app.Activity;
import android.support.test.espresso.intent.Intents;

import com.fastaccess.R;
import com.fastaccess.ui.modules.main.MainActivity;
import com.fastaccess.ui.modules.trending.TrendingActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import blk.base.ConcreteApplicationTest;
import blk.pages.MainPage;

public class TrendingActivityTest extends ConcreteApplicationTest<MainActivity> {

    private Activity activity;

    public TrendingActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() {
        Intents.init();
        activity = launch();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void checkTrending() {
        new MainPage()
                .getDrawer()
                .open()
                .openItem(R.string.trending, 8)
                .checkActivityStarts(TrendingActivity.class.getName());
    }

    @Test
    public void checkRestore() {
        new MainPage()
                .getDrawer()
                .open()
                .openItem(R.string.restore_purchases, 15)
                .checkIntent();
    }

    @Test
    public void checkDarkTheme() {
        new MainPage()
                .getDrawer()
                .open()
                .openItem(R.string.settings, 14)
                .clickToField(R.string.theme_title)
                .swipeToRight()
                .pressFab()
                .compareWithBackgroundColor(R.color.darkDivider);
    }

    @Test
    public void checkToast() {
        new MainPage()
                .getDrawer()
                .open()
                .openItem(R.string.send_feedback, 11)
                .clickToField(R.string.ok)
                .setTextOnField("hello")
                .clickToDescriptionTW()
                .compareBuildWithSystem()
                .pressSubmit()
                .pressSubmit()
                .checkToastWithText("Message was sent");
    }

    @Test
    public void testAbout() {
        new MainPage()
                .getDrawer()
                .open()
                .openItem(R.string.about, 15)
                .scrollToPositionAbout(2)
                .assertElementAbout(1, R.string.changelog, R.drawable.ic_track_changes);
    }
}