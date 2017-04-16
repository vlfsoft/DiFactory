package vlfsoft.difactorytest.factory4;

import vlfsoft.common.annotations.di.DiFactory;

public class Fct4ClsOnlyImplWithInject {

    private Fct4ClsOnlyImpl mfct4C3sOnlyImpl;

    @DiFactory.ConstructorInject
    public Fct4ClsOnlyImplWithInject(Fct4ClsOnlyImpl afct4ClsOnlyImpl) {
        this.mfct4C3sOnlyImpl = afct4ClsOnlyImpl;
    }

}
