package vlfsoft.difactorytest.factory4.di;

import vlfsoft.common.annotations.di.DiFactory;
import vlfsoft.difactorytest.factory4.Fct4ClsOnlyImpl;
import vlfsoft.difactorytest.factory4.Fct4ClsOnlyImplWithInject;

interface App4 {

    @DiFactory.Component
    void get(Fct4ClsOnlyImpl a);

    @DiFactory.Component
    void get(Fct4ClsOnlyImplWithInject a);

}
