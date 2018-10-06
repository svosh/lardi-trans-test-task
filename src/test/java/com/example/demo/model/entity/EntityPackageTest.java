package com.example.demo.model.entity;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

public class EntityPackageTest {

    private String packageName = User.class.getPackage().getName();

    private PojoClassFilter filterTestClasses = new FilterTestClasses();

    @Test
    public void validate() {
        Validator validator = ValidatorBuilder.create()
                .with(new SetterMustExistRule(),
                        new GetterMustExistRule())
                .with(new SetterTester(),
                        new GetterTester())
                .build();
        validator.validate(packageName, filterTestClasses);
    }

    private static class FilterTestClasses implements PojoClassFilter {
        public boolean include(PojoClass pojoClass) {
            return !pojoClass.getSourcePath().contains("/test-classes/");
        }
    }

}