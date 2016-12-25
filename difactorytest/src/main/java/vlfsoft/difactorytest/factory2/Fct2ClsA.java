package vlfsoft.difactorytest.factory2;

import vlfsoft.common.annotations.di.DiFactory;

@DiFactory(
        // factoryPackageName = "vlfsoft.difactorytest.di.factory2",
        factoryClassName = "Factory2",
        // To test "Annotated class %s has value of the generateDefaultFactoryImplementation, which is different from other annotated classes related to the same factory module",
        // change to false
        generateDefaultFactoryImplementation = false,
        abstractionClassSuffix = "A")
public abstract class Fct2ClsA {
}
