package com.epam.esm.persistence.repository.test.matchers;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static com.epam.esm.persistence.repository.test.matchers.SameTag.sameTag;
import static org.hamcrest.Matchers.*;

public class SameGiftCertificate extends TypeSafeMatcher<GiftCertificate> {
    private final GiftCertificate expected;

    public SameGiftCertificate(GiftCertificate expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(GiftCertificate item) {
        if (!expected.getId().equals(item.getId())) return false;
        if (!expected.getName().equals(item.getName())) return false;
        if (!expected.getDescription().equals(item.getDescription())) return false;
        if (expected.getPrice() != item.getPrice()) return false;
        if (expected.getDuration() != item.getDuration()) return false;
        if (!expected.getCreateDate().equals(item.getCreateDate())) return false;
        if (!expected.getLastUpdateDate().equals(item.getLastUpdateDate())) return false;
        List<Tag> tags = item.getAssociatedTags();
        List<Tag> expectedTags = expected.getAssociatedTags();
        if (tags.size() != expectedTags.size()) return false;
        for (Tag tag : tags) {
            if (not(hasItem(sameTag(tag))).matches(expectedTags)) return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same with " + expected.toString());
    }

    public static Matcher<GiftCertificate> sameGiftCertificate(GiftCertificate expected) {
        return new SameGiftCertificate(expected);
    }
}
