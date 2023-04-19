package shop.mtcoding.metablog.core.util;

public class Script {
    public static String href(String msg, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.href='"+url+"';");
        sb.append("</script>");
        return sb.toString();
    }

    public static String href(String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("location.href='"+url+"';");
        sb.append("</script>");
        return sb.toString();
    }

    public static String back(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
