package vlfsoft.difactorytest.factory1;

import vlfsoft.common.annotations.di.DiFactory;

@DiFactory(
        factoryPackageName = "vlfsoft.difactorytest.di.factory1",
        factoryClassName = "Factory1")
public class Fct1ClsOnlyImplWithInject {

    private Fct1ClsOnlyImpl mfct1ClsOnlyImpl;

    @DiFactory.Inject
    public Fct1ClsOnlyImplWithInject(Fct1ClsOnlyImpl afct1ClsOnlyImpl) {
        this.mfct1ClsOnlyImpl = afct1ClsOnlyImpl;
    }

}
