package com.sinostar.assistant.ui.BlogMessage;

import java.io.Serializable;
import java.util.List;

public class TestEntity implements Serializable {
    private ResultBean Result;

    public ResultBean getResult(){return Result;}

    public void setResult(ResultBean result){this.Result=result;}

    public static class ResultBean {

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public static class ListBean {
            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            private String TestTitle;
            private String TestMessage;

            public ListBean(String title, String message) {
                TestTitle = title;
                TestMessage = message;
            }

            public String getTestTitle() {
                return TestTitle;
            }

            public void setTestTitle(String title) {
                this.TestTitle = title;
            }

            public String getTestMessage() {
                return TestMessage;
            }

            public void setTestMessage(String message) {
                this.TestMessage = message;
            }
        }
    }
}
