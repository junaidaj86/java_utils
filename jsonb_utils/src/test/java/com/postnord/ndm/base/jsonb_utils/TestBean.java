package com.postnord.ndm.base.jsonb_utils;

import java.util.Collections;
import java.util.List;

public class TestBean {
    private String stringField;
    private Integer integerField;
    private List<String> listField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(final String stringField) {
        this.stringField = stringField;
    }

    public Integer getIntegerField() {
        return integerField;
    }

    public void setIntegerField(final Integer integerField) {
        this.integerField = integerField;
    }

    public List<String> getListField() {
        return listField;
    }

    public void setListField(final List<String> listField) {
        this.listField = listField;
    }

    public static class Builder {
        private String stringField;
        private Integer integerField;
        private List<String> listField;

        public Builder() {
            stringField = "string";
            integerField = 0;
            listField = Collections.singletonList("string");
        }

        public Builder withStringField(final String stringField) {
            this.stringField = stringField;
            return this;
        }

        public Builder withIntegerField(final Integer integerField) {
            this.integerField = integerField;
            return this;
        }

        public Builder withListField(final List<String> listField) {
            this.listField = listField;
            return this;
        }

        public TestBean build() {
            final TestBean testBean = new TestBean();
            testBean.setStringField(stringField);
            testBean.setIntegerField(integerField);
            testBean.setListField(listField);
            return testBean;
        }
    }
}
