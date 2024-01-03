
import java.util.HashMap;
import java.util.Map;

public class XUrlImpl implements XUrl {

    private Map<String, String> longToShortUrl;
    private Map<String, String> shortToLongUrl;
    private Map<String, Integer> hitCount;

    public XUrlImpl() {
        this.longToShortUrl = new HashMap<>();
        this.shortToLongUrl = new HashMap<>();
        this.hitCount = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        // If longUrl already has a corresponding shortUrl, return that shortUrl
        if (longToShortUrl.containsKey(longUrl)) {
            return longToShortUrl.get(longUrl);
        }

        // If longUrl is new, create a new shortUrl for the longUrl and return it
        String shortUrl = generateShortUrl();

        longToShortUrl.put(longUrl, shortUrl);
        shortToLongUrl.put(shortUrl, longUrl);

        hitCount.put(longUrl, 0);

        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        // If shortUrl already exists, return null
        if (shortToLongUrl.containsKey(shortUrl)) {
            return null;
        }

        // Register a mapping between the longUrl and specified shortUrl
        longToShortUrl.put(longUrl, shortUrl);
        shortToLongUrl.put(shortUrl, longUrl);

        hitCount.put(longUrl, 0);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        // If shortUrl doesn't have a corresponding longUrl, return null
        if (!shortToLongUrl.containsKey(shortUrl)) {
            return null;
        }

        // Return the corresponding longUrl
        String longUrl = shortToLongUrl.get(shortUrl);
        // Increment hit count
        hitCount.put(longUrl, hitCount.get(longUrl) + 1);

        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        // Return the number of times the longUrl has been looked up using getUrl()
        return hitCount.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        // Delete the mapping between this longUrl and its corresponding shortUrl
        String shortUrl = longToShortUrl.remove(longUrl);
        if (shortUrl != null) {
            shortToLongUrl.remove(shortUrl);
        }
        return shortUrl;
    }

    // Helper method to generate a unique alphanumeric short URL
    private String generateShortUrl() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortUrl = new StringBuilder("http://short.url/");
        for (int i = 0; i < 9; i++) {
            int index = (int) (Math.random() * characters.length());
            shortUrl.append(characters.charAt(index));
        }
        return shortUrl.toString();
    }

}
