package App.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Model {
    public int appMode;
    public int pagesCount;
    public ArrayList<MovieData> moviesArray;

    public Model(int appMode) {
        this.appMode = appMode;
        this.moviesArray = new ArrayList<>();
    }

    public boolean start(String movieName, int pageNum) {
        this.pagesCount = 0;

        try {
            Document allMoviesDoc = Jsoup.connect("https://prehraj.to/hledej/" + movieName + "?vp-page=" + pageNum).userAgent("Mozilla/17.0").get();
            Elements allMoviesLinks = allMoviesDoc.select("a.video-item.video-item-link");
            int results = allMoviesLinks.size();

            Elements allOtherPagesLinks = allMoviesDoc.select("a.pagination-item");
            if (!allOtherPagesLinks.isEmpty()) {
                String lastPageNumStr = Objects.requireNonNull(allOtherPagesLinks.last()).text();
                this.pagesCount = Integer.parseInt(lastPageNumStr);
            }

            ExecutorService es = Executors.newCachedThreadPool();
            for (int i = 0; i < results; i++) {
                int finalI = i;
                es.execute(new Thread(() -> {
                    getAllMovies(finalI, results, allMoviesLinks);
                }));
            }
            es.shutdown();
            return es.awaitTermination(10, TimeUnit.SECONDS);

        }
        catch (IOException | InterruptedException | NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getAllMovies(int i, int results, Elements allMoviesLinks) {
        try {
            String videoLink = allMoviesLinks.eq(i).attr("href");
            Document selectedMovieDoc = Jsoup.connect("https://prehraj.to" + videoLink).userAgent("Mozilla/17.0").get();

            String finalMovieName = selectedMovieDoc.getElementsByClass("h2 video-detail-title").text();
            Elements scriptElements = selectedMovieDoc.getElementsByTag("script");

            for (Element element : scriptElements) {
                if (element.data().contains("var sources")) {
                    String movieThumbImg = Objects.requireNonNull(selectedMovieDoc.select("meta[property=og:image]").first()).attr("content");
                    MovieData newMovie = getBestQualityLink(i, element.toString(), finalMovieName, movieThumbImg);
                    if (newMovie != null) {
                        this.moviesArray.add(newMovie);
                        double percentage = (((100 * (i + 1)) / (float) results) / 100);
                    }
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MovieData getBestQualityLink(int id, String htmlString, String finalMovieName, String movieThumbImg) {
        int stringStart = htmlString.indexOf("file: \"");
        int stringEnd = htmlString.indexOf("\", label:");

        if (stringStart < 0 || stringEnd < 0)
            return null;

        int quality = getMovieQuality(htmlString, stringEnd);
        String finalLink = linkParser(htmlString, stringStart, stringEnd);

        int nextStringStart = stringEnd;
        int nextStringEnd = stringEnd;

        while (nextStringEnd != -1) {
            nextStringStart = htmlString.indexOf("file: \"", nextStringStart);
            if (nextStringStart != -1) {
                nextStringEnd = htmlString.indexOf("\", label:", nextStringStart);
                if (nextStringEnd != -1) {
                    Integer nextQuality = getMovieQuality(htmlString, nextStringEnd);
                    if (nextQuality > quality) {
                        finalLink = linkParser(htmlString, nextStringStart, nextStringEnd);
                        nextStringStart = nextStringEnd;
                        quality = nextQuality;
                    }
                }
            }
            else {
                nextStringEnd = -1;
            }
        }
        return new MovieData(id, finalMovieName, finalLink, quality, movieThumbImg, this.appMode);
    }

    public Integer getMovieQuality(String htmlString, int stringEnd) {
        int qualityStart =  stringEnd + 11;
        int qualityEnd = htmlString.indexOf('p', qualityStart);
        return Integer.valueOf(htmlString.substring(qualityStart, qualityEnd));
    }

    public String linkParser(String htmlString, int stringStart, int stringEnd) {
        String subStrLink = htmlString.substring(stringStart + 7, stringEnd);
        StringBuilder finalLinkSB = new StringBuilder(subStrLink);
        int subStrLinkLen = subStrLink.length();
        int subStrLinkPos = 0;

        while (subStrLinkPos < subStrLinkLen) {
            if (finalLinkSB.charAt(subStrLinkPos) == '\\') {
                finalLinkSB.deleteCharAt(subStrLinkPos);
                subStrLinkLen--;
            }
            else {
                subStrLinkPos++;
            }
        }
        return finalLinkSB.toString();
    }

    public void printAllMoviesLinks(ArrayList<MovieData> movieLinks) {
        for (MovieData movie : movieLinks)
            System.out.println(movie);
    }
}




