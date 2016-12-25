package vlfsoft.difactorytest.factory1;

import vlfsoft.common.annotations.di.DiFactory;

interface App {

    @DiFactory(
            factoryPackageName = "vlfsoft.difactorytest.di.factory1",
            factoryClassName = "Factory1")
    void get(Fct1ClsOnlyImpl a);

}

//@DiFactory(
//        factoryPackageName = "vlfsoft.difactorytest.di.factory1",
//        factoryClassName = "Factory1")
public class Fct1ClsOnlyImpl {
}
