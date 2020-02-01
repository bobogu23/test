package algorith.stack;

/**
 * 模拟浏览器前进后退。使用两个栈,将浏览过的网页 当做一个节点 放入第一个栈X中。如果有后退操作将该节点放入另一个栈Y中.如果做前进操作，将Y中
 * 的节点取出
 * @author:ben.gu
 * @Date:2020/2/1 4:13 PM
 */
public class SimulateBrowser {

    private Stack a;

    private Stack b;

    public static void main(String args[]) {
        SimulateBrowser b = new SimulateBrowser();
        b.browse("a");
        b.browse("b");
        b.browse("c");
        b.browse("d");

        System.err.println("BrowsedPage:");
        b.printBrowsedPage();

        System.err.println("BackwardPage:");
        b.printBackwardPage();

        System.err.println("--------");

        System.err.println("backward--------");

        b.backward();

        System.err.println("BrowsedPage:");
        b.printBrowsedPage();
        System.err.println("BackwardPage:");
        b.printBackwardPage();

        System.err.println("backward--------");

        b.backward();

        System.err.println("BrowsedPage:");
        b.printBrowsedPage();
        System.err.println("BackwardPage:");

        b.printBackwardPage();

        System.err.println("forward--------");

        b.forward();
        System.err.println("BrowsedPage:");

        b.printBrowsedPage();
        System.err.println("BackwardPage:");

        b.printBackwardPage();

        b.browse("x");
        System.err.println("BrowsedPage:");

        b.printBrowsedPage();
        System.err.println("BackwardPage:");

        b.printBackwardPage();

        System.err.println("forward--------");

        b.forward();

        System.err.println("BrowsedPage:");
        b.printBrowsedPage();
        System.err.println("BackwardPage:");

        b.printBackwardPage();

    }

    public SimulateBrowser() {
        this.a = new Stack();
        this.b = new Stack();
    }

    static class WebPage {
        private String data;

        private WebPage next;

        private WebPage pre;

        public WebPage(String data, WebPage next, WebPage pre) {
            this.data = data;
            this.next = next;
            this.pre = pre;
        }
    }

    public void printBrowsedPage() {
        a.print();
    }

    public void printBackwardPage() {
        b.print();
    }

    //浏览网页压栈
    public String browse(String page) {
        a.push(page);
        return page;
    }

    public String backward() {
        WebPage page = a.pop();
        if (page == null) {
            return null;
        }
        //push into stack b
        b.push(page);
        return page.data;
    }

    //前进 判断stack b的栈顶元素的next 节点是否是 stack a 的栈顶元素,如果不是 说明 前进之前浏览了新的网页，无法前进。
    // 清空stack b
    public String forward() {
        WebPage page = b.pop();
        if (page == null) {
            return null;
        }
        if (a.isEmpty() || a.top().pre == page) {
            a.push(page);
            return page.data;
        }
        b.clear();
        return null;
    }

    static class Stack {
        private WebPage top;

        public void clear() {
            this.top = null;
        }

        public WebPage top() {
            return this.top;
        }

        public boolean isEmpty() {
            return this.top == null;
        }

        public void push(WebPage data) {
            WebPage top = this.top;
            WebPage n = data;
            if (top == null) {
                this.top = n;
                return;
            }
            n.next = top;
            // this pre property of next node of  the top should be new node
            top.pre = n;
            this.top = n;
        }

        //压栈
        public void push(String data) {
            WebPage top = this.top;
            WebPage n = new WebPage(data, top, null);
            if (top == null) {
                this.top = n;
                return;
            }
            n.next = top;
            // this pre property of next node of  the top should be new node
            top.pre = n;
            this.top = n;
        }

        public WebPage pop() {
            if (this.top == null) {
                return null;
            }
            WebPage t = this.top;
            this.top = t.next;
            t.next = null;
            return t;
        }

        public void print() {
            if (this.top == null) {
                System.err.println("empty");
                return;
            }
            StringBuilder sb = new StringBuilder();
            WebPage p = this.top;
            while (p != null) {
                sb.append(p.data).append(",");
                p = p.next;
            }
            System.err.println(sb.deleteCharAt(sb.length() - 1).toString());

        }
    }

}
