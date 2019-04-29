package blk.pages;

import android.app.Activity;

import com.fastaccess.ui.modules.main.MainActivity;

import static org.junit.Assert.assertTrue;

public class MainPage extends BasePage {

    public MainPage validate(Activity activity) {
        assertTrue(activity instanceof MainActivity);

        return this;
    }


}