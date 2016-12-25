package vlfsoft.difactorytest.factory1;

import vlfsoft.common.annotations.di.DiFactory;

@DiFactory(
        factoryPackageName = "vlfsoft.difactorytest.di.factory1",
        factoryClassName = "Factory1",
        singleton = true,
        abstractionClassSuffix = "")
public class Fct1SngClsOnlyImpl {
}
