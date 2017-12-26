package nl.agility.commons.repository.test;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;

public abstract class BaseRestIntegrationTest extends BaseTest {

    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String EXPIRES = "Expires";
    public static final String PRAGMA = "Pragma";
    public static final String SERVER = "Server";
    public static final String CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    public static final String FRAME_OPTIONS = "X-Frame-Options";
    public static final String XSS_PROTECTION = "X-XSS-Protection";

//    public static final String CONTENT_TYPE = "Content-Type";

    @SuppressWarnings("serial")
    public static final Map<String, Matcher<?>> SECURITY_HEADERS = new HashMap<String, Matcher<?>>() {{
        put(CACHE_CONTROL, equalTo("no-cache, no-store, max-age=0, must-revalidate"));
        put(EXPIRES, equalTo("0"));
        put(PRAGMA, equalTo("no-cache"));
        put(SERVER, equalTo("Unknown"));
        put(CONTENT_TYPE_OPTIONS, equalTo("nosniff"));
        put(FRAME_OPTIONS, equalTo("SAMEORIGIN"));
        put(XSS_PROTECTION, equalTo("1; mode=block"));

    }};

    @SuppressWarnings("serial")
    public static final Map<String, Matcher<?>> RESPONSE_HEADERS = new HashMap<String, Matcher<?>>() {{
        putAll(SECURITY_HEADERS);
//            put(CONTENT_TYPE, equalTo("application/hal+json;charset=UTF-8"));
    }};

}
