package com.bielu.dracul.rss;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

/**
 * Servlet implementation class for Servlet: TrojmiastoRssProvider
 */
public class NewTrojmiastoRssProvider extends AbstractRssProvider {

	private static final long serialVersionUID = 1L;
    private static final String TITLE_XML = "<title><![CDATA[Przemyslaw Bielicki - Trojmiasto.pl]]></title>\n";

    protected List<Article> getArticles(NodeIterator iter) throws ParserException {
        List<Article> list = new ArrayList<Article>();
        
        while (iter.hasMoreNodes()) {
            Node n = iter.nextNode();
            if (n instanceof TagNode) {
                TagNode node = (TagNode) n;
                
                if ("arch-item".equals(node.getAttribute("class"))) {
                    list.add(processListItem(node.getChildren().elements()));
                } else {
                    if (node.getChildren() != null) {
                        list.addAll(getArticles(node.getChildren().elements()));
                    }
                }
            }
        }
        
        return list;
    }
    
    private Article processListItem(NodeIterator elements, Article... article) throws ParserException {
        Article result = null;
        if (article.length == 0) {
            result = new Article();
        } else {
            result = article[0];
        }
        
        while (elements.hasMoreNodes()) {
            Node node = elements.nextNode();
            
            if (node instanceof Div) {
                Div span = (Div) node;
                if ("category".equals(span.getAttribute("class")) && result.getCategory().length() == 0) {
                    result.setCategory(span.getChild(1).getFirstChild().getText().trim());
                }
            }
            
            if (node instanceof LinkTag) {
                LinkTag titleLink = (LinkTag) node;
                if ("color04".equals(titleLink.getAttribute("class")) && result.getTitle().length() == 0) {
                    result.setTitle(titleLink.getStringText());
                }
            }
            
            if (node instanceof ParagraphTag) {
                ParagraphTag paragraph = (ParagraphTag) node;
                LinkTag link = (LinkTag) paragraph.getChild(1);
                result.setLink(link.extractLink());
                ImageTag img = (ImageTag) link.getFirstChild();
                img.removeAttribute("alt");
                img.setAttribute("src", img.getImageURL());
                result.setImage(img.toHtml());
                
                TextNode text = (TextNode) paragraph.getChild(2);
                result.setDescription(text.getText().trim());
            }
            
            if (node.getChildren() != null) {
                result = processListItem(node.getChildren().elements(), result);
            }
        }
        return result;
    }

    protected String getTitle() {
        return TITLE_XML;
    }

    protected File getFile() {
        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH.mm");
        String now = format.format(new Date());
        
        return new File(String.format("%s/trojmiasto/%s%s%s", tempDir.getAbsolutePath(), getPrefix(), now, RSS));
    }

    protected String getPrefix() {
        return "";
    }
}