package medium;

public class BrowserHistory {

    Page homePage;

    Page current;
    public BrowserHistory(String homepage) {
        this.current = this.homePage = new Page(homepage);
    }

    public void visit(String url) {
        Page page = new Page(url);
        page.previousPage = current;
        current.nextPage = page;
        current = page;
    }

    public String back(int steps) {
        Page curr = current;
        while (curr != null && curr.previousPage != null && steps > 0) {
            curr = curr.previousPage;
            steps--;
        }
        current = curr;
        return curr.url;
    }

    public String forward(int steps) {
        Page curr = current;
        while (curr != null && curr.nextPage != null && steps > 0) {
            curr = curr.nextPage;
            steps--;
        }
        current = curr;
        return curr.url;
    }
}
class Page {
    String url;
    Page nextPage;
    Page previousPage;

    public Page(String url) {
        this.url = url;
        this.nextPage = null;
        this.previousPage = null;
    }
}
