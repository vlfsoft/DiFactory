package vlfsoft.difactorytest.factory3.di;

import vlfsoft.common.annotations.di.DiFactory;
import vlfsoft.difactorytest.factory3.Fct3ClsOnlyImpl;
import vlfsoft.difactorytest.factory3.Fct3ClsOnlyImplWithInject;

interface App {

    @DiFactory
    void get(Fct3ClsOnlyImpl a);

    @DiFactory
    void get(Fct3ClsOnlyImplWithInject a);

}
