package vlfsoft.difactorytest.factory3;

import vlfsoft.common.annotations.di.DiFactory;

public class Fct3ClsOnlyImplWithInject {

    private Fct3ClsOnlyImpl mfct3C3sOnlyImpl;

    @DiFactory.Inject
    public Fct3ClsOnlyImplWithInject(Fct3ClsOnlyImpl afct3ClsOnlyImpl) {
        this.mfct3C3sOnlyImpl = afct3ClsOnlyImpl;
    }

}
