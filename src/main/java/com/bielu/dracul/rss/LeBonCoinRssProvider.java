package com.bielu.dracul.rss;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.htmlparser.Node;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

/**
 * Servlet implementation class for Servlet: TrojmiastoRssProvider
 */
public class LeBonCoinRssProvider extends AbstractRssProvider {

    private static final long serialVersionUID = 1L;
    private static final String TITLE_XML = "<title><![CDATA[leboncoin.fr - ${subtitle}]]></title>\n";

    @Override
	protected List<Article> getArticles(NodeIterator iter) throws ParserException {
        List<Article> result = new ArrayList<Article>();
        
        while (iter.hasMoreNodes()) {
            Node n = iter.nextNode();
            if (n instanceof TagNode) {
                TagNode node = (TagNode) n;
                
                if ("list-ads".equals(node.getAttribute("class"))) {
                    return processItem(node.getChildren().elements(), null);
                } else {
                    if (node.getChildren() != null) {
                        result.addAll(getArticles(node.getChildren().elements()));
                    }
                }
            }
        }
        
        return result;
    }
    
    private List<Article> processItem(NodeIterator elements, List<Article> resultList) throws ParserException {
    	List<Article> result = new ArrayList<Article>();
    	if (resultList != null) {
    		result = resultList;
    	}
        
        while (elements.hasMoreNodes()) {
        	boolean department = false;
            Node node = elements.nextNode();
            if (node instanceof LinkTag) {
                if (node.getChildren().elementAt(0) instanceof TextNode) {
                    LinkTag tmp = (LinkTag) node;
                    Article a = new Article();
                    a.setLink(tmp.extractLink());
                    a.setTitle(StringEscapeUtils.unescapeHtml(tmp.getAttribute("title")));
                    a.setDescription(StringEscapeUtils.unescapeHtml(tmp.getAttribute("title")));
                    result.add(a);
                }
            } else if (node instanceof Div) {
            	if (node.getChildren() != null && node.getChildren().elements() != null) {
            		processItem(node.getChildren().elements(), result);
            	}
            } else if (node instanceof ImageTag) {
                if (result.size() > 0) {
                	Article a = result.get(result.size() - 1);
                	a.setImage(((ImageTag) node).toHtml());
                }
            } else if (node instanceof TextNode && node.toHtml().trim().contains("&euro;")) {
                if (result.size() > 0) {
                    Article a = result.get(result.size() - 1);
                    a.setDescription(a.getRawDescription() + " " + node.toHtml().trim());
                    a.setTitle(a.getTitle() + " - " + node.toHtml().trim().replace("&nbsp;", " ").replace("&euro;", "€"));
                }
            } else if (node instanceof TextNode && node.toHtml().toLowerCase().trim().contains("alpes-maritimes")) {
                department = true;
            } else if (node instanceof TextNode && department == true && node.toHtml().contains("<br />") == false) {
                if (result.size() > 0) {
                    Article a = result.get(result.size() - 1);
                    a.setTitle(a.getTitle() + " (" + node.toHtml().trim().replace("&nbsp;", " ") + ")");
                    department = false;
                }
            }
        }
        return result;
    }

    @Override
	protected String getTitle() {
        return TITLE_XML;
    }

    @Override
	protected File getFile() {
        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH.mm");
        String now = format.format(new Date());
        
        return new File(String.format("%s/leboncoin/%s%s%s", tempDir.getAbsolutePath(), getPrefix(), now, RSS));
    }

    protected String getPrefix() {
        return "";
    }
}