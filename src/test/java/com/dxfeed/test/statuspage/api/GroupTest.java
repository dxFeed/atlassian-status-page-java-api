package com.dxfeed.test.statuspage.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runners.MethodSorters;
import com.dxfeed.statuspage.model.Component;
import com.dxfeed.statuspage.model.Group;
import com.dxfeed.statuspage.model.Page;
import com.dxfeed.test.statuspage.config.StatusPageTestResource;
import com.dxfeed.test.statuspage.config.TestConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@Slf4j
@FixMethodOrder(MethodSorters.JVM)
public class GroupTest {

    @ClassRule
    public static StatusPageTestResource resource = StatusPageTestResource.get();

    private static Optional<Component> componentA;
    private static Optional<Component> componentB;

    @BeforeClass
    public static void prepare() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        componentA = resource.statusPage().createComponent(
                page.id(),
                TestConstants.TEST_COMPONENT_A_TITLE,
                TestConstants.TEST_COMPONENT_A_DESCRIPTION
        );
        assertNotNull(componentA);
        assertTrue(componentA.isPresent());
        componentB = resource.statusPage().createComponent(
                page.id(),
                TestConstants.TEST_COMPONENT_B_TITLE,
                TestConstants.TEST_COMPONENT_B_DESCRIPTION
        );
        assertNotNull(componentA);
        assertTrue(componentB.isPresent());
    }

    @AfterClass
    public static void cleanup() {
        resource.statusPage().deleteComponent(componentA.get());
        resource.statusPage().deleteComponent(componentB.get());
    }

    @Test
    public void testCreateGroup() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        resource.statusPage().createGroup(
                page.id(),
                TestConstants.TEST_GROUP_TITLE,
                TestConstants.TEST_GROUP_DESCRIPTION,
                Arrays.asList(componentA.get().id(), componentB.get().id())
        );
        page = resource.statusPage().sync(page);
        assertTrue(page.groups().stream().filter(i -> i.name().equals(TestConstants.TEST_GROUP_TITLE)).findAny().isPresent());
    }

    @Test
    public void testListGroups() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();

        List<Group> groups = resource.statusPage().groups(page);
        assertTrue(groups.size() > 0);

        Optional<Group> found = groups.stream().filter(g -> g.name().equals(TestConstants.TEST_GROUP_TITLE)).findAny();
        assertTrue(found.isPresent());
    }

    @Test
    public void testGetGroup() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();

        List<Group> groups = resource.statusPage().groups(page);
        assertTrue(groups.size() > 0);
        Optional<Group> found = groups.stream().filter(g -> g.name().equals(TestConstants.TEST_GROUP_TITLE)).findAny();
        assertTrue(found.isPresent());

        Optional<Group> group = resource.statusPage().getGroup(page.id(), found.get().id(), true);
        assertTrue(group.isPresent());
    }

    @Test
    public void testUpdateGroup() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        List<Group> groups = resource.statusPage().groups(page);
        assertTrue(groups.size() > 0);

        Optional<Group> found = groups.stream().filter(g -> g.name().equals(TestConstants.TEST_GROUP_TITLE)).findAny();
        found = resource.statusPage().getGroup(page.id(), found.get().id(), true);
        assertTrue(found.isPresent());

        found.get().componentIds().remove(0);
        found.get().components().remove(0);
        Optional<Group> updated = resource.statusPage().updateGroup(found.get());
        assertTrue(updated.isPresent());

        Group group = updated.get();
        resource.statusPage().sync(group);

        assertEquals(1, group.componentIds().size());
        assertTrue(Arrays.asList(componentA.get().id(), componentB.get().id()).contains(group.componentIds().get(0)));
    }

    @Test
    public void testDeleteGroup() {
        Page page = resource.statusPage().getPage(resource.statusPage().pages().get(0).id(), true).get();
        List<Group> groups = resource.statusPage().groups(page);
        assertTrue(groups.size() > 0);

        Optional<Group> found = groups.stream().filter(g -> g.name().equals(TestConstants.TEST_GROUP_TITLE)).findAny();
        found = resource.statusPage().getGroup(page.id(), found.get().id(), true);
        assertTrue(found.isPresent());

        Optional<Group> removed = resource.statusPage().deleteGroup(page.id(), found.get().id());
        assertTrue(removed.isPresent());

        assertFalse(resource.statusPage().groups(page).stream().filter(g -> g.name().equals(TestConstants.TEST_GROUP_TITLE)).findAny().isPresent());
    }

}