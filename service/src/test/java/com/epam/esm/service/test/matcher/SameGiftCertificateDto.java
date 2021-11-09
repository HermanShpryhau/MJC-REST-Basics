package com.epam.esm.service.test.matcher;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class SameGiftCertificateDto extends TypeSafeMatcher<GiftCertificateDto> {
    private final GiftCertificateDto expected;

    public SameGiftCertificateDto(GiftCertificateDto expected) {
        this.expected = expected;
    }

    public static Matcher<GiftCertificateDto> sameGiftCertificateDto(GiftCertificateDto expected) {
        return new SameGiftCertificateDto(expected);
    }

    @Override
    protected boolean matchesSafely(GiftCertificateDto item) {
        if (!expected.getId().equals(item.getId())) return false;
        if (!expected.getName().equals(item.getName())) return false;
        if (!expected.getDescription().equals(item.getDescription())) return false;
        if (expected.getPrice() != item.getPrice()) return false;
        if (expected.getDuration() != item.getDuration()) return false;
        if (!expected.getCreateDate().equals(item.getCreateDate())) return false;
        if (!expected.getLastUpdateDate().equals(item.getLastUpdateDate())) return false;
        List<TagDto> tags = item.getTags();
        List<TagDto> expectedTags = expected.getTags();
        if (tags.size() != expectedTags.size()) return false;
        for (TagDto tag : tags) {
            if (not(hasItem((tag))).matches(expectedTags)) return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same with " + expected.toString());
    }
}
