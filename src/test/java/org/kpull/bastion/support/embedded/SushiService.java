package org.kpull.bastion.support.embedded;

import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;
import static org.kpull.bastion.support.embedded.SushiError.*;
import static spark.Spark.*;

public class SushiService {

    private static Map<Long, Sushi> sushiRepository = new HashMap<>();
    private static AtomicLong idGenerator = new AtomicLong();

    public static void start(final int port) {
        final JsonTransformer json = new JsonTransformer();

        port(port);

        before("/protected/*", (req, res) -> res.body(json.render(NOT_AUTHENTICATED.toResponse(res))));
        after((req, res) -> res.header("Content-Type", "application/json"));

        post("/sushi", (req, res) -> {
            final Sushi newSushi = json.fromJson(req.body(), Sushi.class);
            long id = idGenerator.incrementAndGet();
            newSushi.setId(id);
            sushiRepository.put(id, newSushi);
            return newSushi;
        }, json);

        get("/sushi", (req, res) -> {
            final String nameFilter = req.queryParams("name");
            if (nameFilter == null) {
                return sushiRepository.values();
            } else {
                return sushiRepository.values().stream().filter(sushi -> sushi.getName().toLowerCase().equals(nameFilter.toLowerCase())).collect(toList());
            }
        }, json);

        get("/sushi/:id", (req, res) -> {
            final long id = Integer.parseInt(req.params("id"));
            final Sushi sushi = sushiRepository.get(id);
            if (sushi == null) {
                return NOT_FOUND.toResponse(res);
            }
            return sushi;
        }, json);

        delete("/sushi/:id", (req, res) -> {
            final long id = Integer.parseInt(req.params("id"));
            final Sushi removed = sushiRepository.remove(id);
            if (removed == null) {
                return NOT_FOUND.toResponse(res);
            }
            return removed;
        });

        exception(RuntimeException.class, (ex, req, res) -> res.body(json.render(INTERNAL_SERVER_ERROR.toResponse(res))));
    }

    public static void stop() {
        Spark.stop();
    }

    private static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(final Object model) {
            return gson.toJson(model);
        }

        public <T> T fromJson(final String json, final Class<T> type) {
            return gson.fromJson(json, type);
        }
    }
}