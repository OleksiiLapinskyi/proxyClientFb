package models;

/**
 * Created by alapi on 7/24/2016.
 */
public class Picture {

    private boolean is_silhouette;
    private String url;

    public boolean is_silhouette() {
        return is_silhouette;
    }

    public void setIs_silhouette(boolean is_silhouette) {
        this.is_silhouette = is_silhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString(){
        return url;
    }
}
