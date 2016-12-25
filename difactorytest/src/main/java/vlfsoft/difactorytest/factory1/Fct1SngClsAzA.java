package vlfsoft.difactorytest.factory1;

import vlfsoft.common.annotations.di.DiFactory;

@DiFactory(
        factoryPackageName = "vlfsoft.difactorytest.di.factory1",
        factoryClassName = "Factory1",
        singleton = true, singletonInstanceNames = {"Ab", "Yz"},

        abstractionClassSuffix = "A")
public abstract class Fct1SngClsAzA {
}
