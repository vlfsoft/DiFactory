package vlfsoft.difactorytest.factory3.di;

import vlfsoft.common.annotations.di.DiFactory;
import vlfsoft.difactorytest.factory3.Fct3ClsA;
import vlfsoft.difactorytest.factory3.Fct3ClsOnlyImpl;
import vlfsoft.difactorytest.factory3.Fct3ClsOnlyImplWithInject;
import vlfsoft.difactorytest.factory3.Fct3ClsWithConstructor1A;
import vlfsoft.difactorytest.factory3.Fct3ClsWithConstructor2A;
import vlfsoft.difactorytest.factory3.Fct3ClsWithConstructor3SngA;
import vlfsoft.difactorytest.factory3.Fct3ClsWithConstructor4SngA;
import vlfsoft.difactorytest.factory3.Fct3ClsWithConstructor5SngA;
import vlfsoft.difactorytest.factory3.Fct3SngClsAzA;
import vlfsoft.difactorytest.factory4.Fct4ClsOnlyImpl;

interface App3 {

    @DiFactory.Component
    void get(Fct3ClsOnlyImpl a);

    @DiFactory.Component
    void get(Fct3ClsOnlyImplWithInject a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    void get(Fct3ClsA a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    void get(Fct3ClsWithConstructor1A a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    void get(Fct3ClsWithConstructor2A a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton
    void get(Fct3ClsWithConstructor3SngA a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton
    void get(Fct3ClsWithConstructor4SngA a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton({"Ab", "Yz"})
    void get(Fct3SngClsAzA a);

    @DiFactory.Component
    @DiFactory.AbstractionTypeSuffix
    @DiFactory.Singleton
    void get(Fct3ClsWithConstructor5SngA a);

    @DiFactory.Component
    @DiFactory.ImplementationType("")
    void get(Fct4ClsOnlyImpl a);

}
