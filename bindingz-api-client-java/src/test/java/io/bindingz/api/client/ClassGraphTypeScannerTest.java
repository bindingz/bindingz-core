package io.bindingz.api.client;

import io.bindingz.api.client.testclasses.hierarchy.AbstractChild;
import io.bindingz.api.client.testclasses.hierarchy.AbstractParent;
import io.bindingz.api.client.testclasses.hierarchy.ChildClass;
import io.bindingz.api.client.testclasses.hierarchy.FamilyTree;
import io.bindingz.api.client.testclasses.hierarchy.ParentClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ClassGraphTypeScannerTest {

    private ClassGraphTypeScanner scanner;

    @Before
    public void setUp() {
        scanner = new ClassGraphTypeScanner(this.getClass().getClassLoader());
    }

    @Test
    public void testSubTypesOfInterface() {
        List<Class<? extends FamilyTree>> family = scanner.getSubTypesOf(FamilyTree.class);
        Assert.assertEquals(8, family.size());
    }

    @Test
    public void testSubTypesOfAbstract() {
        List<Class<? extends AbstractParent>> family = scanner.getSubTypesOf(AbstractParent.class);
        Assert.assertEquals(Arrays.asList(AbstractChild.class), family);
    }

    @Test
    public void testSubTypesOfClass() {
        List<Class<? extends ParentClass>> family = scanner.getSubTypesOf(ParentClass.class);
        Assert.assertEquals(Arrays.asList(ChildClass.class), family);
    }
}
