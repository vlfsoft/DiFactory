package vlfsoft.difactorytest.factory3;

public class Fct3ClsWithConstructor1 extends Fct3ClsWithConstructor1A {
    @Override
    public String getName() {
        return "Impl";
    }

    public Fct3ClsWithConstructor1(Fct3ClsOnlyImpl afct3ClsOnlyImpl) {
        super(afct3ClsOnlyImpl);
    }

}
