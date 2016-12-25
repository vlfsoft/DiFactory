package vlfsoft.difactorytest.factory1;

// To test "Error:classNameA = Fct1Cls must end with 'A'",
// uncomment
//@DiFactory(
//        factoryPackageName = "vlfsoft.difactorytest.di.factory1",
//        factoryClassName = "Factory1",
//        singleton = false)

public class Fct1Cls extends Fct1ClsA {
    @Override
    public String getName() {
        return "Impl";
    }
}
