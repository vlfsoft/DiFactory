package vlfsoft.difactorytest.factory1;

import vlfsoft.common.annotations.di.DiFactory;

@DiFactory(
        factoryPackageName = "vlfsoft.difactorytest.di.factory1",
        factoryClassName = "Factory1",
        abstractionClassSuffix = "A")
public abstract class Fct1ClsA {

    abstract public String getName();

}
