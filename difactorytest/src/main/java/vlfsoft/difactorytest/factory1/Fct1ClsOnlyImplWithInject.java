package vlfsoft.difactorytest.factory1;

import vlfsoft.common.annotations.di.DiFactory;

public class Fct1ClsOnlyImplWithInject {

    private Fct1ClsOnlyImpl mfct1ClsOnlyImpl;

    @DiFactory.ConstructorInject
    public Fct1ClsOnlyImplWithInject(Fct1ClsOnlyImpl afct1ClsOnlyImpl) {
        this.mfct1ClsOnlyImpl = afct1ClsOnlyImpl;
    }

}
