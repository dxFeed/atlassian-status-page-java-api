package com.dxfeed.test.statuspage.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.dxfeed.statuspage.model.Page;
import com.dxfeed.test.statuspage.config.StatusPageTestResource;
import com.dxfeed.test.statuspage.config.TestConstants;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@FixMethodOrder(MethodSorters.JVM)
public class PageTest {

    @ClassRule
    public static StatusPageTestResource resource = StatusPageTestResource.get();

    @Test public void testPagesList() {
        List<Page> pages = resource.statusPage().pages();
        assertTrue(pages.size() > 0);
        assertEquals(1, pages.size());
        assertEquals(TestConstants.TEST_PAGE_NAME, pages.get(0).name());
    }

    @Test public void testGetPage() {
        Optional<Page> page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true);
        assertTrue(page.isPresent());
        assertEquals(TestConstants.TEST_PAGE_NAME, page.get().name());
    }

}
