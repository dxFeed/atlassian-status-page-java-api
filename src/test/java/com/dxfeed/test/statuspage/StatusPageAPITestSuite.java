package com.dxfeed.test.statuspage;

import com.dxfeed.test.statuspage.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({PageTest.class, ComponentTest.class, ComponentUpdateTest.class, GroupTest.class, IncidentTest.class, IncidentUpdateTest.class})
@RunWith(Suite.class)
public class StatusPageAPITestSuite {
}
