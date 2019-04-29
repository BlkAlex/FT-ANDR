package blk.base;
import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

// TODO (15) создадим обертку для тестов
public class ConcreteApplicationTest<T extends Activity> {

    public final ActivityTestRule<T> rule;

    // TODO (16) создадим и не будем запускать правило
    public ConcreteApplicationTest(Class<T> clazz) {
        rule = new ActivityTestRule<>(clazz, true, false);
    }

    public T launch() {
        return rule.launchActivity(null);
    }

    public T launch(Intent intent) {
        return rule.launchActivity(intent);
    }

}