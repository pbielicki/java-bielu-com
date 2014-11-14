package com.bielu.dracul.rss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public abstract class AbstractRssProvider extends HttpServlet {
    
    private static final long serialVersionUID = -1490588154963983671L;
    
    protected static final String RSS = ".rss";
    protected static final String FULL_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static final String START_XML = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<rss xmlns:jf='http://www.jivesoftware.com/xmlns/jiveforums/rss' xmlns:feedburner='http://rssnamespace.org/feedburner/ext/1.0' version='2.0'>\n"
            + "<channel>\n"
            + "<link>http://java.bielu.com</link>\n"
            + "<description>java.bielu.com</description>\n"
            + "<language>pl</language>\n"
            + "<copyright>Copyright 2010 Przemyslaw Bielicki</copyright>\n"
            + "<lastBuildDate>${lastBuildDate}</lastBuildDate>\n"
            + "<generator>java.bielu.com</generator>\n"
            + "<managingEditor>bielu@dracul.kill.pl</managingEditor>\n"
            + "<webMaster>bielu@dracul.kill.pl</webMaster>\n"
            + "<ttl>10</ttl>\n"
            + "<pubDate>${pubDate}</pubDate>\n"
            + "<atom10:link xmlns:atom10='http://www.w3.org/2005/Atom' rel='self' href='${rssUrl}' type='application/rss+xml' />\n"
            + "<feedburner:browserFriendly>This is an XML content feed. It is intended to be viewed in a newsreader or syndicated to another site, subject to copyright and fair use.</feedburner:browserFriendly>\n";

    private static final String ITEM_XML = "<item>\n" + "<link>${link}</link>\n"
            + "<guid isPermaLink='false'>${link}</guid>\n" + "<feedburner:origLink>${link}</feedburner:origLink>\n"
            + "<author>Przemyslaw Bielicki</author>\n" + "<title><![CDATA[${title}]]></title>\n"
            + "<category><![CDATA[${category}]]></category>\n"
            + "<description><![CDATA[${description}]]></description>\n" + "<published>${pubDate}</published>\n"
            + "</item>\n";

    private static final String END_XML = "</channel>\n" + "</rss>\n";

    private String address;
    
    @Override
    public void init() throws ServletException {
        address = getServletConfig().getInitParameter("address");
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean forceNew = (request.getParameter("forceNew") != null);
        response.setContentType("application/rss+xml");
        response.setCharacterEncoding("UTF-8");

        try {
            File fileRss = processUrlAndStoreRss(request, forceNew);
            FileInputStream in = new FileInputStream(fileRss);
            byte[] buffer = new byte[2048];
            int size = 0;
            while ((size = in.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, size);
            }
            in.close();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected File processUrlAndStoreRss(HttpServletRequest request, boolean forceFresh) throws Exception {
        File fileRss = getFile();
        if (fileRss.getParentFile().exists() == false) {
            fileRss.getParentFile().mkdirs();
        }
        if (fileRss.exists() == false || forceFresh == true) {
            // clean old cache files
            for (File file : fileRss.getParentFile().listFiles()) {
                if (file.getName().endsWith(RSS)) {
                    file.delete();
                }
            }

            synchronized (this) {
            	String title = getTitle();
            	String subtitle = "All";
            	String localAddress = address;
            	if (request.getParameter("q") != null) {
            		localAddress += "&q=" + request.getParameter("q");
            		subtitle = StringUtils.capitalize(request.getParameter("q").toLowerCase());
            	}
            	title = title.replace("${subtitle}", subtitle);
                List<Article> articles = getArticles(new Parser(localAddress).elements());

                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileRss), Charset.forName("UTF-8"));
                try {
                    String nowString = new SimpleDateFormat(FULL_TIME_FORMAT).format(new Date());
                    out.append(START_XML.replace("${lastBuildDate}", nowString).replace("${pubDate}", nowString)
                            .replace("${rssUrl}", request.getRequestURL()));
                    out.append(title);

                    for (Article article : articles) {
                        out.append(ITEM_XML.replace("${link}", article.getLink()).replace("${title}",
                                article.getTitle()).replace("${category}", article.getCategory()).replace(
                                "${description}", article.getDescription()).replace("${pubDate}", nowString));
                    }

                    out.append(END_XML);
                    out.close();
                } catch (IOException e) {
                    out.close();
                    fileRss.delete();
                    throw e;
                }
            }
        }
        return fileRss;
    }

    protected abstract String getTitle();
    
    protected abstract List<Article> getArticles(NodeIterator elements) throws ParserException;
    
    protected abstract File getFile();
}
