package vlfsoft.difactorytest.factory2;

import vlfsoft.common.annotations.di.DiFactory;

@DiFactory(
        // factoryPackageName = "vlfsoft.difactorytest.di.factory2",
        factoryClassName = "Factory2",
        singleton = true,
        generateDefaultFactoryImplementation = false,
        abstractionClassSuffix = "A")
public abstract class Fct2SngClsA {
}
