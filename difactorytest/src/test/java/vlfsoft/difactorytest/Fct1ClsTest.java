package vlfsoft.difactorytest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import vlfsoft.difactorytest.di.factory1.Factory1;
import vlfsoft.difactorytest.di.factory1.Factory1A;
import vlfsoft.difactorytest.factory1.Fct1Cls;
import vlfsoft.difactorytest.factory1.Fct1ClsA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Fct1ClsTest {

    @Before
    public void beforeTest() {
    }

    private Fct1ClsA buildTestEnvironmentNotMocked() {
        Factory1A.inject(new Factory1());
        Fct1ClsA impl = Factory1A.getInstance().ofFct1Cls();
        return impl;
    }

    @Test
    public void implTestNotMocked() throws Exception {
        Fct1ClsA impl = buildTestEnvironmentNotMocked();
        assertTrue(impl instanceof Fct1Cls);
        assertEquals("Impl", impl.getName());
    }

    private Fct1ClsA buildTestEnvironmentMocked1() {
        Factory1A.inject(new Factory1Mock());
        Fct1ClsA impl = Factory1A.getInstance().ofFct1Cls();

        Mockito.when(impl.getName()).thenReturn("Mock1");
        return impl;
    }

    @Test
    public void implTestMocked1() throws Exception {
        Fct1ClsA impl = buildTestEnvironmentMocked1();
        assertTrue(impl instanceof Fct1Cls);
        assertEquals("Mock1", impl.getName());
    }

    private class Factory1Mock extends Factory1 {
        @Override
        public Fct1ClsA ofFct1Cls() {
            return Mockito.mock(Fct1Cls.class);
        }
    }

    private Fct1ClsA buildTestEnvironmentMocked2() {
        Factory1 Factory1Mocked = Mockito.mock(Factory1.class);
        Mockito.when(Factory1Mocked.ofFct1Cls()).thenReturn(Mockito.mock(Fct1Cls.class));

        Factory1A.inject(Factory1Mocked);
        Fct1ClsA impl = Factory1A.getInstance().ofFct1Cls();

        Mockito.when(impl.getName()).thenReturn("Mock2");
        return impl;
    }

    @Test
    public void implTestMocked2() throws Exception {
        Fct1ClsA impl = buildTestEnvironmentMocked2();
        assertTrue(impl instanceof Fct1Cls);
        assertEquals("Mock2", impl.getName());
    }

}
