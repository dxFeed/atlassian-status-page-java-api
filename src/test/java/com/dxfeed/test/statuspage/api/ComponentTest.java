package com.dxfeed.test.statuspage.api;

import com.dxfeed.test.statuspage.config.StatusPageTestResource;
import com.dxfeed.test.statuspage.config.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.dxfeed.statuspage.model.Component;
import com.dxfeed.statuspage.model.Page;
import com.dxfeed.statuspage.type.ComponentStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Slf4j
@FixMethodOrder(MethodSorters.JVM)
public class ComponentTest {

    @ClassRule
    public static StatusPageTestResource resource = StatusPageTestResource.get();

    @Test public void testCreateComponent() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        resource.statusPage().createComponent(
            page.id(),
            TestConstants.TEST_COMPONENT_A_TITLE,
            TestConstants.TEST_COMPONENT_A_DESCRIPTION
        );
        page = resource.statusPage().sync(page);
        assertTrue(page.components().stream().filter(i -> i.name().equals(TestConstants.TEST_COMPONENT_A_TITLE)).findAny().isPresent());
    }

    @Test public void testListComponents() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        List<Component> components = resource.statusPage().components(page);
        assertTrue(components.size() > 0);
        assertTrue(components.stream().filter(i -> i.name().equals(TestConstants.TEST_COMPONENT_A_TITLE)).findAny().isPresent());
    }

    @Test public void testGetComponent() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        assertTrue(page.components().size() > 0);

        Optional<Component> component = page.components().stream().filter(c -> c.name().equals(TestConstants.TEST_COMPONENT_A_TITLE)).findAny();
        assertTrue(component.isPresent());

        Optional<Component> loaded = resource.statusPage().getComponent(page.id(), component.get().id(), true);
        assertTrue(loaded.isPresent());
        assertEquals(TestConstants.TEST_COMPONENT_A_TITLE, loaded.get().name());
        assertEquals(ComponentStatus.OPERATIONAL, loaded.get().status());
    }

    @Test public void testUpdateComponent() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();

        Optional<Component> component = page.components().stream().filter(c -> c.name().equals(TestConstants.TEST_COMPONENT_A_TITLE)).findAny();
        assertTrue(component.isPresent());

        Optional<Component> loaded = resource.statusPage().getComponent(page.id(), component.get().id(), true);
        assertTrue(loaded.isPresent());

        loaded.get().status(ComponentStatus.DEGRADED);
        loaded.get().description("updated description");
        Optional<Component> updated = resource.statusPage().updateComponent(loaded.get());

        assertTrue(updated.isPresent());
        assertEquals(ComponentStatus.DEGRADED, updated.get().status());
        assertEquals("updated description", updated.get().description());
    }

    @Test public void testDeleteComponent() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();

        Optional<Component> component = page.components().stream().filter(c -> c.name().equals(TestConstants.TEST_COMPONENT_A_TITLE)).findAny();
        assertTrue(component.isPresent());

        Optional<Component> removed = resource.statusPage().deleteComponent(page.id(), component.get().id());
        assertTrue(removed.isPresent());

        page = resource.statusPage().sync(page);
        assertFalse(page.components().stream().filter(c -> c.name().equals(TestConstants.TEST_COMPONENT_A_TITLE)).findAny().isPresent());
    }

}
