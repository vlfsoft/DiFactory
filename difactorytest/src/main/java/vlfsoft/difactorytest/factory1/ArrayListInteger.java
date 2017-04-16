package vlfsoft.difactorytest.factory1;

import java.util.ArrayList;

public class ArrayListInteger {

    private ArrayList<Integer> mInstance = new ArrayList<>();

    ArrayList<Integer> getInstance() {
        return mInstance;
    }
}
