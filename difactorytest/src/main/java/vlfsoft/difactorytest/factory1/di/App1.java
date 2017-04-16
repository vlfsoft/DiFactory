package vlfsoft.difactorytest.factory1.di;

import java.util.ArrayList;

import vlfsoft.common.annotations.di.DiFactory;
import vlfsoft.difactorytest.factory1.ArrayListInteger;
import vlfsoft.difactorytest.factory1.Fct1ClsA;
import vlfsoft.difactorytest.factory1.Fct1ClsOnlyImpl;
import vlfsoft.difactorytest.factory1.Fct1ClsOnlyImplReturnNull;
import vlfsoft.difactorytest.factory1.Fct1ClsOnlyImplWithInject;
import vlfsoft.difactorytest.factory1.Fct1InterfaceA;
import vlfsoft.difactorytest.factory1.Fct1InterfaceB;
import vlfsoft.difactorytest.factory1.Fct1SngClsA;
import vlfsoft.difactorytest.factory1.Fct1SngClsAzA;
import vlfsoft.difactorytest.factory1.Fct1SngClsOnlyImpl;

interface App1 {

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    void get(Fct1ClsA a);

    @DiFactory.Component
    void get(Fct1ClsOnlyImpl a);

    @DiFactory.Component
    // @DiFactory.ImplementationType("-")
    @DiFactory.ImplementationType("")
    void get(Fct1ClsOnlyImplReturnNull a);

    @DiFactory.Component
    void get(Fct1ClsOnlyImplWithInject a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.ImplementationType("Fct1InterfaceImpl")
    @DiFactory.Singleton
    void get(Fct1InterfaceA a);

    @DiFactory.Component
    @DiFactory.ImplementationType("Fct1InterfaceImpl")
    @DiFactory.Singleton
    void get(Fct1InterfaceB a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton
    void get(Fct1SngClsA a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton({"Ab", "Yz"})
    void get(Fct1SngClsAzA a);

    @DiFactory.Component
    void get(Fct1SngClsOnlyImpl a);

    @DiFactory.Component
    @DiFactory.Singleton
    void get(ArrayList<String> a);

    @DiFactory.Component
    @DiFactory.Singleton
    void get(ArrayListInteger a);

}
