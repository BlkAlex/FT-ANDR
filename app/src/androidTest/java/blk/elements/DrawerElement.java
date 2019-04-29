package blk.elements;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.test.espresso.Root;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.intent.Checks;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastaccess.R;
import com.fastaccess.ui.modules.main.donation.CheckPurchaseActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import blk.pages.MainPage;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.apollographql.apollo.api.internal.Utils.checkNotNull;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

//import android.support.test.espresso.contrib.DrawerActions;
//import ru.tinkoff.fintech.mobile.pages.MainPage;

final public class DrawerElement {


    public DrawerElement open() {
        onView(allOf(withParent(withId(R.id.toolbar)), isAssignableFrom(ImageButton.class)))
                .perform(click());
        return this;
    }

    public MainPage close() {
        onView(withId(R.id.drawer)).perform(DrawerActions.close());
        return new MainPage();
    }

    public DrawerElement openItem(int text, int position) {
        onView(allOf(
                is(withId(R.id.design_navigation_view)),
                isDescendantOfA(withId(R.id.drawerViewPager))))
                .perform(scrollToPosition(position));
        onView(withText(text)).perform(click());
        return this;
    }

    public DrawerElement clickToField(int name) {
        onView(withText(name)).perform(click());
        return this;
    }

    public DrawerElement setTextOnField(String text) {
        onView(instanceOf(EditText.class)).perform(typeText(text));
        return this;
    }

    public DrawerElement clickToDescriptionTW() {
        onView(allOf(instanceOf(TextView.class), withId(R.id.description))).perform(click());
        return this;
    }

    public DrawerElement checkIntent() {
        intended(hasComponent(CheckPurchaseActivity.class.getName()));
        return this;
    }

    public DrawerElement swipeToRight() {
        onView(withId(R.id.pager)).perform(swipeLeft());
        return this;
    }

    public DrawerElement pressSubmit() {
        onView(withId(R.id.submit)).perform(click());
        return this;
    }

    public static Matcher<View> checkBuild() {
        return new TypeSafeMatcher<View>() {

            @Override
            protected boolean matchesSafely(View item) {
                if (!(item instanceof TextView)) return false;
                String textFromView = ((TextView) item).getText().toString();
                return true;//(textFromView.contains(Build.MANUFACTURER) && (textFromView.contains(Build.BRAND) && (textFromView.contains(Build.MODEL))));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Value expected is wrong");
            }
        };
    }

    public class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }

    public DrawerElement scrollToPositionAbout(int position) {
        onView(withId(R.id.mal_recyclerview))
                .perform(scrollToPosition(position));
        return this;
    }

    public DrawerElement checkToastWithText(String text) {
        onView(withText(text)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        return this;
    }

    public DrawerElement compareBuildWithSystem() {
        onView(withId(R.id.editText)).check(matches(checkBuild()));
        return this;
    }

    public DrawerElement pressFab() {
        onView(allOf(withId(R.id.apply), isCompletelyDisplayed())).perform(click());
        return this;
    }


    public DrawerElement checkActivityStarts(String name) {
        intended(hasComponent(name));
        return this;

    }

    public DrawerElement assertUsername(Matcher<String> matcher) {
        onView(withId(R.id.navUsername)).check(matches(withText(matcher)));
        return this;
    }


    public DrawerElement assertElementAbout(
            final int position, int stringRes, int iconRes
    ) {
        // onView(new DrawableMatcher(iconRes)).perform(click());

        onView(allOf(
                instanceOf(RecyclerView.class),
                withParent(allOf(
                        instanceOf(LinearLayout.class),
                        withChild(withText(R.string.about))))
        )).check(matches(atPosition(position,
                withChild(allOf(instanceOf(LinearLayout.class), withChild(withText(stringRes))))
        )));

        onView(allOf(
                instanceOf(RecyclerView.class),
                withParent(allOf(
                        instanceOf(LinearLayout.class),
                        withChild(withText(R.string.about))))
        )).check(matches(atPosition(position, new DrawableMatcher(iconRes)/*withId(R.id.mal_item_image)*/))
        );


        return this;
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static Matcher<View> withColor(final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, ViewPager>(ViewPager.class) {
            @Override
            public boolean matchesSafely(ViewPager warning) {
                int localColor = -1;
                Drawable background = warning.getBackground();
                if (background instanceof ColorDrawable)
                    localColor = ((ColorDrawable) background).getColor();
                Log.d("COLOR", "matchesSafely: color" + localColor);
                return color == localColor;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: " + color);
            }
        };
    }


    public class DrawableMatcher extends TypeSafeMatcher<View> {

        private final int expectedId;
        private String resourceName;
        static final int EMPTY = -1;
        static final int ANY = -2;

        DrawableMatcher(int expectedId) {
            super(View.class);
            this.expectedId = expectedId;
        }

        @Override
        protected boolean matchesSafely(View target) {
            if (!(target instanceof ImageView)) {
                return false;
            }
            ImageView imageView = (ImageView) target;
            if (expectedId == EMPTY) {
                return imageView.getDrawable() == null;
            }
            if (expectedId == ANY) {
                return imageView.getDrawable() != null;
            }
            Resources resources = target.getContext().getResources();
            Drawable expectedDrawable = resources.getDrawable(expectedId);
            resourceName = resources.getResourceEntryName(expectedId);

            if (expectedDrawable == null) {
                return false;
            }
            Log.d("DRAWABLE", "IMAGE VIEW: " + imageView.getDrawable());

            Log.d("DRAWABLE", "expected VIEW: " + expectedDrawable);

            return expectedDrawable.getConstantState().equals
                    (imageView.getDrawable().getConstantState());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with drawable from resource id: ");
            description.appendValue(expectedId);
            if (resourceName != null) {
                description.appendText("[");
                description.appendText(resourceName);
                description.appendText("]");
            }
        }
    }

    public DrawerElement compareWithBackgroundColor(int color) {
        onView(withId(R.id.recycler)).check(matches(withColor(color)));
        return this;
    }
}
