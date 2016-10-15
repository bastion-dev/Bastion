package rocks.bastion.support.embedded;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import spark.ResponseTransformer;
import spark.Spark;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static rocks.bastion.support.embedded.SushiError.*;
import static spark.Spark.*;

/**
 * A sushi based testing service that contains basic API functionality using {@link Spark} as a lightweight web service framework.
 * The sushi service deploys an embedded Jetty container on a provided port and exposes the APIs on localhost.
 */
public class SushiService {

    private Map<Long, Sushi> sushiRepository = new HashMap<>();
    private AtomicLong nextId = new AtomicLong();
    private int port;
    private String restaurantName = "The Sushi Parlour";

    public SushiService(int port) {
        this.port = port;
    }

    /**
     * Registers all routes for the web service and starts up the embedded Jetty container.
     */
    public void start() {
        JsonTransformer json = new JsonTransformer();

        port(port);

        before("/protected/*", (req, res) -> res.body(json.render(NOT_AUTHENTICATED.toResponse(res))));

        get("/restaurant", (req, res) -> {
            res.header("Content-type", "plain/text");
            return restaurantName;
        });

        post("/restaurant", (req, res) -> {
            res.header("Content-type", "plain/text");
            restaurantName = req.body();
            return restaurantName;
        });

        post("/sushi", (req, res) -> {
            res.header("Content-type", "application/json");
            Sushi newSushi = json.fromJson(req.body(), Sushi.class);
            long id = nextId.incrementAndGet();
            newSushi.setId(id);
            sushiRepository.put(id, newSushi);
            res.status(SC_CREATED);
            return newSushi;
        }, json);

        get("/sushi", (req, res) -> {
            res.header("Content-type", "application/json");
            String nameFilter = req.queryParams("name");
            if (nameFilter == null) {
                return sushiRepository.values();
            } else {
                return sushiRepository.values().stream().filter(sushi -> sushi.getName().toLowerCase().equals(nameFilter.toLowerCase())).collect(toList());
            }
        }, json);

        get("/sushi/:id", (req, res) -> {
            res.header("Content-type", "application/json");
            long id = Integer.parseInt(req.params("id"));
            Sushi sushi = sushiRepository.get(id);
            if (sushi == null) {
                return NOT_FOUND.toResponse(res);
            }
            return sushi;
        }, json);

        get("/nigiri", (req, res) -> {
            res.header("Content-type", "application/json");
            Sushi sushi = new Sushi();
            sushi.setId(5L);
            sushi.setName("Salmon Nigiri");
            sushi.setPrice(new BigDecimal("23.55"));
            return sushi;
        }, json);

        delete("/sushi/:id", (req, res) -> {
            res.header("Content-type", "application/json");
            long id = Integer.parseInt(req.params("id"));
            Sushi removed = sushiRepository.remove(id);
            if (removed == null) {
                return NOT_FOUND.toResponse(res);
            }
            return removed;
        });

        post("/sashimi", (req, res) -> {
            res.header("Content-type", "application/json");
            Sushi sushi = new Sushi();
            sushi.setId(5L);
            sushi.setName("Sashimi");
            sushi.setPrice(new BigDecimal("5.95"));
            return sushi;
        }, json);

        exception(RuntimeException.class, (ex, req, res) -> {
            res.header("Content-type", "application/json");
            res.body(json.render(INTERNAL_SERVER_ERROR.toResponse(res, getRootCauseMessage(ex))));
        });
        exception(JsonParseException.class, (ex, req, res) -> {
            res.header("Content-type", "application/json");
            res.body(json.render(INVALID_ENTITY.toResponse(res, getRootCauseMessage(ex))));
        });
    }

    public void stop() {
        Spark.stop();
    }

    /**
     * {@link ResponseTransformer} for converting {@link Spark} responses to JSON.
     */
    private class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }

        public <T> T fromJson(String json, Class<T> type) {
            return gson.fromJson(json, type);
        }
    }
}