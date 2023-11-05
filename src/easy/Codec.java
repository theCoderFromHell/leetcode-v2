package easy;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

public class Codec {

    private static HashMap<String, String> urlMapper;

    public Codec() {
        this.urlMapper = new HashMap<>();
    }

    // Encodes a URL to a shortened URL.
    public static String encode(String longUrl) {
        if(longUrl == null || longUrl.isBlank())
            return "";
        String encodedTinyURL = Base64.getUrlEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8));
        urlMapper.put(encodedTinyURL, longUrl);
        return encodedTinyURL;
    }

    // Decodes a shortened URL to its original URL.
    public static String decode(String shortUrl) {
        if(shortUrl == null || shortUrl.isBlank() || !urlMapper.containsKey(shortUrl))
            return "";
        return urlMapper.get(shortUrl);
    }

    public static void main(String[] args) {
        Codec codec = new Codec();
        String encoded = codec.encode("https://leetcode.com/problems/design-tinyurl");
        System.out.println(encoded);
        System.out.println(codec.decode(encoded));
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(url));
