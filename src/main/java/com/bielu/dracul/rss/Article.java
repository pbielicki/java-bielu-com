package com.bielu.dracul.rss;

public class Article {

    private String title = "";
    private String description = "";
    private String image;
    private String link = "";
    private String category = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        if (image != null) {
            return String.format("%s<br/><br/>%s", image, description);
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	public String getRawDescription() {
		return description;
	}
}
