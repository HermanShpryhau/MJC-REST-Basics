package com.epam.esm.persistence.repository.test.matchers;

import com.epam.esm.model.Tag;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class SameTag extends TypeSafeMatcher<Tag> {
    private Tag expected;

    public SameTag(Tag expected) {
        super();
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Tag item) {
        return item.getId().equals(expected.getId()) && item.getName().equals(expected.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same with " + expected.toString());
    }

    public static Matcher<Tag> sameTag(Tag expected) {
        return new SameTag(expected);
    }
}
